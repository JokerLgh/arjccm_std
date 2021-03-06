<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>实时轨迹点管理</title>
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
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/patrol/ccmTracingpoint/">数据列表</a></li>
		<shiro:hasPermission name="patrol:ccmTracingpoint:edit"><li><a href="${ctx}/patrol/ccmTracingpoint/form">数据添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmTracingpoint" action="${ctx}/patrol/ccmTracingpoint/" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form">
			<li><label>用户：</label>
				<sys:treeselect id="user" name="user.id" value="${ccmTracingpoint.user.id}" labelName="user.name" labelValue="${ccmTracingpoint.user.name}"
					title="用户" url="/sys/office/treeData?type=3" cssClass="input-small" allowClear="true" notAllowSelectParent="true"/>
			</li>
			<li><label>当前时间：</label>
				<input name="beginCurDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmTracingpoint.beginCurDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/> - 
				<input name="endCurDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmTracingpoint.endCurDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->
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
				<th>坐标</th>
				<th>用户</th>
				<th>当前时间</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="patrol:ccmTracingpoint:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmTracingpoint">
			<tr>
				<td><a href="${ctx}/patrol/ccmTracingpoint/form?id=${ccmTracingpoint.id}">
					${ccmTracingpoint.areaPoint}
				</a></td>
				<td>
					${ccmTracingpoint.user.name}
				</td>
				<td>
					<fmt:formatDate value="${ccmTracingpoint.curDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					<fmt:formatDate value="${ccmTracingpoint.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td>
					${ccmTracingpoint.remarks}
				</td>
				<shiro:hasPermission name="patrol:ccmTracingpoint:edit"><td>
    				<a class="btnList" href="${ctx}/patrol/ccmTracingpoint/form?id=${ccmTracingpoint.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/patrol/ccmTracingpoint/delete?id=${ccmTracingpoint.id}" onclick="return confirmx('确认要删除该实时轨迹点吗？', this.href)" title="删除"><i class="icon-trash"></i></a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>