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

    // FIFO: se agrega siempre por el final, actualizando el puntero finalCola; O(1)
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

    // se saca siempre por el frente (a diferencia de la pila, que agrega y saca por el mismo lado)
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