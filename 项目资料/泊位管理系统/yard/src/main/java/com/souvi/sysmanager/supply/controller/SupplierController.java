package com.souvi.sysmanager.supply.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Min;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
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
import com.souvi.sysmanager.supply.entity.SupplierBerthMapper;
import com.souvi.sysmanager.supply.entity.SupplierInfo;
import com.souvi.sysmanager.supply.service.SupplierService;
import com.souvi.sysmanager.user.entity.User;

/**
 * 供应商管理
 * @author wq3426
 *
 */
@Controller
@RequestMapping("/supply")
public class SupplierController extends BasicController {
	//属于每个Controller类的日志对象
	private static final Logger logger = LoggerFactory.getLogger(SupplierController.class);

	@Autowired
	private SupplierService supplierService;
	
	//供应商列表查询-分页
	@RequestMapping(value="/selectSupplierList", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject selectSupplierList(HttpServletRequest request, 
			@RequestParam(required=false, defaultValue="1") @Min(value=1, message="page最小为1") int page, 
			@RequestParam(required=false, defaultValue="10") @Min(value=1, message="pageSize最小为1") int pageSize,
			@RequestParam(required=false, defaultValue="") String supplier_name){
		
		try {
			// 使用分页工具类进行分页
//			PageHelper.startPage(page, pageSize);
			List<SupplierInfo> supplierList = supplierService.selectSupplierList(supplier_name);
//			PageInfo<SupplierInfo> pageInfo = new PageInfo<SupplierInfo>(supplierList);
			
			//获取供应商已经关联的泊位id集合，以","分隔
			JSONArray supplierArray = net.sf.json.JSONArray.fromObject(supplierList);
			for(int i=0; i<supplierArray.size(); i++){
				net.sf.json.JSONObject supplierObj = supplierArray.getJSONObject(i);
				List<Integer> mappedBerthIdList = supplierService.getMappedBerthIdList(supplierObj.getInt("supplier_id"));
				
				supplierObj.put("berth_ids", StringUtils.join(mappedBerthIdList, ","));
			}
			
			setResultJSON(1, "", supplierArray);
			
			logger.info("查询供应商列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商名称校验
	@RequestMapping(value="/checkSupplierName", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject checkSupplierName(HttpServletRequest request, @RequestParam(required=true) String supplier_name){
		
		try {
			JSONObject data = new JSONObject();
			
			int is_exist = supplierService.checkSupplierNameIsExist(supplier_name);
			data.put("is_exist", is_exist);// 1 已经存在 0 不存在
			
			setResultJSON(1, "", data);
			
			logger.info("供应商名称校验-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商添加  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	@RequestMapping(value="/addSupplier", produces="application/json;charset=UTF-8", 
			method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody JSONObject addSupplier(HttpServletRequest request, @RequestBody SupplierInfo supplierInfo){
		try {
			//输入校验(分组：添加 AddValidator)
			ValidationUtil.validate(supplierInfo, AddValidator.class);
			
			int is_exist = supplierService.checkSupplierNameIsExist(supplierInfo.getSupplier_name());
			if(is_exist == 1){//已经存在，提示
				setResultJSON(0, "该供应商已经存在，请检查");
			}else{
				User currentLoginUser = SessionUser.getLoginUser(request);//获取当前登录用户
				
				supplierInfo.setCreater(currentLoginUser.getUser_name());
				supplierInfo.setCreate_time(DateUtil.getSystemDate());
				supplierInfo.setLast_updater(currentLoginUser.getUser_name());
				supplierInfo.setUpdate_time(DateUtil.getSystemDate());
				supplierService.addSupplier(supplierInfo);
				
				setResultJSON(1, "", "");
			}
			
			logger.info("供应商添加-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//根据id查询供应商信息
	@RequestMapping(value="/getSupplierInfoById", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getSupplierInfoById(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="supplier_id应该大于0") int supplier_id){
		try {
			SupplierInfo supplierInfo = supplierService.getSupplierInfoById(supplier_id);
			
			setResultJSON(1, "", net.sf.json.JSONObject.fromObject(supplierInfo));
			
			logger.info("根据id查询供应商信息-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商信息修改  请求类型为POST，请求的参数类型为json，即Content Type为"application/json"
	@RequestMapping(value="/updateSupplierInfo", produces="application/json;charset=UTF-8", 
			method=RequestMethod.POST, consumes="application/json")
	public @ResponseBody JSONObject updateSupplierInfo(HttpServletRequest request, @RequestBody SupplierInfo supplierInfo){
		try {
			//输入校验(分组：修改 UpdateValidator)
			ValidationUtil.validate(supplierInfo, UpdateValidator.class);
			
			int is_exist = supplierService.checkUpdateSupplierNameIsExist(supplierInfo.getSupplier_id(), supplierInfo.getSupplier_name());
			if(is_exist == 1){//已经存在，提示
				setResultJSON(0, "该供应商已经存在，请检查");
			}else{
				User currentLoginUser = SessionUser.getLoginUser(request);//获取当前登录用户
				
				supplierInfo.setLast_updater(currentLoginUser.getUser_name());
				supplierInfo.setUpdate_time(DateUtil.getSystemDate());
				supplierService.updateSupplierInfo(supplierInfo);
				
				setResultJSON(1, "", "");
			}
			
			logger.info("供应商信息修改-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商信息删除 当该供应商已经与某个计划关联过，提示不允许删除
	@RequestMapping(value="/deleteSupplierInfo", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject deleteSupplierInfo(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="supplier_id应该大于0") int supplier_id){
		try {
			boolean has_relation = supplierService.checkRelationWithOtherTable(supplier_id);//检查是否与泊位、计划有关联
			if(has_relation){//存在关联，不允许删除
				setResultJSON(0, "该供应商已经与某个计划关联，不允许删除");
			}else{
				supplierService.deleteSupplierInfo(supplier_id);
				
				setResultJSON(1, "", "");
			}
			
			logger.info("供应商信息删除-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商-泊位关联-获取可关联的泊位列表
	@RequestMapping(value="/getMapBerthes", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject getMapBerthes(HttpServletRequest request){
		try {
			//获取所有可关联的泊位列表
			List<BerthInfo> mapBerthesList = supplierService.getMapBerthes();
			
			if(mapBerthesList.size() > 0){
				JSONArray resultArray = new JSONArray();
				for(BerthInfo berth : mapBerthesList){
					net.sf.json.JSONObject berthObj = new net.sf.json.JSONObject();
					berthObj.put("berth_id", berth.getBerth_id());
					berthObj.put("berth_number", berth.getBerth_number());
					
					resultArray.add(berthObj);
				}
				setResultJSON(1, "", resultArray);
			} else{
				setResultJSON(0, "没有可关联的泊位");
			}
			
			logger.info("供应商-泊位关联-获取可关联的泊位列表-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
	//供应商-泊位关联 泊位号id字符串berth_ids，以","为分隔符
	@RequestMapping(value="/addMapWithBerthes", produces="application/json;charset=UTF-8")
	public @ResponseBody JSONObject addMapWithBerthes(HttpServletRequest request, 
			@RequestParam(required=true) @Min(value=1, message="supplier_id应该大于0") int supplier_id,
			@RequestParam(required=true) String berth_ids){
		try {
			if(supplier_id != 0 && "".equals(berth_ids)){//berth_ids为空字符串，则删除该供应商关联的泊位
				supplierService.deleteMapWithBerthes(supplier_id);
			}else if(supplier_id != 0 && !"".equals(berth_ids)){//berth_ids不为空字符串，则重新创建该供应商关联的泊位
				User currentLoginUser = SessionUser.getLoginUser(request);//获取当前登录用户
				
				String[] berthIdArray = berth_ids.split(",");
				if(berthIdArray.length > 0){
					List<SupplierBerthMapper> supplierBerthMapList = new ArrayList<SupplierBerthMapper>();
					for(String berthId : berthIdArray){
						SupplierBerthMapper supplierBerthMapper = new SupplierBerthMapper();
						
						supplierBerthMapper.setBid(Integer.valueOf(berthId));
						supplierBerthMapper.setSid(supplier_id);
						supplierBerthMapper.setCreater(currentLoginUser.getUser_name());
						supplierBerthMapper.setCreate_time(DateUtil.getSystemDate());
						
						supplierBerthMapList.add(supplierBerthMapper);
					}
					
					supplierService.addMapWithBerthes(supplier_id, supplierBerthMapList);
				}
			}
			
			setResultJSON(1, "", "");
			
			logger.info("供应商-泊位关联-请求成功");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.error(e.getMessage());
			
			setResultJSON(0, e.getMessage());
		}
		
		return resultObj;
	}
	
}
