package com.zwj.factory.common;

/**工厂方法-一个族类工厂
 * @author zwj
 * @date 2020-11-13
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        try {
            Class<?> concreteFactory1 = Class.forName("com.zwj.factory.common." + "ConcreteFactory2");
            Object o = concreteFactory1.newInstance();
            AbstractFactory a = (AbstractFactory) o;
            Product p = a.newProduct();
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
 * 抽象产品：提供了产品的接口
 */
interface Product {
    void show();
}

/**
 * 具体产品1
 */
class ConcreteProduct1 implements Product {
    public void show() {
        System.out.println("具体产品1");
    }
}

/**
 * 具体产品2
 */
class ConcreteProduct2 implements Product {
    public void show() {
        System.out.println("具体产品2");
    }
}

/**
 * 抽象工厂
 */
interface AbstractFactory {
    public Product newProduct();
}

/**
 * 具体工厂1
 */
class ConcreteFactory1 implements AbstractFactory {
    public Product newProduct() {
        System.out.println("具体工厂1->具体产品1");
        return new ConcreteProduct1();
    }
}

/**
 * 具体工厂2
 */
class ConcreteFactory2 implements AbstractFactory {
    public Product newProduct() {
        System.out.println("具体工厂2->具体产品2");
        return new ConcreteProduct2();
    }
}
