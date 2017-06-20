package com.souvi.sysmanager.right.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.souvi.common.Constant;
import com.souvi.common.KeyUtil;
import com.souvi.common.MessageUtil;
import com.souvi.sysmanager.right.entity.Right;
import com.souvi.sysmanager.right.listener.RightListener;
import com.souvi.sysmanager.right.service.rightService;
import com.souvi.sysmanager.user.entity.User;

@Controller
@RequestMapping("right")
public class rightController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private rightService rightService;
	
	/*
	 * 获取当前登录的用户的权限
	 */
	@RequestMapping("rightList")
	public String rightList(HttpServletRequest request,Model model){
		// 获取Session 得到登录人信息
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute(Constant.USER_SESSION_NAME);
		String roleName = "";
		// 添加操作人
		if (null != user) {
			roleName = user.getUser_name();
		}
		//获得所有的权限
		List<Right> rightList = rightService.getAllRightList();
		request.setAttribute("rightList", rightList);
		
		//获取用户的权限信息
		model.addAttribute("rightTree", JSONObject.toJSONString(rightService.loadTree()));
		return "sysmanager/right/rightList";
	}
	
	/*
	 * 用Ajax返回权限信息
	 */
	@RequestMapping("rightInfo")
	@ResponseBody
	public Right rightInfo(String rightId){
		//获取当前的权限信息
		Right rightInfo = rightService.getRightInfo(rightId);
		return rightInfo;
	}
	
	/*
	 * 验证权限名称，权限代码是否唯一
	 */
	@RequestMapping("checkRightManage")
	@ResponseBody
	public boolean checkRightManage(String rightId,String rightName,String rightCode){
		try {
			int i = rightService.checkRightManage(rightId,rightName,rightCode);
			if(i>0){
				return false;
			}else{
				return true;
			}
		} catch (Exception e) {
			logger.info("验证权限名称，权限代码是否唯一时出现异常:", e);
			return false;
		}
		
		
	}
	
	/*
	 * 修改&添加权限
	 */
	@RequestMapping(value = "/rightManage")
	@ResponseBody
	public boolean rightManage(Right right){
		try {
			String rightId = right.getRightId();
			int i = 0;
			if(rightId != "" && rightId != null){
				i = rightService.updateRight(right);
				RightListener.updateRight(right);
				logger.info(MessageUtil.MessageUtil("权限管理","修改", right));
			}else{
				//生成主键
				right.setRightId(KeyUtil.getGuidID());
				i = rightService.insertRight(right);
				RightListener.addRight(right);
				logger.info(MessageUtil.MessageUtil("权限管理","添加", right));
			}
			if(i>0){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			logger.info("修改&添加权限时出现异常:", e);
			return false;
		}
	}
	
	/*
	 * 删除权限
	 */
	@RequestMapping("deleteRight")
	public String deleteRight(String rightId){
		try {
			rightService.deleteRight(rightId);
			logger.info(MessageUtil.MessageUtil("权限管理","删除", rightId));
		} catch (Exception e) {
			logger.info("删除权限出现异常:", e);
		}
		return "redirect:/right/rightList.do";
	}
	
}
