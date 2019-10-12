package com.java4all.netty.test;

import org.springframework.stereotype.Service;

/**
 * @author wangzhongxiang
 * @date 2019年10月11日 20:04:23
 */
@Service
public class AServiceImpl implements AService{
    ThreadLocal threadLocal = new ThreadLocal();

    @Override
    public void a() {
        System.out.println("AServiceImpl#a 线程名称="+Thread.currentThread().getName()+"threadLocal value="+threadLocal.get());
        System.out.println("AServiceImpl#a 线程名称="+Thread.currentThread().getName()+"AController 中 static threadLocal value="+AController.threadLocal.get());

    }
}
