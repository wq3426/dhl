package com.souvi.sysmanager.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.Constant;
import com.souvi.common.DateUtil;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.common.RSA;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.user.entity.User;
import com.souvi.sysmanager.user.service.userService;

@Controller
@RequestMapping("user")
public class userController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private userService userService;

	/*
	 * 去显示页面
	 */
	@RequestMapping("toUserList")
	public String toUserList() {
		return "sysmanager/user/userList";
	}

	/*
	 * 用户列表
	 */
	@RequestMapping("userList")
	@ResponseBody
	public PageInfo<User> userList(HttpServletRequest request, User user,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "10") int count, Model model) {

		PageHelper.startPage(page, count);
		// 是否删除
//		user.setIsDelete(Constant.ISDELETE_N);
		List<User> ulist = userService.getUserList(user);
		PageInfo<User> pageInfo = new PageInfo<User>(ulist);

		return pageInfo;
	}

	/*
	 * 去添加页面
	 */
	@RequestMapping("toADD")
	public String toADD() {
		return "sysmanager/user/addUser";
	}

	/*
	 * 添加/编辑时验证
	 */
	@RequestMapping("addUserClick")
	@ResponseBody
	public int addUserClick(String userId, String userName, String idnum) {
		int flag = 0;
		// 验证用户名唯一
		int i = userService.checkUserName(userId, userName);
		// 验证工号唯一
		int j = userService.checkIdnum(userId, idnum);
		if (i > 0) {
			flag = 1;
		} else {
			if (j > 0) {
				flag = 2;
			}
		}
		return flag;
	}

	/*
	 * 查询Infocenter用户信息
	 */
	@RequestMapping("queryUser")
	@ResponseBody
	public PageInfo<User> queryUser(HttpServletRequest request, User user,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "2") int count, Model model) {
		PageHelper.startPage(page, count);
		List<User> InUser = userService.checkUser(user);
		PageInfo<User> pageInfo = new PageInfo<User>(InUser);
		return pageInfo;
	}

	/*
	 * 添加用户验证是否存在
	 */
	@RequestMapping("checkUser")
	@ResponseBody
	public Map<String,Object> checkUser(String userIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String name = "";
			Boolean success = true;
			String uId[] = userIds.split(",");
			for (int i = 0; i < uId.length; i++) {
				String userId = uId[i];
				// 查询此用户是否存在
				String userName = userService.checkUserExist(userId);
				if(userName != null && userName != "") {
					name += userName + ","; 
					success = false;
				}
			}
			map.put("success",success);
			map.put("msg",name);
		} catch (Exception e) {
			logger.info("验证用户是否存在时存在异常",e);
		}
		return map;
	}

	/*
	 * 添加用户
	 */
	@RequestMapping("addUser")
	public String addUser(String userIds, HttpServletRequest request) {
		try {
			// 获取Session 得到登录人信息
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);

			String userId[] = userIds.split(",");
			for (int i = 0; i < userId.length; i++) {
				String inId = userId[i];
				// 通过用户名得到Infocenter用户信息
				User dto = userService.checkInUser(inId);
				// 添加主键
				dto.setUser_id(KeyUtil.getGuidID());
				// 添加状态
				dto.setUser_status(Constant.STATUS_Y);
				// 添加时的时间
				String systemDate = DateUtil.getSystemDate();
				// 创建时间
				dto.setCreate_time(systemDate);
				// 最后更新时间
				dto.setUpdate_time(systemDate);
				// 添加是否删除
//				dto.setIsDelete(Constant.ISDELETE_N);
				// 添加操作人
				if (null != user) {
					dto.setLast_updater(user.getUser_name());
					dto.setCreater(user.getUser_name());
				}
				userService.insertUser(dto);
				logger.info(MessageUtil.MessageUtil("用户管理","添加", dto));
			}
		} catch (Exception e) {
			logger.info("添加用户时存在异常：",e);
		}
		return "redirect:/user/toUserList.do";
	}

	/*
	 * 去修改页面
	 */
	@RequestMapping("toUpdateUser")
	public String ToUpdate(String userId, HttpServletRequest request) {
		User user = userService.getUserByID(userId);
		user.setPassword(new RSA().decode(user.getPassword()));
		request.setAttribute("user", user);
		return "sysmanager/user/updateUser";
	}

	/*
	 * 修改用户信息
	 */
	@RequestMapping("updateUser")
	public String updateUser(User dto, HttpServletRequest request) {
		try {
			// 最后更新时间
			dto.setUpdate_time(DateUtil.getSystemDate());
			// 密码加密
			String password = new RSA().encode(dto.getPassword());
			dto.setPassword(password);
			// 获取Session 得到登录人信息
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
			if (null != user) {
				dto.setLast_updater(user.getUser_name());
			}
			userService.updateUser(dto);
			logger.info(MessageUtil.MessageUtil("用户管理","修改", dto));
		} catch (Exception e) {
			logger.info("修改用户数据时存在异常：",e);
		}
		return "redirect:/user/toUserList.do";
	}

	/*
	 * 删除用户
	 */
	@RequestMapping("deleteUser")
	@ResponseBody
	public boolean deleteUser(User dto, HttpServletRequest request) throws Exception {
		try {
			// 获取Session 得到登录人信息
			HttpSession session = request.getSession();
			User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
			if (null != user) {
				dto.setLast_updater(user.getUser_name());
			}
			// 最后更新时间
			dto.setUpdate_time(DateUtil.getSystemDate());
//			dto.setIsDelete(Constant.ISDELETE_Y);
			//查询此用户下有没有资产
			int i = userService.checkUserAsset(dto.getUser_id());
			if(i > 0){
				logger.info(MessageUtil.MessageUtil("用户管理","此用户下有资产不能删除：", dto.getUser_id()));
				return false;
			}else{
				logger.info(MessageUtil.MessageUtil("用户管理","删除", dto.getUser_id()));
				return userService.deleteUser(dto) == 1;
			}
		} catch (Exception e) {
			logger.info(MessageUtil.MessageUtil("用户管理","删除时存在异常：",e));
			return false;
		}
		
	}

	/*
	 * 去分配角色得到角色信息
	 */
	@RequestMapping("AssignRole")
	@ResponseBody
	public PageInfo<Role> AssignRole(String userId) {
		// 得到所有的角色
		List<Role> roleList = userService.getAllRole(userId);
		PageInfo<Role> pageInfo = new PageInfo<Role>(roleList);
		return pageInfo;
	}

	/*
	 * 分配角色
	 */
	@RequestMapping("userAssignRole")
	@ResponseBody
	public Boolean userAssignRole(String roleIds, String userId) {
		try {
			// 进行角色分配
			boolean b = userService.userAssignROle(roleIds, userId);
			return b;
		} catch (Exception e) {
			logger.info("用户分配角色时存在异常：",e);
			return false;
		}
		
	}
}
