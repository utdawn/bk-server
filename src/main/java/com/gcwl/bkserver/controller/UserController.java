package com.gcwl.bkserver.controller;

import com.gcwl.bkserver.entity.User;
import com.gcwl.bkserver.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import com.gcwl.bkserver.util.Result;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api
@Controller
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    @ResponseBody
    public String defaultLogin() {
        return "首页";
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public Result login(String userName, String password) {
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        // 执行认证登陆
        try {
            subject.login(token);
        } catch (UnknownAccountException uae) {
            return Result.error("未知账户");
        } catch (IncorrectCredentialsException ice) {
            return Result.error("密码不正确");
        } catch (LockedAccountException lae) {
            return Result.error("账户已锁定");
        } catch (ExcessiveAttemptsException eae) {
            return Result.error("用户名或密码错误次数过多");
        } catch (AuthenticationException ae) {
            return Result.error("用户名或密码不正确！");
        }
        if (subject.isAuthenticated()) {
            return Result.success("登录成功");
        } else {
            token.clear();
            return Result.error("登录失败");
        }
    }

    @PostMapping("/logout")
    @ResponseBody
    public Result logout(){
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return Result.success("登出成功");
    }

    @PostMapping("/register")
    @ResponseBody
    public Result register(User user){
        if(null != userServiceImpl.getPwdByUserName(user.getUserName())){
            return Result.error("用户名已存在");
        }
        //将密码进行2次MD5加密后，存入数据库
        String md5Pwd = new SimpleHash("MD5", user.getPassword(),
                ByteSource.Util.bytes(user.getUserName() + "salt"), 2).toHex();
        user.setPassword(md5Pwd);
        return userServiceImpl.register(user);
    }

    /**
     * 获取用户信息，包含角色、权限
     * logical= Logical.OR： 可以任一拥有权限
     * logical= Logical.AND：必须同时拥有权限
     * @param userName
     * @return
     */
    @RequiresPermissions(value={"user:list","user:add"}, logical = Logical.AND)
    @RequestMapping("/getUserByUserName")
    @ResponseBody
    public User getUserByUserName(String userName){
        return userServiceImpl.getUserByUserName(userName);
    }

    /**
     * 仅获取用户信息，不包含角色、权限
     * @return
     */
    @RequiresRoles(value={"r0001"})
    @PostMapping("/userList")
    @ResponseBody
    public List<User> getCommonUserList(){
        return userServiceImpl.getCommonUserList();
    }
}
