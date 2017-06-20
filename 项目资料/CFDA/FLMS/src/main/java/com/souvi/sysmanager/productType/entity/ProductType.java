package com.souvi.sysmanager.productType.entity;

import java.util.Date;

public class ProductType {
	private String productTypeId;//id
	private String createBy;//创建人
	private Date createDate;//创建时间
	private String lastUpdateBy;//最后更改人
	private Date lastUpdateDate;//最后更改时间	
	private String productTypeName;//名称
	private String productTypeLevel;//类型
	private String productTypeNo;//编号
	private String remark;//备注
	private String dataStatus;//数据状态
	public String getProductTypeId() {
		return productTypeId;
	}
	public void setProductTypeId(String productTypeId) {
		this.productTypeId = productTypeId;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getLastUpdateBy() {
		return lastUpdateBy;
	}
	public void setLastUpdateBy(String lastUpdateBy) {
		this.lastUpdateBy = lastUpdateBy;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getProductTypeName() {
		return productTypeName;
	}
	public void setProductTypeName(String productTypeName) {
		this.productTypeName = productTypeName;
	}
	public String getProductTypeLevel() {
		return productTypeLevel;
	}
	public void setProductTypeLevel(String productTypeLevel) {
		this.productTypeLevel = productTypeLevel;
	}
	public String getProductTypeNo() {
		return productTypeNo;
	}
	public void setProductTypeNo(String productTypeNo) {
		this.productTypeNo = productTypeNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	
	
}
