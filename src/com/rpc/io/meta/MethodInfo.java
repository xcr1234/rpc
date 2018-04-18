package com.rpc.io.meta;


import java.io.Serializable;
import java.lang.reflect.Method;

public class MethodInfo implements Serializable {

    private static final long serialVersionUID = 7128488060743911159L;
    private String name;


    private String[] parameterTypes;


    public MethodInfo(){

    }

    public MethodInfo(Method method){
        this.name = method.getName();
        Class[] parameterTypes = method.getParameterTypes();
        this.parameterTypes = new String[parameterTypes.length];
        for(int i=0;i<parameterTypes.length;i++){
            this.parameterTypes[i] = parameterTypes[i].getName();
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(String[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

}
