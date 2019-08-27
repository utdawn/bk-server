package com.gcwl.bkserver.service;

import com.gcwl.bkserver.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public String getPwdByUserName(String userName);

    public User getUserByUserName(String userName);

//    public Map<Object,Object> checkLogin(String userName, String password);
}
