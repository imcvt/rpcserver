package com.gupaoedu.remoterpc;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RpcServerProxy {

    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    public void publisher(Object service, int port) {

        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(port);

            for (; ; ) {
                Socket socket = serverSocket.accept();

                cachedThreadPool.execute(new ProcessorHandler(socket, service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(serverSocket != null) {
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
