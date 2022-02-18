package itmo.zavar.mathlab.workspace.common;

import java.io.OutputStream;

public interface Result {
    Object getRaw();
    void print(OutputStream outputStream);
}
