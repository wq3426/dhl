package com.souvi.sysmanager.auditLog.entity;

import java.util.Date;

/**
 * 审批日志
 * 
 * @author sunjitao
 *
 */
public class AuditLog {

	private String auditId;// 主键
	private String foreignId;// 外健
	private String auditType;// 类型(C公司P产品)
	private Integer auditStage;// 审批阶段(0提交人1一级审批2二级审批)
	private String auditPersion;// 审批人
	private String auditPersionId;// 审批人主键
	private String auditStatus;// 审批状态(审批通过、审批不通过)
	private Date auditDate;// 审批时间
	private String auditPosition;// 审批岗位
	private String auditOpinion;// 审批意见

	public String getAuditId() {
		return auditId;
	}

	public void setAuditId(String auditId) {
		this.auditId = auditId;
	}

	public String getForeignId() {
		return foreignId;
	}

	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}

	public String getAuditType() {
		return auditType;
	}

	public void setAuditType(String auditType) {
		this.auditType = auditType;
	}

	public Integer getAuditStage() {
		return auditStage;
	}

	public void setAuditStage(Integer auditStage) {
		this.auditStage = auditStage;
	}

	public String getAuditPersion() {
		return auditPersion;
	}

	public void setAuditPersion(String auditPersion) {
		this.auditPersion = auditPersion;
	}

	public String getAuditPersionId() {
		return auditPersionId;
	}

	public void setAuditPersionId(String auditPersionId) {
		this.auditPersionId = auditPersionId;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public Date getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(Date auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditPosition() {
		return auditPosition;
	}

	public void setAuditPosition(String auditPosition) {
		this.auditPosition = auditPosition;
	}

	public String getAuditOpinion() {
		return auditOpinion;
	}

	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
}
