package com.java4all.netty.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author wangzhongxiang
 * @date 2019年10月11日 17:54:15
 */
@RestController
@RequestMapping("aController")
public class AController {

    ThreadLocal threadLocal = new ThreadLocal();

    @GetMapping("a")
    public String a(){
        threadLocal.set(1111111);
        System.out.println("AController#a 线程名称="+Thread.currentThread().getName()+"threadLocal value="+threadLocal.get());

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("AController#a 中其他线程 线程名称="+Thread.currentThread().getName()+"threadLocal value="+threadLocal.get());
            }
        }).start();
        return "aaa";
    }
}
