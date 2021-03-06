/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.event.entity;

import java.util.Date;

import org.hibernate.validator.constraints.Length;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.modules.sys.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 事件处理Entity
 * 
 * @author arj
 * @version 2018-01-10
 */
public class CcmEventCasedeal extends DataEntity<CcmEventCasedeal> {

	private static final long serialVersionUID = 1L;
//	private String eventIncidentId; // 案事件
	private String objId; // 事件id
	private String objType; // 事件类型（字典：ccm_event_obj_table）
	private String caseName; // 案事件名称
	private String status; // 案事件状态
	private User handleUser;		// 处理人员
	private Date handleDeadline;		// 处理截至时间
	private String isSupervise;		// 是否督办
	private String ratify;		//是否批示
	private String stick;		//是否置顶
	private String urgent;		//是否加急
	private String historyLegacy;		//是否历史遗留
	private Date handleDate;		// 处理完成时间
	private String handleStep;		// 处理措施
	private String handleStatus;		// 处理状态
	private String handleFeedback;		// 案事件反馈
	private Date checkDate;		// 考评日期
	private String checkUser;		// 考评人员
	private String checkOpinion;		// 考评意见
	private int checkScore;		// 得分
	private Date beginHandleDeadline;		// 开始 处理截至时间
	private Date endHandleDeadline;		// 结束 处理截至时间
	private int isExtension;		// 是否处理超期，页面显示用
	private String isCheck;		// 查询条件用，check表示是事件考评，否则表示事件处理
	private String objTypeLable; // app列表显示
	private String statusLable;	//用于app接口列表显示
	private String manageType;		//办理状态
	private String file;		//附件
	private int gradeNum;
	private String effectType;
	private String tailCase;
	private String tailContent;
	private String tailPerson;
	private String more1;
	private Date tailTime;
	private String evaluate;

	/*@Length(min = 1, max = 64, message = "案事件长度必须优先添加后才可以添加案事件处理信息")
	public String getEventIncidentId() {
		return eventIncidentId;
	}

	public void setEventIncidentId(String eventIncidentId) {
		this.eventIncidentId = eventIncidentId;
	}*/


	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getTailTime() {
		return tailTime;
	}

	public void setTailTime(Date tailTime) {
		this.tailTime = tailTime;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public String getTailCase() {
		return tailCase;
	}

	public void setTailCase(String tailCase) {
		this.tailCase = tailCase;
	}

	public String getTailContent() {
		return tailContent;
	}

	public void setTailContent(String tailContent) {
		this.tailContent = tailContent;
	}

	public String getTailPerson() {
		return tailPerson;
	}

	public void setTailPerson(String tailPerson) {
		this.tailPerson = tailPerson;
	}

	public String getMore1() {
		return more1;
	}

	public void setMore1(String more1) {
		this.more1 = more1;
	}

	public int getGradeNum() {
		return gradeNum;
	}

	public void setGradeNum(int gradeNum) {
		this.gradeNum = gradeNum;
	}

	public String getEffectType() {
		return effectType;
	}

	public void setEffectType(String effectType) {
		this.effectType = effectType;
	}

	public String getManageType() {
		return manageType;
	}

	public void setManageType(String manageType) {
		this.manageType = manageType;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getObjId() {
		return objId;
	}

	public void setObjId(String objId) {
		this.objId = objId;
	}

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getCaseName() {
		return caseName;
	}

	public void setCaseName(String caseName) {
		this.caseName = caseName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public User getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(User handleUser) {
		this.handleUser = handleUser;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleDeadline() {
		return handleDeadline;
	}

	public void setHandleDeadline(Date handleDeadline) {
		this.handleDeadline = handleDeadline;
	}
	
	@Length(min=0, max=2, message="是否督办长度必须介于 0 和 2 之间")
	public String getIsSupervise() {
		return isSupervise;
	}

	public void setIsSupervise(String isSupervise) {
		this.isSupervise = isSupervise;
	}
	
	
	public String getRatify() {
		return ratify;
	}

	public void setRatify(String ratify) {
		this.ratify = ratify;
	}

	public String getStick() {
		return stick;
	}

	public void setStick(String stick) {
		this.stick = stick;
	}

	public String getUrgent() {
		return urgent;
	}

	public void setUrgent(String urgent) {
		this.urgent = urgent;
	}

	public String getHistoryLegacy() {
		return historyLegacy;
	}

	public void setHistoryLegacy(String historyLegacy) {
		this.historyLegacy = historyLegacy;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(Date handleDate) {
		this.handleDate = handleDate;
	}
	
	@Length(min=0, max=256, message="处理措施长度必须介于 0 和 256 之间")
	public String getHandleStep() {
		return handleStep;
	}

	public void setHandleStep(String handleStep) {
		this.handleStep = handleStep;
	}
	
	@Length(min=0, max=2, message="处理状态长度必须介于 0 和 2 之间")
	public String getHandleStatus() {
		return handleStatus;
	}

	public void setHandleStatus(String handleStatus) {
		this.handleStatus = handleStatus;
	}
	
	@Length(min=0, max=256, message="案事件反馈长度必须介于 0 和 256 之间")
	public String getHandleFeedback() {
		return handleFeedback;
	}

	public void setHandleFeedback(String handleFeedback) {
		this.handleFeedback = handleFeedback;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCheckDate() {
		return checkDate;
	}

	public void setCheckDate(Date checkDate) {
		this.checkDate = checkDate;
	}
	
	@Length(min=0, max=64, message="考评人员长度必须介于 0 和 64 之间")
	public String getCheckUser() {
		return checkUser;
	}

	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	
	@Length(min=0, max=256, message="考评意见长度必须介于 0 和 256 之间")
	public String getCheckOpinion() {
		return checkOpinion;
	}

	public void setCheckOpinion(String checkOpinion) {
		this.checkOpinion = checkOpinion;
	}
	
	public int getCheckScore() {
		return checkScore;
	}

	public void setCheckScore(int checkScore) {
		this.checkScore = checkScore;
	}

	public Date getBeginHandleDeadline() {
		return beginHandleDeadline;
	}

	public void setBeginHandleDeadline(Date beginHandleDeadline) {
		this.beginHandleDeadline = beginHandleDeadline;
	}
	
	public Date getEndHandleDeadline() {
		return endHandleDeadline;
	}

	public void setEndHandleDeadline(Date endHandleDeadline) {
		this.endHandleDeadline = endHandleDeadline;
	}

	public int getIsExtension() {
		return isExtension;
	}

	public void setIsExtension(int isExtension) {
		this.isExtension = isExtension;
	}

	public String getObjTypeLable() {
		return objTypeLable;
	}

	public void setObjTypeLable(String objTypeLable) {
		this.objTypeLable = objTypeLable;
	}

	public String getStatusLable() {
		return statusLable;
	}

	public void setStatusLable(String statusLable) {
		this.statusLable = statusLable;
	}

}