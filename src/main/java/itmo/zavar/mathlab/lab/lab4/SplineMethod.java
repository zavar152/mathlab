package itmo.zavar.mathlab.lab.lab4;

import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.linear.LUDecomposition;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.None;
import org.mariuszgromada.math.mxparser.Function;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;

public final class SplineMethod {

    public static void calculate(double[] x, double[] y, Function realFunction, double delta, SwingWrapper<XYChart> wrap, XYChart chart) {
        int n = x.length;
        int equations = 4 * (n - 1);

        double[][] matrix = new double[equations][equations + 1];
        double[][] firstCoefficients = new double[n][4];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 4; j++) {
                firstCoefficients[i][j] = Math.pow(x[i], j);
            }
        }
        for (int i = 0; i < n - 1; i++) {
            int k = 0;
            for (int j = 0; j < equations; j += n - 1) {
                matrix[i][j + i] = firstCoefficients[i][k];
                k += 1;
            }
        }
        int f = 0;
        for (int j = n - 1; j <= equations; j += n - 1) {
            matrix[n - 1][j - 1] = firstCoefficients[n - 1][f];
            f++;
        }
        for (int i = 0; i < n; i++) {
            matrix[i][equations] = y[i];
        }

        double[][] secondCoefficients = new double[n - 2][8];

        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 8; i += 2) {
                double pow = Math.pow(x[j + 1], k);
                secondCoefficients[j][i] = pow;
                secondCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = 0; j < equations; j += n - 1) {
                matrix[n + i][j + i] = secondCoefficients[i][k];
                matrix[n + i][j + 1 + i] = secondCoefficients[i][k + 1];
                k += 2;
            }
        }

        double[][] thirdCoefficients = new double[n - 2][6];
        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 6; i += 2) {
                double pow = (k + 1) * Math.pow(x[j + 1], k);
                thirdCoefficients[j][i] = pow;
                thirdCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = n - 1; j < equations; j += n - 1) {
                matrix[n - 2 + n + i][j + i] = thirdCoefficients[i][k];
                matrix[n - 2 + n + i][j + 1 + i] = thirdCoefficients[i][k + 1];
                k += 2;
            }
        }

        double[][] forthCoefficients = new double[n - 2][4];
        for (int j = 0; j < n - 2; j++) {
            int k = 0;
            for (int i = 0; i < 4; i += 2) {
                double pow = 0;
                if(i == 0)
                    pow = 2;
                else
                    pow = 6 * x[j + 1];
                forthCoefficients[j][i] = pow;
                forthCoefficients[j][i + 1] = -pow;
                k++;
            }
        }

        for (int i = 0; i < n - 2; i++) {
            int k = 0;
            for (int j = 2 * (n - 1); j < equations; j += n - 1) {
                matrix[2 * (n - 2) + n + i][j + i] = forthCoefficients[i][k];
                matrix[2 * (n - 2) + n + i][j + 1 + i] = forthCoefficients[i][k + 1];
                k += 2;
            }
        }

        matrix[equations - 2][equations-2*(n-1)] = 1;
        matrix[equations - 1][equations-2*(n-1)+(n-2)] = 1;
        matrix[equations - 1][equations-(n-1)+(n-2)] = 3*x[n-1];

        Matrix m = new Matrix("m", matrix);

        RealVector b = new ArrayRealVector(m.getColumn(equations),false);
        RealMatrix a = new Array2DRowRealMatrix(m.getWithoutColumn(equations).getElements(),false);

        DecompositionSolver solver = new LUDecomposition(a).getSolver();
        RealVector solution = solver.solve(b);
        double[] coefs = solution.toArray();
        Function[] functions = new Function[n - 1];

        for (int i = 0; i < n - 1; i++) {
            int k = 0;
            StringBuilder formula = new StringBuilder("f(x)=");
            for (int j = i; j < equations; j+=n-1) {
                formula.append(coefs[j] >= 0 ? "+" + coefs[j] : coefs[j]).append("*x^").append(k);
                k++;
            }
            functions[i] = new Function(formula.toString());
        }


        ArrayList<Double> splineDotY = new ArrayList<>();
        ArrayList<Double> splineDotX = new ArrayList<>();

        ArrayList<Double> functionY = new ArrayList<>();
        ArrayList<Double> functionX = new ArrayList<>();

        ArrayList<Double> realY = new ArrayList<>();
        ArrayList<Double> realX = new ArrayList<>();

        double step = 0.01;

        for (int i = 0; i < n - 1; i++) {
            splineDotX.add(x[i]);
            splineDotY.add(functions[i].calculate(x[i]));
            splineDotX.add(x[i+1]);
            splineDotY.add(functions[i].calculate(x[i+1]));
            if(i == 0)
                x[i]-=delta;
            else if(i == n - 2)
                x[i+1]+=delta;

            for (double j = x[i]; j <= x[i+1]; j+=step) {
                functionY.add(functions[i].calculate(j));
                functionX.add(j);
                realY.add(realFunction.calculate(j));
                realX.add(j);
            }
        }



        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideN);
        chart.getStyler().setYAxisLogarithmic(false);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setMarkerSize(10);

        chart.addSeries("dots", splineDotX, splineDotY);
        chart.addSeries("function", functionX, functionY);
        chart.addSeries("real", realX, realY);

        chart.getSeriesMap().get("function").setMarker(new None());
        chart.getSeriesMap().get("function").setLineWidth(2.7F);
        chart.getSeriesMap().get("real").setMarker(new None());
        chart.getSeriesMap().get("real").setLineWidth(0.7F);
        chart.getSeriesMap().get("dots").setShowInLegend(false);
        chart.getSeriesMap().get("dots").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        wrap.repaintChart();
    }
}
