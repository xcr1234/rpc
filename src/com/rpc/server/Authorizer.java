package com.rpc.server;

import com.rpc.io.meta.Auth;

public interface Authorizer {
    boolean isValidAuth(Auth auth)throws Throwable;
}
