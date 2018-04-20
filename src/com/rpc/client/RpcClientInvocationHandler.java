package com.rpc.client;

import com.rpc.exception.RpcConnException;
import com.rpc.exception.RpcRemoteException;
import com.rpc.io.meta.InvocationInfo;
import com.rpc.io.meta.ResultInfo;
import com.rpc.io.meta.RpcConnection;
import com.rpc.util.BaseInvocationHandler;

import java.io.IOException;
import java.lang.reflect.Method;

public class RpcClientInvocationHandler extends BaseInvocationHandler {

    private RpcClient client;
    private Class<?> type;

    public RpcClientInvocationHandler(RpcClient client,Class<?> type) {
        this.client = client;
        this.type = type;
    }

    @Override
    protected Object invokeProxy(Object proxy, Method method, Object[] args) throws Throwable {
        RpcConnection connection = null;
        ResultInfo resultInfo = null;
        try{
            connection = client.openConnection();
            InvocationInfo invocationInfo = new InvocationInfo(method,args,type.getName());
            connection.writeObject(client.getVersion());
            connection.writeObject(invocationInfo);
            connection.writeObject(client.getAuth());
            connection.flush();
            resultInfo = connection.readObject(ResultInfo.class);
        }catch (IOException e){
            throw new RpcConnException(e);
        }catch (ClassNotFoundException ex){
            throw new RpcConnException(ex);
        }finally {
            if(connection != null){
                try{
                    connection.close();
                }catch (IOException ex){}
            }
        }
        if(resultInfo == null){
            throw new RpcRemoteException("null result.");
        }
        if(resultInfo.getThrowable() != null){
            throw new RpcRemoteException("remote server report an error:" + resultInfo.getThrowable(),resultInfo.getThrowable());
        }
        return resultInfo.getResult();
    }

    @Override
    protected String toStringImpl(Object proxy) {
        return "RpcClientProxy$" + type.getName() + "@" + Integer.toHexString(System.identityHashCode(proxy));
    }
}
