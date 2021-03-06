<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>个人通讯录分组管理</title>
	<meta name="decorator" content="default"/>
	<%@include file="/WEB-INF/views/include/treetable.jsp" %>
	<script type="text/javascript">
		$(document).ready(function() {
			var tpl = $("#treeTableTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
			var data = ${fns:toJson(list)}, ids = [], rootIds = [];
			for (var i=0; i<data.length; i++){
				ids.push(data[i].id);
			}
			ids = ',' + ids.join(',') + ',';
			for (var i=0; i<data.length; i++){
				if (ids.indexOf(','+data[i].parentId+',') == -1){
					if ((','+rootIds.join(',')+',').indexOf(','+data[i].parentId+',') == -1){
						rootIds.push(data[i].parentId);
					}
				}
			}
			for (var i=0; i<rootIds.length; i++){
				addRow("#treeTableList", tpl, data, rootIds[i], true);
			}
			$("#treeTable").treeTable({expandLevel : 5});
		});
		function addRow(list, tpl, data, pid, root){
			for (var i=0; i<data.length; i++){
				var row = data[i];
				if ((${fns:jsGetVal('row.parentId')}) == pid){
					$(list).append(Mustache.render(tpl, {
						dict: {
						blank123:0}, pid: (root?0:pid), row: row
					}));
					addRow(list, tpl, data, row.id);
				}
			}
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/addressbook/plmEmployeeGroups/">个人通讯录分组列表</a></li>
		<shiro:hasPermission name="addressbook:plmEmployeeGroups:edit"><li><a href="${ctx}/addressbook/plmEmployeeGroups/form">个人通讯录分组添加</a></li></shiro:hasPermission>
	</ul>
	<sys:message content="${message}"/>
	<table id="treeTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
				<th>分组名字</th>
				<th>更新时间</th>
				<th>备注信息</th>
				<shiro:hasPermission name="addressbook:plmEmployeeGroups:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody id="treeTableList"></tbody>
	</table>
	<script type="text/template" id="treeTableTpl">
		<tr id="{{row.id}}" pId="{{pid}}">
			<td><a href="${ctx}/addressbook/plmEmployeeGroups/form?id={{row.id}}">
				{{row.name}}
			</a></td>
			<td>
				{{row.updateDate}}
			</td>
			<td>
				{{row.remarks}}
			</td>
			<shiro:hasPermission name="addressbook:plmEmployeeGroups:edit"><td>
   				<a class="btnList" href="${ctx}/addressbook/plmEmployeeGroups/form?id={{row.id}}" title="修改"><i class="icon-pencil"></i></a>
				<a class="btnList" href="${ctx}/addressbook/plmEmployeeGroups/delete?id={{row.id}}" onclick="return confirmx('确认要删除该个人通讯录分组及所有子个人通讯录分组吗？', this.href)" title="删除"><i class="icon-remove-sign"></i></a>
				<a class="btnList" href="${ctx}/addressbook/plmEmployeeGroups/form?parent.id={{row.id}}"><i title="添加下级个人通讯录分组" class="icon-plus"></i></a> 
			</td></shiro:hasPermission>
		</tr>
	</script>
</body>
</html>