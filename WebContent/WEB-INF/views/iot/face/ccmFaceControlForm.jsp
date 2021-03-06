<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人脸布控记录管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(
			function() {
				if("${ccmFaceControl.listId}"!=null&&"${ccmFaceControl.listId}"!=""){
					var libraryList = "${ccmFaceControl.listId}".split(",");
					$("#librarySelectList").val(libraryList).select2();
				}
				if("${ccmFaceControl.machine}"!=null&&"${ccmFaceControl.machine}"!=""){
					var machineList = "${ccmFaceControl.machine}".split(",");
					$('#captureMachineList').val(machineList).select2();
				}
				//关闭弹框事件
				$('#btnCancel').click(function() {
					parent.layer.close(parent.layerIndex);
				})

				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
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
</script>
</head>
<body>
	<br />
	<form:form id="inputForm" modelAttribute="ccmFaceControl"
		action="${ctx}/face/ccmFaceControl/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />

		<table>
			<tr>
				<td><label class="control-label">布控名称：</label>
					<div class="controls">
						<form:input path="name" htmlEscape="false" maxlength="255"
							class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>

				<td><label class="control-label">布控等级：</label>
					<div class="controls">
						<form:select path="controllerLevel" class="input-xlarge">
							<form:option value="" label="请选择" />
							<form:options items="${fns:getDictList('ccm_control_level')}"
								itemLabel="label" itemValue="value" htmlEscape="false"
								class="required" />
						</form:select>
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>


			</tr>
			<tr>
				<td><label class="control-label">布控开始时间：</label>
					<div class="controls">
						<input name="startTime" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${ccmFaceControl.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					</div></td>
				<td><label class="control-label">布控结束时间：</label>
					<div class="controls">
						<input name="endTime" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate "
							value="<fmt:formatDate value="${ccmFaceControl.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">布控名单库：</label>
					<div class="controls">
						<select data-placeholder="选择一个或多个名单库" style="width: 90%;" id="librarySelectList" name="librarySelectList" class="required" multiple tabindex="4">
							<option value=""></option>
							<c:forEach items="${libraryList}" var="people">
								<option value="${people.id}">${people.name}</option>
							</c:forEach>
						</select>
<%--						<form:input path="listId" htmlEscape="false" maxlength="64"--%>
<%--							class="input-xlarge required" />--%>
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
				<td><label class="control-label">布控抓拍机：</label>
					<div class="controls">
						<select data-placeholder="选择一个或多个名单库" style="width: 90%;" id="captureMachineList" name="captureMachineList" class="required" multiple tabindex="4">
							<option value=""></option>
							<c:forEach items="${grabberList}" var="grabber">
								<option value="${grabber.id}">${grabber.grabberName}</option>
							</c:forEach>
						</select>
<%--						<form:input path="machine" htmlEscape="false" maxlength="255"--%>
<%--							class="input-xlarge required" />--%>
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
			</tr>
			<tr>
				<td colspan="2"><label class="control-label">布控原因：</label>
					<div class="controls">
						<form:textarea path="controllerReason" htmlEscape="false" rows="4"
							maxlength="255" class="input-xlarge " />
					</div></td>
			</tr>
		</table>

		<div class="form-actions">
			<shiro:hasPermission name="face:ccmFaceControl:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;
				<input id="btnCancel" class="btn btn-danger" type="button"
					value="关闭" />&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>