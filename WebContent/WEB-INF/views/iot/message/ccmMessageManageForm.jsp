<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>消息管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			//关闭弹框事件
			$('#btnCancel').click(function() {
				parent.layer.close(parent.layerIndex);
			})
			//$("#name").focus();
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
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/message/ccmMessageManage/">消息管理列表</a></li>
		<li class="active"><a href="${ctx}/message/ccmMessageManage/form?id=${ccmMessageManage.id}">消息管理<shiro:hasPermission name="message:ccmMessageManage:edit">${not empty ccmMessageManage.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="message:ccmMessageManage:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%><br/>
	<form:form id="inputForm" modelAttribute="ccmMessageManage" action="${ctx}/message/ccmMessageManage/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="display:none;">
			<label class="control-label">关联设备id：</label>
			<div class="controls">
				<form:input path="equipmentId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">关联设备编号：</label>
			<div class="controls">
				<form:input path="equipmentNum" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">消息：</label>
			<div class="controls">
				<form:input path="message" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group" style="display:none;">
			<label class="control-label">发送状态：</label>
			<div class="controls">
				<form:select path="sendState" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_message_send_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="message:ccmMessageManage:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn btn-danger" type="button" value="关 闭"/>
		</div>
	</form:form>
</body>
</html>