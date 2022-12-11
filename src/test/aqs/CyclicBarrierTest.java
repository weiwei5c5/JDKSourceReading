package test.aqs;

import java.util.concurrent.CyclicBarrier;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName CyclicBarrierTest.java
 * @Description
 * @createTime 2022年08月30日 10:47:00
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3);

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                System.out.println("before");
                try {
                    cyclicBarrier.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
                System.out.println("after");
            }).start();
        }
    }
}
