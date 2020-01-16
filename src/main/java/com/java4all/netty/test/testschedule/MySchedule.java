package com.java4all.netty.test.testschedule;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

/**
 * @author IT云清
 * @date 2020年01月16日 19:20:54
 */
public class MySchedule {

    private static ScheduledThreadPoolExecutor poolExecutor =
            new ScheduledThreadPoolExecutor(1);

    public static void main(String[]args){
        System.out.println(LocalDateTime.now().toString());
        poolExecutor.scheduleAtFixedRate(()->System.out.println(LocalDateTime.now().toString()),30,10,
                TimeUnit.SECONDS);
        ThreadFactory threadFactory = new ThreadFactory();
        threadFactory.

    }
}
