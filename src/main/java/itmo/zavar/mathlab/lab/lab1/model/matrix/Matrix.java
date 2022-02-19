package itmo.zavar.mathlab.lab.lab1.model.matrix;

import com.sun.istack.internal.NotNull;
import itmo.zavar.mathlab.workspace.common.MathObject;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;

public final class Matrix implements MathObject {

    private final String name;
    private final double[][] elements;
    private final int rows;
    private final int cols;

    public Matrix(String name, int rows, int cols) {
        this.name = name;
        this.elements = new double[rows][cols];
        this.rows = rows;
        this.cols = cols;
    }

    public Matrix(String name, double[][] elements) {
        this.name = name;
        this.elements = elements;
        this.rows = elements.length;
        this.cols = elements[0].length;
    }

    public double[][] getElements() {
        return elements;
    }

    public double[][] getCopyElements() {
        return Arrays.copyOf(elements, elements.length);
    }

    public double get(int row, int col) {
        return elements[row][col];
    }

    public void set(double value, int row, int col) {
        elements[row][col] = value;
    }

    public double[] getRow(int i) {
        return elements[i];
    }

    public double[] getColumn(int j) {
        double[] column = new double[cols];
        for (int i = 0; i < column.length; i++) {
            column[i] = elements[i][j];
        }
        return column;
    }

    public Matrix getWithoutRow(int ren) {
        double[][] matrix2 = new double[elements.length - 1][elements[0].length];
        int p = 0;
        for (int i = 0; i < elements.length; ++i) {
            if (i == ren)
                continue;
            int q = 0;
            for (int j = 0; j < elements[0].length; ++j) {
                matrix2[p][q] = elements[i][j];
                ++q;
            }
            ++p;
        }
        return new Matrix(name, matrix2);
    }

    public int getRowsCount() {
        return rows;
    }

    public int getColumnsCount() {
        return cols;
    }

    public boolean isEmpty() {
        return elements.length == 0;
    }

    public boolean isSquare() {
        return rows == cols;
    }

    public boolean isRectangle() {
        return rows != cols;
    }

    public boolean isRow() {
        return rows == 1;
    }

    public boolean isColumn() {
        return cols == 1;
    }

    public boolean isSingleton() {
        return rows == 1 && cols == 1;
    }

    public boolean isNull() {
        for (int i = 0; i < rows * cols; i++) {
            if (elements[i / cols][i % cols] != 0)
                return false;
        }
        return true;
    }

    public boolean isDiagonal() {
        if (!isSquare())
            return false;

        for (int i = 0; i < rows * cols; i++) {
            if (i / cols != i % cols && elements[i / cols][i % cols] != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isScalar() {
        if (!isSquare() && !isDiagonal())
            return false;
        for (int i = 0; i < rows - 1; i++) {
            if (elements[i][i] != elements[i + 1][i + 1])
                return false;
        }
        return true;
    }

    public boolean isIdentity() {
        if (!isSquare() && !isDiagonal())
            return false;
        for (int i = 0; i < rows - 1; i++) {
            if (elements[i][i] != 1)
                return false;
        }
        return true;
    }

    public boolean isSymmetric() {
        if (!isSquare())
            return false;
        for (int i = 0; i < rows * cols; i++) {
            if (i / cols != i % cols && elements[i / cols][i % cols] != elements[i % cols][i / cols]) {
                return false;
            }
        }
        return true;
    }

    public boolean isAntiSymmetric() {
        if (!isSquare())
            return false;
        for (int i = 0; i < rows * cols; i++) {
            if (i / cols != i % cols && elements[i / cols][i % cols] != -elements[i % cols][i / cols]) {
                return false;
            }
        }
        return true;
    }

    public boolean isLowerTriangular() {
        if (!isSquare())
            return false;

        for (int i = 0; i < rows; i++) {
            for (int j = i + 1; j < rows; j++) {
                if (elements[i][j] != 0)
                    return false;
            }
        }
        return true;
    }

    public boolean isUpperTriangular() {
        if (!isSquare())
            return false;

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < i; j++) {
                if (elements[i][j] != 0)
                    return false;
            }
        }
        return true;
    }

    public static Matrix subMatrix(@NotNull Matrix matrix, String newName, int excludeRow, int excludeCol) {
        Matrix result = new Matrix(newName, matrix.getRowsCount() - 1, matrix.getColumnsCount() - 1);

        for (int row = 0, p = 0; row < matrix.getRowsCount(); row++) {
            if (row != excludeRow - 1) {
                for (int col = 0, q = 0; col < matrix.getColumnsCount(); col++) {
                    if (col != excludeCol - 1) {
                        result.set(matrix.get(row, col), p, q);
                        ++q;
                    }
                }
                ++p;
            }
        }

        return result;
    }

    public Matrix transpose(String name) {
        Matrix result = new Matrix(name, cols, rows);

        for (int i = 0; i < rows * cols; i++) {
            result.set(elements[i / cols][i % cols], i % cols, i / cols);
        }

        return result;
    }

    public void exchangeRows(int from, int to) {
        for (int i = 0; i < elements[0].length; i++) {
            double temp = elements[from - 1][i];
            elements[from - 1][i] = elements[to - 1][i];
            elements[to - 1][i] = temp;
        }
    }

    public double determinant() {
        if (rows != cols) {
            return Double.NaN;
        } else {
            return determinant0(this);
        }
    }

    private double determinant0(@NotNull Matrix matrix) {
        if (matrix.cols == 1) {
            return matrix.getElements()[0][0];
        } else if (matrix.cols == 2) {
            return (matrix.getElements()[0][0] * matrix.getElements()[1][1] -
                    matrix.getElements()[0][1] * matrix.getElements()[1][0]);
        } else {
            double result = 0.0;

            for (int col = 0; col < matrix.cols; ++col) {
                Matrix sub = subMatrix(matrix, matrix.name, 1, col + 1);

                result += (Math.pow(-1, 1 + col + 1) *
                        matrix.getElements()[0][col] * determinant0(sub));
            }

            return result;
        }
    }

    public void print(@NotNull OutputStream outputStream) {
        PrintStream writer = new PrintStream(outputStream);
        for (int row = 0; row < rows; row++) {
            writer.print("[");
            for (int col = 0; col < cols; col++) {
                writer.printf("%8.3f", elements[row][col]);
                if (col != cols - 1) {
                    writer.print(" ");
                }
            }
            writer.println("]");
        }
    }

    public long order() {
        return (long) cols * (long) rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Matrix matrix = (Matrix) o;
        return rows == matrix.rows && cols == matrix.cols && Arrays.deepEquals(elements, matrix.elements);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(rows, cols);
        result = 31 * result + Arrays.deepHashCode(elements);
        return result;
    }

    @Override
    public String toString() {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            print(outputStream);
            return outputStream.toString(StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            return "";
        }
    }

    @Override
    public String name() {
        return name;
    }
}
