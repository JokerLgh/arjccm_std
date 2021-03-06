<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>缴费信息</title>
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
		<li><a href="${ctx}/payment/pbsPayment/">缴费信息</a></li>
		<li class="active"><a href="${ctx}/payment/pbsPayment/form?id=${pbsPayment.id}">缴费信息<shiro:hasPermission name="payment:pbsPayment:edit">${not empty pbsPayment.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="payment:pbsPayment:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="pbsPayment" action="${ctx}/payment/pbsPayment/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label"><font color="red">*&nbsp;</font>学员：</label>
			<div class="controls">
				<sys:treeselect id="pbspartymem" name="pbspartymem"
					value="${pbsPayment.pbspartymem.id}" labelName="mastername"
					labelValue="${pbsPayment.pbspartymem.SName}" title="学员"
					url="/sys/pbsOffice/treeData?type=4" cssClass="required" allowClear="true"
					notAllowSelectParent="true" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><font color="red">*&nbsp;</font>缴费金额：</label>
			<div class="controls">
				<form:input path="money" htmlEscape="false" maxlength="11" class="input-xlarge  digits required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费时间：</label>
			<div class="controls">
				<input name="paymentTime" type="text" readonly="readonly" maxlength="20" class="input-xlarge Wdate "
					value="<fmt:formatDate value="${pbsPayment.paymentTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">缴费方式：</label>
			<div class="controls">
				<form:select path="sType" class="input-xlarge required">
					<form:options items="${fns:getDictList('pay_type')}" itemLabel="label"
						itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4"
					maxlength="255" class="input-xlarge " />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="payment:pbsPayment:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>