package com.souvi.report.companyNearInvalid.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.report.companyNearInvalid.entity.CompanyLicense;
import com.souvi.report.companyNearInvalid.service.CompanyNearInvalidService;
import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.sysmanager.productManagement.entity.Product;

@Controller
@RequestMapping("companyNearInvalid")
public class CompanyNearInvalidController {

	@Autowired
	private CompanyNearInvalidService companyNearInvalidService;

	// 跳转界面
	@RequestMapping("tocompanyNearInvalid")
	public String toproductNearInvalid() {
		return "report/companynearinvalid/companyNearInvalidList";
	}

	/*
	 * 列表
	 */

	@RequestMapping("companyNearInvalidList")
	@ResponseBody
	public PageInfo<Product> productNearInvalidList(HttpServletRequest request,
			CompanyLicense cle,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);
		// 查询公司证照列表
		List cleList = companyNearInvalidService.companyNearInvalidList(cle);

		PageInfo<Product> pageInfo = new PageInfo<Product>(cleList);

		return pageInfo;
	}

}
