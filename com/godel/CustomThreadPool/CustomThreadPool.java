package com.godel.CustomThreadPool;

import java.util.LinkedList;
import java.util.List;

public class CustomThreadPool {
    private final List<WorkerThread> threads;
    private final List<Runnable> taskQueue;

    public CustomThreadPool(int threadCount) {
        threads = new LinkedList<>();
        taskQueue = new LinkedList<>();

        for (int i = 0; i < threadCount; i++) {
            WorkerThread thread = new WorkerThread();
            thread.start();
            threads.add(thread);
        }
    }

    public void submit(Runnable task) {
        synchronized (taskQueue) {
            taskQueue.add(task);
            taskQueue.notify();
        }
    }

    private class WorkerThread extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (taskQueue) {
                    while (taskQueue.isEmpty()) {
                        try {
                            taskQueue.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    task = taskQueue.remove(0);
                }
                try {
                    task.run();
                } catch (RuntimeException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

