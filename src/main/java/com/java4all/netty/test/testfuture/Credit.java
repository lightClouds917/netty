package com.java4all.netty.test.testfuture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor.AbortPolicy;
import java.util.concurrent.TimeUnit;

/**
 * @author wangzhongxiang
 * @date 2019年10月23日 13:32:40
 */
public class Credit {

    public static void main(String[]args) {
        List<FutureTask<Integer>> taskList = new ArrayList<>();
        ThreadPoolExecutor executor =
                new ThreadPoolExecutor(4, 10, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(2000), new AbortPolicy());

        for (int i = 0; i < 10; i++) {
            FutureTask<Integer> result;
//            Callable<Integer> callable = () -> compute();
//            FutureTask<Integer> futureTask1 = new FutureTask<>(callable);
            FutureTask<Integer> futureTask = new FutureTask<Integer>(() -> compute());
            executor.submit(futureTask);
            taskList.add(futureTask);
        }

        Integer total = 0;
        for(FutureTask<Integer> futureTask : taskList){
            try {
                total += futureTask.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        System.out.println(String.format("总结果= %s",total));

    }

    public static Integer compute(){
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        int re = new Random().nextInt(10);
        System.out.println(Thread.currentThread().getName() + "---" + re);
        return re;
    }



}

