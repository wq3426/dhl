package com.souvi.sysmanager.sendMessageLog.entity;

import java.util.Date;

import com.souvi.common.Constant;

public class SendMessageLog {

	private String id;
	private String foreignId;
	private String messageType;
	private String title;
	private String noticeContent;
	private String sendChannel;
	private String toList;
	private String ccList;
	private Date createDate;
	private String sendFlag;
	private String sendDate;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getForeignId() {
		return foreignId;
	}
	public void setForeignId(String foreignId) {
		this.foreignId = foreignId;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNoticeContent() {
		return noticeContent;
	}
	public void setNoticeContent(String noticeContent) {
		this.noticeContent = noticeContent;
	}
	public String getSendChannel() {
		return sendChannel;
	}
	public void setSendChannel(String sendChannel) {
		this.sendChannel = sendChannel;
	}
	public String getToList() {
		return toList;
	}
	public void setToList(String toList) {
		this.toList = toList;
	}
	public String getCcList() {
		return ccList;
	}
	public void setCcList(String ccList) {
		this.ccList = ccList;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getSendFlag() {
		return sendFlag;
	}
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	/**
	 * 邮件主题
	 */
	public String getMailTitle(String sendChannel){
		String mailtitle="";
		if(sendChannel.equalsIgnoreCase(Constant.Mail_Company_Audit)){
			mailtitle="公司审批";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Company_EDI_Add)){
			mailtitle="EDI公司添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_Audit)){
			mailtitle="产品审批";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_EDI_Add)){
			mailtitle="EDI产品添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_EDI_Update)){
			mailtitle="EDI产品更新";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_ProductLicense_EDI_Add)){
			mailtitle="EDI产品证照添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_ProductLicense_EDI_Update)){
			mailtitle="EDI产品证照更新";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_CompanyLicense_EDI_Add)){
			mailtitle="EDI公司证照添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_CompanyLicense_EDI_Update)){
			mailtitle="EDI公司证照更新";
		}else {
			mailtitle="FLMS";
		}
		return mailtitle;
	}
	/**
	 * 邮件内容
	 * Object，公司，公司证照，产品，产品证照的Entity
	 */
	public String getMailNoticeContent(String sendChannel,Object obj){
		String mailContent="";
		if(sendChannel.equalsIgnoreCase(Constant.Mail_Company_Audit)){
			mailContent="公司审批";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Company_EDI_Add)){
			mailContent="EDI公司添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_Audit)){
			mailContent="产品审批";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_EDI_Add)){
			mailContent="EDI产品添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_Product_EDI_Update)){
			mailContent="EDI产品更新";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_ProductLicense_EDI_Add)){
			mailContent="EDI产品证照添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_ProductLicense_EDI_Update)){
			mailContent="EDI产品证照更新";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_CompanyLicense_EDI_Add)){
			mailContent="EDI公司证照添加";
		}else if(sendChannel.equalsIgnoreCase(Constant.Mail_CompanyLicense_EDI_Update)){
			mailContent="EDI公司证照更新";
		}else {
			mailContent="FLMS";
		}
		return mailContent;
	}
}
