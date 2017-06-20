package com.souvi.task.ediTextTask.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.task.ediTextTask.mapper.EdiTextTaskMpper;

@Service
public class EdiTextTaskService {

	@Autowired
	private EdiTextTaskMpper ediTextTaskMpper;

	public void setproductLicense(Product product) {
		// TODO Auto-generated method stub
		ediTextTaskMpper.setproductLicense(product);
	}

	public void setproductUpdate(ProductUpdate productUpdate) {
		// TODO Auto-generated method stub
		ediTextTaskMpper.setproductUpdate(productUpdate);
	}

	public ProductType setproductname(String productType) {
		// TODO Auto-generated method stub
		
		return ediTextTaskMpper.setproductname(productType);
	}

	public ProjectManage getprojectname(String projectName) {
		// TODO Auto-generated method stub
		return ediTextTaskMpper.getprojectname(projectName);
	}

	public Product getproductmaterial(String material) {
		// TODO Auto-generated method stub
		return ediTextTaskMpper.getproductmaterial(material);
	}
}
