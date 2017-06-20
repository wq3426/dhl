package com.souvi.sysmanager.berth.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.souvi.common.DateUtil;
import com.souvi.common.SessionUser;
import com.souvi.common.ValidationUtil;
import com.souvi.common.validator.AddValidator;
import com.souvi.common.validator.UpdateValidator;
import com.souvi.sysmanager.basic.BasicController;
import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.berth.service.BerthService;
import com.souvi.sysmanager.user.entity.User;

/**
 * 泊位管理
 * @author wq3426
 *
 */
@Controller
@RequestMapping("/berth")
public class BerthController extends BasicController {
	//属于每个Controller类的日志对象
	private static final Logger logger = LoggerFactory.getLogger(BerthController.class);
	
	@Autowired
	private BerthService berthService;
	
	//泊位列表查询-分页
	@RequestMapping(value="/selectBerthList", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject selectBerthList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") @Min(value=1, message="page最小为1") int page, 
			@RequestParam(required=false, defaultValue="10") @Min(value=1, message="pageSize最小为1") int pageSize,
			@RequestParam(required=false, defaultValue="") String berth_number){
		try {
			// 使用分页工具类进行分页
//			PageHelper.startPage(page, pageSize);
			List<BerthInfo> berthList = berthService.selectBerthList(berth_number);
//			PageInfo<BerthInfo> pageInfo = new PageInfo<BerthInfo>(berthList);
			
			setResultJSON(1, "", net.sf.json.JSONArray.fromObject(berthList));
			
			logger.info("查询泊位列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位号校验
	@RequestMapping(value="/checkBerthNumber", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject checkBerthNumber(HttpServletRequest request, @RequestParam(required=true) String berth_number){
		try {
			JSONObject data = new JSONObject();
			
			int is_exist = berthService.checkBerthNumberIsExist(berth_number);
			data.put("is_exist", is_exist);// 1 已经存在 0 不存在
			
			setResultJSON(1, "", data);
			
			logger.info("校验泊位号-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位添加  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	@RequestMapping(value="/addBerth", produces="application/json;charset=UTF-8", 
			method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody JSONObject addBerth(HttpServletRequest request, @RequestBody BerthInfo berthInfo){
		try {
			//输入校验(分组：添加 AddValidator)
			ValidationUtil.validate(berthInfo, AddValidator.class);
			
			int is_exist = berthService.checkBerthNumberIsExist(berthInfo.getBerth_number());
			if(is_exist == 1){//已经存在，提示
				setResultJSON(0, "该泊位号已经存在，请检查");
			}else{
				User currentLoginUser = SessionUser.getLoginUser(request);//获取当前登录用户
				
				berthInfo.setCreater(currentLoginUser.getUser_name());
				berthInfo.setCreate_time(DateUtil.getSystemDate());
				berthInfo.setLast_updater(currentLoginUser.getUser_name());
				berthInfo.setUpdate_time(DateUtil.getSystemDate());
				berthService.addBerth(berthInfo);
				
				setResultJSON(1, "");
			}
			
			logger.info("泊位添加-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//根据id查询泊位信息 
	@RequestMapping(value="/getBerthInfoById", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getBerthInfoById(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="berth_id应该大于0") int berth_id){
		try {
			BerthInfo berthInfo = berthService.getBerthInfoById(berth_id);
			setResultJSON(1, "", net.sf.json.JSONObject.fromObject(berthInfo));
			
			logger.info("根据id查询泊位信息-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位信息修改  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	@RequestMapping(value="/updateBerthInfo", produces="application/json;charset=UTF-8", 
			method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody JSONObject updateBerthInfo(HttpServletRequest request, @RequestBody BerthInfo berthInfo){
		try {
			//输入校验(分组：修改 UpdateValidator)
			ValidationUtil.validate(berthInfo, UpdateValidator.class);
			
			int is_exist = berthService.checkUpdateBerthNumberIsExist(berthInfo.getBerth_id(), berthInfo.getBerth_number());
			if(is_exist == 1){//已经存在，提示
				setResultJSON(0, "该泊位号已经存在，请检查");
			}else{
				User currentLoginUser = SessionUser.getLoginUser(request);//获取当前登录用户
				
				berthInfo.setLast_updater(currentLoginUser.getUser_name());
				berthInfo.setUpdate_time(DateUtil.getSystemDate());
				berthService.updateBerthInfo(berthInfo);
				
				setResultJSON(1, "", "");
			}
			
			logger.info("泊位信息修改-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位信息删除 当该泊位已经与供应商或者某个计划关联过，提示不允许删除
	@RequestMapping(value="/deleteBerthInfo", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject deleteBerthInfo(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="berth_id应该大于0") int berth_id){
		try {
			boolean has_relation = berthService.checkRelationWithSupplyAndPlan(berth_id);//检查是否与泊位-供应商中间表、计划表有关联
			if(has_relation){//存在关联，不允许删除
				setResultJSON(0, "该泊位已经与供应商或者某个计划关联，不允许删除");
			}else{
				berthService.deleteBerthInfo(berth_id);
				setResultJSON(1, "", "");
			}
			
			logger.info("泊位信息删除-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/********************app接口**********************/
	/**
	 * 查询泊位状态 0 空闲 1 忙碌
	 * 
	 * @param request
	 * @param berth_number
	 * @return
	 */
	@RequestMapping(value="/getBerthStatus", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getBerthStatus(HttpServletRequest request, @RequestParam(required=true) String berth_number){
		try {
			if(isSystemUser(request)){
//				berthService.getBerthStatus(berth_number);
			}else{
				setResultJSON(0, "用户名校验失败，不允许进行操作");
			}

			logger.info("查询泊位状态-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
}
