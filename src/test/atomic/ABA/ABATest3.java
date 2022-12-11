package test.atomic.ABA;

import java.util.concurrent.atomic.AtomicStampedReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName ABATest3.java
 * @Description TODO
 * @createTime 2021年09月29日 22:28:00
 */
public class ABATest3 {

    public static void main(String[] args) {
        testStamp();
    }

    private static void testStamp() {
        //维护一个pair<元素值，版本号>
        AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(1, 1);

        new Thread(()->{
            int[] stampHolder = new int[1];
            int value = atomicStampedReference.get(stampHolder);
            int stamp = stampHolder[0];
            System.out.println("thread 1 read value: " + value + ", stamp: " + stamp);

            // 阻塞1s
            LockSupport.parkNanos(1000000000L);
            System.out.println(atomicStampedReference.getStamp()+" "+stamp);
            if (atomicStampedReference.compareAndSet(value, 3, stamp, stamp + 1)) {
                System.out.println("thread 1 update from " + value + " to 3");
            } else {
                System.out.println("thread 1 update fail!");
            }
        }).start();

        new Thread(()->{
            int[] stampHolder = new int[1];
            int value = atomicStampedReference.get(stampHolder);
            int stamp = stampHolder[0];
            System.out.println("thread 2 read value: " + value + ", stamp: " + stamp);
            if (atomicStampedReference.compareAndSet(value, 2, stamp, stamp + 1)) {
                System.out.println("thread 2 update from " + value + " to 2 ");

                // do sth

                value = atomicStampedReference.get(stampHolder);
                stamp = stampHolder[0];
                System.out.println("thread 2 read value: " + value + ", stamp: " + stamp);
                if (atomicStampedReference.compareAndSet(value, 1, stamp, stamp + 1)) {
                    System.out.println("thread 2 update from " + value + " to 1");
                }
            }
        }).start();
    }
}
