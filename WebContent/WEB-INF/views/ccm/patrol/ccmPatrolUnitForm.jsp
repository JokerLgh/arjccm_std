<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>巡逻单位管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
                    window.parent.document.getElementById("mainFrame").setAttribute("src","${ctx}/patrol/ccmPatrolUnit/");
                    parent.layer.close(parent.layer.index);
                },
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
			$("#iframeDetails").attr("src","${ctx}/patrol/ccmPatrolMissions/details2"+location.search);
			if("${isccmPatrolUnit}"=="yes"){
				$("#content").hide();
				$('input,select').attr('disabled',true);
				$('#btnCancel1').removeAttr("disabled")
				$('textarea').attr('disabled',"disabled");
				$("#isccmPatrolUnit").show();
			}
		});
		<%--console.log("${ctx}/patrol/ccmPatrolMissions/details2"+location.search)--%>
	</script>
</head>
<body>
	<br/>
	<iframe id="iframeDetails" style="width: 1200px;height: 480px;border: 0px;" src="" ></iframe>

	<form:form id="inputForm" modelAttribute="ccmPatrolUnit" action="${ctx}/patrol/ccmPatrolUnit/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<div class="control-group">
			<label >巡逻安排----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------</label>
		</div>
			<div id="isccmPatrolUnit" style="display: none;" class="control-group">
				<label class="error" >已有安排请去，单位列表查看修改</label>
			</div>
		<div id="content">
		<div class="control-group">
			<label class="control-label">巡逻民警</label>
			<div class="controls">
				<sys:treeselect id="user" name="userIds" value="${ccmPatrolUnit.user.id}" disabled="${isccmPatrolUnit=='yes' ? 'disabled' : ''}" labelName="user.name" labelValue="${ccmPatrolUnit.user.name}"
					title="用户" url="/patrol/ccmPatrolUnit/getTreeData?id=${id}" cssClass="required" allowClear="true" checked="true"  notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">巡逻车辆：</label>
			<div class="controls">
				<form:input path="patrolVehicles" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">车载设备：</label>
			<div class="controls">
				<form:input path="vehicleEquipment" htmlEscape="false" maxlength="128" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">单兵装备：</label>
			<div class="controls">
				<form:input path="individualEquipment" htmlEscape="false" maxlength="255" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">时间：</label>--%>
			<%--<div class="controls">--%>
				<%--<input name="departureTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"--%>
					<%--value="<fmt:formatDate value="${ccmPatrolUnit.departureTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"--%>
					<%--onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<%--<div class="control-group">--%>
			<%--<label class="control-label">状态：</label>--%>
			<%--<div class="controls">--%>
				<%--<form:select path="status" class="input-xlarge required">--%>
					<%--<form:option value="" label=""/>--%>
					<%--<form:options items="${fns:getDictList('ccm_patrol_missions_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>--%>
				<%--</form:select>--%>
				<%--<span class="help-inline"><font color="red">*</font> </span>--%>
			<%--</div>--%>
		<%--</div>--%>
		<div class="control-group">
			<label class="control-label">描述信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="patrol:ccmPatrolUnit:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>

			<input id="btnCancel1" class="btn" type="button" value="返 回" onclick="parent.layer.close(parent.layer.index)"/>
		</div>
	</form:form>
</body>
</html>