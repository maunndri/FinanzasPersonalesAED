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

    public void apilar(T valor) {
        Nodo node = new Nodo(valor);
        node.siguiente = cima;
        cima = node;
    }

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

