package com.souvi.report.productNearInvalid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.report.productNearInvalid.mapper.ProductNearInvalidMapper;
import com.souvi.sysmanager.productManagement.entity.Product;

@Service
public class ProductNearInvalidService {

	@Autowired
	private ProductNearInvalidMapper productNearInvalidMapper;

	public List<Product> toproductNearInvalid() {
		// TODO Auto-generated method stub
		return productNearInvalidMapper.toproductNearInvalid();
	}

	public List productNearInvalidList(ProductLicense product) {
		// TODO Auto-generated method stub
		return productNearInvalidMapper.productNearInvalidList(product);
	}
}
