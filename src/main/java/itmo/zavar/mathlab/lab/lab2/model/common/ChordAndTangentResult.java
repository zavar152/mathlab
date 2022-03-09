package itmo.zavar.mathlab.lab.lab2.model.common;

import com.google.common.collect.Lists;
import itmo.zavar.mathlab.lab.lab2.model.chord.ChordResult;
import itmo.zavar.mathlab.lab.lab2.model.tangent.TangentResult;
import itmo.zavar.mathlab.workspace.common.Result;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public final class ChordAndTangentResult implements Result {

    private final ChordResult chordResult;
    private final TangentResult tangentResult;

    public ChordAndTangentResult(ChordResult chordResult, TangentResult tangentResult) {
        this.chordResult = chordResult;
        this.tangentResult = tangentResult;
    }

    public ChordResult getChordResult() {
        return chordResult;
    }

    public TangentResult getTangentResult() {
        return tangentResult;
    }

    @Override
    public ArrayList<Object> getRaw() {
        return Lists.newArrayList(tangentResult, chordResult);
    }

    @Override
    public void print(OutputStream outputStream) {
        PrintStream printStream = new PrintStream(outputStream);
        tangentResult.print(outputStream);
        chordResult.print(outputStream);
        printStream.println("Difference: ");
        printStream.println(Math.abs(tangentResult.getAnswer() - chordResult.getAnswer()));
        printStream.println();
    }
}
