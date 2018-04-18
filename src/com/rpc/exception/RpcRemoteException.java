package com.rpc.exception;

public class RpcRemoteException extends RuntimeException {

    private static final long serialVersionUID = -7751828962619720682L;

    public RpcRemoteException() {
    }

    public RpcRemoteException(String message) {
        super(message);
    }

    public RpcRemoteException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcRemoteException(Throwable cause) {
        super(cause);
    }
}
