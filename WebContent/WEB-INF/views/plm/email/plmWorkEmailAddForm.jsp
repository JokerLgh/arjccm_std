<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>工作日志管理</title>
<meta name="decorator" content="default" />
<link rel="stylesheet" href="${ctxStatic}/plm/email/emailReceive.css">
<script type="text/javascript"
	src="${ctxStatic}/plm/email/plmWorkEmailinfo.js"></script>
<style type="text/css">
.form-horizontal .control-label {
    padding-top: 5px;
}
.padding {
    padding-bottom: 5px; 
}
</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<c:if test="${plmWorkEmail.status eq '0'}" var="condition">
			<li><a href="${ctx}/email/plmWorkEmail/?status=0">已发送</a></li>
		</c:if>
		<c:if test="${plmWorkEmail.status eq '1'}">
			<li><a href="${ctx}/email/plmWorkEmail/?status=1">草稿箱</a></li>
		</c:if>
			<li class="active" style="width: 140px"><a class="nav-head"
				href="${ctx}/email/plmWorkEmail/form?id=${plmWorkEmail.id}">邮件<shiro:hasPermission
						name="email:plmWorkEmail:edit">${not empty plmWorkEmail.id ?'编辑':'添加'}</shiro:hasPermission>
					<shiro:lacksPermission name="email:plmWorkEmail:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<br />
	<form:form id="inputForm" modelAttribute="plmWorkEmail"
		action="${ctx}/email/plmWorkEmail/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
 		<form:hidden path="status" />
 		<form:hidden path="isC" />
 		<form:hidden path="isM" />
		<sys:message content="${message}" />
		<div class="control-group blue" style="height: 29px;">
			<%--<c:if test="${plmWorkEmail.status ne '0'}">
				<a id="btnApply" class="btn" href="javascript:;" onclick="$('#status').val('0');saveForm()"><i class="icon-envelope"></i>发送</a>&nbsp;
				<a id="btnSubmit" class="btn" href="javascript:;" onclick="$('#status').val('1')"><i class="icon-edit"></i>存草稿</a>&nbsp;
			</c:if>
			<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i class="icon-reply"></i>返回</a>--%>
		</div>		
		<div class="control-group gray">
			<label class="blackLabel control-label">收件人：</label>
			<div class="controls">
				<sys:treeselect id="plmWorkEmailSRead" name="plmWorkEmailSReadIds"
					value="${plmWorkEmail.plmWorkEmailSReadIds}" isAll="true"
					labelName="plmWorkEmailSReadNames"
					labelValue="${plmWorkEmail.plmWorkEmailSReadNames}" title="用户"
					url="/sys/office/treeData?type=3" 
					cssClass="input-xxlarge" notAllowSelectParent="true"
					checked="true" />
					<label id="show1"></label>
				<!-- <span class="help-inline"><font color="red" id="show1"></font> </span> -->
			</div>
 			<div class="controls">
				<a id="addC" title="什么是抄送：同时将这一封邮件发送给其他联系人">添加抄送</a>
				<a id="subC" title="什么是抄送：同时将这一封邮件发送给其他联系人">删除抄送</a>
				&nbsp;&nbsp;
				<a id="addM" title="什么是密送：同时将这一封邮件发送给其他联系人，但收件人及抄送人不会看到密送人。">添加密送</a>
				<a id="subM" title="什么是密送：同时将这一封邮件发送给其他联系人，但收件人及抄送人不会看到密送人。">删除密送</a>
			</div>
		</div>
		<div id="divC" class="control-group gray">
			<label class="blackLabel control-label">抄送人：</label>
			<div class="controls">
				<sys:treeselect id="plmWorkEmailCRead" name="plmWorkEmailCReadIds"
					value="${plmWorkEmail.plmWorkEmailCReadIds}" isAll="true"
					labelName="oaNotifyRecordNames"
					labelValue="${plmWorkEmail.plmWorkEmailCReadNames}" title="用户"
					url="/sys/office/treeData?type=3"
					cssClass="input-xxlarge" notAllowSelectParent="true"
					checked="true" />
				<!-- <span class="help-inline"><font color="red">*</font> </span> -->
			</div>
		</div>
		<div id="divM" class="control-group gray">
			<label class="blackLabel control-label">密送人：</label>
			<div class="controls">
				<sys:treeselect id="plmWorkEmailMRead" name="plmWorkEmailMReadIds"
					value="${plmWorkEmail.plmWorkEmailMReadIds}" isAll="true"
					labelName="oaNotifyRecordNames"
					labelValue="${plmWorkEmail.plmWorkEmailMReadNames}" title="用户"
					url="/sys/office/treeData?type=3"
					cssClass="input-xxlarge required" notAllowSelectParent="true"
					checked="true" />
			</div>
		</div>				
		<div class="control-group gray">
			<label class="blackLabel control-label">主&nbsp;&nbsp;&nbsp;&nbsp;题：</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="100"
					class="input-xxlarge required" />
			</div>
		</div>
		<div class="control-group gray">
			<label class="blackLabel control-label">内&nbsp;&nbsp;&nbsp;&nbsp;容：</label>
			<div class="controls">
				<div style="width: 960px">
				<form:textarea id="content" htmlEscape="true" path="content" rows="4" maxlength="1000" class="input-xxlarge"/>
				<sys:ckeditor replace="content" uploadPath="/plm/email" />
				</div>
			</div>
		</div>
		<div class="control-group gray">
			<label class="blackLabel control-label ">附件：</label>
			<div class="controls padding">
				<form:hidden id="files" path="files" htmlEscape="false"
					maxlength="1000" class="input-xlarge" />
				<sys:ckfinder input="files" type="files"
					uploadPath="/email/plmWorkEmail" selectMultiple="true" />
			</div>
		</div>


		<div class="control-group blue">
			<c:if test="${plmWorkEmail.status ne '0'}">
				<a id="btnApply" class="btn" href="javascript:;" onclick="$('#status').val('0');saveForm()"><i class="icon-envelope"></i>发送</a>&nbsp;
				<a id="btnSubmit" class="btn" href="javascript:;" onclick="$('#status').val('1');saveForm()"><i class="icon-edit"></i>存草稿</a>&nbsp;
			</c:if>
			<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i class="icon-reply"></i>返回</a>
		</div>
	</form:form>
</body>
</html>