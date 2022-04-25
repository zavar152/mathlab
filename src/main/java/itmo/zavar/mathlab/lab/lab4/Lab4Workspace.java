package itmo.zavar.mathlab.lab.lab4;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.lab.lab4.model.SplineMethod;
import itmo.zavar.mathlab.lab.lab4.model.SplineResult;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYSeries;
import org.knowm.xchart.style.Styler;
import org.knowm.xchart.style.markers.BaseSeriesMarkers;
import org.knowm.xchart.style.markers.None;
import org.mariuszgromada.math.mxparser.Function;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

@Workspace(name = "lab4", id = 4)
public class Lab4Workspace extends AbstractWorkspace {

    private final XYChart chart;
    private final SwingWrapper<XYChart> wrap;

    protected Lab4Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
        chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Метод кубических сплайнов")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        chart.getStyler().setChartTitleVisible(true);
        chart.getStyler().setLegendPosition(Styler.LegendPosition.OutsideE);
        chart.getStyler().setYAxisLogarithmic(false);
        chart.getStyler().setXAxisLabelRotation(45);
        chart.getStyler().setMarkerSize(10);
        chart.getStyler().setZoomEnabled(true);
        chart.getStyler().setZoomResetByDoubleClick(true);
        chart.getStyler().setZoomSelectionColor(new Color(0, 0, 192, 128));

        chart.getStyler().setCursorEnabled(true);
        chart.getStyler().setCursorColor(Color.BLACK);
        chart.getStyler().setCursorLineWidth(3f);
        chart.getStyler().setCursorFont(new Font("Verdana", Font.BOLD, 12));
        chart.getStyler().setCursorFontColor(Color.ORANGE);
        chart.getStyler().setCursorBackgroundColor(Color.GRAY);

        wrap = new SwingWrapper<>(chart);
        wrap.displayChart();
    }

    @Override
    public void calculate(Object[] args) throws CalculationException {
        if (args.length != 4)
            throw new CalculationException("Check input arguments");


        double[] x = (double[]) args[0];
        double[] y = (double[]) args[1];
        Function realFunction = (Function) args[2];
        double delta = (double) args[3];

        SplineResult result = SplineMethod.calculate(x, y);

        int n = x.length;

        ArrayList<Double> splineDotY = new ArrayList<>();
        ArrayList<Double> splineDotX = new ArrayList<>();

        ArrayList<Double> functionY = new ArrayList<>();
        ArrayList<Double> functionX = new ArrayList<>();

        ArrayList<Double> realY = new ArrayList<>();
        ArrayList<Double> realX = new ArrayList<>();

        ArrayList<Double> inputDotY = new ArrayList<>();
        ArrayList<Double> inputDotX = new ArrayList<>();

        double step = 0.01;

        for (int i = 0; i < n - 1; i++) {
            splineDotX.add(x[i]);
            inputDotX.add(x[i]);
            splineDotY.add(result.getSplines()[i].calculate(x[i]));
            inputDotY.add(result.getSplines()[i].calculate(x[i]));
            splineDotX.add(x[i+1]);
            inputDotX.add(x[i+1]);
            splineDotY.add(result.getSplines()[i].calculate(x[i+1]));
            inputDotY.add(result.getSplines()[i].calculate(x[i+1]));
            if(i == 0)
                x[i]-=delta;
            else if(i == n - 2)
                x[i+1]+=delta;

            for (double j = x[i]; j <= x[i+1]; j+=step) {
                functionY.add(result.getSplines()[i].calculate(j));
                functionX.add(j);
                realY.add(realFunction.calculate(j));
                realX.add(j);
            }
        }

        chart.removeSeries("input");
        chart.removeSeries("dots");
        chart.removeSeries("spline");
        chart.removeSeries("function");

        chart.addSeries("input", inputDotX, inputDotY);
        chart.addSeries("dots", splineDotX, splineDotY);
        chart.addSeries("spline", functionX, functionY);
        chart.addSeries("function", realX, realY);

        chart.getSeriesMap().get("spline").setMarker(new None());
        chart.getSeriesMap().get("spline").setLineWidth(0.7F);
        chart.getSeriesMap().get("function").setMarker(new None());
        chart.getSeriesMap().get("function").setLineWidth(0.7F);
        chart.getSeriesMap().get("dots").setShowInLegend(false);
        chart.getSeriesMap().get("dots").setMarker(BaseSeriesMarkers.DIAMOND);
        chart.getSeriesMap().get("dots").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);
        chart.getSeriesMap().get("input").setShowInLegend(false);
        chart.getSeriesMap().get("input").setXYSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Scatter);

        wrap.repaintChart();
    }
}
