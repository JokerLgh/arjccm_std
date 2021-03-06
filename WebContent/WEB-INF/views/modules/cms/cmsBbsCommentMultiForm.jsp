<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>网上论坛多级评论管理</title>
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
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/cms/cmsBbsCommentMulti/">网上论坛多级评论列表</a></li>
		<li class="active"><a href="${ctx}/cms/cmsBbsCommentMulti/form?id=${cmsBbsCommentMulti.id}">网上论坛多级评论<shiro:hasPermission name="cms:cmsBbsCommentMulti:edit">${not empty cmsBbsCommentMulti.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="cms:cmsBbsCommentMulti:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="cmsBbsCommentMulti" action="${ctx}/cms/cmsBbsCommentMulti/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">一级评论id：</label>
			<div class="controls">
				<form:input path="commentId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="cms:cmsBbsCommentMulti:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>