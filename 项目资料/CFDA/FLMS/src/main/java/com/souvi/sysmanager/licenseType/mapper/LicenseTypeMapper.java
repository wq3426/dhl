package com.souvi.sysmanager.licenseType.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.licenseType.entity.LicenseType;

public interface LicenseTypeMapper {

	//证照列表
	List<LicenseType> getlicenseTypeList(@Param("le")LicenseType le);

	////添加证照类型
	void addlicenseType(@Param("le")LicenseType le);

	//跳转修改页面
	LicenseType toupdatelicenseType(@Param("licenseId")String licenseId);

	//修改证照类型
	void updatelicenseType(@Param("le")LicenseType le);

	//删除证照类型
	boolean deletelicenseType(@Param("licenseId")String licenseId);

	/*
	 * 校验证照类型唯一
	 */
	int licenseTypeCheck(@Param("licenseId")String licenseId,@Param("licenseType") String licenseType);

	/*
	 * 校验添加时证照类型唯一
	 */
	int licenseTypeChecktwo(@Param("licenseType")String licenseType);

}
