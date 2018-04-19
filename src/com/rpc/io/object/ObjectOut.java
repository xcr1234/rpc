package com.rpc.io.object;

import java.io.IOException;

public interface ObjectOut {
    void writeObject(Object object) throws IOException;
    void flush() throws IOException;
}
