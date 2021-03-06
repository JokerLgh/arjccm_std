<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>数字化预案管理</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/alarm.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#planName").on("input",function(e){
				var planName = e.delegateTarget.value;
				if(planName == "" || planName == null || planName === undefined){
					$("#nameLabel").hide();
					$("#nameLabel").html("");
					return;
				}
				var oldPlanName = $("#oldPlanName").val();
				if(planName == oldPlanName){
					$("#nameLabel").hide();
					$("#nameLabel").html("");
					return;
				}
				$.post("${ctx}/planinfo/bphPlanInfo/checkPlanName",{'planName':planName},function(data) {
					if(data == "false"){
						$("#nameLabel").show();
						$("#nameLabel").html("该预案名已存在，请重新输入。");
					}else if(data == "true"){
						$("#nameLabel").hide();
						$("#nameLabel").html("");
					}
				});
			});
			$("#btnSubmit").click(function(){
				var nameLabel = $("#nameLabel").text();
				if(nameLabel != null && nameLabel != "" && nameLabel !== undefined){
					return;
				}
				$("#inputForm").submit();
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
	<%--引入文本框外部样式--%>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/planinfo/bphPlanInfo/">数据列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/planinfo/bphPlanInfo/form?id=${bphPlanInfo.id}">数据<shiro:hasPermission name="planinfo:bphPlanInfo:edit">${not empty bphPlanInfo.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="planinfo:bphPlanInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="bphPlanInfo" action="${ctx}/planinfo/bphPlanInfo/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group head_Space" >
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>预案名称：</label>
			<div class="controls">
				<input id="oldPlanName" name="oldPlanName" type="hidden" value="${bphPlanInfo.name}">
				<form:input path="name" htmlEscape="false" maxlength="80" class="input-xlarge required" id="planName"/>
				<label class="error" id="nameLabel" style="display:none;"></label>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">警情类型：</label>
			<div class="controls">
				<form:select path="typeCode" class="input-xlarge ">
					<form:options items="${fns:getDictList('bph_alarm_info_typecode')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">警情级别：</label>
			<div class="controls">
				<form:select path="isImportant" class="input-xlarge ">
					<form:options items="${fns:getDictList('bph_plan_alarm_level')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		
		<div class="control-group">
			<label class="control-label">预案描述：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="400" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="planinfo:bphPlanInfo:edit">
			<!-- <input id="btnSubmit" class="btn btn-primary" type="button" value="保存"/> -->
			<a id="btnSubmit" class="btn btn-primary" href="javascript:;"><i ></i>保存</a>
			&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
			<a id="btnCancel" class="btn btn-back" href="javascript:;"  onclick="history.go(-1)"><i ></i>返回</a>
		</div>
	</form:form>
<%--	<!-- 保存提交 -->
	<script type="text/javascript">

	</script>--%>
</body>
</html>