package com.rpc.io.object;

import java.io.*;

public class SerializableObjectFactory implements ObjectFactory {

    protected ObjectOutput newObjectOutput(OutputStream out)throws IOException{
        return new ObjectOutputStream(out);
    }

    protected java.io.ObjectInput newObjectInput(InputStream in)throws IOException{
        return new ObjectInputStream(in);
    }

    @Override
    public final ObjectOut getOut(OutputStream out) throws IOException {
        final class Out implements ObjectOut{

            private ObjectOutput objectOutputStream;

            private Out(OutputStream out) throws IOException{
                objectOutputStream = newObjectOutput(out);
            }

            @Override
            public void writeObject(Object object) throws IOException {
                objectOutputStream.writeObject(object);
            }
        }
        return new Out(out);
    }

    @Override
    public final ObjectInput getInput(InputStream in) throws IOException {
        final class Input implements ObjectInput{

            private java.io.ObjectInput objectInputStream;

            private Input(InputStream in) throws IOException {
                this.objectInputStream = newObjectInput(in);
            }

            @Override
            public <T> T readObject(Class<T> expectedType) throws IOException, ClassNotFoundException {
                return expectedType.cast(objectInputStream.readObject());
            }
        }
        return new Input(in);
    }


}
