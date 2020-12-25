package com.zwj.bridge;

/**
 * 桥接模式
 * <p>
 * 桥接（Bridge）模式的定义如下：将抽象与实现分离，使它们可以独立变化。
 * 它是用组合关系代替继承关系来实现，从而降低了抽象和实现这两个可变维度的耦合度。
 * <p>
 * 通过上面的讲解，我们能很好的感觉到桥接模式遵循了里氏替换原则和依赖倒置原则，
 * 最终实现了开闭原则，对修改关闭，对扩展开放。这里将桥接模式的优缺点总结如下。
 * <p>
 * 桥接（Bridge）模式的优点是：
 * 抽象与实现分离，扩展能力强
 * 符合开闭原则
 * 符合合成复用原则
 * 其实现细节对客户透明
 * <p>
 * 缺点是：由于聚合关系建立在抽象层，要求开发者针对抽象化进行设计与编程，
 * 能正确地识别出系统中两个独立变化的维度，这增加了系统的理解与设计难度。
 *
 * @author zwj
 * @date 2020-11-18
 */
public class BridgeTest {
    public static void main(String[] args) {
        Color color = new Yellow();
        AbstractBag bag = new HandBag();
        bag.setColor(color);
        System.out.println(bag.getName());
    }
}

/**
 * 实现化角色：颜色
 */
interface Color {
    String getColor();
}

/**
 * 扩展实现化角色：黄色
 */
class Yellow implements Color {

    @Override
    public String getColor() {
        return "yellow";
    }
}

/**
 * 扩展实现化角色：红色
 */
class Red implements Color {

    @Override
    public String getColor() {
        return "red";
    }
}

/**
 * 实现化角色：包种类
 */
abstract class AbstractBag {
    protected Color color;

    public void setColor(Color color) {
        this.color = color;
    }

    abstract String getName();
}

/**
 * 扩展实现化角色：挎包
 */
class HandBag extends AbstractBag {

    @Override
    String getName() {
        return color.getColor() + "HandBag";
    }
}

/**
 * 扩展实现化角色：挎包
 */
class Wallet extends AbstractBag {

    @Override
    String getName() {
        return color.getColor() + "Wallet ";
    }
}