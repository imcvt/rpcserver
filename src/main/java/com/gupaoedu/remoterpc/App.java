package com.gupaoedu.remoterpc;

public class App {

    public static void main(String[] args) {
        IHelloService helloService = new HelloService();

        RpcServerProxy rpcServerProxy = new RpcServerProxy();

        rpcServerProxy.publisher(helloService, 8080);
    }
}
