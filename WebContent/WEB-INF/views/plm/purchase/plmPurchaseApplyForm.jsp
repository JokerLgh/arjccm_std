<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>政府项目采购申请管理</title>
<meta name="decorator" content="default" />

<link href="${ctxStatic}/common/zztable.css" type="text/css"
	rel="stylesheet">
<!-- 表格试表单css -->
<%-- <link href="${ctxStatic}/common/zzformtable.css" type="text/css"
	rel="stylesheet"> --%>
	<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
	<script type="text/javascript" src="${ctxStatic}/plm/act/supervise.js"></script> 
<script type="text/javascript">
	$(document).ready(
			function() {
				//$("#name").focus();
				//自定义validate验证输入的数字小数点位数不能大于两位
					 jQuery.validator.addMethod("Number",function(value, element){
					     var returnVal = true;
					     inputZ=value;
					     var ArrMen= inputZ.split(".");    //截取字符串
					     if(ArrMen.length==2){
					         if(ArrMen[1].length>4){    //判断小数点后面的字符串长度
					             returnVal = false;
					             return false;
					         }
					         if(ArrMen[0].length>6){    //判断整数部分后面的字符串长度
					             returnVal = false;
					             return false;
					         }
					     }
					     
					     return returnVal;
					 },"整数部分最多6位，小数点后最多为4位");         //验证错误信息
 
				 $('#btnSubmit').click(function(){
						$('#inputForm').submit();
					});
				$("#inputForm")
						.validate(
								{
									 //验证规则
						            rules: {
						            	purBudget: {
						                    required: true,    //要求输入不能为空
						                    number: true,     //输入必须是数字
						                    min: 0.0001,         //输入的数字最小值为0.01，不能为0或者负数
						                    max:999999,
						                    Number: $("#purBudget").val()    //调用自定义验证
						                }
						            },

						            //错误提示信息
						            messages: {
						            	purBudget: {
						                    required: "必填信息",
						                    number: "请正确输入金额",
						                    min: "输入最小金额为0.0001",
						                    max: "输入最大金额为999999",
						                    length: "输入数字最多小数点后四位"
						                }
						            },
									
									
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

	function tijiao() {
		jBox.confirm('您确认提交申请吗？提交申请后将不能修改申请内容！', "系统提示", function(v, h, f) {
			if (v == "ok") {
				$('#flag').val('yes');
				$("#inputForm").submit();
			}
		}, {
			buttonsFocus : 1
		});

	}
	function baocun() {

		$("#inputForm").attr("action",
				"${ctx}/purchase/plmPurchaseApply/saveUnSubmit");
		$("#inputForm").submit();

	}
</script>


</head>
<body>

	<form:form target="_parent" id="inputForm" modelAttribute="plmPurchaseApply"
		action="${ctx}/purchase/plmPurchaseApply/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="plmAct.id"/>
		<form:hidden path="plmAct.title"/>
		<form:hidden path="plmAct.tableName"/>
		<form:hidden path="plmAct.tableId"/>
		<form:hidden path="plmAct.formUrl"/>	
		<form:hidden path="procInsId" />
		<form:hidden path="act.taskId" />
		<form:hidden path="act.taskName" />
		<form:hidden path="act.taskDefKey" />
		<form:hidden path="act.procInsId" />
		<form:hidden path="act.procDefId" />
		<form:hidden id="flag" path="act.flag" />
		<sys:message content="${message}" />
         


		<h2>政府项目采购申请单</h2>

		<table id="tabletop" class="table  ">
			
			<tr>
				<td class="tabletop" colspan="2" style="width: 25%">申请人：
				 <sys:treeselect id="createBy"
						name="createBy.id" value="${plmPurchaseApply.createBy.id}"
						labelName="createBy.name"
						labelValue="${plmPurchaseApply.createBy.name}" title="用户"
						url="/sys/office/treeData?type=3" cssClass=""
						allowClear="true" notAllowSelectParent="true"  disabled="disabled"/>
				   </td>
				<td class="tabletop" colspan="2">采购项目部门：
				     <sys:treeselect id="office"
						name="office.id" value="${plmPurchaseApply.office.id}"
						labelName="office.name"
						labelValue="${plmPurchaseApply.office.name}" title="部门"
						url="/sys/office/treeData?type=2" cssClass="required"
						allowClear="true" notAllowSelectParent="true" disabled="disabled"  /> &nbsp;&nbsp;&nbsp;<span
					class="help-inline"></span></td>
				
				<td class="tabletop" colspan="2">申请日期：
				    <input name="applyDate"  type="text" readonly="readonly" maxlength="20" class="input-medium  required"
					value="<fmt:formatDate value="${plmPurchaseApply.applyDate}" pattern="yyyy-MM-dd HH:mm:ss" />"/>
					 
					</td>
					<td class="tabletop" colspan="2">项目编号：
				    <form:input path="applyId"
						 htmlEscape="false" maxlength="64" class="input-xlarge " readonly="true" placeholder="保存后自动生成"/>
					<span class="help-inline"></span></td>
			</tr>
		</table>
		<div style="color: red;text-align: right;">采购请单请上传附件</div>
		<table id="table" class="table   table-condensed">
			<tr>
				<td class="trtop">经费项目名称<font color="red">*</font></td>
				<td colspan="5">
				    <form:input path="fundingName"
						htmlEscape="false" maxlength="40" class="input-xxlarge required" />
					<span class="help-inline"></span></td>
			</tr>
			<tr>
				<td class="trtop">经费卡号<font color="red">*</font></td>
				<td colspan="2">
				    <form:input path="fundingCard"
						htmlEscape="false" maxlength="19" class="input-xlarge required number" />
						<span class="help-inline"></span>
						</td>
				<td class="trtop">经费类型<font color="red">*</font></td>
				<td colspan="2">
				    <form:select path="fundingType"
						class="input-xlarge required">
						<form:option value="" label="" />
						<form:options items="${fns:getDictList('purchase_funding_type')}"
							itemLabel="label" itemValue="value" htmlEscape="false" />
					</form:select> <span class="help-inline"></span></td>
			</tr>

			<tr>
				<td class="trtop">采购项目名称<font color="red">*</font></td>
				<td colspan="2">
				    <form:input path="name" htmlEscape="false"
						maxlength="40" class="input-xlarge required" /> <span
					class="help-inline"></span></td>
				<td class="trtop">采购项目预算(万元 )<font color="red">*</font></td>
				<td colspan="2">
				    <form:input path="purBudget" maxlength="11" htmlEscape="false"
						class="input-xlarge number required" /><span
					class="help-inline"></span></td>
			</tr>
			<tr>
				<td rowspan="" class="trtop tabletd1">采购项目负责人<font color="red">*</font></td>
			<!-- 	<td class="tabletd2 trtop">姓名</td> -->
				<td colspan="2" class="tabletd3 ">
				    <sys:treeselect id="userPur"
						name="userPur.id" value="${plmPurchaseApply.userPur.id}"
						labelName="userPur.name"
						labelValue="${plmPurchaseApply.userPur.name}" title="用户"
						url="/sys/office/treeData?type=3" cssClass="required"
						allowClear="true" notAllowSelectParent="true" isAll="true"/> <span
					class="help-inline"></span></td>
				<td rowspan="" class="trtop tabletd2">项目技术负责人<font color="red">*</font></td>
				<!-- <td class="tabletd5 trtop">姓名</td> -->
				<td colspan="2" class="tabletd6 ">
				     <sys:treeselect id="userTech"
						name="userTech.id" value="${plmPurchaseApply.userTech.id}"
						labelName="userTech.name"
						labelValue="${plmPurchaseApply.userTech.name}" title="用户"
						url="/sys/office/treeData?type=3" cssClass="required"
						allowClear="true" notAllowSelectParent="true" isAll="true"/> <span
					class="help-inline"></span></td>
			</tr>
			
			<tr>
				<td class="trtop">采购项目概括<font color="red">*</font></td>
				<td class="pingshen" colspan="5">
				   
                    <form:textarea path="describes" htmlEscape="false" rows="4"
								maxlength="255" class="input-xxlarge required "  cssStyle=" "/>
								<span class="help-inline"></span>
				</td>				
			</tr>
			<tr>
				<td class="trtop">备注</td>
				<td class="pingshen" colspan="5">
				   
                    <form:textarea path="remarks" htmlEscape="false"   maxlength="255" rows="4" class="input-xxlarge "/>
				</td>				
			</tr>
			<tr>
				<td class="trtop">附件（采购清单等）</td>
				<td colspan="5"><form:hidden id="files" path="files"
						htmlEscape="false" maxlength="256" class="input-xlarge" /> <sys:ckfinder
						input="files" type="files" uploadPath="/purchase/plmPurchaseApply"
						selectMultiple="true" />  </td>
                   
			</tr>
			<tr>
				<td class="trtop" colspan="1" style="width: 20%">是否督办</td>
				<td id="isSubTd" colspan="5">
					<form:radiobuttons path="plmAct.isSup" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class=""/>
				</td>
				<td class="trtop isSup" colspan="1" style="width: 20%">督办人</td>
				<td class="isSup" colspan="2" style="width: 30%">
					<sys:treeselect id="supExe" name="plmAct.supExe.id" value="${plmPurchaseApply.plmAct.supExe.id}" labelName="plmAct.supExe.name" labelValue="${plmPurchaseApply.plmAct.supExe.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="" allowClear="true" notAllowSelectParent="true" isAll="true"/>
				</td>
			</tr>		
			<tr class="isSup">
				<td class="trtop" colspan="1">督办明细</td>
				<td colspan="5">
					<form:textarea path="plmAct.supDetail" htmlEscape="false" rows="4" maxlength="256" class="input-xxlarge "/>
				</td>
			</tr>			
			<!-- 自定义标签   把流转信息用纸质表的方式显示   colspan:表格意见部分跨列数    WEB-INF/tags/act/histoicTable.tag   -->
			<act:histoicTable procInsId="${plmPurchaseApply.act.procInsId}"
				colspan="5" titleColspan="1" />
            <c:if test="${not empty plmCarApplyBuy.procInsId}">
			<tr>
				<td class="trtop">修改备注</td>
				<td class="pingshen" colspan="5">
				   <form:textarea path="act.comment" htmlEscape="false" rows="5" maxlength="255" class="input-xxlarge "/>
                   
				</td>				
			</tr>
		</c:if>
		</table>

		<div class="form-actions">
			
			<a id="btnSubmit" class="btn btn-primary" href="javascript:;"><i class="icon-print"></i>提交申请</a>&nbsp;
			<c:if test="${ empty plmPurchaseApply.procInsId}">
					<a id="btnSubmit2" class="btn btn-primary" href="javascript:;"><i class="icon-minus-sign"></i>销毁申请</a>&nbsp;
			</c:if>
				<c:if test="${not empty plmPurchaseApply.procInsId}">
					<a id="btnSubmit3" class="btn btn-primary" href="javascript:;"><i class="icon-ok"></i>保存</a>&nbsp;
				</c:if>
		
			<c:if test="${not empty plmPurchaseApply.id}">
			<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i class="icon-reply"></i>返回</a>
			</c:if>
			<c:if test="${empty plmPurchaseApply.id}">
			<a id="btnCancelf" class="btn btn-primary" href="javascript:;" onclick="parent.layer.closeAll();" ><i class="icon-remove-circle"></i>关闭</a>
			</c:if>
		</div>


	</form:form>

</body>
</html>