package com.souvi.sysmanager.plan.entity;

import java.io.Serializable;

public class SupplierBerthMapInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer bid;             //泊位主键id
	private Integer sid;             //供应商主键id
	private String berth_number;     //泊位号
	private String supplier_name;    //供应商名称
	private Integer berth_status;    //泊位状态  0 空闲 1 忙碌
//	private String creater;          //创建人
//	private String create_time;      //创建时间
	
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
	
	public String getBerth_number() {
		return berth_number;
	}

	public void setBerth_number(String berth_number) {
		this.berth_number = berth_number;
	}

	public String getSupplier_name() {
		return supplier_name;
	}

	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}

	public Integer getBerth_status() {
		return berth_status;
	}

	public void setBerth_status(Integer berth_status) {
		this.berth_status = berth_status;
	}
	
}
