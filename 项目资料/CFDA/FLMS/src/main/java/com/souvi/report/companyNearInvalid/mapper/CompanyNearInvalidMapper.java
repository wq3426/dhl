package com.souvi.report.companyNearInvalid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.report.companyNearInvalid.entity.CompanyLicense;

public interface CompanyNearInvalidMapper {

	List companyNearInvalidList(@Param("cle")CompanyLicense cle);

}
