package com.zwj.factory.Abstract;



/**
 * 抽象工厂-多个族类
 *
 * @author zwj
 * @date 2020-11-13
 */
public class AbstractFactoryTest2 {
    public static void main(String[] args) {
        try {
            Class<?> concreteFactory2 = Class.forName("com.zwj.factory.Abstract." + "ConcreteFactory");
            Object o = concreteFactory2.newInstance();
            AbstractFactory1 a = (AbstractFactory1) o;
            Product1 p = a.newProduct1();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

/**
 * 抽象产品
 */
interface Product1 {
    void show();
}

interface Product2 {
    void show();
}

/**
 * 具体产品1
 */
class ConcreteProduct1 implements Product1 {
    public void show() {
        System.out.println("具体产品1");
    }
}

/**
 * 具体产品2
 */
class ConcreteProduct2 implements Product2 {
    public void show() {
        System.out.println("具体产品2");
    }
}

/**
 * 抽象工厂
 */
interface AbstractFactory1 {
    Product1 newProduct1();

    Product2 newProduct2();
}

/**
 * 具体工厂
 */
class ConcreteFactory implements AbstractFactory1 {

    public Product1 newProduct1() {
        System.out.println("具体工厂1-->具体产品1");
        return new ConcreteProduct1();
    }

    public Product2 newProduct2() {
        System.out.println("具体工1厂-->具体产品2");
        return new ConcreteProduct2();
    }
}