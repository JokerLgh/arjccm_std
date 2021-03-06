/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.sys.dao;

import com.arjjs.ccm.common.persistence.TreeDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.sys.entity.SysDicts;

import java.util.List;

/**
 * 字典树DAO接口
 * @author liang
 * @version 2018-10-16
 */
@MyBatisDao
public interface SysDictsDao extends TreeDao<SysDicts> {

	List<String> findTypeList(SysDicts sysDicts);
	//根据类型名称查询警情状态信息
	List<SysDicts> findAlarmInfoByTypeName(SysDicts sysDicts);

	//根据type条件查询全部
	List<SysDicts> findAllListByType(String type);
	
	//根据type条件查询value
	List<SysDicts> findValueByType(String type);
}