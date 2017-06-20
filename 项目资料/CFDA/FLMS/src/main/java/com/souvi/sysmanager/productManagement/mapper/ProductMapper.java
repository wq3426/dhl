package com.souvi.sysmanager.productManagement.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.productManagement.entity.AuditLog;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.sendMessageLog.entity.SendMessageLog;

public interface ProductMapper {

	/*
	 * 产品列表
	 */
	List<Product> getProductList(@Param("product")Product product);
	/*
	 * 查询项目
	 */
	List<ProjectManage> getProjectList();
	/*
	 * 查询产品类型
	 */
	List<ProductType> getProductTypeList(@Param("productType")ProductType productType);
	/*
	 * 通过产品的ID查询产品更新数据
	 */
	List<ProductUpdate> getProductUpdate(@Param("projectId")String projectId, @Param("material")String material);
	/*
	 * 通过ID查询产品更新的详细数据
	 */
	ProductUpdate getDetails(@Param("productUpdateId")String productUpdateId);
	/*
	 * 添加/编辑时验证   项目名称+物料号必须唯一。
	 */
	int toCheckProduct(@Param("productId")String productId, @Param("projectId")String projectId, @Param("material")String material);
	/*
	 * 添加产品
	 */
	boolean addProduct(@Param("product")Product product);
	/*
	 * 查询产品数据
	 */
	Product getProductByID(@Param("productId")String productId);
	/*
	 * 修改产品数据
	 */
	boolean updateProduct(@Param("product")Product product);
	/*
	 * 删除产品数据
	 */
	boolean deleteProduct(@Param("productId")String productId);
	/*
	 * 产品状态启用禁用
	 */
	boolean updateStatus(@Param("dataStatus")String dataStatus, @Param("productId")String productId);
	/*
	 * 驳回产品信息的更新
	 */
	boolean reject(@Param("productUpdateId")String productUpdateId);
	/*
	 * 更新产品信息
	 */
	boolean updateDetails(@Param("productId")String productId, @Param("productUpdateId")String productUpdateId, @Param("productUpdate")ProductUpdate productUpdate);
	/*
	 * 添加审批日志
	 */
	boolean submitAuditLog(@Param("auditLog")AuditLog auditLog);
	/*
	 * 添加邮件发送日志
	 */
	boolean submitSendMessageLog(@Param("sendMessageLog")SendMessageLog sendMessageLog);
	/*
	 * 先通过产品ID得到审批阶段
	 */
	int getAuditStage(@Param("productId")String productId);
	/*
	 * 更改项目的审批状态
	 */
	boolean updateAuditStatus(@Param("productId")String productId, @Param("audittypeInapproval")String audittypeInapproval, @Param("dataStatus")String dataStatus);
	/*
	 * 得到项目的审批日志
	 */
	List<AuditLog> getAuditLogList(@Param("productId")String productId);

}
