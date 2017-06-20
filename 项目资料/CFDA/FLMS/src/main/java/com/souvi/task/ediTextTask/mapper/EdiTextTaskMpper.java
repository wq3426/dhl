package com.souvi.task.ediTextTask.mapper;

import org.apache.ibatis.annotations.Param;

import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;

public interface EdiTextTaskMpper {

	void setproductLicense(@Param("product")Product product);

	void setproductUpdate(@Param("productUpdate")ProductUpdate productUpdate);

	ProductType setproductname(@Param("productType")String productType);

	ProjectManage getprojectname(@Param("projectName")String projectName);

	Product getproductmaterial(@Param("material")String material);

}
