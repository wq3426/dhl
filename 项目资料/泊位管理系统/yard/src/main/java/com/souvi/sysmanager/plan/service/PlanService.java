package com.souvi.sysmanager.plan.service;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.souvi.common.Constant;
import com.souvi.common.DateUtil;
import com.souvi.common.StringUtil;
import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.plan.entity.PlanInfo;
import com.souvi.sysmanager.plan.entity.PlanTimeLength;
import com.souvi.sysmanager.plan.entity.SupplierBerthMapInfo;
import com.souvi.sysmanager.plan.mapper.PlanMapper;
import com.souvi.sysmanager.supply.entity.SupplierInfo;
import com.souvi.sysmanager.user.entity.User;

@Service
public class PlanService {
	
	@Autowired
	private PlanMapper planMapper;

	//查询供应商-泊位及泊位状态关联列表
	public List<SupplierBerthMapInfo> getSupplierBerthMapList(String supplier_name) {
		List<SupplierBerthMapInfo> mapList = planMapper.getSupplierBerthMapList(supplier_name);
		
		//遍历，请求华为获取泊位地磁传感器数据接口，获取泊位状态
		
		return mapList;
	}
	
	//查询当天是否有计划被创建，没有则可以修改计划时间间隔，否则不允许修改
	public boolean planTimeLengthIsSettable(String date) {
		int count = planMapper.getPlanCountByDate(date);
		
		return count == 0 ? true : false;
	}

	/**
	 * 设置/修改当天的计划时间间隔
	 * 
	 * 默认计划间隔时间为8分钟，每天可调节，当所有泊位上没有计划时，则可以修改;
	 * 当所有泊位上有任何计划时不可修改。
	 * 若前一天间隔时间的调整在第二天同样适用，则第二天可不做任何修改，默认继承上一次的修改参数。
	 * @param currentLoginUser 
	 */
	public void setPlanTimeLength(User currentLoginUser, String date, int time_length) {
		//获取当天计划时间间隔
		PlanTimeLength planTimeLength = planMapper.getTimeLengthOfPlan(date);
		
		if(planTimeLength == null){//当天时间间隔没有设置，新增
			planTimeLength = new PlanTimeLength();
			
			planTimeLength.setDate(date);
			planTimeLength.setTime_length(time_length);
			planTimeLength.setCreater(currentLoginUser.getUser_name());
			planTimeLength.setCreate_time(DateUtil.getSystemDate());
			planTimeLength.setLast_updater(currentLoginUser.getUser_name());
			planTimeLength.setUpdate_time(DateUtil.getSystemDate());
			
			planMapper.addPlanTimeLength(planTimeLength);
		}else{//当天时间间隔已经设置，修改
			planTimeLength.setDate(date);
			planTimeLength.setTime_length(time_length);
			planTimeLength.setLast_updater(currentLoginUser.getUser_name());
			planTimeLength.setUpdate_time(DateUtil.getSystemDate());
			
			planMapper.updatePlanTimeLength(planTimeLength);
		}
	}
	
	/**
	 * 判断泊位计划是否可添加
	 * 
	 * 如果泊位不可用，不允许添加
	 * 如果添加预定计划固定时间是否覆盖了预定计划固定时间，不允许添加
	 * 
	 * @param play_type      计划类型  0 只查询泊位是否可用  1 追加计划  2 预定计划
	 * @param currentLoginUser
	 * @param berth_id
	 * @param plan_startTime
	 * @return
	 * @throws ParseException
	 */
	public JSONObject isPlanAddable(int play_type, User currentLoginUser, int berth_id, String plan_startTime) throws ParseException {
		JSONObject obj = new JSONObject();
		
		boolean is_addable = true;//是否可添加
		String msg = "";//不可添加时返回的提示信息
		
		int berth_status = planMapper.getBerthStatus(berth_id);
		if(berth_status == 0){//查询泊位是否停用，如果泊位停用，不允许添加
			is_addable = false;
			msg = "当前泊位已停用，不能添加计划任务";
		}else if(play_type == 2){//如果添加预定计划固定时间是否覆盖了预定计划固定时间，不允许添加
			//获取当天的计划时间间隔
			int time_length = getTimeLengthOfSystemConfig(currentLoginUser, DateUtil.getSystemDateTwo());
			long add_plan_startTime = DateUtil.getMillisecondOfDateStr(plan_startTime);
			long add_plan_endTime = add_plan_startTime + time_length * 60 * 1000l;
			
			//缩小查询是否有受影响的预定计划的时间范围
			String query_startTime = DateUtil.DateToString(new Date(add_plan_startTime - time_length * 60 * 1000l), "yyyy-MM-dd HH:mm:ss");
			String query_endTime = DateUtil.DateToString(new Date(add_plan_endTime + time_length * 60 * 1000l), "yyyy-MM-dd HH:mm:ss");
			
			//查询是否有受影响的预定计划
			List<PlanInfo> plan_list = planMapper.getPlanListOfTimeLength(query_startTime, query_endTime);
			if(plan_list.size() > 0){
				for(PlanInfo planInfo : plan_list){
					long plan_start_time = DateUtil.getMillisecondOfDateStrSSS(planInfo.getPlan_starttime());
					long plan_end_time = plan_start_time + time_length * 60 * 1000l;
					if(add_plan_startTime >= plan_start_time && add_plan_startTime <= plan_end_time){
						is_addable = false;
						msg = "您添加的预定计划的开始时间与之前添加的预定计划的开始时间有冲突，请重新指定该预定计划的开始时间";
						break;
					}
					if(add_plan_endTime >= plan_start_time && add_plan_endTime <= plan_end_time){
						is_addable = false;
						msg = "您添加的预定计划的开始时间与之前添加的预定计划的开始时间有冲突，请重新指定该预定计划的开始时间";
						break;
					}
				}
			}
		}
		
		obj.put("is_addable", is_addable);
		obj.put("msg", msg);
		
		return obj;
	}

	//泊位计划预定-获取供应商-泊位关联的下拉列表
	public JSONArray getSupplierBerthSelectList() {
		JSONArray mapArray = null;
		
		//获取启用的供应商列表
		List<SupplierInfo> supplier_idList = planMapper.getEnableSupplierInfo();
		if(supplier_idList.size() > 0){
			mapArray = new JSONArray();
			
			for(SupplierInfo supplierInfo : supplier_idList){
				int supplier_id = supplierInfo.getSupplier_id();
				
				//获取供应商关联的启用泊位列表
				List<BerthInfo> berthInfoList = planMapper.getBerthListOfSupplier(supplier_id);
				
				if(berthInfoList.size() > 0){
					net.sf.json.JSONObject supplierObj = new net.sf.json.JSONObject();
					
					supplierObj.put("supplier_id", supplier_id);
					supplierObj.put("supplier_name", supplierInfo.getSupplier_name());
					
					JSONArray berth_list = new JSONArray();
					for(BerthInfo berthInfo : berthInfoList){
						net.sf.json.JSONObject berthObj = new net.sf.json.JSONObject();
						
						berthObj.put("berth_id", berthInfo.getBerth_id());
						berthObj.put("berth_number", berthInfo.getBerth_number());
						
						berth_list.add(berthObj);
					}
					
					supplierObj.put("berth_list", berth_list);
					
					mapArray.add(supplierObj);
				}
			}
		}
		
		return mapArray;
	}

	/**
	 * 泊位计划添加
	 * 
	 * @param plan_type  1 追加 2 预定
	 * @param currentLoginUser
	 * @param planInfo
	 * @throws ParseException
	 */
	@Transactional //多个数据库操作，添加Spring事务控制
	public void addBerthPlan(int plan_type, User currentLoginUser, PlanInfo planInfo) throws ParseException {
		planInfo.setPlan_date(DateUtil.getSystemDateTwo());
		planInfo.setPlan_type(plan_type);
		planInfo.setCreater(currentLoginUser.getUser_name());
		planInfo.setCreate_time(DateUtil.getSystemDate());
		planInfo.setLast_updater(currentLoginUser.getUser_name());
		planInfo.setUpdate_time(DateUtil.getSystemDate());
		
		//获取当天的计划时间间隔
		int time_length = getTimeLengthOfSystemConfig(currentLoginUser, DateUtil.getSystemDateTwo());
		
		if(plan_type == 1){//追加计划，设置计划预计开始时间  plan_starttime 和 排序号sort_number
			/**
			 * 设置计划预计开始时间  plan_starttime
			 */
			//获取当天最大排序号的计划
			PlanInfo max_sort_plan = planMapper.getMaxSortNumberPlan(DateUtil.getSystemDateTwo());
			String plan_starttime = getPlanStartTimeOfAddPlan(max_sort_plan, time_length);
			planInfo.setPlan_starttime(plan_starttime);
			
			/**
			 * 设置排序号sort_number
			 */
			int sort_number = max_sort_plan == null ? 1 : max_sort_plan.getSort_number() + 1;
			planInfo.setSort_number(sort_number);
		}
		
		//添加计划前，检查计划的预计开始时间是否与现有的已完成计划、预定计划、追加计划冲突，是否需要对计划进行重新排序
		planInfo = verifyPlanStartTimeAndReSortPlanBeforeAdd(time_length, planInfo);
		
		//设置计划的驶入等候泊位的时间（计划预计开始时间）waiting_berth_starttime
		planInfo.setWaiting_berth_starttime(planInfo.getPlan_starttime());//驶入等候泊位的时间（计划预计开始时间）
		
		//计划添加
		planMapper.addPlan(planInfo);
	}
	
	/**
	 * 添加计划前，检查计划的预计开始时间是否与已完成计划、预定计划冲突，是否需要对计划进行重新排序
	 * 
	 * 固定时间：按照时间预定的计划的预计开始时间
	 * 计划优先级：已完成(修改) > 预定计划(可以设置预计开始时间，没有排序号) > 追加计划(不能设置预计开始时间，有排序号)
	 * 
	 * 【计划添加】
	 * 在下一个计划执行前，检查之前的计划是否完成，若没有完成，告知操作员检查，根据其设定重排后续未完成计划的固定时间(第一期暂不考虑)
	 * 追加计划的时间覆盖了预定计划固定时间，追加时间跳过固定时间向后追加，重排该时间之后的当天的未完成追加计划
	 * 预定计划固定时间覆盖了追加时间，追加时间跳过固定时间向后追加，重排该时间之后的当天的未完成追加计划
	 * 
	 * @param time_length 计划时间间隔
	 * @param planInfo 添加的计划信息对象，包含计划日期、计划浮动时间、计划实际完成时间等
	 * @throws ParseException 
	 */
	public PlanInfo verifyPlanStartTimeAndReSortPlanBeforeAdd(int time_length, PlanInfo planInfo) throws ParseException {
		int plan_type = planInfo.getPlan_type();//获取添加的计划类型 
		String plan_date = planInfo.getPlan_date();//获取计划日期
		String add_plan_start_time = planInfo.getPlan_starttime();//获取未判断冲突前自动计算出的计划预计开始时间
		
		/**
		 * 1.判断是否与已完成计划的完成时间冲突
		 * 计划添加只能在已完成计划之后添加，先查出当天最后完成的计划的完成时间，判断添加的计划的预计开始时间是否与完成时间冲突，如果冲突，则从该完成时间之后开始追加
		 */
		//查出当天最后完成的计划的开始时间、完成时间
		PlanInfo last_completed_plan = planMapper.getLastCompletedPlan(plan_date);
		long last_completed_plan_startTime = last_completed_plan == null ? 0 : DateUtil.getMillisecondOfDateStrSSS(last_completed_plan.getPlan_starttime());
		long last_completed_plan_endTime = last_completed_plan == null ? 0 : DateUtil.getMillisecondOfDateStrSSS(last_completed_plan.getPlan_endtime());
		long plan_start_time = DateUtil.getMillisecondOfDateStr(add_plan_start_time);
		//如果计划预计开始时间与已完成计划的时间间隔冲突，则从完成时间之后开始追加
		if(plan_start_time < last_completed_plan_startTime){
			plan_start_time = last_completed_plan_endTime;
		}else if(plan_start_time < last_completed_plan_endTime){
			plan_start_time = last_completed_plan_endTime;
		}
		long plan_end_time = plan_start_time + time_length * 60 * 1000l;//添加的计划的预计结束时间
		
		//将添加的计划的预计开始时间向前移动一个时间间隔作为查询可能受影响的计划的起始时间，提高查询精度，减少处理时间
		String queryStartTime = DateUtil.DateToString(new Date(plan_start_time - time_length * 60 * 1000l), "yyyy-MM-dd HH:mm:ss");
		
		//追加计划
		if(plan_type == 1){
			/**
			 * 2.判断是否与预定计划的预计开始时间冲突
			 * 追加计划的时间覆盖了预定计划固定时间，追加时间跳过固定时间向后追加，重排该时间之后的当天的追加计划
			 */
			//获取当天可能受影响的预定计划列表
			List<PlanInfo> reserve_planList = planMapper.getPlanListByType(plan_date, queryStartTime, 2);
			if(reserve_planList.size() > 0){
				for(PlanInfo reserve_plan : reserve_planList){
					long reserve_plan_starttime = DateUtil.getMillisecondOfDateStrSSS(reserve_plan.getPlan_starttime());
					long reserve_plan_endtime = reserve_plan_starttime + time_length * 60 * 1000l;
					if(plan_end_time <= reserve_plan_starttime){//追加计划在预定计划之前添加且时间不冲突，直接添加
						break;
					}else if(plan_end_time < reserve_plan_endtime){//追加计划完成时间在预定计划的时间间隔内，将该追加计划开始时间向后追加
						plan_start_time = reserve_plan_endtime;
						plan_end_time = plan_start_time + time_length * 60 * 1000l;
					}else if(plan_start_time < reserve_plan_endtime){//追加计划起始时间在预定计划的时间间隔内，将该追加计划开始时间向后追加
						plan_start_time = reserve_plan_endtime;
						plan_end_time = plan_start_time + time_length * 60 * 1000l;
					}
				}
			}
		}
		
		//预定计划
		if(plan_type == 2){
			/**
			 * 3.判断是否与追加计划的预计开始时间冲突
			 * 预定计划固定时间覆盖了追加时间，追加时间跳过固定时间向后追加，重排该时间之后的当天的追加计划
			 */
			//获取当天可能受影响的追加计划列表
			List<PlanInfo> added_planList = planMapper.getPlanListByType(plan_date, queryStartTime, 1);
			int sort_number = 0;//如果与追加计划冲突，记录第一个冲突的追加计划的排序号，重排该排序号之后的追加计划
			if(added_planList.size() > 0){
				for(PlanInfo added_plan : added_planList){
					long added_plan_starttime = DateUtil.getMillisecondOfDateStrSSS(added_plan.getPlan_starttime());
					long added_plan_endtime = added_plan_starttime + time_length * 60 * 1000l;

					if(plan_start_time >= added_plan_starttime && plan_start_time <= added_plan_endtime){
						sort_number = added_plan.getSort_number();
						break;
					}
					if(plan_end_time >= added_plan_starttime && plan_end_time <= added_plan_endtime){
						sort_number = added_plan.getSort_number();
						break;
					}
				}
				
				//如果有冲突，则对冲突的追加计划之后的当天所有追加计划进行重排
				if(sort_number > 0){
					reArrangeWhenAddPlan(planInfo.getPlan_date(), sort_number, time_length, plan_end_time);
				}
			}
		}
		
		add_plan_start_time = DateUtil.DateToString(new Date(plan_start_time), "yyyy-MM-dd HH:mm:ss");
		planInfo.setPlan_starttime(add_plan_start_time);//重新设置计划预计开始时间
		
		return planInfo;
	}

	//对追加计划进行重排
	public void reArrangeWhenAddPlan(String plan_date, int sort_number, int time_length, long plan_start_time) throws ParseException {
		//重新计算过计划起始时间的追加计划list
		List<PlanInfo> new_addedPlanList = null;
		
		//将添加的计划的预计开始时间向前移动一个时间间隔作为查询可能受影响的计划的起始时间，提高查询精度，减少处理时间
		String queryStartTime = DateUtil.DateToString(new Date(plan_start_time), "yyyy-MM-dd HH:mm:ss");
		
		//获取与预定计划添加有冲突的追加计划之后的所有追加计划
		List<PlanInfo> addedPlanList = planMapper.getAddedPlanList(plan_date, sort_number);
		if(addedPlanList.size() > 0){
			new_addedPlanList = new ArrayList<>();
			
			//追加计划的开始时间、结束时间
			long added_plan_start_time = 0l;
			long added_plan_end_time = 0l;
			
			for(int i=0; i < addedPlanList.size(); i++){
				PlanInfo added_plan = addedPlanList.get(i);
				
				if(i == 0){//获取第一个追加计划的开始时间，赋值
					added_plan_start_time = DateUtil.getMillisecondOfDateStrSSS(added_plan.getPlan_starttime());
				}
				added_plan_end_time = added_plan_start_time + time_length * 60 * 1000l;
				
				//获取添加的预定计划之后的所有预定计划
				List<PlanInfo> reserve_planList = planMapper.getPlanListByType(plan_date, queryStartTime, 2);
				if(reserve_planList.size() > 0){//重新计算，修改追加计划列表的计划的预计开始时间，将追加计划插入到计划列表中
					boolean starttime_has_changed = false;
					for(PlanInfo reserve_plan : reserve_planList){
						long reserve_plan_starttime = DateUtil.getMillisecondOfDateStrSSS(reserve_plan.getPlan_starttime());
						long reserve_plan_endtime = reserve_plan_starttime + time_length * 60 * 1000l;
						
						if(added_plan_end_time <= reserve_plan_starttime){//追加计划在预定计划之前,时间不冲突，不修改计划开始时间
							starttime_has_changed = true;
							break;
						}else if(added_plan_end_time < reserve_plan_endtime){//追加计划完成时间在预定计划的时间间隔内，将该追加计划开始时间向后追加
							added_plan_start_time = reserve_plan_endtime;
							added_plan_end_time = added_plan_start_time + time_length * 60 * 1000l;
						}else if(plan_start_time < reserve_plan_endtime){//追加计划起始时间在预定计划的时间间隔内，将该追加计划开始时间向后追加
							added_plan_start_time = reserve_plan_endtime;
							added_plan_end_time = added_plan_start_time + time_length * 60 * 1000l;
						}
					}
					
					if(starttime_has_changed){
						//重新设置追加计划的开始时间
						added_plan.setPlan_starttime(DateUtil.DateToString(new Date(added_plan_start_time), "yyyy-MM-dd HH:mm:ss"));
						
						//添加到修改list
						new_addedPlanList.add(added_plan);
					}
					
					//设置下一个追加计划的开始时间
					added_plan_start_time = added_plan_end_time;
					
					//重新设置查询开始时间，进行下一次遍历，确定下一个追加计划的开始时间
					queryStartTime = DateUtil.DateToString(new Date(added_plan_end_time), "yyyy-MM-dd HH:mm:ss");
				}
			}
		}
		
		//重新设置追加计划的开始时间
		if(new_addedPlanList != null && new_addedPlanList.size() > 0){
			planMapper.updatePlanStartTime(new_addedPlanList);
		}
	}

	/**
	 * 获取计划预计开始时间
	 * 
	 * 字段说明：
	 * plan_starttime  计划预计开始时间
	 * 计划表中查找当前排队序号最大的任务计划；
	 * - 若没有：则默认为8:00开始；
	 * - 若有：则为最大序号的计划预计时间+系统设置的计划时间间隔(默认为8分钟)
	 *  
	 * @param max_sort_plan
	 * @param time_length
	 * @return
	 * @throws ParseException
	 */
	public String getPlanStartTimeOfAddPlan(PlanInfo max_sort_plan, int time_length) throws ParseException{
		String plan_starttime = "";
		
		if(max_sort_plan != null){//当天已有计划，自动追加时间间隔
			long plan_start_time = DateUtil.getMillisecondOfDateStrSSS(max_sort_plan.getPlan_starttime());
			plan_start_time += time_length * 60 * 1000l;
			
			plan_starttime = DateUtil.DateToString(new Date(plan_start_time), "yyyy-MM-dd HH:mm:ss");
		}else{//当天没有计划，从8:00开始
			plan_starttime = DateUtil.getSystemDateTwo() + " 08:00:00";//获取日期字符串,拼接时分秒
		}
		
		return plan_starttime;
	}
	
	/**
	 * 获取系统设置的计划的时间间隔
	 * 
	 * 没有时间间隔，设置前一天的时间间隔作为当天时间间隔，前一天也没有，则设置为默认时间间隔(8分钟)
	 * @param user
	 * @param date
	 * @return
	 */
	public int getTimeLengthOfSystemConfig(User user, String date){
		
		int time_length = 0;
		
		//获取当天的计划执行时间间隔
		PlanTimeLength plan_time_length = planMapper.getTimeLengthOfPlan(DateUtil.getSystemDateTwo());
		
		if(plan_time_length != null){//有，获取
			time_length = plan_time_length.getTime_length();
		}else{//没有时间间隔，设置前一天的时间间隔作为当天时间间隔，前一天也没有，则设置为默认时间间隔
			//获取前一天的时间间隔
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DATE, -1);
			plan_time_length = planMapper.getTimeLengthOfPlan(DateUtil.DateToString(c.getTime(), "yyyy-MM-dd"));
			
			if(plan_time_length != null){//设置为前一天的时间间隔
				time_length = plan_time_length.getTime_length();
				
				plan_time_length.setDate(DateUtil.getSystemDateTwo());
				plan_time_length.setCreater(user.getUser_name());
				plan_time_length.setCreate_time(DateUtil.getSystemDate());
				
				planMapper.addPlanTimeLength(plan_time_length);
			}else{//前一天时间间隔未设置，则设置为默认时间间隔
				time_length = Constant.DEFAULT_PLAN_TIME_LENGTH;
				
				plan_time_length = new PlanTimeLength();
				plan_time_length.setDate(DateUtil.getSystemDateTwo());
				plan_time_length.setTime_length(time_length);
				plan_time_length.setCreater(user.getUser_name());
				plan_time_length.setCreate_time(DateUtil.getSystemDate());
				
				planMapper.addPlanTimeLength(plan_time_length);
			}
		}
		
		return time_length;
	}
	
	//判断计划是否可修改(如果要修改的内容包含浮动时间，但该计划已完成且不是最后一个已完成计划，则不允许修改)
	public boolean isPlanUpdatable(PlanInfo planInfo) {
		//获取要修改的计划
		PlanInfo des_plan = planMapper.getPlanDetails(planInfo.getPlan_id());
		
		int plan_status = des_plan.getPlan_status();
		
		//如果要修改的内容包含浮动时间，但该计划已完成且不是最后一个已完成计划，则不允许修改
		if(plan_status == 1 && planInfo.getFloat_time() > 0){
			int last_completed_plan_id = planMapper.getLastCompletedPlanId(planInfo.getPlan_date());
			
			if(planInfo.getPlan_id() != last_completed_plan_id){
				return false;
			}
		}
		
		return true;
	}
	
	//计划修改-获取要修改的计划信息
	public net.sf.json.JSONObject getUpdatePlanInfo(int plan_id) {
		net.sf.json.JSONObject obj = null;
		
		PlanInfo planInfo = planMapper.getUpdatePlanInfo(plan_id);
		if(planInfo != null){
			obj = new net.sf.json.JSONObject();
			
			obj.put("plan_starttime", DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));
			obj.put("start_time", DateUtil.getTimeStrOfDate(planInfo.getStart_time()));
			obj.put("end_time", DateUtil.getTimeStrOfDate(planInfo.getEnd_time()));
			obj.put("plan_endtime", DateUtil.getTimeStrOfDate(planInfo.getPlan_endtime()));
			obj.put("supplier_name", planInfo.getSupplier_name());
			obj.put("berth_number", planInfo.getBerth_number());
			obj.put("waiting_berth_number", planInfo.getWaiting_berth_number());
			obj.put("sort_number", planInfo.getSort_number());
			obj.put("plate_number", planInfo.getPlate_number());
			obj.put("float_time", planInfo.getFloat_time());
			obj.put("description", planInfo.getDescription());
			
			int plan_status = planInfo.getPlan_status();
			obj.put("plan_status", plan_status);
			
			if(plan_status == 1){//计划已完成，返回计划日期
				obj.put("plan_date", planInfo.getPlan_date());
			}
			
			if(plan_status == 0){//计划未完成，返回泊位关联的可用供应商列表
				List<SupplierInfo> supplierList = planMapper.getSupplierListOfBerth(planInfo.getBerth_id());
				if(supplierList.size() > 0){
					JSONArray supplierArray = new JSONArray();
					for(SupplierInfo supplierInfo : supplierList){
						net.sf.json.JSONObject supplierObj = new net.sf.json.JSONObject();
						
						supplierObj.put("supplier_id", supplierInfo.getSupplier_id());
						supplierObj.put("supplier_name", supplierInfo.getSupplier_name());
						
						supplierArray.add(supplierObj);
					}
					
					obj.put("supplier_list", supplierArray);
				}
			}
		}
		
		return obj;
	}
	
	/**
	 * 泊位计划修改
	 * 
	 * 【计划修改】
	 * 未完成计划，修改驶离时间计划完成后，更新计划实际完成时间(驶离时间+浮动时间)，重排该计划实际完成时间之后的当天的未完成计划
	 * 允许修改最后一个已完成计划（有驶离时间）的浮动时间，如果修改后发生延迟覆盖到了追加时间或固定时间(则追加时间和固定时间向后推迟)，更新计划实际完成时间，重排该完成时间之后的当天的未完成计划
	 * 
	 * 说明：
	 * 已完成计划允许修改浮动时间，但如果该计划之后还有已完成计划，此修改会使后面的已完成计划的预计开始时间被修改，所以只允许修改最后一个已完成计划的浮动时间
	 * 
	 * @param currentLoginUser
	 * @param planInfo
	 * @throws ParseException 
	 */
	@Transactional//多个数据库操作，添加Spring事务控制
	public void updateBerthPlan(User currentLoginUser, PlanInfo updatePlanInfo) throws ParseException {
		/**
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
		 */
		
		/**
		 * 判断是否需要重排:如果浮动时间有修改或驶离时间有修改，重排修改计划之后的当天计划
		 */
		//查询要修改的计划信息
		PlanInfo plan_info = planMapper.getPlanDetails(updatePlanInfo.getPlan_id());
		
		long plan_endtime = 0l;//计划完成时间
		long time_interval = 0l;//计划顺延的时间间隔(ms)
		
		//获取计划完成状态
		int plan_status = plan_info.getPlan_status();
		if(plan_status == 0){//计划未完成
			if(!StringUtil.isNull(updatePlanInfo.getEnd_time())){//更新计划完成时间
				plan_endtime = DateUtil.getMillisecondOfDateStr(updatePlanInfo.getEnd_time());
			}
			if(updatePlanInfo.getFloat_time() > 0){
				plan_endtime += updatePlanInfo.getFloat_time() * 60 * 1000;
			}
			//计算计划完成的时间间隔
			int time_length = planMapper.getTimeLengthOfPlan(plan_info.getPlan_date()).getTime_length();
			long time_diff = plan_endtime - DateUtil.getMillisecondOfDateStrSSS(plan_info.getPlan_starttime());
			
			//如果计划未按预期完成(超时或提前)，将当天后续计划开始时间顺延一个时间间隔
			if(time_diff != time_length){
				time_interval = time_diff - time_length;
			}
		}
		
		if(plan_status == 1){//计划已完成(最后一个已完成计划)
			if(updatePlanInfo.getFloat_time() > 0){//浮动时间被修改
				time_interval = updatePlanInfo.getFloat_time() * 60 * 1000l;
				
				//更新计划完成时间
				plan_endtime = DateUtil.getMillisecondOfDateStrSSS(plan_info.getPlan_endtime()) + time_interval;
			}
			
			//已完成计划，不能修改驶入时间、驶离时间、供应商
			updatePlanInfo.setStart_time("");
			updatePlanInfo.setEnd_time("");
			updatePlanInfo.setSupplier_id(0);
		}
		
		if(plan_endtime > 0){//修改计划完成时间，对后续当天计划开始时间进行重排
			updatePlanInfo.setPlan_endtime(DateUtil.DateToString(new Date(plan_endtime), "yyyy-MM-dd HH:mm:ss"));
			
			if(time_interval > 0){//计划完成时间未按预期，调整当天后续计划的开始时间
				//获取修改的计划之后的当天计划plan_id集合
				List<Integer> plan_idList = planMapper.getPlanIdList(plan_info.getPlan_date(), plan_info.getPlan_starttime());
				
				//对该计划之后的当天后续计划的开始时间重排
				planMapper.reSetPlanStartTimeInPlanIds(time_interval, plan_idList);
			}
		}
		
		//计划修改
		planMapper.updateBerthPlan(updatePlanInfo);

	}
	
	//计划删除  只允许删除未完成的计划
	public JSONObject deleteBerthPlan(int plan_id) {
		JSONObject obj = null;
		
		//获取要删除的计划的完成状态，如果已完成，不能删除
		PlanInfo planInfo = planMapper.getPlanDetails(plan_id);

		if(planInfo != null){
			obj = new JSONObject();
			
			int plan_status = planInfo.getPlan_status();
			
			if(plan_status == 1){//已经完成，不允许删除
				obj.put("result", 0);
				obj.put("msg", "不能删除已经完成的计划");
			}
			
			if(plan_status == 0){//未完成，删除该计划
				planMapper.deleteBerthPlan(plan_id);
				obj.put("result", 1);
			}
		}
		
		return obj;
	}

	//获取泊位计划监控展示列表
	public List<JSONObject> getBerthPlanMonitorList(String supplier_name, String berth_number) {
		List<JSONObject> berthPlanMonitorList = null;
		
		//按顺序查询计划表中的泊位id集合
		List<String> berthIdList = planMapper.getBerthIdListOfPlan(DateUtil.getSystemDateTwo(), supplier_name, berth_number);
		if(berthIdList.size() > 0){
			berthPlanMonitorList = new ArrayList<>();
			
			String berth_ids = StringUtils.join(berthIdList, ",");
			
			//按泊位id查询计划列表list，并将得到的list添加到返回的展示list中
			String[] bidArray = berth_ids.split(",");
			for(String berth_id : bidArray){
				JSONObject berth_obj = new JSONObject();
				//调用华为接口，获取泊位状态
				
				berth_obj.put("berth_id", berth_id);
				berth_obj.put("berth_number", "");
				berth_obj.put("berth_status", "");
				
				//获取泊位下的计划list
				List<PlanInfo> plan_list = planMapper.getPlanListByBerthId(DateUtil.getSystemDateTwo(), berth_id, supplier_name);
				
				berth_obj.put("plan_list", JSONArray.fromObject(plan_list));
				
				berthPlanMonitorList.add(berth_obj);
			}
		}
		
		return berthPlanMonitorList;
	}

	//获取泊位当天计划详情
	public net.sf.json.JSONObject getBerthPlanDetails(int berth_id) {
		net.sf.json.JSONObject obj = null;
		
		//调用华为接口，获取泊位状态
		int berth_status = 1;//泊位状态 0 空闲 1 忙碌
		String start_time = "";//驶入时间
		
		//获取泊位当天当前未完成计划信息
		PlanInfo planInfo = planMapper.getBerthPlanDetails(DateUtil.getSystemDateTwo(), berth_id);
		if(planInfo != null){
			obj = new net.sf.json.JSONObject();
			
			obj.put("berth_number", planInfo.getBerth_number());
			obj.put("sort_number", planInfo.getSort_number());
			obj.put("plate_number", planInfo.getPlate_number());
			
			if(berth_status == 0){//空闲
				obj.put("plan_starttime", DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));
			}
			
			if(berth_status == 1){//忙碌
				obj.put("start_time",  DateUtil.getTimeStrOfDate(planInfo.getStart_time()));
				//持续时间为当前时间与驶入时间的时间差，待华为接口调用完成后实现
				obj.put("duration_time", 0);
			}
		}
		
		//获取当天当前泊位的计划总量百分比
		obj = getPlanCompletedRatio(obj, berth_id);
		
		return obj;
	}

	//获取计划详情
	public net.sf.json.JSONObject getPlanDetails(int plan_id) throws Exception {
		net.sf.json.JSONObject obj = null;
		
		PlanInfo planInfo = planMapper.getPlanDetails(plan_id);
		if(planInfo != null){
			obj = new net.sf.json.JSONObject();
			
			int berth_id = planInfo.getBerth_id();
			
			obj.put("berth_id", berth_id);
			obj.put("berth_number", planInfo.getBerth_number());
			obj.put("waiting_berth_number", planInfo.getWaiting_berth_number());
			obj.put("plan_starttime", DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));
			obj.put("sort_number", planInfo.getSort_number());	
			obj.put("plan_status", planInfo.getPlan_status());
			
			int plan_status = planInfo.getPlan_status();//计划完成状态   0 未完成 1 已完成
			
			if(plan_status == 0){//计划未完成
				obj.put("plate_number", planInfo.getPlate_number());
			}else if(plan_status == 1){//计划已完成
				obj.put("start_time", DateUtil.getTimeStrOfDate(planInfo.getStart_time()));
				obj.put("end_time", DateUtil.getTimeStrOfDate(planInfo.getEnd_time()));
				
				//获取当前泊位的计划总量百分比
				obj = getPlanCompletedRatio(obj, berth_id);
			}
		}

		return obj;
	}
	
	//获取当天当前泊位的计划总量百分比
	public net.sf.json.JSONObject getPlanCompletedRatio(net.sf.json.JSONObject obj, int berth_id){
		int totalPlanCount = planMapper.getTotalPlanCountOfBerth(DateUtil.getSystemDateTwo(), berth_id);//获取泊位当天的总计划数
		if(totalPlanCount > 0){
			int completedPlanCount = planMapper.getCompletedPlanCountOfBerth(DateUtil.getSystemDateTwo(), berth_id);//获取泊位当天已经完成的计划数
			float plan_ratio = (float)completedPlanCount / totalPlanCount;
			
			obj = obj == null ? new net.sf.json.JSONObject() : obj;
			
			obj.put("plan_completed_ratio", new DecimalFormat("#.##%").format(plan_ratio));
		}
		
		return obj;
	}

	//查询已经完成的历史计划信息
	public JSONArray selectHistoryPlan(String query_start_time, String query_end_time, 
			String berth_number, String plate_number, Integer sort_number) {
		JSONArray planListArray = null;
		
		List<PlanInfo> planList = planMapper.selectHistoryPlan(query_start_time, query_end_time, berth_number, plate_number, sort_number);
		
		if(planList.size() > 0){
			planListArray = new JSONArray();
			
			for(PlanInfo planInfo : planList){
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				
				obj.put("plan_starttime", DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));
				obj.put("start_time", DateUtil.getTimeStrOfDate(planInfo.getStart_time()));
				obj.put("end_time", DateUtil.getTimeStrOfDate(planInfo.getEnd_time()));
				obj.put("plan_endtime", DateUtil.getTimeStrOfDate(planInfo.getPlan_endtime()));
				obj.put("year", DateUtil.getYearStrOfDate(planInfo.getPlan_date()));
				obj.put("plan_date", DateUtil.getDateStrOfDate(planInfo.getPlan_date()));
				obj.put("supplier_name", planInfo.getSupplier_name());
				obj.put("sort_number", planInfo.getSort_number());
				obj.put("berth_number", planInfo.getBerth_number());
				obj.put("waiting_berth_number", planInfo.getWaiting_berth_number());
				obj.put("plate_number", planInfo.getPlate_number());
				
				planListArray.add(obj);
			}
		}
		
		return planListArray;
	}
	
	//历史计划查询-导出excel
	public List<List<String>> exportExcelOfHistoryPlan(String query_start_time, String query_end_time, String berth_number, 
			String plate_number, Integer sort_number) {
		List<List<String>> contentList = null;
		
		List<PlanInfo> planContentList = planMapper.selectHistoryPlan(query_start_time, query_end_time, berth_number, plate_number, sort_number);
		
		if(planContentList.size() > 0){
			contentList = new ArrayList<List<String>>();
			
			for(PlanInfo planInfo : planContentList){
				List<String> rowList = new ArrayList<String>();
				
				rowList.add(DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));//plan_starttime
				rowList.add(DateUtil.getTimeStrOfDate(planInfo.getStart_time()));//start_time
				rowList.add(DateUtil.getTimeStrOfDate(planInfo.getEnd_time()));//end_time
				rowList.add(DateUtil.getTimeStrOfDate(planInfo.getPlan_endtime()));//plan_endtime
				rowList.add(DateUtil.getYearStrOfDate(planInfo.getPlan_date()));//year
				rowList.add(DateUtil.getDateStrOfDate(planInfo.getPlan_date()));//plan_date
				rowList.add(planInfo.getSupplier_name());//supplier_name
				rowList.add(planInfo.getSort_number()+"");//sort_number
				rowList.add(planInfo.getBerth_number());//berth_number
				rowList.add(planInfo.getWaiting_berth_number());//waiting_berth_number
				rowList.add(planInfo.getPlate_number());//plate_number
				
				contentList.add(rowList);
			}
		}
		
		return contentList;
	}

	//按日期查询车辆计划，默认查询当天
	public JSONArray selectCarPlanOfDate(String plate_number, String plan_date) {
		JSONArray planListArray = null;
		
		if(StringUtil.isNull(plan_date)){
			plan_date = DateUtil.getSystemDateTwo();
		}
		
		List<PlanInfo> planList = planMapper.selectCarPlanOfDate(plate_number, plan_date);
		
		if(planList.size() > 0){
			planListArray = new JSONArray();
			
			for(PlanInfo planInfo : planList){
				net.sf.json.JSONObject obj = new net.sf.json.JSONObject();
				
				obj.put("plan_starttime", DateUtil.getTimeStrOfDate(planInfo.getPlan_starttime()));
				obj.put("start_time", DateUtil.getTimeStrOfDate(planInfo.getStart_time()));
				obj.put("end_time", DateUtil.getTimeStrOfDate(planInfo.getEnd_time()));
				obj.put("plan_endtime", DateUtil.getTimeStrOfDate(planInfo.getPlan_endtime()));
				obj.put("year", DateUtil.getYearStrOfDate(planInfo.getPlan_date()));
				obj.put("plan_date", DateUtil.getDateStrOfDate(planInfo.getPlan_date()));
				obj.put("supplier_name", planInfo.getSupplier_name());
				obj.put("sort_number", planInfo.getSort_number());
				obj.put("berth_number", planInfo.getBerth_number());
				obj.put("waiting_berth_number", planInfo.getWaiting_berth_number());
				obj.put("plate_number", planInfo.getPlate_number());
				
				planListArray.add(obj);
			}
		}
		
		return planListArray;	
	}
	
}
