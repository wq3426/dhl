package com.souvi.sysmanager.company.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.company.entity.Company;
import com.souvi.sysmanager.company.entity.CompanyValidateLicense;
import com.souvi.sysmanager.licenseType.entity.LicenseType;

public interface CompanyMapper {

	/*
	 * 添加公司
	 */
	void insertCompany(@Param("company") Company company);

	/*
	 * 查询公司列表
	 */
	List<Company> getCompanyList(@Param("company") Company company);

	/**
	 * 删除公司证照
	 * 
	 * @param companyId
	 */
	void deleteCompanyValidateLicense(@Param("companyId") String companyId);

	/**
	 * 添加公司与证照关联关系
	 * 
	 * @param companyValidateLicense
	 */
	void insertCompanyValidateLicense(@Param("license") CompanyValidateLicense companyValidateLicense);

	/**
	 * 根据公司Id获取公司信息
	 * 
	 * @param companyId
	 * @return
	 */
	Company getCompanyByID(@Param("companyId") String companyId);

	/**
	 * 根据公司Id删除公司信息
	 * 
	 * @param companyId
	 */
	int deleteCompany(@Param("companyId") String companyId);

	/**
	 * 校验公司信息
	 * 
	 * @param companyId
	 * @param projectId
	 * @param companyType
	 * @param companyName
	 * @return
	 */
	int toCompanyCheck(@Param("companyId") String companyId, @Param("projectId") String projectId,
			@Param("companyType") String companyType, @Param("companyName") String companyName);

	/**
	 * 更新公司
	 * 
	 * @param company
	 * @return
	 */
	int updateCompany(@Param("company") Company company);

	/**
	 * 获取公司选择的证照类型
	 * 
	 * @param companyId
	 * @return
	 */
	List<LicenseType> getCompanyLicense(@Param("companyId") String companyId);

	/**
	 * 更新公司的审批状态
	 * 
	 * @param company
	 * @return
	 */
	int updateCompanyAuditStatus(@Param("company") Company company);

	/**
	 * 更新公司的数据状态
	 * 
	 * @param company
	 * @return
	 */
	int updateCompanyDataStatus(@Param("company") Company company);
}
