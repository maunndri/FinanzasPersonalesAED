package servicio;

import estructuras.ListaArreglo;
import estructuras.ListaDobleEnlazada;
import estructuras.ListaEnlazada;
import estructuras.ColaEnlazada;
import estructuras.PilaEnlazada;
import estructuras.Visitante;
import model.Categoria;
import model.Transaccion;
import estructuras.MatrizDensa;
import estructuras.ArbolBusquedaTransacciones;

public class GestorFinanzas {
    private ListaArreglo<Transaccion> transacciones;
    private ListaEnlazada<Transaccion> transaccionesEnlazadas;
    private ListaDobleEnlazada<Categoria> categorias;
    private PilaEnlazada<String> historialAcciones;
    private ColaEnlazada<Transaccion> pagosPendientes;
    private MatrizDensa matrizMensual;
    private ArbolBusquedaTransacciones abbPorId;
    private int siguienteId;
    private Transaccion ultimoMovimiento;

    // instancia las 7 estructuras (una por responsabilidad) y precarga 6 meses de datos de ejemplo
    public GestorFinanzas() {
        transacciones = new ListaArreglo<Transaccion>();
        transaccionesEnlazadas = new ListaEnlazada<Transaccion>();
        categorias = new ListaDobleEnlazada<Categoria>();
        historialAcciones = new PilaEnlazada<String>();
        pagosPendientes = new ColaEnlazada<Transaccion>();
        matrizMensual = new MatrizDensa(12, 2);
        abbPorId = new ArbolBusquedaTransacciones();
        siguienteId = 1;
        cargarDatosIniciales();
    }

    // el corazón del sistema: valida, crea la transacción, y la reparte a las 5 estructuras que
    // la necesitan (lista maestra, lista para ordenar, árbol por id, matriz de totales, pila de historial)
    public void registrarMovimiento(String tipo, String categoria, double monto, int mes, String descripcion) {
        validarMovimiento(tipo, categoria, monto, mes);
        Transaccion transaccion = new Transaccion(siguienteId, tipo, categoria, monto, mes, descripcion);
        siguienteId++;
        transacciones.agregar(transaccion);
        transaccionesEnlazadas.agregarAlFinal(transaccion);
        abbPorId.insertar(transaccion);
        matrizMensual.agregar(mes - 1, tipo.equals(Transaccion.TIPO_INGRESO) ? 0 : 1, monto);
        historialAcciones.apilar("Se registro: " + transaccion);
        ultimoMovimiento = transaccion;
    }

    public void agregarCategoria(String nombre, double limite) {
        agregarCategoria(nombre, limite, Transaccion.TIPO_GASTO);
    }

    public void agregarCategoria(String nombre, double limite, String tipo) {
        categorias.agregarAlFinal(new Categoria(nombre, limite, tipo));
        historialAcciones.apilar("Se agrego categoria: " + nombre);
    }

    // guarda un gasto "a futuro" en la cola; todavía no toca ninguna de las 5 estructuras de
    // registrarMovimiento, porque el gasto aún no se ha pagado de verdad
    public void encolarGastoPendiente(String categoria, double monto, int mes, String descripcion) {
        validarMovimiento(Transaccion.TIPO_GASTO, categoria, monto, mes);
        Transaccion pendiente = new Transaccion(0, Transaccion.TIPO_GASTO, categoria, monto, mes, descripcion);
        pagosPendientes.encolar(pendiente);
        historialAcciones.apilar("Se registro pago pendiente: " + descripcion);
    }

    // saca el primero de la fila (FIFO) y recién ahí lo convierte en un movimiento real
    public void procesarGastoPendiente() {
        Transaccion pendiente = pagosPendientes.desencolar();
        if (pendiente == null) {
            System.out.println("No hay pagos pendientes.");
            return;
        }
        registrarMovimiento(Transaccion.TIPO_GASTO, pendiente.obtenerCategoria(), pendiente.obtenerMonto(), pendiente.obtenerMes(), "Pago procesado");
    }

    // recorre la lista maestra buscando el id; al encontrarlo, lo quita de las 3 estructuras donde
    // vive (lista, lista enlazada, árbol) y resta su monto de la matriz mensual (el signo negativo
    // "deshace" la suma que se hizo al registrarlo)
    public boolean eliminarMovimientoPorId(int id) {
        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion actual = transacciones.obtener(i);
            if (actual.obtenerId() == id) {
                transacciones.eliminarEn(i);
                transaccionesEnlazadas.eliminar(actual);
                abbPorId.eliminar(id);
                matrizMensual.agregar(actual.obtenerMes() - 1, actual.obtenerTipo().equals(Transaccion.TIPO_INGRESO) ? 0 : 1,
                        -actual.obtenerMonto());
                historialAcciones.apilar("Se elimino movimiento #" + id);
                if (ultimoMovimiento != null && ultimoMovimiento.obtenerId() == id) {
                    ultimoMovimiento = null;
                }
                return true;
            }
        }
        return false;
    }

    // delega directo en el AVL: búsqueda en O(log n) en vez de recorrer la lista entera
    public Transaccion buscarPorId(int id) {
        return abbPorId.buscar(id);
    }

    public boolean hayUltimoMovimiento() {
        return ultimoMovimiento != null;
    }

    public String obtenerDescripcionUltimoMovimiento() {
        return ultimoMovimiento == null ? "No hay movimientos registrados." : ultimoMovimiento.toString();
    }

    // deshace el último movimiento registrado: saca el post-it de la pila (el texto de ese registro)
    // y llama a eliminarMovimientoPorId para sacarlo también de las demás estructuras
    public String eliminarUltimoMovimiento() {
        if (ultimoMovimiento == null) {
            return null;
        }
        String descripcion = ultimoMovimiento.toString();
        int id = ultimoMovimiento.obtenerId();
        historialAcciones.desapilar();
        eliminarMovimientoPorId(id);
        return descripcion;
    }

    // patrón Visitor: recorre las categorías buscando nombre+tipo exacto. El array de 1 casilla
    // (encontrada[0]) es el truco para poder "modificar" una variable desde dentro de la clase anónima
    public Categoria buscarCategoria(final String nombre, final String tipo) {
    final Categoria[] encontrada = new Categoria[1];
    categorias.recorrerAdelante(new Visitante<Categoria>() {
        public void visitar(Categoria valor) {
            if (encontrada[0] == null && valor.obtenerNombre().equalsIgnoreCase(nombre)
                    && valor.obtenerTipo().equals(tipo)) {
                encontrada[0] = valor;
            }
        }
        });
        return encontrada[0];
    }

    // suma con Visitante todos los gastos que coincidan en categoría y mes; mismo truco del array
    // de 1 casilla para acumular el total dentro de la clase anónima
    public double obtenerGastoPorCategoriaYMes(final String categoria, final int mes) {
    final double[] total = new double[] { 0 };
    transacciones.recorrer(new Visitante<Transaccion>() {
        public void visitar(Transaccion valor) {
            if (valor.obtenerMes() == mes
                    && Transaccion.TIPO_GASTO.equals(valor.obtenerTipo())
                    && valor.obtenerCategoria().equalsIgnoreCase(categoria)) {
                total[0] += valor.obtenerMonto();
            }
        }
        });
        return total[0];
    }

    // un límite de 0 significa "sin límite definido" (así están cargadas todas las categorías de ingreso)
    public boolean tieneLimiteDefinido(String categoria) {
    Categoria cat = buscarCategoria(categoria, Transaccion.TIPO_GASTO);
    return cat != null && cat.obtenerLimiteMensual() > 0;
    }

    // compara el gasto acumulado del mes contra el límite de la categoría; base del sistema de alertas
    public boolean superaLimiteMensual(String categoria, int mes) {
        Categoria cat = buscarCategoria(categoria, Transaccion.TIPO_GASTO);
        if (cat == null || cat.obtenerLimiteMensual() <= 0) {
            return false;
        }
        return obtenerGastoPorCategoriaYMes(categoria, mes) > cat.obtenerLimiteMensual();
    }

    // los siguientes métodos recorrerXxx son puentes: reciben un Visitante de quien llama y lo
    // delegan a la estructura correspondiente, sin que quien llama necesite conocer esa estructura
    public void recorrerMovimientos(Visitante<Transaccion> visitante) {
            transacciones.recorrer(visitante);
        }

    // ordena por monto antes de recorrer (usa el merge sort de ListaEnlazada)
    public void recorrerMovimientosOrdenadosPorMonto(Visitante<Transaccion> visitante) {
        transaccionesEnlazadas.ordenar();
        transaccionesEnlazadas.recorrer(visitante);
    }

    public void recorrerCategoriasAdelante(Visitante<Categoria> visitante) {
        categorias.recorrerAdelante(visitante);
    }

    public void recorrerCategoriasAtras(Visitante<Categoria> visitante) {
        categorias.recorrerAtras(visitante);
    }

    // filtra por tipo antes de pasarle cada categoría al Visitante original; un Visitante "envolviendo" a otro
    public void recorrerCategoriasPorTipo(final String tipo, final Visitante<Categoria> visitante) {
        categorias.recorrerAdelante(new Visitante<Categoria>() {
            public void visitar(Categoria valor) {
                if (valor.obtenerTipo().equals(tipo)) {
                    visitante.visitar(valor);
                }
            }
        });
    }

    // los 3 recorrerArbolXxx delegan al AVL; recorrerArbolInorden es el más útil porque
    // da las transacciones ordenadas por id sin necesitar ordenar nada
    public void recorrerArbolInorden(Visitante<Transaccion> visitante) {
        abbPorId.recorrerInorden(visitante);
    }

    public void recorrerArbolPreorden(Visitante<Transaccion> visitante) {
        abbPorId.recorrerPreorden(visitante);
    }

    public void recorrerArbolPostorden(Visitante<Transaccion> visitante) {
        abbPorId.recorrerPostorden(visitante);
    }

    // recorre toda la lista maestra sumando ingresos y restando gastos; O(n), no usa la matriz
    public double obtenerSaldoActual() {
        double saldo = 0;
        for (int i = 0; i < transacciones.size(); i++) {
            Transaccion actual = transacciones.obtener(i);
            if (Transaccion.TIPO_INGRESO.equals(actual.obtenerTipo())) {
                saldo += actual.obtenerMonto();
            } else {
                saldo -= actual.obtenerMonto();
            }
        }
        return saldo;
    }

    // suma la columna 0 (ingresos) de la matriz mensual, mes por mes; obtenerTotalGastos es
    // exactamente igual pero con la columna 1 (gastos)
    public double obtenerTotalIngresos() {
        double total = 0;
        for (int mes = 0; mes < 12; mes++) {
            total += matrizMensual.obtener(mes, 0);
        }
        return total;
    }

    public double obtenerTotalGastos() {
        double total = 0;
        for (int mes = 0; mes < 12; mes++) {
            total += matrizMensual.obtener(mes, 1);
        }
        return total;
    }

    // lee directo una celda de la matriz (un mes y un tipo específicos), sin sumar todos los meses
    public double obtenerMontoMensual(int mes, String tipo) {
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
        int columna = Transaccion.TIPO_INGRESO.equals(tipo) ? 0 : 1;
        return matrizMensual.obtener(mes - 1, columna);
    }

    // demuestra las operaciones "académicas" de ListaArreglo (copiar, fusionar, comparar tamaño) en texto
    public String obtenerResumenOperacionesArreglo() {
        ListaArreglo<Transaccion> copiar = transacciones.copiar();
        ListaArreglo<Transaccion> fusionado = transacciones.fusionar(copiar);
        return "Cantidad original: " + transacciones.size()
                + "\nCopia del arreglo: " + copiar.size()
                + "\nFusion original + copia: " + fusionado.size()
                + "\nComparacion por size original/copia: " + transacciones.mismoTamanio(copiar);
    }

    public String obtenerUltimaAccion() {
        String last = historialAcciones.verCima();
        return last == null ? "No hay acciones para mostrar." : "Ultima accion: " + last;
    }

    public boolean hayPagosPendientes() {
        return !pagosPendientes.estaVacia();
    }

    public void listarMovimientosArreglo() {
        transacciones.recorrer(new Visitante<Transaccion>() {
            public void visitar(Transaccion valor) {
                System.out.println(valor);
            }
        });
    }

    public void listarMovimientosOrdenadosPorMonto() {
        transaccionesEnlazadas.ordenar();
        transaccionesEnlazadas.recorrer(new Visitante<Transaccion>() {
            public void visitar(Transaccion valor) {
                System.out.println(valor);
            }
        });
    }

    public void listarCategoriasAdelante() {
        categorias.recorrerAdelante(new Visitante<Categoria>() {
            public void visitar(Categoria valor) {
                System.out.println(valor);
            }
        });
    }

    public void listarCategoriasAtras() {
        categorias.recorrerAtras(new Visitante<Categoria>() {
            public void visitar(Categoria valor) {
                System.out.println(valor);
            }
        });
    }

    public void mostrarMatrizMensual() {
        System.out.println("Columnas: ingresos | gastos");
        matrizMensual.imprimir();
    }

    public void mostrarRecorridosArboles() {
        System.out.println("ABB por ID - inorden:");
        abbPorId.inorden();
        System.out.println("ABB por ID - preorden:");
        abbPorId.preorden();
        System.out.println("ABB por ID - postorden:");
        abbPorId.postorden();
    }

    public void mostrarDemoOperacionesArreglo() {
        ListaArreglo<Transaccion> copiar = transacciones.copiar();
        ListaArreglo<Transaccion> fusionado = transacciones.fusionar(copiar);
        System.out.println("Cantidad original: " + transacciones.size());
        System.out.println("Copia del arreglo: " + copiar.size());
        System.out.println("Fusion original + copia: " + fusionado.size());
        System.out.println("Comparacion por size original/copia: " + transacciones.mismoTamanio(copiar));
    }

    public void mostrarUltimaAccion() {
        String last = historialAcciones.desapilar();
        System.out.println(last == null ? "No hay acciones para mostrar." : "Ultima accion: " + last);
    }

    // se ejecuta antes de cualquier registro: si algo falla acá, se lanza la excepción y
    // registrarMovimiento se corta sin llegar a tocar ninguna estructura
    private void validarMovimiento(String tipo, String categoria, double monto, int mes) {
        if (!Transaccion.TIPO_INGRESO.equals(tipo) && !Transaccion.TIPO_GASTO.equals(tipo)) {
            throw new IllegalArgumentException("Tipo de movimiento no valido.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe seleccionar una categoria.");
        }
        if (!existeCategoriaParaTipo(categoria, tipo)) {
            throw new IllegalArgumentException("La categoria '" + categoria + "' no corresponde a un " + tipo + ".");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
    }

    // evita, por ejemplo, registrar un ingreso con categoría de gasto: revisa que exista una
    // categoría con ese nombre Y ese tipo exacto
    private boolean existeCategoriaParaTipo(final String nombre, final String tipo) {
        final boolean[] existe = new boolean[] { false };
        categorias.recorrerAdelante(new Visitante<Categoria>() {
            public void visitar(Categoria valor) {
                if (valor.obtenerNombre().equalsIgnoreCase(nombre.trim()) && valor.obtenerTipo().equals(tipo)) {
                    existe[0] = true;
                }
            }
        });
        return existe[0];
    }

    // precarga las categorías (6 de ingreso, 10 de gasto, cada una con su límite mensual) y
    // 6 meses de movimientos de ejemplo, para que el sistema no arranque vacío
    private void cargarDatosIniciales() {
        agregarCategoria("Sueldo", 0, Transaccion.TIPO_INGRESO);
        agregarCategoria("Bonos", 0, Transaccion.TIPO_INGRESO);
        agregarCategoria("Freelance", 0, Transaccion.TIPO_INGRESO);
        agregarCategoria("Ventas", 0, Transaccion.TIPO_INGRESO);
        agregarCategoria("Intereses", 0, Transaccion.TIPO_INGRESO);
        agregarCategoria("Otros ingresos", 0, Transaccion.TIPO_INGRESO);

        agregarCategoria("Comida", 600, Transaccion.TIPO_GASTO);
        agregarCategoria("Transporte", 250, Transaccion.TIPO_GASTO);
        agregarCategoria("Servicios", 350, Transaccion.TIPO_GASTO);
        agregarCategoria("Vivienda", 900, Transaccion.TIPO_GASTO);
        agregarCategoria("Salud", 300, Transaccion.TIPO_GASTO);
        agregarCategoria("Educacion", 400, Transaccion.TIPO_GASTO);
        agregarCategoria("Entretenimiento", 250, Transaccion.TIPO_GASTO);
        agregarCategoria("Ropa", 250, Transaccion.TIPO_GASTO);
        agregarCategoria("Deudas", 500, Transaccion.TIPO_GASTO);
        agregarCategoria("Cuidado personal", 200, Transaccion.TIPO_GASTO);
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 1, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 230, 1, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Transporte", 80, 1, "Recarga tarjeta");
        encolarGastoPendiente("Servicios", 210, 1, "Recibo de luz");

        // Febrero
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 2, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 210, 2, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Vivienda", 900, 2, "Alquiler");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Transporte", 75, 2, "Recarga tarjeta");

        // Marzo
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 3, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Bonos", 300, 3, "Bono trimestral");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 245, 3, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Servicios", 180, 3, "Recibo de agua");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Entretenimiento", 90, 3, "Cine");

        // Abril
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 4, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 220, 4, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Vivienda", 900, 4, "Alquiler");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Salud", 150, 4, "Consulta medica");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Ropa", 130, 4, "Compras de temporada");

        // Mayo
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 5, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Freelance", 450, 5, "Proyecto externo");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 260, 5, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Transporte", 90, 5, "Recarga tarjeta");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Deudas", 500, 5, "Cuota tarjeta de credito");

        // Junio
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 6, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 230, 6, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Vivienda", 900, 6, "Alquiler");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Servicios", 195, 6, "Recibo de luz");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Cuidado personal", 85, 6, "Peluqueria");
    }
}
