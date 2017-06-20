package com.souvi.sysmanager.role.entity;

import java.io.Serializable;

public class Role implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String roleId;			//主键
	private String roleName;		//角色名
	private String assetClass;		//资产等级
	private String description;		//描述	
	private String roleStatus;		//角色状态
	private String createBy;		//创建人
	private String createDate;		//创建时间
	private String lastUpdateBy;	//最后更新人
	private String lastUpdateDate;	//最后更新时间
	private String selected;		//选中
	
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getSelected() {
		return selected;
	}
	public void setSelected(String selected) {
		this.selected = selected;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getRoleStatus() {
		return roleStatus;
	}
	public void setRoleStatus(String roleStatus) {
		this.roleStatus = roleStatus;
	}
	
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName
				+ ", assetClass=" + assetClass + ", description=" + description
				+ ", roleStatus=" + roleStatus + ", createBy=" + createBy
				+ ", createDate=" + createDate + ", lastUpdateBy="
				+ lastUpdateBy + ", lastUpdateDate=" + lastUpdateDate
				+ ", selected=" + selected + "]";
	}
	
}
