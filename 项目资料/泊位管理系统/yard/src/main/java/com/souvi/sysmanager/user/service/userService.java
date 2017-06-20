package com.souvi.sysmanager.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.common.KeyUtil;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.mapper.UserMapper;

@Service
public class userService{

	@Autowired
	private UserMapper userMapper;

	/*
	 * 查询用户列表
	 */
	public List<User> getUserList(User user) {
		return userMapper.getUserList(user);
	}

	/*
	 * 添加用户
	 */
	public void insertUser(User dto) {
		userMapper.insertUser(dto);
	}

	/*
	 * 通过用户ID得到用户信息
	 */
	public User getUserByID(String userId) {
		return userMapper.getUserByID(userId);
	}

	/*
	 * 编辑用户信息
	 */
	public void updateUser(User dto) {
		userMapper.updateUser(dto);
	}
	
	/*
	 * 删除用户
	 */
	public int deleteUser(User dto) {
		return userMapper.deleteUser(dto);
	}

	/*
	 * 验证用户名唯一
	 */	
	public int checkUserName(String userId, String userName) {
		return userMapper.checkUserName(userId,userName);
	}

	/*
	 * 验证工号唯一
	 */
	public int checkIdnum(String userId, String idnum) {
		return userMapper.checkIdnum(userId,idnum);
	}

	/*
	 * 得到所有的角色
	 */
	public List<Role> getAllRole(String userId) {
		return userMapper.getAllRole(userId);
	}

	/*
	 * 通过用户ID查到用户已分配的角色
	 */
	public List<Role> getUserAssRole(String userId) {
		return userMapper.getUserAssRole(userId);
	}

	/*
	 * 分配角色
	 */
	public boolean userAssignROle(String roleIds, String userId) {
		try {
			//先删除原有角色
			userMapper.deleteAssignROle(userId);
			if(roleIds!=null&&roleIds!=""){
				String[] split = roleIds.split(",");
				for (String roleId : split) {
					String UserRoleId = KeyUtil.getGuidID();
					//在添加新角色
					userMapper.insertUserAssignROle(UserRoleId,roleId,userId);
				}
			}
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	/*
	 * 查询登录用户 
	 */
	public User getUserOnLogin(String userName,String password){
		return this.userMapper.getUserOnLogin(userName, password);
	}
	/*
	 * 查询Infocenter用户信息
	 */
	public List<User> checkUser(User user) {
		return this.userMapper.checkUser(user);
	}
	/*
	 * 查询用户是否存在
	 */
	public String checkUserExist(String userId) {
		return this.userMapper.checkUserExist(userId);
	}
	/*
	 * 通过用户名得到Infocenter用户信息
	 */
	public User checkInUser(String inId) {
		return this.userMapper.checkInUser(inId);
	}

	public int checkUserAsset(String userId) {
		return this.userMapper.checkUserAsset(userId);
	}
	/*
	 * 通过用户MappingId得到用户信息
	 */
	public User getUserByMppingID(String mappingId) {
		return userMapper.getUserByMppingID(mappingId);
	}

	/*****************************下面为业务实现代码，上面的为无用代码，暂时保留供参考****************************/
	
	//获取登录用户
	public User getUserByUsernameAndPassword(String user_name, String password) {
		return userMapper.getUserByUsernameAndPassword(user_name, password);
	}

}
