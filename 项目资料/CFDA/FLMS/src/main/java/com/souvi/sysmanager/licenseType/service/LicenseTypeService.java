package com.souvi.sysmanager.licenseType.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.licenseType.entity.LicenseType;
import com.souvi.sysmanager.licenseType.mapper.LicenseTypeMapper;

@Service
public class LicenseTypeService {

	@Autowired
	private LicenseTypeMapper licenseTypeMapper;

	//证照列表
	public List<LicenseType> getlicenseTypeList(LicenseType le) {
		// TODO Auto-generated method stub
		return licenseTypeMapper.getlicenseTypeList(le);
	}

	//添加证照类型
	public void addlicenseType(LicenseType le) {
		// TODO Auto-generated method stub
		licenseTypeMapper.addlicenseType(le);
	}

	//跳转修改页面
	public LicenseType toupdatelicenseType(String licenseId) {
		// TODO Auto-generated method stub
		return licenseTypeMapper.toupdatelicenseType(licenseId);
	}

	//修改证照类型
	public void updatelicenseType(LicenseType le) {
		// TODO Auto-generated method stub
		licenseTypeMapper.updatelicenseType(le);
	}
	//删除证照类型
	public boolean deletelicenseType(String licenseId) {
		// TODO Auto-generated method stub
		return licenseTypeMapper.deletelicenseType(licenseId);
	}

	/*
	 * 校验证照类型唯一
	 */
	public int licenseTypeCheck(String licenseId, String licenseType) {
		// TODO Auto-generated method stub
		return licenseTypeMapper.licenseTypeCheck(licenseId,licenseType);
	}
	/*
	 * 校验添加时证照类型唯一
	 */
	public int licenseTypeChecktwo(String licenseType) {
		// TODO Auto-generated method stub
		return licenseTypeMapper.licenseTypeChecktwo(licenseType);
	}


	

	
}
