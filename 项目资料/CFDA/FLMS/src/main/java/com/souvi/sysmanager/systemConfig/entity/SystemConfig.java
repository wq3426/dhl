package com.souvi.sysmanager.systemConfig.entity;

import java.util.ArrayList;
import java.util.List;

public class SystemConfig {

	private String configId;
	private int number;
	private String type;
	private String status;
	private String value;
	private String remark;
	
	List<SystemConfig> SystemConfigList;
	
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<SystemConfig> getSystemConfigList() {
		if(null==SystemConfigList)
			SystemConfigList = new ArrayList();
		return SystemConfigList;
	}
	public void setSystemConfigList(List<SystemConfig> systemConfigList) {
		SystemConfigList = systemConfigList;
	}

	
}
