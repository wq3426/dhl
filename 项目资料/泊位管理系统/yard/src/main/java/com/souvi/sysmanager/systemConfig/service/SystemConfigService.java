package com.souvi.sysmanager.systemConfig.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souvi.sysmanager.systemConfig.entity.SystemConfig;
import com.souvi.sysmanager.systemConfig.mapper.SystemConfigMapper;

@Service
public class SystemConfigService {

	@Autowired
	private SystemConfigMapper systemConfigMapper;

	public List<SystemConfig> getListByName(String name) {
		return this.systemConfigMapper.getListByName(name);
	}

	public SystemConfig getById(String id) {
		return this.systemConfigMapper.getById(id);
	}

	public List<SystemConfig> getListByParentId(String parentId) {
		return this.systemConfigMapper.getListByParentId(parentId);
	}

	public List<SystemConfig> getByParentNumberAndName(String number, String name) {
		return this.systemConfigMapper.getByParentNumberAndName(number, name);
	}

	public Integer updateValueById(SystemConfig systemConfig) {
		return systemConfigMapper.updateValueById(systemConfig);
	}

	public int delConfig(SystemConfig systemConfig) {
		return systemConfigMapper.delConfig(systemConfig);
	}

	@Transactional
	public int addConfig(SystemConfig config) {
		Integer number = systemConfigMapper.getMaxNumber(config.getName(), config.getParentId());
		config.setNumber(number == null ? 1 : ++number);
		int row = systemConfigMapper.insertConfig(config);
		return row;
	}

	/**
	 * 根据name属性验证config配置中是否存在该value值
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 * @param parentId
	 *            parentId
	 * @return
	 */
	public boolean toConfigCheck(String name, String value, String parentId) {
		SystemConfig systemConfig = systemConfigMapper.toConfigCheck(name, value, parentId);
		return systemConfig == null;
	}
}
