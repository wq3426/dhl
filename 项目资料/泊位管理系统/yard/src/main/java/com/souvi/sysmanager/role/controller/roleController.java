package com.souvi.sysmanager.role.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.souvi.common.Constant;
import com.souvi.common.DateUtil;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.role.service.roleService;
import com.souvi.sysmanager.systemConfig.entity.LeftTree;
import com.souvi.sysmanager.user.entity.User;

@Controller
@RequestMapping("role")
public class roleController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private roleService roleService;
	
	/*
	 * 去显示页面
	 */
	@RequestMapping("toRoleList")
	public String toUserList(HttpServletRequest request) {
		return "sysmanager/role/roleList";
	}
	
	/*
	 * 角色列表&模糊查询
	 */
	@RequestMapping("roleList")
	@ResponseBody
	public PageInfo<Role> roleList(HttpServletRequest request,Role role,
			@RequestParam(required = false, defaultValue = "1") int page,
			@RequestParam(required = false, defaultValue = "2") int count, Model model){  
		PageHelper.startPage(page, count);
		
		List<Role> Rlist = roleService.getRoleList(role);
		
		PageInfo<Role> pageInfo = new PageInfo<Role>(Rlist);
		
		return pageInfo;
	}
	
	/*
	 * 去添加页面
	 */
	@RequestMapping("toADD")
	public String toADD(HttpServletRequest request){
		return "sysmanager/role/addRole";
	}
	
	/*
	 * 添加/编辑时验证用户名唯一
	 */
	@RequestMapping("addRoleClick")
	@ResponseBody
	public boolean addRoleClick(String roleId,String roleName){
		boolean flag = true;
		//验证用户名唯一
		int i = roleService.checkRoleName(roleId,roleName);
		if(i>0){
			flag = false;
		}
		return flag;
	}
	
	/*
	 *  角色添加
	 */
	@RequestMapping("addRole")
	public String addRole(Role role,HttpServletRequest request){
		try {
			//生成主键
			role.setRoleId(KeyUtil.getGuidID());
			
			//获取Session  得到登录人信息
	        HttpSession session = request.getSession();  
	        User user =  (User) session.getAttribute(Constant.USER_SESSION_NAME); 
	        if(null != user){
	        	role.setLastUpdateBy(user.getUser_name());
	        	role.setCreateBy(user.getUser_name());
	        }
			
	        //添加时的时间
	      	String systemDate = DateUtil.getSystemDate();
	      	//创建时间
	        role.setCreateDate(systemDate);
	        //最后操作时间
	        role.setLastUpdateDate(systemDate);
			roleService.insertRole(role);
			logger.info(MessageUtil.MessageUtil("角色管理","添加", role));
		} catch (Exception e) {
			logger.info("添加角色数据时存在异常：",e);
		}
		return "redirect:/role/toRoleList.do";
	}
	
	/*
	 * 去编辑页面
	 */
	@RequestMapping("toUpdateRole")
	public String ToUpdate(String roleId,HttpServletRequest request){
		Role role = roleService.getRoleByID(roleId);
		
		String assetClass = role.getAssetClass();
		if(assetClass != null && assetClass != ""){
			
			String[] split = assetClass.split(",");
			List<String> classList = new ArrayList<String>();
			for(String aclass : split){
				classList.add(aclass);
			}
			request.setAttribute("classList", classList);
		}
		
		request.setAttribute("role", role);
		return "sysmanager/role/updateRole";
	}
	
	/*
	 * 编辑角色信息
	 */
	@RequestMapping("updateRole")
	public String updateRole(Role role,HttpServletRequest request){
		try {
			role.setLastUpdateDate(DateUtil.getSystemDate());
			
			//获取Session  得到登录人信息
	        HttpSession session = request.getSession();  
	        User user =  (User) session.getAttribute(Constant.USER_SESSION_NAME); 
	        if(null != user){
	        	role.setLastUpdateBy(user.getUser_name());
	        }
	        roleService.updateRole(role);
	        logger.info(MessageUtil.MessageUtil("角色管理","编辑", role));
		} catch (Exception e) {
			logger.info("编辑角色的数据时存在异常：",e);
		}
		return "redirect:/role/toRoleList.do";
	}
	
	/*
	 * 角色删除
	 */
	@RequestMapping("deleteRole")
	@ResponseBody
	public boolean deleteRole(String roleId){
		try {
			logger.info(MessageUtil.MessageUtil("角色管理","删除", roleId));
			return roleService.deleteRole(roleId) == 1;
		} catch (Exception e) {
			logger.info("角色删除的时候存在异常",e);
			return false;
		}
		
	}

	/*
	 * 用Ajax返回权限信息
	 */
	@RequestMapping("toAssignRight")
	@ResponseBody
	public String toAssignRight(HttpServletRequest request,String roleId,Model model){
		//获取用户的权限信息
		List<LeftTree> loadTree = roleService.loadTree(roleId);
		String string = JSONArray.toJSONString(loadTree);
		return string;
	}
	
	/*
	 * 分配权限
	 */
	@RequestMapping("roleAssignRight")
	@ResponseBody
	public Boolean roleAssignRight(String rightIds,String roleId,HttpServletRequest request){
		try {
			//进行权限分配
			boolean b = roleService.roleAssignRight(rightIds,roleId);
			
			//获取Session  得到登录人信息
	        HttpSession session = request.getSession();  
	        User user =  (User) session.getAttribute(Constant.USER_SESSION_NAME); 
	        Role role = new Role();
	        if(null != user){
	        	role.setLastUpdateBy(user.getUser_name());
	        }
	        role.setLastUpdateDate(DateUtil.getSystemDate());
	        role.setRoleId(roleId);
	        //更改角色的最后操作状态
	        roleService.updateLast(role);
			return b;
		} catch (Exception e) {
			logger.info("分配权限的时候存在异常",e);
			return false;
		}
		
	}
}
