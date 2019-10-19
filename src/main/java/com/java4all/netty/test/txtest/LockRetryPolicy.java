package com.java4all.netty.test.txtest;

import java.util.concurrent.Callable;

/**
 * @author wangzhongxiang
 * @date 2019年10月19日 09:52:53
 */
public class LockRetryPolicy extends ConnectionProxy.LockRetryPolicy{

    private  String name = "";

    public LockRetryPolicy(String name) {
        this.name = name;
    }


    @Override
    public <T> T excute(Callable<T> callable) throws Exception {
        return super.excute(callable);
    }

}
