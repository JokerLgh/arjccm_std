/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.citycomponents.service;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.ccmsys.entity.CcmUploadLog;
import com.arjjs.ccm.modules.ccm.ccmsys.service.CcmUploadLogService;
import com.arjjs.ccm.modules.ccm.citycomponents.dao.CcmCityComponentsDao;
import com.arjjs.ccm.modules.ccm.citycomponents.entity.CcmCityComponents;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmAreaPoint;
import com.arjjs.ccm.modules.ccm.sys.entity.CcmAreaPointVo;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.EchartType;
import com.arjjs.ccm.tool.SysConfigInit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 城市部件Service
 * @author pengjianqiang
 * @version 2018-03-06
 */
@Service
@Transactional(readOnly = true)
public class CcmCityComponentsService extends CrudService<CcmCityComponentsDao, CcmCityComponents> {

	@Autowired
	private CcmCityComponentsDao ccmCityComponentsDao;
	
	//上传上级平台记录
	@Autowired
	private CcmUploadLogService ccmUploadLogService;
	
	public CcmCityComponents get(String id) {
		return super.get(id);
	}
	
	public List<CcmCityComponents> findList(CcmCityComponents ccmCityComponents) {
		return super.findList(ccmCityComponents);
	}
	
	public Page<CcmCityComponents> findPage(Page<CcmCityComponents> page, CcmCityComponents ccmCityComponents) {
		return super.findPage(page, ccmCityComponents);
	}
	
	@Transactional(readOnly = false)
	public void save(CcmCityComponents ccmCityComponents) {
		boolean isNew = false;
		if (ccmCityComponents.getId() == null || "".equals(ccmCityComponents.getId())) {//无主键ID，则是新记录
			isNew = true;
		}
		if (ccmCityComponents.getIsNewRecord()) {//指定了是新增记录，则算新记录
			isNew = true;
		}
		super.save(ccmCityComponents);

		//上传上级平台记录
		if (!SysConfigInit.UPPER_URL.equals("")) {//有上级平台地址时，才存入上传上级平台记录的日志
			CcmUploadLog uploadLog = new CcmUploadLog();
			if (isNew) {//新增数据
				uploadLog.setOperateType("1");
				uploadLog.setRemarks("新增城市部件：" + ccmCityComponents.getName());
			} else {//编辑数据
				uploadLog.setOperateType("2");
				uploadLog.setRemarks("编辑城市部件：" + ccmCityComponents.getName());
			}
			uploadLog.setTableName("ccm_city_components");
			uploadLog.setDataId(ccmCityComponents.getId());
			uploadLog.setUploadStatus("1");
			User user = UserUtils.getUser();
			if (user == null || StringUtils.isBlank(user.getId())){
				uploadLog.setCreateBy(new User("1"));
				uploadLog.setUpdateBy(new User("1"));
			}
			ccmUploadLogService.save(uploadLog);
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(CcmCityComponents ccmCityComponents) {
		super.delete(ccmCityComponents);

		//上传上级平台记录
		if (!SysConfigInit.UPPER_URL.equals("")) {//有上级平台地址时，才存入上传上级平台记录的日志
			CcmUploadLog uploadLog = new CcmUploadLog();
			uploadLog.setOperateType("3");
			uploadLog.setRemarks("删除城市部件：" + ccmCityComponents.getName());
			uploadLog.setTableName("ccm_city_components");
			uploadLog.setDataId(ccmCityComponents.getId());
			uploadLog.setUploadStatus("1");
			User user = UserUtils.getUser();
			if (user == null || StringUtils.isBlank(user.getId())){
				uploadLog.setCreateBy(new User("1"));
				uploadLog.setUpdateBy(new User("1"));
			}
			ccmUploadLogService.save(uploadLog);
		}
	}

	// 更新点位坐标信息
	@Transactional(readOnly = false)
	public boolean updateCoordinates(CcmCityComponents ccmCityComponents) {
		return ccmCityComponentsDao.updateCoordinates(ccmCityComponents)>0;
	}

	//公用设施
	public List<EchartType> getCityTypeGY(CcmCityComponents ccmCityComponentsCY) {
		return ccmCityComponentsDao.getCityTypeGY(ccmCityComponentsCY);
	}

	//城市部件数量
	public int findListNum() {
		return ccmCityComponentsDao.findListNum();
	}

	public List<CcmAreaPoint> selectByAreaGIdAndName(CcmAreaPointVo areaPointVo){
		return dao.selectByAreaGIdAndName(areaPointVo);
	}
}