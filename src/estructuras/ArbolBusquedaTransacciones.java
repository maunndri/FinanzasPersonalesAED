package estructuras;

import model.Transaccion;

public class ArbolBusquedaTransacciones {
    private class Nodo {
        Transaccion valor;
        Nodo izquierda;
        Nodo derecha;
        int altura;

        Nodo(Transaccion valor) {
            this.valor = valor;
            this.altura = 1;
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

    public void recorrerInorden(Visitante<Transaccion> visitante) {
        recorrerInorden(raiz, visitante);
    }

    public void preorden() {
        preorden(raiz);
    }

    public void recorrerPreorden(Visitante<Transaccion> visitante) {
        recorrerPreorden(raiz, visitante);
    }

    public void postorden() {
        postorden(raiz);
    }

    public void recorrerPostorden(Visitante<Transaccion> visitante) {
        recorrerPostorden(raiz, visitante);
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
        return equilibrar(node);
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
        return equilibrar(node);
    }

    private Nodo minimo(Nodo node) {
        while (node.izquierda != null) {
            node = node.izquierda;
        }
        return node;
    }

    private Nodo equilibrar(Nodo node) {
        actualizarAltura(node);
        int balance = obtenerBalance(node);
        if (balance > 1) {
            if (obtenerBalance(node.izquierda) < 0) {
                node.izquierda = rotarIzquierda(node.izquierda);
            }
            return rotarDerecha(node);
        }
        if (balance < -1) {
            if (obtenerBalance(node.derecha) > 0) {
                node.derecha = rotarDerecha(node.derecha);
            }
            return rotarIzquierda(node);
        }
        return node;
    }

    private Nodo rotarDerecha(Nodo node) {
        Nodo nuevaRaiz = node.izquierda;
        Nodo subarbol = nuevaRaiz.derecha;
        nuevaRaiz.derecha = node;
        node.izquierda = subarbol;
        actualizarAltura(node);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    private Nodo rotarIzquierda(Nodo node) {
        Nodo nuevaRaiz = node.derecha;
        Nodo subarbol = nuevaRaiz.izquierda;
        nuevaRaiz.izquierda = node;
        node.derecha = subarbol;
        actualizarAltura(node);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    private int obtenerAltura(Nodo node) {
        return node == null ? 0 : node.altura;
    }

    private int obtenerBalance(Nodo node) {
        return node == null ? 0 : obtenerAltura(node.izquierda) - obtenerAltura(node.derecha);
    }

    private void actualizarAltura(Nodo node) {
        node.altura = 1 + Math.max(obtenerAltura(node.izquierda), obtenerAltura(node.derecha));
    }

    private void inorden(Nodo node) {
        if (node != null) {
            inorden(node.izquierda);
            System.out.println(node.valor);
            inorden(node.derecha);
        }
    }

    private void recorrerInorden(Nodo node, Visitante<Transaccion> visitante) {
        if (node != null) {
            recorrerInorden(node.izquierda, visitante);
            visitante.visitar(node.valor);
            recorrerInorden(node.derecha, visitante);
        }
    }

    private void preorden(Nodo node) {
        if (node != null) {
            System.out.println(node.valor);
            preorden(node.izquierda);
            preorden(node.derecha);
        }
    }

    private void recorrerPreorden(Nodo node, Visitante<Transaccion> visitante) {
        if (node != null) {
            visitante.visitar(node.valor);
            recorrerPreorden(node.izquierda, visitante);
            recorrerPreorden(node.derecha, visitante);
        }
    }

    private void postorden(Nodo node) {
        if (node != null) {
            postorden(node.izquierda);
            postorden(node.derecha);
            System.out.println(node.valor);
        }
    }

    private void recorrerPostorden(Nodo node, Visitante<Transaccion> visitante) {
        if (node != null) {
            recorrerPostorden(node.izquierda, visitante);
            recorrerPostorden(node.derecha, visitante);
            visitante.visitar(node.valor);
        }
    }
}

