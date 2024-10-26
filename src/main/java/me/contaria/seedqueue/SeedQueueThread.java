package me.contaria.seedqueue;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeedQueueThread {

    // Define a thread pool with a fixed number of threads (adjust size based on need)
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private volatile boolean isRunning = false;

    // Starts processing the queue at a fixed interval
    public void startQueueProcessing() {
        if (!isRunning) {
            isRunning = true;
            executorService.scheduleAtFixedRate(this::processQueue, 0, 1, TimeUnit.SECONDS);
        }
    }

    // Stops the queue processing and shuts down the thread pool
    public void stopQueueProcessing() {
        isRunning = false;
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

    // Method containing queue processing logic
    private void processQueue() {
        if (isRunning) {
            // Add the actual queue processing logic here, e.g., dequeuing items and handling them.
        }
    }
}
