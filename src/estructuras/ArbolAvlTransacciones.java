package estructuras;

import model.Transaccion;

public class ArbolAvlTransacciones {
    private class Nodo {
        Transaccion valor;
        Nodo izquierda;
        Nodo derecha;
        int altura;

        Nodo(Transaccion valor) {
            this.valor = valor;
            altura = 1;
        }
    }

    private Nodo raiz;

    public void insertar(Transaccion transaccion) {
        raiz = insertar(raiz, transaccion);
    }

    public void eliminar(Transaccion transaccion) {
        raiz = eliminar(raiz, transaccion);
    }

    public void inorden() {
        inorden(raiz);
    }

    private Nodo insertar(Nodo node, Transaccion transaccion) {
        if (node == null) {
            return new Nodo(transaccion);
        }
        if (transaccion.compareTo(node.valor) < 0) {
            node.izquierda = insertar(node.izquierda, transaccion);
        } else if (transaccion.compareTo(node.valor) > 0) {
            node.derecha = insertar(node.derecha, transaccion);
        } else {
            return node;
        }
        actualizarAltura(node);
        return equilibrar(node);
    }

    private Nodo eliminar(Nodo node, Transaccion transaccion) {
        if (node == null) {
            return null;
        }
        if (transaccion.compareTo(node.valor) < 0) {
            node.izquierda = eliminar(node.izquierda, transaccion);
        } else if (transaccion.compareTo(node.valor) > 0) {
            node.derecha = eliminar(node.derecha, transaccion);
        } else {
            if (node.izquierda == null || node.derecha == null) {
                node = node.izquierda != null ? node.izquierda : node.derecha;
            } else {
                Nodo sucesor = minimo(node.derecha);
                node.valor = sucesor.valor;
                node.derecha = eliminar(node.derecha, sucesor.valor);
            }
        }
        if (node == null) {
            return null;
        }
        actualizarAltura(node);
        return equilibrar(node);
    }

    private Nodo equilibrar(Nodo node) {
        int equilibrar = obtenerEquilibrio(node);
        if (equilibrar > 1) {
            if (obtenerEquilibrio(node.izquierda) < 0) {
                node.izquierda = rotarIzquierda(node.izquierda);
            }
            return rotarDerecha(node);
        }
        if (equilibrar < -1) {
            if (obtenerEquilibrio(node.derecha) > 0) {
                node.derecha = rotarDerecha(node.derecha);
            }
            return rotarIzquierda(node);
        }
        return node;
    }

    private Nodo rotarDerecha(Nodo y) {
        Nodo x = y.izquierda;
        Nodo temporal = x.derecha;
        x.derecha = y;
        y.izquierda = temporal;
        actualizarAltura(y);
        actualizarAltura(x);
        return x;
    }

    private Nodo rotarIzquierda(Nodo x) {
        Nodo y = x.derecha;
        Nodo temporal = y.izquierda;
        y.izquierda = x;
        x.derecha = temporal;
        actualizarAltura(x);
        actualizarAltura(y);
        return y;
    }

    private void actualizarAltura(Nodo node) {
        node.altura = 1 + Math.max(altura(node.izquierda), altura(node.derecha));
    }

    private int altura(Nodo node) {
        return node == null ? 0 : node.altura;
    }

    private int obtenerEquilibrio(Nodo node) {
        return node == null ? 0 : altura(node.izquierda) - altura(node.derecha);
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
}

