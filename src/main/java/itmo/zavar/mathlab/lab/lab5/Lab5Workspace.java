package itmo.zavar.mathlab.lab.lab5;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.lab.lab4.model.SplineMethod;
import itmo.zavar.mathlab.lab.lab4.model.SplineResult;
import itmo.zavar.mathlab.lab.lab5.model.euler.AdvancedEulerMethod;
import itmo.zavar.mathlab.lab.lab5.model.euler.AdvancedEulerResult;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.Circle;
import org.knowm.xchart.style.markers.None;
import org.mariuszgromada.math.mxparser.Function;

import java.awt.*;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;

@Workspace(name = "lab5", id = 5)
public class Lab5Workspace extends AbstractWorkspace {

    private final XYChart chart;
    private final SwingWrapper<XYChart> wrap;

    protected Lab5Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
        chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Усовершенствованный метод Эйлера")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setYAxisLogarithmic(false);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setMarkerSize(8);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setZoomResetByDoubleClick(true);
        chart.getStyler().setZoomSelectionColor(new Color(0, 0, 192, 128));

        chart.getStyler().setCursorEnabled(true);
        chart.getStyler().setCursorColor(Color.BLACK);
        chart.getStyler().setCursorLineWidth(1f);
        chart.getStyler().setCursorFont(new Font("Verdana", Font.BOLD, 12));
        chart.getStyler().setCursorFontColor(Color.ORANGE);
        chart.getStyler().setCursorBackgroundColor(Color.GRAY);

        wrap = new SwingWrapper<>(chart);
        wrap.displayChart();
    }

    @Override
    public void calculate(Object[] args) {
        if (args.length != 8)
            throw new CalculationException("Check input arguments");

        double x0 = Double.parseDouble((String) args[1]);
        double y0 = Double.parseDouble((String) args[2]);
        double b = Double.parseDouble((String) args[3]);
        int dotsNumber = Integer.parseInt((String) args[4]);
        Function function = (Function) args[5];
        Function realFunction = (Function) args[6];
        double delta = Double.parseDouble((String) args[7]);

        AdvancedEulerResult r = AdvancedEulerMethod.calculate(y0, x0, b, dotsNumber, function, realFunction);

        r.print((OutputStream) args[0]);

        double[] x = r.getX();
        double[] y = r.getY();
        SplineResult result = SplineMethod.calculate(x, y);

        ArrayList<Double> functionY = new ArrayList<>();
        ArrayList<Double> functionX = new ArrayList<>();

        ArrayList<Double> realY = new ArrayList<>();
        ArrayList<Double> realX = new ArrayList<>();

        ArrayList<Double> realDotsY = new ArrayList<>();
        ArrayList<Double> realDotsX = new ArrayList<>();

        ArrayList<Double> dotsY = new ArrayList<>();
        ArrayList<Double> dotsX = new ArrayList<>();

        double step = 0.01;

        for (int i = 0; i < x.length - 1; i++) {
            for (double j = x[i]; j <= x[i + 1]; j += step) {
                functionY.add(result.getSplines()[i].calculate(j));
                functionX.add(j);
                realY.add(r.getRealFunction().calculate(j));
                realX.add(j);

                if (i != 0) {
                    dotsX.add(x[i]);
                    realDotsX.add(x[i]);
                    dotsY.add(y[i]);
                    realDotsY.add(r.getRealY()[i]);
                }
            }
        }
        functionY.add(result.getSplines()[x.length - 2].calculate(x[x.length - 1] + delta));
        functionX.add(x[x.length - 1] + delta);
        realY.add(r.getRealFunction().calculate(x[x.length - 1] + delta));
        realX.add(x[x.length - 1] + delta);
        dotsX.add(x[x.length - 1]);
        realDotsX.add(x[x.length - 1]);
        dotsY.add(y[x.length - 1]);
        realDotsY.add(r.getRealY()[x.length - 1]);

        chart.removeSeries("solution");
        chart.removeSeries("exact");
        chart.removeSeries("initial");
        chart.removeSeries("dots");
        chart.removeSeries("realDots");

        chart.addSeries("solution", functionX, functionY);
        chart.addSeries("exact", realX, realY);
        chart.addSeries("initial", Lists.newArrayList(x0), Lists.newArrayList(y0));
        chart.addSeries("dots", dotsX, dotsY);
        chart.addSeries("realDots", realDotsX, realDotsY);

        chart.getSeriesMap().get("solution").setMarker(new None());
        chart.getSeriesMap().get("solution").setLineWidth(0.7F);
        chart.getSeriesMap().get("exact").setMarker(new None());
        chart.getSeriesMap().get("exact").setLineWidth(0.7F);
        chart.getSeriesMap().get("initial").setMarker(new Circle()).setMarkerColor(Color.RED);
        chart.getSeriesMap().get("initial").setShowInLegend(false);
        chart.getSeriesMap().get("initial").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getSeriesMap().get("dots").setMarker(new Circle()).setMarkerColor(Color.BLACK);
        chart.getSeriesMap().get("dots").setShowInLegend(false);
        chart.getSeriesMap().get("dots").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getSeriesMap().get("realDots").setMarker(new Circle()).setMarkerColor(Color.GREEN);
        chart.getSeriesMap().get("realDots").setShowInLegend(false);
        chart.getSeriesMap().get("realDots").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        wrap.repaintChart();
    }
}
