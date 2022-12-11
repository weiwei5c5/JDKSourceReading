package test.MyThreadPool;

public interface RejectPolicy {
    void reject(Runnable task, MyThreadPoolExecutor myThreadPoolExecutor);
}

