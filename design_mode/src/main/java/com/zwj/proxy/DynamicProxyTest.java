package com.zwj.proxy;

import jdk.nashorn.internal.objects.annotations.Getter;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 动态代理
 *
 * @author zwj
 */
public class DynamicProxyTest {
    public static void main(String[] args) {
//创建一个实例对象，这个对象是被代理的对象
        Person zhangsan = new Student("张三");

        //创建一个与代理对象相关联的InvocationHandler
        InvocationHandler stuHandler = new StuInvocationHandler<Person>(zhangsan);

        //创建一个代理对象stuProxy来代理zhangsan，代理对象的每个执行方法都会替换执行Invocation中的invoke方法
        Person stuProxy = (Person) java.lang.reflect.Proxy.newProxyInstance(Person.class.getClassLoader(), new Class<?>[]{Person.class}, stuHandler);

        //代理执行上交班费的方法
        stuProxy.giveMoney();
    }
}

interface Person {
    void giveMoney();
}

class Student implements Person {
    private String name;

    public Student(String name) {
        this.name = name;
    }

    @Override
    public void giveMoney() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(name + "上交班费50元！");
    }
}

class MonitorUtil {
    private static ThreadLocal<Long> t1 = new ThreadLocal();

    public static void start() {
        t1.set(System.currentTimeMillis());
    }

    public static void finish(String methodName) {
        Long finishTime = System.currentTimeMillis();
        System.out.println(methodName + "方法耗时" + (finishTime - t1.get()) + "ms");
    }
}

class StuInvocationHandler<T> implements InvocationHandler {
    T target;
    public StuInvocationHandler(T target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理执行" +method.getName() + "方法");
        MonitorUtil.start();
        Object result = method.invoke(target, args);
        MonitorUtil.finish(method.getName());
        return result;
    }
}

