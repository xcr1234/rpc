package com.rpc.io.meta;

import com.rpc.io.object.ObjectFactory;
import com.rpc.io.object.ObjectInput;
import com.rpc.io.object.ObjectOut;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class RpcConnection implements Closeable{

    private Socket socket;
    private ObjectOut out;
    private ObjectInput in;
    private ObjectFactory objectFactory;

    public RpcConnection(Socket socket) throws IOException {
        this.socket = socket;

    }

    public void writeObject(Object o) throws IOException{
        if(out == null){
            out = getObjectFactory().getOut(socket.getOutputStream());
        }
        out.writeObject(o);
    }

    public <T> T readObject(Class<T> expectedClass) throws IOException,ClassNotFoundException{
         if(in == null){
             in = getObjectFactory().getInput(socket.getInputStream());
         }
         return in.readObject(expectedClass);
    }

    public void flush() throws IOException{
        out.flush();
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }



    @Override
    public void close() throws IOException {
        if(socket != null){
            socket.close();
        }
    }
}
