package com.souvi.sysmanager.berth.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.berth.entity.BerthInfo;

public interface BerthMapper {

	//查询泊位列表 含条件（泊位号）
	List<BerthInfo> selectBerthList(@Param("berth_number") String berth_number);

	//泊位号校验  1 已经存在 0 不存在
	int checkBerthNumberIsExist(@Param("berth_number") String berth_number);

	//泊位号添加
	void addBerth(@Param("berth") BerthInfo berthInfo);

	//根据id查询泊位信息 
	BerthInfo getBerthInfoById(@Param("berth_id") int berth_id);

	//泊位信息修改时校验修改后的泊位名称是否重复
	int checkUpdateBerthNumberIsExist(@Param("berth_id") Integer berth_id, @Param("berth_number") String berth_number);
	
	//泊位信息修改
	void updateBerthInfo(@Param("berth") BerthInfo berthInfo);

	//检查要删除的泊位是否与供应商有关联
	int getCountOfSupplierByBerthId(@Param("berth_id") int berth_id);

	//检查要删除的泊位是否与计划有关联
	int getCountOfPlanByBerthId(@Param("berth_id") int berth_id);

	//泊位信息删除 
	void deleteBerthInfo(@Param("berth_id") int berth_id);

}
