package com.zwj.adapter;

/**
 * 类适配器模式---功能：定义一个访问接口，通过适配器名称访问多个适配器具体业务
 *
 * 该模式的主要优点如下。
 * 客户端通过适配器可以透明地调用目标接口。
 * 复用了现存的类，程序员不需要修改原有代码而重用现有的适配者类。
 * 将目标类和适配者类解耦，解决了目标类和适配者类接口不一致的问题。
 * 在很多业务场景中符合开闭原则。
 *
 * 其缺点是：
 * 适配器编写过程需要结合业务场景全面考虑，可能会增加系统的复杂性。
 * 增加代码阅读难度，降低代码可读性，过多使用适配器会使系统代码变得凌乱。
 *
 *
 * @author zwj
 * @date 2020-11-18
 */
public class AdapterTest {

    public static void main(String[] args) {
        Adapter adapter = new Adapter();
        adapter.method1();
    }
}

/**
 *目标接口
 */
interface Target{
    public void method1();
}

/**
 *适配者
 */
class Matches{
    public void method2(){
        System.out.println("目标接口的方法被调用！");
    }
}
/**
 * 适配器
 */
class Adapter extends Matches implements Target{

    @Override
    public void method1() {
        method2();
    }
}