<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>预处理事件管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
<script type="text/javascript">
	$(document).ready(
			function() {
				//关闭弹框事件
                $('#btnCancel').click(function(){
              	  parent.layer.close(parent.layerIndex);
                })

				//$("#name").focus();
				$("#EventDetailBtn").click(function() {
					$("#EventDetailTable").toggle();
				});
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
			});

    function saveForm(id) {

        if (id == "" || id.length == 0) {

            var area = $("#areaId").val();
            var html1 = '<label for="" class="error">必选字段 *<label>';
            //alert(officeId.length);
            if (area.length != 0) {
                $("#show1").html("*");
            } else {
                $("#show1").html(html1);
            }

            if (area.length != 0) {
                $("#inputForm").submit();
            }

        } else {
            $("#inputForm").submit();
        }

    }
</script>
</head>
<body>
	<form:form id="inputForm" modelAttribute="ccmEventIncidentPreview"
		action="${ctx}/preview/ccmEventIncidentPreview/save/3" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<sys:message content="${message}" />
		<table>
			<tr>
				<td colspan="2" style="width: 50%;"><label
					class="control-label">事件名称：</label>
					<div class="controls">
						<form:input path="caseName" htmlEscape="false" maxlength="100"
							class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font></span>
					</div></td>

			</tr>
			<tr>
				<td colspan="2"><label class="control-label">事件详情：</label>
					<div class="controls">
						<form:textarea path="caseCondition" htmlEscape="false"
							class="input-xxlarge " />
					</div></td>
			</tr>
			<tr>
				<td colspan="2"><label class="control-label">发生地详址：</label>
					<div class="controls">
						<form:textarea path="happenPlace" htmlEscape="false"
							maxlength="200" class="input-xxlarge " />
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">上报人：</label>
					<div class="controls">
						<form:input path="reportPerson" htmlEscape="false" maxlength="15"
							class="input-xlarge required" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
				<td><label class="control-label">上报人联系电话：</label>
					<div class="controls">
					<form:input path="reportPersonPhone" htmlEscape="false" maxlength="11"
								class="input-xlarge telephone0 phone required" />
							<span id="telephone0"></span> <span class="help-inline"><font
								color="red" id="show1">*</font></span>
					</div></td>
			</tr>
			<tr>
				<td><label class="control-label">所属网格：</label>
					<div class="controls">
						<sys:treeselect id="area" name="casePlace"
							value="${ccmEventIncidentPreview.area.id}" labelName="area.name"
							labelValue="${ccmEventIncidentPreview.area.name}" title="区域"
							url="/sys/area/treeData" allowClear="true"
							notAllowSelectParent="true" cssStyle="width: 220.22px" cssClass=""/>
							<span class="help-inline"><font color="red" id="show1">*</font></td>
				<td><label class="control-label">事件时间：</label>
					<div class="controls">
						<input name="happenDate" type="text" readonly="readonly"
							maxlength="20" class="input-medium Wdate required"
							value="<fmt:formatDate value="${ccmEventIncidentPreview.happenDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
							onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
						<span class="help-inline"><font color="red">*</font> </span>
					</div></td>
			</tr>
			<tr>
				<td style="width: 50%;"><label class="control-label">事件类别：</label>
					<div class="controls">
						<form:select path="eventKind" class="input-xlarge required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_event_sort')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<span class="help-inline"><font color="red">*</font></span>
					</div></td>

				<td style="width: 50%;"><label class="control-label">事件规模：</label>
					<div class="controls">
						<form:select path="caseScope" class="input-xlarge required">
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_event_scope')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						<span class="help-inline"><font color="red">*</font></span>
					</div></td>
			</tr>
			<tr>

				<td><label class="control-label">是否重点场所：</label>
					<div class="controls">
						<form:radiobuttons path="isKeyPlace"
							items="${fns:getDictList('is_key_place')}" itemLabel="label"
							itemValue="value" htmlEscape="false" class="" />
					</div></td>
					<td><label class="control-label">事件图片：</label>
					<div class="controls">
						<form:hidden id="file1" path="file1" htmlEscape="false"
							maxlength="200" class="input-xxlarge" />
						<sys:ckfinder input="file1" type="images"
							uploadPath="/event/ccmEventIncident" selectMultiple="flase" />
					</div></td>
			</tr>
		</table>
		<br />
		<div>
			<h4 class="tableNamefirst" id="EventDetailBtn"
				style="cursor: pointer;">
				高级<i class=" icon-share-alt"></i>
			</h4>
			<table id="EventDetailTable" style="display: none">
				<tr>
					<td><label class="control-label">事（事)件性质：</label>
						<div class="controls">
							<form:select path="property" class="input-xlarge">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ccm_case_natu')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div></td>
					<td><label class="control-label">事件类型：</label>
						<div class="controls">
							<form:select path="eventType" class="input-xlarge">
								<form:option value="" label="" />
								<form:options items="${fns:getDictList('ccm_case_type')}"
									itemLabel="label" itemValue="value" htmlEscape="false" />
							</form:select>
						</div></td>

				</tr>
				<tr>
					<td><label class="control-label">主犯（嫌疑人)证件代码：</label>
						<div class="controls">
							<form:input path="culPapercode" htmlEscape="false" maxlength="3"
								class="input-xlarge " />
						</div></td>

					<td><label class="control-label">主犯（嫌疑人)证件号码：</label>
						<div class="controls">
							<form:input path="culPaperid" htmlEscape="false" maxlength="18" class="input-xlarge ident1 card"/>
							<span id="ident1"></span>
						</div></td>
				</tr>
				<tr>
					<td><label class="control-label">主犯（嫌疑人）姓名：</label>
						<div class="controls">
							<form:input path="culName" htmlEscape="false" maxlength="80"
								class="input-xlarge " />
						</div></td>

					<td><label class="control-label">是否破案：</label>
						<div class="controls">
							<form:input path="typeSolve" htmlEscape="false" maxlength="1"
								class="input-xlarge  digits" />
						</div></td>
				</tr>
				<tr>
					<td><label class="control-label">作案人数(人)：</label>
						<div class="controls">
							<form:input path="numCrime" htmlEscape="false" maxlength="6"
								class="input-xlarge  digits" />
						</div></td>

					<td><label class="control-label">在逃人数(人)：</label>
						<div class="controls">
							<form:input path="numFlee" htmlEscape="false" maxlength="6"
								class="input-xlarge  digits" />
						</div></td>
				</tr>
				<tr>
					<td><label class="control-label">抓捕人数(人)：</label>
						<div class="controls">
							<form:input path="numArrest" htmlEscape="false" maxlength="6"
								class="input-xlarge  digits" />
						</div></td>

					<td><label class="control-label">侦查终结日期：</label>
						<div class="controls">
							<input name="caseOverDay" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${ccmEventIncidentPreview.caseOverDay}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});" />
						</div></td>
				</tr>
				<tr>
					<td><label class="control-label">案件侦破情况：</label>
						<div class="controls">
							<form:input path="caseSolve" htmlEscape="false"
								class="input-xlarge " />
						</div></td>
					<td><label class="control-label">事件分级：</label>
						<div class="controls">
							<form:radiobuttons path="eventScale"
								items="${fns:getDictList('ccm_case_grad')}" itemLabel="label"
								itemValue="value" htmlEscape="false" class="" />
						</div></td>
				</tr>

				<tr>
					<td><label class="control-label">附件二：</label>
						<div class="controls">
								<form:hidden id="file2" path="file2" htmlEscape="false" maxlength="24" class="input-xlarge"/>
							    <sys:ckfinder input="file2" type="files" uploadPath="/event/ccmEventIncident" selectMultiple="true"/>
						</div></td>

					<td><label class="control-label">附件三：</label>
						<div class="controls">
							<form:hidden id="file3" path="file3" htmlEscape="false" maxlength="24" class="input-xlarge"/>
					   			<sys:ckfinder input="file3" type="files" uploadPath="/event/ccmEventIncident" selectMultiple="true"/>
						</div></td>
				</tr>

				<tr>
					<td colspan="2"><label class="control-label">备注信息：</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="2"
								maxlength="255" class="input-xxlarge " />
						</div></td>
				</tr>
			</table>
			<div class="form-actions">
				<shiro:hasPermission name="preview:ccmEventIncidentPreview:edit">
					<input id="btnSubmit" class="btn btn-primary" onclick="saveForm('${ccmEventIncidentPreview.id}')" type="button" value="保 存" />&nbsp;
					<input id="btnCancel" class="btn btn-danger" type="button" value="关闭" /></shiro:hasPermission>
			</div>
		</div>
	</form:form>
</body>
</html>