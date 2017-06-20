package com.souvi.sysmanager.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.user.entity.User;



public interface UserMapper {

	/*
	 * 查询用户列表
	 */
	List<User> getUserList(@Param("user")User user);
	
	/*
	 * 添加用户
	 */
	void insertUser(@Param("dto")User dto);

	/*
	 * 通过用户ID得到用户信息
	 */
	User getUserByID(@Param("userId")String userId);

	/*
	 * 编辑用户信息
	 */
	void updateUser(@Param("dto")User dto);
	
	/*
	 * 删除用户
	 */
	int deleteUser(@Param("dto")User dto);

	/*
	 * 验证用户名唯一
	 */	
	int checkUserName(@Param("userId")String userId, @Param("userName")String userName);

	/*
	 * 验证工号唯一
	 */
	int checkIdnum(@Param("userId")String userId, @Param("idnum")String idnum);

	/*
	 * 得到所有的角色
	 */
	List<Role> getAllRole(@Param("userId")String userId);

	/*
	 * 通过用户ID查到用户已分配的角色
	 */
	List<Role> getUserAssRole(@Param("userId")String userId);

	/*
	 * 先删除原有权限
	 */
	void deleteAssignROle(@Param("userId")String userId);

	/*
	 * 在添加新权限
	 */
	void insertUserAssignROle(@Param("UserRoleId")String UserRoleId,@Param("roleId")String roleId, @Param("userId")String userId);
	/*
	 * 查询登录用户 
	 */
	User getUserOnLogin(@Param("userName")String userName,@Param("password")String password);
	/*
	 * 查询Infocenter用户信息
	 */
	List<User> checkUser(@Param("user")User user);
	/*
	 * 查询用户是否存在
	 */
	String checkUserExist(@Param("userId")String userId);
	/*
	 * 通过用户名得到Infocenter用户信息
	 */
	User checkInUser(@Param("inId")String inId);

	int checkUserAsset(@Param("userId")String userId);
	/*
	 * 通过用户MappingID得到用户信息
	 */
	User getUserByMppingID(@Param("mappingId")String mappingId);

	/*****************************下面为业务实现代码，上面的为无用代码，暂时保留供参考****************************/
	
	//获取登录用户
	User getUserByUsernameAndPassword(@Param("user_name") String user_name, @Param("password") String password);
}
