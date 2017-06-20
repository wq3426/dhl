package com.souvi.sysmanager.user.entity;

import java.io.Serializable;


public class User implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String user_id;        // 主键id
	private String user_name;       // 用户名
	private String password;        // 密码
	private String name;            // 真实姓名
	private String id_num;          // 工号
	private String email;           // 邮箱
	private String telephone;       // 电话
	private String decription;      // 描述
	private Integer user_status;    // 用户状态 1 启用 0 停用
	private String creater;         // 创建人
	private String create_time;     // 创建时间
	private String last_updater;    // 最后更新人
	private String update_time;     // 最后更新时间
	
	public String getUser_id() {
		return user_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	
	public String getUser_name() {
		return user_name;
	}
	
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getId_num() {
		return id_num;
	}
	
	public void setId_num(String id_num) {
		this.id_num = id_num;
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
	
	public String getDecription() {
		return decription;
	}
	
	public void setDecription(String decription) {
		this.decription = decription;
	}
	
	public Integer getUser_status() {
		return user_status;
	}
	
	public void setUser_status(Integer user_status) {
		this.user_status = user_status;
	}
	
	public String getCreater() {
		return creater;
	}
	
	public void setCreater(String creater) {
		this.creater = creater;
	}
	
	public String getCreate_time() {
		return create_time;
	}
	
	public void setCreate_time(String create_time) {
		this.create_time = create_time;
	}
	
	public String getLast_updater() {
		return last_updater;
	}
	
	public void setLast_updater(String last_updater) {
		this.last_updater = last_updater;
	}
	
	public String getUpdate_time() {
		return update_time;
	}
	
	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
	
}
