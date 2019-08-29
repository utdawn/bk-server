package com.gcwl.bkserver.mapper;

import com.gcwl.bkserver.entity.Permission;
import com.gcwl.bkserver.entity.Role;
import com.gcwl.bkserver.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Set;

@Mapper
public interface UserMapper {

    @Select({"select password from user where userName=#{userName}"})
    public String getPwdByUserName(String userName);

    @Select({"select * from user"})
    public List<User> getCommonUserList();

    @Select({"select * from role"})
    public List<Role> getRoleList();

    @Select({"select * from permission"})
    public List<Permission> getPermissionList();

    @Select({"select * from user where username=#{userName}"})
    public User getUserByUserName(String userName);

    @Select({"select * from role where roleCode=#{roleCode}"})
    public Role getRoleByRoleCode(String roleCode);

    @Select({"select * from permission where permissionCode=#{permissionCode}"})
    public Permission getPermissionByPermissionCode(String permissionCode);

    @Select({"select r.rid, r.roleCode, r.roleName \n" +
            "from user_role ur \n" +
            "  left join user u on ur.userName=u.userName \n" +
            "  left join role r on ur.roleCode=r.roleCode \n" +
            "where ur.userName=#{userName}"})
    public Set<Role> getUserRoleByUserName(String userName);

    @Select({"select permissionCode from role_permission where roleCode=#{roleCode}"})
    public Set<String> getRolePermissionByRoleCode(String roleCode);

    @Insert({"insert into user (userName,password,tel,address,realName)" +
            "values (#{userName},#{password},#{tel},#{address},#{realName})"})
    public int register(User user);

    //
    @Insert({"insert into user_role (userName,roleCode)" +
            "values (#{userName},#{roleCOde})"})
    public int addRole(String userName, String roleCOde);
}
