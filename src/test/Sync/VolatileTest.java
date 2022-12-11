package test.Sync;

/**
 * @author Chenkunwei
 * @version 1.0.0
 * @ClassName VolatileTest.java
 * @Description TODO
 * @createTime 2021年10月12日 21:35:00
 */
public class VolatileTest {
    /**
     * 针对finished变量，使用volatile修饰时这个程序可以正常结束，
     * 不使用volatile修饰时这个程序永远不会结束。
     */
     public static int finished = 0;
//    public static volatile int finished = 0;

    private static void checkFinished() {
        while (finished == 0) {
            // do nothing
        }
        System.out.println("finished");
    }

    private static void finish() {
        finished = 1;
    }

    public static void main(String[] args) throws InterruptedException {
        // 起一个线程检测是否结束
        new Thread(() -> checkFinished()).start();

        Thread.sleep(100);

        // 主线程将finished标志置为1
        finish();

        System.out.println("main finished");

    }
}