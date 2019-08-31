package com.gcwl.bkserver;

import com.gcwl.bkserver.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BkServerApplicationTests {

    @Test
    public void contextLoads() throws Exception {
//        UserServiceImpl userServiceImpl = new UserServiceImpl();
        int count = 500;
//        for(int i = 0; i < count; i++){
//            userServiceImpl.deleteUser("user" + i);
//        }
//        System.out.println("over");
        List<User> users = new ArrayList<>();
        // 生成用户
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword(new SimpleHash("MD5", "123",
                    ByteSource.Util.bytes("user" + i + "salt"), 2).toHex());
            user.setTel("123");
            user.setAddress("123123");
            user.setRealName("user" + i);
//            userServiceImpl.register(user);
            users.add(user);
        }
        System.out.println("create user over");

        // 登录，生成token
        String urlString = "http://localhost:8099/login";
        File file = new File("D:/tokens.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            URL url = new URL(urlString);
            HttpURLConnection co = (HttpURLConnection) url.openConnection();
            co.setRequestMethod("POST");
            co.setDoOutput(true);

            OutputStream out = co.getOutputStream();
            String params = "userName=" + user.getUserName() + "&password=123";
            out.write(params.getBytes());
            out.flush();
            InputStream inputStream = co.getInputStream();
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            byte buff[] = new byte[1024];
            int len = 0;
            while ((len = inputStream.read(buff)) >= 0) {
                bout.write(buff, 0, len);
            }
            inputStream.close();
            bout.close();
            String response = new String(bout.toByteArray());
            JSONObject jo = JSON.parseObject(response);
            String token = jo.getString("data");
            System.out.println("create token : " + user.getUserName());

            String row = user.getUserName() + "," + token;
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getUserName());
        }
        raf.close();

        System.out.println("over");
    }

    @Test
    public void loginTest() throws Exception {
        int count = 5000;
        List<User> users = new ArrayList<>();
        // 生成用户
        for (int i = 0; i < count; i++) {
            User user = new User();
            user.setUserName("user" + i);
            user.setPassword(new SimpleHash("MD5", "123",
                    ByteSource.Util.bytes("user" + i + "salt"), 2).toHex());
            users.add(user);
        }
        System.out.println("create user over");

        File file = new File("D:/u_p.txt");
        if (file.exists()) {
            file.delete();
        }
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        file.createNewFile();
        raf.seek(0);
        for (int i = 0; i < users.size(); i++) {
            User user = users.get(i);
            String row = user.getUserName() + "," + user.getPassword();
            raf.seek(raf.length());
            raf.write(row.getBytes());
            raf.write("\r\n".getBytes());
            System.out.println("write to file : " + user.getUserName());
        }
        raf.close();

        System.out.println("over");
    }

}
