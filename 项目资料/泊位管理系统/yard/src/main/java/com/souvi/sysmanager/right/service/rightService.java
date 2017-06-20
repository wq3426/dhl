package com.souvi.sysmanager.right.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.right.entity.Right;
import com.souvi.sysmanager.right.mapper.rightMapper;
import com.souvi.sysmanager.systemConfig.entity.LeftTree;

@Service
public class rightService{
	
	@Autowired
	private rightMapper rightMapper;

	/*
	 * 获取当前登录的用户的权限
	 */
	public List<Right> getrightList(String roleName) {
		return rightMapper.getrightList(roleName);
	}

	/*
	 * 获取所有的权限名称
	 */
	public List<Right> getAllRightList() {
		return rightMapper.getAllRightList();
	}
	
	/*
	 * 修改权限
	 */
	public void updateRight(String roleId, String rightId) {
		//先通过角色ID删除之前的角色权限
		rightMapper.deleteRightByRoleId(roleId);
		//再通过角色ID添加新的角色权限
		for(int i=0;i<rightId.length();i++){
			String[] split = rightId.split(",");
			rightMapper.insertRoleRight(roleId,split);
		}
	}
	
	/*
	 *  添加权限
	 */
	public int insertRight(Right right) {
		return rightMapper.insertRight(right);
	}
	
	/*
	 * 删除权限
	 */
	public void deleteRight(String rightId) {
		//删除权限
		rightMapper.deleteRight(rightId);
		//删除角色和权限的关联
		rightMapper.deleteMiddleByRightId(rightId);
	}

	/*
	 * 通过ID获取权限信息
	 */
	public Right getRightInfo(String rightId) {
		return rightMapper.getRightInfo(rightId);
	}
	
	/*
	 * 修改权限
	 */
	public int updateRight(Right right) {
		return rightMapper.updateRight(right);
	}

	public List<LeftTree> loadTree() {
		return rightMapper.loadTree();
	}

	public int checkRightManage(String rightId,String rightName, String rightCode) {
		return rightMapper.checkRightManage(rightId,rightName,rightCode);
	}

}
