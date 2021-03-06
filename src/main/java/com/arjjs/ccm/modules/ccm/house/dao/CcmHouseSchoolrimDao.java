/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.house.dao;

import java.util.List;

import com.arjjs.ccm.common.persistence.CrudDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseSchoolrim;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.tool.SearchTab;
import com.arjjs.ccm.tool.SearchTabMore;

/**
 * 学校DAO接口
 * 
 * @author wwh
 * @version 2018-01-10
 */
@MyBatisDao
public interface CcmHouseSchoolrimDao extends CrudDao<CcmHouseSchoolrim> {
	public int updateCoordinates(CcmHouseSchoolrim CcmHouseSchoolrim);

	//校园周边重点人员区域树
	public List<CcmHouseSchoolrim> findListSP(CcmHouseSchoolrim ccmHouseSchoolrim);

	//街道信息-大屏-滨海新区社会网格化管理信息平台
	public List<SearchTab> findListAllData(CcmHouseSchoolrim ccmHouseSchoolrim);
	
	/**
	 * @see 学生人数、教职工人数、教学经费getCountInfo
	 */
	SearchTabMore getCountInfo();
	List<CcmHouseSchoolrim> findCountSchool();
	
	List<EchartType> selectSchoolNumAllByOffice();
	List<EchartType> selectschoolEventAmbiScale();
	List<CcmHouseSchoolrim> getAreaBySchool();
}
