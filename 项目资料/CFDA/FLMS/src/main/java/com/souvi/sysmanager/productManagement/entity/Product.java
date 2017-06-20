package com.souvi.sysmanager.productManagement.entity;

import java.util.Date;

public class Product {
	
	private String productId;				//主键
	private String projectId;				//项目外键
	private String projectName;				//项目名称
	private String material;				//物料号
	private String materialDescription;		//物料描述
	private String materalType;				//物料类型
	private String productName;				//产品名称
	private String model;					//规格/型号
	private String janCode;					//JANCode
	private String inplantFlag;				//植入/非植入
	private String shippingCondition;		//运输条件
	private String weight;					//毛重
	private String size;					//尺寸
	private String traceType;				//Trace Type
	private String cfdaStatecode;			//CFDA状态代码
	private String validManage;				//是否效期管理物料
	private String validManageDays;			//近效期管理天数
	private String isitColdChainStorage;	//是否需要冷链存储
	private String storageCondition;		//存储条件
	private String validityPeriod;			//产品有效期（年）
	private String productionCompanyLicense;//生产企业许可证号
	private String productionLicenseHolder;	//产品许可证持证商
	private String productionCompany;		//生产企业名称
	private String productType;				//产品类型
	private String uom;						//单位
	private String auditStatus;				//审批状态
	private String dataStatus;				//数据状态
	private String dataSources;				//数据来源
	private String remark1;					//备注1
	private String remark2;					//备注2
	private String remark3;					//备注3
	private String remark4;					//备注4
	private String remark5;					//备注5
	private String remark6;					//备注6
	private String remark7;					//备注7
	private String remark8;					//备注8
	private String remark9;					//备注9
	private String remark10;				//备注10
	private String createBy;				//创建人
	private Date   createDate;				//创建时间
	private String lastUpdateBy;			//最后更新人
	private Date   lastUpdateDate;			//最后更新时间
	
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getMaterialDescription() {
		return materialDescription;
	}
	public void setMaterialDescription(String materialDescription) {
		this.materialDescription = materialDescription;
	}
	public String getMateralType() {
		return materalType;
	}
	public void setMateralType(String materalType) {
		this.materalType = materalType;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getJanCode() {
		return janCode;
	}
	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}
	public String getInplantFlag() {
		return inplantFlag;
	}
	public void setInplantFlag(String inplantFlag) {
		this.inplantFlag = inplantFlag;
	}
	public String getShippingCondition() {
		return shippingCondition;
	}
	public void setShippingCondition(String shippingCondition) {
		this.shippingCondition = shippingCondition;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getTraceType() {
		return traceType;
	}
	public void setTraceType(String traceType) {
		this.traceType = traceType;
	}
	public String getCfdaStatecode() {
		return cfdaStatecode;
	}
	public void setCfdaStatecode(String cfdaStatecode) {
		this.cfdaStatecode = cfdaStatecode;
	}
	public String getValidManage() {
		return validManage;
	}
	public void setValidManage(String validManage) {
		this.validManage = validManage;
	}
	public String getValidManageDays() {
		return validManageDays;
	}
	public void setValidManageDays(String validManageDays) {
		this.validManageDays = validManageDays;
	}
	public String getIsitColdChainStorage() {
		return isitColdChainStorage;
	}
	public void setIsitColdChainStorage(String isitColdChainStorage) {
		this.isitColdChainStorage = isitColdChainStorage;
	}
	public String getStorageCondition() {
		return storageCondition;
	}
	public void setStorageCondition(String storageCondition) {
		this.storageCondition = storageCondition;
	}
	public String getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(String validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public String getProductionCompanyLicense() {
		return productionCompanyLicense;
	}
	public void setProductionCompanyLicense(String productionCompanyLicense) {
		this.productionCompanyLicense = productionCompanyLicense;
	}
	public String getProductionLicenseHolder() {
		return productionLicenseHolder;
	}
	public void setProductionLicenseHolder(String productionLicenseHolder) {
		this.productionLicenseHolder = productionLicenseHolder;
	}
	public String getProductionCompany() {
		return productionCompany;
	}
	public void setProductionCompany(String productionCompany) {
		this.productionCompany = productionCompany;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
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
