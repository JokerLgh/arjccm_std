<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>社工签到管理</title>
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
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/worker/ccmWorkerSign/">社工签到列表</a></li> --%>
		<%-- <li class="active"><a href="${ctx}/worker/ccmWorkerSign/form?id=${ccmWorkerSign.id}">社工签到<shiro:lacksPermission name="worker:ccmWorkerSign:edit">查看</shiro:lacksPermission></a></li> --%>
	</ul>
	<form:form id="inputForm" modelAttribute="ccmWorkerSign" action="${ctx}/worker/ccmWorkerSign/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group" style="padding-top: 8px">
			<label class="control-label">人员：</label>
			<div class="controls">
				<sys:treeselect id="user" name="user.id" value="${ccmWorkerSign.user.id}" labelName="user.name" labelValue="${ccmWorkerSign.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass=""  notAllowSelectParent="true" /> 
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签到内容：</label>
			<div class="controls">
				<form:input path="content" htmlEscape="false" maxlength="64" class="input-xlarge required" readonly="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">签到类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge required" disabled="true">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('ccm_worker_sign_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">签到状态：</label>
			<div class="controls">
				<form:select path="status" class="input-xlarge required" disabled="true">
					<form:options items="${fns:getDictList('ccm_worker_sign_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div> --%>
		<div class="control-group">
			<label class="control-label">签到时间：</label>
			<div class="controls">
				<input name="signDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${ccmWorkerSign.signDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" disabled="true"/>
			</div>
		</div>
	</form:form>
</body>
</html>