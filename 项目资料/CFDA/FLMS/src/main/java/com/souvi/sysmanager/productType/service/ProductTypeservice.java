package com.souvi.sysmanager.productType.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.productType.mapper.ProductTypeMapper;

/**
 * 产品类型 service
 * 
 * @author xiao.wang
 * 
 */

@Service
public class ProductTypeservice {

	
	
	@Autowired
	private ProductTypeMapper producttypemapper;
//  查询功能
	public List<ProductType> findProducstType(ProductType pte) {
		// TODO Auto-generated method stub
		return producttypemapper.findProductType(pte);
	}
// 添加功能
	public void insertTypeList(ProductType pte) {
		// TODO Auto-generated method stub
		producttypemapper.insertTypeList(pte);
	}
	// 回显/跳转到编辑界面
	public ProductType findProductID(String productTypeId) {
		// TODO Auto-generated method stub
		return producttypemapper.findProductID(productTypeId);
	}

	// 编辑功能
	public boolean updataProduct(ProductType pte) {
		// TODO Auto-generated method stub
		return producttypemapper.updataProduct(pte);
	}

	//根据ProductTypeId进行删除
	public void deleteProduct(String productTypeId) {
		// TODO Auto-generated method stub
		 producttypemapper.deleteProduct(productTypeId);
	}

	//校验:类型+编号必须唯一
	public int ProductTypeListCheck(String projectTypeId, String productTypeLevel,
			String productTypeNo) {
		// TODO Auto-generated method stub
		return producttypemapper.ProductTypeListCheck(projectTypeId,productTypeLevel,productTypeNo);
	}

}
