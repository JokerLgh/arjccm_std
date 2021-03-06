/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.pop.entity;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.common.utils.excel.annotation.ExcelField;
import com.arjjs.ccm.modules.ccm.house.entity.CcmHouseBuildmanage;
import com.arjjs.ccm.modules.sys.entity.Area;
import com.arjjs.ccm.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 实有人口Entity
 * @author liang
 * @version 2018-01-04
 */
public class CcmPeople extends DataEntity<CcmPeople> {
	
	private static final long serialVersionUID = 1L;
	private String type;		// 人口类型
	private String name;		// 姓名
	private String usedname;		// 曾用名
	private String sex;		// 性别
	private Date birthday;		// 出生日期
	private String nation;		// 民族
	private String censu;		// 籍贯
	private String marriage;		// 婚姻状况
	private String politics;		// 政治面貌
	private String education;		// 学历
	private String belief;		// 宗教信仰
	private String profType;		// 职业类别
	private String profession;		// 职业
	private String isPublicServants;		// 是否公职人员
	private String officeName;		// 所在单位/学校
	private String servPlace;		// 服务处所
	private String domicile;		// 户籍地
	private String domiciledetail;		// 户籍门（楼）详址
	private String residence;		// 现住地
	private String residencedetail;		// 现住门（楼）详址
	private Area areaComId;		// 所属社区ID
	private Area areaGridId;		// 所属网格ID
	private CcmHouseBuildmanage buildId;		// 所属楼栋ID
	private CcmPopTenant roomId;   //所属房屋ID
	private Integer isBehind;		// 是否留守
	private Integer isRelease;		// 是否安置帮教
	private Integer isRectification;		// 是否社区矫正
	private Integer isAids;		// 是否艾滋病患者
	private Integer isPsychogeny;		// 是否肇事肇祸等严重精神障碍患者
	private Integer isKym;		// 是否重点青少年
	private Integer isDrugs;		// 是否吸毒
	private Integer  isHarmNational;//是否危害国家安全
	private Integer  isDeliberatelyIllegal;//是否故意犯法释放
	private Integer  isCriminalOffense;//是否严重犯罪嫌疑        
	private Integer isEscape;       //是否在逃
	private Integer isVisit;		// 是否重点上访
	private Integer isHeresy;		// 是否涉教
	private Integer isDangerous;		// 是否危险品从业人员
	private Integer isMore1;		// 是否特殊1
	private Integer isMore2;		// 是否特殊2
	private Integer isDispute;		//是否存在矛盾纠纷
	private Integer typeSpec;		// 特殊人群分类
	private String specialCareType;		// 特殊关怀类型
	private String images;		// 图片
	private String ident;		// 公民身份号码
	private String telephone;		// 联系方式
	private String uniformlogo;		// 人户一致标识
	private String account;		// 户号
	private String accountidentity;		// 户主公民身份号码
	private String accountname;		// 户主姓名
	private String accountrelation;		// 与户主关系
	private String accounttelephone;		// 户主联系方式
	private String personType;		// 户籍状态
	private Date personTime;		// 户籍变动时间
	private String personReason;		// 迁入、迁出原因
	private String flowRea;		// 流入原因
	private String accrType;		// 办证类型
	private String certNum;		// 证件号码（流入）
	private Date recoDate;		// 登记日期
	private Date dueDate;		// 证件到期日期
	private String domiType;		// 住所类型
	private Integer focuPers;		// 是否重点关注人员
	private Date time;		// 时间
	private String cause;		// 原因
	private String explainelse;		// 说明
	private String esurname;		// 外文姓
	private String ename;		// 外文名
	private String nationality;		// 国籍（地区)
	private String idenCode;		// 证件代码
	private String idenNum;		// 证件号码（境外）
	private Date idenDate;		// 证件有效期
	private String purpose;		// 来华目的
	private Date arriDate;		// 抵达日期
	private Date departDate;		// 预计离开日期
	private String more1;		// 冗余字段1
	private String more2;		// 冗余字段2
	private String more3;		// 冗余字段3
	private String more4;		// 冗余字段4
	private String more5;		// 冗余字段5
	private Date beginBirthday;		// 开始 出生日期
	private Date endBirthday;		// 结束 出生日期
	private String[] specialCareTypes;		// 特殊关怀类型数组
	private Integer age;		// 年龄
	private String[] listLimite;		// GET数组

	private String isPermanent;		// 是否常住
	private String unsettleReason;		// 未落户原因
	private Date unsettleDate;		// 未落户时间
	private String unsettleCardType;		// 持证种类名称
	private String unsettleCardNumber;		// 持证编号
	
	private String roomIdString;		//所属房屋
	private String tableName;//表名称
	private User checkUser;		// 拦截器中使用该用户进行权限拦截，App的rest接口使用
	private String sexLable;		//app接口使用
	private String currentPage;
	private Integer pageSize;
	private int startIndex;
	private String count;
	private Area userArea;
	
	private String resultName;
	private int resultNum;
	private int peopleAge;
	
	private String areaPoint;//所在楼栋中心点
	private String areaMap;//所在楼栋区域图
	private String buildName;		// 所属楼栋名称
	private String roomName;   //所属房屋编号名称
	private String unitCategory;   //所属房屋编号名称
	private String elemNum;   //pjq人口导入时使用， 楼栋单元总数 ，对应CcmHouseBuildmanage中的elemNum
	private String buildDoorNum;   //pjq人口导入时使用， 所属单元，对应CcmPopTenant中的buildDoorNum
	
	private String cpptype;
	
	private String isControl;   //布控标识

	private String mqTitle;	//上传至mq时用于显示的标题
	
	private int timeInterval;
	
	private String practitioners;  //特种行业从业类型

	private String che;	//是否显示更多查询条件
	
	private Integer pilesNum;  //楼栋层数
	private Integer floorNum;  //房屋所属层数
	
	@ExcelField(title="楼栋总层数", align=2, sort=22)
	public Integer getPilesNum() {
		return pilesNum;
	}

	public void setPilesNum(Integer pilesNum) {
		this.pilesNum = pilesNum;
	}
	
	@ExcelField(title="房屋楼层", align=2, sort=23)
	public Integer getFloorNum() {
		return floorNum;
	}

	public void setFloorNum(Integer floorNum) {
		this.floorNum = floorNum;
	}

	public String getPractitioners() {
		return practitioners;
	}

	public void setPractitioners(String practitioners) {
		this.practitioners = practitioners;
	}

	public int getTimeInterval() {
		return timeInterval;
	}

	public void setTimeInterval(int timeInterval) {
		this.timeInterval = timeInterval;
	}

	public String getResultName() {
		return resultName;
	}

	public void setResultName(String resultName) {
		this.resultName = resultName;
	}

	public int getResultNum() {
		return resultNum;
	}

	public void setResultNum(int resultNum) {
		this.resultNum = resultNum;
	}

	public String getCpptype() {
		return cpptype;
	}

	public void setCpptype(String cpptype) {
		this.cpptype = cpptype;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Area getUserArea() {
		return userArea;
	}

	public void setUserArea(Area userArea) {
		this.userArea = userArea;
	}

	public int getStartIndex() {
		if(StringUtils.isNotBlank(currentPage) && pageSize != null) {
			startIndex = (Integer.valueOf(currentPage) - 1) * pageSize;
		}
		return startIndex;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public String getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(String currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String[] getListLimite() {
		return listLimite;
	}

	public void setListLimite(String[] listLimite) {
		this.listLimite = listLimite;
	}

	public User getCheckUser() {
		return checkUser;
	}
    
	public Integer getIsHarmNational() {
		return isHarmNational;
	}
	
	public Integer getIsCriminalOffense() {
		return isCriminalOffense;
	}

	public void setIsCriminalOffense(Integer isCriminalOffense) {
		this.isCriminalOffense = isCriminalOffense;
	}

	public void setIsHarmNational(Integer isHarmNational) {
		this.isHarmNational = isHarmNational;
	}

	public Integer getIsDeliberatelyIllegal() {
		return isDeliberatelyIllegal;
	}

	public void setIsDeliberatelyIllegal(Integer isDeliberatelyIllegal) {
		this.isDeliberatelyIllegal = isDeliberatelyIllegal;
	}

	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}
	

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String[] getSpecialCareTypes() {
		return specialCareTypes;
	}

	public void setSpecialCareTypes(String[] specialCareTypes) {
		this.specialCareTypes = specialCareTypes;
	}

	public List<String> getSpecialCareTypeList() {
		List<String> list = Lists.newArrayList();
		if (specialCareType != null){
			for (String s : StringUtils.split(specialCareType, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	public void setSpecialCareTypeList(List<String> list) {
		specialCareType = ","+StringUtils.join(list, ",")+",";
	}


	public CcmPeople() {
		super();
	}

	public CcmPeople(String id){
		super(id);
	}
	
	@ExcelField(title="姓名", align=1,  sort=1)
	@Length(min=0, max=50, message="姓名长度必须介于 0 和 50 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	@ExcelField(title="人口类型", align=2, sort=2,dictType="sys_ccm_people")
	@Length(min=0, max=10, message="人口类型长度必须介于 0 和 10 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

//	@ExcelField(title="曾用名", align=2, sort=3)
	@Length(min=0, max=50, message="曾用名长度必须介于 0 和 50 之间")
	public String getUsedname() {
		return usedname;
	}

	public void setUsedname(String usedname) {
		this.usedname = usedname;
	}
	
	@ExcelField(title="性别", align=2, sort=3,dictType="sex")
	@Length(min=0, max=6, message="性别长度必须介于 0 和 6 之间")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title="出生日期", align=2, sort=4)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(title="公民身份号码", align=2, sort=5)
	@Length(min=0, max=18, message="公民身份号码长度必须介于 0 和 18 之间")
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ExcelField(title="联系方式", align=2, sort=6)
	@Length(min=0, max=50, message="联系方式长度必须介于 0 和 50 之间")
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@ExcelField(title="民族", align=2, sort=7,dictType="sys_volk")
	@Length(min=0, max=20, message="民族长度必须介于 0 和 20 之间")
	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	@ExcelField(title="籍贯", align=2, sort=8)
	@Length(min=0, max=36, message="籍贯长度必须介于 0 和36 之间")
	public String getCensu() {
		return censu;
	}

	public void setCensu(String censu) {
		this.censu = censu;
	}

	@ExcelField(title="婚姻状况", align=2, sort=9,dictType="sys_ccm_mari_stat")
	@Length(min=0, max=12, message="婚姻状况长度必须介于 0 和 12 之间")
	public String getMarriage() {
		return marriage;
	}

	public void setMarriage(String marriage) {
		this.marriage = marriage;
	}

	@ExcelField(title="政治面貌", align=2, sort=10,dictType="sys_ccm_poli_stat")
	@Length(min=0, max=12, message="政治面貌长度必须介于 0 和 12 之间")
	public String getPolitics() {
		return politics;
	}

	public void setPolitics(String politics) {
		this.politics = politics;
	}

	@ExcelField(title="学历", align=2, sort=11,dictType="sys_ccm_degree")
	@Length(min=0, max=22, message="学历长度必须介于 0 和 22 之间")
	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	@ExcelField(title="宗教信仰", align=2, sort=12,dictType="sys_ccm_belief")
	@Length(min=0, max=22, message="宗教信仰长度必须介于 0 和 22 之间")
	public String getBelief() {
		return belief;
	}

	public void setBelief(String belief) {
		this.belief = belief;
	}

//	@ExcelField(title="职业类别", align=2, sort=12)
	@Length(min=0, max=16, message="职业类别长度必须介于 0 和 16 之间")
	public String getProfType() {
		return profType;
	}

	public void setProfType(String profType) {
		this.profType = profType;
	}

//	@ExcelField(title="职业", align=2, sort=13)
	@Length(min=0, max=30, message="职业长度必须介于 0 和 30 之间")
	public String getProfession() {
		return profession;
	}

	public void setProfession(String profession) {
		this.profession = profession;
	}

	@ExcelField(title="服务处所", align=2, sort=13)
	@Length(min=0, max=100, message="服务处所长度必须介于 0 和 100 之间")
	public String getServPlace() {
		return servPlace;
	}

	public void setServPlace(String servPlace) {
		this.servPlace = servPlace;
	}

	@ExcelField(title="户籍地", align=2, sort=26)
	@Length(min=0, max=6, message="户籍地长度必须介于 0 和 6 之间")
	public String getDomicile() {
		return domicile;
	}

	public void setDomicile(String domicile) {
		this.domicile = domicile;
	}

	@ExcelField(title="户籍门（楼）详址", align=2, sort=14)
	@Length(min=0, max=80, message="户籍门（楼）详址长度必须介于 0 和 80 之间")
	public String getDomiciledetail() {
		return domiciledetail;
	}

	public void setDomiciledetail(String domiciledetail) {
		this.domiciledetail = domiciledetail;
	}

//	@ExcelField(title="现住地", align=2, sort=16)
	@Length(min=0, max=16, message="现住地长度必须介于 0 和 16 之间")
	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}

	@ExcelField(title="现住门（楼）详址", align=2, sort=15)
	@Length(min=0, max=80, message="现住门（楼）详址长度必须介于 0 和 80 之间")
	public String getResidencedetail() {
		return residencedetail;
	}

	public void setResidencedetail(String residencedetail) {
		this.residencedetail = residencedetail;
	}

	@ExcelField(title="所属社区", align=2, sort=17)
	public Area getAreaComId() {
		return areaComId;
	}

	public void setAreaComId(Area areaComId) {
		this.areaComId = areaComId;
	}

	@ExcelField(title="所属网格", align=2, sort=18)
	public Area getAreaGridId() {
		return areaGridId;
	}

	public void setAreaGridId(Area areaGridId) {
		this.areaGridId = areaGridId;
	}

//	@ExcelField(title="所属楼栋", value="buildId.buildname", align=2, sort=18)
	public CcmHouseBuildmanage getBuildId() {
		return buildId;
	}

	public void setBuildId(CcmHouseBuildmanage buildId) {
		this.buildId = buildId;
	}
	
	@ExcelField(title="所属楼栋", align=2, sort=19)
	public String getBuildName() {
		return buildName;
	}

	public void setBuildName(String buildName) {
		this.buildName = buildName;
	}

	@ExcelField(title="楼栋单元总数", align=2, sort=20)
	public String getElemNum() {
		return elemNum;
	}

	public void setElemNum(String elemNum) {
		this.elemNum = elemNum;
	}

	@ExcelField(title="所属单元", align=2, sort=21)
	public String getBuildDoorNum() {
		return buildDoorNum;
	}

	public void setBuildDoorNum(String buildDoorNum) {
		this.buildDoorNum = buildDoorNum;
	}
	
	@ExcelField(title="所属房屋", align=2, sort=24)
	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}


//	@ExcelField(title="是否留守", align=2, sort=20,dictType="yes_no")
	public Integer getIsBehind() {
		return isBehind;
	}

	public void setIsBehind(Integer isBehind) {
		this.isBehind = isBehind;
	}

//	@ExcelField(title="是否安置帮教", align=2, sort=21,dictType="yes_no")
	public Integer getIsRelease() {
		return isRelease;
	}

	public void setIsRelease(Integer isRelease) {
		this.isRelease = isRelease;
	}

//	@ExcelField(title="是否社区矫正", align=2, sort=22,dictType="yes_no")
	public Integer getIsRectification() {
		return isRectification;
	}

	public void setIsRectification(Integer isRectification) {
		this.isRectification = isRectification;
	}

//	@ExcelField(title="是否艾滋病患者", align=2, sort=23,dictType="yes_no")
	public Integer getIsAids() {
		return isAids;
	}

	public void setIsAids(Integer isAids) {
		this.isAids = isAids;
	}

//	@ExcelField(title="是否肇事肇祸等严重精神障碍患者", align=2, sort=24,dictType="yes_no")
	public Integer getIsPsychogeny() {
		return isPsychogeny;
	}

	public void setIsPsychogeny(Integer isPsychogeny) {
		this.isPsychogeny = isPsychogeny;
	}

//	@ExcelField(title="是否重点青少年", align=2, sort=25,dictType="yes_no")
	public Integer getIsKym() {
		return isKym;
	}

	public void setIsKym(Integer isKym) {
		this.isKym = isKym;
	}

//	@ExcelField(title="是否吸毒", align=2, sort=26,dictType="yes_no")
	public Integer getIsDrugs() {
		return isDrugs;
	}
	
	public void setIsDrugs(Integer isDrugs) {
		this.isDrugs = isDrugs;
	}

//	@ExcelField(title="是否重点上访", align=2, sort=27,dictType="yes_no")
	public Integer getIsVisit() {
		return isVisit;
	}

	public void setIsVisit(Integer isVisit) {
		this.isVisit = isVisit;
	}
//	@ExcelField(title="是否危险品", align=2, sort=28,dictType="yes_no")
	public Integer getIsDangerous() {
		return isDangerous;
	}

	public void setIsDangerous(Integer isDangerous) {
		this.isDangerous = isDangerous;
	}
//	@ExcelField(title="是否涉教", align=2, sort=29,dictType="yes_no")
	public Integer getIsHeresy() {
		return isHeresy;
	}

	public void setIsHeresy(Integer isHeresy) {
		this.isHeresy = isHeresy;
	}
//	@ExcelField(title="是否特殊1", align=2, sort=30,dictType="yes_no")
	public Integer getIsMore1() {
		return isMore1;
	}

	public void setIsMore1(Integer isMore1) {
		this.isMore1 = isMore1;
	}

//	@ExcelField(title="是否特殊2", align=2, sort=31,dictType="yes_no")
	public Integer getIsMore2() {
		return isMore2;
	}

	public void setIsMore2(Integer isMore2) {
		this.isMore2 = isMore2;
	}

//	@ExcelField(title="特殊人群分类", align=2, sort=32)
	public Integer getTypeSpec() {
		return typeSpec;
	}

	public void setTypeSpec(Integer typeSpec) {
		this.typeSpec = typeSpec;
	}

	@Length(min=0, max=255, message="图片长度必须介于 0 和 255 之间")
	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}


//	@ExcelField(title="人户一致标识", align=2, sort=35,dictType="ccm_huh_cst")
	@Length(min=0, max=2, message="人户一致标识长度必须介于 0 和 2 之间")
	public String getUniformlogo() {
		return uniformlogo;
	}

	public void setUniformlogo(String uniformlogo) {
		this.uniformlogo = uniformlogo;
	}

//	@ExcelField(title="所属房屋", align=2, sort=19)
	public String getRoomIdString() {
		return roomIdString;
	}

	public void setRoomIdString(String roomIdString) {
		this.roomIdString = roomIdString;
	}
	
	@ExcelField(title="户号", align=2, sort=25)
	@Length(min=0, max=18, message="户号长度必须介于 0 和 9 之间")
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@ExcelField(title="所在楼栋中心点", align=2, sort=28)
	public String getAreaPoint() {
		return areaPoint;
	}

	public void setAreaPoint(String areaPoint) {
		this.areaPoint = areaPoint;
	}

	public String getAreaMap() {
		return areaMap;
	}

	public void setAreaMap(String areaMap) {
		this.areaMap = areaMap;
	}

	@ExcelField(title="户主公民身份号码", align=2, sort=27)
	@Length(min=0, max=18, message="户主公民身份号码长度必须介于 0 和 18 之间")
	public String getAccountidentity() {
		return accountidentity;
	}

	public void setAccountidentity(String accountidentity) {
		this.accountidentity = accountidentity;
	}

//	@ExcelField(title="户主姓名", align=2, sort=38)
	@Length(min=0, max=50, message="户主姓名长度必须介于 0 和 50 之间")
	public String getAccountname() {
		return accountname;
	}

	public void setAccountname(String accountname) {
		this.accountname = accountname;
	}

//	@ExcelField(title="与户主关系", align=2, sort=39,dictType="sys_ccm_fami_ties")
	@Length(min=0, max=2, message="与户主关系长度必须介于 0 和 2 之间")
	public String getAccountrelation() {
		return accountrelation;
	}

	public void setAccountrelation(String accountrelation) {
		this.accountrelation = accountrelation;
	}

//	@ExcelField(title="户主联系方式", align=2, sort=40)
	@Length(min=0, max=50, message="户主联系方式长度必须介于 0 和 50 之间")
	public String getAccounttelephone() {
		return accounttelephone;
	}

	public void setAccounttelephone(String accounttelephone) {
		this.accounttelephone = accounttelephone;
	}

//	@ExcelField(title="户籍状态", align=2, sort=41,dictType="ccm_people_person_type")
	@Length(min=0, max=2, message="户籍状态长度必须介于 0 和 2 之间")
	public String getPersonType() {
		return personType;
	}

	public void setPersonType(String personType) {
		this.personType = personType;
	}

//	@ExcelField(title="户籍变动时间", align=2, sort=42)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getPersonTime() {
		return personTime;
	}

	public void setPersonTime(Date personTime) {
		this.personTime = personTime;
	}

//	@ExcelField(title="迁入、迁出原因", align=2, sort=43)
	@Length(min=0, max=1000, message="迁入、迁出原因长度必须介于 0 和 1000 之间")
	public String getPersonReason() {
		return personReason;
	}

	public void setPersonReason(String personReason) {
		this.personReason = personReason;
	}

//	@ExcelField(title="流入原因", align=2, sort=44,dictType="ccm_flow_res")
	@Length(min=0, max=2, message="流入原因长度必须介于 0 和 2 之间")
	public String getFlowRea() {
		return flowRea;
	}

	public void setFlowRea(String flowRea) {
		this.flowRea = flowRea;
	}

//	@ExcelField(title="办证类型", align=2, sort=45,dictType="ccm_acc_type")
	@Length(min=0, max=2, message="办证类型长度必须介于 0 和 2 之间")
	public String getAccrType() {
		return accrType;
	}

	public void setAccrType(String accrType) {
		this.accrType = accrType;
	}

//	@ExcelField(title="证件号码（流入）", align=2, sort=46)
	@Length(min=0, max=22, message="证件号码（流入）长度必须介于 0 和 22 之间")
	public String getCertNum() {
		return certNum;
	}

	public void setCertNum(String certNum) {
		this.certNum = certNum;
	}

//	@ExcelField(title="登记日期", align=2, sort=47)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRecoDate() {
		return recoDate;
	}

	public void setRecoDate(Date recoDate) {
		this.recoDate = recoDate;
	}

//	@ExcelField(title="证件到期日期", align=2, sort=48)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

//	@ExcelField(title="住所类型", align=2, sort=49,dictType="ccm_dom_type")
	@Length(min=0, max=2, message="住所类型长度必须介于 0 和 2 之间")
	public String getDomiType() {
		return domiType;
	}

	public void setDomiType(String domiType) {
		this.domiType = domiType;
	}

//	@ExcelField(title="是否重点关注人员", align=2, sort=50,dictType="yes_no")
	public Integer getFocuPers() {
		return focuPers;
	}

	public void setFocuPers(Integer focuPers) {
		this.focuPers = focuPers;
	}

//	@ExcelField(title="时间", align=2, sort=52)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

//	@ExcelField(title="原因", align=2, sort=52)
	@Length(min=0, max=18, message="原因长度必须介于 0 和 18 之间")
	public String getCause() {
		return cause;
	}

	public void setCause(String cause) {
		this.cause = cause;
	}

//	@ExcelField(title="说明", align=2, sort=53)
	@Length(min=0, max=1024, message="说明长度必须介于 0 和 1024 之间")
	public String getExplainelse() {
		return explainelse;
	}

	public void setExplainelse(String explainelse) {
		this.explainelse = explainelse;
	}

//	@ExcelField(title="外文姓", align=2, sort=54)
	@Length(min=0, max=40, message="外文姓长度必须介于 0 和 40 之间")
	public String getEsurname() {
		return esurname;
	}

	public void setEsurname(String esurname) {
		this.esurname = esurname;
	}

//	@ExcelField(title="外文名", align=2, sort=55)
	@Length(min=0, max=40, message="外文名长度必须介于 0 和 40 之间")
	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

//	@ExcelField(title="国籍（地区)", align=2, sort=56)
	@Length(min=0, max=3, message="国籍（地区)长度必须介于 0 和 3 之间")
	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

//	@ExcelField(title="证件代码", align=2, sort=57)
	@Length(min=0, max=3, message="证件代码长度必须介于 0 和 3 之间")
	public String getIdenCode() {
		return idenCode;
	}

	public void setIdenCode(String idenCode) {
		this.idenCode = idenCode;
	}

//	@ExcelField(title="证件号码（境外）", align=2, sort=58)
	@Length(min=0, max=30, message="证件号码（境外）长度必须介于 0 和 30 之间")
	public String getIdenNum() {
		return idenNum;
	}

	public void setIdenNum(String idenNum) {
		this.idenNum = idenNum;
	}

//	@ExcelField(title="证件有效期", align=2, sort=59)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getIdenDate() {
		return idenDate;
	}

	public void setIdenDate(Date idenDate) {
		this.idenDate = idenDate;
	}

//	@ExcelField(title="来华目的", align=2, sort=60,dictType="ccm_cn_aim")
	@Length(min=0, max=2, message="来华目的长度必须介于 0 和 2 之间")
	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

//	@ExcelField(title="抵达日期", align=2, sort=61)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getArriDate() {
		return arriDate;
	}

	public void setArriDate(Date arriDate) {
		this.arriDate = arriDate;
	}

//	@ExcelField(title="预计离开日期", align=2, sort=62)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getDepartDate() {
		return departDate;
	}

	public void setDepartDate(Date departDate) {
		this.departDate = departDate;
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
	
	@Length(min=0, max=100, message="冗余字段4长度必须介于 0 和 100 之间")
	public String getMore4() {
		return more4;
	}

	public void setMore4(String more4) {
		this.more4 = more4;
	}
	
	@Length(min=0, max=100, message="冗余字段5长度必须介于 0 和 100 之间")
	public String getMore5() {
		return more5;
	}

	public void setMore5(String more5) {
		this.more5 = more5;
	}
	
	public Date getBeginBirthday() {
		return beginBirthday;
	}

	public void setBeginBirthday(Date beginBirthday) {
		this.beginBirthday = beginBirthday;
	}
	
	public Date getEndBirthday() {
		return endBirthday;
	}

	public void setEndBirthday(Date endBirthday) {
		this.endBirthday = endBirthday;
	}
	
	public CcmPopTenant getRoomId() {
		return roomId;
	}

	public void setRoomId(CcmPopTenant roomId) {
		this.roomId = roomId;
	}

	@Length(min=0, max=32, message="特殊关怀类型长度必须介于 0 和 32 之间")
	public String getSpecialCareType() {
		return specialCareType;
	}

	public void setSpecialCareType(String specialCareType) {
		this.specialCareType = specialCareType;
	}
	
	@ExcelField(title="是否常住", align=2, sort=16, dictType="yes_no")
	@Length(min=0, max=2, message="是否常住长度必须介于 0 和 2 之间")
	public String getIsPermanent() {
		return isPermanent;
	}

	public void setIsPermanent(String isPermanent) {
		this.isPermanent = isPermanent;
	}
	
	@Length(min=0, max=255, message="未落户原因长度必须介于 0 和 255 之间")
	public String getUnsettleReason() {
		return unsettleReason;
	}

	public void setUnsettleReason(String unsettleReason) {
		this.unsettleReason = unsettleReason;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getUnsettleDate() {
		return unsettleDate;
	}

	public void setUnsettleDate(Date unsettleDate) {
		this.unsettleDate = unsettleDate;
	}
	
	@Length(min=0, max=100, message="持证种类名称长度必须介于 0 和 100 之间")
	public String getUnsettleCardType() {
		return unsettleCardType;
	}

	public void setUnsettleCardType(String unsettleCardType) {
		this.unsettleCardType = unsettleCardType;
	}
	
	@Length(min=0, max=100, message="持证编号长度必须介于 0 和 100 之间")
	public String getUnsettleCardNumber() {
		return unsettleCardNumber;
	}

	public void setUnsettleCardNumber(String unsettleCardNumber) {
		this.unsettleCardNumber = unsettleCardNumber;
	}

	public String getSexLable() {
		return sexLable;
	}

	public void setSexLable(String sexLable) {
		this.sexLable = sexLable;
	}

	public String getIsPublicServants() {
		return isPublicServants;
	}

	public void setIsPublicServants(String isPublicServants) {
		this.isPublicServants = isPublicServants;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public Integer getIsEscape() {
		return isEscape;
	}

	public void setIsEscape(Integer isEscape) {
		this.isEscape = isEscape;
	}

	public Integer getIsDispute() {
		return isDispute;
	}

	public void setIsDispute(Integer isDispute) {
		this.isDispute = isDispute;
	}

	public String getUnitCategory() {
		return unitCategory;
	}

	public void setUnitCategory(String unitCategory) {
		this.unitCategory = unitCategory;
	}
	
	@Length(min=0, max=2, message="布控标识长度必须介于 0 和2 之间")
	public String getIsControl() {
		return isControl;
	}

	public void setIsControl(String isControl) {
		this.isControl = isControl;
	}

	@Override
	public String toString() {
		return "CcmPeople{" +
				"type='" + type + '\'' +
				", name='" + name + '\'' +
				", usedname='" + usedname + '\'' +
				", sex='" + sex + '\'' +
				", birthday=" + birthday +
				", nation='" + nation + '\'' +
				", censu='" + censu + '\'' +
				", marriage='" + marriage + '\'' +
				", politics='" + politics + '\'' +
				", education='" + education + '\'' +
				", belief='" + belief + '\'' +
				", profType='" + profType + '\'' +
				", profession='" + profession + '\'' +
				", isPublicServants='" + isPublicServants + '\'' +
				", officeName='" + officeName + '\'' +
				", servPlace='" + servPlace + '\'' +
				", domicile='" + domicile + '\'' +
				", domiciledetail='" + domiciledetail + '\'' +
				", residence='" + residence + '\'' +
				", residencedetail='" + residencedetail + '\'' +
				", areaComId=" + areaComId +
				", areaGridId=" + areaGridId +
				", buildId=" + buildId +
				", roomId=" + roomId +
				", isBehind=" + isBehind +
				", isRelease=" + isRelease +
				", isRectification=" + isRectification +
				", isAids=" + isAids +
				", isPsychogeny=" + isPsychogeny +
				", isKym=" + isKym +
				", isDrugs=" + isDrugs +
				", isHarmNational=" + isHarmNational +
				", isDeliberatelyIllegal=" + isDeliberatelyIllegal +
				", isCriminalOffense=" + isCriminalOffense +
				", isEscape=" + isEscape +
				", isVisit=" + isVisit +
				", isHeresy=" + isHeresy +
				", isDangerous=" + isDangerous +
				", isMore1=" + isMore1 +
				", isMore2=" + isMore2 +
				", isDispute=" + isDispute +
				", typeSpec=" + typeSpec +
				", specialCareType='" + specialCareType + '\'' +
				", images='" + images + '\'' +
				", ident='" + ident + '\'' +
				", telephone='" + telephone + '\'' +
				", uniformlogo='" + uniformlogo + '\'' +
				", account='" + account + '\'' +
				", accountidentity='" + accountidentity + '\'' +
				", accountname='" + accountname + '\'' +
				", accountrelation='" + accountrelation + '\'' +
				", accounttelephone='" + accounttelephone + '\'' +
				", personType='" + personType + '\'' +
				", personTime=" + personTime +
				", personReason='" + personReason + '\'' +
				", flowRea='" + flowRea + '\'' +
				", accrType='" + accrType + '\'' +
				", certNum='" + certNum + '\'' +
				", recoDate=" + recoDate +
				", dueDate=" + dueDate +
				", domiType='" + domiType + '\'' +
				", focuPers=" + focuPers +
				", time=" + time +
				", cause='" + cause + '\'' +
				", explainelse='" + explainelse + '\'' +
				", esurname='" + esurname + '\'' +
				", ename='" + ename + '\'' +
				", nationality='" + nationality + '\'' +
				", idenCode='" + idenCode + '\'' +
				", idenNum='" + idenNum + '\'' +
				", idenDate=" + idenDate +
				", purpose='" + purpose + '\'' +
				", arriDate=" + arriDate +
				", departDate=" + departDate +
				", more1='" + more1 + '\'' +
				", more2='" + more2 + '\'' +
				", more3='" + more3 + '\'' +
				", more4='" + more4 + '\'' +
				", more5='" + more5 + '\'' +
				", beginBirthday=" + beginBirthday +
				", endBirthday=" + endBirthday +
				", specialCareTypes=" + Arrays.toString(specialCareTypes) +
				", age=" + age +
				", listLimite=" + Arrays.toString(listLimite) +
				", isPermanent='" + isPermanent + '\'' +
				", unsettleReason='" + unsettleReason + '\'' +
				", unsettleDate=" + unsettleDate +
				", unsettleCardType='" + unsettleCardType + '\'' +
				", unsettleCardNumber='" + unsettleCardNumber + '\'' +
				", roomIdString='" + roomIdString + '\'' +
				", tableName='" + tableName + '\'' +
				", checkUser=" + checkUser +
				", sexLable='" + sexLable + '\'' +
				", currentPage='" + currentPage + '\'' +
				", pageSize=" + pageSize +
				", startIndex=" + startIndex +
				", count='" + count + '\'' +
				", areaPoint='" + areaPoint + '\'' +
				", areaMap='" + areaMap + '\'' +
				", buildName='" + buildName + '\'' +
				", roomName='" + roomName + '\'' +
				", unitCategory='" + unitCategory + '\'' +
				'}';
	}

	public int getPeopleAge() {
		return peopleAge;
	}

	public void setPeopleAge(int peopleAge) {
		this.peopleAge = peopleAge;
	}

	public String getMqTitle() {
		return mqTitle;
	}

	public void setMqTitle(String mqTitle) {
		this.mqTitle = mqTitle;
	}

	public String getChe() {
		return che;
	}

	public void setChe(String che) {
		this.che = che;
	}

	
}