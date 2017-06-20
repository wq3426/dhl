package com.souvi.sysmanager.company.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.common.Constant;
import com.souvi.common.KeyUtil;
import com.souvi.sysmanager.auditLog.entity.AuditLog;
import com.souvi.sysmanager.auditLog.service.AuditLogService;
import com.souvi.sysmanager.company.entity.Company;
import com.souvi.sysmanager.company.entity.CompanyValidateLicense;
import com.souvi.sysmanager.company.mapper.CompanyMapper;
import com.souvi.sysmanager.licenseType.entity.LicenseType;
import com.souvi.sysmanager.login.service.GlobalServiceUtil;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.role.mapper.roleMapper;
import com.souvi.sysmanager.sendMessageLog.entity.SendMessageLog;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.mapper.userMapper;

@Service
public class CompanyService {

	@Autowired
	private CompanyMapper companyMapper;

	@Autowired
	private AuditLogService auditLogService;

	@Autowired
	private GlobalServiceUtil globalServiceUtil;

	@Autowired
	private userMapper userMapper;

	@Autowired
	private roleMapper roleMapper;

	/*
	 * 添加公司
	 */
	public void insertCompany(Company company, String[] licenseIds) {
		company.setCompanyId(KeyUtil.getGuidID());
		company.setDataSources("1");// 手动创建
		company.setAuditStatus("1");// 审批状态为新建
		company.setDataStatus("2");// 数据状态为禁用
		company.setCreateDate(new Date());
		this.companyMapper.insertCompany(company);

		// 添加公司与证照关系
		if (null != licenseIds && licenseIds.length > 0) {
			for (String licenseId : licenseIds) {
				CompanyValidateLicense companyValidateLicense = new CompanyValidateLicense();
				companyValidateLicense.setId(KeyUtil.getGuidID());
				companyValidateLicense.setCompanyId(company.getCompanyId());
				companyValidateLicense.setLicenseId(licenseId);
				this.companyMapper.insertCompanyValidateLicense(companyValidateLicense);
			}
		}
	}

	/*
	 * 更新公司
	 */
	public void updateCompany(Company company, String[] licenseIds) {
		company.setDataSources("1");// 手动创建
		company.setLastUpdateDate(new Date());
		this.companyMapper.updateCompany(company);
		// 删除公司与证照关系
		this.companyMapper.deleteCompanyValidateLicense(company.getCompanyId());
		// 添加公司与证照关系
		if (null != licenseIds && licenseIds.length > 0) {
			for (String licenseId : licenseIds) {
				CompanyValidateLicense companyValidateLicense = new CompanyValidateLicense();
				companyValidateLicense.setId(KeyUtil.getGuidID());
				companyValidateLicense.setCompanyId(company.getCompanyId());
				companyValidateLicense.setLicenseId(licenseId);
				this.companyMapper.insertCompanyValidateLicense(companyValidateLicense);
			}
		}
	}

	/*
	 * 查询公司列表
	 */
	public List<Company> getCompanyList(Company company) {
		return this.companyMapper.getCompanyList(company);
	}

	/**
	 * 根据公司Id获取公司信息
	 * 
	 * @param companyId
	 * @return
	 */
	public Company getCompanyByID(String companyId) {
		return this.companyMapper.getCompanyByID(companyId);
	}

	/**
	 * 根据公司Id删除公司信息
	 * 
	 * @param companyId
	 */
	public int deleteCompany(String companyId) {
		this.companyMapper.deleteCompanyValidateLicense(companyId);
		return this.companyMapper.deleteCompany(companyId);
	}

	/**
	 * 校验公司信息
	 * 
	 * @param companyId
	 * @param projectId
	 * @param companyType
	 * @param companyName
	 * @return
	 */
	public int toCompanyCheck(String companyId, String projectId, String companyType, String companyName) {
		return this.companyMapper.toCompanyCheck(companyId, projectId, companyType, companyName);
	}

	/**
	 * 获取公司选择的证照类型
	 * 
	 * @param companyId
	 * @return
	 */
	public List<LicenseType> getCompanyLicense(String companyId) {
		return this.companyMapper.getCompanyLicense(companyId);
	}

	/**
	 * 提交公司
	 */
	@Transactional
	public void submitCompany(User user, String companyId) {

		// 1、更新公司的审批状态
		Company company = new Company();
		company.setCompanyId(companyId);
		company.setAuditStatus("2");// 审批中
		company.setLastUpdateBy(user.getUserId());
		company.setLastUpdateDate(new Date());
		this.companyMapper.updateCompanyAuditStatus(company);

		// 2、添加审批日志
		AuditLog auditLog = new AuditLog();
		auditLog.setForeignId(companyId);
		auditLog.setAuditType("C");// 审批类型为公司
		auditLog.setAuditStage(Constant.AuditLog_Submit);
		auditLog.setAuditPersion(user.getCname());
		auditLog.setAuditPersionId(user.getUserId());

		this.auditLogService.insertAuditLog(auditLog);

		// 3、发送邮件
		String toMailList = this.globalServiceUtil.getUserMailByRoleId(Constant.Role_FirstAduit, null);
		this.sendMail(companyId, toMailList, "提交公司审批");
	}

	/**
	 * 审批公司
	 */
	@Transactional
	public void auditCompany(User user, String companyId, int auditStage, String auditStatus, String auditOpinion) {

		// 1、更新公司状态
		Company company = new Company();
		company.setCompanyId(companyId);
		// 审批不通过
		if ("4".equals(auditStatus)) {
			company.setAuditStatus("4");// 审批不通过
		} else {
			// 二级审批通过
			if (2 == auditStage) {
				company.setAuditStatus("3");// 审批通过
			} else {
				company.setAuditStatus("2");// 审批中
			}
		}
		company.setLastUpdateBy(user.getUserId());
		company.setLastUpdateDate(new Date());
		this.companyMapper.updateCompanyAuditStatus(company);

		Role role = null;
		if (1 == auditStage) {
			role = this.roleMapper.getRoleByID(Constant.Role_FirstAduit);
		} else if (2 == auditStage) {
			role = this.roleMapper.getRoleByID(Constant.Role_SecondAduit);
		}

		// 2、添加审批日志
		AuditLog auditLog = new AuditLog();
		auditLog.setForeignId(companyId);
		auditLog.setAuditType("C");// 审批类型为公司
		auditLog.setAuditStage(auditStage);
		auditLog.setAuditStatus(auditStatus);
		auditLog.setAuditPersion(user.getCname());
		auditLog.setAuditPersionId(user.getUserId());
		auditLog.setAuditOpinion(auditOpinion);
		auditLog.setAuditPosition(null != role ? role.getRoleName() : null);

		this.auditLogService.insertAuditLog(auditLog);

		// 3、发送邮件
		String toMailStr = "";
		if ("4".equals(auditStatus)) {
			List<String> mailList = new ArrayList<>();
			// 获取最近一次提交人的邮箱
			List<User> userList = this.userMapper.getUserMailByAuditStage(1, companyId);
			if (CollectionUtils.isNotEmpty(userList)) {
				mailList.add(userList.get(0).getEmail());
			}
			// 如果是二级审批不通过，获取最近一次一级审批人的邮箱
			if (2 == auditStage) {
				userList = this.userMapper.getUserMailByAuditStage(2, companyId);
				if (CollectionUtils.isNotEmpty(userList)) {
					mailList.add(userList.get(0).getEmail());
				}
			}
			toMailStr = StringUtils.join(mailList, ",");
		} else {
			if (1 == auditStage) {
				toMailStr = this.globalServiceUtil.getUserMailByRoleId(Constant.Role_SecondAduit, null);
			}
		}
		if (StringUtils.isNoneBlank(toMailStr)) {
			this.sendMail(companyId, toMailStr, "公司审批");
		}
	}

	/**
	 * 添加邮件发送日志
	 * 
	 * @param companyId
	 * @param toMailList
	 * @param title
	 */
	private void sendMail(String companyId, String toMailList, String title) {
		SendMessageLog sendMessageLog = new SendMessageLog();
		sendMessageLog.setId(KeyUtil.getGuidID());
		sendMessageLog.setForeignId(companyId);
		sendMessageLog.setMessageType("mail");
		sendMessageLog.setTitle("提交公司审批");
		sendMessageLog.setSendChannel("");
		sendMessageLog.setToList(toMailList);
		sendMessageLog.setCreateDate(new Date());

		// TODO:添加到数据库
	}

	/**
	 * 更新公司的数据状态
	 * 
	 * @param user
	 * @param companyId
	 * @param dataStatus
	 * @return
	 */
	public int updateCompanyDataStatus(User user, String companyId, String dataStatus) {
		Company company = new Company();
		company.setCompanyId(companyId);
		company.setDataStatus(dataStatus);
		company.setLastUpdateBy(user.getUserId());
		company.setLastUpdateDate(new Date());
		return this.companyMapper.updateCompanyDataStatus(company);
	}
}
