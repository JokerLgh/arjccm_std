<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>校园周边重点人员</title>
	<meta name="decorator" content="default"/>
	<link href="${ctxStatic}/form/css/form.css" rel="stylesheet" />
	<script type="text/javascript">
		$(document).ready(function() {
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
			$("td").css({"border":"1px dashed #CCCCCC"});
			var dis = $("#id").val();
			if(dis==null||dis==""){
				//$("#formdisplay").css("display","none");
				$("#formdisplay").hide();
			}else{
				//$("#formdisplay").css("display","block");
				$("#formdisplay").show();
			}
		});
		
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
		$(function(){
			$(".pimg").click(function(){
				var _this = $(this);//将当前的pimg元素作为_this传入函数
				imgShow("#outerdiv", "#innerdiv", "#bigimg", _this);
			});
		});
		function imgShow(outerdiv, innerdiv, bigimg, _this){
			var src = _this.attr("src");//获取当前点击的pimg元素中的src属性
			$(bigimg).attr("src", src);//设置#bigimg元素的src属性
			/*获取当前点击图片的真实大小，并显示弹出层及大图*/
			$("<img/>").attr("src", src).load(function(){
				var windowW = $(window).width();//获取当前窗口宽度
				var windowH = $(window).height();//获取当前窗口高度
				var realWidth = this.width;//获取图片真实宽度
				var realHeight = this.height;//获取图片真实高度
				var imgWidth, imgHeight;
				var scale = 0.8;//缩放尺寸，当图片真实宽度和高度大于窗口宽度和高度时进行缩放
				if(realHeight>windowH*scale) {//判断图片高度
					imgHeight = windowH*scale;//如大于窗口高度，图片高度进行缩放
					imgWidth = imgHeight/realHeight*realWidth;//等比例缩放宽度
					if(imgWidth>windowW*scale) {//如宽度扔大于窗口宽度
						imgWidth = windowW*scale;//再对宽度进行缩放
					}
				} else if(realWidth>windowW*scale) {//如图片高度合适，判断图片宽度
					imgWidth = windowW*scale;//如大于窗口宽度，图片宽度进行缩放
					imgHeight = imgWidth/realWidth*realHeight;//等比例缩放高度
				} else {//如果图片真实高度和宽度都符合要求，高宽不变
					imgWidth = realWidth;
					imgHeight = realHeight;
				}
				$(bigimg).css("width",imgWidth);//以最终的宽度对图片缩放
				var w = (windowW-imgWidth)/2;//计算图片与窗口左边距
				var h = (windowH-imgHeight)/2;//计算图片与窗口上边距
				$(innerdiv).css({"top":h, "left":w});//设置#innerdiv的top和left属性
				$(outerdiv).fadeIn("fast");//淡入显示#outerdiv及.pimg
			});
			$(outerdiv).click(function(){//再次点击淡出消失弹出层
				$(this).fadeOut("fast");
			});
		}
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="">校园周边重点人员列表</a></li>
	</ul> 
	
	<form:form id="formdisplay" modelAttribute="ccmHouseSchoolrim" action="" method="post" class="form-horizontal">
	<h4><c:if test="${not empty ccmHouseSchoolrim.id}">${ccmHouseSchoolrim.schoolName }</c:if><c:if test="${empty ccmHouseSchoolrim.id}">所有学校</c:if>基本信息：</h4>
		<form:hidden id="id" path="id"/>
		<sys:message content="${message}"/>		
		<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
			<tr>
				<td>
					<div>
						<label class="control-label">学校名称：</label>
						<div class="controls">
							<form:input path="schoolName" htmlEscape="false" maxlength="100" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">学校地址：</label>
						<div class="controls">
							<form:input path="schoolAdd" htmlEscape="false" maxlength="200" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">在校学生人数：</label>
						<div class="controls">
							<form:input path="schoolNum" htmlEscape="false" maxlength="6" class="input-xlarge  digits" type= "number" readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">所属社区：</label>
						<div class="controls">
							<form:input path="area.name" htmlEscape="false" maxlength="255" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">校长姓名：</label>
						<div class="controls">
							<form:input path="headName" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">校长联系方式：</label>
						<div class="controls">
							<form:input path="headTl" htmlEscape="false" maxlength="18" class="input-xlarge  digits" readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">分管安全保卫责任人姓名：</label>
						<div class="controls">
							<form:input path="secuBranName" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">分管安全保卫责任人联系方式：</label>
						<div class="controls">
							<form:input path="secuBranTl" htmlEscape="false" maxlength="50" class="input-xlarge  digits" readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">安全保卫责任人姓名：</label>
						<div class="controls">
							<form:input path="secuGuardName" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">安全保卫责任人联系方式：</label>
						<div class="controls">
							<form:input path="secuGuardTl" htmlEscape="false" maxlength="50" class="input-xlarge  digits"  readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
			<tr>
				<td>
					<div>
						<label class="control-label">治安责任人姓名：</label>
						<div class="controls">
							<form:input path="secuName" htmlEscape="false" maxlength="50" class="input-xlarge " readonly="true"/>
						</div>
					</div>
				</td>
				<td>
					<div>
						<label class="control-label">治安责任人联系方式：</label>
						<div class="controls">
							<form:input path="secuTl" htmlEscape="false" maxlength="50" class="input-xlarge  digits"  readonly="true"/>
						</div>
					</div>
				</td>
			</tr>
		</table>
	</form:form>
	<form:form id="searchForm" modelAttribute="ccmHouseSchoolrim" action="${ctx}/house/ccmHouseSchoolrim/popList?id=${ccmHouseSchoolrim.id}" method="post" class="breadcrumb form-search">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<h4><c:if test="${not empty ccmHouseSchoolrim.id}">${ccmHouseSchoolrim.schoolName }</c:if><c:if test="${empty ccmHouseSchoolrim.id}">所有校园</c:if>周边重点人员列表：</h4>
	</form:form>
	<sys:message content="${message}"/>
	<table border="1px" style="border-color: #CCCCCC; border: 1px solid #CCCCCC; padding: 10px; width: 100%;">
		<thead>
			<tr>
				<th>人员图片</th>
				<th>姓名</th>
				<th>现住地</th>
				<th>是否安置帮教</th>
				<th>是否社区矫正</th>
				<th>是否肇事肇祸等严重精神障碍患者</th>
				<th>是否吸毒</th>
				<th>是否艾滋病危险人员</th>
				<th>是否重点上访</th>
				<th>是否涉教</th>
				<shiro:hasPermission name="pop:ccmPeople:edit"><th>操作</th></shiro:hasPermission>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="ccmPeople">
			<tr>
				<td width="100px"><img src="${ccmPeople.images}" style="height:50px;" class="pimg"/></td>
				<td><a href="${ctx}/pop/ccmPeople/formPop?id=${ccmPeople.id}">${ccmPeople.name}</a></td>
				<td>${ccmPeople.residence}</td>
				<td>${fns:getDictLabel(ccmPeople.isRelease, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isRectification, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isPsychogeny, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isDrugs, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isAids, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isVisit, 'yes_no', '')}</td>
				<td>${fns:getDictLabel(ccmPeople.isHeresy, 'yes_no', '')}</td>
				<td><shiro:hasPermission name="pop:ccmPeople:edit">
				<a class="btnList" href="${ctx}/pop/ccmPeople/formPop?id=${ccmPeople.id}" title="查看"><i class="icon-file"></i></a>
				</shiro:hasPermission> <shiro:hasPermission name="log:ccmLogTail:edit">
					<a class="btnList" onclick="parent.LayerDialog('${ctx}/log/ccmLogTail/list?relevance_id=${ccmPeople.id}&relevance_table=ccm_house_schoolrim', '记录信息', '800px', '660px')" 
								  title="记录信息"><i class="icon-print" style="color: cornflowerblue;"></i></a>
					<a class="btnList" onclick="top.LayerDialog1('','${ctx}/log/ccmLogTail/formPro?relevance_id=${ccmPeople.id}&relevance_table=ccm_house_schoolrim', '添加记录', '800px', '660px')" title="添加记录"><i class="icon-plus"></i></a>
				</shiro:hasPermission></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
		<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:2;width:100%;height:100%;display:none;">
			<div id="innerdiv" style="position:absolute;">
				<img id="bigimg" style="border:5px solid #fff;" src="" />
			</div>
		</div>
	<div class="pagination">${page}</div>
</body>
</html>