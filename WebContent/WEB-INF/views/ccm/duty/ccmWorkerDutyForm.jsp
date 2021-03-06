<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>社工职责管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
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
	<ul class="nav nav-tabs"  style="display:none;">
		<%-- <li><a href="${ctx}/duty/ccmWorkerDuty/">社工职责列表</a></li> --%>
		<li class="active"><a href="${ctx}/duty/ccmWorkerDuty/form?id=${ccmWorkerDuty.id}">社工职责<shiro:hasPermission name="duty:ccmWorkerDuty:edit">${not empty ccmWorkerDuty.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="duty:ccmWorkerDuty:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="ccmWorkerDuty" action="${ctx}/duty/ccmWorkerDuty/${not empty ccmWorkerDuty.id?'update':'save'}" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="padding-top: 8px">
			<label class="control-label">标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">内容：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="30" maxlength="2000" class="input-xxlarge required"/>
				<sys:ckeditor uploadPath="/duty/ccmWorkerDuty" replace="content" height="200"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">附件：</label>
			<div class="controls">
				<form:hidden id="file" path="file" htmlEscape="false"
					maxlength="1000" class="input-xlarge" />
				<sys:ckfinder input="file" type="files"
					uploadPath="/duty/ccmWorkerDuty" selectMultiple="true" />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="duty:ccmWorkerDuty:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			${not empty ccmWorkerDuty.id?'<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> ':''}
		</div>
	</form:form>
</body>
</html>