package test.aqs;

import java.util.concurrent.CountDownLatch;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName CountDownLatchTest.java
 * @Description
 * @createTime 2022年07月20日 21:41:00
 */
public class CountDownLatchTest {

    public static void main(String[] args) throws Exception{
        CountDownLatch startSignal = new CountDownLatch(1);

        CountDownLatch doneSignal = new CountDownLatch(5);

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                try {
                    System.out.println("Aid thread is waiting for starting.");
                    startSignal.await();
                    // do sth
                    System.out.println("Aid thread is doing something.");
                    doneSignal.countDown();
                }catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
        // main thread do sth
        Thread.sleep(2000);
        System.out.println("Main thread is doing something.");
        startSignal.countDown();

        // main thread do sth else
        System.out.println("Main thread is waiting for aid threads finishing.");
        doneSignal.await();

        System.out.println("Main thread is doing something after all threads have finished.");
    }
}
