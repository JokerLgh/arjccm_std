<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>事件处理管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<script charset="utf-8" type="text/javascript" src="${ctxStatic}/ccm/validator/validatorBaseList.js"></script>
<script type="text/javascript">
	$(document).ready(
			function() {
				var hide=${ccmEventCasedeal.handleStatus};
				if(hide=="01"||hide=="04"||hide=="05"){//已拒绝或者已处理
					$('.hide').show();
					$('.form-actions').hide();
					$('.help-inline').hide();
					$('.input-xlarge').attr("readonly","readonly");
					$('.input-xxlarge').attr("readonly","readonly");
					$('.displayedbuttons').attr("disabled","disabled");
				}
			}
	)
	$(function () {

		//获取url中的参数
		function getUrlParam(name) {
			var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
			var r = window.location.search.substr(1).match(reg);  //匹配目标参数
			if (r != null) return unescape(r[2]); return null; //返回参数值
		}


	    <%--$("input[name='manageType']").click(function () {--%>
	    <%--	var manageTypeVal = $(this).val();--%>
   	 <%--		var select = document.getElementById("handleStatus");--%>
   	 <%--		var options = select.options;--%>
        <%--	var option = ${ccmEventCasedeal.handleStatus};--%>
        <%--	for(var i = 0;i < options.length;i++){--%>
		<%--        if (manageTypeVal == "1") {--%>
       	<%-- 			if(options[i].value == '03'){--%>
	    <%--   	 			$("#handleStatus option:selected").attr("selected",false); --%>
	    <%--   	 			options[3].selected=true;--%>
	    <%--   	 			$('.select2-chosen').text(options[3].text);--%>
       	<%-- 			}--%>
	    <%--    	} else{--%>
	    <%--    		if(option == options[i].value){--%>
	    <%--    			options[i].selected=true;--%>
	    <%--    			$('.select2-chosen').text(options[i].text);--%>
       	<%--			}--%>
        <%--		}--%>
	    <%--    }--%>
	    <%--});--%>
	});
</script>
<style type="text/css">
	td{padding: 8px;border:0px dashed #CCCCCC}
</style>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<!--  相关的事件处理 -->
		<li class="active"><a
			href="${ctx}/event/ccmEventCasedeal/form?id=${ccmEventCasedeal.id}">案事件处理<shiro:hasPermission
					name="event:ccmEventCasedeal:edit">${not empty ccmEventCasedeal.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="event:ccmEventCasedeal:edit">查看</shiro:lacksPermission></a></li>
	</ul>

	<form:form id="inputForm" modelAttribute="ccmEventCasedeal"
		action="${ctx}/event/ccmEventCasedeal/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="objId" />
		<form:hidden path="objType" />
        <form:hidden path="checkDate" />
        <form:hidden path="checkUser" />
        <form:hidden path="checkOpinion" />
        <form:hidden path="checkScore" />
		<sys:message content="${message}" />
		
		<h4 class="color-bg6">任务安排：</h4>
		<br>
		<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td style="width: 50%;">
					<label class="control-label">处理人员：</label>
					<div class="controls">
						${ccmEventCasedeal.handleUser.name}
					</div>
				</td>
				<td>
					<label class="control-label">处理截至时间：</label>
					<div class="controls">
						<fmt:formatDate value="${ccmEventCasedeal.handleDeadline}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</div>
				
				</td>
			</tr>
			<tr>
<%--				<td>--%>
<%--					<div>--%>
<%--						<label class="control-label">事件反馈状态：</label>--%>
<%--						<div class="controls">--%>
<%--							<form:radiobuttons name="manageType" path="manageType" items="${fns:getDictList('manage_type')}"--%>
<%--								itemLabel="label" itemValue="value" htmlEscape="false" class="displayedbuttons required" /><span class="help-inline"><font color="red">*</font> </span>--%>
<%--						</div>--%>
<%--					</div>--%>
<%--				</td>--%>
				<td colspan="2">
					<label class="control-label">是否督办：</label>
					<div class="controls">
						${fns:getDictLabel(ccmEventCasedeal.isSupervise, 'yes_no', '')}
					</div>
				
				</td>
			</tr>
			
			<tr>
			</tr>
			<tr>
				<td colspan="2">
					<label class="control-label">事件说明及任务安排：</label>
					<div class="controls">
						<form:textarea path="remarks"  htmlEscape="false" rows="8" maxlength="1000" class="input-xxlarge "/>
					</div>
				</td>
			</tr>
		</table>
		
		<br>
		<h4 class="color-bg6">处理信息：</h4>
		<br>
		<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>处理时间：</label>
						<div class="controls">
							<input name="handleDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${ccmEventCasedeal.handleDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>

						</div>
				</td>
<%--				<td>--%>
<%--						<label class="control-label">任务处理状态：</label>--%>
<%--						<div class="controls">--%>
<%--							<form:select id="handleStatus" path="handleStatus" class="input-xlarge ">--%>
<%--								<form:options items="${fns:getDictList('ccm_task_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
<%--							</form:select>--%>
<%--						</div>--%>
<%--				</td>--%>
				<td>
					<div>
						<label class="control-label">附件：</label>
						<div class="controls">
							<form:hidden id="file" path="file" htmlEscape="false"
										 maxlength="255" class="input-xlarge" />
							<c:if test="${ccmEventCasedeal.handleStatus eq '03'||ccmEventCasedeal.handleStatus eq '05'}">
								<sys:ckfinder input="file" type="images" readonly="true"
											  uploadPath="/event/ccmEventCasedeal" selectMultiple="false" maxWidth="240"
											  maxHeight="360" />
							</c:if>
							<c:if test="${ccmEventCasedeal.handleStatus ne '03'&& ccmEventCasedeal.handleStatus ne '05'}">
								<sys:ckfinder input="file" type="images"
											  uploadPath="/event/ccmEventCasedeal" selectMultiple="false" maxWidth="240"
											  maxHeight="360" />
							</c:if>
						</div>
					</div>
				</td>
			</tr>
			
			<tr>
				<td>
						<label class="control-label">处理措施：</label>
						<div class="controls">
							<form:textarea path="handleStep" htmlEscape="false" rows="4" maxlength="256" class="input-xlarge "/>
						</div>
				</td>
				<td>
						<label class="control-label">案事件反馈：</label>
						<div class="controls">
							<form:textarea path="handleFeedback" htmlEscape="false" rows="4" maxlength="256" class="input-xlarge "/>
						</div>
				</td>
			</tr>
			<!--  进行事件处理时，下述不显示
			<div class="control-group">
				<label class="control-label">考评日期：</label>
				<div class="controls">
					<input name="checkDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${ccmEventCasedeal.checkDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">考评人员：</label>
				<div class="controls">
					<form:input path="checkUser" htmlEscape="false" maxlength="64" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">考评意见：</label>
				<div class="controls">
					<form:input path="checkOpinion" htmlEscape="false" maxlength="256" class="input-xlarge "/>
				</div>
			</div>
			<div class="control-group">
				<label class="control-label">得分：</label>
				<div class="controls">
					<form:input path="checkScore" htmlEscape="false" maxlength="4" class="input-xlarge "/>
				</div>
			</div>
			-->

			
		</table>

		<div class="form-actions">
			<shiro:hasPermission name="event:ccmEventCasedeal:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
		</div>
	</form:form>

</body>
</html>