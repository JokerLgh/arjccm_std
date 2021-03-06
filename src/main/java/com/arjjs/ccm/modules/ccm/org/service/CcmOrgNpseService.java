/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.service;

import com.arjjs.ccm.common.persistence.Page;
import com.arjjs.ccm.common.service.CrudService;
import com.arjjs.ccm.common.utils.StringUtils;
import com.arjjs.ccm.modules.ccm.ccmsys.entity.CcmUploadLog;
import com.arjjs.ccm.modules.ccm.ccmsys.service.CcmUploadLogService;
import com.arjjs.ccm.modules.ccm.org.dao.CcmOrgNpseDao;
import com.arjjs.ccm.modules.ccm.org.entity.CcmOrgNpse;
import com.arjjs.ccm.modules.ccm.pop.entity.CcmAreaPoint;
import com.arjjs.ccm.modules.ccm.sys.entity.CcmAreaPointVo;
import com.arjjs.ccm.modules.ccm.sys.entity.SysDicts;
import com.arjjs.ccm.modules.ccm.sys.service.SysDictsService;
import com.arjjs.ccm.modules.sys.dao.AreaDao;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.modules.sys.utils.UserUtils;
import com.arjjs.ccm.tool.*;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 非公有制经济组织Service
 * 
 * @author wwh
 * @version 2018-01-26
 */
@Service
@Transactional(readOnly = true)
public class CcmOrgNpseService extends CrudService<CcmOrgNpseDao, CcmOrgNpse> {
	//查询区域父级级别
	public static final int num = 5;

	@Autowired
	private CcmOrgNpseDao ccmOrgNpseDao;
	@Autowired
	private SysDictsService sysDictsService;
	
	//上传上级平台记录
	@Autowired
	private CcmUploadLogService ccmUploadLogService;

	@Autowired
	private AreaDao areaDao;

	public CcmOrgNpse get(String id) {
		return super.get(id);
	}

	public List<CcmOrgNpse> findList(CcmOrgNpse ccmOrgNpse) {
		return super.findList(ccmOrgNpse);
	}

	public Page<CcmOrgNpse> findPage(Page<CcmOrgNpse> page, CcmOrgNpse ccmOrgNpse) {
		return super.findPage(page, ccmOrgNpse);
	}

	// 安全生产重点
	public Page<CcmOrgNpse> findPageSafe(Page<CcmOrgNpse> page, CcmOrgNpse ccmOrgNpse) {
		return super.findPage(page, ccmOrgNpse);
	}

	@Transactional(readOnly = false)
	public void save(CcmOrgNpse ccmOrgNpse) {
		boolean isNew = false;
		if (ccmOrgNpse.getId() == null || "".equals(ccmOrgNpse.getId())) {//无主键ID，则是新记录
			isNew = true;
		}
		if (ccmOrgNpse.getIsNewRecord()) {//指定了是新增记录，则算新记录
			isNew = true;
		}
		System.out.println(ccmOrgNpse.toString());
		super.save(ccmOrgNpse);

		//上传上级平台记录
		if (!SysConfigInit.UPPER_URL.equals("")) {//有上级平台地址时，才存入上传上级平台记录的日志
			CcmUploadLog uploadLog = new CcmUploadLog();
			if (isNew) {//新增数据
				uploadLog.setOperateType("1");
				uploadLog.setRemarks("新增非公有制经济组织数据：" + ccmOrgNpse.getCompName());
			} else {//编辑数据
				uploadLog.setOperateType("2");
				uploadLog.setRemarks("编辑非公有制经济组织数据：" + ccmOrgNpse.getCompName());
			}
			uploadLog.setTableName("ccm_org_npse");
			uploadLog.setDataId(ccmOrgNpse.getId());
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
	public void delete(CcmOrgNpse ccmOrgNpse) {
		super.delete(ccmOrgNpse);

		//上传上级平台记录
		if (!SysConfigInit.UPPER_URL.equals("")) {//有上级平台地址时，才存入上传上级平台记录的日志
			CcmUploadLog uploadLog = new CcmUploadLog();
			uploadLog.setOperateType("3");
			uploadLog.setRemarks("删除非公有制经济组织数据：" + ccmOrgNpse.getCompName());
			uploadLog.setTableName("ccm_org_npse");
			uploadLog.setDataId(ccmOrgNpse.getId());
			uploadLog.setUploadStatus("1");
			User user = UserUtils.getUser();
			if (user == null || StringUtils.isBlank(user.getId())){
				uploadLog.setCreateBy(new User("1"));
				uploadLog.setUpdateBy(new User("1"));
			}
			ccmUploadLogService.save(uploadLog);
		}
	}

	// 更新点坐标信息
	@Transactional(readOnly = false)
	public boolean updateCoordinates(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.updateCoordinates(ccmOrgNpse)>0;
	}
	//报表:非公有经济组织数量
	public SearchTabMore findNumStatistics() {
		return ccmOrgNpseDao.findNumStatistics();
	}

	//报表:非公有经济组织类别
	public List<EchartType> findCompType() {
		return ccmOrgNpseDao.findCompType();
	}

	//报表:非公有经济组织控股情况
	public List<EchartType> findHoldCase() {
		return ccmOrgNpseDao.findHoldCase();
	}

	//报表:非公有经济组织安全隐患类型
	public List<EchartType> findSafeHazaType() {
		return ccmOrgNpseDao.findSafeHazaType();
	}

	//报表:非公有经济组织重点类型
	public List<EchartType> findCompImpoType() {
		return ccmOrgNpseDao.findCompImpoType();
	}

	//报表:非公有经济组织是否危化企业
	public List<EchartType> findDangComp() {
		return ccmOrgNpseDao.findDangComp();
	}

	//报表:非公有经济组织关注程度
	public List<EchartType> findConcExte() {
		return ccmOrgNpseDao.findConcExte();
	}

	//报表:重点场所数据重点类型
	public List<EchartType> findKeyPlaceType(EchartType echartType) {
		return ccmOrgNpseDao.findKeyPlaceType(echartType);
	}

	//报表:重点场所数据重点类型-学校
	public List<EchartType> findSchool() {
		return ccmOrgNpseDao.findSchool();
	}

	//报表:重点场所数据-学校办学类型统计
	public List<EchartType> findSchoolType() {
		return ccmOrgNpseDao.findSchoolType();
	}

	//重点企业数量
	public int findCompImpoTypes() {
		return ccmOrgNpseDao.findCompImpoTypes();
	}

	//报表:重点场所数据区域
	public List<EchartType> findArea(EchartType echartType) {
		return ccmOrgNpseDao.findArea(echartType);
	}

	//危化企业
	public List<EchartType> getDangComp() {
		return ccmOrgNpseDao.getDangComp();
	}

	//风险级别数量
	public int findNumRiskRank(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.findNumRiskRank(ccmOrgNpse);
	}

	//首页概况各种总数查询//安全生产重点//消防重点//物流安全
	public int findCompImpoTypeData(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.findCompImpoTypeData(ccmOrgNpse);
	}

	//重点企业分布
	public List<EchartType> getCompImpoTypeTrue() {
		return ccmOrgNpseDao.getCompImpoTypeTrue();
	}

	//治安重点场所
	public List<EchartType> getSafePubData() {
		return ccmOrgNpseDao.getSafePubData();
	}

	//企业数量-大屏-滨海新区社会网格化管理信息平台
	public String getqiyeNumData(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.getqiyeNumData(ccmOrgNpse);
	}

	//风险单位数据分析-按街道-大屏-滨海新区社会网格化管理信息平台
	public List<EchartType> findSumByString(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.findSumByString(ccmOrgNpse);
	}

	//街道信息-大屏-滨海新区社会网格化管理信息平台
	public List<SearchTab> findListAllData(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.findListAllData(ccmOrgNpse);
	}

	//报表:非公有经济组织重点类型-无关联
	public List<EchartType> findCompImpoType2() {
		return ccmOrgNpseDao.findCompImpoType2();
	}


    public List<EchartType> getOrgNpseByRegiType(CcmOrgNpse ccmOrgNpse) {
		return ccmOrgNpseDao.getOrgNpseByRegiType(ccmOrgNpse);
    }
	//根据行业类型统计
	public List<EchartType> getOrgNpseBysysdicts(EchartType echartType) {
		return ccmOrgNpseDao.getOrgNpseBysysdicts(echartType);
	}
	//各区域企业分布
	public List<EchartType> getOrgNpseBysysArea() {
		return ccmOrgNpseDao.getOrgNpseBysysArea();
	}


	/**
	 * 查询企业注册资金
	 * @return
	 */
	public List<EchartType> getOrgNpseregisteredBysysArea() {
		List<EchartType> list = Lists.newArrayList();
		List<CcmOrgNpse> reslist = ccmOrgNpseDao.getRegistered();
		Map<String,String> map = Maps.newHashMap();
		for(int i=0;i<reslist.size();i++){
			Area area = areaDao.get(reslist.get(i).getId());
			if(area!=null){
				Area resarea = getAreabyType(area);
				if(map.containsKey(resarea.getName())){
					String ss = getBigDecimalByString(map.get(resarea.getName()),reslist.get(i).getRegisteredFund());
					map.put(resarea.getName(), ss);
				} else {
					map.put(resarea.getName(), reslist.get(i).getRegisteredFund());
				}
			}

		}

		for(Map.Entry<String,String> mapone: map.entrySet()){
			EchartType echartType = new EchartType();
			echartType.setType(mapone.getKey());
			echartType.setValue(mapone.getValue());
			list.add(echartType);
		}

		return list;
	}

	/**
	 *  查询指定父节点的区域
	 * @param area
	 * @return
	 */
	public Area getAreabyType(Area area){
		if(Integer.parseInt(area.getType()) <= num){
			return area;
		} else {
			Area res = areaDao.get(area.getParent().getId());
			return getAreabyType(res);
		}
	}

	/**
	 * 计算 企业注册资金
	 * @param a
	 * @param b
	 * @return
	 */
	public String getBigDecimalByString(String a,String b){
		if(StringUtils.isBlank(b)){
			return a;
		}

		BigDecimal sum = new BigDecimal(a).add(new BigDecimal(b));
		return sum.toString();
	}


	public  List<CcmAreaPoint> selectByAreaGIdAndName(CcmAreaPointVo areaPointVo) {
		return dao.selectByAreaGIdAndName(areaPointVo);
	}
	//按照风险级别分组
	public List<EchartType> getNpseGroupByRiskType(){
		List<EchartType> resultNpseList = new ArrayList<>();
		List<EchartType> npseGroupByRiskType = dao.getNpseGroupByRiskType();
		List<String> resultList = new ArrayList<>();
		npseGroupByRiskType.forEach(echartType -> resultList.add(echartType.getType()));
		List<String> dictList = new ArrayList<>();
		List<SysDicts> dictPoList = sysDictsService.findValueByType("ccm_npse_risk_rank");
		dictPoList.forEach(dictPo -> dictList.add(dictPo.getLabel()));
		List<String> diffrent = Constants.getDiffrent(resultList, dictList);
		resultNpseList.addAll(npseGroupByRiskType);
		diffrent.forEach(dict-> {
			EchartType echartType = new EchartType();
			echartType.setType(dict);
			echartType.setValue("0");
			resultNpseList.add(echartType);
		});
		return resultNpseList;
	}
}