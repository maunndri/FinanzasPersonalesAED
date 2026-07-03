package estructuras;

public class PilaArreglo<T> implements InterfazPila<T> {
    private Object[] datos;
    private int cima;

    public PilaArreglo(int capacidad) {
        datos = new Object[Math.max(1, capacidad)];
        cima = -1;
    }

    public void apilar(T valor) {
        if (cima == datos.length - 1) {
            grow();
        }
        cima++;
        datos[cima] = valor;
    }

    @SuppressWarnings("unchecked")
    public T desapilar() {
        if (estaVacia()) {
            return null;
        }
        T valor = (T) datos[cima];
        datos[cima] = null;
        cima--;
        return valor;
    }

    @SuppressWarnings("unchecked")
    public T verCima() {
        return estaVacia() ? null : (T) datos[cima];
    }

    public boolean estaVacia() {
        return cima == -1;
    }

    private void grow() {
        Object[] masGrande = new Object[datos.length * 2];
        for (int i = 0; i < datos.length; i++) {
            masGrande[i] = datos[i];
        }
        datos = masGrande;
    }
}

