package estructuras;

public interface InterfazCola<T> {
    void encolar(T valor);

    T desencolar();

    boolean estaVacia();
}
