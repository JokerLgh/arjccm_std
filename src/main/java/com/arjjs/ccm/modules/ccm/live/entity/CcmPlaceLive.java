/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.live.entity;

import java.util.Date;
import java.util.List;

import com.arjjs.ccm.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.google.common.collect.Lists;

/**
 * 生活配套场所表Entity
 * 
 * @author lgh
 * @version 2019-04-23
 */
public class CcmPlaceLive extends DataEntity<CcmPlaceLive> {

	private CcmBasePlace ccmBasePlace;
	private static final long serialVersionUID = 1L;
	private String type; // 场所类型（01 农贸市场 02 公共厕所）
	private String certification; // 证照情况
	private String businessScope; // 经营范围
	private String openProperty; // 开放属性
	private String isFree; // 是否免费
	private int sanitationmanNumber; // 环卫工人数量
	private String basePlaceId; // 场所id
	private Area area;
	private Area userArea;

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public Area getUserArea() {
		return userArea;
	}

	public void setUserArea(Area userArea) {
		this.userArea = userArea;
	}

	// 场所基础信息（部门）
	private String placeType;
	private String placeName; // 场所名称
	private String relevanceOrg; // 关联组织机构
	private String leaderName; // 负责人姓名
	private String address; // 地址
	private String leaderContact; // 负责人联系方式
	private String leaderIdCard;
	private String placeUse;
	private Date createTime;
	private String keyPoint;
	private String workerNumber;
	private String placeArea;
	private String placePicture;
	private String governingBodyName;
	private String administrativeDivision; // 行政区划
	private User checkUser;
	public List<String> getCertificationList() {
		List<String> list = Lists.newArrayList();
		if (certification != null) {
			for (String s : StringUtils.split(certification, ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public void setCertificationList(List<String> certificationList) {
		certification = "," + StringUtils.join(certificationList, ",") + ",";
	}

	public List<String> getKeyPointList() {
		List<String> list = Lists.newArrayList();
		if (ccmBasePlace!= null&&ccmBasePlace.getKeyPoint() != null) {
			for (String s : StringUtils.split(ccmBasePlace.getKeyPoint(), ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public void setKeyPointList(List<String> keyPointList) {
		ccmBasePlace.setKeyPoint("," + StringUtils.join(keyPointList, ",") + ",");
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getRelevanceOrg() {
		return relevanceOrg;
	}

	public void setRelevanceOrg(String relevanceOrg) {
		this.relevanceOrg = relevanceOrg;
	}

	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getLeaderContact() {
		return leaderContact;
	}

	public void setLeaderContact(String leaderContact) {
		this.leaderContact = leaderContact;
	}

	public CcmPlaceLive() {
		super();
	}

	public CcmPlaceLive(String id) {
		super(id);
	}

	@Length(min = 1, max = 2, message = "场所类型（01 农贸市场 02 公共厕所）长度必须介于 1 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 0, max = 255, message = "证照情况长度必须介于 0 和 255 之间")
	public String getCertification() {
		return certification;
	}

	public void setCertification(String certification) {
		this.certification = certification;
	}

	@Length(min = 0, max = 512, message = "经营范围长度必须介于 0 和 512 之间")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Length(min = 0, max = 255, message = "开放属性长度必须介于 0 和 255 之间")
	public String getOpenProperty() {
		return openProperty;
	}

	public void setOpenProperty(String openProperty) {
		this.openProperty = openProperty;
	}

	@Length(min = 0, max = 255, message = "是否免费长度必须介于 0 和 255 之间")
	public String getIsFree() {
		return isFree;
	}

	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}

	public int getSanitationmanNumber() {
		return sanitationmanNumber;
	}

	public void setSanitationmanNumber(int sanitationmanNumber) {
		this.sanitationmanNumber = sanitationmanNumber;
	}

	@Length(min = 0, max = 64, message = "场所id长度必须介于 0 和 64 之间")
	public String getBasePlaceId() {
		return basePlaceId;
	}

	public void setBasePlaceId(String basePlaceId) {
		this.basePlaceId = basePlaceId;
	}

	public CcmBasePlace getCcmBasePlace() {
		return ccmBasePlace;
	}

	public void setCcmBasePlace(CcmBasePlace ccmBasePlace) {
		this.ccmBasePlace = ccmBasePlace;
	}

	public String getLeaderIdCard() {
		return leaderIdCard;
	}

	public void setLeaderIdCard(String leaderIdCard) {
		this.leaderIdCard = leaderIdCard;
	}

	public String getPlaceUse() {
		return placeUse;
	}

	public void setPlaceUse(String placeUse) {
		this.placeUse = placeUse;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getKeyPoint() {
		return keyPoint;
	}

	public void setKeyPoint(String keyPoint) {
		this.keyPoint = keyPoint;
	}

	public String getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(String workerNumber) {
		this.workerNumber = workerNumber;
	}

	public String getPlaceArea() {
		return placeArea;
	}

	public void setPlaceArea(String placeArea) {
		this.placeArea = placeArea;
	}

	public String getPlacePicture() {
		return placePicture;
	}

	public void setPlacePicture(String placePicture) {
		this.placePicture = placePicture;
	}

	public String getGoverningBodyName() {
		return governingBodyName;
	}

	public void setGoverningBodyName(String governingBodyName) {
		this.governingBodyName = governingBodyName;
	}

	public String getAdministrativeDivision() {
		return administrativeDivision;
	}

	public void setAdministrativeDivision(String administrativeDivision) {
		this.administrativeDivision = administrativeDivision;
	}

	public String getPlaceType() {
		return placeType;
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public User getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}
}