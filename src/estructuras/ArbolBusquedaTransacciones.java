package estructuras;

import model.Transaccion;

public class ArbolBusquedaTransacciones {
    private class Nodo {
        Transaccion valor;
        Nodo izquierda;
        Nodo derecha;
        int altura; // clave del balanceo AVL: sin esto, sería un BST común

        Nodo(Transaccion valor) {
            this.valor = valor;
            this.altura = 1; // un nodo nuevo siempre nace como hoja, altura 1
        }
    }

    private Nodo raiz;

    public void insertar(Transaccion transaccion) {
        raiz = insertar(raiz, transaccion);
    }

    // busca por id de forma iterativa (no recursiva); baja a la izquierda o derecha según comparación
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

    // los siguientes 3 pares (inorden/preorden/postorden) recorren el árbol en distinto orden de visita;
    // cada uno tiene una versión que imprime directo y otra con Visitante para reutilizar el resultado
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

    // baja recursivamente por id (menor a la izquierda, mayor a la derecha) hasta un hueco,
    // y al volver por cada nivel, re-equilibra el árbol
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

    // busca el nodo por id; al encontrarlo, hay 3 casos: sin hijo izquierdo, sin hijo derecho,
    // o con ambos (ahí se copia el sucesor -el mínimo del subárbol derecho- y se elimina de su lugar original)
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

    // el mínimo de un subárbol siempre está bajando todo a la izquierda
    private Nodo minimo(Nodo node) {
        while (node.izquierda != null) {
            node = node.izquierda;
        }
        return node;
    }

    // el corazón del AVL: si un lado quedó más de 1 nivel más alto que el otro, rota para enderezar.
    // el chequeo interno (ej: obtenerBalance(node.izquierda) < 0) detecta el caso "doble" -cuando el hijo
    // pesado está inclinado hacia el lado contrario- y ahí hace 2 rotaciones en vez de 1
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

    // "gira" el nodo pesado hacia abajo: el hijo izquierdo sube y toma su lugar. El orden de
    // actualizarAltura importa: primero el que bajó (depende de sus hijos actuales), después el que subió
    private Nodo rotarDerecha(Nodo node) {
        Nodo nuevaRaiz = node.izquierda;
        Nodo subarbol = nuevaRaiz.derecha;
        nuevaRaiz.derecha = node;
        node.izquierda = subarbol;
        actualizarAltura(node);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    // espejo exacto de rotarDerecha, para el lado derecho
    private Nodo rotarIzquierda(Nodo node) {
        Nodo nuevaRaiz = node.derecha;
        Nodo subarbol = nuevaRaiz.izquierda;
        nuevaRaiz.izquierda = node;
        node.derecha = subarbol;
        actualizarAltura(node);
        actualizarAltura(nuevaRaiz);
        return nuevaRaiz;
    }

    // altura 0 para null, para no tener que chequear nulos en cada cálculo de balance
    private int obtenerAltura(Nodo node) {
        return node == null ? 0 : node.altura;
    }

    // diferencia entre alturas de los dos lados; si pasa de 1 o -1, el árbol está desbalanceado
    private int obtenerBalance(Nodo node) {
        return node == null ? 0 : obtenerAltura(node.izquierda) - obtenerAltura(node.derecha);
    }

    // la altura de un nodo es 1 + la del hijo más alto de los dos
    private void actualizarAltura(Nodo node) {
        node.altura = 1 + Math.max(obtenerAltura(node.izquierda), obtenerAltura(node.derecha));
    }

    // visita izquierda → nodo → derecha; como se insertó por id, esto da las transacciones
    // ordenadas por id de menor a mayor, sin necesidad de ordenar nada aparte
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

    // visita nodo → izquierda → derecha (empieza por la raíz); los 2 recorridos que siguen
    // (preorden, postorden) son la misma idea que inorden, solo cambia en qué momento se visita el nodo
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
