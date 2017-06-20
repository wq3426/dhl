package com.souvi.sysmanager.company.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 公司
 * 
 * @author sunjitao
 *
 */
public class Company implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4838744158156946854L;

	private String companyId;// 主键
	private String companyNo; // 编号
	private String companyName;// 公司名称
	private String projectId;// 项目主键
	private String projectName;// 项目名称
	private String companyType;// 公司类型(主键)
	private String dataStatus;// 数据状态(主键)
	private String dataSources;// 数据来源(主键)
	private String auditStatus;// 审批状态(主键)
	private String isMedicalinstrumentsRight;// 是否允许经营医疗器械
	private String isColdChainService;// 是否提供冷链服务
	private String operationType;// 经营方式(主键)
	private String lockReason;// 锁定原因
	private String remark1;// 备注1
	private String remark2;// 备注2
	private String remark3;// 备注3
	private String remark4;// 备注4
	private String remark5;// 备注5
	private String remark6;// 备注6
	private String remark7;// 备注7
	private String remark8;// 备注8
	private String remark9;// 备注9
	private String remark10;// 备注10
	private String createBy;
	private Date createDate;
	private String lastUpdateBy;
	private Date lastUpdateDate;

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getCompanyNo() {
		return companyNo;
	}

	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getCompanyType() {
		return companyType;
	}

	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	public String getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String dataStatus) {
		this.dataStatus = dataStatus;
	}

	public String getDataSources() {
		return dataSources;
	}

	public void setDataSources(String dataSources) {
		this.dataSources = dataSources;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getIsMedicalinstrumentsRight() {
		return isMedicalinstrumentsRight;
	}

	public void setIsMedicalinstrumentsRight(String isMedicalinstrumentsRight) {
		this.isMedicalinstrumentsRight = isMedicalinstrumentsRight;
	}

	public String getIsColdChainService() {
		return isColdChainService;
	}

	public void setIsColdChainService(String isColdChainService) {
		this.isColdChainService = isColdChainService;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getLockReason() {
		return lockReason;
	}

	public void setLockReason(String lockReason) {
		this.lockReason = lockReason;
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
