import modelo.Transaccion;
import servicio.GestorFinanzas;

public class Principal {
    public static void main(String[] args) {
        java.util.Scanner lector = new java.util.Scanner(System.in);
        GestorFinanzas gestor = new GestorFinanzas();
        int opcion;
        
        do {
            imprimirMenu();
            opcion = leerEntero(lector, "Opcion: ");
            switch (opcion) {
                case 1:
                    registrarMovimientoDesdeConsola(lector, gestor);
                    break;
                case 2:
                    gestor.listarMovimientosArreglo();
                    break;
                case 3:
                    int id = leerEntero(lector, "ID a buscar: ");
                    Transaccion encontrado = gestor.buscarPorId(id);
                    System.out.println(encontrado == null ? "No encontrado." : encontrado);
                    break;
                case 4:
                    int idEliminar = leerEntero(lector, "ID a eliminar: ");
                    System.out.println(gestor.eliminarMovimientoPorId(idEliminar) ? "Eliminado." : "No encontrado.");
                    break;
                case 5:
                    gestor.listarMovimientosOrdenadosPorMonto();
                    break;
                case 6:
                    gestor.mostrarMatrizMensual();
                    break;
                case 7:
                    gestor.mostrarRecorridosArboles();
                    break;
                case 8:
                    registrarPagoPendienteDesdeConsola(lector, gestor);
                    break;
                case 9:
                    gestor.procesarGastoPendiente();
                    break;
                case 10:
                    gestor.mostrarUltimaAccion();
                    break;
                case 11:
                    gestor.listarCategoriasAdelante();
                    System.out.println("Recorrido inverso:");
                    gestor.listarCategoriasAtras();
                    break;
                case 12:
                    gestor.mostrarDemoOperacionesArreglo();
                    break;
                case 0:
                    System.out.println("Fin del programa.");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while (opcion != 0);

        lector.close();
    }

    private static void imprimirMenu() {
        System.out.println();
        System.out.println("=== Sistema de Gestion de Finanzas Personales ===");
        System.out.println("1. Registrar ingreso o gasto");
        System.out.println("2. Listar movimientos (ListaArreglo)");
        System.out.println("3. Buscar movimiento por ID (ABB)");
        System.out.println("4. Eliminar movimiento por ID");
        System.out.println("5. Listar movimientos ordenados por monto (lista simple)");
        System.out.println("6. Ver matriz mensual ingresos/gastos");
        System.out.println("7. Ver recorridos de arboles");
        System.out.println("8. Registrar pago pendiente (cola dinamica)");
        System.out.println("9. Procesar pago pendiente (cola dinamica)");
        System.out.println("10. Ver ultima accion (pila dinamica)");
        System.out.println("11. Ver categorias (lista doble)");
        System.out.println("12. Demo de copia, comparacion y fusion de arreglos");
        System.out.println("0. Salir");
    }

    private static void registrarMovimientoDesdeConsola(java.util.Scanner lector, GestorFinanzas gestor) {
        int opcionTipo = leerEntero(lector, "1 ingreso, 2 gasto: ");
        String tipo = opcionTipo == 1 ? Transaccion.TIPO_INGRESO : Transaccion.TIPO_GASTO;
        System.out.print("Categoria: ");
        String categoria = lector.nextLine();
        double monto = leerDecimal(lector, "Monto: ");
        int mes = leerEntero(lector, "Mes (1-12): ");
        System.out.print("Descripcion: ");
        String descripcion = lector.nextLine();
        try {
            gestor.registrarMovimiento(tipo, categoria, monto, mes, descripcion);
            System.out.println("Movimiento registrado.");
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
    }

    private static void registrarPagoPendienteDesdeConsola(java.util.Scanner lector, GestorFinanzas gestor) {
        System.out.print("Categoria: ");
        String categoria = lector.nextLine();
        double monto = leerDecimal(lector, "Monto: ");
        int mes = leerEntero(lector, "Mes (1-12): ");
        System.out.print("Descripcion: ");
        String descripcion = lector.nextLine();
        try {
            gestor.encolarGastoPendiente(categoria, monto, mes, descripcion);
            System.out.println("Pago pendiente registrado.");
        } catch (IllegalArgumentException error) {
            System.out.println(error.getMessage());
        }
    }

    private static int leerEntero(java.util.Scanner lector, String mensaje) {
        System.out.print(mensaje);
        while (!lector.hasNextInt()) {
            System.out.print("Ingrese un numero entero: ");
            lector.next();
        }
        int valor = lector.nextInt();
        lector.nextLine();
        return valor;
    }

    private static double leerDecimal(java.util.Scanner lector, String mensaje) {
        System.out.print(mensaje);
        while (!lector.hasNextDouble()) {
            System.out.print("Ingrese un numero decimal: ");
            lector.next();
        }
        double valor = lector.nextDouble();
        lector.nextLine();
        return valor;
    }
}
