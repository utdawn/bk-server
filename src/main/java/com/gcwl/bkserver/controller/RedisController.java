package com.gcwl.bkserver.controller;

import com.gcwl.bkserver.service.JedisCache;
import com.gcwl.bkserver.util.Result;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api
@Controller
public class RedisController {

    @Autowired
    private JedisCache jedisCache;

    @GetMapping("test")
    @ResponseBody
    public Object test() {
//        jedisCache.setObject("object", 111);
//        return jedisCache.getObject("object");
        return null;
    }

    @GetMapping("haha")
    @ResponseBody
    public Object haha() {
        return Result.error("gg");
    }

}
