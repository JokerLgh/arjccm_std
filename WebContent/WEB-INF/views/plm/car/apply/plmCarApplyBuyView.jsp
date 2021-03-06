<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>购车申请管理</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/common/zztable.css" type="text/css" rel="stylesheet">
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
			$("#btn").on("click", function(){
				$("#inputForm").attr("action","${ctx}/act/plmAct/cancelApply");
				$("#inputForm").submit();
			});
		});
		//根据html模版 pdf下载       url表示请求路径,进入后台处理,后台返回一个文件流		
		function downloadFile(){

		    //定义一个form表单,通过form表单来发送请求
		    var form=$("<form>");

		    //设置表单状态为不显示
		    form.attr("style","display:none");

		    //method属性设置请求类型为get
		    form.attr("method","get");

		    //action属性设置请求路径,(如有需要,可直接在路径后面跟参数)
		    //例如:htpp://127.0.0.1/test?id=123
		    form.attr("action",'${ctx}/car/apply/plmCarApplyBuy/printPdfIo');
            
		    var input1 = $('<input>');//将你请求的数据模仿成一个input表单
		    input1.attr('type', 'hidden');
		    input1.attr('name', 'id');//该输入框的name
		    input1.attr('value','${plmCarApplyBuy.id}');//该输入框的值
		    
		    //将表单放置在页面(body)中
		    $("body").append(form);
		    form.append(input1);
		    
		    //表单提交
		    form.submit();
		    		  
		    form.remove();
		} 		
	</script>
</head>
<body>
	<form:form id="inputForm" style="margin: 30px 100px;" modelAttribute="plmCarApplyBuy" action="${ctx}/car/apply/plmCarApplyBuy/save" method="post" class="form-horizontal">
		<form:hidden path="procInsId"/>
		<sys:message content="${message}"/>		
		<h2>购车申请单</h2>	
		<div style="text-align: right; ">       <a onclick="downloadFile()"><i class="icon-download"></i>下载</a></div>	  	  	
	    <table id="tabletop" class="table">
			<tr>
				<td class="tabletop" colspan="2" >申请编号:<u>&nbsp;&nbsp; &nbsp;&nbsp;<span>${plmCarApplyBuy.title}</span></u></td>
				<td class="tabletop" colspan="2">申请人:<u> &nbsp;&nbsp; &nbsp;&nbsp;<span>${plmCarApplyBuy.user.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;</u></td>
				<td class="tabletop" colspan="2">所属部门:<u> &nbsp;&nbsp; &nbsp;&nbsp;<span>${plmCarApplyBuy.user.office.name}</span>&nbsp;&nbsp;&nbsp;&nbsp;</u></td>
				<td class="tabletop" colspan="2">申请日期： <u> &nbsp;&nbsp; &nbsp;&nbsp;<span><fmt:formatDate value="${plmCarApplyBuy.createDate}" pattern="yyyy-MM-dd"/></span>&nbsp;&nbsp;&nbsp;&nbsp;</u></td>
			</tr>
		</table>	
		<table id="table" class="table table-condensed">
			<tr>
				<td class="trtop" colspan="2" style="width: 20%">购车原因</td>
				<td colspan="6">${plmCarApplyBuy.reason}</td>
			</tr>
			<tr>
				<td class="trtop" colspan="2">车辆描述</td>
				<td colspan="6">${plmCarApplyBuy.carDesc}</td>
			</tr>
			<tr>
				<td class="trtop" colspan="2">购车数量</td>
				<td colspan="6">${plmCarApplyBuy.num}台</td>
			</tr>						
			<tr>
				<td class="trtop" colspan="2" style="width: 20%">是否督办</td>
				<td colspan="2" style="width: 30%">${fns:getDictLabel(plmCarApplyBuy.plmAct.isSup, 'yes_no', '')}</td>
				<td class="trtop" colspan="2" style="width: 20%">督办人</td>
				<td colspan="2" style="width: 30%">${plmCarApplyBuy.plmAct.supExe.name}</td>
			</tr>		
			<tr>
				<td class="trtop" colspan="2">督办明细</td>
				<td colspan="6">${plmCarApplyBuy.plmAct.supDetail}</td>
			</tr>			
			<act:histoicTable procInsId="${plmCarApplyBuy.procInsId}" colspan="6" titleColspan="2"/>
		</table>
		
		<div class="form-actions">
			<c:if test="${cancelFlag == 1}">
				<a id="btn" class="btn" ><i class="icon-undo"></i>撤销</a>&nbsp;
			</c:if>
			<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i class="icon-reply"></i>返回</a>
		</div>
	</form:form>
</body>
</html>