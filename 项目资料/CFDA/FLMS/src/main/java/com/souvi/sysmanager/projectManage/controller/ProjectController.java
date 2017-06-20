package com.souvi.sysmanager.projectManage.controller;

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
import com.souvi.common.Constant;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.common.SessionUser;
import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.projectManage.entity.UpdateLog;
import com.souvi.sysmanager.projectManage.service.ProjectService;

/*
 * 项目管理
 */
@Controller
@RequestMapping("projectManage")
public class ProjectController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectService projectService;
	
	/*
	 * 去显示页面
	 */
	@RequestMapping("toProjectList")
	public String toProjectList(HttpServletRequest request) {
		return "sysmanager/projectManage/projectList";
	}
	
	/*
	 * 项目列表
	 */
	@RequestMapping("projectList")
	@ResponseBody
	public PageInfo<ProjectManage> projectList(HttpServletRequest request,ProjectManage pm,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count) {
		PageHelper.startPage(page, count);
		
		List<ProjectManage> pmList = projectService.getProjectList(pm);
		PageInfo<ProjectManage> pageInfo = new PageInfo<ProjectManage>(pmList);
		
		return pageInfo;
	}
	
	/*
	 * 去添加页面
	 */
	@RequestMapping("toAddProject")
	public String toAddProject(HttpServletRequest request) {
		return "sysmanager/projectManage/addProject";
	}
	
	/*
	 * 添加/编辑时验证   编号，项目名称唯一
	 */
	@RequestMapping("toProjectCheck")
	@ResponseBody
	public int toProjectCheck(String projectId,String projectNo,String projectName) {
		int flag = 0 ;
		//验证项目编号是否唯一
		int i = projectService.toProjectCheckProjectNo(projectId,projectNo);
		//验证项目名称是否唯一
		int j = projectService.toProjectCheckProjectName(projectId,projectName);
		if(i!=0 && j!=0){
			flag = 1;	//项目编号和名称重复
		}else if(i!=0){
			flag = 2;	//项目编号重复
		}else if(j!=0){
			flag = 3;	//项目名称重复
		}
		return flag;
	}
	/*
	 * 添加项目
	 */
	@RequestMapping("addProject")
	@ResponseBody
	public boolean addProject(HttpServletRequest request,ProjectManage pm) {
		boolean b  = false;
		try {
			pm.setProjectId(KeyUtil.getGuidID());
			pm.setDataStatus(Constant.STATUS_Y);
			pm.setCreateBy(SessionUser.getLoginUserName(request));
			pm.setCreateDate(new Date());
			b = projectService.addProject(pm);
			logger.info(MessageUtil.MessageUtil("项目管理","添加", pm));
		}catch (Exception e) {
			logger.error("添加项目管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 去修改页面
	 */
	@RequestMapping("toUpdateProject")
	public String toUpdateProject(String projectId, HttpServletRequest request) {
		ProjectManage project = projectService.getProjectByID(projectId);
		request.setAttribute("project", project);
		return "sysmanager/projectManage/updateProject";
	}
	/*
	 * 修改项目数据
	 */
	@RequestMapping("updateProject")
	@ResponseBody
	public boolean updateProject(ProjectManage pm,HttpServletRequest request,String startUpdateReason,String endUpdateReason) {
		boolean b  = false;
		try {
			//添加修改日志
			if(startUpdateReason != null && startUpdateReason != ""){
				UpdateLog updateLog = new UpdateLog();
				//得到项目修改前的合同开始时间
				String ContractStartDate = projectService.getStartDate(pm.getProjectId());
				updateLog.setUpdateId(KeyUtil.getGuidID());
				updateLog.setForeignId(pm.getProjectId());
				updateLog.setFieldName("合同开始时间");
				updateLog.setOldValue(ContractStartDate);
				updateLog.setNewValue(pm.getContractStartDate());
				updateLog.setUpdateReason(startUpdateReason);
				updateLog.setUpdatePersion(SessionUser.getLoginUserName(request));
				updateLog.setUpdateDate(new Date());
				//添加修改日志
				projectService.updateReasonLog(updateLog);
			}
			if(endUpdateReason != null && endUpdateReason != ""){
				UpdateLog updateLog = new UpdateLog();
				//得到项目修改前的合同结束时间
				String ContractEndDate = projectService.getEndDate(pm.getProjectId());
				
				updateLog.setUpdateId(KeyUtil.getGuidID());
				updateLog.setForeignId(pm.getProjectId());
				updateLog.setFieldName("合同结束时间");
				updateLog.setOldValue(ContractEndDate);
				updateLog.setNewValue(pm.getContractStartDate());
				updateLog.setUpdateReason(endUpdateReason);
				updateLog.setUpdatePersion(SessionUser.getLoginUserName(request));
				updateLog.setUpdateDate(new Date());
				//添加修改日志
				projectService.updateReasonLog(updateLog);
			}
			pm.setLastUpdateBy(SessionUser.getLoginUserName(request));
			pm.setLastUpdateDate(new Date());
			b = projectService.updateProject(pm);
			logger.info(MessageUtil.MessageUtil("项目管理","编辑", pm));
		}catch (Exception e) {
			logger.error("编辑项目管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 删除项目数据
	 */
	@RequestMapping("deleteProject")
	@ResponseBody
	public boolean deleteProject(String projectId, HttpServletRequest request) {
		boolean b = false;
		try {
			b = projectService.deleteProject(projectId);
			logger.info(MessageUtil.MessageUtil("项目管理","删除", projectId));
		} catch (Exception e) {
			logger.error("删除项目管理信息时存在异常：",e);
		}
		return b;
	}
	/*
	 * 项目状态启用禁用
	 */
	@RequestMapping("updateStatus")
	@ResponseBody
	public boolean updateStatus(String dataStatus,String projectId) {
		boolean b = false;
		try {
			b = projectService.updateStatus(dataStatus,projectId);
			logger.info(MessageUtil.MessageUtil("项目管理","修改状态", projectId));
		} catch (Exception e) {
			logger.error("修改项目状态时存在异常：",e);
		}
		return b;
	}
	/*
	 * 查看项目日志
	 */
	@RequestMapping("toDetailLog")
	public String toDetailLog(String projectId, HttpServletRequest request) {
		//得到所有的数据
		ProjectManage project = projectService.getProjectByID(projectId);
		//查看项目的日志
		List<UpdateLog> updateLog = projectService.getUpdateLog(projectId);
		request.setAttribute("project", project);
		request.setAttribute("updateLog", updateLog);
		return "sysmanager/projectManage/detailLog";
	}
}
