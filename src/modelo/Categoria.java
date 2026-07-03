package modelo;

public class Categoria {
    private String nombre;
    private double monthlyLimit;

    public Categoria(String nombre, double monthlyLimit) {
        this.nombre = nombre;
        this.monthlyLimit = monthlyLimit;
    }

    public String obtenerNombre() {
        return nombre;
    }

    public double obtenerLimiteMensual() {
        return monthlyLimit;
    }

    public String toString() {
        return nombre + " (limite mensual: S/ " + String.format("%.2f", monthlyLimit) + ")";
    }
}

