package com.souvi.sysmanager.projectManage.entity;

import java.util.Date;

public class UpdateLog {
	
	private String updateId;		//主键
	private String foreignId;		//外键
	private String fieldName;		//字段名称
	private String oldValue;		//修改前的值
	private String newValue;		//修改后的值
	private String updateReason;	//修改原因
	private String updatePersion;	//修改人
	private Date updateDate;		//修改时间
	
	public String getUpdateId() {
		return updateId;
	}
	public void setUpdateId(String updateId) {
		this.updateId = updateId;
	}
	public String getForeignId() {
		return foreignId;
	}
	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	public String getUpdateReason() {
		return updateReason;
	}
	public void setUpdateReason(String updateReason) {
		this.updateReason = updateReason;
	}
	public String getUpdatePersion() {
		return updatePersion;
	}
	public void setUpdatePersion(String updatePersion) {
		this.updatePersion = updatePersion;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
}
