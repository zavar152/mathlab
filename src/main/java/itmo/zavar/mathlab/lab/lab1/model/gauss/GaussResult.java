package itmo.zavar.mathlab.lab.lab1.model.gauss;

import itmo.zavar.mathlab.lab.lab1.model.matrix.Matrix;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.OutputStream;

public final class GaussResult implements Result {

    private final Matrix x;
    private final Matrix discrepancy;
    private final Matrix newSystem;
    private final double det0;
    private final double det1;
    private final long time;

    public GaussResult(Matrix newSystem, Matrix x, Matrix discrepancy, double det0, double det1, long time) {
        this.x = x;
        this.discrepancy = discrepancy;
        this.newSystem = newSystem;
        this.det0 = det0;
        this.det1 = det1;
        this.time = time;
    }

    @Override
    public Object getRaw() {
        return null;
    }

    @Override
    public void print(OutputStream outputStream) {

    }
}
