package estructuras;

// contrato que debe cumplir cualquier pila (LIFO); PilaEnlazada lo implementa,
// así el resto del código puede usar "una pila" sin saber si por dentro es de nodos o de array
public interface InterfazPila<T> {
    void apilar(T valor);

    T desapilar();

    T verCima();

    boolean estaVacia();
}