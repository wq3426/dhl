package com.souvi.sysmanager.right.entity;

public class Right {
	
	private String rightId;			//主键
	private String rightName;		//权限名称
	private String rightCode;		//权限代码
	private String path;			//路径
	private String rightType;		//类型
	private String parentid;		//父主键
	private String orderNum;		//顺序号
	private String rightStatus;		//状态
	private String remark;			//备注
	private String checked;			//选中
	
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getRightId() {
		return rightId;
	}
	public void setRightId(String rightId) {
		this.rightId = rightId;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getRightCode() {
		return rightCode;
	}
	public void setRightCode(String rightCode) {
		this.rightCode = rightCode;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getParentid() {
		return parentid;
	}
	public void setParentid(String parentid) {
		this.parentid = parentid;
	}
	public String getRightStatus() {
		return rightStatus;
	}
	public void setRightStatus(String rightStatus) {
		this.rightStatus = rightStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getRightType() {
		return rightType;
	}
	public void setRightType(String rightType) {
		this.rightType = rightType;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	
	
	
}
