package demo;

import java.util.Objects;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import lombok.Data;

/**
 * @author miaoshuo
 * @date 2020-10-04 19:34
 */
@Data
public class FutureImpl<V> implements Future<V> {

    private V result;
    private CountDownLatch countDownLatch = new CountDownLatch(1);

    public void setResult(Object result) {
        this.result = (V)result;
        countDownLatch.countDown();
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isCancelled() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDone() {
        return Objects.equals(0, countDownLatch.getCount());
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        countDownLatch.wait();
        return result;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        long timeoutMillis = unit.toMillis(timeout);
        countDownLatch.wait(timeoutMillis);
        return result;
    }
}
