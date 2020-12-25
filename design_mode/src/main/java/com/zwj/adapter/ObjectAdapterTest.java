package com.zwj.adapter;

/**
 * 对象适配器模式
 *
 * @author zwj
 * @date 2020-11-18
 */
public class ObjectAdapterTest {
    public static void main(String[] args) {
        ObjectAdapter objectAdapter = new ObjectAdapter(new Adapter());
        objectAdapter.method1();
    }
}

/**
 * 对象适配器
 */
class ObjectAdapter implements Target {
    public ObjectAdapter(Adapter adapter) {
        this.adapter = adapter;
    }

    private Adapter adapter;

    @Override
    public void method1() {
        adapter.method2();
    }
}
