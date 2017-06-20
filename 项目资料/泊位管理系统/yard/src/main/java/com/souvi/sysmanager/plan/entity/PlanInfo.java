package com.souvi.sysmanager.plan.entity;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.souvi.common.RegExpValidatorUtils;
import com.souvi.common.validator.AddValidator;
import com.souvi.common.validator.AddValidator2;
import com.souvi.common.validator.UpdateValidator;

public class PlanInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@NotNull(message="计划表主键plan_id不能为空", groups={UpdateValidator.class})
	@Min(value=1, message="计划表主键plan_id应该大于0", groups={UpdateValidator.class})
	private Integer plan_id;                   //计划表主键id
	private String item_number;                //零件号                     【输入，可选】
	private String item_name;                  //零件名称                 【输入，可选】
	private String item_type;                  //零件类型                 【输入，可选】
	private String vans_type;                  //车型                         【输入，可选】
	private String vans_description;           //车型描述                 【输入，可选】
	private Integer box_count;                 //标准装框数（个） 【输入，可选】
	
	@Pattern(regexp=RegExpValidatorUtils.y_M_d_DatePattern, message="计划日期plan_date格式不正确(yyyy-MM-dd)", groups={AddValidator.class, UpdateValidator.class})
	private String plan_date;                  //计划日期      当天日期
	private Integer plan_type;                 //计划类型  1 追加 2 预定
	/**
	 * 计划表中查找当前排队序号最大的任务计划；
	 * - 若没有：则默认为8:00开始；
	 * - 若有：则为最大序号的计划预计时间+8分钟(系统设置的计划时间间隔)
	 */
	@NotNull(message="计划预计开始时间plan_starttime不能为空", groups={AddValidator2.class})
	@Pattern(regexp=RegExpValidatorUtils.timePattern, message="计划预计开始时间plan_starttime格式不正确(yyyy-MM-dd HH:mm:ss)", groups={AddValidator.class})
	private String plan_starttime;             //计划预计开始时间
	
	@Pattern(regexp=RegExpValidatorUtils.timePattern, message="驶入时间start_time格式不正确(yyyy-MM-dd HH:mm:ss)", groups={UpdateValidator.class})
	private String start_time;                 //驶入时间（华为接口上传）
	
	@Pattern(regexp=RegExpValidatorUtils.timePattern, message="驶离时间end_time格式不正确(yyyy-MM-dd HH:mm:ss)", groups={UpdateValidator.class})
	private String end_time;                   //驶离时间（华为接口上传）
	
	@Min(value=0, message="浮动时间float_time应该大于等于0", groups={AddValidator.class, UpdateValidator.class})
	private Integer float_time;                //浮动时间（非计划因素耽搁时间） 分钟 【输入，可选】
	
	private String plan_endtime;               //任务实际结束时间       浮动时间+驶离时间
	
	@NotNull(message="供应商主键supplier_id不能为空", groups={AddValidator.class})
	@Min(value=1, message="供应商主键supplier_id应该大于0", groups={AddValidator.class, UpdateValidator.class})
	private Integer supplier_id;               //供应商主键id           【输入，必须】
	
	@NotNull(message="泊位主键berth_id不能为空", groups={AddValidator.class})
	@Min(value=1, message="泊位主键berth_id应该大于0", groups={AddValidator.class})
	private Integer berth_id;                  //泊位主键id            【输入，必须】
	
	@Min(value=0, message="排序号sort_number应该大于等于0", groups={AddValidator.class, UpdateValidator.class})
	private Integer sort_number;               //排队序号        当前泊位最大排队序号加1
	
	@NotNull(message="车牌号plate_number不能为空", groups={AddValidator.class})
	@Pattern(regexp=RegExpValidatorUtils.carplatePattern, message="车牌号plate_number格式不正确", groups={AddValidator.class, UpdateValidator.class})
	private String plate_number;               //车牌号                                              【输入，必须】
	
	private String driver_contact;             //司机联系方式                                  【输入，可选】
	
	@NotNull(message="等候泊位号waiting_berth_number不能为空", groups={AddValidator.class})
	private String waiting_berth_number;       //等候泊位号                                      【输入，必须】
	private String waiting_berth_starttime;    //驶入等候泊位的时间  计划预计开始时间
	private String waiting_berth_endtime;      //驶离等候泊位的时间  驶入时间
	private String description;                //描述                                                   【输入，可选】
	private String creater;                    //创建人
	private String create_time;                //创建时间
	private String last_updater;               //上次更新人
	private String update_time;                //上次更新时间
	
	private Integer plan_status;               //计划完成状态  0 未完成 1 已完成
	private String berth_number;               //泊位号
	private String supplier_name;              //供应商名称
	
	public Integer getPlan_id() {
		return plan_id;
	}
	
	public void setPlan_id(Integer plan_id) {
		this.plan_id = plan_id;
	}
	
	public String getItem_number() {
		return item_number;
	}
	
	public void setItem_number(String item_number) {
		this.item_number = item_number;
	}
	
	public String getItem_name() {
		return item_name;
	}
	
	public void setItem_name(String item_name) {
		this.item_name = item_name;
	}
	
	public String getItem_type() {
		return item_type;
	}
	
	public void setItem_type(String item_type) {
		this.item_type = item_type;
	}
	
	public String getVans_type() {
		return vans_type;
	}
	
	public void setVans_type(String vans_type) {
		this.vans_type = vans_type;
	}
	
	public String getVans_description() {
		return vans_description;
	}
	
	public void setVans_description(String vans_description) {
		this.vans_description = vans_description;
	}
	
	public Integer getBox_count() {
		return box_count;
	}
	
	public void setBox_count(Integer box_count) {
		this.box_count = box_count;
	}
	
	public String getPlan_date() {
		return plan_date;
	}
	
	public void setPlan_date(String plan_date) {
		this.plan_date = plan_date;
	}
	
	public Integer getPlan_type() {
		return plan_type;
	}

	public void setPlan_type(Integer plan_type) {
		this.plan_type = plan_type;
	}

	public String getPlan_starttime() {
		return plan_starttime;
	}
	
	public void setPlan_starttime(String plan_starttime) {
		this.plan_starttime = plan_starttime;
	}
	
	public String getStart_time() {
		return start_time;
	}
	
	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}
	
	public String getEnd_time() {
		return end_time;
	}
	
	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}
	
	public Integer getFloat_time() {
		return float_time;
	}

	public void setFloat_time(Integer float_time) {
		this.float_time = float_time;
	}

	public String getPlan_endtime() {
		return plan_endtime;
	}
	
	public void setPlan_endtime(String plan_endtime) {
		this.plan_endtime = plan_endtime;
	}
	
	public Integer getSupplier_id() {
		return supplier_id;
	}
	
	public void setSupplier_id(Integer supplier_id) {
		this.supplier_id = supplier_id;
	}
	
	public Integer getBerth_id() {
		return berth_id;
	}
	
	public void setBerth_id(Integer berth_id) {
		this.berth_id = berth_id;
	}
	
	public Integer getSort_number() {
		return sort_number;
	}
	
	public void setSort_number(Integer sort_number) {
		this.sort_number = sort_number;
	}
	
	public String getPlate_number() {
		return plate_number;
	}
	
	public void setPlate_number(String plate_number) {
		this.plate_number = plate_number;
	}
	
	public String getDriver_contact() {
		return driver_contact;
	}
	
	public void setDriver_contact(String driver_contact) {
		this.driver_contact = driver_contact;
	}
	
	public String getWaiting_berth_number() {
		return waiting_berth_number;
	}
	
	public void setWaiting_berth_number(String waiting_berth_number) {
		this.waiting_berth_number = waiting_berth_number;
	}
	
	public String getWaiting_berth_starttime() {
		return waiting_berth_starttime;
	}
	
	public void setWaiting_berth_starttime(String waiting_berth_starttime) {
		this.waiting_berth_starttime = waiting_berth_starttime;
	}
	
	public String getWaiting_berth_endtime() {
		return waiting_berth_endtime;
	}
	
	public void setWaiting_berth_endtime(String waiting_berth_endtime) {
		this.waiting_berth_endtime = waiting_berth_endtime;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
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

	public Integer getPlan_status() {
		return plan_status;
	}

	public void setPlan_status(Integer plan_status) {
		this.plan_status = plan_status;
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
	
}
