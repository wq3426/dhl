package com.souvi.sysmanager.systemConfig.mapper;

import java.util.List;

import com.souvi.sysmanager.systemConfig.entity.SystemConfig;

public interface SystemConfigMapper {
	//根据type查询
	public List<SystemConfig> getListByType(String type);
	//查询全部数据
	public  List<SystemConfig> getSystemConfigAll();

	
}
