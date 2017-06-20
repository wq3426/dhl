package com.souvi.sysmanager.right.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.souvi.sysmanager.right.entity.LeftRightTree;
import com.souvi.sysmanager.right.entity.Right;
import com.souvi.sysmanager.right.mapper.rightMapper;
import com.souvi.sysmanager.role.entity.Role;
import com.souvi.sysmanager.role.mapper.roleMapper;

/**
 * 应用场景：
 * 很多时候我们想要在某个类加载完毕时干某件事情，但是使用了spring管理对象，我们这个类引用了其他类（可能是更复杂的关联），
 * 所以当我们去使用这个类做事情时发现包空指针错误，这是因为我们这个类有可能已经初始化完成，但是引用的其他类不一定初始化完成，所以发生了空指针错误.
 * 解决方案如下：
 * 写一个类继承spring的ApplicationListener监听，并监控ContextRefreshedEvent事件（容器初始化完成事件）
 * @author wq3426
 *
 */
public class RightListener implements ApplicationListener<ContextRefreshedEvent> {

	private static Logger logger = LoggerFactory.getLogger(RightListener.class);

	private rightMapper rightMapper;

	private roleMapper roleMapper;

	/**
	 * 权限缓存 key值：rightId
	 */
	private static final Map<String, Right> rightMap = new HashMap<String, Right>();
	/**
	 * 角色和权限对应缓存 key值：角色ID
	 */
	private static final Map<String, LinkedHashMap<String, Right>> roleRightMap = new LinkedHashMap<String, LinkedHashMap<String, Right>>();
	/**
	 * 权限树缓存
	 */
	private static final Map<String, LinkedHashMap<String, LeftRightTree>> rightTreeMap = new LinkedHashMap<String, LinkedHashMap<String, LeftRightTree>>();

	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {//需要执行的逻辑代码，当spring容器初始化完成后就会执行该方法。 
			logger.info("初始化权限左侧tree数据");
//			init();
		}
	}

	/**
	 * 初始化缓存数据
	 */
	private void init() {
		rightMap.clear();
		roleRightMap.clear();
		rightTreeMap.clear();
		List<Map<String, Object>> roleRightList = roleMapper.queryAllRoleRight();
		List<Right> rightList = rightMapper.getAllRightList();
		for (Right right : rightList) {
			rightMap.put(right.getRightId(), right);
			addRightTree(right);
		}
		for (Map<String, Object> roleRight : roleRightList) {
			String roleId = (String) roleRight.get("roleId");
			String rightId = (String) roleRight.get("rightId");
			addRight(roleId, rightId);
		}
	}

	/**
	 * 加载权限树缓存
	 * 
	 * @param right
	 */
	private static void addRightTree(Right right) {
		String key = right.getParentid();
		LinkedHashMap<String, LeftRightTree> rightTree;
		LeftRightTree leftRightTree;
		if (!rightTreeMap.containsKey(key)) {
			rightTree = new LinkedHashMap<String, LeftRightTree>();
		} else {
			rightTree = rightTreeMap.get(key);
		}
		if (rightTree.containsKey(right.getRightId())) {
			leftRightTree = rightTree.get(right.getRightId());
		} else {
			leftRightTree = new LeftRightTree();
			leftRightTree.setId(right.getRightId());
		}
		leftRightTree.setRightCode(right.getRightCode());
		leftRightTree.setOpen(false);
		leftRightTree.setName(right.getRightName());
		rightTree.put(right.getRightId(), leftRightTree);
		if (!rightTreeMap.containsKey(key)) {
			rightTreeMap.put(key, rightTree);
		}
	}

	/**
	 * 根据角色id和权限id 添加角色与权限对应关系
	 * 
	 * @param roleId
	 * @param rightId
	 */
	private static void addRight(String roleId, String rightId) {
		LinkedHashMap<String, Right> rights = null;
		boolean isExist = roleRightMap.containsKey(roleId);
		if (isExist) {
			rights = roleRightMap.get(roleId);
		} else {
			rights = new LinkedHashMap<String, Right>();
		}
		if (!rightMap.containsKey(rightId)) {
			return;
		}
		Right right = rightMap.get(rightId);
		rights.put(right.getRightCode(), right);
		if (!isExist) {
			roleRightMap.put(roleId, rights);
		}
	}

	/**
	 * 根据权限主节点id获取权限树
	 * 
	 * @param mainId
	 *            "-1"
	 * @return
	 */
	public static List<LeftRightTree> getRightTreeList(String mainId) {
		List<LeftRightTree> rightTreeList = new ArrayList<LeftRightTree>();
		if (!rightTreeMap.containsKey(mainId)) {
			return rightTreeList;
		}
		LinkedHashMap<String, LeftRightTree> rightTree = rightTreeMap.get(mainId);
		for (Entry<String, LeftRightTree> rightEntry : rightTree.entrySet()) {
			LeftRightTree leftRightTree = rightEntry.getValue();
			// 递归加载子节点
			leftRightTree.setChildren(getRightTreeList(leftRightTree.getId()));
			rightTreeList.add(leftRightTree);
		}
		return rightTreeList;
	}

	/*
	 * public static List<LeftRightTree> getRightTreeList(String mainId){
	 * List<LeftRightTree> rightTreeList = new ArrayList<LeftRightTree>();
	 * String json = JSONArray.fromObject(dto).toString(); return rightTreeList;
	 * }
	 */

	/**
	 * 缓存：添加权限
	 * 
	 * @param right
	 */
	public static void addRight(Right right) {
		rightMap.put(right.getRightId(), right);
		addRightTree(right);
	}

	/**
	 * 缓存：修改或添加角色与权限对应关系
	 * 
	 * @param roleId
	 *            角色ID
	 * @param rightIdList
	 *            权限ID<List>
	 */
	public static void addOrUpdateRoleRight(String roleId, List<String> rightIdList) {
		if (roleRightMap.containsKey(roleId)) {
			roleRightMap.remove(roleId);
		}
		LinkedHashMap<String, Right> newRightMap = new LinkedHashMap<String, Right>();
		for (String rightId : rightIdList) {
			if (rightMap.containsKey(rightId)) {
				Right right = rightMap.get(rightId);
				newRightMap.put(right.getRightCode(), right);
			}
		}
		roleRightMap.put(roleId, newRightMap);
	}

	/**
	 * 缓存：修改权限
	 * 
	 * @param right
	 */
	public static void updateRight(Right right) {
		rightMap.put(right.getRightId(), right);
		addRightTree(right);
	}

	/**
	 * 缓存：删除权限
	 * 
	 * @param right
	 */
	/*
	 * public static void delRight(Right right){ String rightCode =
	 * right.getRightCode(); rightMap.remove(rightCode); }
	 */
	/**
	 * 根据用户所拥有角色和权限代码 判断当前用户是否拥有该权限
	 * 
	 * @param roleList
	 * @param rightCode
	 * @return
	 */
	public static boolean isRight(List<Role> roleList, String rightCode) {
		if (CollectionUtils.isEmpty(roleList) || StringUtils.isEmpty(rightCode)) {
			return false;
		}
		for (Role role : roleList) {
			LinkedHashMap<String, Right> rightMap = roleRightMap.get(role.getRoleId());
			if (MapUtils.isEmpty(rightMap)) {
				return false;
			}
			Right right = rightMap.get(rightCode);
			// TODO 还要根据当前权限状态判断吗？？？
			return right != null;
		}
		return false;
	}

	public rightMapper getRightMapper() {
		return rightMapper;
	}

	public void setRightMapper(rightMapper rightMapper) {
		this.rightMapper = rightMapper;
	}

	public roleMapper getRoleMapper() {
		return roleMapper;
	}

	public void setRoleMapper(roleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

}
