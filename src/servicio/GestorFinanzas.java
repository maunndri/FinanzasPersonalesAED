package servicio;

import estructuras.ListaArreglo;
import estructuras.ListaCircular;
import estructuras.ListaDobleEnlazada;
import estructuras.ListaEnlazada;
import estructuras.ColaPrioridad;
import estructuras.ColaArreglo;
import estructuras.ColaEnlazada;
import estructuras.PilaArreglo;
import estructuras.PilaEnlazada;
import estructuras.Visitante;
import estructuras.MatrizDispersa;
import estructuras.ArbolAvlTransacciones;
import estructuras.ArbolBusquedaTransacciones;
import modelo.Categoria;
import modelo.TareaPrioridad;
import modelo.Transaccion;

public class GestorFinanzas {
    private ListaArreglo<Transaccion> transacciones;
    private ListaEnlazada<Transaccion> transaccionesEnlazadas;
    private ListaDobleEnlazada<Categoria> categorias;
    private ListaCircular<String> consejos;
    private PilaEnlazada<String> historialAcciones;
    private PilaArreglo<String> pilaAuditoria;
    private ColaEnlazada<Transaccion> pagosPendientes;
    private ColaArreglo<String> notificaciones;
    private ColaPrioridad tareasPrioritarias;
    private MatrizDispersa matrizMensual;
    private ArbolBusquedaTransacciones abbPorId;
    private ArbolAvlTransacciones avlPorMonto;
    private int siguienteId;

    public GestorFinanzas() {
        transacciones = new ListaArreglo<Transaccion>();
        transaccionesEnlazadas = new ListaEnlazada<Transaccion>();
        categorias = new ListaDobleEnlazada<Categoria>();
        consejos = new ListaCircular<String>();
        historialAcciones = new PilaEnlazada<String>();
        pilaAuditoria = new PilaArreglo<String>(5);
        pagosPendientes = new ColaEnlazada<Transaccion>();
        notificaciones = new ColaArreglo<String>(5);
        tareasPrioritarias = new ColaPrioridad();
        matrizMensual = new MatrizDispersa(12, 2);
        abbPorId = new ArbolBusquedaTransacciones();
        avlPorMonto = new ArbolAvlTransacciones();
        siguienteId = 1;
        cargarDatosIniciales();
    }

    public void registrarMovimiento(String tipo, String categoria, double monto, int mes, String descripcion) {
        Transaccion transaccion = new Transaccion(siguienteId, tipo, categoria, monto, mes, descripcion);
        siguienteId++;
        transacciones.agregar(transaccion);
        transaccionesEnlazadas.agregarAlFinal(transaccion);
        abbPorId.insertar(transaccion);
        avlPorMonto.insertar(transaccion);
        matrizMensual.agregar(mes - 1, tipo.equals(Transaccion.TIPO_INGRESO) ? 0 : 1, monto);
        historialAcciones.apilar("Se registro: " + transaccion);
        pilaAuditoria.apilar("ALTA #" + transaccion.obtenerId());
        notificaciones.encolar("Nuevo movimiento registrado: #" + transaccion.obtenerId());
    }

    public void agregarCategoria(String nombre, double limite) {
        categorias.agregarAlFinal(new Categoria(nombre, limite));
        historialAcciones.apilar("Se agrego categoria: " + nombre);
    }

    public void encolarGastoPendiente(String categoria, double monto, int mes, String descripcion) {
        Transaccion pendiente = new Transaccion(0, Transaccion.TIPO_GASTO, categoria, monto, mes, descripcion);
        pagosPendientes.encolar(pendiente);
        tareasPrioritarias.encolar(new TareaPrioridad("Pagar " + descripcion + " por S/ " + monto, monto > 200 ? 3 : 1));
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
                avlPorMonto.eliminar(actual);
                matrizMensual.agregar(actual.obtenerMes() - 1, actual.obtenerTipo().equals(Transaccion.TIPO_INGRESO) ? 0 : 1,
                        -actual.obtenerMonto());
                historialAcciones.apilar("Se elimino movimiento #" + id);
                pilaAuditoria.apilar("BAJA #" + id);
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

    public void mostrarDemoConceptosMatriz() {
        MatrizDispersa matriz = new MatrizDispersa(3, 3);
        matriz.agregar(0, 0, 100);
        matriz.agregar(1, 0, 40);
        matriz.agregar(1, 1, 80);
        matriz.agregar(2, 1, 30);
        matriz.agregar(2, 2, 60);
        System.out.println("Matriz cuadrada de ejemplo:");
        matriz.imprimir();
        System.out.println("Triangular inferior: " + matriz.esTriangularInferior());
        System.out.println("Triangular superior: " + matriz.esTriangularSuperior());
        System.out.println("Tridiagonal: " + matriz.esTridiagonal());
        System.out.println("Simetrica: " + matriz.esSimetrica());
        System.out.println("Transpuesta:");
        matriz.transpuesta().imprimir();
    }

    public void mostrarRecorridosArboles() {
        System.out.println("ABB por ID - inorden:");
        abbPorId.inorden();
        System.out.println("ABB por ID - preorden:");
        abbPorId.preorden();
        System.out.println("ABB por ID - postorden:");
        abbPorId.postorden();
        System.out.println("AVL por monto - inorden:");
        avlPorMonto.inorden();
    }

    public void mostrarDemoOperacionesArreglo() {
        ListaArreglo<Transaccion> copiar = transacciones.copiar();
        ListaArreglo<Transaccion> fusionado = transacciones.fusionar(copiar);
        System.out.println("Cantidad original: " + transacciones.size());
        System.out.println("Copia del arreglo: " + copiar.size());
        System.out.println("Fusion original + copia: " + fusionado.size());
        System.out.println("Comparacion por size original/copia: " + transacciones.mismoTamanio(copiar));
    }

    public void mostrarSiguienteConsejo() {
        System.out.println(consejos.obtenerPorTurno(transacciones.size()));
    }

    public void mostrarSiguienteNotificacion() {
        String mensaje = notificaciones.desencolar();
        System.out.println(mensaje == null ? "No hay notificaciones." : mensaje);
    }

    public void mostrarTareaMasPrioritaria() {
        TareaPrioridad tarea = tareasPrioritarias.desencolar();
        System.out.println(tarea == null ? "No hay tareas prioritarias." : tarea);
    }

    public void mostrarUltimaAccion() {
        String last = historialAcciones.desapilar();
        System.out.println(last == null ? "No hay acciones para mostrar." : "Ultima accion: " + last);
    }

    private void cargarDatosIniciales() {
        agregarCategoria("Sueldo", 0);
        agregarCategoria("Comida", 600);
        agregarCategoria("Transporte", 250);
        agregarCategoria("Servicios", 350);
        consejos.agregar("Consejo: separa al menos 10% de tus ingresos para ahorro.");
        consejos.agregar("Consejo: revisa gastos hormiga antes de fin de mes.");
        consejos.agregar("Consejo: paga primero las deudas con mayor interes.");
        registrarMovimiento(Transaccion.TIPO_INGRESO, "Sueldo", 1800, 1, "Pago mensual");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Comida", 230, 1, "Supermercado");
        registrarMovimiento(Transaccion.TIPO_GASTO, "Transporte", 80, 1, "Recarga tarjeta");
        encolarGastoPendiente("Servicios", 210, 1, "Recibo de luz");
    }
}

