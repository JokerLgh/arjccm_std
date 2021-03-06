<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>绩效考评方案管理</title>
	<meta name="decorator" content="default"/>
	<%--引入文本框外部样式--%>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">

</head>
<body>
	<h5 class="nav nav-tabs ">
		绩效考评方案${not empty kpiScheme.id?'修改':'添加'}
	</h5>
	<form:form id="inputForm" cssStyle="padding-top: 10px" modelAttribute="kpiScheme" action="${ctx}/scheme/kpiScheme/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group head_Space" >
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>方案名称：</label>
			<div class="controls">
				<form:input path="name" htmlEscape="false" maxlength="64" class="input-xlarge required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>开始日期：</label>
			<div class="controls">
				<input name="startTime" type="text" id="startTime" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${kpiScheme.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({maxDate: '#F{$dp.$D(\'endTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>结束日期：</label>
			<div class="controls">
				<input name="endTime" type="text" id="endTime" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${kpiScheme.endTime}"  pattern="yyyy-MM-dd HH:mm:ss"/>"
					   onclick="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}',dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>所属部门：</label>
			<div class="controls">
				<sys:treeselect id="office" name="office.id" value="${kpiScheme.office.id}" labelName="office.name" labelValue="${kpiScheme.office.name}"
					title="部门" url="/sys/office/treeData?type=2" cssClass="" allowClear="true" notAllowSelectParent="true"/>
				<span class="help-inline"><font color="red" id="show1"></font></span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label width:182px"><span class="help-inline"><font color="red" >*</font> </span>考核人员类别：</label>
			<div class="controls">
				<form:select path="userType" class="input-xlarge required ">
					<form:options items="${fns:getDictList('sys_user_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>方案状态：</label>
			<div class="controls">
				<form:select path="state" class="input-xlarge required" disabled="true">
					<form:options items="${fns:getDictList('kpi_scheme_state')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions" >
			<input id="btnSubmit1" class="btn btn-primary" onclick="saveForm()" type="button" value="保 存"/>&nbsp;
			<input id="btnDelete" class="btn btn-danger" onclick="deleteForm()" type="button" value="删 除"/>&nbsp;
			<!-- <input id="btnCancel1" class="btn" type="button" value="返 回" onclick="history.go(-1)"/> -->
		</div>
	</form:form>
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
		function saveForm(){
			var state = $('#state option:selected').val();
			//alert(state);
			if(state=="0"){
				var officeId = $("#officeId").val();
				var html1 = '<label for="" class="error">必填信息 <label>';
				//alert(officeId.length);
				if(officeId.length!=0){
					$("#show1").html("");
				}else{
					$("#show1").html(html1);
				}
				if(officeId.length!=0){
					$("#inputForm").submit();
				}
			}else if(state=="1"){
				top.$.jBox.tip('此方案状态为发布状态，不可编辑！ ')
			}else if(state=="2"){
				top.$.jBox.tip('此方案状态为冻结状态，不可编辑！ ')
			}

		}
		function deleteForm(){
			var state = $('#state option:selected').val();
			if(state=="0"){
				var id = $('#id').val();
				if(id==""){
					top.$.jBox.tip('新建的未保存方案，不可编辑！ ');
				}else{
					top.$.jBox.confirm("如果删除父级将连同子级一起删掉，确认删除？", "系统提示", function(v, h, f) {
						if (v == "ok") {
							window.location.href="${ctx}/scheme/kpiScheme/delete?id=${kpiScheme.id}";
						} else {

						}
					}, {
						buttonsFocus : 1
					});
				}

			}else if(state=="1"){
				top.$.jBox.tip('此方案状态为发布状态，不可编辑！ ')
			}else if(state=="2"){
				top.$.jBox.tip('此方案状态为冻结状态，不可编辑！ ')
			}

		}
	</script>
</body>
</html>