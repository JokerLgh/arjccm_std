<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>城市部件管理</title>
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
			$("td").css({"padding":"8px"});
			$("td").css({"border":"0px dashed #CCCCCC"});
		});
		
		
		function saveForm() {
			var area = $("#areaId").val();
			var html1 = '<label for="" class="error">必选字段 <label>';
			//alert(officeId.length);
			if (area.length != 0) {
				$("#show1").html("");
			} else {
				$("#show1").html(html1);
			}

			if (area.length != 0) {
				$("#inputForm").submit();
			}

		}
		
		function ThisLayerDialog(src, title, height, width) {
			parent.drawForm = parent.layer.open({
				type: 2,
				title: title,
				area: [height, width],
				fixed: true, //固定
				maxmin: false,
				/*   btn: ['关闭'], //可以无限个按钮 */
				content: src,
				zIndex: '1992',
				cancel: function () {
					//防止取消和删除效果一样
					window.isCancel = true;
				},
				end: function () {
					if (!window.isCancel) {
						$("#areaPoint")[0].value = parent.areaPoint;
						parent.areaPoint = null;
						$("#areaMap")[0].value = parent.areaMap;
						parent.areaMap = null;
					}
					window.isCancel = null;
				}
			});
		}
	</script>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/citycomponents/ccmCityComponents/">数据列表</a></li>
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/citycomponents/ccmCityComponents/form?id=${ccmCityComponents.id}">数据<shiro:hasPermission name="citycomponents:ccmCityComponents:edit">${not empty ccmCityComponents.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="citycomponents:ccmCityComponents:edit">查看</shiro:lacksPermission></a></li>
	</ul>
	<form:form id="inputForm" modelAttribute="ccmCityComponents" action="${ctx}/citycomponents/ccmCityComponents/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		
		<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;border-top-color: white">
				
			<tr>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>部件类型：</label>
						<div class="controls">
							<form:select path="type" class="input-xlarge required">
								<form:options items="${fns:getDictList('ccm_city_components_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>

						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>名称：</label>
						<div class="controls">
							<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

						</div>
					</div>
				</td>

			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>编号：</label>
						<div class="controls">
							<form:input path="code" htmlEscape="false" maxlength="64" class="input-xlarge required"/>

						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">主管部门代码：</label>
						<div class="controls">
							<form:input path="competentDepartmentCode" htmlEscape="false" maxlength="6" class="input-xlarge number"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td rowspan="">
					<div>
						<label class="control-label">城市部件图片：</label>
						<div class="controls">
							<form:hidden id="images" path="imagePath" htmlEscape="false" maxlength="255" class="input-xlarge"/>
							<sys:ckfinder input="images" type="images" uploadPath="/photo/ChengShiBuJian" selectMultiple="false" maxWidth="160" maxHeight="240"/>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label">主管部门名称：</label>
						<div class="controls">
							<form:input path="competentDepartmentName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">权属部门代码：</label>
						<div class="controls">
							<form:input path="ownershipDepartmentCode" htmlEscape="false" maxlength="6" class="input-xlarge number"/>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label">权属部门名称：</label>
						<div class="controls">
							<form:input path="ownershipDepartmentName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">养护部门代码：</label>
						<div class="controls">
							<form:input path="maintainDepartmentCode" htmlEscape="false" maxlength="6" class="input-xlarge number"/>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label">养护部门名称：</label>
						<div class="controls">
							<form:input path="maintainDepartmentName" htmlEscape="false" maxlength="100" class="input-xlarge "/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">养护部门电话：</label>
						<div class="controls">
							<form:input path="maintainDepartmentTel" htmlEscape="false" maxlength="64" class="input-xlarge phone"/>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>设备所在区域：</label>
						<div class="controls">
							<sys:treeselect id="area" name="area.id" value="${ccmHouseBuildmanage.area.id}" labelName="area.name" labelValue="${ccmHouseBuildmanage.area.name}" title="网格" url="/tree/ccmTree/treeDataArea?type=7&areaid=" cssClass="" allowClear="true" notAllowSelectParent="true"/>
							<span class="help-inline"><font color="red" id="show1"></font> </span>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">详细地点：</label>
						<div class="controls">
							<form:input path="address" htmlEscape="false" maxlength="100" class="input-xlarge "/>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				
				<td>
					<div>
						<label class="control-label">空间形态：</label>
						<div class="controls">
							<form:select path="spatialForm" class="input-xlarge ">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ccm_city_components_spatial')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>状态：</label>
						<div class="controls">
							<form:select path="status" class="input-xlarge required">
								<form:options items="${fns:getDictList('ccm_city_components_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>

						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td>
					<div>
						<label class="control-label">坐标（面）：</label>
						<div class="controls">
							<form:input path="areaMap" htmlEscape="false" maxlength="2000" class="input-xlarge " disabled="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">坐标（点）：</label>
						<div class="controls">
							<form:input path="areaPoint" htmlEscape="false" maxlength="40" class="input-xlarge " disabled="true"/>
							<a onclick="ThisLayerDialog('${ctx}/event/ccmEventIncident/drawForm?areaMap='+$('#areaMap').val()+'&areaPoint='+$('#areaPoint').val(), '标注', '1100px', '700px');"
						    	class="btn hide1 btn-primary">标 注</a>
						</div>
					</div>
				</td>
			</tr>
						
			<tr>
				<td colspan="2">
					<div>
						<label class="control-label">备注信息：</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
						</div>
					</div>
				</td>
				<td>
				</td>
			</tr>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="citycomponents:ccmCityComponents:edit"><input id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="button" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>