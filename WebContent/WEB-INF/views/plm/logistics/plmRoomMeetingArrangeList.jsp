<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>会议室管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$('#btnSubmit').click(function(){
				$('#searchForm').submit();
			});
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
	<style type="text/css">
        img {
		   	width: 130px;
		    max-width: 150px;
		}
		p {
		    margin: 0 27px 10px;
		}
    </style>	
</head>
<body>
<%--<img  src="${ctxStatic}/images/shouyedaohang.png"; class="nav-home">--%>
<%--<span class="nav-position">当前位置 ：</span><span class="nav-menu"><%=session.getAttribute("activeMenuName")%>></span><span class="nav-menu2">事件管理</span>--%>
<ul class="back-list">
	<ul class="nav nav-tabs">
		<li class="active" style="width: 124px"><a class="nav-head" href="${ctx}/logistics/plmRoomMeetingApplyResource/getroombyuserId">会议室安排</a></li>
		<shiro:hasPermission name="logistics:plmRoom:edit"><li><a style="text-align: center" href="${ctx}/logistics/plmRoomMeetingApplyResource/form?update=ok">会议安排添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="plmRoomApply" action="${ctx}/logistics/plmRoomMeetingApplyResource/getroombyuserId" method="post" class="breadcrumb form-search clearfix">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<ul class="ul-form pull-left">
			<li class="first-line"><label>会议类型：</label>
				<form:select path="type" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('plm_room_apply_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
			<li/>
			<li class="first-line"><label>会议名称：</label>
				<form:input path="subject" htmlEscape="false" maxlength="50" class=""  cssStyle="width:158px"/>
			<li/>
			<li class="first-line"><label>会议地点：</label>
				<form:select path="room" class="input-medium">
					<form:option value="" label="全部"/>
					<form:options items="${roomlist}" itemLabel="subject" itemValue="id" htmlEscape="false"/>
				</form:select>
			<li/>
			<li class="first-line"><label>开始日期：</label>
				<input name="startTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${plmRoomApply.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>
			<li class="first-line"><label>结束日期：</label>
				<input name="endTime" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${plmRoomApply.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false});"/>
			</li>


		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<div class="clearfix pull-right btn-box">
			<a href="javascript:void(0);" id="btnSubmit" class="btn btn-primary"style="width: 49px;display:inline-block;float: right;margin-left: 20px;margin-right: 14px;margin-bottom: 20px" onclick="return page();">
				<i></i> <span style="font-size: 12px">查询</span>
			</a>
	</div>
	<div id="prodInfo_List">
		<table id="contentTable" class="table table-striped table-bordered table-condensed table-gradient" >
			<thead>
				<tr>
					<th>会议名称</th>
					<th>会议类型</th>
					<th>会议地点</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>申请人</th>
					<shiro:hasPermission name="logistics:plmRoom:edit"><th>操作</th></shiro:hasPermission>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${page.list}" var="plmRoom">
				<tr>
					<td style="height: 50px">
						<a href="${ctx}/logistics/plmRoomMeetingApplyResource/form?id=${plmRoom.id}&update=ok">${plmRoom.subject}</a>
					</td>
					<td style="height: 50px">
						${fns:getDictLabel(plmRoom.type, 'plm_room_apply_type', '')}
					</td>
					<td style="height: 50px">
						${plmRoom.room.subject}
					</td>
					<td style="height: 50px">
						<fmt:formatDate value="${plmRoom.startTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="height: 50px">
						<fmt:formatDate value="${plmRoom.endTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
					</td>
					<td style="height: 50px">
						${plmRoom.createBy.name}
					</td>
					<td style="height: 50px">
					<shiro:hasPermission name="logistics:plmRoom:edit">
						<c:if test="${user.id eq plmRoom.createBy.id or user.id eq '1'}">
							<a class="btnList" href="${ctx}/logistics/plmRoomMeetingApplyResource/form?id=${plmRoom.id}&update=ok" title="修改"><i class="icon-pencil"></i></a>
							<a class="btnList" href="${ctx}/logistics/plmRoomMeetingApplyResource/delete?id=${plmRoom.id}" onclick="return confirmx('确认要删除该会议安排吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
		    				<a class="btnList" onclick="parent.parent.LayerDialog('${ctx}/logistics/plmRoomMeetingApplyResource/getroomresource?id=${plmRoom.id}', '附件上传', '800px', '660px')" title="附件上传"><i class="icon-plus"></i></a>
	    				    <a class="btnList" onclick="parent.parent.LayerDialog('${ctx}/logistics/plmRoomMeetingApplyResource/findlistresource?meetingId=${plmRoom.id}', '附件列表', '800px', '660px')"><i title="附件列表" class="icon-list-ul"></i></a>
						</c:if>
						<c:if test="${user.id ne plmRoom.createBy.id and user.id ne '1'}">
							<a class="btnList" href="${ctx}/logistics/plmRoomMeetingApplyResource/form?id=${plmRoom.id}" title="详情"><i class="icon-file"></i></a>
						</c:if>
					</shiro:hasPermission>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>
	<div class="pagination" style="float: right; margin-top: 12px">${page}</div>
</ul>
</body>
</html>