package demo;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author miaoshuo
 * @date 2020-10-05 22:45
 */
public class Main {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        Future<Integer> future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(5);
                System.out.println("执行完毕");
                return 1;
            }
        });

        try {
            Integer i = future.get(1, TimeUnit.SECONDS);
            System.out.println("i:"+i);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }
}
