package test.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName atomicTest.java
 * @Description TODO
 * @createTime 2021年09月24日 19:26:00
 */
public class AtomicTest {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(14);
        atomicInteger.incrementAndGet();
        atomicInteger.getAndIncrement();
        atomicInteger.compareAndSet(3, 666);
        System.out.println(atomicInteger.get());
    }
}
