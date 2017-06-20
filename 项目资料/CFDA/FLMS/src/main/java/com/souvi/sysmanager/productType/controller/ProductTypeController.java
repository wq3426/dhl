package com.souvi.sysmanager.productType.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.sysmanager.productType.entity.ProductType;
import com.souvi.sysmanager.productType.service.ProductTypeservice;

/**
 * 产品类型 Controller
 * 
 * @author xiao.wang
 * 
 */

@Controller
@RequestMapping("productType")
public class ProductTypeController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ProductTypeservice productTypeservice;

	// 跳转到查询界面
	@RequestMapping("toProductTypeList")
	public String toProductTypeList() {
		return "sysmanager/productType/productTypeList";
	}

	// 查询
	@RequestMapping("productTypeList")
	@ResponseBody
	public PageInfo<ProductType> ProductTypeList(HttpServletRequest request,
			ProductType pte,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count,
			Model model) {
		PageHelper.startPage(page, count);
		List<ProductType> ptelist = productTypeservice.findProducstType(pte);
		PageInfo<ProductType> pageInfo = new PageInfo<ProductType>(ptelist);
		return pageInfo;
	}

	// 跳转到添加页面
	@RequestMapping("toAddProductType")
	public String addProductTypeList(HttpServletRequest request) {
		return "sysmanager/productType/addProductTypeList";
	}
	
	// 跳转到查看页面
	@RequestMapping("toDetailProductType")
	public String lookupProductTypeList(HttpServletRequest request,String productTypeId) {
		ProductType ptelist = productTypeservice.findProductID(productTypeId);
		request.setAttribute("ptelist", ptelist);
		return "sysmanager/productType/detailProducType";
	}
	
	// 校验:类型+编号必须唯一
	public int ProductTypeListCheck(String projectTypeId, String productTypeLevel,
			String productTypeNo) {

		return productTypeservice.ProductTypeListCheck(projectTypeId, productTypeLevel,
				productTypeNo);

	}

	// 添加
	@RequestMapping("insertProductType")
	@ResponseBody
	public int insertTypeList(HttpServletRequest request, ProductType pte) {
		int flag = 0;
		try {
			if (ProductTypeListCheck(pte.getProductTypeId(),
					pte.getProductTypeLevel(), pte.getProductTypeNo()) > 0) {
				flag = 2;
				return flag;
			} else {
				pte.setCreateBy((SessionUser.getLoginUserName(request)));
				pte.setCreateDate(new Date());// KeyUtil.getGuidID()
				pte.setProductTypeId(KeyUtil.getGuidID());
				productTypeservice.insertTypeList(pte);
			}
			logger.info(MessageUtil.MessageUtil("产品类型", "添加", pte));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("产品类型添加时存在异常：", e);
		}
		return flag;
	}

	// 回显/跳转到编辑界面
	@RequestMapping("toProductTypeOnID")
	public String ProductIDist(HttpServletRequest request, String productTypeId) {
		try {
			ProductType ptelist = productTypeservice.findProductID(productTypeId);
			request.setAttribute("ptelist", ptelist);
			logger.info(MessageUtil.MessageUtil("产品类型", "回显", productTypeId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("产品类型回显时存在异常：", e);
		}
		return "sysmanager/productType/updataProductTypeList";

	}

	// 编辑功能
	@RequestMapping("updataProductType")
	@ResponseBody
	public int updataProduct(HttpServletRequest request, ProductType pte) {
		int b = 0;
		try {
			if (ProductTypeListCheck(pte.getProductTypeId(),
					pte.getProductTypeLevel(), pte.getProductTypeNo()) > 0) {
				b = 2;
				return b;
			} else {
				pte.setLastUpdateBy((SessionUser.getLoginUserName(request)));
				pte.setLastUpdateDate(new Date());
				productTypeservice.updataProduct(pte);
			}
			logger.info(MessageUtil.MessageUtil("产品类型", "修改", pte));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("产品类型修改时存在异常：", e);
		}
		return b;

	}

	// 根据Id进行删除
	@RequestMapping("deleteProductType")
	@ResponseBody
	public boolean deleteProduct(HttpServletRequest request,
			String productTypeId) {
		productTypeservice.deleteProduct(productTypeId);

		return true;

	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
