package estructuras;

public class MatrizDispersa {
    private double[][] values;

    public MatrizDispersa(int filas, int columnas) {
        values = new double[filas][columnas];
    }

    public void agregar(int row, int column, double valor) {
        values[row][column] += valor;
    }

    public double obtener(int row, int column) {
        return values[row][column];
    }

    public MatrizDispersa transpuesta() {
        MatrizDispersa result = new MatrizDispersa(values[0].length, values.length);
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                result.values[j][i] = values[i][j];
            }
        }
        return result;
    }

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

    public void imprimir() {
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                System.out.print(String.format("%10.2f", values[i][j]));
            }
            System.out.println();
        }
    }
}

