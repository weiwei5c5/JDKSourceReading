package test.atomic.ABA;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName ABATest.java
 * @Description TODO
 * @createTime 2021年09月29日 20:16:00
 */
public class ABATest {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(1);

        new Thread(()->{
            int value = atomicInteger.get();
            System.out.println("thread 1 read value: " + value);

            // 阻塞1s = 1000000000纳秒
            LockSupport.parkNanos(1000000000L);

            if (atomicInteger.compareAndSet(value, 3)) {
                System.out.println("thread 1 update from " + value + " to 3");
            } else {
                System.out.println("thread 1 update fail!");
            }
        }).start();

        new Thread(()->{
            int value = atomicInteger.get();
            System.out.println("thread 2 read value: " + value);
            if (atomicInteger.compareAndSet(value, 2)) {
                System.out.println("thread 2 update from " + value + " to 2");

                // do sth

                value = atomicInteger.get();
                System.out.println("thread 2 read value: " + value);
                if (atomicInteger.compareAndSet(value, 1)) {
                    System.out.println("thread 2 update from " + value + " to 1");
                }
            }
        }).start();
    }
}
