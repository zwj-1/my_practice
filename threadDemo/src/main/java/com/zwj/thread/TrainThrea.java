package com.zwj.thread;

public class TrainThrea implements Runnable {
    private static Integer trainTicketTotal = 100;

    public void run() {
        synchronized (this) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            while (trainTicketTotal > 0) {
                sale();
            }
        }
    }

    private void sale() {
        if (trainTicketTotal > 0) {
            System.out.println(Thread.currentThread().getName() + "出售第" + trainTicketTotal + "张火车票");
            trainTicketTotal--;
        }
    }
}
