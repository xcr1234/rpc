package com.rpc.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public abstract class BaseInvocationHandler implements InvocationHandler {
    @Override
    public final Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if(method.getDeclaringClass() == Object.class){
            if("hashCode".equals(method.getName())){
                return hashCodeImpl(proxy);
            }
            if("equals".equals(method.getName())){
                return equalsImpl(proxy,args[0]);
            }
            if("toString".equals(method.getName())){
                return toStringImpl(proxy);
            }
            if("finalize".equals(method.getName())){
                finalizeImpl(proxy);
                return null;
            }
        }
        return invokeProxy(proxy,method,args);
    }

    protected abstract Object invokeProxy(Object proxy, Method method, Object[] args) throws Throwable;

    protected int hashCodeImpl(Object proxy){
        return System.identityHashCode(proxy);
    }

    protected boolean equalsImpl(Object proxy,Object o){
        return o != null && o.equals(proxy);
    }


    protected String toStringImpl(Object proxy){
        return String.valueOf(proxy);
    }

    protected void finalizeImpl(Object proxy) throws Throwable{

    }
}
