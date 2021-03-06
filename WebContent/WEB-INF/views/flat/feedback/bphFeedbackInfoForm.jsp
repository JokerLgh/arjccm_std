<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>反馈信息管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/alarm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
				$('#btnSubmit').click(function(){
				$('#inputForm').submit();
			});
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
		<li><a href="${ctx}/feedback/bphFeedbackInfo/">数据列表</a></li>
		<li class="active"><a href="${ctx}/feedback/bphFeedbackInfo/form?id=${bphFeedbackInfo.id}">数据<shiro:hasPermission name="feedback:bphFeedbackInfo:edit">${not empty bphFeedbackInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="feedback:bphFeedbackInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="bphFeedbackInfo" action="${ctx}/feedback/bphFeedbackInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">反馈信息ID：</label>
			<div class="controls">
				<form:input path="feedbackId" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作ID：</label>
			<div class="controls">
				<form:input path="actionId" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈内容标题：</label>
			<div class="controls">
				<form:input path="feedbackTitle" htmlEscape="false" maxlength="80" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">反馈内容类型 0 文字 1 图片 2 音频 3 视频：</label>
			<div class="controls">
				<form:checkboxes path="feedbackType" items="${fns:getDictList('')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="feedback:bphFeedbackInfo:edit">
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="保存"/> -->
			<a id="btnSubmit" class="btn btn-primary" href="javascript:;"><i class="icon-ok"></i>保存</a>
			&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
			<a id="btnCancel" class="btn btn-back" href="javascript:;"  onclick="history.go(-1)"><i class="icon-reply"></i>返回</a>
		</div>
	</form:form>
</body>
</html>