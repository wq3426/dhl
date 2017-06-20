package com.souvi.sysmanager.productManagement.entity;

import java.util.Date;

public class AuditLog {
	private String 	auditId;
	private String 	foreignId;
	private String 	auditType;
	private Integer auditStage;
	private String 	auditPersion;
	private String	auditPersionId;
	private String	auditStatus;
	private Date	auditDate;
	private String 	auditPosition;
	private String 	auditOpinion;
	
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
