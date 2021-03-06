/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.event.entity.wechat;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.arjjs.ccm.common.persistence.DataEntity;

/**
 * 微信信息上报Entity
 * @author fu
 * @version 2018-04-14
 */
public class CcmWechatEvent extends DataEntity<CcmWechatEvent> {
	
	private static final long serialVersionUID = 1L;
	private String wechatUser;		// 微信用户
	private String reportInfo;		// 上报信息
	private Date reportTime;		// 上报时间
	private String state;		// 回复情况
	private String status;		// 处理状态
	private String userTel;		// 联系方式
	private Date beginReportTime;		// 开始 上报时间
	private Date endReportTime;		// 结束 上报时间
	private List<CcmWechatEventAttachment> eventAttachmentList;	//附件
	private List<String> FilePathList;	//附件路径表
	private String statusLable;	//用于app接口列表显示
	private String stateLable;	//用于app接口列表显示
	private String file;


	public CcmWechatEvent() {
		super();
	}

	public CcmWechatEvent(String id){
		super(id);
	}

	@Length(min=0, max=64, message="微信用户长度必须介于 0 和 64 之间")
	public String getWechatUser() {
		return wechatUser;
	}

	public void setWechatUser(String wechatUser) {
		this.wechatUser = wechatUser;
	}
	
	@Length(min=0, max=256, message="上报信息长度必须介于 0 和 256 之间")
	public String getReportInfo() {
		return reportInfo;
	}

	public void setReportInfo(String reportInfo) {
		this.reportInfo = reportInfo;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getReportTime() {
		return reportTime;
	}

	public void setReportTime(Date reportTime) {
		this.reportTime = reportTime;
	}
	
	@Length(min=0, max=2, message="处理状态长度必须介于 0 和 2 之间")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Length(min=0, max=64, message="联系方式长度必须介于 0 和 64 之间")
	public String getUserTel() {
		return userTel;
	}

	public void setUserTel(String userTel) {
		this.userTel = userTel;
	}
	
	public Date getBeginReportTime() {
		return beginReportTime;
	}

	public void setBeginReportTime(Date beginReportTime) {
		this.beginReportTime = beginReportTime;
	}
	
	public Date getEndReportTime() {
		return endReportTime;
	}

	public void setEndReportTime(Date endReportTime) {
		this.endReportTime = endReportTime;
	}

	public List<CcmWechatEventAttachment> getEventAttachmentList() {
		return eventAttachmentList;
	}

	public void setEventAttachmentList(List<CcmWechatEventAttachment> eventAttachmentList) {
		this.eventAttachmentList = eventAttachmentList;
	}

	public List<String> getFilePathList() {
		return FilePathList;
	}

	public void setFilePathList(List<String> filePathList) {
		FilePathList = filePathList;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusLable() {
		return statusLable;
	}

	public void setStatusLable(String statusLable) {
		this.statusLable = statusLable;
	}

	public String getStateLable() {
		return stateLable;
	}

	public void setStateLable(String stateLable) {
		this.stateLable = stateLable;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}
}