<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>聊天室管理</title>
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
		<li><a href="${ctx}/chat/pbsChatroom/">聊天室列表</a></li>
		<li class="active"><a href="${ctx}/chat/pbsChatroom/form?id=${pbsChatroom.id}">聊天室<shiro:hasPermission name="chat:pbsChatroom:edit">${not empty pbsChatroom.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="chat:pbsChatroom:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pbsChatroom" action="${ctx}/chat/pbsChatroom/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><font color="red">*&nbsp;</font>聊天室：</label>
			<div class="controls">
				<form:input path="sTitle" htmlEscape="false" maxlength="200" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*&nbsp;</font>聊天室管理员：</label>
			<div class="controls">
				<sys:treeselect id="sMater" name="sMater" value="${pbsChatroom.sMater.id}" labelName="sMater" labelValue="${pbsChatroom.sMater.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="required" allowClear="true" notAllowSelectParent="true"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*&nbsp;</font>聊天室创建时间：</label>
			<div class="controls">
				<input name="dtCreatetime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate required "
					value="<fmt:formatDate value="${pbsChatroom.dtCreatetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">聊天室关闭时间：</label>
			<div class="controls">
				<input name="dtClosetime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${pbsChatroom.dtClosetime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">聊天室人员数量：</label>
			<div class="controls">
				<form:input path="iCurrntmem" htmlEscape="false" maxlength="11" class="input-xlarge  digits "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述信息：</label>
			<div class="controls">
				<form:input path="sDescription" htmlEscape="false" maxlength="100" class="input-xlarge "/>
			</div>
		</div>
		<%-- <div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div> --%>
		<div class="form-actions">
			<shiro:hasPermission name="chat:pbsChatroom:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>