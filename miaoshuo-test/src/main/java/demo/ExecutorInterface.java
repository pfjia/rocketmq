package demo;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author miaoshuo
 * @date 2020-10-04 20:08
 */
public interface ExecutorInterface {

    <T> Future<T> submit(Callable<T> callable);
}
