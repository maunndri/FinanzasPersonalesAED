package estructuras;

// mismo principio que InterfazPila, pero para una cola (FIFO); ColaEnlazada lo implementa
public interface InterfazCola<T> {
    void encolar(T valor);

    T desencolar();

    boolean estaVacia();
}