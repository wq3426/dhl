package com.souvi.sysmanager.company.entity;

/**
 * 公司与证照的关系
 * 
 * @author sunjitao
 *
 */
public class CompanyValidateLicense {

	private String id;
	private String companyId;// 公司主键
	private String licenseId;// 证照主键

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}
}
