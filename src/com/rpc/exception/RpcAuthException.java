package com.rpc.exception;

public class RpcAuthException extends RpcRemoteException {
    private static final long serialVersionUID = -7454511250894649092L;

    public RpcAuthException() {
    }

    public RpcAuthException(String message) {
        super(message);
    }

    public RpcAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcAuthException(Throwable cause) {
        super(cause);
    }
}
