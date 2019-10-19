package com.java4all.netty.test.txtest;

import java.util.concurrent.Callable;

/**
 * @author wangzhongxiang
 * @date 2019年10月14日 20:41:21
 */
public class ConnectionProxy {

    public static class LockRetryPolicy{
        protected final static boolean LOCK_RETRY = false;

        public <T> T excute(Callable<T> callable) throws Exception {
            System.out.println("bbbbbb:"+Thread.currentThread().getName());
            if(LOCK_RETRY){
                System.out.println("callable.call()");
                return callable.call();
            }else {
                return doRetryOnLockConfict(callable);
            }
        }

        protected <T> T doRetryOnLockConfict(Callable<T> callable) throws Exception {
            while (true){
                System.out.println("cccccc:"+Thread.currentThread().getName());
                System.out.println("doRetryOnLockConfict(callable) callable.call()");
                return callable.call();
            }
        }
    }
}
