package com.souvi.sysmanager.projectManage.entity;

import java.util.Date;

public class ProjectManage {
	
	private String projectId;				//序号
	private String projectNo;				//项目编号
	private String projectName;				//项目名称
	private String contractStartDate;		//合同开始时间
	private String contractEndDate;			//合同结束时间
	private String isValidateContract;		//是否效验合同有效期
	private String isValidateOperationtype;	//是否效验经营方式
	private String contractPeriod;			//委托期限（月）
	private String isChineseLabels;			//是否委托加贴中文标签
	private String customerMail;			//委托客户邮箱
	private String businessScope;			//委托业务范围
	private String dataStatus;				//数据状态
	private String remark;					//备注
	private String createBy;				//创建人
	private Date createDate;				//创建时间
	private String lastUpdateBy;			//更新人
	private Date lastUpdateDate;			//更新时间
	
	public String getContractPeriod() {
		return contractPeriod;
	}
	public void setContractPeriod(String contractPeriod) {
		this.contractPeriod = contractPeriod;
	}
	public String getIsChineseLabels() {
		return isChineseLabels;
	}
	public void setIsChineseLabels(String isChineseLabels) {
		this.isChineseLabels = isChineseLabels;
	}
	public String getCustomerMail() {
		return customerMail;
	}
	public void setCustomerMail(String customerMail) {
		this.customerMail = customerMail;
	}
	public String getBusinessScope() {
		return businessScope;
	}
	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getProjectNo() {
		return projectNo;
	}
	public void setProjectNo(String projectNo) {
		this.projectNo = projectNo;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getIsValidateContract() {
		return isValidateContract;
	}
	public void setIsValidateContract(String isValidateContract) {
		this.isValidateContract = isValidateContract;
	}
	public String getIsValidateOperationtype() {
		return isValidateOperationtype;
	}
	public void setIsValidateOperationtype(String isValidateOperationtype) {
		this.isValidateOperationtype = isValidateOperationtype;
	}
	public String getDataStatus() {
		return dataStatus;
	}
	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
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
}
