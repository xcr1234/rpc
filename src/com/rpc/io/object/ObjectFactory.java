package com.rpc.io.object;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ObjectFactory {

    ObjectOut getOut(OutputStream out) throws IOException;

    ObjectInput getInput(InputStream in)throws IOException;

}
