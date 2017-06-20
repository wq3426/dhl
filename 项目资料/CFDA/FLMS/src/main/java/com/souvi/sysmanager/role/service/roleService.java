package com.souvi.sysmanager.role.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.common.KeyUtil;
import com.souvi.sysmanager.right.listener.RightListener;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.role.mapper.roleMapper;
import com.souvi.sysmanager.systemConfig.entity.LeftTree;

@Service
public class roleService{
	
	@Autowired
	private roleMapper roleMapper;
	
	/*
	 * 角色列表&模糊查询
	 */
	public List<Role> getRoleList(Role role) {
		return roleMapper.getRoleList(role);
	}
	
	/*
	 * 添加/编辑时验证用户名唯一
	 * */
	public int checkRoleName(String roleId, String roleName) {
		return roleMapper.checkRoleName(roleId,roleName);
	}

	/*
	 *  角色添加
	 */
	public void insertRole(Role role) {
		roleMapper.insertRole(role);
	}
	
	/*
	 * 通过ID得到角色信息
	 */
	public Role getRoleByID(String roleId) {
		return roleMapper.getRoleByID(roleId);
	}
	
	/*
	 * 编辑角色信息
	 */
	public void updateRole(Role role) {
		roleMapper.updateRole(role);
	}
	
	/*
	 * 角色删除
	 */
	public int deleteRole(String roleId) {
		return roleMapper.deleteRole(roleId);
	}

	/*
	 * 进行权限分配
	 */
	public boolean roleAssignRight(String rightIds, String roleId) {
		try {
			List<String> rightIdList = new ArrayList<String>();
			//先删除原有权限
			roleMapper.deleteAssignRight(roleId);
			if(rightIds!=null&&rightIds!=""){
				
				String[] split = rightIds.split(",");
				for (String rightId : split) {
					rightIdList.add(rightId);
					//生成主键
					String RoleRightId = KeyUtil.getGuidID();
					//再给角色添加新权限
					roleMapper.insertRight(RoleRightId,rightId,roleId);
				}
				RightListener.addOrUpdateRoleRight(roleId,rightIdList);
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public List<LeftTree> loadTree(String roleId) {
		return roleMapper.loadTree(roleId);
	}

	public void updateLast(Role role) {
		roleMapper.updateLast(role);
	}
}
