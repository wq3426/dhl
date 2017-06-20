package com.souvi.report.companyNearInvalid.entity;

import java.util.Date;

public class CompanyLicense {

	private String licenceId; // 主键
	private String licenseName; // 证照名称
	private String licenceType; // 证照类型
	private String projectId; // 项目外键
	private String companyId; // 公司外键
	private String licenceNo; // 证照编号
	private String registrationNumber; // 注册号
	private String managementMode; // 经营方式
	private String actualManagementMode; // 实际经营方式
	private String personInCharge; // 企业负责人
	private String qualityOfficer; // 企业质量负责人
	private String legalPerson; // 法定代表人
	private String enterpriseName; // 企业名称
	private String enterpriseType; // 企业类型
	private String residence; // 住所
	private String businessPlace; // 经营场所
	private String issueDate; // 发证日期
	private String expiredDate; // 有效期至
	private String certificationDepartment; // 发证部门
	private String registeredCapital; // 注册资本
	private String withholdingDuty; // 扣缴义务
	private String checkColdchainservice; // 是否提供冷链服务
	private String checkConfirmStatus; // 检查确认状态
	private String productStatus; // 数据状态
	private String dataSources; // 数据来源
	private String remindDate; // 近效期提醒时间（天）
	private String lockReason; // 锁定原因
	private String createBy; // CreateBy
	private Date createDate; // CreateDate
	private String lastUpdateBy; // LastUpdateBy
	private Date lastUpdateDate; // LastUpdateDate
	private String remark1; // 备注1
	private String remark2; // 备注2
	private String remark3; // 备注3
	private String remark4; // 备注4
	private String remark5; // 备注5
	private String remark6; // 备注6
	private String remark7; // 备注7
	private String remark8; // 备注8
	private String remark9; // 备注9
	private String remark10; // 备注10
	private String projectName;
	private String expiredDate_Start;
	private String expiredDate__End; 
	private String companyName;
	
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getExpiredDate_Start() {
		return expiredDate_Start;
	}
	public void setExpiredDate_Start(String expiredDate_Start) {
		this.expiredDate_Start = expiredDate_Start;
	}
	public String getExpiredDate__End() {
		return expiredDate__End;
	}
	public void setExpiredDate__End(String expiredDate__End) {
		this.expiredDate__End = expiredDate__End;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getLicenceId() {
		return licenceId;
	}
	public void setLicenceId(String licenceId) {
		this.licenceId = licenceId;
	}
	public String getLicenseName() {
		return licenseName;
	}
	public void setLicenseName(String licenseName) {
		this.licenseName = licenseName;
	}
	public String getLicenceType() {
		return licenceType;
	}
	public void setLicenceType(String licenceType) {
		this.licenceType = licenceType;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getLicenceNo() {
		return licenceNo;
	}
	public void setLicenceNo(String licenceNo) {
		this.licenceNo = licenceNo;
	}
	public String getRegistrationNumber() {
		return registrationNumber;
	}
	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}
	public String getManagementMode() {
		return managementMode;
	}
	public void setManagementMode(String managementMode) {
		this.managementMode = managementMode;
	}
	public String getActualManagementMode() {
		return actualManagementMode;
	}
	public void setActualManagementMode(String actualManagementMode) {
		this.actualManagementMode = actualManagementMode;
	}
	public String getPersonInCharge() {
		return personInCharge;
	}
	public void setPersonInCharge(String personInCharge) {
		this.personInCharge = personInCharge;
	}
	public String getQualityOfficer() {
		return qualityOfficer;
	}
	public void setQualityOfficer(String qualityOfficer) {
		this.qualityOfficer = qualityOfficer;
	}
	public String getLegalPerson() {
		return legalPerson;
	}
	public void setLegalPerson(String legalPerson) {
		this.legalPerson = legalPerson;
	}
	public String getEnterpriseName() {
		return enterpriseName;
	}
	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}
	public String getEnterpriseType() {
		return enterpriseType;
	}
	public void setEnterpriseType(String enterpriseType) {
		this.enterpriseType = enterpriseType;
	}
	public String getResidence() {
		return residence;
	}
	public void setResidence(String residence) {
		this.residence = residence;
	}
	public String getBusinessPlace() {
		return businessPlace;
	}
	public void setBusinessPlace(String businessPlace) {
		this.businessPlace = businessPlace;
	}
	public String getIssueDate() {
		return issueDate;
	}
	public void setIssueDate(String issueDate) {
		this.issueDate = issueDate;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getCertificationDepartment() {
		return certificationDepartment;
	}
	public void setCertificationDepartment(String certificationDepartment) {
		this.certificationDepartment = certificationDepartment;
	}
	public String getRegisteredCapital() {
		return registeredCapital;
	}
	public void setRegisteredCapital(String registeredCapital) {
		this.registeredCapital = registeredCapital;
	}
	public String getWithholdingDuty() {
		return withholdingDuty;
	}
	public void setWithholdingDuty(String withholdingDuty) {
		this.withholdingDuty = withholdingDuty;
	}
	public String getCheckColdchainservice() {
		return checkColdchainservice;
	}
	public void setCheckColdchainservice(String checkColdchainservice) {
		this.checkColdchainservice = checkColdchainservice;
	}
	public String getCheckConfirmStatus() {
		return checkConfirmStatus;
	}
	public void setCheckConfirmStatus(String checkConfirmStatus) {
		this.checkConfirmStatus = checkConfirmStatus;
	}
	public String getProductStatus() {
		return productStatus;
	}
	public void setProductStatus(String productStatus) {
		this.productStatus = productStatus;
	}
	public String getDataSources() {
		return dataSources;
	}
	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}
	public String getRemindDate() {
		return remindDate;
	}
	public void setRemindDate(String remindDate) {
		this.remindDate = remindDate;
	}
	public String getLockReason() {
		return lockReason;
	}
	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
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
	public String getRemark1() {
		return remark1;
	}
	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}
	public String getRemark2() {
		return remark2;
	}
	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	public String getRemark3() {
		return remark3;
	}
	public void setRemark3(String remark3) {
		this.remark3 = remark3;
	}
	public String getRemark4() {
		return remark4;
	}
	public void setRemark4(String remark4) {
		this.remark4 = remark4;
	}
	public String getRemark5() {
		return remark5;
	}
	public void setRemark5(String remark5) {
		this.remark5 = remark5;
	}
	public String getRemark6() {
		return remark6;
	}
	public void setRemark6(String remark6) {
		this.remark6 = remark6;
	}
	public String getRemark7() {
		return remark7;
	}
	public void setRemark7(String remark7) {
		this.remark7 = remark7;
	}
	public String getRemark8() {
		return remark8;
	}
	public void setRemark8(String remark8) {
		this.remark8 = remark8;
	}
	public String getRemark9() {
		return remark9;
	}
	public void setRemark9(String remark9) {
		this.remark9 = remark9;
	}
	public String getRemark10() {
		return remark10;
	}
	public void setRemark10(String remark10) {
		this.remark10 = remark10;
	}
	
	

}
