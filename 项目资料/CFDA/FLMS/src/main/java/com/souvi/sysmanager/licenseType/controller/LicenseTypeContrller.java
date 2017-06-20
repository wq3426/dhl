package com.souvi.sysmanager.licenseType.controller;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.sysmanager.licenseType.entity.LicenseType;
import com.souvi.sysmanager.licenseType.service.LicenseTypeService;

@Controller
@RequestMapping("licenseType")
public class LicenseTypeContrller {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private LicenseTypeService licenseTypeService;

	// 显示界面
	@RequestMapping("tolicenseTypeList")
	public String tolicenseTypeList() {
		return "sysmanager/licenseType/licenseTypeList";
	}

	/*
	 * 校验编辑时证照类型唯一
	 */
	public int licenseTypeCheck(String licenseId, String licenseType) {

		return licenseTypeService.licenseTypeCheck(licenseId, licenseType);
	}

	/*
	 * 校验添加时证照类型唯一
	 */
	public int licenseTypeChecktwo(String licenseType) {

		return licenseTypeService.licenseTypeChecktwo(licenseType);
	}

	/*
	 * 证照列表
	 */
	@RequestMapping("licenseTypeList")
	@ResponseBody
	public PageInfo<LicenseType> licenseTypeList(HttpServletRequest request,
			LicenseType le,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);

		List<LicenseType> leList = licenseTypeService.getlicenseTypeList(le);
		PageInfo<LicenseType> pageInfo = new PageInfo<LicenseType>(leList);

		return pageInfo;
	}

	/*
	 * 去添加页面
	 */
	@RequestMapping("toAddlicenseType")
	public String toAddlicenseType(HttpServletRequest request) {
		return "sysmanager/licenseType/addlicenseType";
	}
	
	/*
	 * 去查看页面
	 */	
	@RequestMapping("tochecklicenseType")
	public String tochecklicenseType(HttpServletRequest request,String licenseId) {
		LicenseType lse = licenseTypeService.toupdatelicenseType(licenseId);
		request.setAttribute("lse", lse);
		return "sysmanager/licenseType/checklicenseType";
	}

	/*
	 * 添加证照类型
	 */
	@RequestMapping("addlicenseType")
	@ResponseBody
	public int addlicenseType(HttpServletRequest request, LicenseType le) {
		int flag = 0;
		try {
			if (licenseTypeChecktwo(le.getLicenseType()) > 0) {
				flag = 2;
			} else {
				le.setLicenseId(KeyUtil.getGuidID());
				le.setCreateBy(SessionUser.getLoginUserName(request));
				le.setCreateDate(new Date());
				licenseTypeService.addlicenseType(le);
			}
			logger.info(MessageUtil.MessageUtil("产品类型", "添加", le));
		} catch (Exception e) {
			logger.error("添加产品类型信息时存在异常：", e);
		}
		return flag;
	}

	/*
	 * 去修改页面
	 */
	@RequestMapping("toupdatelicenseType")
	public String toupdatelicenseType(HttpServletRequest request,
			String licenseId) {
		try {
			LicenseType lse = licenseTypeService.toupdatelicenseType(licenseId);
			request.setAttribute("lse", lse);

			logger.info(MessageUtil.MessageUtil("证照类型", "修改回显", licenseId));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改回显证照类型信息时存在异常：", e);
		}
		return "sysmanager/licenseType/updatelicenseType";
	}

	/*
	 * 修改证照类型
	 */
	@RequestMapping("updatelicenseType")
	@ResponseBody
	public int updatelicenseType(HttpServletRequest request, LicenseType le) {
		int flag = 0;
		try {
			if (licenseTypeCheck(le.getLicenseId(), le.getLicenseType()) > 0) {
				flag = 2;
			} else {
				le.setLastUpdateBy((SessionUser.getLoginUserName(request)));
				le.setLastUpdateDate(new Date());
				licenseTypeService.updatelicenseType(le);
			}
			logger.info(MessageUtil.MessageUtil("证照类型", "修改", le));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("修改证照类型信息时存在异常：", e);
		}

		return flag;
	}

	/*
	 * 删除证照类型
	 */
	@RequestMapping("deletelicenseType")
	@ResponseBody
	public boolean deletelicenseType(String licenseId,
			HttpServletRequest request) {
		boolean b = false;
		try {
			b = licenseTypeService.deletelicenseType(licenseId);
			logger.info(MessageUtil.MessageUtil("证照类型", "删除", licenseId));
		} catch (Exception e) {
			logger.error("删除证照类型信息时存在异常：", e);
		}
		return b;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}
}
