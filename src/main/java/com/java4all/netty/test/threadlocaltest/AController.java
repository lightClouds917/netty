package com.java4all.netty.test.threadlocaltest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author IT云清
 * @date 2019年10月11日 17:54:15
 */
@RestController
@RequestMapping("aController")
public class AController {

    @Autowired
    private AService aService;

    //static is good
    static ThreadLocal threadLocal = new ThreadLocal();

    @GetMapping("a")
    public String a(){
        threadLocal.set(1111111);
        System.out.println("AController#a 线程名称="+Thread.currentThread().getName()+"，threadLocal value="+threadLocal.get());

        aService.a();

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("AController#a 中其他线程 线程名称="+Thread.currentThread().getName()+"，threadLocal value="+threadLocal.get());
                //跨线程了，即使静态也无法使用。但是跨类可以。
                System.out.println("AController#a 中其他线程 线程名称="+Thread.currentThread().getName()+"，static threadLocal value="+AController.threadLocal.get());
            }
        }).start();
        return "aaa";
    }
}
