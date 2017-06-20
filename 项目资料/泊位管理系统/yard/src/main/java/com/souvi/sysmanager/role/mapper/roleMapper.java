package com.souvi.sysmanager.role.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.right.entity.Right;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.systemConfig.entity.LeftTree;

public interface roleMapper {

	/*
	 * 角色列表&模糊查询
	 */
	List<Role> getRoleList(@Param("role")Role role);
	
	/*
	 * 添加/编辑时验证用户名唯一
	 */
	int checkRoleName(@Param("roleId")String roleId, @Param("roleName")String roleName);

	/*
	 *  角色添加
	 */
	void insertRole(@Param("role")Role role);
	
	/*
	 * 通过ID得到角色信息
	 */
	Role getRoleByID(@Param("roleId")String roleId);
	
	/*
	 * 编辑角色信息
	 */
	void updateRole(@Param("role")Role role);
	
	/*
	 * 角色删除
	 */
	int deleteRole(@Param("roleId")String roleId);

	/*
	 * 通过角色ID查到角色已分配的权限
	 */
	List<Right> getRightById(@Param("roleId")String roleId);

	/*
	 * 先删除原有权限
	 */
	void deleteAssignRight(@Param("roleId")String roleId);

	/*
	 * 再添加新权限
	 */
	void insertRight(@Param("roleRightId")String roleRightId, @Param("rightId")String rightId, @Param("roleId")String roleId);
	
	List<Map<String, Object>> queryAllRoleRight();

	List<LeftTree> loadTree(@Param("roleId")String roleId);

	void updateLast(@Param("role")Role role);

}
