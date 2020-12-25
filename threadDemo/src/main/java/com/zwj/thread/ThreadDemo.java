package com.zwj.thread;

/**
 * 多线程练习
 *
 * @author zwj
 * @date 2020-10-15
 */
public class ThreadDemo {
    /**
     * 模拟多线程枪火票
     *
     * @param args
     */
    public static void main(String[] args) {
        Thread thread = new Thread(new TrainThrea(), "线程1");
        Thread thread1 = new Thread(new TrainThrea(), "线程2");
        thread.start();
        thread1.start();
    }
}
