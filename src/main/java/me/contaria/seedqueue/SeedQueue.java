package me.contaria.seedqueue;

import java.util.concurrent.LinkedBlockingQueue;

public class SeedQueue {

    // Thread-safe queue for storing entries
    private final LinkedBlockingQueue<SeedQueueEntry> queue = new LinkedBlockingQueue<>();

    // Adds an entry to the queue
    public void enqueue(SeedQueueEntry entry) {
        queue.offer(entry); // Adds entry if space is available, otherwise returns false
    }

    // Retrieves and removes the head of the queue, or returns null if empty
    public SeedQueueEntry dequeue() {
        return queue.poll();
    }

    // Checks if the queue is empty
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
