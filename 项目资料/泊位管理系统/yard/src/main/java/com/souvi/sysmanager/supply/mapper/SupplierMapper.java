package com.souvi.sysmanager.supply.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.berth.entity.BerthInfo;
import com.souvi.sysmanager.supply.entity.SupplierBerthMapper;
import com.souvi.sysmanager.supply.entity.SupplierInfo;

public interface SupplierMapper {

	//供应商列表查询-分页
	List<SupplierInfo> selectSupplierList(@Param("supplier_name") String supplier_name);

	//获取供应商已经关联的泊位id集合
	List<Integer> getMappedBerthIdList(@Param("supplier_id") int supplier_id);

	//供应商名称校验
	int checkSupplierNameIsExist(@Param("supplier_name") String supplier_name);

	//供应商添加
	void addSupplier(@Param("supplier") SupplierInfo supplierInfo);

	//根据id查询供应商信息
	SupplierInfo getSupplierInfoById(@Param("supplier_id") int supplier_id);

	//供应商信息修改时校验修改后的供应商名称是否重复
	int checkUpdateSupplierNameIsExist(@Param("supplier_id") Integer supplier_id, @Param("supplier_name") String supplier_name);
	
	//供应商信息修改 
	void updateSupplierInfo(@Param("supplier") SupplierInfo supplierInfo);
	
	//检查要删除的供应商是否与泊位有关联
	int getCountOfBerthBySupplierId(@Param("supplier") int supplier_id);

	//检查要删除的供应商是否与计划有关联
	int getCountOfPlanBySupplierId(@Param("supplier_id") int supplier_id);

	//删除泊位-供应商中间表记录
	void deleteBerthSupplierMap(@Param("supplier_id") int supplier_id);
	
	//供应商信息删除
	void deleteSupplierInfo(@Param("supplier_id") int supplier_id);

	//删除供应商-泊位关联
	void deleteMapWithBerthes(@Param("supplier_id") int supplier_id);
	
	//添加供应商-泊位关联
	void addMapWithBerthes(@Param("maplist") List<SupplierBerthMapper> supplierBerthMapList);

	//供应商-泊位关联-获取可关联的泊位列表
	List<BerthInfo> getMapBerthes();
	
}
