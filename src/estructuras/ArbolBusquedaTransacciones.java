package estructuras;

import modelo.Transaccion;

public class ArbolBusquedaTransacciones {
    private class Nodo {
        Transaccion valor;
        Nodo izquierda;
        Nodo derecha;

        Nodo(Transaccion valor) {
            this.valor = valor;
        }
    }

    private Nodo raiz;

    public void insertar(Transaccion transaccion) {
        raiz = insertar(raiz, transaccion);
    }

    public Transaccion buscar(int id) {
        Nodo actual = raiz;
        while (actual != null) {
            if (id == actual.valor.obtenerId()) {
                return actual.valor;
            }
            actual = id < actual.valor.obtenerId() ? actual.izquierda : actual.derecha;
        }
        return null;
    }

    public void eliminar(int id) {
        raiz = eliminar(raiz, id);
    }

    public void inorden() {
        inorden(raiz);
    }

    public void preorden() {
        preorden(raiz);
    }

    public void postorden() {
        postorden(raiz);
    }

    private Nodo insertar(Nodo node, Transaccion transaccion) {
        if (node == null) {
            return new Nodo(transaccion);
        }
        if (transaccion.obtenerId() < node.valor.obtenerId()) {
            node.izquierda = insertar(node.izquierda, transaccion);
        } else if (transaccion.obtenerId() > node.valor.obtenerId()) {
            node.derecha = insertar(node.derecha, transaccion);
        }
        return node;
    }

    private Nodo eliminar(Nodo node, int id) {
        if (node == null) {
            return null;
        }
        if (id < node.valor.obtenerId()) {
            node.izquierda = eliminar(node.izquierda, id);
        } else if (id > node.valor.obtenerId()) {
            node.derecha = eliminar(node.derecha, id);
        } else {
            if (node.izquierda == null) {
                return node.derecha;
            }
            if (node.derecha == null) {
                return node.izquierda;
            }
            Nodo sucesor = minimo(node.derecha);
            node.valor = sucesor.valor;
            node.derecha = eliminar(node.derecha, sucesor.valor.obtenerId());
        }
        return node;
    }

    private Nodo minimo(Nodo node) {
        while (node.izquierda != null) {
            node = node.izquierda;
        }
        return node;
    }

    private void inorden(Nodo node) {
        if (node != null) {
            inorden(node.izquierda);
            System.out.println(node.valor);
            inorden(node.derecha);
        }
    }

    private void preorden(Nodo node) {
        if (node != null) {
            System.out.println(node.valor);
            preorden(node.izquierda);
            preorden(node.derecha);
        }
    }

    private void postorden(Nodo node) {
        if (node != null) {
            postorden(node.izquierda);
            postorden(node.derecha);
            System.out.println(node.valor);
        }
    }
}

