package com.zwj.factory.Abstract;


/**
 * 抽象工厂实例
 *
 * @author zwj
 */
public class LivingExample {

    public static void main(String[] args) {
        try {
            Class<?> aClass = Class.forName("com.zwj.factory.Abstract." + "ConcreteFactoryAnimal");
            Object o = aClass.newInstance();
            AbstractFactory a= (AbstractFactory) o;
            Animal b=a.newAnimal();
            b.show();
            Botany botany = a.nwwBotany();
            botany.show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

}

/**
 * 抽象产品：动物
 */
interface Animal {
    void show();
}

/**
 * 抽象产品：植物
 */
interface Botany {
    void show();
}

/**
 * 具体产品：马
 */
class Horse implements Animal {
    public void show() {
        System.out.println("这是一匹马！");
    }
}

/**
 * 具体产品：水果
 */
class Fruitage implements Botany {
    public void show() {
        System.out.println("这是一水果！");
    }
}

/**
 * 抽象工厂:动物
 */
interface AbstractFactory {
    Animal newAnimal();

    Botany nwwBotany();
}

/**
 * 具体工厂
 */
class ConcreteFactoryAnimal implements AbstractFactory {
    public Animal newAnimal() {
        System.out.println("具体动物工厂-->一匹马！");
        return new Horse();
    }

    public Botany nwwBotany() {
        System.out.println("具体植物工厂-->一水果！");
        return new Fruitage();
    }
}
