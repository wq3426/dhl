package com.souvi.report.companyNearInvalid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.report.companyNearInvalid.entity.CompanyLicense;
import com.souvi.report.companyNearInvalid.mapper.CompanyNearInvalidMapper;


@Service
public class CompanyNearInvalidService {

	
	@Autowired
	private CompanyNearInvalidMapper companyNearInvalidMapper;

	public List companyNearInvalidList(CompanyLicense cle) {
		// TODO Auto-generated method stub
		return companyNearInvalidMapper.companyNearInvalidList(cle);
	}
}
