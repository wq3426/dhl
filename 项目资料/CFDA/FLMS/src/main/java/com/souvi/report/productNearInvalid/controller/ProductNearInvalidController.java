package com.souvi.report.productNearInvalid.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.report.productNearInvalid.entity.ProductLicense;
import com.souvi.report.productNearInvalid.service.ProductNearInvalidService;
import com.souvi.sysmanager.productManagement.entity.Product;

@Controller
@RequestMapping("productNearInvalid")
public class ProductNearInvalidController {

	@Autowired
	private ProductNearInvalidService productNearInvalidService;

	@RequestMapping("toproductNearInvalid")
	public String toproductNearInvalid() {
		// List<Product>
		// Prolist=productNearInvalidService.toproductNearInvalid();
		// request.setAttribute("Prolist",Prolist);
		return "report/productNearInvalid/productNearInvalidList";
	}

	/*
	 * 列表
	 */
	@RequestMapping("productNearInvalidList")
	@ResponseBody
	public PageInfo<Product> productNearInvalidList(HttpServletRequest request,
			ProductLicense product,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);
		// 查询产品证照列表
		List productList = productNearInvalidService.productNearInvalidList(
				product);

		PageInfo<Product> pageInfo = new PageInfo<Product>(productList);

		return pageInfo;
	}
}
