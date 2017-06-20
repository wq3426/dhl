package com.souvi.sysmanager.right.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.right.entity.Right;
import com.souvi.sysmanager.systemConfig.entity.LeftTree;

public interface rightMapper {

	/*
	 * 获取当前登录的用户的权限
	 */
	List<Right> getrightList(String roleName);

	/*
	 * 获取所有的权限名称
	 */
	List<Right> getAllRightList();

	/*
	 *  再通过角色ID添加新的角色权限
	 */
	void insertRoleRight(String roleId, String[] split);

	/*
	 *  先通过角色ID删除之前的角色权限
	 */
	void deleteRightByRoleId(String roleId);

	/*
	 *  添加权限
	 */
	int insertRight(@Param("right")Right right);
	
	/*
	 * 删除权限
	 */
	void deleteRight(@Param("rightId")String rightId);

	/*
	 * 通过权限ID获取权限信息
	 */
	Right getRightInfo(@Param("rightId")String rightId);
	
	/*
	 * 修改权限
	 */
	int updateRight(@Param("right")Right right);
	
	/*
	 * 删除角色和权限的关联
	 */
	void deleteMiddleByRightId(@Param("rightId")String rightId);

	List<LeftTree> loadTree();

	int checkRightManage(@Param("rightId")String rightId,@Param("rightName")String rightName, @Param("rightCode")String rightCode);

}
