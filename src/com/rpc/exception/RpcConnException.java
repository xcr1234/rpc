package com.rpc.exception;

public class RpcConnException extends RuntimeException {


    private static final long serialVersionUID = -5188753694223653753L;

    public RpcConnException() {
    }

    public RpcConnException(String message) {
        super(message);
    }

    public RpcConnException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcConnException(Throwable cause) {
        super(cause);
    }
}
