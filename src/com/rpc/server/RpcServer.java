package com.rpc.server;

import com.rpc.io.meta.*;
import com.rpc.io.object.ObjectFactory;
import com.rpc.io.object.SerializableObjectFactory;

import java.io.Closeable;
import java.io.IOException;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class RpcServer extends Thread implements Closeable{


    private int port;
    private ServerSocket serverSocket;
    private Authorizer authorizer;
    private ServerHandler serverHandler = new DefaultHandler();

    private Version version = new Version();
    private Map<Class,Object> objectMap = new HashMap<Class, Object>();

    private ObjectFactory objectFactory = new SerializableObjectFactory();

    private boolean running = true;

    public RpcServer(int port) throws IOException {
        this.port = port;
        this.serverSocket = new ServerSocket(port);
        this.serverHandler.setServer(this);
    }

    public RpcServer(ServerSocket serverSocket){
        this.serverSocket = serverSocket;
        this.serverHandler.setServer(this);
    }

    public void setServerHandler(ServerHandler serverHandler) {
        this.serverHandler = serverHandler;
        this.serverHandler.setServer(this);
    }

    public ServerHandler getServerHandler() {
        return serverHandler;
    }

    public ObjectFactory getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory objectFactory) {
        this.objectFactory = objectFactory;
    }

    public int getPort() {
        return port;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public <T> void addInvocation(Class<T> type,T target){
        if(type == null){
            throw new NullPointerException("the type of invocation is null.");
        }
        if(target == null){
            throw new NullPointerException("the target of invocation is null.");
        }
        if(!type.isInterface()){
            throw new IllegalArgumentException("not interface class :" + type.getName());
        }
        objectMap.put(type,target);
    }

    public Authorizer getAuthorizer() {
        return authorizer;
    }

    public void setAuthorizer(Authorizer authorizer) {
        this.authorizer = authorizer;
    }


    public Version getVersion() {
        return version;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    @Override
    public void run() {
        while (running){
            Socket socket = null;
            try{
                socket = serverSocket.accept();
                RpcConnection connection = open(socket);
                Version clientVersion = connection.readObject(Version.class);
                InvocationInfo invocationInfo = connection.readObject(InvocationInfo.class);
                Auth auth = connection.readObject(Auth.class);
                ResultInfo resultInfo = new ResultInfo();
                serverHandler.handle(resultInfo,connection,clientVersion,invocationInfo,auth);
                connection.writeObject(resultInfo);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(socket != null){
                    try {
                        socket.close();
                    } catch (IOException e) {

                    }
                }
            }
        }
    }

    protected RpcConnection open(Socket socket) throws IOException{
        RpcConnection connection = new RpcConnection(socket);
        connection.setObjectFactory(objectFactory);
        return connection;
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }

    public Map<Class, Object> getObjectMap() {
        return objectMap;
    }

    @Override
    public void close() throws IOException {
        this.running = false;
        if(serverSocket != null){
            serverSocket.close();
        }
    }
}
