package com.rpc.io.object;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoSerializable;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.JavaSerializer;
import com.rpc.exception.RpcAuthException;
import com.rpc.exception.RpcConnException;
import com.rpc.exception.RpcRemoteException;
import com.rpc.io.meta.*;
import com.rpc.util.BaseAuth;


import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;

public class KryoObjectFactory implements ObjectFactory {

    protected static Kryo kryo;

    static {
        kryo = new Kryo();
        kryo.register(InvocationInfo.class,0xee01);
        kryo.register(MethodInfo.class,0xee02);
        kryo.register(ResultInfo.class,0xee03);
        kryo.register(RpcConnection.class,0xee04);
        kryo.register(Version.class,0xee05);
        kryo.register(BaseAuth.class,0xee06);

        JavaSerializer javaSerializer = new JavaSerializer();
        kryo.register(RpcRemoteException.class,javaSerializer,0xee07);
        kryo.register(RpcAuthException.class,javaSerializer,0xee08);
        kryo.register(RpcConnException.class,javaSerializer,0xee09);
    }

    @Override
    public ObjectOut getOut(OutputStream out) throws IOException {
        final Output output = new Output(out);
        return new ObjectOut() {
            @Override
            public void writeObject(Object object) throws IOException {
                kryo.writeClassAndObject(output,object);
            }

            @Override
            public void flush() throws IOException {
                output.flush();
            }
        };
    }

    @Override
    public ObjectInput getInput(InputStream in) throws IOException {
        final Input input = new Input(in);
        return new ObjectInput() {
            @Override
            public <T> T readObject(Class<T> expectedType) throws IOException, ClassNotFoundException {
                return expectedType.cast( kryo.readClassAndObject(input));
            }
        };
    }


}
