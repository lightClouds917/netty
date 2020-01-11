package com.java4all.netty.test.threadlocaltest;

import com.java4all.netty.test.threadlocaltest.AController;
import com.java4all.netty.test.threadlocaltest.AService;
import org.springframework.stereotype.Service;

/**
 * @author IT云清
 * @date 2019年10月11日 20:04:23
 */
@Service
public class AServiceImpl implements AService {
    ThreadLocal threadLocal = new ThreadLocal();

    @Override
    public void a() {
        System.out.println("AServiceImpl#a 线程名称="+Thread.currentThread().getName()+"，new threadLocal value="+threadLocal.get());
        //在一个类中使用另外一个类的theadLocal，目标类的threadLocal静态。
        //此时，这个threadLocal可以跨类使用，但是不能跨线程
        System.out.println("AServiceImpl#a 线程名称="+Thread.currentThread().getName()+"，AController 中 static threadLocal value="+ AController.threadLocal.get());

    }
}
