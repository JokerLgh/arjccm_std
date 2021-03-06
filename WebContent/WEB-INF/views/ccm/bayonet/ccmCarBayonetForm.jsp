<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>车辆卡口管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(function() {
		//关闭弹框事件
		$('#btnCancel').click(function() {
			parent.layer.close(parent.layerIndex);
		})
		//$("#name").focus();
		$("#inputForm").validate({
			submitHandler : function(form) {
				/* var useType = $('#useType').val();
				if(useType === undefined || useType == null || useType == ''){
					$.jBox.tip('请确认选择卡口使用类别!');
			 		return false;
				} */
				loading('正在提交，请稍等...');
				form.submit();
			},
			errorContainer : "#messageBox",
			errorPlacement : function(error, element) {
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
	function ValidateValue(textbox) {
		var IllegalString = "[`~!#$^&*()=|{}':;',\\[\\].<>/?~！#￥……&*（）——|{}【】‘；：”“'。，、？]‘’";
		var textboxvalue = textbox.value;
		var index = textboxvalue.length - 1;
	
		var s = textbox.value.charAt(index);
	
		if (IllegalString.indexOf(s) >= 0) {
			s = textboxvalue.substring(0, index);
			textbox.value = s;
		}
 
	}
</script>
</head>
<body>
	<br />
	<form:form id="inputForm" modelAttribute="ccmCarBayonet"
		action="${ctx}/bayonet/ccmCarBayonet/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table>
			<tr>
				<td><label class="control-label">卡口名称：</label>
					<div class="controls">
						<form:input path="bayonetName" htmlEscape="false" maxlength="64"
							class="input-xlarge required" onkeyup = "ValidateValue(this)" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
				<td><label class="control-label">使用类别：</label>
					<div class="controls">
						<form:select path="useType" class="input-xlarge required">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('bayonet_use_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">组织编号：</label>
					<div class="controls">
						<form:input path="organizationNumber" htmlEscape="false"
							maxlength="64" class="input-xlarge required number" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
				<td><label class="control-label">卡警编号：</label>
					<div class="controls">
						<form:input path="policeNumber" htmlEscape="false" maxlength="64"
							class="input-xlarge required number" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">国标码：</label>
					<div class="controls">
						<form:input path="gbCode" htmlEscape="false" maxlength="64"
							class="input-xlarge required number" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
				<td><label class="control-label">标记地址：</label>
					<div class="controls">
						<form:input path="address" htmlEscape="false" maxlength="255"
							class="input-xlarge " />
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">前端类型：</label>
					<div class="controls">
						<form:select path="frontType" class="input-xlarge">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('bayonet_front_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
					</div></td>
				<td><label class="control-label">卡口类型：</label>
					<div class="controls">
						<form:select path="bayonetType" class="input-xlarge">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('ccm_bayonet_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">卡口模式：</label>
					<div class="controls">
						<form:select path="bayonetPattern" class="input-xlarge">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('ccm_bayonet_pattern')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
					</div></td>
				<td><label class="control-label">是否一图多车：</label>
					<div class="controls">
						<form:select path="isOneToMore" class="input-xlarge">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('yes_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
					</div></td>
			</tr>
			<tr>
				<td colspan="2"><label class="control-label">备注信息：</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4"
							maxlength="255" class="input-xlarge " />
					</div></td>
			</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="bayonet:ccmCarBayonet:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;
				<input id="btnCancel" class="btn btn-danger" type="button"
					value="关闭" />&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>