package com.java4all.netty.test.call;

/**
 * @author wangzhongxiang
 * @date 2019年10月29日 16:07:16
 */
public class CallTest {
    public static void main(String[]args){
        System.out.println("锚点1+"+Thread.currentThread().getName());
        Boolean aBoolean = printAndExecute(() -> {
            return true;
        });
        System.out.println("结果为："+aBoolean);

    }

    public static  <T>T printAndExecute(ACallable<T> aCallable){
        System.out.println("aaaaa+"+Thread.currentThread().getName());
        try {
            return aCallable.call();
        }finally {
            System.out.println("最终执行finally");
        }
    }

    private void test(){
        System.out.println("内部啊+"+Thread.currentThread().getName());
    }

}
