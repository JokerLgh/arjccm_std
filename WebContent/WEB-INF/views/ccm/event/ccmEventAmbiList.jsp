<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>矛盾纠纷登记</title>
	<meta name="decorator" content="default"/>
	<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
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
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">矛盾纠纷排查</span>--%>
<div class="back-list">
<div class="context" content="${ctx}"></div>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" href="${ctx}/event/ccmEventAmbi/map">矛盾纠纷统计</a></li>
		<li class="active" style="width: 140px"><a class="nav-head"  href="${ctx}/event/ccmEventAmbi/">矛盾纠纷列表</a></li>
		<shiro:hasPermission name="event:ccmEventAmbi:edit"><li><a style="width: 140px;text-align:center" href="${ctx}/event/ccmEventAmbi/form">矛盾纠纷添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="ccmEventAmbi" action="${ctx}/event/ccmEventAmbi/" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<ul class="ul-form pull-left">
			<li class="first-line"><label>矛盾纠纷名称：</label>
				<form:input path="name" htmlEscape="false" maxlength="100" class="input-medium"/>
			</li>
			<li class="first-line"><label >开始时间：</label> <input
					name="beginSendDate" id="beginSendDate" type="text" readonly="readonly"
					maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${ccmEventAmbi.beginSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,maxDate:'#F{$dp.$D(\'endSendDate\')||\'%y-%M-%d\'}'});" /></li>
			<li class="first-line"><label>结束时间：</label><input name="endSendDate" id="endSendDate"  type="text" readonly="readonly"
										   maxlength="20" class="input-medium Wdate"
										   value="<fmt:formatDate value="${ccmEventAmbi.endSendDate}" pattern="yyyy-MM-dd HH:mm:ss"/>"
										   onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,minDate:'#F{$dp.$D(\'beginSendDate\')}' ,maxDate:'%y-%M-%d'});" />
			</li>
			<li class="first-line"><label>所属网格：</label>
				<sys:treeselect id="area" name="area.id" value="${ccmEventAmbi.area.id}" labelName="area.name" labelValue="${ccmEventAmbi.area.name}"
					title="区域" url="/tree/ccmTree/treeDataArea?type=7&areaid=" cssClass="input-medium" allowClear="true" notAllowSelectParent="true"/>
			</li>

			<li class="first-line"><label>矛盾纠纷规模：</label>
				<form:select path="eventScale" class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_event_scale')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="second-line"><label>矛盾纠纷类别：</label>
				<form:select path="eventType" class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_event_sort')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			<li class="second-line"><label>处理状态：</label>
				<form:select path="status" class="input-medium">
					<form:option value="" label="全部" />
					<form:options items="${fns:getDictList('ccm_event_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			</li>
			

			<!-- <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li> -->

		</ul>


	<div class="clearfix pull-right btn-box">
		<a href="javascript:;" id="btnSubmit" class="btn btn-primary" style="width: 49px;display:inline-block;float: right;">
			<i></i><span style="font-size: 12px">查询</span>  </a>
	</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient">
		<thead>
			<tr>
				<th>矛盾纠纷名称</th>
				<th>所属网格</th>
				<th>发生时间</th>
				<th>矛盾纠纷规模</th>
				<th>矛盾纠纷类别</th>
				<th>处理状态</th>
				<th>主要当事人姓名</th>
				<shiro:hasPermission name="event:ccmEventAmbi:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmEventAmbi">
			<tr>
				<td style="height: 50px"><a href="${ctx}/event/ccmEventAmbi/form?id=${ccmEventAmbi.id}">
					${ccmEventAmbi.name}
				</a></td>
				<td style="height: 50px">
					${ccmEventAmbi.area.name}
				</td>
				<td style="height: 50px">
					<fmt:formatDate value="${ccmEventAmbi.sendDate}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmEventAmbi.eventScale, 'ccm_event_scale', '')}
				</td>
				<td style="height: 50px">
					${fns:getDictLabel(ccmEventAmbi.eventType, 'ccm_event_sort', '')}
				</td>
				<td style="height: 50px"><span class="eventScaleCss statusCss-${ccmEventAmbi.status}">
					${fns:getDictLabel(ccmEventAmbi.status, 'ccm_event_status', '')}
					</span>
				</td>
				<td style="height: 50px">
					${ccmEventAmbi.partName}
				</td>
				<td style="height: 50px"><shiro:hasPermission name="event:ccmEventAmbi:edit">
    				<a class="btnList" href="${ctx}/event/ccmEventAmbi/form?id=${ccmEventAmbi.id}" title="修改"><i class="icon-pencil"></i></a>
					<a class="btnList" href="${ctx}/event/ccmEventAmbi/delete?id=${ccmEventAmbi.id}" onclick="return confirmx('确认要删除该矛盾纠纷排查化解吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
					<a class="btnList" href="javascript:;" onclick="LocationOpen('${ccmEventAmbi.id}')"  title="位置信息"><i class="icon-map-marker "></i></a>
			
				</shiro:hasPermission> 
				<!--  
				<shiro:hasPermission name="log:ccmLogTail:edit">
							<a	class="btnList"
								href="${ctx}/log/ccmLogTail/formPro?relevance_id=${ccmEventAmbi.id}&relevance_table=ccm_event_ambi" title="添加记录"><i class="icon-plus"></i></a>
				</shiro:hasPermission>-->
				<!-- 事件处理 编辑权限  --> 
				<%-- <shiro:hasPermission name="event:ccmEventCasedeal:edit">
					<a class="btnList" onclick="LayerDialogWithReload('${ctx}/event/ccmEventCasedeal/dealformCommon?objType=ccm_event_ambi&objId=${ccmEventAmbi.id}', '处理', '700px', '500px')" title="添加处理"><i class="icon-plus"></i></a>
				</shiro:hasPermission> --%>
						
				</td>
				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</body>
</html>