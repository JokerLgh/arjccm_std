<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>重点青少年管理</title>
	<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
	<meta name="decorator" content="default" />
	<!-- 鱼骨图 -->
	<link rel="stylesheet" href="${ctxStatic}/ccm/event/css/fishBonePop.css" />
	<script type="text/javascript" src="${ctxStatic}/ccm/event/js/fishBonePop.js"></script>
	<script type="text/javascript" src="${ctxStatic}/ccm/event/js/jquery.SuperSlide.2.1.1.js"></script>
	<style>
		.hide1,.hide2{
			display: none
		}
	</style>
	<script type="text/javascript">

		$(document).ready(
				function() {
					//获取url中的参数
					function getUrlParam(name) {
						var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
						var r = window.location.search.substr(1).match(reg);  //匹配目标参数
						if (r != null) return unescape(r[2]); return null; //返回参数值
					}
					var hide1=getUrlParam('hide1');
					var hide2=getUrlParam('hide2');
					if(hide1!=null&&hide2!=null){
						if(hide1=="true"){
							$('.hide1').show();
							$('.form-actions').hide();
							$('.help-inline').hide();
							$('.input-xlarge').attr("readonly","readonly");
							$('.displayedbuttons').attr("disabled","disabled");
						}
						if(hide2=="true"){
							$('.form-actions').hide();
							$('.hide2').show();

						}
					}else{
						$('.hide1').show();
						$('.hide2').show();
					}
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
					//创建案事件历史
					var jsonString = '${documentList}';
					data = JSON.parse(jsonString);
					$(".fishBone1").fishBone(data, '${ctx}','deal');
					$(".fishBone2").fishBone(data, '${ctx}','read');
				});
	</script>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>

<form:form id="inputForm" modelAttribute="ccmHouseKym"
		   action="${ctx}/house/ccmHouseKym/save" method="post"
		   class="form-horizontal hide1">
	<h4 class="tableNamefirst color-bg6">人员基本信息</h4>
	<form:hidden path="id" />
	<form:hidden path="peopleId" value="${ccmPeople.id}" />
	<sys:message content="${message}" />
	<table class="first_table" border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
		<tr>
			<td>
				<div>
					<label class="control-label">人口类型：</label>
					<div class="controls">
							${fns:getDictLabel(ccmHouseKym.type, 'sys_ccm_people', "")}
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">姓名：</label>
					<div class="controls">
							${ccmHouseKym.name}
					</div>
				</div>
			</td>
			<td rowspan="4" width="30%" align="left" >
				<div>
					<label class="control-label">照片：</label>
					<div class="controls">
						<form:hidden id="images" path="images" htmlEscape="false" maxlength="255" class="input-xlarge" />
						<sys:ckfinder input="images" readonly="true" type="images" uploadPath="/photo/ShiYouRenKou" selectMultiple="false" maxWidth="120" maxHeight="180" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">公民身份号码：</label>
					<div class="controls">
							${ccmHouseKym.ident}
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">籍贯：</label>
					<div class="controls">
							${ccmHouseKym.censu}
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">性别：</label>
					<div class="controls">
							${fns:getDictLabel(ccmHouseKym.sex, 'sex', '')}
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">联系方式：</label>
					<div class="controls">
							${ccmHouseKym.telephone}
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">户籍门（楼）详址：</label>
					<div class="controls">
							${ccmHouseKym.domiciledetail}
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">现住门（楼）详址：</label>
					<div class="controls">
							${ccmHouseKym.residencedetail}
					</div>
				</div>
			</td>
		</tr>
	</table>

	<h4 class="tableName color-bg6">重点青少年信息</h4>
	<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;border: none ">
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>人员类型：</label>
					<div class="controls">
						<form:select path="manType" class="input-xlarge required" >
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_delp_type')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>

					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>家庭情况：</label>
					<div class="controls">
						<form:select path="famiStat"  class="input-xlarge required" >
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_famy_stat')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">监护人公民身份号码：</label>
					<div class="controls">
						<form:input path="guarPerId" htmlEscape="false" maxlength="18" class="input-xlarge card" />
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">监护人姓名：</label>
					<div class="controls">
						<form:input path="guarName" htmlEscape="false" maxlength="50" class="input-xlarge" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">与监护人关系：</label>
					<div class="controls">
						<form:select path="guarRela" class="input-xlarge" >
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('sys_ccm_fami_ties')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">监护人联系方式：</label>
					<div class="controls">
						<form:input path="guarTl" htmlEscape="false" maxlength="50" class="input-xlarge phone" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">监护人居住详址：</label>
					<div class="controls">
						<form:input path="guarAdd" htmlEscape="false" maxlength="200" class="input-xlarge" />
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>是否违法犯罪：</label>
					<div class="controls">
						<form:radiobuttons path="delinquency" items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false" class="displayedbuttons required" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">违法犯罪情况：</label>
					<div class="controls">
						<form:input path="deliCase" htmlEscape="false" maxlength="1024" class="input-xlarge " />
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">帮扶人姓名：</label>
					<div class="controls">
						<form:input path="assistName" htmlEscape="false" maxlength="50" class="input-xlarge" />
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label">帮扶人联系方式：</label>
					<div class="controls">
						<form:input path="assistTl" htmlEscape="false" maxlength="50" class="input-xlarge phone" />
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>帮扶手段：</label>
					<div class="controls">
						<form:select path="assistMethod" class="input-xlarge required" >
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_supp_tool')}" itemLabel="label" itemValue="value" htmlEscape="false" />
						</form:select>
					</div>
				</div>
			</td>
		</tr>
		<tr>
			<td>
				<div>
					<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>关注程度：</label>
					<div class="controls">
						<form:select path="atteType" class="input-xlarge required"  >
							<form:option value="" label="" />
							<form:options items="${fns:getDictList('ccm_conc_exte')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
						</form:select>
					</div>
				</div>
			</td>
			<td>
				<div>
					<label class="control-label">帮扶情况：</label>
					<div class="controls">
						<form:input path="assistCase" htmlEscape="false" maxlength="1024" class="input-xlarge " />
					</div>
				</div>
			</td>
		</tr>

		<tr>
			<td>
				<div>
					<label class="control-label">备注信息：</label>
					<div class="controls">
						<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge " />
					</div>
				</div>
			</td>
			<td>

			</td>
		</tr>
	</table>

	<%-- <table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
        <%@include file="/WEB-INF/views/include/ccmlog.jsp" %>
    </table> --%>
	<div class="form-actions">
		<shiro:hasPermission name="house:ccmHouseKym:edit">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存" />&nbsp;
		</shiro:hasPermission>
		<!-- 			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)" />
         -->		</div>
</form:form><br>
<c:if test="${documentNumber > 0}">
	<shiro:hasPermission name="log:ccmLogTail:edit">
		<h4 class="hide2">&nbsp;跟踪信息：</h4>
		<br>
		<div class="fishBone1 hide2" ></div>
	</shiro:hasPermission>
	<shiro:lacksPermission name="log:ccmLogTail:edit">
		<h4 class="hide2">&nbsp;查看跟踪信息：</h4>
		<br>
		<div class="fishBone2 hide2" ></div>
	</shiro:lacksPermission>
</c:if>
<c:if test="${documentNumber <= 0}">
	<shiro:hasPermission name="log:ccmLogTail:edit">
		<h2 class="hide2">&nbsp;&nbsp;暂无跟踪信息</h2>
		<br>
	</shiro:hasPermission>
</c:if>
</body>
</html>