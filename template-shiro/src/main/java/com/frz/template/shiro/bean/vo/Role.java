package com.frz.template.shiro.bean.vo;

import com.frz.template.shiro.bean.db.ShiroPermission;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fengrongze on 2017/6/27.
 */
public class Role {
    private Integer id;
    private String rolename;
    private List<ShiroPermission> permissionList;// 一个角色对应多个权限

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public List<ShiroPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<ShiroPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public List<String> getPermissionsName() {
        List<String> list = new ArrayList<String>();
        List<ShiroPermission> perlist = getPermissionList();
        for (ShiroPermission per : perlist) {
            list.add(per.getPermissionname());
        }
        return list;
    }
}
