package com.souvi.sysmanager.berth.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.berth.mapper.BerthMapper;

@Service
public class BerthService {

	@Autowired
	private BerthMapper berthMapper;
	
	//查询泊位列表
	public List<BerthInfo> selectBerthList(String berth_number) {
		return berthMapper.selectBerthList(berth_number);
	}

	//泊位号校验  1 已经存在 0 不存在
	public int checkBerthNumberIsExist(String berth_number) {
		int count = berthMapper.checkBerthNumberIsExist(berth_number);
		
		return count > 0 ? 1 : 0;
	}

	//泊位号添加
	public void addBerth(BerthInfo berthInfo) {
		berthMapper.addBerth(berthInfo);
	}
	
	//根据id查询泊位信息 
	public BerthInfo getBerthInfoById(int berth_id) {
		return berthMapper.getBerthInfoById(berth_id);
	}
	
	//泊位信息修改时校验修改后的泊位名称是否重复
	public int checkUpdateBerthNumberIsExist(Integer berth_id, String berth_number) {
		int count = berthMapper.checkUpdateBerthNumberIsExist(berth_id, berth_number);
		
		return count > 0 ? 1 : 0;
	}

	//泊位信息修改
	public void updateBerthInfo(BerthInfo berthInfo) {
		berthMapper.updateBerthInfo(berthInfo);
	}
	
	//检查是否与泊位-供应商中间表、计划表有关联，当该泊位已经与供应商或者某个计划关联过，提示不允许删除
	public boolean checkRelationWithSupplyAndPlan(int berth_id) {
		int supplier_count = berthMapper.getCountOfSupplierByBerthId(berth_id);
		if(supplier_count > 0){//与供应商有关联，返回true
			return true;
		}
		int plan_count = berthMapper.getCountOfPlanByBerthId(berth_id);//与计划有关联，返回true，否则返回false
		
		return plan_count > 0 ? true : false;
	}

	//泊位信息删除 
	public void deleteBerthInfo(int berth_id) {
		berthMapper.deleteBerthInfo(berth_id);
	}

}
