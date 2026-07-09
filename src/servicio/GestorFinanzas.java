package servicio;

import estructuras.ListaArreglo;
import estructuras.ListaDobleEnlazada;
import estructuras.ListaEnlazada;
import estructuras.ColaEnlazada;
import estructuras.PilaEnlazada;
import estructuras.Visitante;
import estructuras.MatrizDispersa;
import estructuras.ArbolBusquedaTransacciones;
import modelo.Categoria;
import modelo.Transaccion;

public class GestorFinanzas {
    private ListaArreglo<Transaccion> transacciones;
    private ListaEnlazada<Transaccion> transaccionesEnlazadas;
    private ListaDobleEnlazada<Categoria> categorias;
    private PilaEnlazada<String> historialAcciones;
    private ColaEnlazada<Transaccion> pagosPendientes;
    private MatrizDispersa matrizMensual;
    private ArbolBusquedaTransacciones abbPorId;
    private int siguienteId;

    public GestorFinanzas() {
        transacciones = new ListaArreglo<Transaccion>();
        transaccionesEnlazadas = new ListaEnlazada<Transaccion>();
        categorias = new ListaDobleEnlazada<Categoria>();
        historialAcciones = new PilaEnlazada<String>();
        pagosPendientes = new ColaEnlazada<Transaccion>();
        matrizMensual = new MatrizDispersa(12, 2);
        abbPorId = new ArbolBusquedaTransacciones();
        siguienteId = 1;
        cargarDatosIniciales();
    }

    public void registrarMovimiento(String tipo, String categoria, double monto, int mes, String descripcion) {
        validarMovimiento(tipo, monto, mes);
        Transaccion transaccion = new Transaccion(siguienteId, tipo, categoria, monto, mes, descripcion);
        siguienteId++;
        transacciones.agregar(transaccion);
        transaccionesEnlazadas.agregarAlFinal(transaccion);
        abbPorId.insertar(transaccion);
        matrizMensual.agregar(mes - 1, tipo.equals(Transaccion.TIPO_INGRESO) ? 0 : 1, monto);
        historialAcciones.apilar("Se registro: " + transaccion);
    }

    public void agregarCategoria(String nombre, double limite) {
        categorias.agregarAlFinal(new Categoria(nombre, limite));
        historialAcciones.apilar("Se agrego categoria: " + nombre);
    }

    public void encolarGastoPendiente(String categoria, double monto, int mes, String descripcion) {
        validarMovimiento(Transaccion.TIPO_GASTO, monto, mes);
        Transaccion pendiente = new Transaccion(0, Transaccion.TIPO_GASTO, categoria, monto, mes, descripcion);
        pagosPendientes.encolar(pendiente);
        historialAcciones.apilar("Se registro pago pendiente: " + descripcion);
    }

    public void procesarGastoPendiente() {
        Transaccion pendiente = pagosPendientes.desencolar();
        if (pendiente == null) {
            System.out.println("No hay pagos pendientes.");
            return;
        }
        registrarMovimiento(Transaccion.TIPO_GASTO, pendiente.obtenerCategoria(), pendiente.obtenerMonto(), pendiente.obtenerMes(), "Pago procesado");
    }

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
                return true;
            }
        }
        return false;
    }

    public Transaccion buscarPorId(int id) {
        return abbPorId.buscar(id);
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

    private void validarMovimiento(String tipo, double monto, int mes) {
        if (!Transaccion.TIPO_INGRESO.equals(tipo) && !Transaccion.TIPO_GASTO.equals(tipo)) {
            throw new IllegalArgumentException("Tipo de movimiento no valido.");
        }
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }
        if (mes < 1 || mes > 12) {
            throw new IllegalArgumentException("El mes debe estar entre 1 y 12.");
        }
    }

    private void cargarDatosIniciales() {
        agregarCategoria("Sueldo", 0);
        agregarCategoria("Comida", 600);
        agregarCategoria("Transporte", 250);
        agregarCategoria("Servicios", 350);
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 1, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 230, 1, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Transporte", 80, 1, "Recarga tarjeta");
        encolarGastoPendiente("Servicios", 210, 1, "Recibo de luz");
    }
}

