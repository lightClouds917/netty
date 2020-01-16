package com.java4all.netty.test.testschedule;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author IT云清
 * @date 2020年01月16日 19:51:49
 */
public class DefaultThreadFactory implements ThreadFactory {

    private static final AtomicInteger poolNumber = new AtomicInteger(1);
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);
    private final String prefix;

    public DefaultThreadFactory() {
        SecurityManager manager = System.getSecurityManager();
        group = (manager != null) ? manager.getThreadGroup():
                Thread.currentThread().getThreadGroup();
        prefix = String.join("-","pool",poolNumber.getAndIncrement()+"","thread")+"-";
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(group, r, prefix + threadNumber.getAndIncrement(), 0);
        if(thread.isDaemon()){
            thread.setDaemon(false);
        }
        if(thread.getPriority() != Thread.NORM_PRIORITY){
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
