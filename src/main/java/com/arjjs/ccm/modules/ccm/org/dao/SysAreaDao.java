/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.dao;

import com.arjjs.ccm.common.persistence.CrudDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.org.entity.SysArea;
import com.arjjs.ccm.plugins.InterceptorEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 区域扩展表（区域查询）DAO接口
 * @author pengjianqiang
 * @version 2018-01-29
 */
@MyBatisDao
public interface SysAreaDao extends CrudDao<SysArea> {

	List<SysArea> queryBuildCollectStat(SysArea sysArea);
	List<SysArea> queryRoomCollectStat(SysArea sysArea);
	List<SysArea> queryPeopleCollectStat(SysArea sysArea);
	List<SysArea> queryNpseCollectStat(SysArea sysArea);
	List<SysArea> queryPlaceCollectStat(SysArea sysArea);
	List<SysArea> selectAreaByType(@Param("type") String type);
	List<SysArea> findByPid(SysArea area);

    List<String> selectAreaIdByParentIdAndId(InterceptorEntity interceptorEntity);
}