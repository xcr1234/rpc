package com.rpc.server;

import com.rpc.exception.RpcAuthException;
import com.rpc.exception.RpcRemoteException;
import com.rpc.io.meta.*;

import java.lang.reflect.InvocationTargetException;

public class DefaultHandler implements ServerHandler {


    private RpcServer server;

    public RpcServer getServer() {
        return server;
    }

    public void setServer(RpcServer server) {
        this.server = server;
    }

    protected boolean checkVersion(ResultInfo resultInfo, Version clientVersion) {
        if (server.getVersion().compareTo(clientVersion) < 0) {
            resultInfo.setThrowable(new RpcRemoteException("Version incompatible." + server.getVersion() + " with " + clientVersion));
            return false;
        }
        return true;
    }

    @Override
    public void handle(ResultInfo resultInfo, RpcConnection connection, Version clientVersion, InvocationInfo invocationInfo, Auth auth) {
        if (!checkVersion(resultInfo, clientVersion)) {
            return;
        }
        if (!authorize(resultInfo, auth)) {
            return;
        }


        try {
            Object o = invocation(invocationInfo);
            resultInfo.setResult(o);
        } catch (Throwable t) {
            if (t instanceof InvocationTargetException) {
                t = ((InvocationTargetException) t).getTargetException();
            }
            //t.printStackTrace();
            resultInfo.setThrowable(t);
        }

    }

    protected boolean authorize(ResultInfo resultInfo, Auth auth) {
        try {
            if (checkAuth(auth)) {
                return true;
            }
            resultInfo.setThrowable(new RpcAuthException("not authorized"));
            return false;
        } catch (Throwable throwable) {
            resultInfo.setThrowable(new RpcAuthException("authorize fail.", throwable));
            return false;
        }
    }

    protected boolean checkAuth(Auth auth) throws Throwable {
        if (server.getAuthorizer() != null) {
            if (auth == null) {
                return false;
            }
            return server.getAuthorizer().isValidAuth(auth);
        }
        return true;
    }

    protected Object invocation(InvocationInfo invocationInfo) throws Throwable {
        Class<?> type = Class.forName(invocationInfo.getType());
        Object target = server.getObjectMap().get(type);
        if (target == null) {
            throw new IllegalArgumentException("Invocation not found for " + invocationInfo.getType());
        }
        MethodInfo methodInfo = invocationInfo.getMethodInfo();
        Class[] parameterTypes = null;
        if(methodInfo.getParameterTypes() != null){
            String[] methodTypes = methodInfo.getParameterTypes();
            parameterTypes = new Class[methodTypes.length];
            for(int i=0;i<methodTypes.length;i++){
                parameterTypes[i] = Class.forName(methodTypes[i],false,getClass().getClassLoader());
            }
        }
        return type.getDeclaredMethod(methodInfo.getName()
                , parameterTypes).invoke(target, invocationInfo.getArgs());
    }
}
