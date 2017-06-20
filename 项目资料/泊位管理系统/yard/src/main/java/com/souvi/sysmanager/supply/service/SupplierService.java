package com.souvi.sysmanager.supply.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.supply.entity.SupplierBerthMapper;
import com.souvi.sysmanager.supply.entity.SupplierInfo;
import com.souvi.sysmanager.supply.mapper.SupplierMapper;

@Service
public class SupplierService {

	@Autowired
	private SupplierMapper supplierMapper;

	//供应商列表查询-分页
	public List<SupplierInfo> selectSupplierList(String supplier_name) {
		return supplierMapper.selectSupplierList(supplier_name);
	}
	
	//获取供应商已经关联的泊位id集合
	public List<Integer> getMappedBerthIdList(int supplier_id) {
		
		return supplierMapper.getMappedBerthIdList(supplier_id);
	}

	//供应商名称校验
	public int checkSupplierNameIsExist(String supplier_name) {
		int count = supplierMapper.checkSupplierNameIsExist(supplier_name);
		
		return count > 0 ? 1 : 0;
	}

	//供应商添加
	public void addSupplier(SupplierInfo supplierInfo) {
		supplierMapper.addSupplier(supplierInfo);
	}

	//根据id查询供应商信息
	public SupplierInfo getSupplierInfoById(int supplier_id) {
		return supplierMapper.getSupplierInfoById(supplier_id);
	}
	
	//供应商信息修改时校验修改后的供应商名称是否重复
	public int checkUpdateSupplierNameIsExist(Integer supplier_id, String supplier_name) {
		int count = supplierMapper.checkUpdateSupplierNameIsExist(supplier_id, supplier_name);
		
		return count > 0 ? 1 : 0;
	}

	//供应商信息修改 
	public void updateSupplierInfo(SupplierInfo supplierInfo) {
		supplierMapper.updateSupplierInfo(supplierInfo);
	}

	//检查是否与计划有关联 当该供应商已经与某个计划关联过，提示不允许删除
	public boolean checkRelationWithOtherTable(int supplier_id) {
//		int berth_count = supplierMapper.getCountOfBerthBySupplierId(supplier_id);
//		if(berth_count > 0){//与泊位有关联，返回true
//			return true;
//		}
		int plan_count = supplierMapper.getCountOfPlanBySupplierId(supplier_id);//与计划有关联，返回true，否则返回false
		
		return plan_count > 0 ? true : false;
	}

	//供应商信息删除
	@Transactional //对关联表删除操作添加Spring事务控制
	public void deleteSupplierInfo(int supplier_id) {
		//删除泊位-供应商中间表记录
		supplierMapper.deleteMapWithBerthes(supplier_id);
		//删除供应商表记录
		supplierMapper.deleteSupplierInfo(supplier_id);
	}
	
	//供应商-泊位关联-获取可关联的泊位列表
	public List<BerthInfo> getMapBerthes() {
		return supplierMapper.getMapBerthes();
	}

	//删除该供应商关联的泊位
	public void deleteMapWithBerthes(int supplier_id) {
		supplierMapper.deleteMapWithBerthes(supplier_id);
	}
	
	//添加供应商-泊位关联
	@Transactional //对多次表删除操作添加Spring事务控制
	public void addMapWithBerthes(int supplier_id, List<SupplierBerthMapper> supplierBerthMapList) {
		//删除原先的供应商-泊位关联
		supplierMapper.deleteMapWithBerthes(supplier_id);
		//添加新的供应商-泊位关联
		supplierMapper.addMapWithBerthes(supplierBerthMapList);
	}

}
