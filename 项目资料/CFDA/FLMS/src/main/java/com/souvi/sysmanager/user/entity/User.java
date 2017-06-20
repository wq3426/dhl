package com.souvi.sysmanager.user.entity;

import java.util.List;

import com.souvi.sysmanager.role.entity.Role;

public class User {

	private String userId;// 主键
	private String userName;// 用户名
	private String cname;// 中文名称
	private String ename;// 英文名称
	private String idnum;// 工号
	private String password;// 密码
	private String email;// 邮箱
	private String telephone;// 电话
	private String isDelete;// 是否删除
	private String status;// 状态
	private String mappingId;// 关联ID
	private String createBy;// 创建人
	private String createDate;// 创建时间
	private String lastUpdateBy;// 最后更新人
	private String lastUpdateDate;// 最后更新时间
	private String infocenterId; // Infocenter用户 ID
	private String Station;//岗位
	
	public String getInfocenterId() {
		return infocenterId;
	}

	public void setInfocenterId(String infocenterId) {
		this.infocenterId = infocenterId;
	}

	private List<Role> role;

	public List<Role> getRole() {
		return role;
	}

	public void setRole(List<Role> role) {
		this.role = role;
	}

	public String getMappingId() {
		return mappingId;
	}

	public void setMappingId(String mappingId) {
		this.mappingId = mappingId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	public String getIdnum() {
		return idnum;
	}

	public void setIdnum(String idnum) {
		this.idnum = idnum;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getStation() {
		return Station;
	}

	public void setStation(String station) {
		Station = station;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", cname=" + cname + ", ename=" + ename
				+ ", idnum=" + idnum + ", email=" + email + ", telephone=" + telephone
				+ ", isDelete=" + isDelete + ", status=" + status + ", mappingId=" + mappingId + ", createBy="
				+ createBy + ", createDate=" + createDate + ", lastUpdateBy=" + lastUpdateBy + ", lastUpdateDate="
				+ lastUpdateDate + ", infocenterId=" + infocenterId + ", role=" + role + "]";
	}
}
