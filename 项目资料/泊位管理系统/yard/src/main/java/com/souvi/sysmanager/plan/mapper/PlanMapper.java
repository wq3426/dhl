package com.souvi.sysmanager.plan.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.plan.entity.PlanInfo;
import com.souvi.sysmanager.plan.entity.PlanTimeLength;
import com.souvi.sysmanager.plan.entity.SupplierBerthMapInfo;
import com.souvi.sysmanager.supply.entity.SupplierInfo;

public interface PlanMapper {

	//查询供应商-泊位及泊位状态关联列表
	List<SupplierBerthMapInfo> getSupplierBerthMapList(@Param("supplier_name") String supplier_name);

	//按日期获取计划数
	int getPlanCountByDate(@Param("date") String date);
	
	//按日期获取计划执行时间间隔
	PlanTimeLength getTimeLengthOfPlan(@Param("date") String date);
	
	//新增计划时间间隔设置
	void addPlanTimeLength(@Param("time_length") PlanTimeLength planTimeLength);

	//修改计划时间间隔
	void updatePlanTimeLength(@Param("time_length") PlanTimeLength planTimeLength);
	
	//获取添加计划的泊位的状态
	int getBerthStatus(@Param("berth_id") int berth_id);
	
	//按日期获取最大排序号的计划
	PlanInfo getMaxSortNumberPlan(@Param("date") String date);

	//按日期获取最后完成的计划
	PlanInfo getLastCompletedPlan(@Param("date") String date);
	
	//按时间段获取计划列表
	List<PlanInfo> getPlanListOfTimeLength(@Param("query_startTime") String query_startTime, @Param("query_endTime") String query_endTime);

	//获取启用的供应商列表
	List<SupplierInfo> getEnableSupplierInfo();
	
	//获取供应商关联的启用泊位列表
	List<BerthInfo> getBerthListOfSupplier(@Param("supplier_id") int supplier_id);
	
	//获取预定或追加计划列表
	List<PlanInfo> getPlanListByType(@Param("plan_date") String plan_date, @Param("plan_starttime") String plan_starttime, @Param("plan_type") int plan_type);
	
	//泊位计划添加
	void addPlan(@Param("plan") PlanInfo planInfo);
	
	//获取最后一个已完成计划的主键id
	int getLastCompletedPlanId(@Param("plan_date") String plan_date);
	
	//获取某个排序号之后的所有追加计划
	List<PlanInfo> getAddedPlanList(@Param("plan_date") String plan_date, @Param("sort_number") int sort_number);
	
	//追加计划开始时间重排，批量修改计划开始时间
	void updatePlanStartTime(@Param("plan_list") List<PlanInfo> new_addedPlanList);
	
	//计划修改-获取要修改的计划信息
	PlanInfo getUpdatePlanInfo(@Param("plan_id") int plan_id);

	//获取泊位关联的可用供应商列表
	List<SupplierInfo> getSupplierListOfBerth(@Param("berth_id") Integer berth_id);
	
	//获取修改的计划之后的当天计划plan_id集合
	List<Integer> getPlanIdList(@Param("plan_date") String plan_date, @Param("plan_starttime") String plan_starttime);
	
	//将修改计划之后的当天后续计划的开始时间追加一个时间间隔
	void reSetPlanStartTimeInPlanIds(@Param("time_interval") Long time_interval, @Param("plan_idList") List<Integer> plan_idList);
	
	//计划修改
	void updateBerthPlan(@Param("plan") PlanInfo updatePlanInfo);
	
	//计划删除
	void deleteBerthPlan(@Param("plan_id") int plan_id);

	//按顺序查询计划表中的泊位id集合
	List<String> getBerthIdListOfPlan(@Param("date") String date, @Param("supplier_name") String supplier_name, @Param("berth_number") String berth_number);

	//获取泊位下的计划list
	List<PlanInfo> getPlanListByBerthId(@Param("date") String date, @Param("berth_id") String berth_id, @Param("supplier_name") String supplier_name);

	//获取泊位当前未完成计划信息
	PlanInfo getBerthPlanDetails(@Param("date") String date, @Param("berth_id") int berth_id);

	//获取泊位总计划数
	int getTotalPlanCountOfBerth(@Param("date") String date, @Param("berth_id") int berth_id);

	//获取泊位已经完成的计划数
	int getCompletedPlanCountOfBerth(@Param("date") String date, @Param("berth_id") int berth_id);

	//获取计划详情
	PlanInfo getPlanDetails(@Param("plan_id") int plan_id);
	
	//查询已经完成的历史计划信息
	List<PlanInfo> selectHistoryPlan(@Param("query_start_time") String query_start_time, @Param("query_end_time") String query_end_time, 
			@Param("berth_number") String berth_number, @Param("plate_number") String plate_number, @Param("sort_number") Integer sort_number);

	//按日期查询车辆计划，默认查询当天
	List<PlanInfo> selectCarPlanOfDate(@Param("plate_number") String plate_number, @Param("plan_date") String plan_date);

}
