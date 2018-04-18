package com.rpc.io.meta;


import java.io.Serializable;
import java.lang.reflect.Method;

public class InvocationInfo implements Serializable{

    private static final long serialVersionUID = 198007473672633105L;

    private MethodInfo methodInfo;

    private Object[] args;

    private String type;

    public InvocationInfo() {
    }

    public InvocationInfo(Method m, Object[] args, String type) {
        this.methodInfo = new MethodInfo(m);
        this.args = args;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MethodInfo getMethodInfo() {
        return methodInfo;
    }

    public void setMethodInfo(MethodInfo methodInfo) {
        this.methodInfo = methodInfo;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

}
