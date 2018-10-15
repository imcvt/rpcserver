package com.gupaoedu.remoterpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

public class ProcessorHandler implements Runnable {

    private Socket socket;
    private Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    public void run() {
        System.out.println("开始处理用户请求--》");
        ObjectInputStream objectInputStream = null;

        try {
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            RPCRequest rpcRequest = (RPCRequest) objectInputStream.readObject();//java的反序列化

            Object result = invoke(rpcRequest);
            //ObejctOutputStream是封装流，得把socket.getOutputStream()作为参数传进去！
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(result);

            objectOutputStream.flush();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Object invoke(RPCRequest rpcRequest) {
        Object[] parameters = rpcRequest.getParameters();
        Class<?>[] types = new Class[parameters.length];

        for (int i = 0; i < types.length; i++) {
            types[i] = parameters[i].getClass();
        }
        try {
            //获取方法的时候需要传入方法名及所有的参数类型
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), types);
            return method.invoke(service, parameters);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }


        return null;
    }
}
