package estructuras;

public class PilaEnlazada<T> implements InterfazPila<T> {
    private class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo cima;

    // LIFO: el nuevo nodo pasa a ser la cima, apuntando al que era la cima anterior; siempre O(1)
    public void apilar(T valor) {
        Nodo node = new Nodo(valor);
        node.siguiente = cima;
        cima = node;
    }

    // saca y devuelve la cima, moviendo el puntero al siguiente; también O(1)
    public T desapilar() {
        if (cima == null) {
            return null;
        }
        T valor = cima.valor;
        cima = cima.siguiente;
        return valor;
    }

    public T verCima() {
        return cima == null ? null : cima.valor;
    }

    public boolean estaVacia() {
        return cima == null;
    }
}