package estructuras;

import modelo.TareaPrioridad;

public class ColaPrioridad implements InterfazCola<TareaPrioridad> {
    private ListaArreglo<TareaPrioridad> tareas;

    public ColaPrioridad() {
        tareas = new ListaArreglo<TareaPrioridad>();
    }

    public void encolar(TareaPrioridad tarea) {
        int indice = 0;
        while (indice < tareas.size() && tareas.obtener(indice).obtenerPrioridad() >= tarea.obtenerPrioridad()) {
            indice++;
        }
        tareas.insertar(indice, tarea);
    }

    public TareaPrioridad desencolar() {
        if (tareas.estaVacia()) {
            return null;
        }
        return tareas.eliminarEn(0);
    }

    public boolean estaVacia() {
        return tareas.estaVacia();
    }

    public void mostrarTodo() {
        tareas.recorrer(new Visitante<TareaPrioridad>() {
            public void visitar(TareaPrioridad valor) {
                System.out.println(valor);
            }
        });
    }
}

