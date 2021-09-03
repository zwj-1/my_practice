package com.zwj.singleton;

import javax.swing.*;
import java.awt.*;

public class TestCode {
    public static void main(String[] args) {
        LazySingleton instance = LazySingleton.getInstance();
        System.out.println("饿汉模式加载(用时再实例化)：" + instance);
        System.out.println("饱汉模式加载(类装载时已实例化)：" + HungrySingleton.getInstance());

        {
            // 框架窗体 JFrame 组件
            JFrame jf = new JFrame("饿汉单例模式测试");
            jf.setLayout(new GridLayout(1, 2));
            Container contentPane = jf.getContentPane();
            Bajie obj1 = Bajie.getInstance();
            contentPane.add(obj1);
            Bajie obj2 = Bajie.getInstance();
            contentPane.add(obj2);
            if (obj1 == obj2) {
                System.out.println("他们是同一人！");
            } else {
                System.out.println("他们不是同一人！");
            }
            jf.pack();
            jf.setVisible(true);
            jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
}
class Bajie extends JPanel {
    private static Bajie instance = new Bajie();

    private Bajie() {
        JLabel l1 = new JLabel(new ImageIcon("src/Bajie.jpg"));
        this.add(l1);
    }

    public static Bajie getInstance() {
        return instance;
    }
}
