package com.java4all.netty.test.testschedule;

import java.time.LocalDateTime;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author IT云清
 * @date 2020年01月16日 19:20:54
 */
public class MySchedule {

    private static ScheduledThreadPoolExecutor poolExecutor =
            new ScheduledThreadPoolExecutor(2,new DefaultThreadFactory(null,"test"));

    public static void main(String[]args){
        System.out.println(String.join(":::",Thread.currentThread().getName(),
                LocalDateTime.now().toString()));

        poolExecutor.scheduleAtFixedRate(
                ()->System.out.println(
                        String.join(":::",Thread.currentThread().getName(),
                                LocalDateTime.now().toString())),
                                10,5,TimeUnit.SECONDS);;

    }
}
