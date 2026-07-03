package modelo;

public class Transaccion implements Comparable<Transaccion> {
    public static final String TIPO_INGRESO = "INGRESO";
    public static final String TIPO_GASTO = "GASTO";

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

    public int compareTo(Transaccion otra) {
        if (monto < otra.monto) {
            return -1;
        }
        if (monto > otra.monto) {
            return 1;
        }
        return id - otra.id;
    }

    public String toString() {
        return "#" + id + " | " + tipo + " | " + categoria + " | S/ " + String.format("%.2f", monto)
                + " | mes " + mes + " | " + descripcion;
    }
}

