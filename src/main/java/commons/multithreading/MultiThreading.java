package commons.multithreading;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultiThreading {

    private ExecutorService executorService;

    public void enableMultiThreading() {

        executorService = Executors.newFixedThreadPool(1);
    }

    public void disableMultiThreading() {

        if (executorService != null) {
            executorService.shutdown();
        }
    }

    public ExecutorService getExecutorService() {
        return executorService;
    }
}
