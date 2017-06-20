package com.souvi.sysmanager.projectManage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.projectManage.entity.ProjectManage;
import com.souvi.sysmanager.projectManage.entity.UpdateLog;

public interface ProjectMapper {
	
	/*
	 * 项目列表
	 */
	List<ProjectManage> getProjectList(@Param("pm")ProjectManage pm);
	/*
	 * 添加/编辑时验证   验证项目编号是否唯一
	 */
	int toProjectCheckProjectNo(@Param("projectId")String ProjectId,@Param("projectNo")String projectNo);
	/*
	 * 添加/编辑时验证   验证项目名称是否唯一
	 */
	int toProjectCheckProjectName(@Param("projectId")String ProjectId,@Param("projectName")String projectName);
	/*
	 * 添加项目
	 */
	boolean addProject(@Param("pm")ProjectManage pm);
	/*
	 * 去修改页面
	 */
	ProjectManage getProjectByID(@Param("projectId")String projectId);
	/*
	 * 得到项目修改前的合同开始时间
	 */
	String getStartDate(@Param("projectId")String projectId);
	/*
	 * 得到项目修改前的合同结束时间
	 */
	String getEndDate(@Param("projectId")String projectId);
	/*
	 * 修改项目数据
	 */
	boolean updateProject(@Param("pm")ProjectManage pm);
	/*
	 * 删除项目数据
	 */
	boolean deleteProject(@Param("projectId")String projectId);
	/*
	 * 添加修改日志
	 */
	void updateReasonLog(@Param("updateLog")UpdateLog updateLog);
	/*
	 * 更改项目的状态
	 */
	boolean updateStatus(@Param("dataStatus")String dataStatus, @Param("projectId")String projectId);
	/*
	 * 查看项目的日志
	 */
	List<UpdateLog> getUpdateLog(@Param("projectId")String projectId);
	
}
