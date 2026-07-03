package estructuras;

public interface InterfazPila<T> {
    void apilar(T valor);

    T desapilar();

    T verCima();

    boolean estaVacia();
}
