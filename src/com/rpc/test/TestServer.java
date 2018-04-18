package com.rpc.test;

import com.rpc.io.meta.Auth;
import com.rpc.util.BaseAuth;
import com.rpc.server.Authorizer;
import com.rpc.server.RpcServer;

import java.net.ServerSocket;

public class TestServer {
    public static void main(String[] args) throws Exception{

        ServerSocket serverSocket = new ServerSocket(8100);
        RpcServer server = new RpcServer(serverSocket);
        server.setAuthorizer(new Authorizer() {
            @Override
            public boolean isValidAuth(Auth auth) throws Throwable {
                BaseAuth baseAuth = (BaseAuth)auth;
                return "admin".equals(baseAuth.getUsername()) && "admin".equals(baseAuth.getPassword());
            }
        });
        server.addInvocation(TestInterface.class,new TestInterfaceImpl());
        server.start();
    }
}
