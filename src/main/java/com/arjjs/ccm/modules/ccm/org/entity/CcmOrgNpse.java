/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.User;
import com.google.common.collect.Lists;
import com.arjjs.ccm.common.persistence.DataEntity;

/**
 * 非公有制经济组织Entity
 * @author wwh
 * @version 2018-01-26
 */
public class CcmOrgNpse extends DataEntity<CcmOrgNpse> {
	
	private static final long serialVersionUID = 1L;
	private String compId;		// 工商执照注册号
	private String compName;		// 名称
	private String compType;		// 类别
	private String compAdd;		// 场所地址
	private String compTl;		// 联系方式
	private Integer companyNum;		// 员工数
	private String legalReprCode;		// 法定代表人证件代码
	private String legalReprId;		// 法定代表人证件号码
	private String legalReprName;		// 法定代表人姓名
	private String legalReprTl;		// 法定代表人联系方式
	private String secuName;		// 治保负责人姓名
	private String secuPhone;		// 治保负责人联系方式
	private String entePrinName;		// 企业负责人姓名
	private Integer dangComp;		// 是否危化企业
	private String entePrincipalTl;		// 企业负责人联系方式
	private String safeHazaType;		// 安全隐患类型
	private String concExte;		// 关注程度
	private String riskRank;		// 风险级别
	private String regiType;		// 登记注册类型
	private Integer estaOrgaCond;		// 是否具备建立中共党组织条件
	private String holdCase;		// 控股情况
	private String compImpoType;		// 重点类型
	private Integer estaOrga;		// 是否有中共党组织
	private Integer partyMem;		// 中共党员数量
	private Integer laborUnion;		// 是否有工会
	private Integer laborUnionNum;		// 工会会员数量
	private Integer youthLeagOrga;		// 是否有共青团组织
	private Integer youthLeagOrgaNum;		// 共青团团员数量
	private Integer womenOrg;		// 是否有妇联组织
	private Integer womenNum;		// 妇女数量
	private Area area;		// 所属网格
	private String registeredFund;		// 注册资金
	private String manageScope;		// 经营范围
	private String enteType;		// 企业类型
	private String servBrand;		// 服务品牌
	private String industry;		// 所属行业
	private Double compArea;		// 面积
	private String dangerCase;		// 隐患情况
	private String reformCase;		// 安全整改情况
	private String images;		// 图片
	private Integer survCameNum;		// 监控摄像机数量
	private Integer xRayNum;		// X光机数量
	private Integer checPack;		// 是否落实100%先验视后封箱
	private Integer realName;		// 是否落实100%寄递实名制
	private Integer xRayChec;		// 是否落实100%X光机安检
	private String areaMap;		// 区域图
	private String areaPoint;		// 中心点
	private String icon;		// 图标
	private Date registerDate;	// 登记日期
	private Date beginRegisterDate;		// 开始登记日期
	private Date endRegisterDate;		// 结束登记日期
	private String more1;		// 冗余字段1
	private String more2;		// 冗余字段2
	private String more3;		// 冗余字段3
	private String concExteLable;	//app接口使用
	private	int is_special;//是否特业场所
	private	int is_sdyq;//是否水电油气
	private User checkUser;		// 拦截器中使用该用户进行权限拦截，App的rest接口使用
	private String[] industryList;		// 所属行业数组
	private String count;
	
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public User getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	
	public CcmOrgNpse() {
		super();
	}

	public CcmOrgNpse(String id){
		super(id);
	}

	@Length(min=0, max=64, message="工商执照注册号长度必须介于 0 和 64之间")
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@Length(min=0, max=100, message="名称长度必须介于0 和 100 之间")
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}
	
	@Length(min=0, max=2, message="类别长度必须介于 0 和 2 之间")
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}
	
	@Length(min=0, max=200, message="场所地址长度必须介于 0 和 200 之间")
	public String getCompAdd() {
		return compAdd;
	}

	public void setCompAdd(String compAdd) {
		this.compAdd = compAdd;
	}
	
	@Length(min=0, max=50, message="企业联系方式长度必须介于 0 和 50 之间")
	public String getCompTl() {
		return compTl;
	}

	public void setCompTl(String compTl) {
		this.compTl = compTl;
	}
	
	public Integer getCompanyNum() {
		return companyNum;
	}

	public void setCompanyNum(Integer companyNum) {
		this.companyNum = companyNum;
	}
	
	@Length(min=0, max=3, message="法定代表人证件代码长度必须介于 0 和 3 之间")
	public String getLegalReprCode() {
		return legalReprCode;
	}

	public void setLegalReprCode(String legalReprCode) {
		this.legalReprCode = legalReprCode;
	}
	
	@Length(min=0, max=50, message="法定代表人证件号码长度必须介于 0 和 50 之间")
	public String getLegalReprId() {
		return legalReprId;
	}

	public void setLegalReprId(String legalReprId) {
		this.legalReprId = legalReprId;
	}
	
	@Length(min=0, max=80, message="法定代表人姓名长度必须介于 0 和 80 之间")
	public String getLegalReprName() {
		return legalReprName;
	}

	public void setLegalReprName(String legalReprName) {
		this.legalReprName = legalReprName;
	}
	
	@Length(min=0, max=50, message="法定代表人联系方式长度必须介于 0 和 50 之间")
	public String getLegalReprTl() {
		return legalReprTl;
	}

	public void setLegalReprTl(String legalReprTl) {
		this.legalReprTl = legalReprTl;
	}
	
	@Length(min=0, max=50, message="治保负责人姓名长度必须介于 0 和 50 之间")
	public String getSecuName() {
		return secuName;
	}

	public void setSecuName(String secuName) {
		this.secuName = secuName;
	}
	
	@Length(min=0, max=50, message="治保负责人联系方式长度必须介于 0 和 50 之间")
	public String getSecuPhone() {
		return secuPhone;
	}

	public void setSecuPhone(String secuPhone) {
		this.secuPhone = secuPhone;
	}
	
	@Length(min=0, max=50, message="企业负责人姓名长度必须介于 0 和 50 之间")
	public String getEntePrinName() {
		return entePrinName;
	}

	public void setEntePrinName(String entePrinName) {
		this.entePrinName = entePrinName;
	}
	
	public Integer getDangComp() {
		return dangComp;
	}

	public void setDangComp(Integer dangComp) {
		this.dangComp = dangComp;
	}
	
	@Length(min=0, max=50, message="企业负责人联系方式长度必须介于 0 和 50 之间")
	public String getEntePrincipalTl() {
		return entePrincipalTl;
	}

	public void setEntePrincipalTl(String entePrincipalTl) {
		this.entePrincipalTl = entePrincipalTl;
	}
	
	@Length(min=0, max=2, message="安全隐患类型长度必须介于 0 和 2 之间")
	public String getSafeHazaType() {
		return safeHazaType;
	}

	public void setSafeHazaType(String safeHazaType) {
		this.safeHazaType = safeHazaType;
	}
	
	@Length(min=0, max=2, message="关注程度长度必须介于 0 和 2 之间")
	public String getConcExte() {
		return concExte;
	}

	public void setConcExte(String concExte) {
		this.concExte = concExte;
	}
	
	@Length(min=0, max=2, message="风险级别长度必须介于 0 和 2 之间")
	public String getRiskRank() {
		return riskRank;
	}

	public void setRiskRank(String riskRank) {
		this.riskRank = riskRank;
	}
	
	
	@Length(min=0, max=3, message="登记注册类型长度必须介于 0 和 3 之间")
	public String getRegiType() {
		return regiType;
	}

	public void setRegiType(String regiType) {
		this.regiType = regiType;
	}
	
	public Integer getEstaOrgaCond() {
		return estaOrgaCond;
	}

	public void setEstaOrgaCond(Integer estaOrgaCond) {
		this.estaOrgaCond = estaOrgaCond;
	}
	
	@Length(min=0, max=3, message="控股情况长度必须介于 0 和 3 之间")
	public String getHoldCase() {
		return holdCase;
	}

	public void setHoldCase(String holdCase) {
		this.holdCase = holdCase;
	}
	
	@Length(min=0, max=2, message="重点类型长度必须介于 0 和 2 之间")
	public String getCompImpoType() {
		return compImpoType;
	}

	public void setCompImpoType(String compImpoType) {
		this.compImpoType = compImpoType;
	}
	
	public Integer getEstaOrga() {
		return estaOrga;
	}

	public void setEstaOrga(Integer estaOrga) {
		this.estaOrga = estaOrga;
	}
	
	public Integer getPartyMem() {
		return partyMem;
	}

	public void setPartyMem(Integer partyMem) {
		this.partyMem = partyMem;
	}
	
	public Integer getLaborUnion() {
		return laborUnion;
	}

	public void setLaborUnion(Integer laborUnion) {
		this.laborUnion = laborUnion;
	}
	
	public Integer getLaborUnionNum() {
		return laborUnionNum;
	}

	public void setLaborUnionNum(Integer laborUnionNum) {
		this.laborUnionNum = laborUnionNum;
	}
	
	public Integer getYouthLeagOrga() {
		return youthLeagOrga;
	}

	public void setYouthLeagOrga(Integer youthLeagOrga) {
		this.youthLeagOrga = youthLeagOrga;
	}
	
	public Integer getYouthLeagOrgaNum() {
		return youthLeagOrgaNum;
	}

	public void setYouthLeagOrgaNum(Integer youthLeagOrgaNum) {
		this.youthLeagOrgaNum = youthLeagOrgaNum;
	}
	
	public Integer getWomenOrg() {
		return womenOrg;
	}

	public void setWomenOrg(Integer womenOrg) {
		this.womenOrg = womenOrg;
	}
	
	public Integer getWomenNum() {
		return womenNum;
	}

	public void setWomenNum(Integer womenNum) {
		this.womenNum = womenNum;
	}
	
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}
	
	public String getRegisteredFund() {
		return registeredFund;
	}

	public void setRegisteredFund(String registeredFund) {
		this.registeredFund = registeredFund;
	}
	
	@Length(min=0, max=32, message="经营范围长度必须介于 0 和 32之间")
	public String getManageScope() {
		return manageScope;
	}

	public void setManageScope(String manageScope) {
		this.manageScope = manageScope;
	}
	
	public List<String> getManageScopeList() {
		List<String> list = Lists.newArrayList();
		if (manageScope != null){
			for (String s : StringUtils.split(manageScope, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	public void setManageScopeList(List<String> list) {
		manageScope = ","+StringUtils.join(list, ",")+",";
	}

	
	@Length(min=0, max=2, message="企业类型长度必须介于 0 和 2 之间")
	public String getEnteType() {
		return enteType;
	}

	public void setEnteType(String enteType) {
		this.enteType = enteType;
	}
	
	@Length(min=0, max=50, message="服务品牌长度必须介于 0 和 50 之间")
	public String getServBrand() {
		return servBrand;
	}

	public void setServBrand(String servBrand) {
		this.servBrand = servBrand;
	}
	
	@Length(min=0, max=50, message="所属行业长度必须介于 0 和 50 之间")
	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}
	
	public Double getCompArea() {
		return compArea;
	}

	public void setCompArea(Double compArea) {
		this.compArea = compArea;
	}
	
	@Length(min=0, max=200, message="隐患情况长度必须介于 0 和 200 之间")
	public String getDangerCase() {
		return dangerCase;
	}

	public void setDangerCase(String dangerCase) {
		this.dangerCase = dangerCase;
	}
	
	public String getReformCase() {
		return reformCase;
	}

	public void setReformCase(String reformCase) {
		this.reformCase = reformCase;
	}
	
	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	public Integer getSurvCameNum() {
		return survCameNum;
	}

	public void setSurvCameNum(Integer survCameNum) {
		this.survCameNum = survCameNum;
	}
	
	public Integer getXRayNum() {
		return xRayNum;
	}

	public void setXRayNum(Integer xRayNum) {
		this.xRayNum = xRayNum;
	}
	
	public Integer getChecPack() {
		return checPack;
	}

	public void setChecPack(Integer checPack) {
		this.checPack = checPack;
	}
	
	public Integer getRealName() {
		return realName;
	}

	public void setRealName(Integer realName) {
		this.realName = realName;
	}
	
	public Integer getXRayChec() {
		return xRayChec;
	}

	public void setXRayChec(Integer xRayChec) {
		this.xRayChec = xRayChec;
	}
	
	public String getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(String areaMap) {
		this.areaMap = areaMap;
	}
	
	@Length(min=0, max=40, message="中心点长度必须介于 0 和 40 之间")
	public String getAreaPoint() {
		return areaPoint;
	}

	public void setAreaPoint(String areaPoint) {
		this.areaPoint = areaPoint;
	}
	
	@Length(min=0, max=255, message="图标长度必须介于 0 和 255 之间")
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Date getRegisterDate() {
		return registerDate;
	}
	
	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}
	
	public Date getBeginRegisterDate() {
		return beginRegisterDate;
	}
	
	public void setBeginRegisterDate(Date beginRegisterDate) {
		this.beginRegisterDate = beginRegisterDate;
	}
	
	public Date getEndRegisterDate() {
		return endRegisterDate;
	}
	
	public void setEndRegisterDate(Date endRegisterDate) {
		this.endRegisterDate = endRegisterDate;
	}
	
	@Length(min=0, max=100, message="冗余字段1长度必须介于 0 和 100 之间")
	public String getMore1() {
		return more1;
	}

	public void setMore1(String more1) {
		this.more1 = more1;
	}
	
	@Length(min=0, max=100, message="冗余字段2长度必须介于 0 和 100 之间")
	public String getMore2() {
		return more2;
	}

	public void setMore2(String more2) {
		this.more2 = more2;
	}
	
	@Length(min=0, max=100, message="冗余字段3长度必须介于 0 和 100 之间")
	public String getMore3() {
		return more3;
	}

	public void setMore3(String more3) {
		this.more3 = more3;
	}
	public String getConcExteLable() {
		return concExteLable;
	}
	public void setConcExteLable(String concExteLable) {
		this.concExteLable = concExteLable;
	}
	public String[] getIndustryList() {
		return industryList;
	}
	public void setIndustryList(String[] industryList) {
		this.industryList = industryList;
	}
	public int getIs_special() {
		return is_special;
	}
	public void setIs_special(int is_special) {
		this.is_special = is_special;
	}
	public int getIs_sdyq() {
		return is_sdyq;
	}
	public void setIs_sdyq(int is_sdyq) {
		this.is_sdyq = is_sdyq;
	}

	@Override
	public String toString() {
		return "CcmOrgNpse{" +
				"compId='" + compId + '\'' +
				", compName='" + compName + '\'' +
				", compType='" + compType + '\'' +
				", compAdd='" + compAdd + '\'' +
				", compTl='" + compTl + '\'' +
				", companyNum=" + companyNum +
				", legalReprCode='" + legalReprCode + '\'' +
				", legalReprId='" + legalReprId + '\'' +
				", legalReprName='" + legalReprName + '\'' +
				", legalReprTl='" + legalReprTl + '\'' +
				", secuName='" + secuName + '\'' +
				", secuPhone='" + secuPhone + '\'' +
				", entePrinName='" + entePrinName + '\'' +
				", dangComp=" + dangComp +
				", entePrincipalTl='" + entePrincipalTl + '\'' +
				", safeHazaType='" + safeHazaType + '\'' +
				", concExte='" + concExte + '\'' +
				", riskRank='" + riskRank + '\'' +
				", regiType='" + regiType + '\'' +
				", estaOrgaCond=" + estaOrgaCond +
				", holdCase='" + holdCase + '\'' +
				", compImpoType='" + compImpoType + '\'' +
				", estaOrga=" + estaOrga +
				", partyMem=" + partyMem +
				", laborUnion=" + laborUnion +
				", laborUnionNum=" + laborUnionNum +
				", youthLeagOrga=" + youthLeagOrga +
				", youthLeagOrgaNum=" + youthLeagOrgaNum +
				", womenOrg=" + womenOrg +
				", womenNum=" + womenNum +
				", area=" + area +
				", registeredFund='" + registeredFund + '\'' +
				", manageScope='" + manageScope + '\'' +
				", enteType='" + enteType + '\'' +
				", servBrand='" + servBrand + '\'' +
				", industry='" + industry + '\'' +
				", compArea=" + compArea +
				", dangerCase='" + dangerCase + '\'' +
				", reformCase='" + reformCase + '\'' +
				", images='" + images + '\'' +
				", survCameNum=" + survCameNum +
				", xRayNum=" + xRayNum +
				", checPack=" + checPack +
				", realName=" + realName +
				", xRayChec=" + xRayChec +
				", areaMap='" + areaMap + '\'' +
				", areaPoint='" + areaPoint + '\'' +
				", icon='" + icon + '\'' +
				", registerDate=" + registerDate +
				", beginRegisterDate=" + beginRegisterDate +
				", endRegisterDate=" + endRegisterDate +
				", more1='" + more1 + '\'' +
				", more2='" + more2 + '\'' +
				", more3='" + more3 + '\'' +
				", concExteLable='" + concExteLable + '\'' +
				", is_special=" + is_special +
				", is_sdyq=" + is_sdyq +
				", checkUser=" + checkUser +
				", industryList=" + Arrays.toString(industryList) +
				", count='" + count + '\'' +
				'}';
	}
}