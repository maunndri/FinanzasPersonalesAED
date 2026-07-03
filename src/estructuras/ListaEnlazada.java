package estructuras;

public class ListaEnlazada<T extends Comparable<T>> {
    private class Nodo {
        T valor;
        Nodo siguiente;

        Nodo(T valor) {
            this.valor = valor;
        }
    }

    private Nodo cabeza;
    private int size;

    public void agregarAlInicio(T valor) {
        Nodo node = new Nodo(valor);
        node.siguiente = cabeza;
        cabeza = node;
        size++;
    }

    public void agregarAlFinal(T valor) {
        Nodo node = new Nodo(valor);
        if (cabeza == null) {
            cabeza = node;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                actual = actual.siguiente;
            }
            actual.siguiente = node;
        }
        size++;
    }

    public boolean eliminar(T valor) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.valor.compareTo(valor) == 0) {
            cabeza = cabeza.siguiente;
            size--;
            return true;
        }
        Nodo actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.valor.compareTo(valor) == 0) {
                actual.siguiente = actual.siguiente.siguiente;
                size--;
                return true;
            }
            actual = actual.siguiente;
        }
        return false;
    }

    public T buscar(T valor) {
        Nodo actual = cabeza;
        while (actual != null) {
            if (actual.valor.compareTo(valor) == 0) {
                return actual.valor;
            }
            actual = actual.siguiente;
        }
        return null;
    }

    public void ordenar() {
        if (cabeza == null || cabeza.siguiente == null) {
            return;
        }
        boolean swapped;
        do {
            swapped = false;
            Nodo actual = cabeza;
            while (actual.siguiente != null) {
                if (actual.valor.compareTo(actual.siguiente.valor) > 0) {
                    T temporal = actual.valor;
                    actual.valor = actual.siguiente.valor;
                    actual.siguiente.valor = temporal;
                    swapped = true;
                }
                actual = actual.siguiente;
            }
        } while (swapped);
    }

    public void recorrer(Visitante<T> visitante) {
        Nodo actual = cabeza;
        while (actual != null) {
            visitante.visitar(actual.valor);
            actual = actual.siguiente;
        }
    }

    public int size() {
        return size;
    }
}

