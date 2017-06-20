package com.souvi.sysmanager.systemConfig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.systemConfig.entity.SystemConfig;
import com.souvi.sysmanager.systemConfig.mapper.SystemConfigMapper;

@Service
public class SystemConfigService {

	@Autowired
	private SystemConfigMapper systemConfigMapper;

	//根据type查询
	public List<SystemConfig> getListByType(String type){
		return this.systemConfigMapper.getListByType(type);
	}
	//查询全部数据
	public  List<SystemConfig> getSystemConfigAll(){
		return this.systemConfigMapper.getSystemConfigAll();
	}
}
