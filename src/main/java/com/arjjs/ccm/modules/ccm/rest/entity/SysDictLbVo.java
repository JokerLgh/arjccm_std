package com.arjjs.ccm.modules.ccm.rest.entity;

import java.util.List;

public class SysDictLbVo {

    private String id;

    private String value;

    private String label;

    private String type;

    private String description;

    private Long sort;

    private String parentId;

    private List<SysDictLxVo>  lxList;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getSort() {
        return sort;
    }

    public void setSort(Long sort) {
        this.sort = sort;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<SysDictLxVo> getLxList() {
        return lxList;
    }

    public void setLxList(List<SysDictLxVo> lxList) {
        this.lxList = lxList;
    }
}
