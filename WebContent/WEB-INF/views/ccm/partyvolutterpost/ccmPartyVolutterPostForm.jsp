<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>志愿者岗位管理管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			//关闭弹框事件
			$('#btnCancel').click(function() {
				parent.layer.close(parent.layerIndex);
			})
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
		<%--<li><a href="${ctx}/partyvolutterpost/ccmPartyVolutterPost/">志愿者岗位管理列表</a></li>
		<li class="active"><a href="${ctx}/partyvolutterpost/ccmPartyVolutterPost/form?id=${ccmPartyVolutterPost.id}">志愿者岗位管理<shiro:hasPermission name="partyvolutterpost:ccmPartyVolutterPost:edit">${not empty ccmPartyVolutterPost.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="partyvolutterpost:ccmPartyVolutterPost:edit">查看</shiro:lacksPermission></a></li>--%>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="ccmPartyVolutterPost" action="${ctx}/partyvolutterpost/ccmPartyVolutterPost/save" method="post" class="form-horizontal">
	<div>
			<form:hidden path="id"/>
			<sys:message content="${message}"/>
		<table id="PartyPersonDetailTable" border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  colspan="2" >
					<div>
						<label class="control-label">岗位名称：</label>
						<div class="controls">
							<form:input path="name" htmlEscape="false" maxlength="100" class="input-xlarge required" style="width: 650px" />
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>

			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div>
						<label class="control-label">岗位类型：</label>
						<div class="controls">
							<form:input path="type" htmlEscape="false" maxlength="64" class="input-xlarge  "/>
						</div>
					</div>
				</td>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div>
						<label class="control-label">专业要求：</label>
						<div class="controls">
							<form:input path="professionalRequirements" htmlEscape="false" maxlength="100" class="input-xlarge  "/>
						</div>
					</div>
				</td>
			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div><label class="control-label">拟认领数：</label>
						<div class="controls">
							<form:input path="claimNum" htmlEscape="false" maxlength="10" class="input-xlarge "/>
						</div>

					</div>
				</td>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div><label class="control-label">发布时间：</label>
						<div class="controls">
							<input name="publishTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
								   value="<fmt:formatDate value="${ccmPartyVolutterPost.publishTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
								   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div><label class="control-label">联系人：</label>
						<div class="controls">
							<form:input path="rela" htmlEscape="false" maxlength="64" class="input-xlarge "/>
						</div>

					</div>
				</td>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  >
					<div>	<label class="control-label">联系电话：</label>
						<div class="controls">
							<form:input path="telphone" htmlEscape="false" maxlength="20" class="input-xlarge "/>
						</div>
					</div>
				</td>
			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  colspan="2"  >
					<div>	<label class="control-label">所属社区：</label>
						<div class="controls">
							<sys:treeselect id="community" name="community.id" value="${ccmPartyVolutterPost.community.id}" labelName=".community.name" labelValue="${ccmPartyVolutterPost.community.name}"
											title="区域" url="/sys/area/treeData" cssClass=" required" allowClear="true" notAllowSelectParent="true" cssStyle="width: 650px"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>

					</div>
				</td>
			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC"  colspan="2"  >
					<div>
						<label class="control-label">岗位描述：</label>
						<div class="controls">
							<form:input path="jobDescription" htmlEscape="false" class="input-xlarge " style="width: 650px"/>
						</div>
					</div>
				</td>
			</tr><tr>
				<td style="padding: 8px;border: 1px dashed #CCCCCC" colspan="2" >
					<div>
						<label class="control-label">简介：</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "  style="width: 650px" />
						</div>
					</div>
				</td>
			</tr>
		</table>
	</div>
		<div class="form-actions">
			<shiro:hasPermission name="partyvolutterpost:ccmPartyVolutterPost:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn btn-danger" type="button" value="关 闭"/>
		</div>
	</form:form>
</body>
</html>