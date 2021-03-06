package com.arjjs.ccm.modules.ccm.rest.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.NotBlank;
@ApiModel(value = "CcmBphReceiveAlarmVo :接处警入参")
public class CcmBphReceiveAlarmVo extends CcmEntityUser{

    @NotBlank(message = "处警人不能为空")
    @ApiModelProperty(value = "处警人ID")
    private String handlePoliceId;

    @ApiModelProperty(value = "警情状态 0:未签收、1:已签收、2:已到达、3:已反馈")
    private String handleStatus;

    public String getHandlePoliceId() {
        return handlePoliceId;
    }

    public void setHandlePoliceId(String handlePoliceId) {
        this.handlePoliceId = handlePoliceId;
    }

    public String getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(String handleStatus) {
        this.handleStatus = handleStatus;
    }
}
