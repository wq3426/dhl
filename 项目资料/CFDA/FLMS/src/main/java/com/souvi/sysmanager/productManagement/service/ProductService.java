package com.souvi.sysmanager.productManagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.productManagement.entity.AuditLog;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productManagement.mapper.ProductMapper;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.sendMessageLog.entity.SendMessageLog;

@Service
public class ProductService {

	@Autowired
	private ProductMapper productMapper;
	
	/*
	 * 产品列表
	 */
	public List<Product> getProductList(Product product) {
		return productMapper.getProductList(product);
	}
	/*
	 * 查询项目
	 */
	public List<ProjectManage> getProjectList() {
		return productMapper.getProjectList();
	}
	/*
	 * 查询产品类型
	 */
	public List<ProductType> getProductTypeList(ProductType productType) {
		return productMapper.getProductTypeList(productType);
	}
	/*
	 * 通过产品的ID查询产品更新数据
	 */
	public List<ProductUpdate> getProductUpdate(String projectId,
			String material) {
		return productMapper.getProductUpdate(projectId,material);
	}
	/*
	 * 通过ID查询产品更新的详细数据
	 */
	public ProductUpdate getDetails(String productUpdateId) {
		return productMapper.getDetails(productUpdateId);
	}
	/*
	 * 添加/编辑时验证   项目名称+物料号必须唯一。
	 */
	public int toCheckProduct(String productId, String projectId,
			String material) {
		return productMapper.toCheckProduct(productId,projectId,material);
	}
	/*
	 * 添加产品
	 */
	public boolean addProduct(Product product) {
		return productMapper.addProduct(product);
	}
	/*
	 * 查询产品数据
	 */
	public Product getProductByID(String productId) {
		return productMapper.getProductByID(productId);
	}
	/*
	 * 修改产品数据
	 */
	public boolean updateProduct(Product product) {
		return productMapper.updateProduct(product);
	}
	/*
	 * 删除产品数据
	 */
	public boolean deleteProduct(String productId) {
		return productMapper.deleteProduct(productId);
	}
	/*
	 * 产品状态启用禁用
	 */
	public boolean updateStatus(String dataStatus, String productId) {
		return productMapper.updateStatus(dataStatus,productId);
	}
	/*
	 * 驳回产品信息的更新
	 */
	public boolean reject(String productUpdateId) {
		return productMapper.reject(productUpdateId);
	}
	/*
	 * 更新产品信息
	 */
	public boolean updateDetails(String productId, String productUpdateId, ProductUpdate productUpdate) {
		return productMapper.updateDetails(productId,productUpdateId,productUpdate);
	}
	/*
	 * 添加审批日志
	 */
	public boolean submitAuditLog(AuditLog auditLog) {
		return productMapper.submitAuditLog(auditLog);
	}
	/*
	 * 添加邮件发送日志
	 */
	public boolean submitSendMessageLog(SendMessageLog sendMessageLog) {
		return productMapper.submitSendMessageLog(sendMessageLog);
	}
	/*
	 * 先通过产品ID得到审批阶段
	 */
	public int getAuditStage(String productId) {
		return productMapper.getAuditStage(productId);
	}
	/*
	 * 更改项目的审批状态
	 */
	public boolean updateAuditStatus(String productId, String audittypeInapproval, String dataStatus) {
		return productMapper.updateAuditStatus(productId,audittypeInapproval,dataStatus);
	}
	/*
	 * 得到项目的审批日志
	 */
	public List<AuditLog> getAuditLogList(String productId) {
		return productMapper.getAuditLogList(productId);
	}
	
}
