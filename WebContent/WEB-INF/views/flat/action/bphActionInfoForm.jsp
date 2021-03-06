<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
<title>执行动作配置管理</title>
<meta name="decorator" content="default" />
<link
	href="${ctxStatic}/jquery-ztree/3.5.12/css/zTreeStyle/zTreeStyle.min.css"
	rel="stylesheet" type="text/css" />
<script src="${ctxStatic}/common/common.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/alarm.js" type="text/javascript"></script>
<script
	src="${ctxStatic}/jquery-ztree/3.5.12/js/jquery.ztree.all-3.5.js"
	type="text/javascript"></script>
<%--引入文本框外部样式--%>
<link href="/arjccm/static/bootstrap/2.3.1/css_input/input_Custom.css" type="text/css" rel="stylesheet">
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						//编辑
						$('#type1').attr('checked', 'true');
						$('#type1').on('click', function() {
							/* document.getElementById("divHide").style.display = "none"; */
							$("#firstTb tr").remove();
						})
						/* $('#type2').on('click', function() {
							document.getElementById("divHide").style.display = "block";
						}) */
						<c:forEach items="${bphActionUserList}" var="bphActionUser">
						$('#treeNmae')
								.append(
										'<input name="userId" type="hidden" val="u_${bphActionUser.userId}"/>"${bphActionUser.uName}"<br/>');
						</c:forEach>
						<c:forEach items="${bphFeedbackInfoList}" var="bphFeedbackInfo">
						EditTr("${bphFeedbackInfo.feedbackType}",
								"${bphFeedbackInfo.feedbackTitle}")
						</c:forEach>
						var editBphActionInfoType = $('#editBphActionInfoType')
								.val();
						if (editBphActionInfoType !== '') {
							//编辑
							$('#type' + editBphActionInfoType).click();
						}
					});
	function RemoveTr(_this) {
		var $trNode = $(_this).parent().parent();
		var textContext = $trNode.find("td:first").text().trim();
		/* var flag = confirm("确定删除\"" + textContext + "\"吗？");
		if (flag) { */
		$trNode.remove();
		/* } */
	}
	//保存提交数据
	function saveData() {
		var json = {};
		var datas = [];
		var users = [];
		var id = $('#actionId').val();
		json.id = id;
		var name = $('#name').val();
		if (name === undefined || name == null || name == '') {
			$.jBox.tip('动作名称不能为空');
			return false;
		}
		json.name = name;
		var title = $('#title').val();
		if (title === undefined || title == null || title == '') {
			$.jBox.tip('默认通知标题不能为空');
			return false;
		}
		json.title = title;
		/* var type = $('input[name="type"]:checked').val(); */
		var type = jQuery("#type  option:selected").val();
		json.type = type;
		var content = $('#content').val();
		json.content = content;
		var remarks = $('#remarks').val();
		json.remarks = remarks;
		var userId = $('input[name=userId]');
		for (var j = 0; j < userId.length; j++) {
			var user = {};
			var userIds = userId[j].getAttribute('val');
			user.userId = userIds;
			users.push(user);
		}
		json.users = users;
		var param = encodeURI(JSON.stringify(json));
		$.post('${ctx}/action/bphActionInfo/save', {
			'param' : param
		}, function(data) {
			$('#bphActionInfoList span').click();
		})

	}
</script>
<style type="text/css">
.ztree {
	overflow: auto;
	margin: 0;
	_margin-top: 10px;
	padding: 10px 0 0 10px;
}

#left {
	background: none
}

.accordion-heading {
	background-image: none
}

input {
	border: 1px solid #ccc;
	border-radius: 2px;
	color: #000;
	font-family: 'Opan Sans', sans-serif;
	font-size: 1em;
	height: 50px;
	padding: 0 16px;
	transition: background 0.3s ease-in-out;
	width: 100%;
}

input:focus {
	outline: none;
	border-color: #9ecaed;
}
textarea.input-xxlarge {
	width: 460px !important;
}

</style>

</head>
<body>
	<ul class="nav nav-tabs">
		<li><a style="width: 140px;text-align:center" id="bphActionInfoList" href="${ctx}/action/bphActionInfo/"><span>执行动作配置列表</span></a></li>
		<li class="active" style="width: 140px"><a class="nav-head"
			href="${ctx}/action/bphActionInfo/form?id=${bphActionInfo.id}">执行动作配置<shiro:hasPermission
					name="action:bphActionInfo:edit">${not empty bphActionInfo.id?'修改':'添加'}</shiro:hasPermission>
				<shiro:lacksPermission name="action:bphActionInfo:edit">查看</shiro:lacksPermission></a></li>
	</ul>

	<form:form id="inputForm" modelAttribute="bphActionInfo" action=""
		method="post" class="form-horizontal">
		<input type="hidden" value="${bphActionInfo.type}"
			id="editBphActionInfoType" />
		<form:hidden path="id" id="actionId" />
		<sys:message content="${message}" />
		<div class="control-group head_Space" >
			<label class="control-label"><span id="nameSpan" class="help-inline"><font color="red">*</font></span>动作名称：</label>
			<div class="controls">
				<form:input id="name" path="name" htmlEscape="false" maxlength="80"
					class="input-xlarge" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作类型：</label>
			<div class="controls">
				<form:select path="type" class="input-xlarge">
					<form:option value="" label="请选择" />
					<form:options items="${fns:getDictList('bph_action_type')}"
						itemLabel="label" itemValue="value" htmlEscape="false"
						class="required" />
				</form:select>

			</div>
		</div>
		<div class="control-group">
			<label class="control-label"><span id="titleSpan" class="help-inline"><font color="red">*</font></span>默认通知标题：</label>
			<div class="controls">
				<form:input id="title" path="title" htmlEscape="false"
					maxlength="80" class="input-xlarge " />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">默认通知内容：</label>
			<div class="controls">
				<form:textarea id="content" path="content" htmlEscape="false"
					rows="6" maxlength="500" class="input-xxlarge " />
			</div>
		</div>
		<div class="control-group"
			style="display: inline-block; width: 800px; height: 200px;">
			<label class="control-label">默认发送人：</label>
			<div id="treeDiv"
				style="float: left; width: 300px; height: 180px; overflow: auto;">
				<div id="ztree" class="ztree"></div>
			</div>
			<div id="treeNmae"
				style="float: left; width: 180px; height: 180px; border: 1px solid rgb(204, 204, 204);overflow-x: hidden;overflow-y: scroll;">
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">动作描述：</label>
			<div class="controls">
				<form:textarea id="remarks" path="remarks" htmlEscape="false"
					rows="6" maxlength="255" class="input-xxlarge " />
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="action:bphActionInfo:edit">
				<!-- 	<input class="btn btn-primary" style="height:30px;" type="button" value="保存" onclick="saveData();" /> -->
				<a id="btnSubmit" class="btn btn-primary" href="javascript:;"
					onclick="saveData();"><i ></i>保 存</a>
				&nbsp;</shiro:hasPermission>
			<!-- <input id="btnCancel" class="btn" style="height:30px;" type="button" value="返 回" onclick="history.go(-1)" /> -->
			<a id="btnCancel" class="btn  btn-back"  href="javascript:;"
				onclick="history.go(-1)"><i ></i>返 回</a>
		</div>
	</form:form>
	<script>
		$(function() {
			var key, lastValue = "", nodeList = [], type = getQueryString(
					"type", "/sys/office/treeData?type=3");
			var tree, setting = {
				data : {
					simpleData : {
						enable : true,
						idKey : "id",
						pIdKey : "pId",
						rootPId : '0'
					}
				},
				check : {
					enable : true,
					chkStyle : "checkbox",
					chkboxType : {
						"Y" : "s",
						"N" : "s"
					}
				},
				async : {
					enable : (type == 3),
					url : "${ctx}/sys/user/treeData",
					autoParam : [ "id=officeId" ]
				},
				callback : {
					onCheck : function(e, treeId, treeNode) {
						var nodes = tree.getCheckedNodes(true);
						$('#treeNmae').html('')
						for (var i = 0, l = nodes.length; i < l; i++) {
							var id = nodes[i].id;
							if (id.indexOf("u_") > -1) {
								$('#treeNmae').append(
										'<input name="userId" type="hidden" val="'+id+'"/>"'
												+ nodes[i].name + '"<br/>');
							}
						}
						return false;
					}
				},
				onAsyncSuccess : function(event, treeId, treeNode, msg) {
					var nodes = tree.getNodesByParam("pId", treeNode.id, null);
					for (var i = 0, l = nodes.length; i < l; i++) {
						try {
							tree.checkNode(nodes[i], treeNode.checked, true);
						} catch (e) {
						}
						//tree.selectNode(nodes[i], false);
					}
					//selectCheckNode();
				},
			};
			function refreshTree() {
				$.getJSON(
						"${ctx}/sys/office/treeData?type=3&&extId=&isAll=&module=&t="
								+ new Date().getTime(), function(data) {
							tree = $.fn.zTree.init($("#ztree"), setting, data);
							// 默认展开一级节点
							var nodes = tree.getNodesByParam("level", 0);
							for (var i = 0; i < nodes.length; i++) {
								tree.expandNode(nodes[i], true, false, false);
							}
							//异步加载子节点（加载用户）
							var nodesOne = tree.getNodesByParam("isParent",
									true);
							for (var j = 0; j < nodesOne.length; j++) {
								tree.reAsyncChildNodes(nodesOne[j], "!refresh",
										true);
							}
						});
			}
			refreshTree();
		})
	</script>
</body>
</html>