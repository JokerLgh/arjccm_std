/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.house.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.common.utils.excel.annotation.ExcelField;
import com.arjjs.ccm.modules.sys.entity.Office;
import com.arjjs.ccm.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * 严重刑事犯罪Entity
 * @author wangyikai
 * @version 2018-09-27
 */
public class CcmSeriousCriminalOffense extends DataEntity<CcmSeriousCriminalOffense> {
	
	private static final long serialVersionUID = 1L;
	private String peopleId;		// 实有人口(id)
	private String suspicionType;		// 刑事类型
	private String discoverer;		// 发现人(告发人)
	private String discoverIdCards;		// 发现人身份证号
	private String discoverPhone;		// 发现人联系方式
	private String activityDescription;		// 参与嫌疑活动描述
	private String conomicState;		// 嫌疑人经济状况(极度贫穷，贫穷，温饱，小康，富有，极度富)
	private String dangerLevel;		// 危险程度(高、中、低)
	private String activityScope;		// 嫌疑人活动范围
	private String superviseSituation;		// 监管情况
	private String isCrimeHistory;		// 有无犯罪史(有、否)
	private String crimeHistory;		// 犯罪史
	private String discoveryWay;		// 发现途径(人口调查、特种行业和公共复杂场所治安管理、案件查处、群众举报、档案材料清理、情况信息通报、治安耳目提供、其他途径)
	private String superviseStatus;		// 监管状态(列管，撤管)
	private String more1;		// 冗余1
	private String more2;		// 冗余2
	
	private String dangerLevelLable;	//app接口使用
	//实有人口
	private String type;		// 人口类型
	private String name;        // 姓名
	private String censu;		// 籍贯
	private String sex;		// 性别
	private String ident;		// 公民身份号码
	private String telephone;	 // 联系方式
	
	private String domiciledetail;		// 户籍门（楼）详址
	private String residencedetail;		// 现住门（楼）详址
	private String images;		// 图片
	private Date birthday;	//出生日期
	private String comName; 	//app接口使用，所属社区
	private User checkUser;		// 拦截器中使用该用户进行权限拦截，App的rest接口使用
	
	
	
	
	public String getSuperviseSituation() {
		return superviseSituation;
	}

	public void setSuperviseSituation(String superviseSituation) {
		this.superviseSituation = superviseSituation;
	}

	public String getDangerLevelLable() {
		return dangerLevelLable;
	}

	public void setDangerLevelLable(String dangerLevelLable) {
		this.dangerLevelLable = dangerLevelLable;
	}
	@ExcelField(title="姓名", align=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
  	
	@ExcelField(title="监管状态", align=2, sort=17,dictType="house_supervise_status")
	public String getSuperviseStatus() {
		return superviseStatus;
	}

	public void setSuperviseStatus(String superviseStatus) {
		this.superviseStatus = superviseStatus;
	}
	@ExcelField(title="发现途径", align=2, sort=18,dictType="house_discovery_way")
	public String getDiscoveryWay() {
		return discoveryWay;
	}

	public void setDiscoveryWay(String discoveryWay) {
		this.discoveryWay = discoveryWay;
	}
	@ExcelField(title="籍贯", align=2, sort=3)
	public String getCensu() {
		return censu;
	}

	public void setCensu(String censu) {
		this.censu = censu;
	}
	@ExcelField(title="性别", align=2, sort=19,dictType="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}
	@ExcelField(title="公民身份号码", align=2, sort=20)
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}
	@ExcelField(title="联系方式", align=2, sort=21)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@ExcelField(title="户籍门（楼）详址", align=2, sort=4)
	public String getDomiciledetail() {
		return domiciledetail;
	}

	public void setDomiciledetail(String domiciledetail) {
		this.domiciledetail = domiciledetail;
	}
	@ExcelField(title="现住门（楼）详址", align=2, sort=5)
	public String getResidencedetail() {
		return residencedetail;
	}

	public void setResidencedetail(String residencedetail) {
		this.residencedetail = residencedetail;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public User getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}
	
	//@ExcelField(title="实有人口（id）", align=2, sort=23)
	@Length(min=0, max=64, message="实有人口（id）长度必须介于 0 和 64 之间")
	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}
	@ExcelField(title="刑事类型", align=2, sort=6 ,dictType="criminal_type")
	@Length(min=0, max=2, message="嫌疑类型长度必须介于 0 和 2 之间")
	public String getSuspicionType() {
		return suspicionType;
	}

	public void setSuspicionType(String suspicionType) {
		this.suspicionType = suspicionType;
	}
	
	@ExcelField(title="发现人（告发人）", align=2, sort=9 )
	@Length(min=0, max=128, message="发现人（告发人）长度必须介于 0 和 128 之间")
	public String getDiscoverer() {
		return discoverer;
	}

	public void setDiscoverer(String discoverer) {
		this.discoverer = discoverer;
	}
	@ExcelField(title="发现人身份证号", align=2, sort=10 )
	@Length(min=0, max=20, message="发现人身份证号长度必须介于 0 和 20 之间")
	public String getDiscoverIdCards() {
		return discoverIdCards;
	}

	public void setDiscoverIdCards(String discoverIdCards) {
		this.discoverIdCards = discoverIdCards;
	}
	@ExcelField(title="发现人联系方式", align=2, sort=11 )
	@Length(min=0, max=15, message="发现人联系方式长度必须介于 0 和 15 之间")
	public String getDiscoverPhone() {
		return discoverPhone;
	}

	public void setDiscoverPhone(String discoverPhone) {
		this.discoverPhone = discoverPhone;
	}
	@ExcelField(title="参与嫌疑活动描述", align=2, sort=12 )
	@Length(min=0, max=2048, message="参与嫌疑活动描述长度必须介于 0 和 2048 之间")
	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}
	@ExcelField(title="嫌疑人经济状况", align=2, sort=13, dictType="conomic_state_dict")
	@Length(min=0, max=2, message="嫌疑人经济状况(极度贫穷，贫穷，温饱，小康，富有，极度富)长度必须介于 0 和 2 之间")
	public String getConomicState() {
		return conomicState;
	}

	public void setConomicState(String conomicState) {
		this.conomicState = conomicState;
	}
	
	@ExcelField(title="危险程度", align=2, sort=22,dictType="danger_level_dict")
	public String getDangerLevel() {
		return dangerLevel;
	}

	public void setDangerLevel(String dangerLevel) {
		this.dangerLevel = dangerLevel;
	}
	@ExcelField(title="嫌疑人活动范围", align=2, sort=14)
	@Length(min=0, max=512, message="嫌疑人活动范围长度必须介于 0 和 512 之间")
	public String getActivityScope() {
		return activityScope;
	}

	public void setActivityScope(String activityScope) {
		this.activityScope = activityScope;
	}
	
	@ExcelField(title="人口类型", align=2, sort=2,dictType="sys_ccm_people")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	@ExcelField(title="有无犯罪史", align=2, sort=15,dictType="have_no")
	@Length(min=0, max=1, message="有无犯罪史（有、否）长度必须介于 0 和 1 之间")
	public String getIsCrimeHistory() {
		return isCrimeHistory;
	}

	public void setIsCrimeHistory(String isCrimeHistory) {
		this.isCrimeHistory = isCrimeHistory;
	}
	@ExcelField(title="犯罪史详情", align=2, sort=16)
	@Length(min=0, max=1024, message="犯罪史长度必须介于 0 和 1024 之间")
	public String getCrimeHistory() {
		return crimeHistory;
	}
  
	public void setCrimeHistory(String crimeHistory) {
		this.crimeHistory = crimeHistory;
	}
	
	@Length(min=0, max=255, message="冗余1长度必须介于 0 和 255 之间")
	public String getMore1() {
		return more1;
	}

	public void setMore1(String more1) {
		this.more1 = more1;
	}
	
	@Length(min=0, max=255, message="冗余2长度必须介于 0 和 255 之间")
	public String getMore2() {
		return more2;
	}

	public void setMore2(String more2) {
		this.more2 = more2;
	}

	public CcmSeriousCriminalOffense() {
		super();
	}

	public CcmSeriousCriminalOffense(String id){
		super(id);
	}

	
	
}