package estructuras;

public class ListaArreglo<T> {
    private Object[] datos;
    private int size;

    public ListaArreglo() {
        this(10);
    }

    public ListaArreglo(int capacidad) {
        datos = new Object[Math.max(1, capacidad)];
        size = 0;
    }

    public void agregar(T valor) {
        asegurarCapacidad();
        datos[size] = valor;
        size++;
    }

    public void insertar(int indice, T valor) {
        validarIndiceInsercion(indice);
        asegurarCapacidad();
        for (int i = size; i > indice; i--) {
            datos[i] = datos[i - 1];
        }
        datos[indice] = valor;
        size++;
    }

    public void actualizar(int indice, T valor) {
        validarIndice(indice);
        datos[indice] = valor;
    }

    public T eliminarEn(int indice) {
        validarIndice(indice);
        T eliminado = obtener(indice);
        for (int i = indice; i < size - 1; i++) {
            datos[i] = datos[i + 1];
        }
        datos[size - 1] = null;
        size--;
        return eliminado;
    }

    @SuppressWarnings("unchecked")
    public T obtener(int indice) {
        validarIndice(indice);
        return (T) datos[indice];
    }

    public int size() {
        return size;
    }

    public boolean estaVacia() {
        return size == 0;
    }

    public ListaArreglo<T> copiar() {
        ListaArreglo<T> copia = new ListaArreglo<T>(datos.length);
        for (int i = 0; i < size; i++) {
            copia.agregar(obtener(i));
        }
        return copia;
    }

    public ListaArreglo<T> fusionar(ListaArreglo<T> otra) {
        ListaArreglo<T> fusionado = new ListaArreglo<T>(size + otra.size());
        for (int i = 0; i < size; i++) {
            fusionado.agregar(obtener(i));
        }
        for (int i = 0; i < otra.size(); i++) {
            fusionado.agregar(otra.obtener(i));
        }
        return fusionado;
    }

    public boolean mismoTamanio(ListaArreglo<T> otra) {
        return size == otra.size();
    }

    public void recorrer(Visitante<T> visitante) {
        for (int i = 0; i < size; i++) {
            visitante.visitar(obtener(i));
        }
    }

    private void asegurarCapacidad() {
        if (size < datos.length) {
            return;
        }
        Object[] masGrande = new Object[datos.length * 2];
        for (int i = 0; i < datos.length; i++) {
            masGrande[i] = datos[i];
        }
        datos = masGrande;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= size) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
    }

    private void validarIndiceInsercion(int indice) {
        if (indice < 0 || indice > size) {
            throw new IndexOutOfBoundsException("Indice de insercion fuera de rango: " + indice);
        }
    }
}

