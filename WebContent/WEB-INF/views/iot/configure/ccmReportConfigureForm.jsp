<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>报警配置管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
	<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function() {
			
			//关闭弹框事件
			$('#btnCancel').click(function() {
				parent.layer.close(parent.layerIndex);
			})
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
			var value = $('#topColor  option:selected').val();
			$("#s2id_topColor").find("span").eq(0).css("background",$("#topColor").val());
			$("#s2id_topColor").find("span").eq(0).css("text-align","center");
		});
		function selectColor(){
			var value = $('#topColor  option:selected').val();
			$("#s2id_topColor").find("span").eq(0).css("background",$("#topColor").val());
			$("#s2id_topColor").find("span").eq(0).css("text-align","center");
		}
	</script>
</head>
<body>
	<%-- <ul class="nav nav-tabs">
		<li><a href="${ctx}/configure/ccmReportConfigure/">报警配置列表</a></li>
		<li class="active"><a href="${ctx}/configure/ccmReportConfigure/form?id=${ccmReportConfigure.id}">报警配置<shiro:hasPermission name="configure:ccmReportConfigure:edit">${not empty ccmReportConfigure.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="configure:ccmReportConfigure:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%><br/>
	<form:form id="inputForm" modelAttribute="ccmReportConfigure" action="${ctx}/configure/ccmReportConfigure/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<h3 class="tableName" style="margin:10px 0 10px 0;"><i class="icon-hand-right"></i>首页及右侧列表展示信息</h3>
		<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">报警类型：</label>
						<div class="controls">
							<form:select path="reportType" class="input-xlarge required">
								<form:option value="" label="全部"/>
								<form:options items="${fns:getDictList('ccm_configure_report_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">上传报警音乐：</label>
						<div class="controls">
							<form:hidden id="reportMusic" path="reportMusic" htmlEscape="false" maxlength="255" class="input-xlarge"/>
							<sys:ckfinder input="reportMusic" type="files" uploadPath="/configure/ccmReportConfigure" selectMultiple="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">上传地图展示图片：</label>
						<div class="controls">
							<form:hidden id="reportAimages" path="reportAimages" htmlEscape="false" maxlength="255" class="input-xlarge"/>
							<sys:ckfinder input="reportAimages" type="images" uploadPath="/configure/ccmReportConfigure" selectMultiple="false"/>
						</div>
					</div>
				</td>
			</tr>
		</table>
		<%--<h3 class="tableName" style="margin:10px 0 10px 0;"><i class="icon-paper-clip"></i>头部数据展示信息</h3>
		<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">头部展示类型：</label>
						<div class="controls">
							<form:radiobuttons path="topRtype" items="${fns:getDictList('ccm_configure_top_rtype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
				<td>
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">头部展示未处理类型：</label>
						<div class="controls">
							<form:radiobuttons path="topRstype" items="${fns:getDictList('ccm_configure_top_rstype')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
							<span class="help-inline"><font color="red">*</font> </span>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">头部展示颜色：</label>
						<div class="controls">
							<form:select id="topColor" path="topColor" class="input-xlarge " onchange="selectColor()">
								<form:option value="" label="全部"/>
								<form:options items="${fns:getDictList('ccm_configure_top_color')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">头部展示图片：</label>
						<div class="controls">
							<form:hidden id="topRimages" path="topRimages" htmlEscape="false" maxlength="255" class="input-xlarge"/>
							<sys:ckfinder input="topRimages" type="images" uploadPath="/configure/ccmReportConfigure" selectMultiple="false"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<div class="control-group" style="border:none;margin-top:10px;">
						<label class="control-label">图标：</label>
						<div class="controls">
							<form:hidden id="icon" path="icon" htmlEscape="false" maxlength="255" class="input-xlarge"/>
							<sys:ckfinder input="icon" type="images" uploadPath="/configure/ccmReportConfigure" selectMultiple="false"/>
						</div>
					</div>
				</td>
			</tr>
		</table>--%>
		<div class="form-actions">
			<shiro:hasPermission name="configure:ccmReportConfigure:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn btn-danger" type="button" value="关 闭"/>
		</div>
	</form:form>
</body>
</html>