package com.souvi.report.productNearInvalid.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.sysmanager.productManagement.entity.Product;

public interface ProductNearInvalidMapper {

	List<Product> toproductNearInvalid();

	List productNearInvalidList(@Param("product")ProductLicense product);

}
