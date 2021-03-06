<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>风险事件管理</title>
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
		<li><a href="${ctx}/report/riskIncident/">风险事件列表</a></li>
		<li class="active"><a href="${ctx}/report/riskIncident/form?id=${riskIncident.id}">风险事件<shiro:hasPermission name="report:riskIncident:edit">${not empty riskIncident.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="report:riskIncident:edit">查看</shiro:lacksPermission></a></li>
		<c:if test="${not empty riskIncident.id}">
			<li><a href="${ctx}/log/ccmLogTail/formPro?relevance_id=${riskIncident.id}&relevance_table=risk_incident">跟踪信息<shiro:hasPermission 
				name="log:ccmLogTail:edit">${not empty ccmLogTail.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="log:ccmLogTail:edit">查看</shiro:lacksPermission></a>
			</li>	
		</c:if>	
	</ul><br/>
	<form:form id="inputForm" modelAttribute="riskIncident" action="${ctx}/report/riskIncident/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<div class="control-group">
			<label class="control-label">风险名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">来源：</label>
			<div class="controls">
				<form:input path="source" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">所属重大事项：</label>
			<div class="controls">
				<sys:treeselect id="riskEventGreat" name="riskEventGreat.id" value="${riskIncident.riskEventGreat.id}" labelName="riskEventGreat.name" labelValue="${riskIncident.riskEventGreat.name}"
					title="重大事项" url="/report/riskIncident/treeData" cssClass="" allowClear="true" notAllowSelectParent="true" cssStyle="width: 150px"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风险类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('risk_risk_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">风险摘要：</label>
			<div class="controls">
				<form:textarea path="description" htmlEscape="false" rows="3" maxlength="256" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">对策研究：</label>
			<div class="controls">
				<form:textarea path="countermeasure" htmlEscape="false" rows="5" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">实施控制：</label>
			<div class="controls">
				<form:textarea path="implement" htmlEscape="false" rows="5" maxlength="1000" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">重要程度：</label>
			<div class="controls">
				<form:select path="importance" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_conc_exte')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">紧急程度：</label>
			<div class="controls">
				<form:select path="urgency" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_conc_exte')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提出人：</label>
			<div class="controls">
				<form:input path="putMan" htmlEscape="false" maxlength="64" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">提出时间：</label>
			<div class="controls">
				<input name="putTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${riskIncident.putTime}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处理状态：</label>
			<div class="controls">
				<form:select path="disposeType" class="input-xlarge ">
					<form:option value="" label=""/>
					<form:options items="${fns:getDictList('ccm_event_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">跟踪记录信息：</label>
			<div class="controls">
				<c:forEach items="${ccmLogTailList}" var="logList">
					<li style="list-style-type: none;">
						<a href="${ctx}/log/ccmLogTail/formPro?id=${logList.id}">
							${logList.tailPerson}&nbsp; 于&nbsp; 
							<fmt:formatDate value="${logList.updateDate}" pattern="yyyy-MM-dd HH:mm:ss" />&nbsp; 跟踪记录信息
						</a>
					</li>
				</c:forEach>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="report:riskIncident:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
	</form:form>
</body>
</html>