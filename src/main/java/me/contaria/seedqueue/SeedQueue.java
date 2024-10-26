package me.contaria.seedqueue;

import net.fabricmc.api.ClientModInitializer;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SeedQueue implements ClientModInitializer {

    // Queue thread management variables
    private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
    private volatile boolean isRunning = false;

    // Thread-safe queue for managing SeedQueue entries
    private final LinkedBlockingQueue<SeedQueueEntry> queue = new LinkedBlockingQueue<>();

    @Override
    public void onInitializeClient() {
        // Start queue processing when the mod initializes
        startQueueProcessing();
    }

    // Starts the queue processing with a scheduled task
    public void startQueueProcessing() {
        if (!isRunning) {
            isRunning = true;
            executorService.scheduleAtFixedRate(this::processQueue, 0, 1, TimeUnit.SECONDS);
        }
    }

    // Stops queue processing and shuts down the thread pool
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

    // Method to process the queue entries
    private void processQueue() {
        if (isRunning) {
            SeedQueueEntry entry = queue.poll(); // Retrieves and removes the head of the queue, or null if empty
            if (entry != null) {
                // Process the SeedQueueEntry here
            }
        }
    }

    // Adds an entry to the queue
    public void enqueue(SeedQueueEntry entry) {
        queue.offer(entry); // Adds the entry if space is available
    }

    // Ensures the thread pool shuts down safely when the mod is unloaded
    public void onShutdown() {
        stopQueueProcessing();
    }
}
