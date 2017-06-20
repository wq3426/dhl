package com.souvi.sysmanager.productType.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.productType.entity.ProductType;


/**
 * 产品类型 Mapper
 * 
 * @author xiao.wang
 * 
 */
public interface ProductTypeMapper {

    // 查询功能
	List<ProductType> findProductType(@Param("pte")ProductType pte);
	
	// 添加功能	
	void insertTypeList(@Param("pte")ProductType pte);
	
	// 回显/跳转到编辑界面	
	ProductType findProductID(@Param("productTypeId") String productTypeId);

	// 编辑功能
	boolean updataProduct(@Param("pte")ProductType pte);

	//根据ProductTypeId进行删除
	void deleteProduct(@Param("productTypeId")String productTypeId);

	//校验:类型+编号必须唯一
	int ProductTypeListCheck(@Param("productTypeId")String productTypeId, @Param("productTypeLevel")String productTypeLevel,
			@Param("productTypeNo")String productTypeNo);

}
