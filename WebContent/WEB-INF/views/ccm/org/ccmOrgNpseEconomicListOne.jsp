<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经济运行数据管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		function saveForm(){
			var years1 = $("#years1").val();
			if(years1.length!=0){
				years1 = years1+"-01";
				$("#years1").val(years1);
			}
			var years2 = $("#years2").val();
			if(years2.length!=0){
				years2 = years2+"-01";
				$("#years2").val(years2);
			}
			$("#searchForm").submit();
			if(years1.length!=0){
				years1 = years1.substring(0,4);
				$("#years1").val(years1);
			}
			if(years2.length!=0){
				years2 = years2.substring(0,4);
				$("#years2").val(years2);
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/org/ccmOrgNpseEconomic/listEconomic?naspId=${ccmOrgNpseEconomic.naspId}">数据列表</a></li>
		<shiro:hasPermission name="org:ccmOrgNpseEconomic:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/org/ccmOrgNpseEconomic/formEconomic?naspId=${ccmOrgNpseEconomic.naspId}">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmOrgNpseEconomic" action="${ctx}/org/ccmOrgNpseEconomic/listEconomic" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input id="naspId" name="naspId" type="hidden" value="${ccmOrgNpseEconomic.naspId}"/>
		<ul class="ul-form">
			<%-- <li><label>企业id：</label>
				<form:input path="naspId" htmlEscape="false" maxlength="64" class="input-medium"/>
			</li> --%>
			<li><label>年：</label>
				<input name="beginYears" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" id="years1"
					value="<fmt:formatDate value="${ccmOrgNpseEconomic.beginYears}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/> - 
				<input name="endYears" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" id="years2"
					value="<fmt:formatDate value="${ccmOrgNpseEconomic.endYears}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="button" value="查询"/></li> -->
			<li class="btns">
			<a href="javascript:;" id="btnSubmit" class="btn btn-primary"  onclick="saveForm()">
                <i class="icon-search"></i> 查询 </a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	
	<label style="margin-left: 20px">经济组织名称：${ccmOrgNpse.compName}</label>
	<label style="margin-left: 20px">工商执照注册号：${ccmOrgNpse.compId}</label>
				
	
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年</th>
				<th>营业额（万元）</th>
				<th>净利润（万元）</th>
				<th>纳税总额（万元）</th>
				<th>固定资产总额（万元）</th>
				<th>负债（万元）</th>
				<shiro:hasPermission name="org:ccmOrgNpseEconomic:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmOrgNpseEconomic">
			<tr>
				<td><a href="${ctx}/org/ccmOrgNpseEconomic/formEconomic?id=${ccmOrgNpseEconomic.id}">
					<fmt:formatDate value="${ccmOrgNpseEconomic.years}" pattern="yyyy"/>
				</a></td>
				<td>
					${ccmOrgNpseEconomic.turnover}
				</td>
				<td>
					${ccmOrgNpseEconomic.netMargin}
				</td>
				<td>
					${ccmOrgNpseEconomic.taxes}
				</td>
				<td>
					${ccmOrgNpseEconomic.fixedAssets}
				</td>
				<td>
					${ccmOrgNpseEconomic.liabilities}
				</td>
				<shiro:hasPermission name="org:ccmOrgNpseEconomic:edit"><td>
    				<a class="btnList" href="${ctx}/org/ccmOrgNpseEconomic/formEconomic?id=${ccmOrgNpseEconomic.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/org/ccmOrgNpseEconomic/deleteEconomic?id=${ccmOrgNpseEconomic.id}" onclick="return confirmx('确认要删除该经济运行数据吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>