package com.frz.template.shiro.business.dao;

import com.frz.template.shiro.bean.db.ShiroPermission;
import com.frz.template.shiro.bean.db.ShiroRole;
import com.frz.template.shiro.bean.db.ShiroUser;
import com.frz.template.shiro.bean.vo.Role;
import com.frz.template.shiro.bean.vo.User;
import com.frz.template.shiro.business.mapper.ShiroPermissionMapper;
import com.frz.template.shiro.business.mapper.ShiroRoleMapper;
import com.frz.template.shiro.business.mapper.ShiroUserMapper;
import com.frz.template.shiro.business.mapper.ShiroUserRoleMapper;
import org.omg.PortableInterceptor.USER_EXCEPTION;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

import static org.apache.shiro.web.filter.mgt.DefaultFilter.user;

/**
 * Created by fengrongze on 2017/6/22.
 */
@Repository
public class UserDao {

    @Autowired
    private ShiroUserMapper shiroUserMapper;

    @Autowired
    private ShiroUserRoleMapper shiroUserRoleMapper;

    @Autowired
    private ShiroPermissionMapper shiroPermissionMapper;

    public User findByName(String loginName) {

        //1.查询用户
        Example example = new Example(ShiroUser.class);
        example.createCriteria().andEqualTo("username",loginName);

        List<ShiroUser> users = shiroUserMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(users)){
            return null;
        }
        User user = new User();
        user.setId(users.get(0).getId());
        user.setPassword(users.get(0).getPassword());
        user.setUsername(users.get(0).getUsername());

        //查询用户相关角色
        List<ShiroRole> shiroRoles = shiroUserRoleMapper.selectRoleByUserName(loginName);
        List<Role>  roles = new ArrayList<>();
        for (int i = 0; i <shiroRoles.size() ; i++) {
            Role role= new Role() ;
            role.setId(shiroRoles.get(i).getId());
            role.setRolename(shiroRoles.get(i).getRolename());

            Example ep = new Example(ShiroPermission.class);
            ep.createCriteria().andEqualTo("roleId",role.getId());

            List<ShiroPermission> permissins = shiroPermissionMapper.selectByExample(ep);
            role.setPermissionList(permissins);
            roles.add(role);
            user.setRoleList(roles);
        }
        return user;
    }


}
