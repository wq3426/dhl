package com.souvi.sysmanager.supply.entity;

import java.io.Serializable;

public class SupplierBerthMapper implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer bid;             //泊位主键id
	private Integer sid;             //供应商主键id
	private String creater;          //创建人
	private String create_time;      //创建时间
	
	public Integer getBid() {
		return bid;
	}
	
	public void setBid(Integer bid) {
		this.bid = bid;
	}
	
	public Integer getSid() {
		return sid;
	}
	
	public void setSid(Integer sid) {
		this.sid = sid;
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
	
}
