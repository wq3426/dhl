package com.souvi.sysmanager.supply.entity;

import java.io.Serializable;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.souvi.common.validator.AddValidator;
import com.souvi.common.validator.UpdateValidator;

public class SupplierInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@NotNull(message="supplier_id不能为空", groups=UpdateValidator.class)
	@Min(value=1, message="供应商表主键supplier_id应该大于0", groups={UpdateValidator.class})
	private Integer supplier_id;     //供应商表主键id
	
	@NotNull(message="供应商名称supplier_name不能为空", groups={AddValidator.class, UpdateValidator.class})
	private String supplier_name;    //供应商名称 唯一
	
	@NotNull(message="供应商状态supplier_status不能为空", groups={AddValidator.class, UpdateValidator.class})
	@Max(value=1, message="供应商状态supplier_status只能是1(启用)或0(停用)", groups={AddValidator.class, UpdateValidator.class})
	private Integer supplier_status; //供应商状态 1 启用 0 停用
	
	private String creater;          //创建人
	private String create_time;      //创建时间
	private String last_updater;     //最后修改人
	private String update_time;      //最后修改时间
	
	public Integer getSupplier_id() {
		return supplier_id;
	}
	
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	
	public String getSupplier_name() {
		return supplier_name;
	}
	
	public void setSupplier_name(String supplier_name) {
		this.supplier_name = supplier_name;
	}
	
	public Integer getSupplier_status() {
		return supplier_status;
	}
	
	public void setSupplier_status(Integer supplier_status) {
		this.supplier_status = supplier_status;
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
