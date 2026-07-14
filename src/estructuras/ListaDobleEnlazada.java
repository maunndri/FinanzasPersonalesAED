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

    // agrega al final; conecta el nuevo nodo con .anterior también, no solo .siguiente como en la simple
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

    // busca por texto (usando toString) y desconecta el nodo delegando en unlink()
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

    // patrón Visitor, de cabeza a último
    public void recorrerAdelante(Visitante<T> visitante) {
        Nodo actual = cabeza;
        while (actual != null) {
            visitante.visitar(actual.valor);
            actual = actual.siguiente;
        }
    }

    // igual que recorrerAdelante pero al revés, gracias al puntero .anterior que no existe en la lista simple
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

    // desconecta un nodo del medio en O(1): reconecta a su anterior y siguiente directamente entre sí,
    // sin tener que recorrer la lista para encontrarlos (ya los tenemos guardados en el propio nodo)
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