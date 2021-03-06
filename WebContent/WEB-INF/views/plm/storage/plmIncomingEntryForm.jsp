
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>入库单管理</title>
<meta name="decorator" content="default" />
<link href="${ctxStatic}/common/zztable.css" type="text/css"
	rel="stylesheet">
	<!-- 表格试表单css -->
	<%-- <link href="${ctxStatic}/common/zzformtable.css" type="text/css"
	rel="stylesheet"> --%>
	<link href="${ctxStatic}/form/css/formTable.css" rel="stylesheet" />
<link rel="stylesheet" href="${ctxStatic}/jquery-ui-1.12.1/jquery-ui.min.css">
<script src="${ctxStatic}/jquery-ui-1.12.1/jquery-ui.min.js"></script>
<script type="text/javascript" src="${ctxStatic}/plm/storage/ajaxMessageAlert.js"></script>
<script type="text/javascript" src="${ctxStatic}/plm/storage/storageValidate.js"></script>
<style type="text/css">
   #updateDetailInfo {
     height: 98%;
     width: 100%;
     
   }
   #updateDetailInfodiv {
      padding: 0px;
     overflow: inherit;
   }
   .ui-widget.ui-widget-content {
    	background-color: rgb(18, 46, 89);
  		border-color: rgb(18, 46, 89);
   }
   .ui-draggable .ui-dialog-titlebar {
       background-color: rgb(18, 46, 89);
  	   border-color: rgb(18, 46, 89);
   }
</style>
<script type="text/javascript">
	$(function() {
		$('#btnSubmit').click(function(){
			$('#inputForm').submit();
		});
		$("#inputForm")
		.validate(
				{
					rules: {
						deliveryNumber: {
							required: true,
							maxlength : 64,
							order_num: $("#deliveryNumber").val()    //调用自定义验证
		                },
		                invoice: {
							maxlength : 20,
							order_num: $("#invoice").val()    //调用自定义验证
		                },
		                invoiceValue: {
							maxlength : 64,
							amount: $("#invoiceValue").val()    //调用自定义验证
		                }
		            },
		            
		            messages: {
		            	deliveryNumber: {
		            		required: "必填信息",
		            		maxlength: "最大长度64"
		                },
		                invoice: {
		            		maxlength: "最大长度20"
		                },
		                invoiceValue: {
		            		maxlength: "最大长度64"
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
		
		
		$( "#updateDetailInfodiv" ).dialog({
		    autoOpen: false,
		    closeOnEscape: false,
		    height: 700,
		    width: 1100,
		    modal: true,
		    close: function() {
		    	$( this ).dialog( "close" );
		    	$("#incomingEntry")[0].click();
		    }
		  });
		
		$( "#updateDetailInfoDetail" ).dialog({
		    autoOpen: false,
		    closeOnEscape: false,
		    height: 500,
		    width: 1100,
		    modal: true,
		    close: function() {
		    	$( this ).dialog( "close" );
		    	$("#incomingEntry")[0].click();
		    }
		  });
		
		$("a[title='updateDetail2']").on("click",function(){
			
			
			
			$("#updateDetailInfoDetail").attr("src",this);
			$("#updateDetailInfoDetail").dialog( "open" );
			$("#updateDetailInfoDetail").css({"width":"98%"});
			return false;
		});
		
		$("#updateDetail").on("click",function(){
			
			 $("#updateDetailInfodiv2").show();
				$("#updateDetailInfo ").hide();
			
			$("#updateDetailInfo").attr("src","${ctx}/storage/plmMinusandAddDetail/countEquipmentByType?incomingId=${plmIncomingEntry.id}");
			$("#updateDetailInfodiv").dialog( "open" );
			$("#updateDetailInfo").css({"width":"98%"});
			return false;
		});
		$("a[title='incomingEquipment']").on("click",function(){
			if (!confirm('确认要进行物资入库吗？')) {
				return false;
			}

			 $("#updateDetailInfodiv2").show();
				$("#updateDetailInfo ").hide();
			
			$("#updateDetailInfo").attr("src",this);
			$("#updateDetailInfodiv").dialog( "open" );
			$("#updateDetailInfo").css({"width":"98%"});
			return false;
		});
		$("#incomingEquipmentAll").on("click",function(){
			if (!confirm('确认要进行物资入库吗？')) {
				return false;
			}

			 $("#updateDetailInfodiv2").show();
				$("#updateDetailInfo ").hide();
			
			$("#updateDetailInfo").attr("src","${ctx}/storage/plmMinusandAddDetail/incomingEquipmentAll?id=${plmIncomingEntry.id}");
			$("#updateDetailInfodiv").dialog( "open" );
			$("#updateDetailInfo").css({"width":"98%"});
			return false;
		});
		$("#btnSubmit").on("click", function() {
			if($("#type").val() == 0) {
				$("#inputForm").submit();
			} else {
				if($("a[title='updateDetail2']").length > 0){
					$("#inputForm").submit();
				} else{
					messageAlert("请添加物资详情！", "error");
				}
			}
		});
		$("#addComplate").on("click", function() {
			$("#type").val("1");
			if($("a[title='updateDetail2']").length > 0){
				$("#inputForm").submit();
			} else{
				messageAlert("请添加物资详情！", "error");
				$("#type").val("0");
			}
		});
		
		$("#btnCancel").on("click",function(){
			window.location.href = "${ctx}/storage/plmIncomingEntry/";
		});
	});
	
	
	function del(id) {
		top.$.jBox
		.confirm(
				"您确定要删除该物资信息么？",
				"系统提示",
				function(v, h, f) {
					if (v == "ok") {
						
						$.ajax({
							url : "${ctx}/storage/plmMinusandAddDetail/delete",
							type : "post",
							data : {"id":id},
							async : true,
							cache : false,
							timeout : 30000,
							success : function(data) {
								var content = jQuery.parseJSON(data);
								
								if (content.ret != 0) {				
									messageAlert("删除失败", "error");
									return;
								}
								
								messageAlert(content.message, "success");
								window.location.reload()
							},
							error : function(jqXHR, textStatus, errorThrown) {
								messageAlert("删除失败！", "error");
								console.error("jqXHR:", jqXHR);
								console.error("textStatus:", textStatus);
								console.error("errorThrown:", errorThrown);
								
							}
						});
					}
				}, {
					buttonsFocus : 1
				});
	}
	
	function stateChangeLoad(){
		
		 $("#updateDetailInfodiv2").hide();

			$("#updateDetailInfo ").show();
			$("#updateDetailInfodiv").css({"height":"650px"});
	}
	
	function  stateChange(){
		  
		$("#updateDetailInfodiv2").hide();

		$("#updateDetailInfo ").show();
	
		

		} 
	
</script>
	<%--引入文本框外部样式--%>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/storage/plmIncomingEntry/">入库单列表</a></li>
		<li class="active" style="width: 140px"><a id="incomingEntry" class="nav-head"
			href="${ctx}/storage/plmIncomingEntry/form?id=${plmIncomingEntry.id}">入库单<shiro:hasPermission
					name="storage:plmIncomingEntry:edit">${not empty plmIncomingEntry.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="storage:plmIncomingEntry:edit">查看</shiro:lacksPermission></a></li>
	</ul>

	<form:form id="inputForm" modelAttribute="plmIncomingEntry"
		action="${ctx}/storage/plmIncomingEntry/save" method="post"
		class="form-horizontal">
		<form:hidden path="id" />
		<form:hidden path="incomingCode"/>
		<form:hidden path="type"/>
		<sys:message content="${message}" />
		<div class="control-group head_Space">
			<h2>入库信息</h2>
		</div>
		
			<table id="table" class="table   table-condensed first_table" style="table-layout:fixed;">
			<tr>
				<td class="trtop"><font color="red">*</font> </span>入库单号(系统自动生成)<span class="help-inline"></td>
				<td colspan="3">${plmIncomingEntry.incomingCode}</td>
				<td class="trtop"><span class="help-inline"><font color="red">*</font> </span>入库类别</td>
				<td colspan="4">
					<c:choose>
						<c:when test="${flag != '0'}">
							${fns:getDictLabel(plmIncomingEntry.incomingType, 'plm_incoming_type', '未知')}
						</c:when>
						<c:otherwise>
							<form:select path="incomingType" class="input-xlarge required">
							<form:option value="" label="未选择" />
							<form:options items="${fns:getDictList('plm_incoming_type')}"
								itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="trtop">入库状态<span class="help-inline"></span></td>
				<td colspan="3">
					${fns:getDictLabel(plmIncomingEntry.type, 'plm_incoming_status', '未知')}
				</td>
				<td class="trtop"><span class="help-inline"><font color="red">*</font> </span>入库日期</td>
				<td colspan="4">
					<c:choose>
						<c:when test="${flag != '0'}">
							<fmt:formatDate value="${plmIncomingEntry.incomingDate}" pattern="yyyy-MM-dd"/>
						</c:when>
						<c:otherwise>
							<input name="incomingDate" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate required"
								value="<fmt:formatDate value="${plmIncomingEntry.incomingDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true});" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="trtop"><span class="help-inline"><font color="red">*</font> </span>供货单位</td>
				<td colspan="3">
					<c:choose>
						<c:when test="${flag != '0'}">
							${plmIncomingEntry.provide.name}
						</c:when>
						<c:otherwise>
							<form:select path="provide.id" class="input-xlarge required">
								<form:option value="" label="未选择" />
								<form:options items="${provideList}" itemLabel="name"
									itemValue="id" htmlEscape="false" />
							</form:select>
						</c:otherwise>
					</c:choose>
				
				</td>
				<td class="trtop"><font color="red">*</font> </span>送货单号<span class="help-inline"></td>
				<td colspan="4">
					<c:choose>
						<c:when test="${flag != '0'}">
							${plmIncomingEntry.deliveryNumber}
						</c:when>
						<c:otherwise>
							<form:input path="deliveryNumber" htmlEscape="false"
								maxlength="64" class="input-xlarge " />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="trtop">发票单号</td>
				<td colspan="2">
					<c:choose>
						<c:when test="${flag != '0'}">
							${plmIncomingEntry.invoice}
						</c:when>
						<c:otherwise>
							<form:input path="invoice" htmlEscape="false" maxlength="20"
								class="input-xlarge " />
						</c:otherwise>
					</c:choose>
				</td>
				<td class="trtop">发票金额</td>
				<td colspan="2">
					<c:choose>
						<c:when test="${flag != '0'}">
							${plmIncomingEntry.invoiceValue}
						</c:when>
						<c:otherwise>
							<form:input path="invoiceValue" htmlEscape="false"
									maxlength="64" class="input-xlarge " />
						</c:otherwise>
					</c:choose>
				</td>
				<td class="trtop">发票日期</td>
				<td colspan="2">
					<c:choose>
						<c:when test="${flag != '0'}">
							<fmt:formatDate value="${plmIncomingEntry.billDate}"
								pattern="yyyy-MM-dd HH:mm:ss" />
						</c:when>
						<c:otherwise>
							<input name="billDate" type="text" readonly="readonly"
								maxlength="20" class="input-medium Wdate "
								value="<fmt:formatDate value="${plmIncomingEntry.billDate}" pattern="yyyy-MM-dd"/>"
								onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});" />
						</c:otherwise>
					</c:choose>
					</td>
			</tr>
			<tr>
				<td class="trtop">附件</td>
				<td colspan="8">
					
					
					<c:choose>
						<c:when test="${flag != '0'}">
							<form:hidden id="file" path="file" htmlEscape="false"
						maxlength="256" class="input-xlarge" />
					<sys:ckfinder input="file" type="files"
						uploadPath="/storage/plmIncomingEntry" selectMultiple="true"  readonly="true"/>
						</c:when>
						<c:otherwise>
							<form:hidden id="file" path="file" htmlEscape="false"
						maxlength="256" class="input-xlarge" />
					<sys:ckfinder input="file" type="files"
						uploadPath="/storage/plmIncomingEntry" selectMultiple="true" />
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
			<tr>
				<td class="trtop">备注</td>
				<td colspan="8">
					<c:choose>
						<c:when test="${flag != '0'}">
							<p>${plmIncomingEntry.remarks}</p>
						</c:when>
						<c:otherwise>
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xxlarge "/>
						</c:otherwise>
					</c:choose>
				</td>
			</tr>
		
			<c:if test="${not empty plmIncomingEntry.id}">
			
			<tr><td class="trtop" colspan="9"><h4>入库资产列表</h4></td></tr>
			<tr>
				<td class="trtop" colspan="2">仓库</td>
				<td class="trtop" colspan="2">物资名称</td>
				<td class="trtop">规格型号</td>
				<td class="trtop" colspan="2">物资类别</td>
				<td class="trtop">物资子类</td>
				<shiro:hasPermission name="storage:plmMinusandAddDetail:edit">
				<td class="trtop">操作</td>
				</shiro:hasPermission>
			</tr>
			<c:forEach items="${detailList}" var="plmMinusandAddDetail">
			<tr>
				<td colspan="2">${plmMinusandAddDetail.storage.name}</td>
				<td colspan="2">${plmMinusandAddDetail.name}</td>
				<td>${plmMinusandAddDetail.spec}</td>
				<td colspan="2">${fns:getDictLabel(plmMinusandAddDetail.typeId, 'plm_equipment_type', '')}</td>
				<td>${fns:getDictLabel(plmMinusandAddDetail.typeChild, 'plm_equipment_type_child', '')}</td>
				<c:choose>
					<c:when test="${plmMinusandAddDetail.flag == '0'}">
						<shiro:hasPermission name="storage:plmMinusandAddDetail:edit">
				<td>
				<a title="updateDetail2" href="${ctx}/storage/plmMinusandAddDetail/form?id=${plmMinusandAddDetail.id}"><i title="修改" class="icon-pencil"></i></a>
				<a  onclick="del('${plmMinusandAddDetail.id}')" ><i title="删除" class="icon-trash"></i></a>
					<c:if test="${flag == '1'}">
					 <a title="incomingEquipment"
					href="${ctx}/storage/plmMinusandAddDetail/incomingEquipment?id=${plmMinusandAddDetail.id}"><i title="物资入库" class="icon-plus"></i></a></td>
									</c:if></shiro:hasPermission>
								</c:when>
								<c:otherwise>
									<td><a title="updateDetail2" href="${ctx}/storage/plmMinusandAddDetail/form?id=${plmMinusandAddDetail.id}"><i title="查看" class="icon-file"></i></a></td>
								</c:otherwise>
							</c:choose>
			</tr>
			</c:forEach>
		</c:if>
		</table>
		<div class="form-actions">
			<shiro:hasPermission name="storage:plmIncomingEntry:edit">
				<c:if test="${flag == '0'}">
				<a id="btnSubmit" class="btn btn-primary" href="javascript:;"><i ></i>保存</a>&nbsp;
				</c:if>
				<c:if test="${not empty plmIncomingEntry.id}">
				<c:if test="${flag == '1'}">
					<a id="incomingEquipmentAll" class="btn" href="javascript:;" ><i class="icon-home"></i>全部入库</a>&nbsp;</c:if>
					<c:if test="${flag == '0'}">
					<a id="updateDetail" class="btn" href="javascript:;" style="width: 60px!important;"><i class="icon-plus"></i>添加物资</a>
					&nbsp;<a id="addComplate" class="btn" href="javascript:;" style="width: 60px!important"><i class="icon-ok-sign"></i>完成添加</a>&nbsp;</c:if>
				</c:if>
			</shiro:hasPermission>
			<a id="btnCancel" class="btn" href="javascript:;" onclick="history.go(-1)" ><i ></i>返回</a>
		</div>
	</form:form>
	
	   <div id="updateDetailInfodiv" style="overflow:none;">
	   <div id="updateDetailInfodiv2" class="progress progress-striped active" style="width: 50% ;height:10px; margin-top: 27%;margin-left: 25%;">
        <div class="bar" style="width: 100%;"></div>
       </div>
		<iframe id="updateDetailInfo" onreadystatechange="stateChange() " onload="stateChangeLoad()" class="ui-dialog-content ui-widget-content" src="">
		
		</iframe>
		</div>
		
		<!-- 查看 -->
		<iframe id="updateDetailInfoDetail" onreadystatechange="stateChange() " onload="stateChangeLoad()" class="ui-dialog-content ui-widget-content" src="">
		
		</iframe>
</body>
</html>