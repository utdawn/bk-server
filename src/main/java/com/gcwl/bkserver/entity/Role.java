package com.gcwl.bkserver.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Role implements Serializable {

    //定义一个序列号
    private static final long serialVersionUID = 1L;

    private int rid;
    private String roleCode;
    private String roleName;
    private List<User> users;
    private Set<String> permissions;

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
