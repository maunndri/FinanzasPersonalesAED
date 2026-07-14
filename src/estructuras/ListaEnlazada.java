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

    // agrega al principio; siempre O(1), no importa cuántos elementos haya
    public void agregarAlInicio(T valor) {
        Nodo node = new Nodo(valor);
        node.siguiente = cabeza;
        cabeza = node;
        if (cola == null) {
            cola = node;
        }
        size++;
    }

    // agrega al final; O(1) gracias al puntero cola, si no habría que recorrer toda la lista primero
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

    // busca por valor (usa compareTo == 0) y desconecta el nodo; caso especial si es la cabeza
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

    // recorre desde la cabeza comparando uno por uno; O(n), no hay atajos posibles en una lista simple
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

    // punto de entrada del merge sort; al reordenar los nodos, el puntero cola queda desactualizado
    // y hay que recalcularlo
    public void ordenar() {
        cabeza = ordenarPorMezcla(cabeza);
        actualizarCola();
    }

    // patrón Visitor: recorre siguiendo los punteros .siguiente, sin exponer los nodos hacia afuera
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

    // merge sort recursivo: caso base (0 o 1 elemento, ya está ordenado), si no, parte a la mitad,
    // ordena cada mitad por separado y fusiona los resultados
    private Nodo ordenarPorMezcla(Nodo inicio) {
        if (inicio == null || inicio.siguiente == null) {
            return inicio;
        }
        Nodo mitad = obtenerMitad(inicio);
        Nodo derecha = mitad.siguiente;
        mitad.siguiente = null;
        return fusionarOrdenado(ordenarPorMezcla(inicio), ordenarPorMezcla(derecha));
    }

    // truco "lento/rápido": rapido avanza el doble que lento, así cuando rapido llega al final,
    // lento queda justo en la mitad, sin necesitar contar el tamaño antes
    private Nodo obtenerMitad(Nodo inicio) {
        Nodo lento = inicio;
        Nodo rapido = inicio.siguiente;
        while (rapido != null && rapido.siguiente != null) {
            lento = lento.siguiente;
            rapido = rapido.siguiente.siguiente;
        }
        return lento;
    }

    // fusiona dos listas ya ordenadas en una sola: en cada paso compara las dos cabezas
    // y "engancha" la más chica al resultado, avanzando solo en esa lista
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

    // después de ordenar, los nodos quedaron reconectados en otro orden, así que hay que
    // volver a caminar hasta el final para saber cuál es el nuevo último nodo
    private void actualizarCola() {
        cola = cabeza;
        while (cola != null && cola.siguiente != null) {
            cola = cola.siguiente;
        }
    }
}