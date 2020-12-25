package com.zwj.proxy;

/**
 * 代理模式(静态)
 * @author zwj
 * @date 2020-11-15
 */
public class ProxyTest {
    public static void main(String[] args) {
        Proxy proxy = new Proxy();
        proxy.request();
    }
}

/**
 * 抽象主题
 */
interface Subject{
    void request();
}
/**
 * 具体主题
 */
class RealSubject implements Subject{

    @Override
    public void request() {
        System.out.println("具体主题。。。。。！");
    }
}

/**
 * 代理类
 */
class Proxy implements Subject{
private RealSubject realSubject;
    @Override
    public void request() {
        if(realSubject==null){
            realSubject=new RealSubject();
        }
        frontRequest();
        realSubject.request();
        afterRequest();
    }
    void frontRequest(){
        System.out.println("前置的方法！");
    }
    void afterRequest(){
        System.out.println("后置的方法！");
    }
}
