package com.souvi.sysmanager.ediLog.entity;

import java.util.Date;

public class EdiLog {
	private String ediId;//主键
	private String fileName;//文件名称
	private String filePath;//文件路径
	private String edi_Type;//edi类型
	private String processingDate;//处理时间
	private String processingDate_Start;//处理开始时间
	private String processingDate_End;//处理结束时间
	private String processingState;//处理状态
	private String processingResult;//处理结果
	private String retransMission;//重发人
	private String retransMissionTime;//重发时间
	private String repeatedCause;//重发原因
	
	public String getProcessingDate() {
		return processingDate;
	}
	public void setProcessingDate(String processingDate) {
		this.processingDate = processingDate;
	}
	public String getRetransMissionTime() {
		return retransMissionTime;
	}
	public void setRetransMissionTime(String retransMissionTime) {
		this.retransMissionTime = retransMissionTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getProcessingState() {
		return processingState;
	}
	public void setProcessingState(String processingState) {
		this.processingState = processingState;
	}
	public String getProcessingResult() {
		return processingResult;
	}
	public void setProcessingResult(String processingResult) {
		this.processingResult = processingResult;
	}
	public String getRetransMission() {
		return retransMission;
	}
	public void setRetransMission(String retransMission) {
		this.retransMission = retransMission;
	}
	
	public String getRepeatedCause() {
		return repeatedCause;
	}
	public void setRepeatedCause(String repeatedCause) {
		this.repeatedCause = repeatedCause;
	}
	
	public String getProcessingDate_Start() {
		return processingDate_Start;
	}
	public void setProcessingDate_Start(String processingDate_Start) {
		this.processingDate_Start = processingDate_Start;
	}
	public String getProcessingDate_End() {
		return processingDate_End;
	}
	public void setProcessingDate_End(String processingDate_End) {
		this.processingDate_End = processingDate_End;
	}
	public String getEdiId() {
		return ediId;
	}
	public void setEdiId(String ediId) {
		this.ediId = ediId;
	}
	public String getEdi_Type() {
		return edi_Type;
	}
	public void setEdi_Type(String edi_Type) {
		this.edi_Type = edi_Type;
	}
	
	
}
 