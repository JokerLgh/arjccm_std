/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.report.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.arjjs.ccm.common.persistence.CrudDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.report.entity.CcmPeopleAmount;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.tool.SearchTab;
import com.arjjs.ccm.tool.SearchTabMore;

/**
 * 人口总数统计DAO接口
 * @author arj
 * @version 2018-01-20
 */
@MyBatisDao
public interface CcmPeopleAmountDao extends CrudDao<CcmPeopleAmount> {
	/**
	 * @see 查询当的
	 * @param column
	 * @return
	 */
	List<EchartType> findListBySum(@Param("column")String column);

	/**
	 * @see 查询当日的新增人口数量
	 * @param column
	 * @return
	 */
	List<EchartType> findListByMon(@Param("column")String column);
	/**
	 * @see 实有人口统计情况
	 * @param column
	 * @return
	 */
	SearchTab getAnalyzePopData();
	/**
	 * @see 特殊人群分析
	 * @param column
	 * @return
	 */
	SearchTabMore getSpecialPopData();
	/**
	 * @see 实有人口总数、新增
	 * @param column
	 * @return
	 */
	EchartType getCameraTotal();
	/**
	 * @see 本月人口信息getnumPopFollowPop
	 * @param column
	 * @return
	 */
	SearchTabMore getnumPopFollowPop();
	/**
	 * @see 重点青少年统计
	 * @param column
	 * @return
	 */
	List<EchartType> getnumPopFollowEcharts();
	/**
	 * @see 社区特殊人群类型统计
	 * @return
	 */
	List<SearchTabMore> findPopByArea(CcmPeopleAmount ccmPeopleAmount);
	/**
	 * @see 社区特殊人群变化趋势
	 * @return
	 */
	List<SearchTabMore> findPopTrendByArea(CcmPeopleAmount ccmPeopleAmount);
	/**
	 * @see 首页社区弹框：人口结构
	 * @return
	 */
	CcmPeopleAmount findMonthAreaAmount(CcmPeopleAmount ccmPeopleAmount);
	
	//按街道查询所有人员及事件总和
	List<EchartType> findPeopleAndEventByArea();

	//各街道人口数据对比（重点人群与特殊人群关系）-大屏-滨海新区社会网格化管理信息平台
	List<SearchTab> getStreetPopData();


	/**
	 * @see 查询总的数量
	 * @param
	 * @return
	 */
	List<EchartType> findListBySumNum();

	//获取所有特殊信息上个月的总数
	SearchTabMore getnumPopFollowPopQL();
}