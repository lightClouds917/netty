package com.java4all.netty.test.testlist;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 验证一个全局的list，一个线程在移除元素时，另外一个线程在增加元素，会不会出现问题
 * 移除指定元素，不会出现问题
 * @author IT云清
 * @date 2019年10月29日 19:34:23
 */
@RestController
public class TestList {

    private static List<String> list = new ArrayList<>();

    @GetMapping("add")
    public String add(String var) {
        for(int i = 0;i < 100;i ++){
            list.add("list"+i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("当前list大小："+list.size()+"当前添加元素为:list"+i);
        }
        return var;
    }

    @GetMapping("remove")
    public void remove(String var){
        list.forEach(s -> System.out.println(s));
        list.remove(var);
        list.forEach(s -> System.out.println(s));
    }

}
