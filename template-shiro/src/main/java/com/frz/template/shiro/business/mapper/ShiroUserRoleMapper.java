package com.frz.template.shiro.business.mapper;

import com.frz.template.shiro.bean.db.ShiroRole;
import com.frz.template.shiro.bean.db.ShiroUserRole;

import com.frz.template.shiro.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShiroUserRoleMapper extends MyMapper<ShiroUserRole>{

    List<ShiroRole> selectRoleByUserName(String loginName);
}