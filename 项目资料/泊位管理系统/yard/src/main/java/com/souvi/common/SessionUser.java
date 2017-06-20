package com.souvi.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

	public static String getLoginUserName(HttpServletRequest request) {
		return getLoginUser(request).getUser_name();
	}

	public static String getLoginUserId(HttpServletRequest request) {
		return getLoginUser(request).getUser_id();
	}

	/**
	 * 获取当用登录用户拥有的资产等级列表
	 */
	@SuppressWarnings("unchecked")
	public static List<String> getLoginUserAssetClassList(HttpServletRequest request) {
		HttpSession session = request.getSession();
		List<Role> roleList = (List<Role>) session.getAttribute(Constant.USER_SESSION_ROLE);
		Set<String> assetClassSet = new HashSet<String>();
		for (Role role : roleList) {
			String assetClass = role.getAssetClass();
			if (StringUtils.isNotBlank(assetClass)) {
				assetClassSet.addAll(Arrays.asList(assetClass.split(",")));
			}
		}
		return new ArrayList<String>(assetClassSet);
	}
}
