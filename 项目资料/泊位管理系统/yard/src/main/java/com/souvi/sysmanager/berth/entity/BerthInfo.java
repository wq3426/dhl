package com.souvi.sysmanager.berth.entity;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.souvi.common.validator.AddValidator;
import com.souvi.common.validator.UpdateValidator;

public class BerthInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5253260795066285890L;
	
	@NotNull(message="泊位主键berth_id不能为空", groups={UpdateValidator.class})
	@Min(value=1, message="泊位主键berth_id应该大于0", groups={UpdateValidator.class})
	private Integer berth_id;     //泊位主键id
	
	@NotNull(message="泊位号berth_number不能为空", groups={AddValidator.class, UpdateValidator.class})
	private String berth_number;  //泊位号 唯一
	
	@NotNull(message="泊位状态berth_status不能为空", groups={AddValidator.class, UpdateValidator.class})
	@Max(value=1, message="泊位状态berth_status只能为0(停用)或1(启用)", groups={AddValidator.class, UpdateValidator.class})
	private Integer berth_status; //泊位状态 1 启用 0 停用
	
	private String creater;       //创建人
	private String create_time;   //创建时间
	private String last_updater;  //最后修改人
	private String update_time;   //最后修改时间
	
	public Integer getBerth_id() {
		return berth_id;
	}
	
	public void setBerth_id(Integer berth_id) {
		this.berth_id = berth_id;
	}
	
	public String getBerth_number() {
		return berth_number;
	}
	
	public void setBerth_number(String berth_number) {
		this.berth_number = berth_number;
	}
	
	public Integer getBerth_status() {
		return berth_status;
	}
	
	public void setBerth_status(Integer berth_status) {
		this.berth_status = berth_status;
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
