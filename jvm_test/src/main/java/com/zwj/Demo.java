package com.zwj;

import java.util.ArrayList;
import java.util.List;

public class Demo {
    Byte[] b1=new Byte[1024*128];
    public static void main(String[] args) {
        //lookJconsoleChange();
        test1();
    }

    /**内存溢出设置值:-Xms60m -Xmx60m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\zzyt\Desktop\error
     * 测试内存溢出
     */
    private static void testMemoryOut() {
        List<DemoEntity> list = new ArrayList<DemoEntity>();
        while (true) {
            list.add(new DemoEntity());
        }
    }

    /**
     * 观察jconsole内存，堆栈信息变化情况
     */
    private static void lookJconsoleChange() {
        try {
            Thread.sleep(5000);
            System.out.println("start...");
            List<Demo> list = new ArrayList<Demo>();
            for (int i = 0; i < 1000; i++) {
                Thread.sleep(100);
                list.add(new Demo());
            }
            System.out.println("end...");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test1(){
        String t1="abc";
        String t2="abc";
        System.out.println(t1==t2);
        String t3=new String("abc");
        // intern方法，将t3添加到运行时常量池
        System.out.println(t1==t3.intern());;
    }

}
