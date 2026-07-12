package modelo;

public class Categoria {
    private String nombre;
    private double monthlyLimit;
    private String tipo;

    public Categoria(String nombre, double monthlyLimit) {
        this(nombre, monthlyLimit, "GASTO");
    }

    public Categoria(String nombre, double monthlyLimit, String tipo) {
        this.nombre = nombre;
        this.monthlyLimit = monthlyLimit;
        this.tipo = tipo;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public double obtenerLimiteMensual() {
        return monthlyLimit;
    }

    public String obtenerTipo() {
        return tipo;
    }

    public String toString() {
        return nombre + " | " + tipo + " (limite mensual: S/ " + String.format("%.2f", monthlyLimit) + ")";
    }
}

