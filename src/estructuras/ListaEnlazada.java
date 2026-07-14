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
    private Nodo cola;
    private int size;

    public void agregarAlInicio(T valor) {
        Nodo node = new Nodo(valor);
        node.siguiente = cabeza;
        cabeza = node;
        if (cola == null) {
            cola = node;
        }
        size++;
    }

    public void agregarAlFinal(T valor) {
        Nodo node = new Nodo(valor);
        if (cola == null) {
            cabeza = node;
        } else {
            cola.siguiente = node;
        }
        cola = node;
        size++;
    }

    public boolean eliminar(T valor) {
        if (cabeza == null) {
            return false;
        }
        if (cabeza.valor.compareTo(valor) == 0) {
            cabeza = cabeza.siguiente;
            if (cabeza == null) {
                cola = null;
            }
            size--;
            return true;
        }
        Nodo actual = cabeza;
        while (actual.siguiente != null) {
            if (actual.siguiente.valor.compareTo(valor) == 0) {
                actual.siguiente = actual.siguiente.siguiente;
                if (actual.siguiente == null) {
                    cola = actual;
                }
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
        cabeza = ordenarPorMezcla(cabeza);
        actualizarCola();
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

    private Nodo ordenarPorMezcla(Nodo inicio) {
        if (inicio == null || inicio.siguiente == null) {
            return inicio;
        }
        Nodo mitad = obtenerMitad(inicio);
        Nodo derecha = mitad.siguiente;
        mitad.siguiente = null;
        return fusionarOrdenado(ordenarPorMezcla(inicio), ordenarPorMezcla(derecha));
    }

    private Nodo obtenerMitad(Nodo inicio) {
        Nodo lento = inicio;
        Nodo rapido = inicio.siguiente;
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento;
    }

    private Nodo fusionarOrdenado(Nodo izquierda, Nodo derecha) {
        Nodo resultado;
        if (izquierda.valor.compareTo(derecha.valor) <= 0) {
            resultado = izquierda;
            izquierda = izquierda.siguiente;
        } else {
            resultado = derecha;
            derecha = derecha.siguiente;
        }
        Nodo actual = resultado;
        while (izquierda != null && derecha != null) {
            if (izquierda.valor.compareTo(derecha.valor) <= 0) {
                actual.siguiente = izquierda;
                izquierda = izquierda.siguiente;
            } else {
                actual.siguiente = derecha;
                derecha = derecha.siguiente;
            }
            actual = actual.siguiente;
        }
        actual.siguiente = izquierda != null ? izquierda : derecha;
        return resultado;
    }

    private void actualizarCola() {
        cola = cabeza;
        while (cola != null && cola.siguiente != null) {
            cola = cola.siguiente;
        }
    }
}

