package com.java4all.netty;

import com.java4all.netty.test.txtest.LockRetryPolicy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author wangzhongxiang
 * @date 2019年10月19日 09:50:45
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestCallable {

    @Test
    public void test() throws Exception {
        new LockRetryPolicy("wangwang").excute(()->{
            String commit = commit();
            System.out.println("aaaaa:"+Thread.currentThread().getName());
            return commit;
        });
    }

    public String commit(){
        System.out.println("YES");
        return "YES";
    }
}
