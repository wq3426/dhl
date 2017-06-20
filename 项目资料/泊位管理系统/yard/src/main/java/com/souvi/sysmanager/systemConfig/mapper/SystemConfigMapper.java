package com.souvi.sysmanager.systemConfig.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.souvi.sysmanager.systemConfig.entity.SystemConfig;

public interface SystemConfigMapper {

	public List<SystemConfig> getListByName(String name);

	public SystemConfig getById(String id);

	public List<SystemConfig> getListByParentId(String parentId);

	public List<SystemConfig> getByParentNumberAndName(@Param("number") String number, @Param("name") String name);

	public Integer updateValueById(@Param("config") SystemConfig systemConfig);

	public Integer delConfig(@Param("config") SystemConfig systemConfig);

	/**
	 * 根据name属性验证config配置中是否存在该value值
	 * 
	 * @param name
	 *            name
	 * @param value
	 *            value
	 * @return
	 */
	SystemConfig toConfigCheck(@Param("name") String name, @Param("value") String value,
			@Param("parentId") String parentId);

	/**
	 * 获取区域或城市最大顺序号
	 * 
	 * @param name
	 * @param parentId
	 * @return
	 */
	Integer getMaxNumber(@Param("name") String name, @Param("parentId") String parentId);

	Integer insertConfig(@Param("config") SystemConfig config);

}
