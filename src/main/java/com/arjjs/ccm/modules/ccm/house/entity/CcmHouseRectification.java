/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.house.entity;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.common.utils.excel.annotation.ExcelField;
import com.arjjs.ccm.modules.sys.entity.User;
import com.arjjs.ccm.tool.importTool.FourHisListType;
import com.arjjs.ccm.tool.importTool.JzxzListType;
import com.arjjs.ccm.tool.importTool.ThrHoldListType;

/**
 * 社区矫正人员Entity
 * 
 * @author arj
 * @version 2018-01-04
 */
public class CcmHouseRectification extends DataEntity<CcmHouseRectification> {

	private static final long serialVersionUID = 1L;
	private String peopleId; // 实有人口（id）
	private String rectNum; // 社区矫正人员编号
	private String rectPlace; // 原羁押场所
	private String atteType; // 关注程度
	private String rectType; // 矫正类別
	private String caseType; // 案事件类別
	private String charge; // 具体罪名
	private String origCharge; // 原判刑期
	private Date origBegin; // 原判刑开始曰期
	private Date origEnd; // 原判刑结束日期
	private Date rectBegin; // 矫正开始日期
	private Date rectEnd; // 矫正结束日期
	private String receiveMode; // 接收方式
	private String fourHis; // 四史情况
	private Integer recidivist; // 是否累惯犯
	private String thrHold; // 三涉情况
	private Integer correcthas; // 是否建立矫正小组
	private String corrected; // 矫正小组人员组成情况
	private String correctlift; // 矫正解除（终止）类型
	private Integer detached; // 是否有脱管
	private String detaReason; // 脱管原因
	private String detaSupe; // 检察监督脱管情况
	private String detaCorr; // 脱管纠正情况
	private Integer lackContr; // 是否有漏管
	private String lackContrRe; // 漏管原因
	private String lackContrCase; // 检察监督漏管情况
	private String lackContrCaseCorr; // 漏管纠正情况
	private String rewandpun; // 奖惩情况
	private String penaChan; // 刑罚变更执行情况
	private Integer reoffend; // 是否重新犯罪
	private String reofCharge; // 重新犯罪名称
	private String atteTypeLable;	//app接口使用
	// 实有人口
	private String type; // 人口类型
	private String name; // 姓名
	private String censu; // 籍贯
	
	private String sex; // 性别
	private String ident; // 公民身份号码
	private String telephone; // 联系方式

	private String domiciledetail; // 户籍门（楼）详址
	private String residencedetail; // 现住门（楼）详址
	private String images; // 图片
	private Date birthday;	//出生日期
	private String comName; 	//app接口使用，所属社区
	private User checkUser;		// 拦截器中使用该用户进行权限拦截，App的rest接口使用
	public User getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(User checkUser) {
		this.checkUser = checkUser;
	}
	
	public CcmHouseRectification() {
		super();
	}

	public CcmHouseRectification(String id) {
		super(id);
	}

	/* @ExcelField(title="实有人口（id）", align=2, sort=39) */
	/* @Length(min = 1, max = 64, message = "实有人口（id）长度必须介于 1 和 64 之间") */
	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}
	
	@ExcelField(title="社区矫正人员编号", align=2, sort=2)
	@Length(min = 0, max = 16, message = "社区矫正人员编号长度必须介于 0 和 16 之间")
	public String getRectNum() {
		return rectNum;
	}

	public void setRectNum(String rectNum) {
		this.rectNum = rectNum;
	}

	@ExcelField(title="原羁押场所", align=2, sort=3)
	@Length(min = 0, max = 100, message = "原羁押场所长度必须介于 0 和 100 之间")
	public String getRectPlace() {
		return rectPlace;
	}

	public void setRectPlace(String rectPlace) {
		this.rectPlace = rectPlace;
	}

	@ExcelField(title="矫正类別", align=2, sort=4,dictType="ccm_core_sort")
	@Length(min = 0, max = 2, message = "矫正类別长度必须介于 0 和 2 之间")
	public String getRectType() {
		return rectType;
	}

	public void setRectType(String rectType) {
		this.rectType = rectType;
	}

	@ExcelField(title="案事件类別", align=2, sort=5)
	@Length(min = 0, max = 6, message = "案事件类別长度必须介于 0 和 6 之间")
	public String getCaseType() {
		return caseType;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	@ExcelField(title="具体罪名", align=2, sort=6)
	@Length(min = 0, max = 100, message = "具体罪名长度必须介于 0 和 100 之间")
	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}

	@ExcelField(title="原判刑期", align=2, sort=7)
	@Length(min = 0, max = 50, message = "原判刑期长度必须介于 0 和 50 之间")
	public String getOrigCharge() {
		return origCharge;
	}

	public void setOrigCharge(String origCharge) {
		this.origCharge = origCharge;
	}

	@ExcelField(title="原判刑开始曰期", align=2, sort=8)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrigBegin() {
		return origBegin;
	}

	public void setOrigBegin(Date origBegin) {
		this.origBegin = origBegin;
	}

	@ExcelField(title="原判刑结束日期", align=2, sort=9)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOrigEnd() {
		return origEnd;
	}

	public void setOrigEnd(Date origEnd) {
		this.origEnd = origEnd;
	}

	@ExcelField(title="矫正开始日期", align=2, sort=10)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRectBegin() {
		return rectBegin;
	}

	public void setRectBegin(Date rectBegin) {
		this.rectBegin = rectBegin;
	}

	@ExcelField(title="矫正结束日期", align=2, sort=11)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getRectEnd() {
		return rectEnd;
	}

	public void setRectEnd(Date rectEnd) {
		this.rectEnd = rectEnd;
	}

	@ExcelField(title="接收方式", align=2, sort=12,dictType="ccm_recv_way")
	@Length(min = 0, max = 2, message = "接收方式长度必须介于 0 和 2 之间")
	public String getReceiveMode() {
		return receiveMode;
	}

	public void setReceiveMode(String receiveMode) {
		this.receiveMode = receiveMode;
	}

	@ExcelField(title="四史情况", align=32, sort=13, fieldType = FourHisListType.class)
	@Length(min = 0, max = 32, message = "四史情况长度必须介于 0 和 32 之间")
	public String getFourHis() {
		return fourHis;
	}

	public void setFourHis(String fourHis) {
		this.fourHis = fourHis;
	}

	public List<String> getFourHisList() {
		List<String> list = Lists.newArrayList();
		if (fourHis != null){
			for (String s : StringUtils.split(fourHis, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	public void setFourHisList(List<String> list) {
		fourHis = ","+StringUtils.join(list, ",")+",";
	}

	
	
	
	@ExcelField(title="是否累惯犯", align=2, sort=14,dictType="yes_no")
	public Integer getRecidivist() {
		return recidivist;
	}

	public void setRecidivist(Integer recidivist) {
		this.recidivist = recidivist;
	}

	@ExcelField(title="三涉情况", align=32, sort=15, fieldType = ThrHoldListType.class)
	@Length(min = 0, max = 32, message = "三涉情况长度必须介于 0 和 32 之间")
	public String getThrHold() {
		return thrHold;
	}

	public void setThrHold(String thrHold) {
		this.thrHold = thrHold;
	}

	public List<String> getThrHoldList() {
		List<String> list = Lists.newArrayList();
		if (thrHold != null){
			for (String s : StringUtils.split(thrHold, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	public void setThrHoldList(List<String> list) {
		thrHold = ","+StringUtils.join(list, ",")+",";
	}

	
	
	@ExcelField(title="是否建立矫正小组", align=2, sort=16,dictType="yes_no")
	public Integer getCorrecthas() {
		return correcthas;
	}

	public void setCorrecthas(Integer correcthas) {
		this.correcthas = correcthas;
	}

	@ExcelField(title="矫正小组人员组成情况", align=64, sort=17, fieldType = JzxzListType.class)
	@Length(min = 0, max = 64, message = "矫正小组人员组成情况长度必须介于 0 和64 之间")
	public String getCorrected() {
		return corrected;
	}

	public void setCorrected(String corrected) {
		this.corrected = corrected;
	}

	public List<String> getCorrectedList() {
		List<String> list = Lists.newArrayList();
		if (corrected != null){
			for (String s : StringUtils.split(corrected, ",")) {
				list.add(s);
			}
		}
		return list;
	}
	public void setCorrectedList(List<String> list) {
		corrected = ","+StringUtils.join(list, ",")+",";
	}

	
	
	@ExcelField(title="矫正解除（终止）类型", align=2, sort=18,dictType="ccm_core_rele")
	@Length(min = 0, max = 2, message = "矫正解除（终止）类型长度必须介于 0 和 2 之间")
	public String getCorrectlift() {
		return correctlift;
	}

	public void setCorrectlift(String correctlift) {
		this.correctlift = correctlift;
	}

	@ExcelField(title="是否有脱管", align=2, sort=19,dictType="yes_no")
	public Integer getDetached() {
		return detached;
	}

	public void setDetached(Integer detached) {
		this.detached = detached;
	}

	@ExcelField(title="脱管原因", align=2, sort=20)
	public String getDetaReason() {
		return detaReason;
	}

	public void setDetaReason(String detaReason) {
		this.detaReason = detaReason;
	}

	@ExcelField(title="检察监督脱管情况", align=2, sort=21)
	public String getDetaSupe() {
		return detaSupe;
	}

	public void setDetaSupe(String detaSupe) {
		this.detaSupe = detaSupe;
	}

	@ExcelField(title="脱管纠正情况", align=2, sort=22)
	public String getDetaCorr() {
		return detaCorr;
	}

	public void setDetaCorr(String detaCorr) {
		this.detaCorr = detaCorr;
	}

	@ExcelField(title="是否有漏管", align=2, sort=23,dictType="yes_no")
	public Integer getLackContr() {
		return lackContr;
	}

	public void setLackContr(Integer lackContr) {
		this.lackContr = lackContr;
	}

	@ExcelField(title="漏管原因", align=2, sort=24)
	public String getLackContrRe() {
		return lackContrRe;
	}

	public void setLackContrRe(String lackContrRe) {
		this.lackContrRe = lackContrRe;
	}

	@ExcelField(title="检察监督漏管情况", align=2, sort=25)
	public String getLackContrCase() {
		return lackContrCase;
	}

	public void setLackContrCase(String lackContrCase) {
		this.lackContrCase = lackContrCase;
	}

	@ExcelField(title="漏管纠正情况", align=2, sort=26)
	public String getLackContrCaseCorr() {
		return lackContrCaseCorr;
	}

	public void setLackContrCaseCorr(String lackContrCaseCorr) {
		this.lackContrCaseCorr = lackContrCaseCorr;
	}

	@ExcelField(title="奖惩情况", align=2, sort=27)
	public String getRewandpun() {
		return rewandpun;
	}

	public void setRewandpun(String rewandpun) {
		this.rewandpun = rewandpun;
	}

	@ExcelField(title="刑罚变更执行情况", align=2, sort=28)
	public String getPenaChan() {
		return penaChan;
	}

	public void setPenaChan(String penaChan) {
		this.penaChan = penaChan;
	}

	@ExcelField(title="是否重新犯罪", align=2, sort=29,dictType="yes_no")
	public Integer getReoffend() {
		return reoffend;
	}

	public void setReoffend(Integer reoffend) {
		this.reoffend = reoffend;
	}

	@ExcelField(title="重新犯罪名称", align=2, sort=30)
	@Length(min = 0, max = 100, message = "重新犯罪名称长度必须介于 0 和 100 之间")
	public String getReofCharge() {
		return reofCharge;
	}

	public void setReofCharge(String reofCharge) {
		this.reofCharge = reofCharge;
	}


	@ExcelField(title="人口类型", align=2, sort=31,dictType="sys_ccm_people")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@ExcelField(title="姓名", align=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@ExcelField(title="籍贯", align=2, sort=32)
	public String getCensu() {
		return censu;
	}

	public void setCensu(String censu) {
		this.censu = censu;
	}

	@ExcelField(title="性别", align=2, sort=33,dictType="sex")
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title="公民身份号码", align=2, sort=34)
	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	@ExcelField(title="联系方式", align=2, sort=35)
	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	@ExcelField(title="户籍门（楼）详址", align=2, sort=36)
	public String getDomiciledetail() {
		return domiciledetail;
	}

	public void setDomiciledetail(String domiciledetail) {
		this.domiciledetail = domiciledetail;
	}

	@ExcelField(title="现住门（楼）详址", align=2, sort=37)
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

	@ExcelField(title="关注程度", align=2, sort=38,dictType="ccm_conc_exte")
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

	@Override
	public String toString() {
		return "CcmHouseRectification{" +
				"peopleId='" + peopleId + '\'' +
				", rectNum='" + rectNum + '\'' +
				", rectPlace='" + rectPlace + '\'' +
				", atteType='" + atteType + '\'' +
				", rectType='" + rectType + '\'' +
				", caseType='" + caseType + '\'' +
				", charge='" + charge + '\'' +
				", origCharge='" + origCharge + '\'' +
				", origBegin=" + origBegin +
				", origEnd=" + origEnd +
				", rectBegin=" + rectBegin +
				", rectEnd=" + rectEnd +
				", receiveMode='" + receiveMode + '\'' +
				", fourHis='" + fourHis + '\'' +
				", recidivist=" + recidivist +
				", thrHold='" + thrHold + '\'' +
				", correcthas=" + correcthas +
				", corrected='" + corrected + '\'' +
				", correctlift='" + correctlift + '\'' +
				", detached=" + detached +
				", detaReason='" + detaReason + '\'' +
				", detaSupe='" + detaSupe + '\'' +
				", detaCorr='" + detaCorr + '\'' +
				", lackContr=" + lackContr +
				", lackContrRe='" + lackContrRe + '\'' +
				", lackContrCase='" + lackContrCase + '\'' +
				", lackContrCaseCorr='" + lackContrCaseCorr + '\'' +
				", rewandpun='" + rewandpun + '\'' +
				", penaChan='" + penaChan + '\'' +
				", reoffend=" + reoffend +
				", reofCharge='" + reofCharge + '\'' +
				", atteTypeLable='" + atteTypeLable + '\'' +
				", type='" + type + '\'' +
				", name='" + name + '\'' +
				", censu='" + censu + '\'' +
				", sex='" + sex + '\'' +
				", ident='" + ident + '\'' +
				", telephone='" + telephone + '\'' +
				", domiciledetail='" + domiciledetail + '\'' +
				", residencedetail='" + residencedetail + '\'' +
				", images='" + images + '\'' +
				", birthday=" + birthday +
				", comName='" + comName + '\'' +
				", checkUser=" + checkUser +
				'}';
	}
}