package com.rpc.io.object;

import java.io.IOException;

public interface ObjectInput {
    <T> T readObject(Class<T> expectedType) throws IOException,ClassNotFoundException;
}
