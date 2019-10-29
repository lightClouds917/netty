package com.java4all.netty.test.call;

/**
 * @author wangzhongxiang
 * @date 2019年10月29日 16:04:58
 */
@FunctionalInterface
public interface ACallable<T> {

    T call();
}
