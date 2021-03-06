<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>网上论坛帖子管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            var dom = $(".content");
            for (var i = 1; i <= dom.length; i++) {
                dom.eq(i).html(dom.eq(i).text());
            }
        });

        function page(n, s) {
            $("#pageNo").val(n);
            $("#pageSize").val(s);
            $("#searchForm").submit();
            return false;
        }
    </script>
    <style>
        #contentTable > tbody > tr > td > span {
            width: 281px;
            white-space: nowrap;
            overflow: hidden;
            text-overflow: ellipsis;
            display: block;
        }


    </style>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/cms/cmsBbsArticle/">网上论坛帖子列表</a></li>
    <%--<shiro:hasPermission name="cms:cmsBbsArticle:edit"><li><a href="${ctx}/cms/cmsBbsArticle/form">网上论坛帖子添加</a></li></shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="cmsBbsArticle" action="${ctx}/cms/cmsBbsArticle/" method="post"
           class="breadcrumb form-search">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <ul class="ul-form">
        <li><label>标题：</label>
            <form:input path="title" htmlEscape="false" maxlength="255" class="input-medium"/>
        </li>
        <li class="btns"><input id="btnSubmit" class="btn btn-primary" type="submit" value="查询"/></li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead>
    <tr>
        <th >标题</th>
        <th >内容</th>
        <th>状 态</th>
        <th>审核日期</th>
        <shiro:hasPermission name="cms:cmsBbsArticle:edit">
            <th>操作</th>
        </shiro:hasPermission>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${page.list}" var="cmsBbsArticle">
        <tr>
            <td><span>
					<a href="${ctx}/cms/cmsBbsArticle/form?id=${cmsBbsArticle.id}">
                            ${cmsBbsArticle.title}
                    </a>
				</span>
            </td>
            <td class="tp">
					<span class="content">
						<c:out value="${cmsBbsArticle.contentText}" escapeXml="false"/>
					</span>

            </td>
            <td>
                    ${cmsBbsArticle.reviewFlag == 0 ? "未审核" : "已审核"}
            </td>
            <td>
                <fmt:formatDate value="${cmsBbsArticle.reviewDate}" pattern="yyyy-MM-dd HH:mm:ss"/>
            </td>
            <shiro:hasPermission name="cms:cmsBbsArticle:edit">
                <td>
                 <%--    <c:if test="${cmsBbsArticle.reviewFlag == 0}">
                        <a href="${ctx}/cms/cmsBbsArticle/save?id=${cmsBbsArticle.id}">审核</a>
                    </c:if>
                    <c:if test="${cmsBbsArticle.reviewFlag == 1}">
                        <a>已审核</a>
                    </c:if>
                     --%>
                    <c:choose>
			   			<c:when test="${cmsBbsArticle.reviewFlag eq '1'}">
			   				<i class="icon-legal" title="审核" style="margin-left: 5px;"></i>
			   			</c:when>
			   			<c:otherwise>
							<a class="btnList" onclick="HandleTips(this,'${cmsBbsArticle.id}')" title="审核"><i class="icon-legal"></i></a>
			   			</c:otherwise>
		   		  	</c:choose>
                    <a class="btnList" href="${ctx}/cms/cmsBbsArticle/delete?id=${cmsBbsArticle.id}" onclick="return confirmx('确认要删除该网上论坛帖子吗？', this.href)"><i class="icon-remove-sign"></i></a>
                </td>
            </shiro:hasPermission>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>