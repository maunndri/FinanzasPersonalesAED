
import java.util.Random;

import model.Transaccion;
import servicio.GestorFinanzas;

public class pruebaCarga {

    public static void main(String[] args) {
        GestorFinanzas gestor = new GestorFinanzas();
        Random random = new Random();

        String[] categoriasIngreso = {
                "Sueldo", "Bonos", "Freelance",
                "Ventas", "Intereses", "Otros ingresos"
        };

        String[] categoriasGasto = {
                "Comida", "Transporte", "Servicios", "Vivienda",
                "Salud", "Educacion", "Entretenimiento", "Ropa",
                "Deudas", "Cuidado personal"
        };

        long inicio = System.currentTimeMillis();

        for (int i = 1; i <= 10000; i++) {
            boolean esIngreso = random.nextBoolean();

            String tipo = esIngreso
                    ? Transaccion.TIPO_INGRESO
                    : Transaccion.TIPO_GASTO;

            String categoria;
            double monto;

            if (esIngreso) {
                categoria = categoriasIngreso[random.nextInt(categoriasIngreso.length)];
                monto = 100 + random.nextInt(4901); // S/ 100.00 a S/ 5000.00
            } else {
                categoria = categoriasGasto[random.nextInt(categoriasGasto.length)];
                monto = 10 + random.nextInt(991); // S/ 10.00 a S/ 1000.00
            }

            int mes = 1 + random.nextInt(12);

            gestor.registrarMovimiento(
                    tipo,
                    categoria,
                    monto,
                    mes,
                    "Movimiento aleatorio #" + i
            );
        }

        long fin = System.currentTimeMillis();

        final int[] cantidad = {0};
        gestor.recorrerMovimientos(transaccion -> cantidad[0]++);

        System.out.println("Carga terminada.");
        System.out.println("Total de transacciones: " + cantidad[0]);
        System.out.println("Saldo final: S/ " + gestor.obtenerSaldoActual());
        System.out.println("Tiempo: " + (fin - inicio) + " ms");

        // La última insertada debe tener ID 10027:
        System.out.println("Última transacción: " + gestor.buscarPorId(10027));
    }
}
    

