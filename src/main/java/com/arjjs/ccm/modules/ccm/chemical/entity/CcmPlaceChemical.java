/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.chemical.entity;

import com.arjjs.ccm.modules.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.modules.ccm.place.entity.CcmBasePlace;
import com.arjjs.ccm.modules.sys.entity.Area;

/**
 * 危化品经营Entity
 * 
 * @author ljd
 * @version 2019-04-29
 */
public class CcmPlaceChemical extends DataEntity<CcmPlaceChemical> {

	private static final long serialVersionUID = 1L;
	private CcmBasePlace ccmBasePlace;
	private String type; // 场所类型
	private String businessCertificateCode; // 经营与可证编号
	private String businessCertificateLevel; // 经营许可证等级（无证 甲种 乙种）
	private Date businessCertificateBegin; // 经营许可证有效开始日期
	private String businessScope; // 经营范围
	private String registeredAddress; // 注册地址
	private String storageAddress; // 仓储地址
	private Date businessCertificateEnd; // 经营许可证有效结束日期
	private String basePlaceId; // 场所id
	private Area area;
	public User checkUser;
	public List<String> getKeyPointList() {
		List<String> list = Lists.newArrayList();
		if (ccmBasePlace!=null&&ccmBasePlace.getKeyPoint() != null) {
			for (String s : StringUtils.split(ccmBasePlace.getKeyPoint(), ",")) {
				list.add(s);
			}
		}
		return list;
	}

	public void setKeyPointList(List<String> keyPointList) {
		ccmBasePlace.setKeyPoint("," + StringUtils.join(keyPointList, ",") + ",");
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getBasePlaceId() {
		return basePlaceId;
	}

	public void setBasePlaceId(String basePlaceId) {
		this.basePlaceId = basePlaceId;
	}

	// 基础场所属性
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
	private String placeArea;
	private String workerNumber; // 工作人员数量
	private String placePicture;
	private String governingBodyName;
	private String administrativeDivision; // 行政区划
	public CcmBasePlace getCcmBasePlace() {
		return ccmBasePlace;
	}

	public void setCcmBasePlace(CcmBasePlace ccmBasePlace) {
		this.ccmBasePlace = ccmBasePlace;
	}

	@Length(min = 1, max = 255, message = "场所名称长度必须介于 1 和 255 之间")
	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	@Length(min = 0, max = 255, message = "关联组织机构长度必须介于 0 和 255 之间")
	public String getRelevanceOrg() {
		return relevanceOrg;
	}

	public void setRelevanceOrg(String relevanceOrg) {
		this.relevanceOrg = relevanceOrg;
	}

	@Length(min = 0, max = 255, message = "负责人姓名长度必须介于 0 和 255 之间")
	public String getLeaderName() {
		return leaderName;
	}

	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	@Length(min = 0, max = 255, message = "负责人身份证号码长度必须介于 0 和 255 之间")
	public String getLeaderIdCard() {
		return leaderIdCard;
	}

	public void setLeaderIdCard(String leaderIdCard) {
		this.leaderIdCard = leaderIdCard;
	}

	@Length(min = 0, max = 255, message = "负责人联系方式长度必须介于 0 和 255 之间")
	public String getLeaderContact() {
		return leaderContact;
	}

	public void setLeaderContact(String leaderContact) {
		this.leaderContact = leaderContact;
	}

	public CcmPlaceChemical() {
		super();
	}

	public CcmPlaceChemical(String id) {
		super(id);
	}

	@Length(min = 1, max = 2, message = "场所类型长度必须介于 1 和 2 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Length(min = 0, max = 255, message = "经营与可证编号长度必须介于 0 和 255 之间")
	public String getBusinessCertificateCode() {
		return businessCertificateCode;
	}

	public void setBusinessCertificateCode(String businessCertificateCode) {
		this.businessCertificateCode = businessCertificateCode;
	}

	@Length(min = 0, max = 255, message = "经营许可证等级（无证  甲种   乙种）长度必须介于 0 和 255 之间")
	public String getBusinessCertificateLevel() {
		return businessCertificateLevel;
	}

	public void setBusinessCertificateLevel(String businessCertificateLevel) {
		this.businessCertificateLevel = businessCertificateLevel;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBusinessCertificateBegin() {
		return businessCertificateBegin;
	}

	public void setBusinessCertificateBegin(Date businessCertificateBegin) {
		this.businessCertificateBegin = businessCertificateBegin;
	}

	@Length(min = 0, max = 255, message = "经营范围长度必须介于 0 和 255 之间")
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}

	@Length(min = 0, max = 255, message = "注册地址长度必须介于 0 和 255 之间")
	public String getRegisteredAddress() {
		return registeredAddress;
	}

	public void setRegisteredAddress(String registeredAddress) {
		this.registeredAddress = registeredAddress;
	}

	@Length(min = 0, max = 255, message = "仓储地址长度必须介于 0 和 255 之间")
	public String getStorageAddress() {
		return storageAddress;
	}

	public void setStorageAddress(String storageAddress) {
		this.storageAddress = storageAddress;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBusinessCertificateEnd() {
		return businessCertificateEnd;
	}

	public void setBusinessCertificateEnd(Date businessCertificateEnd) {
		this.businessCertificateEnd = businessCertificateEnd;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
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

	public String getPlaceArea() {
		return placeArea;
	}

	public void setPlaceArea(String placeArea) {
		this.placeArea = placeArea;
	}

	public String getWorkerNumber() {
		return workerNumber;
	}

	public void setWorkerNumber(String workerNumber) {
		this.workerNumber = workerNumber;
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