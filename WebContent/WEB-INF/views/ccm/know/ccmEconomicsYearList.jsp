<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>经济运行数据-年管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#btnSubmit").on("click" ,function(){
				$("#searchForm").submit();
			})
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
		<li class="active"><a href="${ctx}/know/ccmEconomicsYear/">数据列表</a></li>
		<shiro:hasPermission name="know:ccmEconomicsYear:edit"><li><a href="${ctx}/know/ccmEconomicsYear/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmEconomicsYear" action="${ctx}/know/ccmEconomicsYear/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>年：</label>
				<input name="beginYears" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" id="years1"
					value="<fmt:formatDate value="${ccmEconomicsYear.beginYears}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/> - 
				<input name="endYears" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate" id="years2"
					value="<fmt:formatDate value="${ccmEconomicsYear.endYears}" pattern="yyyy"/>"
					onclick="WdatePicker({dateFmt:'yyyy',isShowClear:false});"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" onclick="saveForm()" type="button" value="查询"/></li> -->
			<li class="btns">
			<a href="javascript:;" id="btnSubmit" class="btn btn-primary">
                <i class="icon-search"></i> 查询 </a>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>年</th>
				<th>任务目标（亿元）</th>
				<th>工业总产值（亿元）</th>
				<th>固定资产投资（亿元）</th>
				<th>招商引资（亿元）</th>
				<th>商业零售（亿元）</th>
				<th>生产总值（亿元）</th>
				<shiro:hasPermission name="know:ccmEconomicsYear:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmEconomicsYear">
			<tr>
				<td><a href="${ctx}/know/ccmEconomicsYear/form?id=${ccmEconomicsYear.id}">
					<fmt:formatDate value="${ccmEconomicsYear.years}" pattern="yyyy"/>
				</a></td>
				<td>
					${ccmEconomicsYear.goal}
				</td>
				<td>
					${ccmEconomicsYear.industrial}
				</td>
				<td>
					${ccmEconomicsYear.invest}
				</td>
				<td>
					${ccmEconomicsYear.imports}
				</td>
				<td>
					${ccmEconomicsYear.retail}
				</td>
				<td>
					${ccmEconomicsYear.production}
				</td>
				<shiro:hasPermission name="know:ccmEconomicsYear:edit"><td>
    				<a class="btnList" href="${ctx}/know/ccmEconomicsYear/form?id=${ccmEconomicsYear.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/know/ccmEconomicsYear/delete?id=${ccmEconomicsYear.id}" onclick="return confirmx('确认要删除该经济运行数据-年吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>