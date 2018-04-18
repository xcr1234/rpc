package com.rpc.io.meta;

import java.io.Serializable;

public class ResultInfo implements Serializable {
    private static final long serialVersionUID = -5263557470977019836L;


    private Throwable throwable;

    private Object result;

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}
