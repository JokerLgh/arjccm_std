<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>门户字典管理</title>
<meta name="decorator" content="default" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$('#btnSubmit').click(function() {
					$('#inputForm').submit();
				});
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
<%--引入文本框外部样式--%>
<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/home/plmPortalDict/">门户字典列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head"
			href="${ctx}/home/plmPortalDict/form?id=${plmPortalDict.id}">门户字典<shiro:hasPermission
					name="home:plmPortalDict:edit">${not empty plmPortalDict.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="home:plmPortalDict:edit">查看</shiro:lacksPermission></a></li>
	</ul>

	<form:form id="inputForm" modelAttribute="plmPortalDict"
		action="${ctx}/home/plmPortalDict/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<div class="control-group head_Space">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>标题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="256"
					class="input-xlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">更多链接：</label>
			<div class="controls">
				<form:input path="connect" htmlEscape="false" maxlength="256"
					class="input-xlarge " />
				<span class="help-inline"> 更多链接为空时，窗口将显示“更多”</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>内容链接：</label>
			<div class="controls">

				<form:input path="content" htmlEscape="false" maxlength="256"
					class="input-xlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font></span>内容行数：</label>
			<div class="controls">
				<form:input path="line" htmlEscape="false" maxlength="2"
					class="input-xlarge required" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">平台类型：</label>
			<div class="controls">
				<form:select path="extend1" class="input-xlarge">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('index_sys_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false"
						maxlength="2" class="input-medium" />
				</form:select>
			</div>
		</div>

		<div class="form-actions">
			<shiro:hasPermission name="home:plmPortalDict:edit">
				<a id="btnSubmit" class="btn btn-primary"  href="javascript:;"><i
					></i>保存</a>&nbsp;</shiro:hasPermission>
			<a id="btnCancel" class="btn"  href="javascript:;"
				onclick="history.go(-1)"><i ></i>返回</a>
		</div>
	</form:form>
</body>
</html>