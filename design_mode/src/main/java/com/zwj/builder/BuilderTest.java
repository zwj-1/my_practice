package com.zwj.builder;


/**
 * 建造者模式
 *
 * @author zwj
 * @date 2020-11-13
 */
public class BuilderTest {
    public static void main(String[] args) {
        ConcreteBuilder builder = new ConcreteBuilder();
        Director director = new Director(builder);
        Product product = director.construct();
        product.show();
    }
}

/**
 * 产品
 */
class Product {
    private String partA;
    private String partB;
    private String partC;

    public void setPartA(String partA) {
        this.partA = partA;
    }

    public void setPartB(String partB) {
        this.partB = partB;
    }

    public void setPartC(String partC) {
        this.partC = partC;
    }

    public void show() {
        System.out.println("产品------！");
    }
}

/**
 * 抽象建造者
 */
abstract class AbstractBuilder {
    // 创建产品对象
    protected Product product = new Product();

    public abstract void builderPartA();

    public abstract void builderPartB();

    public abstract void builderPartC();

    public Product getResult() {
        return product;
    }
}

/**
 * 具体建造者
 */
class ConcreteBuilder extends AbstractBuilder {

    @Override
    public void builderPartA() {
        product.setPartA("建造者A");
    }

    @Override
    public void builderPartB() {
        product.setPartA("建造者B");
    }

    @Override
    public void builderPartC() {
        product.setPartA("建造者C");
    }
}

/**
 * 指挥者
 */
class Director {
    private AbstractBuilder builder;

    public Director(AbstractBuilder builder) {
        this.builder = builder;
    }

    public Product construct() {
        builder.builderPartA();
        builder.builderPartB();
        builder.builderPartC();
        return builder.getResult();
    }
}
