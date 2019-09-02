package com.gcwl.bkserver.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class User implements Serializable {

    //定义一个序列号
    private static final long serialVersionUID = 1L;

    private int uid;
    private String userName;
    private String password;
    private String realName;
    private String tel;
    private String address;
    private String icoUrl;
    private Set<Role> roles;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIcoUrl() {
        return icoUrl;
    }

    public void setIcoUrl(String icoUrl) {
        this.icoUrl = icoUrl;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

//    @Override
//    public String toString() {
//        return new String("uid:"+uid+","+
//                "userName:"+userName+","+
//                "password:"+password+","+
//                "realName:"+realName+","+
//                "tel:"+tel+","+
//                "address:"+address);
//    }
}
