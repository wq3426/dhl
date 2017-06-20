package com.souvi.sysmanager.plan.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import net.sf.json.JSONArray;

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
import com.souvi.common.ExcelUtil;
import com.souvi.common.RegExpValidatorUtils;
import com.souvi.common.SessionUser;
import com.souvi.common.ValidationUtil;
import com.souvi.common.validator.AddValidator;
import com.souvi.common.validator.AddValidator2;
import com.souvi.common.validator.UpdateValidator;
import com.souvi.sysmanager.basic.BasicController;
import com.souvi.sysmanager.plan.entity.PlanInfo;
import com.souvi.sysmanager.plan.entity.SupplierBerthMapInfo;
import com.souvi.sysmanager.plan.service.PlanService;
import com.souvi.sysmanager.user.entity.User;

/**
 * 泊位计划管理
 * @author wq3426
 *
 */
@Controller
@RequestMapping("/plan")
public class PlanController extends BasicController {
	//属于每个Controller类的日志对象
	private static final Logger logger = LoggerFactory.getLogger(PlanController.class);
	
	@Autowired
	private PlanService planService;
	
	//查询供应商-泊位及泊位状态关联列表
	@RequestMapping(value="/getSupplierBerthMapList", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getSupplierBerthMapList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") @Min(value=1, message="page最小为1") int page, 
			@RequestParam(required=false, defaultValue="10") @Min(value=1, message="pageSize最小为1") int pageSize,
			@RequestParam(required=false, defaultValue="") String supplier_name){
		
		try {
			// 使用分页工具类进行分页
//			PageHelper.startPage(page, pageSize);
			
			List<SupplierBerthMapInfo> mapList = planService.getSupplierBerthMapList(supplier_name);
			
			setResultJSON(1, "", net.sf.json.JSONArray.fromObject(mapList));
			
			logger.info("查询供应商-泊位及泊位状态关联列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 设置/修改当天的计划时间间隔
	 * 
	 * 默认计划间隔时间为8分钟，每天可调节，当所有泊位上没有计划时，则可以修改;
	 * 当所有泊位上有任何计划时不可修改。
	 * 若前一天间隔时间的调整在第二天同样适用，则第二天可不做任何修改，默认继承上一次的修改参数。
	 */
	@RequestMapping(value="/setPlanTimeLength", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject setPlanTimeLength(HttpServletRequest request, 
			@RequestParam(required=true, defaultValue="8") @Min(value=1, message="时间间隔time_length应该大于0") int time_length){
		try {
			//查询当天是否有计划被创建，没有则可以修改计划时间间隔，否则不允许修改
			boolean is_settable = planService.planTimeLengthIsSettable(DateUtil.getSystemDateTwo());
			
			if(is_settable){//当天没有计划被创建，允许修改
				//获取当前登录用户
				User currentLoginUser = SessionUser.getLoginUser(request);
				
				planService.setPlanTimeLength(currentLoginUser, DateUtil.getSystemDateTwo(), time_length);
				
				setResultJSON(1, "设置计划时间间隔成功");
			}else{//当天已有计划被创建，不允许修改
				setResultJSON(0, "当天已有计划在执行，不能再修改计划执行时间间隔");
			}
			
			logger.info("设置计划时间间隔-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//获取当天的时间间隔
	@RequestMapping(value="/getPlanTimeLength", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getPlanTimeLength(HttpServletRequest request){
		try {
			JSONObject data = new JSONObject();
			
			//获取当前登录用户
			User currentLoginUser = SessionUser.getLoginUser(request);
			
			int time_length = planService.getTimeLengthOfSystemConfig(currentLoginUser, DateUtil.getSystemDateTwo());
			
			data.put("time_length", time_length);
			
			setResultJSON(1, "获取计划时间间隔成功", data);
			
			logger.info("获取计划时间间隔-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 判断泊位计划是否可添加
	 * 
	 * 如果泊位不可用，不允许添加
	 * 如果添加预定计划固定时间是否覆盖了预定计划固定时间，不允许添加
	 * 
	 * @param request
	 * @param plan_startTime
	 * @param berth_id
	 * @return
	 */
	@RequestMapping(value="/isPlanAddable", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject isPlanAddable(HttpServletRequest request, 
			@RequestParam(required=true) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="plan_startTime时间格式不正确(yyyy-MM-dd HH:mm:ss)") String plan_startTime, 
			@RequestParam(required=true) @Min(value=1, message="berth_id应该大于0") int berth_id){
		try {
			//获取当前登录用户
			User currentLoginUser = SessionUser.getLoginUser(request);
			
			JSONObject obj = planService.isPlanAddable(0, currentLoginUser, berth_id, plan_startTime);
			
			boolean is_addable = obj.getBoolean("is_addable");
			
			if(is_addable){
				setResultJSON(1, "");
			}else{
				setResultJSON(0, obj.getString("msg"));
			}
			
			logger.info("判断泊位计划是否可添加-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 泊位计划追加  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	 * 计划添加必填入参：
	 *  - 供应商主键id        supplier_id
	 *  - 泊位主键id         berth_id
	 *  - 车牌号                                     plate_number
	 *  - 等候泊位号                             waiting_berth_number
	 *  - 计划预计开始时间（可选） plan_starttime       添加预定计划时必填，添加追加计划时不需要填写  
	 *  - 司机联系方式（可选）         driver_contact
	 *  - 浮动时间 （可选）                float_time
	 *  - 描述（可选）                         description
	 *  - 零件号（可选）                     item_number
	 *  - 零件名称（可选）                 item_name
	 *  - 零件类型（可选）                 item_type
	 *  - 车型（可选）                         vans_type
	 *  - 车型描述（可选）                 vans_description
	 *  - 标准装框数（个）（可选） box_count
	 * 
	 * @param request
	 * @param planInfo
	 * @return
	 */
	@RequestMapping(value="/addAdditionalBerthPlan", produces="application/json;charset=UTF-8", 
			consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody JSONObject addAdditionalBerthPlan(HttpServletRequest request, @RequestBody PlanInfo planInfo){
		try {
			//输入校验(分组：添加 AddValidator)
			ValidationUtil.validate(planInfo, AddValidator.class);
			
			//获取当前登录用户
			User currentLoginUser = SessionUser.getLoginUser(request);
			
			//如果泊位停用，不允许添加
			JSONObject obj = planService.isPlanAddable(1, currentLoginUser, planInfo.getBerth_id(), planInfo.getPlan_starttime());
			boolean is_addable = obj.getBooleanValue("is_addable");
			
			if(is_addable){
				planService.addBerthPlan(1, currentLoginUser, planInfo);
				
				setResultJSON(1, "");
			}else{
				setResultJSON(0, obj.getString("msg"));
			}
			
			logger.info("泊位计划追加-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 泊位计划预定-获取供应商-泊位关联的下拉列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getSupplierBerthSelectList", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getSupplierBerthSelectList(HttpServletRequest request){
		try {
			JSONArray mapArray = planService.getSupplierBerthSelectList();
			
			if(mapArray != null && mapArray.size() > 0){
				setResultJSON(1, "", mapArray);
			}else{
				setResultJSON(0, "没有可选择的供应商");
			}
			
			logger.info("泊位计划预定-获取供应商-泊位关联的下拉列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 泊位计划预定  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	 * 计划添加必填入参：
	 *  - 供应商主键id        supplier_id
	 *  - 泊位主键id         berth_id
	 *  - 车牌号                                     plate_number
	 *  - 等候泊位号                             waiting_berth_number
	 *  - 计划预计开始时间                 plan_starttime       添加预定计划时必填，添加追加计划时不需要填写  
	 *  - 司机联系方式（可选）         driver_contact
	 *  - 浮动时间 （可选）                float_time
	 *  - 描述（可选）                         description
	 *  - 零件号（可选）                     item_number
	 *  - 零件名称（可选）                 item_name
	 *  - 零件类型（可选）                 item_type
	 *  - 车型（可选）                         vans_type
	 *  - 车型描述（可选）                 vans_description
	 *  - 标准装框数（个）（可选） box_count
	 * 
	 * @param request
	 * @param planInfo
	 * @return
	 */
	@RequestMapping(value="/addScheduledBerthPlan", produces="application/json;charset=UTF-8", 
			consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody JSONObject addScheduledBerthPlan(HttpServletRequest request, @RequestBody PlanInfo planInfo){
		try {
			//输入校验(分组：添加 AddValidator AddValidator2)
			ValidationUtil.validate(planInfo, AddValidator.class, AddValidator2.class);
			
			//获取当前登录用户
			User currentLoginUser = SessionUser.getLoginUser(request);
			
			/**
			 * 判断计划是否可添加
			 * 如果泊位停用，不允许添加
			 * 如果添加预定计划固定时间是否覆盖了预定计划固定时间，不允许添加
			 */
			JSONObject obj = planService.isPlanAddable(2, currentLoginUser, planInfo.getBerth_id(), planInfo.getPlan_starttime());
			boolean is_addable = obj.getBooleanValue("is_addable");
			
			if(is_addable){
				planService.addBerthPlan(2, currentLoginUser, planInfo);
				
				setResultJSON(1, "");
			}else{
				setResultJSON(0, obj.getString("msg"));
			}
			
			logger.info("泊位计划预定-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 计划修改-获取要修改的计划信息
	 * 
	 * @param request
	 * @param plan_id
	 * @return
	 */
	@RequestMapping(value="/getUpdatePlanInfo", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getUpdatePlanInfo(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="计划主键plan_id应该大于0") int plan_id){
		try {
			net.sf.json.JSONObject plan_info = planService.getUpdatePlanInfo(plan_id);
			
			if(plan_info != null){
				setResultJSON(1, "", plan_info);
			}else{
				setResultJSON(0, "该计划不存在");
			}
			
			logger.info("计划修改-获取要修改的计划信息-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 泊位计划修改  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	 * 计划修改入参：
	 *  - 计划主键id         plan_id
	 *  - 等候泊位号                             waiting_berth_number
	 *  - 车牌号                                     plate_number
	 *  - 浮动时间 （可选）                float_time
	 *  - 描述（可选）                         description
	 *  
	 * 未完成计划修改额外添加的入参：
	 *  - 供应商主键id        supplier_id
	 *  - 驶入时间                                 start_time
	 *  - 驶离时间                                 end_time
	 * 
	 * 【计划修改】
	 * 未完成计划，修改驶离时间计划完成后，更新计划实际完成时间(驶离时间+浮动时间)，重排该计划实际完成时间之后的当天的未完成计划
	 * 允许修改最后一个已完成计划（有驶离时间）的浮动时间，如果修改后发生延迟覆盖到了追加时间或固定时间(则追加时间和固定时间向后推迟)，更新计划实际完成时间，重排该完成时间之后的当天的未完成计划
	 * 
	 * 说明：
	 * 已完成计划允许修改浮动时间，但如果该计划之后还有已完成计划，此修改会使后面的已完成计划的预计开始时间被修改，所以只允许修改最后一个已完成计划的浮动时间
	 * 
	 * @param request
	 * @param planInfo
	 * @return
	 */
	@RequestMapping(value="/updateBerthPlan", produces="application/json;charset=UTF-8", 
			consumes="application/json", method=RequestMethod.POST)
	public @ResponseBody JSONObject updateBerthPlan(HttpServletRequest request, @RequestBody PlanInfo planInfo){
		
		try {
			//输入校验(分组：修改 UpdateValidator)
			ValidationUtil.validate(planInfo, UpdateValidator.class);
			
			//判断计划是否可修改(如果要修改的内容包含浮动时间，但该计划已完成且不是最后一个已完成计划，则不允许修改)
			boolean is_updatable = planService.isPlanUpdatable(planInfo);
			
			if(is_updatable){
				//获取当前登录用户
				User currentLoginUser = SessionUser.getLoginUser(request);
				
				planService.updateBerthPlan(currentLoginUser, planInfo);
				
				setResultJSON(1, "");
			}else{
				setResultJSON(0, "该计划不是最后一个已完成计划，不允许修改它的浮动时间，否则会对该计划之后的已完成计划的时间造成影响");
			}
			
			logger.info("泊位计划修改-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 计划删除  只允许删除未完成的计划
	 * 
	 * @param request
	 * @param plan_id
	 * @return
	 */
	@RequestMapping(value="/deleteBerthPlan", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject deleteBerthPlan(HttpServletRequest request, 
			@RequestParam @Min(value=1, message="plan_id应该大于0") int plan_id){
		try {
			JSONObject msgObj = planService.deleteBerthPlan(plan_id);
			
			if(msgObj != null){
				int result = msgObj.getIntValue("result");
				
				if(result == 1){
					setResultJSON(1, "");
				}else{
					setResultJSON(0, msgObj.getString("msg"));
				}
			}else{
				setResultJSON(0, "该计划不存在");
			}
			
			logger.info("计划删除-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//获取泊位计划监控展示列表
	@RequestMapping(value="/getBerthPlanMonitorList", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getBerthPlanMonitorList(HttpServletRequest request,
			@RequestParam(required=false, defaultValue="1") @Min(value=1, message="page最小为1") int page, 
			@RequestParam(required=false, defaultValue="10") @Min(value=1, message="pageSize最小为1") int pageSize,
			@RequestParam(required=false, defaultValue="") String supplier_name,
			@RequestParam(required=false, defaultValue="") String berth_number){
		try {
			// 使用分页工具类进行分页
//			PageHelper.startPage(page, pageSize);
			
			List<JSONObject> berthPlanMonitorList = planService.getBerthPlanMonitorList(supplier_name, berth_number);
			
			setResultJSON(1, "", JSONArray.fromObject(berthPlanMonitorList));
			
			logger.info("获取泊位计划监控展示列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位监控-获取泊位计划详情  点击泊位后根据泊位状态(0 空闲 1 忙碌)的不同返回不同的详情信息
	@RequestMapping(value="/getBerthPlanDetails", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getBerthPlanDetails(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="berth_id应该大于0") int berth_id){
		try {
			net.sf.json.JSONObject berth_plan_details = planService.getBerthPlanDetails(berth_id);
			
			if(berth_plan_details != null){
				setResultJSON(1, "", berth_plan_details);
			}else{
				setResultJSON(0, "该泊位号下暂时没有计划");
			}
			
			logger.info("获取泊位计划详情-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//泊位监控-获取计划详情  点击计划后根据计划完成状态(0 未完成 1 已完成)的不同返回不同的详情信息
	@RequestMapping(value="/getPlanDetails", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getPlanDetails(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="plan_id应该大于0") int plan_id){
		try {
			net.sf.json.JSONObject plan_details = planService.getPlanDetails(plan_id);
		
			if(plan_details != null){
				setResultJSON(1, "", plan_details);
			}else{
				setResultJSON(0, "该计划不存在");
			}
			
			logger.info("获取计划详情-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 历史计划查询  查询已经完成的历史计划信息
	 * 
	 * 条件查询入参：
	 * 起始时间     query_start_time
	 * 结束时间     query_end_time
	 * 泊位号         berth_number
	 * 车牌号         plate_number
	 * 排队序号     sort_number
	 * 
	 */
	@RequestMapping(value="/selectHistoryPlan", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject selectHistoryPlan(HttpServletRequest request, 
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_start_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_start_time,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_end_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_end_time,
			@RequestParam(required=false) String berth_number,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.carplatePattern, message="车牌号格式不正确") String plate_number,
			@RequestParam(required=false) @Min(value=0, message="sort_number最小值为0") Integer sort_number){
		
		try {
			// 使用分页工具类进行分页
//			PageHelper.startPage(page, pageSize);
			
			JSONArray planListArray = planService.selectHistoryPlan(query_start_time, query_end_time, berth_number, plate_number, sort_number);
			
			if(planListArray != null && planListArray.size() > 0){
				setResultJSON(1, "", planListArray);
			}else{
				setResultJSON(0, "没有记录");
			}
			
			logger.info("历史计划查询-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 历史计划查询-导出excel
	 * 
	 * 条件查询入参：
	 * 起始时间     query_start_time
	 * 结束时间     query_end_time
	 * 泊位号         berth_number
	 * 车牌号         plate_number
	 * 排队序号     sort_number
	 * 
	 */
	@RequestMapping("/exportExcelOfHistoryPlan")
	public void exportExcelOfHistoryPlan(HttpServletRequest request, HttpServletResponse response, 
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_start_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_start_time,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_end_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_end_time,
			@RequestParam(required=false) String berth_number,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.carplatePattern, message="车牌号格式不正确") String plate_number,
			@RequestParam(required=false) @Min(value=0, message="sort_number最小值为0") Integer sort_number){

		try {
			//设置响应头
			String filename = "计划" + DateUtil.DateToString(new Date(), "yyyyMMddHHmmss");
			response.reset();
//			response.setContentType("application/vnd.ms-excel;charset=UTF-8");//xls
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8");//xlsx
//			response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
			response.setHeader("Content-disposition", "attachment;filename=" + new String(filename.getBytes(), "iso-8859-1") + ".xlsx");
			
			//编辑excel表格标题栏
			List<String> titleList = new ArrayList<String>();
			titleList.add("开始时间");
			titleList.add("驶入时间");
			titleList.add("驶离时间");
			titleList.add("结束时间");
			titleList.add("年份");
			titleList.add("日期");
			titleList.add("供应商");
			titleList.add("排队序号");
			titleList.add("泊位号");
			titleList.add("等候泊位号");
			titleList.add("车牌号");
			
			//查询获取excel表格内容
			List<List<String>> planList = planService.exportExcelOfHistoryPlan(query_start_time, query_end_time, berth_number, plate_number, sort_number);
			
			if(planList.size() > 0){
				ExcelUtil.exportExcel(response.getOutputStream(), titleList, planList);
			}
			
			logger.info("历史计划查询-导出excel-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
		}
	}
	
	/*****************app接口*********************/
	
	/**
	 * 按日期查询车辆计划，默认查询当天
	 * 
	 * @param request
	 * @param plate_number
	 * @param plan_date
	 * @return
	 */
	@RequestMapping(value="/selectCarPlanOfDate", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject selectCarPlanOfDate(HttpServletRequest request, 
			@RequestParam(required=true) @Pattern(regexp=RegExpValidatorUtils.carplatePattern, message="车牌号格式不正确") String plate_number,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.y_M_d_DatePattern, message="plan_date格式不正确(yyyy-MM-dd)") String plan_date){
		
		try {
			if(isSystemUser(request)){
				// 使用分页工具类进行分页
//				PageHelper.startPage(page, pageSize);
				
				JSONArray planListArray = planService.selectCarPlanOfDate(plate_number, plan_date);
				
				if(planListArray != null && planListArray.size() > 0){
					setResultJSON(1, "", planListArray);
				}else{
					setResultJSON(0, "没有记录");
				}
			}else{
				setResultJSON(0, "用户名校验失败，不允许进行操作");
			}
			
			logger.info("按日期查询车辆计划，默认查询当天-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	/**
	 * 获取车辆历史完成计划
	 * 
	 * @param request
	 * @param plate_number
	 * @param query_start_time
	 * @param query_end_time
	 * @param berth_number
	 * @param sort_number
	 * @return
	 */
	@RequestMapping(value="/selectCarHistoryPlan", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject selectCarHistoryPlan(HttpServletRequest request, 
			@RequestParam(required=true) @Pattern(regexp=RegExpValidatorUtils.carplatePattern, message="车牌号格式不正确") String plate_number,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_start_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_start_time,
			@RequestParam(required=false) @Pattern(regexp=RegExpValidatorUtils.timePattern, message="query_end_time格式不正确(yyyy-MM-dd HH:mm:ss)") String query_end_time,
			@RequestParam(required=false) String berth_number,
			@RequestParam(required=false) @Min(value=0, message="sort_number最小值为0") Integer sort_number){
		
		try {
			if(isSystemUser(request)){
				// 使用分页工具类进行分页
//				PageHelper.startPage(page, pageSize);
				
				JSONArray planListArray = planService.selectHistoryPlan(query_start_time, query_end_time, berth_number, plate_number, sort_number);
				
				if(planListArray != null && planListArray.size() > 0){
					setResultJSON(1, "", planListArray);
				}else{
					setResultJSON(0, "没有记录");
				}
			}else{
				setResultJSON(0, "用户名校验失败，不允许进行操作");
			}
			
			logger.info("获取车辆历史完成计划-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
}
