/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.book.dao;

import com.arjjs.ccm.common.persistence.CrudDao;
import com.arjjs.ccm.common.persistence.annotation.MyBatisDao;
import com.arjjs.ccm.modules.ccm.book.entity.CcmDatabaseBook;

import java.util.List;

/**
 * 资料库录入管理DAO接口
 * @author cdz
 * @version 2019-09-16
 */
@MyBatisDao
public interface CcmDatabaseBookDao extends CrudDao<CcmDatabaseBook> {

    List<CcmDatabaseBook> findAllList();

    List<CcmDatabaseBook> findListById(String id);
    
    CcmDatabaseBook getArticleInfo(CcmDatabaseBook var1);

    List<CcmDatabaseBook> findByParentIdsLike(CcmDatabaseBook var1);

    List<CcmDatabaseBook> findByUserId(CcmDatabaseBook var1);

    int updateParentIds(CcmDatabaseBook var1);

    int updateSort(CcmDatabaseBook var1);

    List<CcmDatabaseBook> findByUserIdPid(CcmDatabaseBook var1);

    List<CcmDatabaseBook> findAllListByPid(CcmDatabaseBook var1);
}