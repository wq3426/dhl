package com.souvi.common;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;

import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.user.entity.User;

public class SessionUser {

	public static User getLoginUser(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (User) session.getAttribute(Constant.USER_SESSION_NAME);
	}
	//登录名
	public static String getLoginUserName(HttpServletRequest request) {
		return getLoginUser(request).getUserName();
	}
	//登录Id
	public static String getLoginUserId(HttpServletRequest request) {
		return getLoginUser(request).getUserId();
	}
	//邮箱
	public static String getLoginUserEmail(HttpServletRequest request) {
		return getLoginUser(request).getUserName();
	}
	//岗位
	public static String getLoginUserStation(HttpServletRequest request) {
		return getLoginUser(request).getStation();
	}
	public static List<Role> getLoginUserRole(HttpServletRequest request) {
		HttpSession session = request.getSession();
		return (List<Role>) session.getAttribute(Constant.USER_SESSION_ROLE);
	}
	//角色Id
	public static String getLoginUserRoleId(HttpServletRequest request){
		List<Role> roleList= getLoginUserRole(request);
		String[] array = new String[roleList.size()];
		int ia =0;
		for(Role role : roleList){
			array[ia]=role.getRoleId();
			ia++;
		}
		return StringUtils.join(array, ",");
	}
	//角色名称
	public static String getLoginUserRoleName(HttpServletRequest request){
		List<Role> roleList= getLoginUserRole(request);
		String[] array = new String[roleList.size()];
		int ia =0;
		for(Role role : roleList){
			array[ia]=role.getRoleName();
			ia++;
		}
		return StringUtils.join(array, ",");
	}
}
