package demo;

import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import lombok.Data;

/**
 * @author miaoshuo
 * @date 2020-10-04 19:35
 */
public class Worker {
    private Thread thread;
    private AtomicBoolean closed = new AtomicBoolean(false);
    private BlockingQueue<Node<?>> queue = new LinkedBlockingQueue<>();

    public Worker() {
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (!closed.get()) {
                        Node<?> node = queue.poll(1000, TimeUnit.MILLISECONDS);
                        if (Objects.isNull(node)) {
                            continue;
                        }

                        Callable<?> callable = node.getCallable();
                        Object o = callable.call();
                        FutureImpl<?> future = node.getFuture();
                        future.setResult(o);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        thread.setDaemon(true);
        thread.start();
    }

    public void shutdown() {
        closed.getAndSet(true);
    }

    public <T> Future<T> submit(Callable<T> callable) {
        //组装
        Node<T> node = new Node<>();
        FutureImpl<T> future = new FutureImpl<>();
        node.setFuture(future);
        node.setCallable(callable);

        // 设置任务
        queue.add(node);
        return future;
    }

    @Data
    public static class Node<T> {
        private FutureImpl<T> future;
        private Callable<T> callable;
    }
}
