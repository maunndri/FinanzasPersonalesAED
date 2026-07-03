package estructuras;

public class ColaArreglo<T> implements InterfazCola<T> {
    private Object[] datos;
    private int frente;
    private int finalCola;
    private int size;

    public ColaArreglo(int capacidad) {
        datos = new Object[Math.max(1, capacidad)];
        frente = 0;
        finalCola = -1;
        size = 0;
    }

    public void encolar(T valor) {
        if (size == datos.length) {
            grow();
        }
        finalCola = (finalCola + 1) % datos.length;
        datos[finalCola] = valor;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T valor = (T) datos[frente];
        datos[frente] = null;
        frente = (frente + 1) % datos.length;
        size--;
        return valor;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    private void grow() {
        Object[] masGrande = new Object[datos.length * 2];
        for (int i = 0; i < size; i++) {
            masGrande[i] = datos[(frente + i) % datos.length];
        }
        datos = masGrande;
        frente = 0;
        finalCola = size - 1;
    }
}

