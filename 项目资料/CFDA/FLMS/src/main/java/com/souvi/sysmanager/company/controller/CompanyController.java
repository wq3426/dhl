package com.souvi.sysmanager.company.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.Constant;
import com.souvi.common.MessageInfo;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.sysmanager.auditLog.entity.AuditLog;
import com.souvi.sysmanager.auditLog.service.AuditLogService;
import com.souvi.sysmanager.company.entity.Company;
import com.souvi.sysmanager.company.service.CompanyService;
import com.souvi.sysmanager.licenseType.entity.LicenseType;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.projectManage.service.ProjectService;
import com.souvi.sysmanager.user.entity.User;

/**
 * 公司管理 Controller
 * 
 * @author sunjitao
 *
 */
@Controller
@RequestMapping("company")
public class CompanyController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static final ObjectMapper ObjectMapper = new ObjectMapper();

	@Autowired
	private CompanyService companyService;

	@Autowired
	private ProjectService projectService;

	@Autowired
	private AuditLogService auditLogService;

	/*
	 * 跳转到公司查询页面
	 */
	@RequestMapping(value = "/toCompanyList", method = RequestMethod.GET)
	public String toCompanyList(HttpServletRequest request, Model model) throws Exception {
		model.addAttribute("companyTypeList",
				ObjectMapper.writeValueAsString(request.getSession().getAttribute("GLOBAL_CompanyType")));
		model.addAttribute("auditTypeList",
				ObjectMapper.writeValueAsString(request.getSession().getAttribute("GLOBAL_AuditType")));
		model.addAttribute("dataStatusList",
				ObjectMapper.writeValueAsString(request.getSession().getAttribute("GLOBAL_DataStatus")));
		model.addAttribute("dataSourceList",
				ObjectMapper.writeValueAsString(request.getSession().getAttribute("GLOBAL_DataSource")));
		return "sysmanager/company/companyList";
	}

	/*
	 * 公司列表
	 */
	@RequestMapping("companyList")
	@ResponseBody
	public PageInfo<Company> companyList(HttpServletRequest request, Company company,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count, Model model) {

		PageHelper.startPage(page, count);
		List<Company> companyList = this.companyService.getCompanyList(company);
		PageInfo<Company> pageInfo = new PageInfo<Company>(companyList);

		return pageInfo;
	}

	/*
	 * 去添加页面
	 */
	@RequestMapping("toAddCompany")
	public String toAddCompany(Model model) {
		model.addAttribute("projectList", this.projectService.getProjectList(new ProjectManage()));
		return "sysmanager/company/addCompany";
	}

	/*
	 * 添加公司
	 */
	@RequestMapping(value = "/addCompany", method = RequestMethod.POST)
	@ResponseBody
	public String addCompany(HttpServletRequest request, Company company, String[] licenseIds) {
		User crrentUser = (User) request.getSession(true).getAttribute(Constant.USER_SESSION_NAME);
		company.setCreateBy(crrentUser.getUserId());
		try {
			this.companyService.insertCompany(company, licenseIds);
			logger.info(MessageUtil.MessageUtil("公司管理", "添加", company));
			return String.valueOf(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("公司添加异常：", e);
		}
		return String.valueOf(Boolean.FALSE);
	}

	/*
	 * 跳转到公司管理更新
	 */
	@RequestMapping(value = "/toUpdateCompany", method = RequestMethod.GET)
	public String toUpdateCompany(HttpServletRequest request, String id, Model model) {
		Company company = this.companyService.getCompanyByID(id);
		model.addAttribute("company", company);
		model.addAttribute("projectList", this.projectService.getProjectList(new ProjectManage()));
		model.addAttribute("licenseList", this.companyService.getCompanyLicense(company.getCompanyId()));

		// 最近一次审批
		AuditLog auditLog = this.auditLogService.getAuditLogByForeignId(company.getCompanyId());

		// 审批阶段
		String operateStage = "0";// 提交
		if (null != auditLog) {
			if (0 == auditLog.getAuditStage()) {
				operateStage = "1";// 一级审批
			} else {
				if ("3".equals(auditLog.getAuditStatus())) {
					if (1 == auditLog.getAuditStage()) {
						operateStage = "2";// 二级审批
					} else {
						operateStage = "3";// 审批完成
					}
				}
			}
		}

		String roleId = SessionUser.getLoginUserRoleId(request);

		// 判断当前角色是否有权限审批
		if (("1".equals(operateStage) && roleId.indexOf(Constant.Role_FirstAduit) > -1)
				|| ("2".equals(operateStage) && roleId.indexOf(Constant.Role_SecondAduit) > -1)) {
			operateStage = "4";// 不能审批
		}
		// 审批日志
		model.addAttribute("operateStage", operateStage);
		return "sysmanager/company/updateCompany";
	}

	/*
	 * 跳转到公司管理更新
	 */
	@RequestMapping(value = "/updateCompany", method = RequestMethod.POST)
	@ResponseBody
	public String updateCompany(HttpServletRequest request, Company company, String[] licenseIds, Model model) {
		User crrentUser = (User) request.getSession(true).getAttribute(Constant.USER_SESSION_NAME);
		company.setLastUpdateBy(crrentUser.getUserId());
		try {
			this.companyService.updateCompany(company, licenseIds);
			logger.info(MessageUtil.MessageUtil("公司管理", "更新", company));
			return String.valueOf(Boolean.TRUE);
		} catch (Exception e) {
			logger.error("公司更新异常：", e);
		}
		return String.valueOf(Boolean.FALSE);
	}

	/*
	 * 删除公司信息
	 */
	@RequestMapping(value = "/deleteCompany", method = RequestMethod.GET)
	@ResponseBody
	public boolean deleteCompany(HttpServletRequest request, String id) {
		boolean deleteFlag = false;
		try {
			Company company = this.companyService.getCompanyByID(id);
			deleteFlag = this.companyService.deleteCompany(id) == 1;
			logger.info(MessageUtil.MessageUtil("公司管理", "删除", company));
		} catch (Exception e) {
			logger.error("公司管理删除异常：", e);
		}
		return deleteFlag;
	}

	/*
	 * 校验公司
	 */
	@RequestMapping(value = "toCompanyCheck", method = RequestMethod.GET)
	@ResponseBody
	public MessageInfo toCompanyCheck(String companyId, String projectId, String companyName, String companyType) {

		MessageInfo messageInfo = new MessageInfo();

		int count = 0;
		// 1、公司类型为第三方的公司只能有一条
		if ("4".equals(companyType)) {
			count = this.companyService.toCompanyCheck(companyId, projectId, companyType, null);
			if (count > 0) {
				messageInfo.setMessage("公司类型为第三方的公司只能有一条");
				return messageInfo;
			}
		}
		// 2、同一个项目下的公司类型为委托客户的数据只能有一条
		else if ("1".equals(companyType)) {
			count = this.companyService.toCompanyCheck(companyId, projectId, companyType, null);
			if (count > 0) {
				messageInfo.setMessage("同一个项目下的公司类型为委托客户的数据只能有一条");
				return messageInfo;
			}
		}

		count = this.companyService.toCompanyCheck(companyId, projectId, companyType, companyName);
		if (count > 0) {
			messageInfo.setMessage("项目名称+公司名称+公司类型必须唯一");
			return messageInfo;
		}
		messageInfo.setSuccess(true);
		return messageInfo;
	}

	/**
	 * 提交审核
	 * 
	 * @param request
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/submitCompany", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo submitCompany(HttpServletRequest request, String companyId) {

		User currentUser = (User) request.getSession(true).getAttribute(Constant.USER_SESSION_NAME);

		MessageInfo messageInfo = new MessageInfo();

		boolean isSuccess = true;

		// 1、TODO://添加判断

		this.companyService.submitCompany(currentUser, companyId);

		messageInfo.setSuccess(isSuccess);
		return messageInfo;
	}

	/**
	 * 审核公司
	 * 
	 * @param request
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/auditCompany", method = RequestMethod.POST)
	@ResponseBody
	public MessageInfo auditCompany(HttpServletRequest request, String companyId, int auditStage, String auditStatus,
			String auditOpinion) {

		User currentUser = (User) request.getSession(true).getAttribute(Constant.USER_SESSION_NAME);

		MessageInfo messageInfo = new MessageInfo();

		boolean isSuccess = true;

		// 1、TODO://添加判断

		this.companyService.auditCompany(currentUser, companyId, auditStage, auditStatus, auditOpinion);

		messageInfo.setSuccess(isSuccess);
		return messageInfo;
	}

	/**
	 * 更新公司状态
	 * 
	 * @param companyId
	 * @param status
	 * @return
	 */
	@RequestMapping(value = "/updateCompanyStatus", method = RequestMethod.GET)
	@ResponseBody
	public MessageInfo updateCompanyStatus(HttpServletRequest request, String companyId, String dataStatus) {
		MessageInfo messageInfo = new MessageInfo();

		// 修改为启用状态
		if ("1".equals(dataStatus)) {
			Company company = this.companyService.getCompanyByID(companyId);
			if ("3".equals(company.getAuditStatus())) {
				if (!this.validateLisence(companyId)) {
					messageInfo.setMessage("该公司关联的证照的状态必须为已确认和启用状态");
					return messageInfo;
				}
			} else {
				messageInfo.setMessage("该公司未审核通过");
				return messageInfo;
			}
		} else {
			if (!this.validateLisence(companyId)) {
				messageInfo.setMessage("该公司关联的证照的状态必须为已确认和启用状态");
				return messageInfo;
			}
		}
		this.companyService.updateCompanyDataStatus(SessionUser.getLoginUser(request), companyId, dataStatus);
		messageInfo.setSuccess(true);
		return messageInfo;
	}

	/**
	 * 判断该公司的证照的状态是否为已确认和启用
	 * 
	 * @param companyId
	 * @return
	 */
	private boolean validateLisence(String companyId) {
		List<LicenseType> licenseTypeList = this.companyService.getCompanyLicense(companyId);
		if (CollectionUtils.isNotEmpty(licenseTypeList)) {
			// TODO：判断每个证照类型下面的证照的状态是否为已确认和启用
		}
		return true;
	}

	/*
	 * 跳转到公司查看页面
	 */
	@RequestMapping(value = "/toCompanyDetail", method = RequestMethod.GET)
	public String toCompanyDetail(HttpServletRequest request, String id, Model model) {
		Company company = this.companyService.getCompanyByID(id);
		model.addAttribute("company", company);
		model.addAttribute("projectList", this.projectService.getProjectList(new ProjectManage()));
		model.addAttribute("licenseList", this.companyService.getCompanyLicense(company.getCompanyId()));

		AuditLog auditLog = new AuditLog();
		auditLog.setForeignId(id);
		model.addAttribute("auditList", this.auditLogService.getAuditLogList(auditLog));

		return "sysmanager/company/companyDetail";
	}
}
