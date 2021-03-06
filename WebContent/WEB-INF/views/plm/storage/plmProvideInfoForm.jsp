<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>供应商信息管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/common/zztable.css" type="text/css"
	rel="stylesheet">
<!-- 表格试表单css -->
<link href="${ctxStatic}/common/zzformtable.css" type="text/css"
	rel="stylesheet">
<script type="text/javascript" src="${ctxStatic}/plm/storage/storageValidate.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$('#btnSubmit').click(function(){
					$('#inputForm').submit();
				});
				$("#inputForm")
						.validate(
								{
									rules: {
										name: {
											required: true,
											maxlength : 128,
											special_str: $("#name").val()    //调用自定义验证
						                },
						                shortName: {
											required: true,
											maxlength : 128,
											special_str: $("#shortName").val()    //调用自定义验证
						                },
						                bankAccounts: {
											maxlength : 32,
											bank: $("#bankAccounts").val()    //调用自定义验证
						                },
						                openBank: {
											maxlength : 64,
											special_str: $("#openBank").val()    //调用自定义验证
						                }
						            },
						            
						            messages: {
						            	name: {
						            		maxlength: "最大长度128"
						                },
						                shortName: {
						            		maxlength: "最大长度128"
						                },
						                bankAccounts: {
						            		maxlength: "最大长度32"
						                },
						                openBank: {
						            		maxlength: "最大长度64"
						                }
						            },
						            
									submitHandler : function(form) {
										$("#btnSubmit").attr("disabled", true);
					loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#btnSubmit").removeAttr('disabled');
					$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
			});
	
	function saveForm() {
		var area = $("#areaId").val();
		var html1 = '<label for="" class="error">必选字段 <label>';
		//alert(officeId.length);
		if (area.length != 0) {
			$("#show1").html("");
		} else {
			$("#show1").html(html1);
		}

		if (area.length != 0) {
			$("#inputForm").submit();
		}

	}
</script>
	<%--引入文本框外部样式--%>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/storage/plmProvideInfo/">供应商信息列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head"
			href="${ctx}/storage/plmProvideInfo/form?id=${plmProvideInfo.id}">供应商信息<shiro:hasPermission
					name="storage:plmProvideInfo:edit">${not empty plmProvideInfo.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="storage:plmProvideInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="plmProvideInfo"
		action="${ctx}/storage/plmProvideInfo/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group head_Space">
			<h2>供应商信息</h2>
		</div>
		<table id="table" class="table   table-condensed first_table">
			<tr>
				<td class="trtop"><span class="help-inline"><font
						color="red">*</font> </span>供应商全称
				</td>
				<td ><form:input path="name" htmlEscape="false"
						maxlength="128" class="input-xlarge required" /></td>
				<td class="trtop"><span class="help-inline"><font
						color="red">*</font> </span>供应商简称
				</td>
				<td ><form:input path="shortName" htmlEscape="false"
						maxlength="128" class="input-xlarge required" /></td>
			</tr>
			<tr>
				<td class="trtop">供应商类型</td>
				<td ><form:select path="proId" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('plm_provide_type')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select></td>
				<td class="trtop"><span class="help-inline"><font
						color="red">*</font> </span>地区
				</td>
				<td ><form:input path="area" htmlEscape="false"
						maxlength="128" class="input-xlarge required" /></td>
			</tr>
			<tr>
				<td class="trtop"><span class="help-inline"><font
						color="red">*</font> </span>负责人
				</td>
				<td ><form:input path="principal" htmlEscape="false"
						maxlength="128" class="input-xlarge required" /></td>
				<td class="trtop"><span class="help-inline"><font
						color="red">*</font> </span>信用等级
				</td>
				<td><form:select path="creditClass"
						class="input-xlarge required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('plm_credit_level')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select></td>
			</tr>
			<tr>
				<td class="trtop">联系电话</td>
				<td ><form:input path="phoneOne" htmlEscape="false"
						maxlength="11" class="input-xlarge phone" /></td>
				<td class="trtop">紧急联系电话</td>
				<td ><form:input path="phoneTwo" htmlEscape="false"
						maxlength="11" class="input-xlarge phone" /></td>
			</tr>
			<tr>
				<td class="trtop">移动电话</td>
				<td ><form:input path="mobilePhone"
						htmlEscape="false" maxlength="14" class="input-xlarge mobile" /></td>
				<td class="trtop">银行帐号</td>
				<td ><form:input path="bankAccounts"
						htmlEscape="false" maxlength="19" class="input-xlarge " /></td>
			</tr>
			<tr>
				<td class="trtop">开户银行</td>
				<td ><form:input path="openBank" htmlEscape="false"
						maxlength="19" class="input-xlarge " /></td>
				<td class="trtop">电子邮件</td>
				<td ><form:input path="email" htmlEscape="false"
						maxlength="32" class="input-xlarge email" /></td>
			</tr>
			<tr>
				<td class="trtop">网址</td>
				<td ><form:input path="web" htmlEscape="false"
						maxlength="64" class="input-xlarge url" /></td>
				<td class="trtop">传真号码</td>
				<td ><form:input path="faxes" htmlEscape="false"
						maxlength="32" class="input-xlarge simplePhone" /></td>
			</tr>
			<tr>
				<td class="trtop">联系人</td>
				<td ><form:input path="emp" htmlEscape="false"
						maxlength="128" class="input-xlarge required" /></td>
				<td class="trtop">行业类别</td>
				<td><form:select path="calling" class="input-xlarge ">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('plm_calling_type')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select></td>
			</tr>
			<tr>
				<td class="trtop">备注</td>
				<td colspan="4"><form:textarea path="remarks"
						htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge " /></td>
			</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="storage:plmProvideInfo:edit">
				<a id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="button" href="javascript:;"><i ></i>保存</a>&nbsp;</shiro:hasPermission>
				<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i ></i>返回</a>
		</div>
	</form:form>
</body>
</html>