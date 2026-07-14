package model;

public class Transaccion implements Comparable<Transaccion> {
    // constantes: un solo lugar de verdad para los strings de tipo, evita typos sueltos en el código
    public static final String TIPO_INGRESO = "INGRESO";
    public static final String TIPO_GASTO = "GASTO";

    // usado por toString() para convertir el mes numérico (1-12) en texto
    private static final String[] NOMBRES_MES = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    private int id;
    private String tipo;
    private String categoria;
    private double monto;
    private int mes;
    private String descripcion;

    public Transaccion(int id, String tipo, String categoria, double monto, int mes, String descripcion) {
        this.id = id;
        this.tipo = tipo;
        this.categoria = categoria;
        this.monto = monto;
        this.mes = mes;
        this.descripcion = descripcion;
    }

    public int obtenerId() {
        return id;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public String obtenerCategoria() {
        return categoria;
    }

    public double obtenerMonto() {
        return monto;
    }

    public int obtenerMes() {
        return mes;
    }

    public String obtenerDescripcion() {
        return descripcion;
    }

    // compara por monto (usado por ListaEnlazada.ordenar() para el merge sort); si empatan, desempata por id
    public int compareTo(Transaccion otra) {
        if (monto < otra.monto) {
            return -1;
        }
        if (monto > otra.monto) {
            return 1;
        }
        return id - otra.id;
    }

    // formato legible para consola/interfaz, ej: "#3 | GASTO | Comida | S/ 230.00 | Enero | Supermercado"
    public String toString() {
        return "#" + id + " | " + tipo + " | " + categoria + " | S/ " + String.format("%.2f", monto)
                + " | " + NOMBRES_MES[mes - 1] + " | " + descripcion;
    }
}