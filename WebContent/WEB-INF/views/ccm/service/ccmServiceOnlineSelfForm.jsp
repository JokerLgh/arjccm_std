<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>在线办事管理</title>
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
	<style type="text/css">
		td{padding:8px;border:1px dashed #CCCCCC }
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/service/ccmServiceOnline/self">数据列表</a></li>
		<li class="active"><a href="${ctx}/service/ccmServiceOnline/selfform?id=${ccmServiceOnline.id}">数据<shiro:hasPermission name="service:ccmServiceOnline:edit">${not empty ccmServiceOnline.id?'查看':'添加'}</shiro:hasPermission><shiro:lacksPermission name="service:ccmServiceOnline:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ccmServiceOnline" action="${ctx}/service/ccmServiceOnline/selfsave" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<c:if test="${empty ccmServiceOnline.id }">
			<input type="hidden" name="status" value="01">
		</c:if>
		<sys:message content="${message}"/>		
		<h4>事项：</h4>
		<br>
		<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
		<tr>
		<td>
			<label class="control-label">事项分类：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_service_online_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">申请人：</label>
			<div class="controls">
				<form:input path="applicant" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">公民身份号码：</label>
			<div class="controls">
				<form:input path="ident" htmlEscape="false" maxlength="18" class="input-xlarge card"/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">联系方式：</label>
			<div class="controls">
				<form:input path="telephone" htmlEscape="false" maxlength="50" class="input-xlarge phone"/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">相关人：</label>
			<div class="controls">
				<form:input path="relevantPerson" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">问题描述：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge " />
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">提交资料：</label>
			<div class="controls">
				<form:hidden id="file" path="file"  htmlEscape="false" maxlength="1000" class="input-xxlarge"/>
				<sys:ckfinder input="file" type="files" uploadPath="/service/serviceOnline" selectMultiple="false"/>
			</div>
		</td>
		</tr>
		</table><br>
		<c:if test="${not empty ccmServiceOnline.id }">
		<h4>审核：</h4>
		<br>
		<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
		<tr>
		<td>
				<label class="control-label">审核状态：</label>
				<div class="controls">
					${fns:getDictLabel(ccmServiceOnline.status, 'ccm_service_online_handle', '')}
				</div>
		</td>
		</tr>
		<c:if test="${ ccmServiceOnline.status ne '01' }">
		<tr>
		<td>
			<label class="control-label">审核人：</label>
			<div class="controls">
				<form:input path="reviewer" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">文字回复：</label>
			<div class="controls">
				<form:textarea path="content" htmlEscape="false" rows="4" maxlength="1000" class="input-xxlarge "/>
			</div>
		</td>
		</tr>
		<tr>
		<td>
			<label class="control-label">相关附件：</label>
			<div class="controls">
				<form:hidden id="replyFile" path="replyFile"  htmlEscape="false" maxlength="1000" class="input-xxlarge"/>
				<sys:ckfinder input="replyFile" type="files" uploadPath="/service/serviceOnline" selectMultiple="false"/>
			</div>
		</td>
		<td>
		</c:if>
		</table>
		</c:if>
		<%-- <div class="control-group">
			<label class="control-label">扩展1：</label>
			<div class="controls">
				<form:input path="extend1" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">扩展2：</label>
			<div class="controls">
				<form:input path="extend2" htmlEscape="false" maxlength="256" class="input-xlarge "/>
			</div>
		</div> 
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>--%>
		<div class="form-actions">
			<c:if test="${empty ccmServiceOnline.id }"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</c:if>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>