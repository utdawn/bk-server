package com.gcwl.bkserver.controller;

import com.gcwl.bkserver.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class RedisController {

    @Autowired
    private RedisService redisService;

    @GetMapping("test")
    @ResponseBody
    public Object test() {
        redisService.set("object", 111);
        return redisService.get("object");
    }

}