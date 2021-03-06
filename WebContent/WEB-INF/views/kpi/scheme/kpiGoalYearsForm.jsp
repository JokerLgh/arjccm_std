<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>部门年度目标管理</title>
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
		function saveForm(){
			var officeId = $("#officeId").val();
			var html1 = '<label for="" class="error">必填信息 <label>';
			//alert(officeId.length);
			if(officeId.length!=0){
				$("#show1").html("*");
			}else{
				$("#show1").html(html1);
			}
		}
	</script>
	<%--引入文本框外部样式--%>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/scheme/kpiGoalYears/">数据列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/scheme/kpiGoalYears/form?id=${kpiGoalYears.id}">数据<shiro:hasPermission name="scheme:kpiGoalYears:edit">${not empty kpiGoalYears.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="scheme:kpiGoalYears:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="kpiGoalYears" action="${ctx}/scheme/kpiGoalYears/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group head_Space" >
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>部门：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${kpiGoalYears.office.id}" labelName="office.name" labelValue="${kpiGoalYears.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="fasle"/>
				<span class="help-inline"><font color="red" id="show1"></font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>年度：</label>
			<div class="controls">
				<input name="years" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					   value="${kpiGoalYears.years}"
					   onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>目标：</label>
			<div class="controls">
				<form:textarea path="goal" htmlEscape="false" rows="4" maxlength="256" class="input-xxlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">目标计划：</label>
			<div class="controls">
				<form:textarea path="goalPlan" htmlEscape="false" rows="8" maxlength="512" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">完成进度：</label>
			<div class="controls">
				<form:textarea path="progress" htmlEscape="false" rows="4" maxlength="256" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">是否完成：</label>
			<div class="controls">
				<form:radiobuttons path="finished" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">考核结论：</label>
			<div class="controls">
				<form:textarea path="conclusion" htmlEscape="false" rows="4" maxlength="256" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="scheme:kpiGoalYears:edit"><input id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>