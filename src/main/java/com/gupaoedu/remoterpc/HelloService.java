package com.gupaoedu.remoterpc;

public class HelloService implements IHelloService{

    public String sayHello(String content) {
        return "hello world:" + content;
    }

    public String saveUser(User user) {
        System.out.println("user==>" + user);
        return "success";
    }
}
