package com.souvi.sysmanager.basic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.basic.mapper.BasicMapper;
import com.souvi.sysmanager.user.entity.User;

@Service
public class BasicService {
	@Autowired
	private BasicMapper basicMapper;

	//根据用户名获取用户
	public User getUserWithUserName(String user_name) {
		
		return basicMapper.getUserWithUserName(user_name);
	}

}
