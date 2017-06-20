package com.souvi.sysmanager.projectManage.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.projectManage.entity.UpdateLog;
import com.souvi.sysmanager.projectManage.mapper.ProjectMapper;

@Service
public class ProjectService {

	@Autowired
	private ProjectMapper projectMapper;
	
	/*
	 * 项目列表
	 */
	public List<ProjectManage> getProjectList(ProjectManage pm) {
		return projectMapper.getProjectList(pm);
	}
	/*
	 * 添加/编辑时验证   验证项目编号是否唯一
	 */
	public int toProjectCheckProjectNo(String projectId,String projectNo) {
		return projectMapper.toProjectCheckProjectNo(projectId,projectNo);
	}
	/*
	 * 添加/编辑时验证   验证项目名称是否唯一
	 */
	public int toProjectCheckProjectName(String projectId,String projectName) {
		return projectMapper.toProjectCheckProjectName(projectId,projectName);
	}
	/*
	 * 添加项目
	 */
	public boolean addProject(ProjectManage pm) {
		return projectMapper.addProject(pm);
	}
	/*
	 * 去修改页面
	 */
	public ProjectManage getProjectByID(String projectId) {
		return projectMapper.getProjectByID(projectId);
	}
	/*
	 * 得到项目修改前的合同开始时间
	 */
	public String getStartDate(String projectId) {
		return projectMapper.getStartDate(projectId);
	}
	/*
	 * 得到项目修改前的合同结束时间
	 */
	public String getEndDate(String projectId) {
		return projectMapper.getEndDate(projectId);
	}
	/*
	 * 修改项目数据
	 */
	public boolean updateProject(ProjectManage pm) {
		return projectMapper.updateProject(pm);
	}
	/*
	 * 删除项目数据
	 */
	public boolean deleteProject(String projectId) {
		return projectMapper.deleteProject(projectId);
	}
	/*
	 * 添加修改日志
	 */
	public void updateReasonLog(UpdateLog updateLog) {
		projectMapper.updateReasonLog(updateLog);
	}
	/*
	 * 更改项目的状态
	 */
	public boolean updateStatus(String dataStatus, String projectId) {
		return projectMapper.updateStatus(dataStatus,projectId);
	}
	/*
	 * 查看项目的日志
	 */
	public List<UpdateLog> getUpdateLog(String projectId) {
		return projectMapper.getUpdateLog(projectId);
	}
	
}
