package estructuras;

public class ListaDobleEnlazada<T> {
    private class Nodo {
        T valor;
        Nodo anterior;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo cabeza;
    private Nodo ultimo;
    private int size;

    public void agregarAlFinal(T valor) {
        Nodo node = new Nodo(valor);
        if (ultimo == null) {
            cabeza = node;
            ultimo = node;
        } else {
            ultimo.siguiente = node;
            node.anterior = ultimo;
            ultimo = node;
        }
        size++;
    }

    public boolean eliminarPorTexto(String text) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.valor.toString().equalsIgnoreCase(text)) {
                unlink(actual);
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public T buscarPorTexto(String text) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.valor.toString().equalsIgnoreCase(text)) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public void recorrerAdelante(Visitante<T> visitante) {
        Nodo actual = cabeza;
        while (actual != null) {
            visitante.visitar(actual.valor);
            actual = actual.siguiente;
        }
    }

    public void recorrerAtras(Visitante<T> visitante) {
        Nodo actual = ultimo;
        while (actual != null) {
            visitante.visitar(actual.valor);
            actual = actual.anterior;
        }
    }

    public int size() {
        return size;
    }

    private void unlink(Nodo node) {
        if (node.anterior == null) {
            cabeza = node.siguiente;
        } else {
            node.anterior.siguiente = node.siguiente;
        }
        if (node.siguiente == null) {
            ultimo = node.anterior;
        } else {
            node.siguiente.anterior = node.anterior;
        }
        size--;
    }
}

