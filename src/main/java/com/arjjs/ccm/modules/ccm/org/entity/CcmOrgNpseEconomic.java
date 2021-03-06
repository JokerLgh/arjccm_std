/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.org.entity;

import org.hibernate.validator.constraints.Length;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.common.utils.excel.annotation.ExcelField;

/**
 * 企业经济运行数据Entity
 * @author liang
 * @version 2018-07-19
 */
public class CcmOrgNpseEconomic extends DataEntity<CcmOrgNpseEconomic> {
	
	private static final long serialVersionUID = 1L;
	private String naspId;		// 企业id
	private Date years;		// 年
	private Double turnover;		// 营业额（万元）
	private Double netMargin;		// 净利润（万元）
	private Double taxes;		// 纳税总额（万元）
	private Double fixedAssets;		// 固定资产总额（万元）
	private Double liabilities;		// 负债（万元）
	private Date beginYears;		// 开始 年
	private Date endYears;		// 结束 年
	private String exportYears;
	
	//非公有制经济组织
	private String compId;		// 工商执照注册号
	private String compName;		// 名称
	
	@ExcelField(title="工商执照注册号", align=2, sort=1)
	public String getCompId() {
		return compId;
	}

	public void setCompId(String compId) {
		this.compId = compId;
	}
	
	@ExcelField(title="名称", align=2, sort=2)
	public String getCompName() {
		return compName;
	}

	public void setCompName(String compName) {
		this.compName = compName;
	}

	public CcmOrgNpseEconomic() {
		super();
	}

	public CcmOrgNpseEconomic(String id){
		super(id);
	}

	@Length(min=0, max=64, message="企业id长度必须介于 0 和 64 之间")
	public String getNaspId() {
		return naspId;
	}

	public void setNaspId(String naspId) {
		this.naspId = naspId;
	}

	public Date getYears() {
		return years;
	}
	
	@ExcelField(title="年份", align=2, sort=3)
	public String getExportYears() {
		return exportYears;
	}

	public void setExportYears(String exportYears) {
		this.exportYears = exportYears;
	}

	public void setYears(Date years) {
		this.years = years;
	}

	
	@ExcelField(title="营业额（万元）", align=2, sort=4)
	public Double getTurnover() {
		return turnover;
	}

	public void setTurnover(Double turnover) {
		this.turnover = turnover;
	}

	
	@ExcelField(title="净利润（万元）", align=2, sort=5)
	public Double getNetMargin() {
		return netMargin;
	}

	public void setNetMargin(Double netMargin) {
		this.netMargin = netMargin;
	}

	
	@ExcelField(title="纳税总额（万元）", align=2, sort=6)
	public Double getTaxes() {
		return taxes;
	}

	public void setTaxes(Double taxes) {
		this.taxes = taxes;
	}

	
	@ExcelField(title="固定资产总额（万元）", align=2, sort=7)
	public Double getFixedAssets() {
		return fixedAssets;
	}

	public void setFixedAssets(Double fixedAssets) {
		this.fixedAssets = fixedAssets;
	}

	
	@ExcelField(title="负债（万元）", align=2, sort=8)
	public Double getLiabilities() {
		return liabilities;
	}

	public void setLiabilities(Double liabilities) {
		this.liabilities = liabilities;
	}
	
	public Date getBeginYears() {
		return beginYears;
	}

	public void setBeginYears(Date beginYears) {
		this.beginYears = beginYears;
	}
	
	public Date getEndYears() {
		return endYears;
	}

	public void setEndYears(Date endYears) {
		this.endYears = endYears;
	}
		
}