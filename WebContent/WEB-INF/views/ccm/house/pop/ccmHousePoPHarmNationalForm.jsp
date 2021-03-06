<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>人员标记</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				$("#inputForm")
						.validate(
								{
									submitHandler : function(form) {
										$("#btnSubmit").attr("disabled", true);
					loading('正在提交，请稍等...');
										form.submit();
									},
									errorContainer : "#messageBox",
									errorPlacement : function(error, element) {
										$("#btnSubmit").removeAttr('disabled');
					$("#messageBox").text("输入有误，请先更正。");
										if (element.is(":checkbox")
												|| element.is(":radio")
												|| element.parent().is(
														".input-append")) {
											error.appendTo(element.parent()
													.parent());
										} else {
											error.insertAfter(element);
										}
									}
								});
				$("td").css({"padding":"8px"});
				$("td").css({"border":"1px dashed #CCCCCC"});
				
				if('${ccmHarmNationalSecurity.isCrimeHistory}' =="0"){
					$("#crimeHistory").attr("readonly", "readonly");
					$("#crimeHistory").val("无犯罪史，不可填写")
				}
				$("input[name='isCrimeHistory']").click(function() {
					if ($(this).val() == "0") {
						$("#crimeHistory").attr("readonly", "readonly");
						$("#crimeHistory").val("无犯罪史，不可填写")
					} else {
						$("#crimeHistory").removeAttr("readonly");
						$("#crimeHistory").val("")
					}
				});
			});
</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<%-- <li><a href="${ctx}/report/ccmPeopleStat/statisticsPage?title=ccmPeopleStatPerson">人口统计</a></li>
		<li class=" "><a href="${ctx}/pop/ccmPeople/">实有人口列表</a></li>
		<li class="active"><a href="">人员标记</a></li> --%>
	</ul>
	<br />
	<%-- <ul class="nav nav-pills">
		<li class="active spePop"><a href="${ctx}/pop/ccmPeople/specialform?id=${ccmPeople.id}">有危害国家安全活动嫌疑人员标记</a></li>
		<li><a href="${ctx}/house/ccmHouseRelease/specialform?id=${ccmPeople.id}">安置帮教人员标记</a></li>
		<li><a href="${ctx}/house/ccmHouseRectification/specialform?id=${ccmPeople.id}">社区矫正人员标记</a></li>
		<li><a href="${ctx}/house/ccmHousePsychogeny/specialform?id=${ccmPeople.id}">易肇事肇祸精神病人标记</a></li>
		<li><a href="${ctx}/house/ccmHouseAids/specialform?id=${ccmPeople.id}">艾滋病危险人员标记</a></li>
		<li><a href="${ctx}/house/ccmHousePetition/specialform?id=${ccmPeople.id}">重点上访人员标记</a></li>
		<li><a href="${ctx}/house/ccmHouseHeresy/specialform?id=${ccmPeople.id}">涉教人员标记</a></li>
		<li><a href="${ctx}/house/ccmHouseDangerous/specialform?id=${ccmPeople.id}">危险品从业人员标记</a></li>
		<li><a href="${ctx}/pop/ccmPopBehind/specialform?id=${ccmPeople.id}">留守人员标记</a></li>
		<li><a href="${ctx}/house/ccmHouseKym/specialform?id=${ccmPeople.id}">重点青少年标记</a></li>
            
	</ul> --%>

	<form:form id="inputForm" modelAttribute="ccmHarmNationalSecurity"
		action="${ctx}/house/ccmHarmNationalSecurity/save" method="post"
		class="form-horizontal">
		<h4 class="tableName">危害国家安全活动嫌疑人员信息</h4>
		<sys:message content="${message}" />
		<form:hidden path="peopleId" value="${ccmPeople.id}" />
		
		<table border="1px"
			style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div>
						<label class="control-label">嫌疑类型：</label>
						<div class="controls">
							<form:select path="suspicionType" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('suspicion_type_dict')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
				<td>
					<div>
					<label class="control-label">危险程度：</label>
						<div class="controls">
							<form:select path="dangerLevel" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('danger_level_dict')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
					<label class="control-label">呈报单位：</label>
						<div class="controls">
							<sys:treeselect id="reportOffice" name="reportOffice"
								value="${ccmHarmNationalSecurity.reportOffice.id}" labelName=""
								labelValue="${ccmHarmNationalSecurity.reportOffice.name}" title="呈报单位"
								url="/sys/office/treeData?type=2" cssClass="required" allowClear="true"
								notAllowSelectParent="true" cssStyle="z-index:9999"/>
								<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
				<td>
					<div>
					<label class="control-label">呈报时间：</label>
						<div class="controls">
							<input name="reportDate" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${ccmHarmNationalSecurity.reportDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
								<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">监管状态：</label>
						<div class="controls">
							<%-- <form:select path="superviseStatus" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('house_supervise_status')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select> --%>
							<form:radiobuttons path="superviseStatus"
								items="${fns:getDictList('house_supervise_status')}"
								itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
				<td>
					<div>
					<label class="control-label">发现途径：</label>
						<div class="controls">
							<form:select path="discoveryWay" class="input-xlarge required">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('house_discovery_way')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
					<label class="control-label">发现人（告发人）：</label>
						<div class="controls">
							<form:input path="discoverer" htmlEscape="false" maxlength="128"
								class="input-xlarge " />
						</div>
						
					</div>
				</td>
				<td>
					<div>
					<label class="control-label">发现人身份证号：</label>
						<div class="controls">
							<form:input path="discoverIdCards" htmlEscape="false"
								maxlength="20" class="input-xlarge ident0 card" />
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
					<label class="control-label">发现人联系方式：</label>
						<div class="controls">
							<form:input path="discoverPhone" htmlEscape="false"
								maxlength="15" class="input-xlarge phone" />
						</div>
						
					</div>
				</td>
				<td>
					<div>
					<label class="control-label">有无犯罪史：</label>
						<div class="controls">
							<form:radiobuttons path="isCrimeHistory"
								items="${fns:getDictList('have_no')}"
								itemLabel="label" itemValue="value" htmlEscape="false" class="required" />
								<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">嫌疑人经济状况：</label>
						<div class="controls">
							<form:select path="conomicState" class="input-xlarge ">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('conomic_state_dict')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">嫌疑人活动范围：</label>
						<div class="controls">
							<form:input path="activityScope" htmlEscape="false"
								maxlength="512" class="input-xlarge " />
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">监管情况：</label>
						<div class="controls">
							<form:textarea path="hType" htmlEscape="false" rows="4"
								maxlength="2084" class="input-xlarge " />
						</div>
					</div>
				</td>
				<td>
					<div><label class="control-label">参与嫌疑活动描述：</label>
						<div class="controls">
							<form:textarea path="activityDescription" htmlEscape="false"
								rows="4" maxlength="2048" class="input-xlarge required" />
								<span class="help-inline"><font color="red">*</font> </span>
						</div>
						
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">犯罪史详情：</label>
						<div class="controls">
							<form:textarea path="crimeHistory" htmlEscape="false" rows="4"
								maxlength="1024" class="input-xlarge " />
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">备注信息：</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="4"
								maxlength="255" class="input-xlarge " />
						</div>
					</div>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="house:ccmHarmNationalSecurity:edit">
				<input id="btnSubmit" class="btn btn-primary" type="submit"
					value="保 存" />&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" type="button" value="返 回"
				onclick="history.go(-1)" /> -->
		</div>
	</form:form>
</body>
</html>