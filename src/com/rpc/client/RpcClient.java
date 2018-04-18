package com.rpc.client;


import com.rpc.io.meta.Auth;
import com.rpc.io.meta.RpcConnection;
import com.rpc.io.meta.Version;
import com.rpc.io.object.ObjectFactory;
import com.rpc.io.object.SerializableObjectFactory;

import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;


public class RpcClient {

    private String hostname;
    private int port;
    private int connectTimeout;
    private int soTimeout;
    private int readTimeout;

    private ObjectFactory objectFactory = new SerializableObjectFactory();

    private Version version = new Version();
    private Auth auth;



    protected RpcClient(){

    }


    public RpcClient(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getSoTimeout() {
        return soTimeout;
    }

    public void setSoTimeout(int soTimeout) {
        this.soTimeout = soTimeout;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public Auth getAuth() {
        return auth;
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
    }

    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> type){
        if(type == null){
            throw new NullPointerException("the type of proxy is null.");
        }
        if(!type.isInterface()){
            throw new IllegalArgumentException("not interface class:" + type);
        }
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{type},
                new RpcClientInvocationHandler(this,type)
        );
    }



    public RpcConnection openConnection() throws IOException{
        Socket socket = newSocket();
        RpcConnection connection =  new RpcConnection(socket);
        connection.setObjectFactory(objectFactory);
        return connection;
    }


    protected Socket newSocket() throws IOException{
        InetSocketAddress address = new InetSocketAddress(hostname,port);
        Socket socket = new Socket();
        if(readTimeout > 0){
            socket.connect(address);
        }else{
            socket.connect(address,readTimeout);
        }
        if(soTimeout > 0){
            socket.setSoTimeout(soTimeout);
        }
        return socket;
    }




}
