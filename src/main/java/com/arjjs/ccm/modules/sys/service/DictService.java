/**
 * Copyright &copy; 2012-2016 <a href="http://www.arjjs.com">arjjs</a> All rights reserved.
 */
package com.arjjs.ccm.modules.sys.service;

import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.common.utils.CacheUtils;
import com.arjjs.ccm.modules.ccm.rest.entity.SysDictLbVo;
import com.arjjs.ccm.modules.ccm.sys.entity.SysDict;
import com.arjjs.ccm.modules.sys.dao.DictDao;
import com.arjjs.ccm.modules.sys.entity.Dict;
import com.arjjs.ccm.modules.sys.utils.DictUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典Service
 * @author admin001
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {
	
	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
		CacheUtils.remove(DictUtils.CACHE_DICT_MAP);
	}

	public List<Dict> findListAll(Dict dict){
		return dao.findListAll(new Dict());
	}
	/**
	 * <!--查询所有流程的流程标识和名称  -->
	 * Dict.getValue:流程标识  
	 * Dict.getName:流程名称  
	 * @return
	 */
	public List<Dict> findActKeyAndName(){
		return dao.findActKeyAndName();
	}
	@Transactional(readOnly = false)
	public void updateTypeAndLabel(Dict dict) {
	  dao.updateTypeAndLabel(dict);		
	};
	public List<Dict> findAll(Dict dict) {
		return dao.findAll( dict);
	};
	public List<Dict> findNumberAll(Dict dict){
		return dao.findNumberAll(dict);
	}
	public List<Dict> findAllNumber(Dict dict){
		return dao.findAllNumber(dict);
	}

	public List<SysDictLbVo> selectAlarmTypeInfo() {
		return dao.selectAlarmTypeInfo();
	}

	public List<SysDict> selectDictByType(String type) {
		return dao.selectDictByType(type);
	}
}
