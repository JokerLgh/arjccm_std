<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>日志管理</title>
	<meta name="decorator" content="default"/>
	<style type="text/css">
		html {
			overflow-x: auto !important;
		}
	</style>
	<script type="text/javascript">
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
	    	return false;
	    }
		$(document).ready(function() {
			$("#btnSubmit").on("click" ,function(){
				$("#searchForm").submit();
			})
		});
		</script>

<script type="text/javascript">
$(function() {
	$("#btnExport").click(
			function() {
				top.$.jBox.confirm("确认要导出数据吗？", "系统提示", function(v, h, f) {
					if (v == "ok") {
						var contexturl = $(".context").attr("content");
						var a=$('input:checkbox[name="check"]:checked');
						var arr = new Array();
						if (a.length == 0){
							alert('没有选择项');
							return;
						}
						for(var i=0;i<a.length;i++)
						{
							arr.push(a[i].value);
						}
						
						if(arr.length >0){
							$("#ids").val(arr);
							$("#searchForm").attr("action",contexturl + "/sys/log/export/");
							$("#searchForm").submit();
							$("#searchForm").attr("action","${ctx}/sys/log/");
						}
					}
				}, {
					buttonsFocus : 1
				});
				top.$('.jbox-body .jbox-icon').css('top', '55px');
			});
});

</script>
</head>
<body>
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">日志管理</span>--%>
<div class="back-list">
<!-- 	<ul class="nav nav-tabs"> -->
<%-- 		<li class="active"><a href="${ctx}/sys/log/">日志列表</a></li> --%>
<!-- 	</ul> -->
	<div class="context" content="${ctx}"></div>
	 <form:form id="searchForm" action="${ctx}/sys/log/" method="post" class="breadcrumb form-search" enctype="multipart/form-data">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<input type="hidden" id="ids" name="ids">
		<ul class="ul-form">
			<li class="first-line"><label>操作菜单：</label><input id="title" name="title" type="text" maxlength="50" class="input-medium" value="${log.title}"/></li>
			<li class="first-line"><label>操作用户：</label><input id="createBy.name" name="createBy.name" type="text" maxlength="50" class="input-medium" value="${log.createBy.name}"/></li>
			<li class="first-line"><label>URI：</label><input id="requestUri" name="requestUri" type="text" maxlength="50" class="input-medium" value="${log.requestUri}"/></li>

			<li class="first-line"><label>开始日期：</label>
			<input id="beginDate" name="beginDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
				   value="<fmt:formatDate value="${log.beginDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></li>
			<li class="first-line"><label>结束日期：</label>
				<input id="endDate" name="endDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					   value="<fmt:formatDate value="${log.endDate}" pattern="yyyy-MM-dd"/>" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/></li>
			<li class="first-line"><label for="exception"><input id="exception" name="exception" type="checkbox"${log.exception eq '1'?' checked':''} value="1"/>只查询异常信息</label>
			<!-- <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/> -->

			</li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<ul>

		<shiro:hasPermission name="ccmsys:ccmDevice:edit">
			<a href="javascript:;" id="btnExport" class="btn btn-export" style="width: 49px;display:inline-block;float: right;margin-left: 20px;margin-bottom: 20px;margin-right: 14px">
				<i></i> <span style="font-size: 12px">导出</span>
			</a>
		</shiro:hasPermission>
		<a href="javascript:;" id="btnSubmit" class="btn btn-primary" style="width: 49px;display:inline-block;float: right;margin-left: 20px;margin-bottom: 20px">
			<i></i><span style="font-size: 12px">查询</span>  </a>
	</ul>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead><tr><th>操作菜单</th><th>操作用户</th><th>所在组织机构</th><th>所在部门</th><th>URI</th><th>提交方式</th><th>操作者IP</th><th>操作时间</th></thead>
		<tbody><%request.setAttribute("strEnter", "\n");request.setAttribute("strTab", "\t");%>
		<c:forEach items="${page.list}" var="log">
			<tr>
		    	<input name="id" type="hidden" value="${log.id}"/>
				<td style="height: 50px"><input name="check" type="checkbox" value="${log.id}"/>${log.title}</td>
				<td style="height: 50px">${log.createBy.name}</td>
				<td style="height: 50px">${log.createBy.company.name}</td>
				<td style="height: 50px">${log.createBy.office.name}</td>
				<td style="height: 50px"><strong>${log.requestUri}</strong></td>
				<td style="height: 50px">${log.method}</td>
				<td style="height: 50px">${log.remoteAddr}</td>
				<td style="height: 50px"><fmt:formatDate value="${log.createDate}" type="both"/></td>
			</tr>
			<c:if test="${not empty log.exception}"><tr>
				<td colspan="8" style="word-wrap:break-word;word-break:break-all;">
<%-- 					用户代理: ${log.userAgent}<br/> --%>
<%-- 					提交参数: ${fns:escapeHtml(log.params)} <br/> --%>
					异常信息: <br/>
					${fn:replace(fn:replace(fns:escapeHtml(log.exception), strEnter, '<br/>'), strTab, '&nbsp; &nbsp; ')}</td>
			</tr></c:if>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>