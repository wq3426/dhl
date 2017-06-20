package com.souvi.sysmanager.plan.entity;

import java.io.Serializable;

public class PlanTimeLength implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;                        //主键id
	private String date;                       //日期   yyyy-MM-dd
	private Integer time_length;               //时间间隔(分钟)
	private String creater;                    //创建人
	private String create_time;                //创建时间
	private String last_updater;               //上次更新人
	private String update_time;                //上次更新时间
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setDate(String date) {
		this.date = date;
	}
	
	public Integer getTime_length() {
		return time_length;
	}
	
	public void setTime_length(Integer time_length) {
		this.time_length = time_length;
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
