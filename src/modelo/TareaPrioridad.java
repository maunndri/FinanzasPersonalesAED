package modelo;

public class TareaPrioridad {
    private String descripcion;
    private int priority;

    public TareaPrioridad(String descripcion, int priority) {
        this.descripcion = descripcion;
        this.priority = priority;
    }

    public int obtenerPrioridad() {
        return priority;
    }

    public String toString() {
        return "[prioridad " + priority + "] " + descripcion;
    }
}

