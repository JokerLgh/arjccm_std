/**
 * Copyright &copy; 2012-2018 All rights reserved.
 */
package com.arjjs.ccm.modules.plm.home.entity;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import com.arjjs.ccm.common.persistence.DataEntity;
import com.arjjs.ccm.modules.sys.entity.User;

/**
 * 个人门户Entity
 * @author liuxue
 * @version 2018-06-29
 */
public class PlmHome extends DataEntity<PlmHome> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 布局标题
	private String portalDictId; // 门户字典ID
	private String connect;		// 链接
	private String content;		// 内容
	private String hight;		// 高度
	private String longItude;		// 行
	private String latItude;		// 列
	private String type;		// 类型
	private String sort;		// 排序
	private String extend1;		// 扩展1
	private String extend2;		// 扩展2
	private String creatorName;		// 创建者
	private String updateName;		// 更新者
	private User user;		// user_id
	
	public String getPortalDictId() {
		return portalDictId;
	}

	public void setPortalDictId(String portalDictId) {
		this.portalDictId = portalDictId;
	}
	
	public PlmHome() {
		super();
	}

	public PlmHome(String id){
		super(id);
	}

	@Length(min=1, max=256, message="布局标题长度必须介于 1 和 256 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=256, message="链接长度必须介于 0 和 256 之间")
	public String getConnect() {
		return connect;
	}

	public void setConnect(String connect) {
		this.connect = connect;
	}
	
	@Length(min=0, max=2000, message="内容长度必须介于 0 和 2000 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=24, message="高度长度必须介于 0 和 24 之间")
	public String getHight() {
		return hight;
	}

	public void setHight(String hight) {
		this.hight = hight;
	}
	
	public String getLongItude() {
		return longItude;
	}

	public void setLongItude(String longItude) {
		this.longItude = longItude;
	}
	
	public String getLatItude() {
		return latItude;
	}

	public void setLatItude(String latItude) {
		this.latItude = latItude;
	}
	
	
	

	@Length(min=0, max=1, message="类型长度必须介于 0 和 1 之间")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	@Length(min=0, max=100, message="排序长度必须介于 0 和 100 之间")
	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}
	
	@Length(min=0, max=256, message="扩展1长度必须介于 0 和 256 之间")
	public String getExtend1() {
		return extend1;
	}

	public void setExtend1(String extend1) {
		this.extend1 = extend1;
	}
	
	@Length(min=0, max=256, message="扩展2长度必须介于 0 和 256 之间")
	public String getExtend2() {
		return extend2;
	}

	public void setExtend2(String extend2) {
		this.extend2 = extend2;
	}
	
	@Length(min=1, max=64, message="创建者长度必须介于 1 和 64 之间")
	public String getCreatorName() {
		return creatorName;
	}

	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	
	@Length(min=1, max=64, message="更新者长度必须介于 1 和 64 之间")
	public String getUpdateName() {
		return updateName;
	}

	public void setUpdateName(String updateName) {
		this.updateName = updateName;
	}
	
	@NotNull(message="user_id不能为空")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}