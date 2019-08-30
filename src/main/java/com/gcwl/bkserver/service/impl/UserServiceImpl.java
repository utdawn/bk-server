package com.gcwl.bkserver.service.impl;

import com.gcwl.bkserver.entity.Permission;
import com.gcwl.bkserver.entity.Role;
import com.gcwl.bkserver.entity.User;
import com.gcwl.bkserver.mapper.UserMapper;
import com.gcwl.bkserver.service.UserService;
import com.gcwl.bkserver.util.Result;
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
        if(null == userName)
            return null;
        User user = userMapper.getUserByUserName(userName);
        Set<Role> roleList = userMapper.getUserRoleByUserName(userName);
        for (Role role:roleList){
            Set<String> permissionList = userMapper.getRolePermissionByRoleCode(role.getRoleCode());
            role.setPermissions(permissionList);
        }
        user.setRoles(roleList);
        return user;
    }

    @Override
    public Result register(User user) {
        int rs = 0;
        rs = userMapper.register(user);
        if(rs == 0)
            return Result.error("注册失败，请重试");
        userMapper.addRole(user.getUserName(), "r0002");
        return Result.success("注册成功");
    }

    @Override
    public List<User> getCommonUserList() {
        return userMapper.getCommonUserList();
    }

    @Override
    public int deleteUser(String userName) {
        return userMapper.deleteUser(userName);
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
