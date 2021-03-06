# 简单的RPC框架

简易RPC,利用Java序列化机制实现

[下载](https://github.com/xcr1234/rpc/blob/master/rpc.jar?raw=true)

## 实例

接口：

```java
public interface TestInterface {
    String say(String s);
}
```

服务端：


```java
public class TestInterfaceImpl implements TestInterface {
    @Override
    public String say(String s) {
        //throw new RuntimeException("hello " + s);
        return   "hello " + s;
    }
}
```

```java
public class TestServer {
    public static void main(String[] args) throws Exception{
        
        RpcServer server = new RpcServer(8100);
        server.addInvocation(TestInterface.class,new TestInterfaceImpl());
        server.start();
    }
}
```

客户端：


```java
public class TestClient {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient("127.0.0.1",8100);
        TestInterface testInterface = rpcClient.getProxy(TestInterface.class);
        System.out.println(testInterface.say("world"));
    }
}
```

## 认证

```java
server.setAuthorizer(new Authorizer() {
            @Override
            public boolean isValidAuth(Auth auth) throws Throwable {
                BaseAuth baseAuth = (BaseAuth)auth;
                return "admin".equals(baseAuth.getUsername()) && "admin".equals(baseAuth.getPassword());
            }
});
```

```java
rpcClient.setAuth(new BaseAuth("admin","admin"));
```

## Kryo支持

java默认的序列化性能很低，因此引入了kryo序列化，以提高性能

引入kryo的jar包：

服务端

```java
server.setObjectFactory(new KryoObjectFactory());
```

客户端

```java
rpcClient.setObjectFactory(new KryoObjectFactory());
```

