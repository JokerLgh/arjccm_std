/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.arjjs.ccm.modules.ccm.moral.entity;

import org.hibernate.validator.constraints.Length;

import com.arjjs.ccm.common.persistence.DataEntity;

/**
 * 道德模范实体类Entity
 * @author lijiupeng
 * @version 2019-06-21
 */
public class CcmMoral extends DataEntity<CcmMoral> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 模范标题
	private String name;		// 名字
	private String headPhoto;		// 头像
	private String content;		// 内容
	
	public CcmMoral() {
		super();
	}

	public CcmMoral(String id){
		super(id);
	}

	@Length(min=0, max=255, message="模范标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=64, message="名字长度必须介于 0 和 64 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=256, message="头像长度必须介于 0 和 256 之间")
	public String getHeadPhoto() {
		return headPhoto;
	}

	public void setHeadPhoto(String headPhoto) {
		this.headPhoto = headPhoto;
	}
	
	@Length(min=0, max=1024, message="内容长度必须介于 0 和 1024 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}