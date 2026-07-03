package estructuras;

public class ListaCircular<T> {
    private class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo ultimo;
    private int size;

    public void agregar(T valor) {
        Nodo node = new Nodo(valor);
        if (ultimo == null) {
            ultimo = node;
            ultimo.siguiente = ultimo;
        } else {
            node.siguiente = ultimo.siguiente;
            ultimo.siguiente = node;
            ultimo = node;
        }
        size++;
    }

    public T obtenerPorTurno(int turn) {
        if (ultimo == null) {
            return null;
        }
        Nodo actual = ultimo.siguiente;
        for (int i = 0; i < turn % size; i++) {
            actual = actual.siguiente;
        }
        return actual.valor;
    }

    public void recorrerUnaVez(Visitante<T> visitante) {
        if (ultimo == null) {
            return;
        }
        Nodo actual = ultimo.siguiente;
        for (int i = 0; i < size; i++) {
            visitante.visitar(actual.valor);
            actual = actual.siguiente;
        }
    }
}

