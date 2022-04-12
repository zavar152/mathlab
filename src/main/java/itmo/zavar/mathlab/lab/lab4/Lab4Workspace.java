package itmo.zavar.mathlab.lab.lab4;

import itmo.zavar.mathlab.annotation.Workspace;
import itmo.zavar.mathlab.command.AbstractCommand;
import itmo.zavar.mathlab.lab.lab2.model.exception.CalculationException;
import itmo.zavar.mathlab.workspace.AbstractWorkspace;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;
import org.knowm.xchart.XYChartBuilder;
import org.mariuszgromada.math.mxparser.Function;

import java.util.HashMap;

@Workspace(name = "lab4", id = 4)
public class Lab4Workspace extends AbstractWorkspace {

    protected Lab4Workspace(HashMap<String, AbstractCommand> commandMap, String name, int id) {
        super(commandMap, name, id);
    }

    @Override
    public void calculate(Object[] args) throws CalculationException {
        if (workspaceContainer.size() == 0)
            throw new CalculationException("No elements in workspace");

        if (args.length != 4)
            throw new CalculationException("Check input arguments");


        Double[] x = (Double[]) args[0];
        Double[] y = (Double[]) args[1];
        Function realFunction = (Function) args[2];
        double delta = (double) args[3];

        XYChart chart = new XYChartBuilder()
                .width(800)
                .height(600)
                .title("Метод кубических сплайнов")
                .xAxisTitle("X")
                .yAxisTitle("Y")
                .build();

        SwingWrapper wrap = new SwingWrapper(chart);
        wrap.displayChart();

        try {
            SplineMethod.calculate(x, y, realFunction, delta, wrap, chart);
        } catch (Exception e) {
            throw new CalculationException(e.getMessage());
        }
    }
}
