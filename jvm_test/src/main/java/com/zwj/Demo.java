package com.zwj;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Demo {
    Byte[] b1 = new Byte[1024 * 128];

    public static void main(String[] args) {
        //lookJconsoleChange();
        //test1();
        fileTest();
    }

    /**
     * 内存溢出设置值:-Xms60m -Xmx60m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=C:\Users\zzyt\Desktop\error
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

    private static void test1() {
        String t1 = "abc";
        String t2 = "abc";
        System.out.println(t1 == t2);
        String t3 = new String("abc");
        // intern方法，将t3添加到运行时常量池
        System.out.println(t1 == t3.intern());
        ;
    }

    private static void fileTest() {
        String pathName = "http://10.150.1.14:9000/test/17/2021-01-07/1610010256430-工资条模板 (1) - 副本.xls";
        File file = new File(pathName);
        try {
            FileInputStream is = new FileInputStream(file);
            FileOutputStream out = new FileOutputStream("ceshi");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0){
                out.write(buffer,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(file.getName());
    }
}
