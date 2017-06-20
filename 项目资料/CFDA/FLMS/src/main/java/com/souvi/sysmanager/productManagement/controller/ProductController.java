package com.souvi.sysmanager.productManagement.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.Constant;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.sysmanager.login.service.GlobalServiceUtil;
import com.souvi.sysmanager.productManagement.entity.AuditLog;
import com.souvi.sysmanager.productManagement.entity.Product;
import com.souvi.sysmanager.productManagement.entity.ProductUpdate;
import com.souvi.sysmanager.productManagement.service.ProductService;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.sendMessageLog.entity.SendMessageLog;

/*
 * 产品管理
 */
@Controller
@RequestMapping("product")
public class ProductController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProductService productService;
	@Autowired
	private GlobalServiceUtil globalServiceUtil;
	
	/*
	 * 去显示页面
	 */
	@RequestMapping("toProductList")
	public String toProductList(HttpServletRequest request) {
		return "sysmanager/productManagement/productList";
	}
	
	/*
	 * 产品列表
	 */
	@RequestMapping("productList")
	@ResponseBody
	public PageInfo<Product> productList(HttpServletRequest request,Product product,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);
		//查询产品列表
		List<Product> productList = productService.getProductList(product);
		
		PageInfo<Product> pageInfo = new PageInfo<Product>(productList);
		
		return pageInfo;
	}
	
	/*
	 * 去添加页面
	 */
	@RequestMapping("toAddProduct")
	public String toAddProduct(HttpServletRequest request) {
		//查询项目
		List<ProjectManage> projectList = productService.getProjectList();
		request.setAttribute("projectList", projectList);
		return "sysmanager/productManagement/addProduct";
	}
	/*
	 * 查询产品类型
	 */
	@RequestMapping("queryProductType")
	@ResponseBody
	public List<ProductType> queryProductType(ProductType productType) {
		//查询产品类型
		List<ProductType> productTypeList = productService.getProductTypeList(productType);
		return productTypeList;
	}
	/*
	 * 添加/编辑时验证   项目名称+物料号必须唯一。
	 */
	@RequestMapping("toProductCheck")
	@ResponseBody
	public int toProductCheck(String productId,String projectId,String material) {
		// 项目名称+物料号必须唯一。
		int flag = productService.toCheckProduct(productId,projectId,material);
		return flag;
	}
	
	/*
	 * 添加产品
	 */
	@RequestMapping("addProduct")
	@ResponseBody
	public boolean addProduct(HttpServletRequest request,Product product) {
		boolean b  = false;
		try {
			product.setProductId(KeyUtil.getGuidID());
			product.setDataSources(Constant.DataSource_Web);
			product.setDataStatus(Constant.STATUS_N);
			product.setAuditStatus(Constant.AuditType_New);
			product.setCreateBy(SessionUser.getLoginUserName(request));
			product.setCreateDate(new Date());
			b = productService.addProduct(product);
			logger.info(MessageUtil.MessageUtil("产品管理","添加", product));
		}catch (Exception e) {
			logger.error("添加产品管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 去修改页面
	 */
	@RequestMapping("toUpdateProduct")
	public String toUpdateProduct(String productId, HttpServletRequest request) {
		//查询项目
		List<ProjectManage> projectList = productService.getProjectList();
		//查询产品数据
		Product product = productService.getProductByID(productId);
		
		ProductType productType = new ProductType();
		productType.setProductTypeId(product.getProductType()); 
		//查询产品类型
		List<ProductType> productTypeList = productService.getProductTypeList(productType);
		
		//得到当前登录人的角色
		String roleId = SessionUser.getLoginUserRoleId(request);
		int falg = 0; 				//未审核
		
		//查看项目的审批状态
		String auditStatus = product.getAuditStatus();
		if(auditStatus.equals(Constant.AuditType_New) || auditStatus.equals(Constant.AuditType_NotApproval)){					//未审批
			falg = 0;	//所有人都可以提交
		}else if(auditStatus.equals(Constant.AuditType_InApproval)){	//审批中
			//通过产品ID得到审批阶段
    		int i = productService.getAuditStage(productId);
    		
			//是否是审批人
	        if(roleId.indexOf("0c4a476ddc56428d81229fb392fba53b")!=-1 && i == 0){	//1级审批人
	        	//包含
	        	falg = 1;	//能看见审核
	        }else if(roleId.indexOf("a7a20685b4554041bafde6a5fffd1609")!=-1 && i == 1){	//2级审批人
	        	//包含
	        	falg = 1;	//能看见审核
	        }
		}else if(auditStatus.equals(Constant.AuditType_Approval)){		//审批通过
			falg = 3;  //所有人都可以修改
		}
		
		request.setAttribute("projectList", projectList);
		request.setAttribute("product", product);
		request.setAttribute("falg", falg);
		request.setAttribute("productTypeList", productTypeList);
		return "sysmanager/productManagement/updateProduct";
	}
	/*
	 * 查询产品的日志
	 */
	@RequestMapping("toDetailLog")
	public String toDetailLog(String productId,HttpServletResponse response,HttpServletRequest request) {
		//查询产品数据
		Product product = productService.getProductByID(productId);
		
		ProductType productType = new ProductType();
		productType.setProductTypeId(product.getProductType()); 
		//查询产品类型
		List<ProductType> productTypeList = productService.getProductTypeList(productType);
		//通过产品的ID查询产品更新所有数据
		List<ProductUpdate> productUpdateList = productService.getProductUpdate(product.getProjectId(),product.getMaterial());
		
		//查看项目的审批状态
		String auditStatus = product.getAuditStatus();
		if(!auditStatus.equals(Constant.AuditType_New)){
			//得到项目的审批日志
			List<AuditLog> auditLogList = productService.getAuditLogList(product.getProductId());
			request.setAttribute("auditLogList", auditLogList);
		}
		
		request.setAttribute("product", product);
		request.setAttribute("productUpdateList", productUpdateList);
		request.setAttribute("productTypeList", productTypeList);
		return "sysmanager/productManagement/detailLog";
	}
	/*
	 * 通过ID查询产品更新的详细数据
	 */
	@RequestMapping("details")
	@ResponseBody
	public List<Object> details(String productId,String productUpdateId) {
		//查询产品数据
		Product product = productService.getProductByID(productId);
		
		//通过ID查询产品更新的详细数据
		ProductUpdate productUpdate = productService.getDetails(productUpdateId);
		List<Object> list = new ArrayList<Object>();
		list.add(product);
		list.add(productUpdate);
		return list;
	}
	/*
	 * 驳回产品信息的更新
	 */
	@RequestMapping("reject")
	@ResponseBody
	public boolean reject(String productUpdateId) {
		boolean flag = false;
		try {
			flag = productService.reject(productUpdateId);
			logger.info(MessageUtil.MessageUtil("产品管理","更新驳回", productUpdateId));
		}catch (Exception e) {
			logger.error("驳回产品更新信息时存在异常：",e);
		}
		return flag;
	}
	/*
	 * 更新产品信息
	 */
	@RequestMapping("updateDetails")
	@ResponseBody
	public boolean updateDetails(String productId,String productUpdateId,ProductUpdate productUpdate) {
		boolean flag = false;
		try {
			flag = productService.updateDetails(productId,productUpdateId,productUpdate);
			logger.info(MessageUtil.MessageUtil("产品管理","更新", productId));
		}catch (Exception e) {
			logger.error("更新产品信息时存在异常：",e);
		}
		return flag;
	}
	/*
	 * 修改产品数据
	 */
	@RequestMapping("updateProduct")
	@ResponseBody
	public boolean updateProduct(Product product,HttpServletRequest request) {
		boolean b  = false;
		try {
			product.setLastUpdateBy(SessionUser.getLoginUserName(request));
			product.setLastUpdateDate(new Date());
			b = productService.updateProduct(product);
			logger.info(MessageUtil.MessageUtil("产品管理","编辑", product));
		}catch (Exception e) {
			logger.error("编辑产品管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 删除产品数据
	 */
	@RequestMapping("deleteProduct")
	@ResponseBody
	public boolean deleteProduct(String productId, HttpServletRequest request) {
		boolean b = false;
		try {
			b = productService.deleteProduct(productId);
			logger.info(MessageUtil.MessageUtil("产品管理","删除", productId));
		} catch (Exception e) {
			logger.error("删除产品管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 产品状态启用禁用
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public boolean updateStatus(String dataStatus,String productId) {
		boolean b = false;
		try {
			b = productService.updateStatus(dataStatus,productId);
			logger.info(MessageUtil.MessageUtil("产品管理","修改状态", productId));
		} catch (Exception e) {
			logger.error("修改产品状态时存在异常：",e);
		}
		return b;
	}
	/*
	 * 提交人提交审核
	 */
	@RequestMapping("submitAudit")
	@ResponseBody
	public boolean submitAudit(String projectId,String productId,Integer audit,String auditOpinion,HttpServletRequest request) {
		boolean b = false;
		
		AuditLog auditLog = new AuditLog();
		SendMessageLog sendMessageLog = new SendMessageLog();
		
		//得到当前登录人的角色
		String roleId = SessionUser.getLoginUserRoleId(request);
		//查询产品数据
		Product product = productService.getProductByID(productId);
		//查看项目的审批状态
		String auditStatus = product.getAuditStatus();
		
		if(auditStatus.equals(Constant.AuditType_New)){					//未审批
			//System.out.println("不包含");   提交人提交审核
        	
        	//审批时得到需要发送邮件的邮箱
    		String email = globalServiceUtil.gerAuditUserEmail(Constant.AuditLog_Submit, projectId, null);
    		//邮件主题
    		String mailtitle = sendMessageLog.getMailTitle(Constant.Mail_Product_Audit);
    		//邮件内容
    		String mailContent = sendMessageLog.getMailNoticeContent(Constant.Mail_Product_Audit, product);
        	
        	//添加审批日志
        	auditLog.setAuditId(KeyUtil.getGuidID());
        	auditLog.setForeignId(productId);        	
        	auditLog.setAuditType("P");
        	auditLog.setAuditStage(Constant.AuditLog_Submit);
        	auditLog.setAuditPersion(SessionUser.getLoginUserName(request));
        	auditLog.setAuditPersionId(SessionUser.getLoginUserId(request));
        	auditLog.setAuditStatus(Constant.AuditType_Submit);
        	auditLog.setAuditDate(new Date());
        	auditLog.setAuditPosition(SessionUser.getLoginUserStation(request));
        	b = productService.submitAuditLog(auditLog);
    		
    		//添加邮件发送日志
    		sendMessageLog.setId(KeyUtil.getGuidID());
    		sendMessageLog.setForeignId(productId);
    		sendMessageLog.setMessageType("mail");
    		sendMessageLog.setTitle(mailtitle);
    		sendMessageLog.setNoticeContent(mailContent);
    		sendMessageLog.setSendChannel(Constant.Mail_Product_Audit);
    		sendMessageLog.setToList(email);
    		sendMessageLog.setCreateDate(new Date());
    		sendMessageLog.setSendFlag(Constant.Mail_SendFlag_New);
    		b = productService.submitSendMessageLog(sendMessageLog);
    		
    		
    		//更改项目的审批状态
    		b = productService.updateAuditStatus(productId,Constant.AuditType_InApproval,null);
		}else if(auditStatus.equals(Constant.AuditType_InApproval)){	//审批中
			//是否是审批人
	        if(roleId.indexOf("0c4a476ddc56428d81229fb392fba53b")!=-1 || roleId.indexOf("a7a20685b4554041bafde6a5fffd1609")!=-1 ){
	        	//System.out.println("包含");	一级/二级审批人审核
	        	String email = null;
	        	//先通过产品ID得到审批阶段
	    		int i = productService.getAuditStage(productId);
	        	if(roleId.indexOf("0c4a476ddc56428d81229fb392fba53b")!=-1 && i == 0){
	        		//一级审批人审批
	        		
	        		if(audit == 1){//审批通过
	        			//审批时得到需要发送邮件的邮箱
	        			email = globalServiceUtil.gerAuditUserEmail(Constant.AuditLog_FirstAduit, projectId, Constant.AuditType_Approval);
	        			auditLog.setAuditStatus(Constant.AuditType_ApprovalY);
	        			auditLog.setAuditStage(Constant.AuditLog_FirstAduit);
	        		}else{//审批不通过
	        			email = globalServiceUtil.gerAuditUserEmail(Constant.AuditLog_Submit, productId, Constant.AuditType_NotApproval);
	        			auditLog.setAuditStatus(Constant.AuditType_NoApprovalN);
	        			auditLog.setAuditStage(Constant.AuditLog_Submit);
	        			//更改项目的审批状态
	            		productService.updateAuditStatus(productId,Constant.AuditType_NotApproval,Constant.STATUS_N);
	        		}
	        		
	        		//邮件主题
	        		String mailtitle = sendMessageLog.getMailTitle(Constant.Mail_Product_Audit);
	        		//邮件内容
	        		String mailContent = sendMessageLog.getMailNoticeContent(Constant.Mail_Product_Audit, product);
	        		
	        		//添加审批日志
	            	auditLog.setAuditId(KeyUtil.getGuidID());
	            	auditLog.setForeignId(productId);        	
	            	auditLog.setAuditType("P");
	            	
	            	auditLog.setAuditPersion(SessionUser.getLoginUserName(request));
	            	auditLog.setAuditPersionId(SessionUser.getLoginUserId(request));
	            	auditLog.setAuditDate(new Date());
	            	auditLog.setAuditPosition(SessionUser.getLoginUserStation(request));
	            	auditLog.setAuditOpinion(auditOpinion);
	            	b = productService.submitAuditLog(auditLog);
	        		
	        		//添加邮件发送日志
	        		sendMessageLog.setId(KeyUtil.getGuidID());
	        		sendMessageLog.setForeignId(productId);
	        		sendMessageLog.setMessageType("mail");
	        		sendMessageLog.setTitle(mailtitle);
	        		sendMessageLog.setNoticeContent(mailContent);
	        		sendMessageLog.setSendChannel(Constant.Mail_Product_Audit);
	        		sendMessageLog.setToList(email);
	        		sendMessageLog.setCreateDate(new Date());
	        		sendMessageLog.setSendFlag(Constant.Mail_SendFlag_New);
	        		b = productService.submitSendMessageLog(sendMessageLog);
	        		
	        	}else if(roleId.indexOf("a7a20685b4554041bafde6a5fffd1609")!=-1 && i == 1){  //二级审批人审批
	        		
	        		if(audit == 1){//审批通过
	        			//审批时得到需要发送邮件的邮箱
	        			email = globalServiceUtil.gerAuditUserEmail(Constant.AuditLog_SecondAduit, productId, Constant.AuditType_Approval);
	        			auditLog.setAuditStatus(Constant.AuditType_ApprovalY);
	        			auditLog.setAuditStage(Constant.AuditLog_SecondAduit);
	        			//更改项目的审批状态
	            		productService.updateAuditStatus(productId,Constant.AuditType_Approval,Constant.STATUS_Y);
	        		}else{//审批不通过
	        			email = globalServiceUtil.gerAuditUserEmail(Constant.AuditLog_FirstAduit, productId, Constant.AuditType_NotApproval);
	        			auditLog.setAuditStatus(Constant.AuditType_NoApprovalN);
	        			auditLog.setAuditStage(Constant.AuditLog_FirstAduit);
	        			//更改项目的审批状态
	            		productService.updateAuditStatus(productId,Constant.AuditType_NotApproval,Constant.STATUS_N);
	        		}
	        		
	        		//邮件主题
	        		String mailtitle = sendMessageLog.getMailTitle(Constant.Mail_Product_Audit);
	        		//邮件内容
	        		String mailContent = sendMessageLog.getMailNoticeContent(Constant.Mail_Product_Audit, product);
	        		
	        		//添加审批日志
	            	auditLog.setAuditId(KeyUtil.getGuidID());
	            	auditLog.setForeignId(productId);        	
	            	auditLog.setAuditType("P");
	            	
	            	auditLog.setAuditPersion(SessionUser.getLoginUserName(request));
	            	auditLog.setAuditPersionId(SessionUser.getLoginUserId(request));
	            	auditLog.setAuditDate(new Date());
	            	auditLog.setAuditPosition(SessionUser.getLoginUserStation(request));
	            	auditLog.setAuditOpinion(auditOpinion);
	            	b = productService.submitAuditLog(auditLog);
	        		
	        		//添加邮件发送日志
	        		sendMessageLog.setId(KeyUtil.getGuidID());
	        		sendMessageLog.setForeignId(productId);
	        		sendMessageLog.setMessageType("mail");
	        		sendMessageLog.setTitle(mailtitle);
	        		sendMessageLog.setNoticeContent(mailContent);
	        		sendMessageLog.setSendChannel(Constant.Mail_Product_Audit);
	        		sendMessageLog.setToList(email);
	        		sendMessageLog.setCreateDate(new Date());
	        		sendMessageLog.setSendFlag(Constant.Mail_SendFlag_New);
	        		b = productService.submitSendMessageLog(sendMessageLog);
	        		
	        	}
	        }
			
		}
		
		return b;
	}
}
