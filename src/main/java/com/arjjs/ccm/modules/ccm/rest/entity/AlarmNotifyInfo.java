package com.arjjs.ccm.modules.ccm.rest.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;
/**
 * 警情通知详情实体类
 */

public class AlarmNotifyInfo  implements Serializable {

	private static final long serialVersionUID = 1L;

	private String alarmId;
	private String alarmOrderNum;
	private String alarmPoliceNum;
	private String alarmPoliceName;
	private String alarmManName;
	private String alarmManTel;
	private String alarmPlace;
	private double alarmX;
	private double alarmY;
	private double alarmZ;
	private String alarmContent;
	private String alarmGenerName;
	private String alarmTypeName;
	private String alarmClassName;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date   alarmTime;
	private String alarmRecord;
	private String officeName;

	private String maxDispatchTime;
	private String maxArriveTime;

	public String getAlarmId() {
		return alarmId;
	}

	public void setAlarmId(String alarmId) {
		this.alarmId = alarmId;
	}

	public String getAlarmOrderNum() {
		return alarmOrderNum;
	}

	public void setAlarmOrderNum(String alarmOrderNum) {
		this.alarmOrderNum = alarmOrderNum;
	}

	public String getAlarmPoliceNum() {
		return alarmPoliceNum;
	}

	public void setAlarmPoliceNum(String alarmPoliceNum) {
		this.alarmPoliceNum = alarmPoliceNum;
	}

	public String getAlarmPoliceName() {
		return alarmPoliceName;
	}

	public void setAlarmPoliceName(String alarmPoliceName) {
		this.alarmPoliceName = alarmPoliceName;
	}

	public String getAlarmManName() {
		return alarmManName;
	}

	public void setAlarmManName(String alarmManName) {
		this.alarmManName = alarmManName;
	}

	public String getAlarmManTel() {
		return alarmManTel;
	}

	public void setAlarmManTel(String alarmManTel) {
		this.alarmManTel = alarmManTel;
	}

	public String getAlarmPlace() {
		return alarmPlace;
	}

	public void setAlarmPlace(String alarmPlace) {
		this.alarmPlace = alarmPlace;
	}

	public double getAlarmX() {
		return alarmX;
	}

	public void setAlarmX(double alarmX) {
		this.alarmX = alarmX;
	}

	public double getAlarmY() {
		return alarmY;
	}

	public void setAlarmY(double alarmY) {
		this.alarmY = alarmY;
	}

	public double getAlarmZ() {
		return alarmZ;
	}

	public void setAlarmZ(double alarmZ) {
		this.alarmZ = alarmZ;
	}

	public String getAlarmContent() {
		return alarmContent;
	}

	public void setAlarmContent(String alarmContent) {
		this.alarmContent = alarmContent;
	}

	public String getAlarmGenerName() {
		return alarmGenerName;
	}

	public void setAlarmGenerName(String alarmGenerName) {
		this.alarmGenerName = alarmGenerName;
	}

	public String getAlarmTypeName() {
		return alarmTypeName;
	}

	public void setAlarmTypeName(String alarmTypeName) {
		this.alarmTypeName = alarmTypeName;
	}

	public String getAlarmClassName() {
		return alarmClassName;
	}

	public void setAlarmClassName(String alarmClassName) {
		this.alarmClassName = alarmClassName;
	}

	public Date getAlarmTime() {
		return alarmTime;
	}

	public void setAlarmTime(Date alarmTime) {
		this.alarmTime = alarmTime;
	}

	public String getAlarmRecord() {
		return alarmRecord;
	}

	public void setAlarmRecord(String alarmRecord) {
		this.alarmRecord = alarmRecord;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	public String getMaxDispatchTime() {
		return maxDispatchTime;
	}

	public void setMaxDispatchTime(String maxDispatchTime) {
		this.maxDispatchTime = maxDispatchTime;
	}

	public String getMaxArriveTime() {
		return maxArriveTime;
	}

	public void setMaxArriveTime(String maxArriveTime) {
		this.maxArriveTime = maxArriveTime;
	}
}
