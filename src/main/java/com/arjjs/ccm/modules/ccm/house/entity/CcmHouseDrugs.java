/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.house.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.common.utils.excel.annotation.ExcelField;
import com.arjjs.ccm.modules.sys.entity.User;

/**
 * 吸毒人口&middot;Entity
 * @author arj
 * @version 2018-01-03
 */
public class CcmHouseDrugs extends DataEntity<CcmHouseDrugs> {
	
	private static final long serialVersionUID = 1L;
	private String peopleId;		// 实有人口（id）
	private Date firstFound;		// 初次发现日期
	private String contSit;		// 管控情况
	private String contName;		// 管控人姓名
	private String contTl;		// 管控人联系方式
	private String helpCase;		// 帮扶情况
	private String helpName;		// 帮扶人姓名
	private String helpTl;		// 帮扶人联系方式
	private String atteType; // 关注程度
	private Integer crimPast;		// 有无犯罪史
	private String crimStat;		// 犯罪情况
	private String drugCaus;		// 吸毒原因
	private String consDrug;		// 吸毒后果
	private String atteTypeLable;	//app接口使用
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
	public User getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}

	
	@ExcelField(title="人口类型", align=2, sort=2,dictType="sys_ccm_people")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title="籍贯", align=2, sort=3)
	public String getCensu() {
		return censu;
	}

	public void setCensu(String censu) {
		this.censu = censu;
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

	public CcmHouseDrugs() {
		super();
	}

	public CcmHouseDrugs(String id){
		super(id);
	}
	
	
//	@ExcelField(title="实有人口（id）", align=2, sort=20)
//	@Length(min=1, max=64, message="实有人口（id）长度必须介于 1 和 64 之间")
	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}
	
	@ExcelField(title="初次发现日期", align=2, sort=6)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFirstFound() {
		return firstFound;
	}

	public void setFirstFound(Date firstFound) {
		this.firstFound = firstFound;
	}

	@ExcelField(title="管控情况", align=2, sort=7,dictType="ccm_cotl_case")
	@Length(min=0, max=2, message="管控情况长度必须介于 0 和 2 之间")
	public String getContSit() {
		return contSit;
	}

	public void setContSit(String contSit) {
		this.contSit = contSit;
	}

	@ExcelField(title="管控人姓名", align=2, sort=8)
	@Length(min=0, max=50, message="管控人姓名长度必须介于 0 和 50 之间")
	public String getContName() {
		return contName;
	}

	public void setContName(String contName) {
		this.contName = contName;
	}

	@ExcelField(title="管控人联系方式", align=2, sort=9)
	@Length(min=0, max=50, message="管控人联系方式长度必须介于 0 和 50 之间")
	public String getContTl() {
		return contTl;
	}

	public void setContTl(String contTl) {
		this.contTl = contTl;
	}

	@ExcelField(title="帮扶情况", align=2, sort=10)
	@Length(min=0, max=1024, message="帮扶情况长度必须介于 0 和 1024 之间")
	public String getHelpCase() {
		return helpCase;
	}

	public void setHelpCase(String helpCase) {
		this.helpCase = helpCase;
	}

	@ExcelField(title="帮扶人姓名", align=2, sort=11)
	@Length(min=0, max=50, message="帮扶人姓名长度必须介于 0 和 50 之间")
	public String getHelpName() {
		return helpName;
	}

	public void setHelpName(String helpName) {
		this.helpName = helpName;
	}

	@ExcelField(title="帮扶人联系方式", align=2, sort=12)
	@Length(min=0, max=50, message="帮扶人联系方式长度必须介于 0 和 50 之间")
	public String getHelpTl() {
		return helpTl;
	}

	public void setHelpTl(String helpTl) {
		this.helpTl = helpTl;
	}

	@ExcelField(title="有无犯罪史", align=2, sort=13,dictType="yes_no")
	public Integer getCrimPast() {
		return crimPast;
	}

	public void setCrimPast(Integer crimPast) {
		this.crimPast = crimPast;
	}

	@ExcelField(title="犯罪情况", align=2, sort=14)
	@Length(min=0, max=1024, message="犯罪情况长度必须介于 0 和 1024 之间")
	public String getCrimStat() {
		return crimStat;
	}

	public void setCrimStat(String crimStat) {
		this.crimStat = crimStat;
	}

	@ExcelField(title="吸毒原因", align=2, sort=15,dictType="sys_ccm_drugs_caus")
	@Length(min=0, max=2, message="吸毒原因长度必须介于 0 和 2 之间")
	public String getDrugCaus() {
		return drugCaus;
	}

	public void setDrugCaus(String drugCaus) {
		this.drugCaus = drugCaus;
	}

	@ExcelField(title="吸毒后果", align=2, sort=16,dictType="sys_ccm_drugs_result")
	@Length(min=0, max=2, message="吸毒后果长度必须介于 0 和 2 之间")
	public String getConsDrug() {
		return consDrug;
	}

	public void setConsDrug(String consDrug) {
		this.consDrug = consDrug;
	}

	@ExcelField(title="姓名", align=1, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="性别", align=2, sort=17,dictType="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title="公民身份号码", align=2, sort=18)
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ExcelField(title="联系方式", align=2, sort=19)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	@ExcelField(title="关注程度", align=2, sort=20,dictType="ccm_conc_exte")
	@Length(min = 0, max = 2, message = "关注程度长度必须介于 0 和 2 之间")
	public String getAtteType() {
		return atteType;
	}

	public void setAtteType(String atteType) {
		this.atteType = atteType;
	}
	public String getAtteTypeLable() {
		return atteTypeLable;
	}
	public void setAtteTypeLable(String atteTypeLable) {
		this.atteTypeLable = atteTypeLable;
	}
	public String getComName() {
		return comName;
	}
	public void setComName(String comName) {
		this.comName = comName;
	}
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
}