package com.zwj.thread;

/**
 * 一个线程输出ABCDEF，一个线程输出123456，输出结果A1B2C3..
 * @author zwj
 * @date 2021年08月31日
 */
public class Interactive {
    public static void main(String[] args) {
        final Object o = new Object();
        new Thread(){
            char[] str={'A','B','C','D','E','F'};

            @Override
            public void run() {
                synchronized (o){
                    for (char c : str) {
                        System.out.print(c);
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    o.notify();
                }
            }
        }.start();


        new Thread(){
            int[] num={1,2,3,4,5,6};

            @Override
            public void run() {
                synchronized (o){
                    for (int c : num) {
                        System.out.print(c);
                        try {
                            o.notify();
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }.start();
    }
}
