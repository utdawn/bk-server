package com.gcwl.bkserver;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BkServerApplicationTests {

    @Test
    public void contextLoads() {
        // 加密算法MD5
        // salt盐 username + salt
        // 迭代次数
        String username = "test";
        String pwd = "test";
        String md5Pwd = new SimpleHash("MD5", pwd,
                ByteSource.Util.bytes(username + "salt"), 2).toHex();
        System.out.println(md5Pwd);
    }

}
