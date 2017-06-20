package com.souvi.sysmanager.basic.mapper;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.user.entity.User;

public interface BasicMapper {

	//根据用户名获取用户
	User getUserWithUserName(@Param("user_name") String user_name);
	
}
