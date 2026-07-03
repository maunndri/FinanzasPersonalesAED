package estructuras;

public class ColaEnlazada<T> implements InterfazCola<T> {
    private class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo frente;
    private Nodo finalCola;

    public void encolar(T valor) {
        Nodo node = new Nodo(valor);
        if (finalCola == null) {
            frente = node;
            finalCola = node;
        } else {
            finalCola.siguiente = node;
            finalCola = node;
        }
    }

    public T desencolar() {
        if (frente == null) {
            return null;
        }
        T valor = frente.valor;
        frente = frente.siguiente;
        if (frente == null) {
            finalCola = null;
        }
        return valor;
    }

    public boolean estaVacia() {
        return frente == null;
    }
}

