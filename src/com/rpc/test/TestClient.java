package com.rpc.test;

import com.rpc.client.RpcClient;
import com.rpc.io.object.KryoObjectFactory;
import com.rpc.util.BaseAuth;


public class TestClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient("127.0.0.1",8100);
        rpcClient.setObjectFactory(new KryoObjectFactory());
        rpcClient.setAuth(new BaseAuth("admin","123456"));
        TestInterface testInterface = rpcClient.getProxy(TestInterface.class);
        System.out.println(testInterface.say("world"));
    }
}
