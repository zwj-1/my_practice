package com.zwj.thread;

import com.zwj.entity.Chopsticks;
import com.zwj.entity.Philosopher;

/**
 * 5哲学家5根筷子吃饭问题
 *
 * @author zwj
 * @date 2021年08月30日
 */
public class PhilosopherThrea {
    public static void main(String[] args) {
        for (int i = 1; i <= 5; i++) {
            Chopsticks left=new Chopsticks();
            Chopsticks right=new Chopsticks();
            Threa1 t = new Threa1(left, right, i, new Philosopher());
            t.start();
        }
    }


    public static class Threa1 extends Thread {
        private Chopsticks left;
        private Chopsticks right;
        private int index;
        private Philosopher philosopher;

        public Threa1(Chopsticks left, Chopsticks right, int index, Philosopher philosopher) {
            this.left = left;
            this.right = right;
            this.index = index;
            this.philosopher = philosopher;
        }


        @Override
        public void run() {
            if (index % 2 == 0) {
                synchronized (left) {
                    System.out.println("第" + index + "个哲学家抢到左手边筷子");
                    try {
                        sleep(index);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    synchronized (right) {
                        try {
                            sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("第" + index + "个哲学家抢到右手边筷子");
                        System.out.println("第" + index + "个哲学家吃到饭");
                    }
                }
            } else {
                synchronized (right) {
                    System.out.println("第" + index + "个哲学家抢到右手边筷子");
                    synchronized (left) {
                        System.out.println("第" + index + "个哲学家抢到左手边筷子");
                        System.out.println("第" + index + "个哲学家吃到饭");
                    }
                }
            }
        }
    }
}

