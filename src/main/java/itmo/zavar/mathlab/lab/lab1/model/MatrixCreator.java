package itmo.zavar.mathlab.lab.lab1.model;

import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Random;

public final class MatrixCreator {

    public static Matrix fromColumn(String name, double[] column) {
        double[][] elements = new double[column.length][1];
        for (int i = 0; i < column.length; i++) {
            elements[i][1] = column[i];
        }
        return new Matrix(name, elements);
    }

    public static Matrix fromRow(String name, double[] row) {
        double[][] elements = {row};
        return new Matrix(name, elements);
    }

    public static Matrix fromNumber(String name, double single) {
        double[][] elements = new double[1][1];
        elements[0][0] = single;
        return new Matrix(name, elements);
    }

    public static Matrix fromString(String name, String matrixString) throws IllegalArgumentException {

        String[] rowsString = matrixString.split("\n");
        int rows = rowsString.length;
        int cols = rowsString[0].split(" ").length;
        double[][] elements = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            double[] temp = Arrays.stream(rowsString[i].split(" ")).mapToDouble(Double::parseDouble).toArray();
            if (temp.length != cols)
                throw new IllegalArgumentException("Check your input");
            elements[i] = temp;
        }

        return new Matrix(name, elements);
    }

    public static Matrix getNullMatrix(int size) {
        Matrix matrix = new Matrix("Null Matrix (" + size + "x" + size + ")", size, size);
        for (int i = 0; i < size * size; i++) {
            matrix.getElements()[i / size][i % size] = 0;
        }
        return matrix;
    }

    public static Matrix getScalarMatrix(String name, int size, double number) {
        Matrix matrix = new Matrix(name, size, size);
        for (int i = 0; i < size * size; i++) {
            if (i / size == i % size) {
                matrix.getElements()[i / size][i % size] = number;
            }
        }
        return matrix;
    }

    public static Matrix getIdentityMatrix(int size) {
        return getScalarMatrix("Identity Matrix (" + size + "x" + size + ")", size, 1);
    }

    public static Matrix getRandomMatrix(String name, int rows, int cols, double maxValue, double minValue) {
        Random r = new Random();
        Matrix matrix = new Matrix(name, rows, cols);
        for (int i = 0; i < rows * cols; i++) {
            matrix.getElements()[i / cols][i % cols] = minValue + (maxValue - minValue) * r.nextDouble();
        }
        return matrix;
    }

}
