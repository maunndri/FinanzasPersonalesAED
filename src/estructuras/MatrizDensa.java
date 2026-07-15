package estructuras;

public class MatrizDensa {
    private double[][] values;

    public MatrizDensa(int filas, int columnas) {
        values = new double[filas][columnas];
    }

    // suma (no reemplaza) el valor en esa celda; así es como se acumula el total mensual de a poco
    public void agregar(int row, int column, double valor) {
        values[row][column] += valor;
    }

    public double obtener(int row, int column) {
        return values[row][column];
    }

    // crea una matriz nueva con filas y columnas invertidas: la celda [i][j] pasa a ser [j][i]
    public MatrizDensa transpuesta() {
        MatrizDensa result = new MatrizDensa(values[0].length, values.length);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                result.values[j][i] = values[i][j];
            }
        }
        return result;
    }

    // solo aplica si es cuadrada; compara cada celda contra su "espejo" [j][i]
    public boolean esSimetrica() {
        if (values.length != values[0].length) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (values[i][j] != values[j][i]) {
                    return false;
                }
            }
        }
        return true;
    }

    // los 3 métodos que siguen (triangular inferior/superior, tridiagonal) son variaciones del mismo
    // patrón: recorren la matriz revisando que ciertas celdas sean 0 según su posición respecto a la diagonal
    public boolean esTriangularInferior() {
        if (values.length != values[0].length) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = i + 1; j < values[i].length; j++) {
                if (values[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esTriangularSuperior() {
        if (values.length != values[0].length) {
            return false;
        }
        for (int i = 1; i < values.length; i++) {
            for (int j = 0; j < i; j++) {
                if (values[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean esTridiagonal() {
        if (values.length != values[0].length) {
            return false;
        }
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                if (Math.abs(i - j) > 1 && values[i][j] != 0) {
                    return false;
                }
            }
        }
        return true;
    }

    // imprime la matriz alineada en columnas de ancho fijo, para que se vea como una tabla en consola
    public void imprimir() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                System.out.print(String.format("%10.2f", values[i][j]));
            }
            System.out.println();
        }
    }
}