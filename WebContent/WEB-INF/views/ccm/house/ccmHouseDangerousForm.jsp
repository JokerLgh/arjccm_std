<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>危险品从业人员管理</title>
	<meta name="decorator" content="default"/>
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
		$(document).ready(function() {
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
			//跟踪信息
			var jsonString = '${documentList}';
            data = JSON.parse(jsonString);  
			$(".fishBone1").fishBone(data, '${ctx}','deal');
			$(".fishBone2").fishBone(data, '${ctx}','read');
		});
	</script>
	<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
</head>
<body>
<%-- 	<ul class="nav nav-tabs">
		<li><a href="${ctx}/report/ccmPeopleStat/statisticsPage?title=ccmPeopleStatDangerous">危险品从业人员人口统计</a></li>
		<li><a   href="javascript:;" data-href="${ctx}/house/ccmHouseDangerous" onclick="HasSecret(this)">危险品从业人员列表</a></li>
		<li class="active"><a href="${ctx}/house/ccmHouseDangerous/form?id=${ccmHouseDangerous.id}">危险品从业人员<shiro:hasPermission name="house:ccmHouseDangerous:edit">${not empty ccmHouseDangerous.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="house:ccmHouseDangerous:edit">查看</shiro:lacksPermission></a></li>
		<li><a
			href="${ctx}/log/ccmLogTail/formPro?relevance_id=${ccmHouseDangerous.id}&relevance_table=ccm_house_dangerous">跟踪信息<shiro:hasPermission
					name="log:ccmLogTail:edit">${not empty ccmLogTail.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="log:ccmLogTail:edit">查看</shiro:lacksPermission></a></li>
	</ul> --%>

	<form:form id="inputForm" modelAttribute="ccmHouseDangerous" action="${ctx}/house/ccmHouseDangerous/save" method="post" class="form-horizontal hide1">
		<h4 class="tableNamefirst color-bg6">人员基本信息</h4>
		<form:hidden path="id" />
		<form:hidden path="peopleId" value="${ccmHouseDangerous.peopleId}" />
		<sys:message content="${message}" />
		<table class="first_table" border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div>
						<label class="control-label">人口类型：</label>
						<div class="controls">
							${fns:getDictLabel(ccmHouseDangerous.type, 'sys_ccm_people', "")}
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">姓名：</label>
						<div class="controls">
							${ccmHouseDangerous.name}
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
							${ccmHouseDangerous.ident}
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">籍贯：</label>
						<div class="controls">
							${ccmHouseDangerous.censu}
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">性别：</label>
						<div class="controls">
							${fns:getDictLabel(ccmHouseDangerous.sex, 'sex', '')}
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">联系方式：</label>
						<div class="controls">
							${ccmHouseDangerous.telephone}
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">户籍详细地址：</label>
						<div class="controls">
							${ccmHouseDangerous.domiciledetail}
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">现住详细地址：</label>
						<div class="controls">
							${ccmHouseDangerous.residencedetail}
						</div>
					</div>
				</td>
			</tr>
		</table>

		<h4 class="tableName color-bg6">危险品从业人员信息</h4>
		<table border="0px" style="border-color: #CCCCCC; border: 0px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>工作单位：</label>
						<div class="controls">
							<form:input path="workUnit" htmlEscape="false" maxlength="100" class="input-xlarge required"/>

						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">工作单位电话：</label>
						<div class="controls">
							<form:input path="workPhone" htmlEscape="false" maxlength="64" class="input-xlarge phone"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">工作单位地点：</label>
						<div class="controls">
							<form:input path="workPlace" htmlEscape="false" maxlength="100" class="input-xlarge "/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>关注程度：</label>
						<div class="controls">
							<form:select path="atteType" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ccm_conc_exte')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>危险品类别：</label>
						<div class="controls">
							<form:select path="goodsType" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ccm_house_dangerous_goods')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label"><span class="help-inline"><font color="red">*</font> </span>工作类别：</label>
						<div class="controls">
							<form:select path="workType" class="input-xlarge required">
								<form:option value="" label=""/>
								<form:options items="${fns:getDictList('ccm_house_dangerous_work')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
							</form:select>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">备注信息：</label>
						<div class="controls">
							<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="255" class="input-xlarge "/>
						</div>
					</div>
				</td>
				<td>
				</td>
			</tr>
		</table>
		
	<%-- 	<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<%@include file="/WEB-INF/views/include/ccmlog.jsp" %>
		</table> --%>
		<div class="form-actions">
			<shiro:hasPermission name="house:ccmHouseDangerous:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
<!-- 			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
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