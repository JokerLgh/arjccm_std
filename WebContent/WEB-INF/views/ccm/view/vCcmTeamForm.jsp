<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>综治队伍管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					$("#btnSubmit").attr("disabled", true);
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#btnSubmit").removeAttr('disabled');
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("td").css({"padding":"10px"});
			$("td").css({"border":"1px dashed #CCCCCC"});
		});
	</script>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/view/vCcmTeam/list?office.id=${office.id}&office.parentIds=,${office.id},">数据列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/view/vCcmTeam/form?id=${vCcmTeam.id}">数据<shiro:hasPermission name="view:vCcmTeam:edit">${not empty vCcmTeam.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="view:vCcmTeam:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="vCcmTeam" action="${ctx}/org/ccmOrgTeam/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<table class="first_table" border="1px" style="border-color: #CCCCCC; border: 1px dashed #CCCCCC; padding: 10px; width: 100%;" >
		<tr>
			<td rowspan="3">	
		<div>
			<label class="control-label">用户头像：</label>
			<div class="controls">
				<!--  
				<form:input path="photo" htmlEscape="false" maxlength="1000" class="input-xlarge " readonly="true"/>-->
				<form:hidden id="images" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
					<sys:ckfinder input="images" type="images" uploadPath="/photo" selectMultiple="false" readonly="true" maxWidth="80" maxHeight="120"/>
			</div>
		</div>		
			</td>
			<td>
		<div>
			<label class="control-label">最后登陆IP：</label>
			<div class="controls">
				<label class="lbl">${vCcmTeam.loginIp}</label>
			</div>
		</div>	
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label">最后登陆时间：</label>
			<div class="controls">
				<label class="lbl"><fmt:formatDate value="${vCcmTeam.loginDate}" type="both" dateStyle="full"/></label>
			</div>
		</div>		
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label">是否可登录：</label>
			<div class="controls">${fns:getDictLabel(vCcmTeam.loginFlag, 'yes_no', '')}
				<!--<form:select path="loginFlag" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>  -->
			</div>
		</div>		
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>机构名称：</label>
			<div class="controls">${vCcmTeam.companyId.name}
				<!--
                <sys:treeselect id="company" name="company.id" value="${user.company.id}" labelName="company.name" labelValue="${user.company.name}"
					title="组织机构" url="/sys/office/treeData?type=1" cssClass="required"/>  -->
				<!--<form:input path="companyId.name" htmlEscape="false" maxlength="255"
					class="input-xlarge " readonly="true"/>  -->
			</div>
		</div>		
			</td>
			
			<td>
				<div>
					<label class="control-label">邮箱：</label>
					<div class="controls">${vCcmTeam.email}
						<!--<form:input path="email" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>  -->
					</div>
				</div>
				
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>部门名称：</label>
			<div class="controls">${vCcmTeam.office.name}
				<!--
				<sys:treeselect id="office" name="office.id" value="${vCcmTeam.office.id}" labelName="office.name" labelValue="${vCcmTeam.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
				  -->
				<!--<form:input path="office.name" htmlEscape="false" maxlength="255"
					class="input-xlarge " readonly="true"/>  -->
			</div>
		</div>		
			</td>
			<td>
			
		<div>
			<label class="control-label">固定电话：</label>
			<div class="controls">${vCcmTeam.phone}
				<!--<form:input path="phone" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>  -->
			</div>
		</div>		
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label">工号：</label>
			<div class="controls">${vCcmTeam.no}
				<!--<form:input path="no" htmlEscape="false" maxlength="100" class="input-xlarge" readonly="true"/>  -->
			</div>
		</div>		
			</td>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>手机号码：</label>
			<div class="controls">${vCcmTeam.mobile}
				<!--<form:input path="mobile" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>  -->

			</div>
		</div>		
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>姓名：</label>
			<div class="controls">${vCcmTeam.name}
				<!-- <form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/> -->

			</div>
		</div>		
			</td>
			<td>
		<div>
			<label class="control-label">用户类型：</label>
			<div class="controls">${fns:getDictLabel(vCcmTeam.userType, 'sys_user_type', '')}
				<!--<form:select path="userType" class="input-xlarge " disabled="true">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>  -->
			</div>
		</div>
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>登录名：</label>
			<div class="controls">${vCcmTeam.loginName}
				<!--<form:input path="loginName" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>  -->

			</div>
		</div>
		<!--
		<div>
			<label class="control-label">密码：</label>
			<div class="controls">
				<form:input path="password" htmlEscape="false" maxlength="100" class="input-xlarge required" readonly="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		 -->		
			</td>
			<td>
			
			</td>
		</tr>
		<tr>
			<td colspan="2">
		<div>
			<label class="control-label">备注信息：</label>
			<div class="controls">${vCcmTeam.remarks}
				<!--<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " readonly="true"/>  -->
			</div>
		</div>		
			</td>
		</tr>
		</table>
		<table>
		<tr>
			<td style="padding-top: 15px" >
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>人员类型：</label>
			<div class="controls">
				<form:select path="teamType" class="input-xlarge ">
					<form:options items="${fns:getDictList('ccm_org_team_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>			
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>级别：</label>
					<div class="controls">
						<form:select path="grade" class="input-xlarge required">
							<form:option value="" label=""/>
							<form:options items="${fns:getDictList('ccm_sta_lev')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>

					</div>
				</div>
			</td>

		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>性别：</label>
					<div class="controls">
						<form:radiobuttons path="sex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required" />

					</div>
				</div>		
			</td>
			<td>
				<div>
					<label class="control-label">其他联系方式：</label>
					<div class="controls">
						<form:input path="fixTel" htmlEscape="false" maxlength="18" class="input-xlarge phone"/>
					</div>
				</div>
			</td>

		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>民族：</label>
			<div class="controls">
				<form:select path="nation" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sys_volk')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>	
			</td>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>职务：</label>
			<div class="controls">
				<form:select path="service" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_sta_dut')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>		
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>政治面貌：</label>
			<div class="controls">
				<form:select path="politics" class="input-xlarge ">
					<form:option value="" label="其他"/>
					<form:options items="${fns:getDictList('sys_ccm_poli_stat')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>		
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>出生日期：</label>
					<div class="controls">
						<input name="birthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${vCcmTeam.birthday}" pattern="yyyy-MM-dd"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>

					</div>
				</div>	
			</td>
		</tr>
		<tr>
			<td>
		<div>
			<label class="control-label">公民身份号码：</label>
			<div class="controls">
				<form:input path="idenNum" htmlEscape="false" maxlength="30" class="input-xlarge ident0 card"/>
			</div>
		</div>			
			</td>
			<td>
		<div>
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>学历：</label>
			<div class="controls">
				<form:select path="education" class="input-xlarge required">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('sys_ccm_degree')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>

			</div>
		</div>
			</td>

		</tr>
		<tr>
			<td colspan="2">
				<div>
					<label class="control-label">专业特长：</label>
					<div class="controls">
						<form:checkboxes path="profExpertiseList" items="${fns:getDictList('ccm_pro_spe')}" 
							itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
					</div>
				</div>
			</td>
		</tr>
		<tr>

			</td>
			<td>
		</tr>
		
		
		
		</table>
		
		
		<div style="display:none">
			<label class="control-label">人员ID：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${vCcmTeam.user.id}" labelName="user.name" labelValue="${vCcmTeam.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		
		
		<!--
		<div>
			<label class="control-label">冗余字段1：</label>
			<div class="controls">
				<form:input path="more1" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<div>
			<label class="control-label">冗余字段2：</label>
			<div class="controls">
				<form:input path="more2" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		  -->
		
		<div class="form-actions">
			<shiro:hasPermission name="org:ccmOrgTeam:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		
	</form:form>
</body>
</html>