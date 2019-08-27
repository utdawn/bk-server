package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Permission;
import com.gcwl.bkserver.entity.Role;
import com.gcwl.bkserver.entity.User;
import com.gcwl.bkserver.mapper.UserMapper;
import com.gcwl.bkserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public String getPwdByUserName(String userName) {
        return userMapper.getPwdByUserName(userName);
    }

    @Override
    public User getUserByUserName(String userName) {
        User user = userMapper.getUserByUserName(userName);
        Set<Role> roleList = userMapper.getUserRoleByUserName(userName);
        for (Role role:roleList){
            Set<String> permissionList = userMapper.getRolePermissionByRoleCode(role.getRoleCode());
            role.setPermissions(permissionList);
        }
        user.setRoles(roleList);
        return user;
    }

//    @Override
//    public Map<Object, Object> checkLogin(String userName, String password) {
//        Map<Object, Object> map = new HashMap<>();
//        String massage = "";
//        String pwd = userMapper.getPwdByUserName(userName);
//        if(null == pwd){
//            massage = "用户不存在";
//        }else if (!pwd.equals(password)){
//            massage = "密码不正确";
//        }else{
//            massage = "密码正确";
//        }
//        map.put("massage",massage);
//        return map;
//    }
}
