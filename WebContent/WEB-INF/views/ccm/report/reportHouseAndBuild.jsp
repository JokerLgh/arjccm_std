<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>建筑物、房屋数据分析</title>
	<meta name="decorator" content="default"/>
	<style>
	.common-pading{
	  width:100%;
	  height:260px;
	  padding:5px;
	}
	.echarts{
	  width:100%;
	  height:100%;
	}
	
	
	</style>
	<script type="text/javascript">
		$(document).ready(function() {
			
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
		<li class="active" style="width: 140px"><a class="nav-head" href="${ctx}/report/ccmReportOthers/houseAndBuild">数据统计</a></li>
		<li><a style="width: 140px;text-align:center" href="${ctx}/house/ccmHouseBuildmanage/">数据列表</a></li>
	</ul>
	<div class="context" content="${ctx}"></div>
	<sys:message content="${message}"/>
	    <br>
	    <div class="row-fluid">
		      <div class="span4" ><h4 style="margin-bottom: 20px">全部房屋状态类型统计</h4>
		      <div class="common-pading">
					<div id="ccmPopTenantHouseType" class="echarts" ></div>
				</div>
		      </div>
		      <div class="span8" ><h4 style="margin-bottom: 20px">全部房屋隐患类型统计</h4>
		         <div class="common-pading">
					<div id="ccmPopTenantHazard" class="echarts" ></div>
				 </div>
		      </div>
		    
	    </div>
	    <br>
	     <div class="row-fluid">
			<div class="span12" ><h4 style="margin-bottom: 20px">社区房屋报表统计</h4>
			<div style="height:571px;overflow: auto">
				<table id="contentTable" class="table table-striped table-bordered table-condensed">
					<thead>
						<tr>
							<th>社区</th>
							<th>建筑物数量（栋）</th>
							<th>建筑面积（平方米）</th>
							<th>房屋总数（套）</th>
							<th>房屋（自住）数（套）</th>
							<th>房屋（出租）数（套）</th>
							<th>房屋（空置）数（套）</th>
						</tr>
					</thead>
					<tbody id="tbody">
					</tbody>
				</table>
				</div>
			</div> 
	    </div>
<script src="${ctxStatic}/jquery/jquery-1.9.1.min.js"></script>
<script src="${ctxStatic}/jquery/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctxStatic}/common/index/Scripts/js/echarts.min.js"></script>
<script>
	var theme=$.cookie('theme');
	if(theme=='gradient'){
		var color = [ '#1F8BFA', '#E84442', '#FAB736', '#2CC189', '#F9A388', '#77E7F1', '#9E56E9', '#FF7453', '#16DDD3', '#FDB733'];
	}
	else{
		var color = [ '#4573a7', '#89a54e', '#71588f', '#4298af', '#db843d', '#93a9d0', '#d09392', '#b9ce96', '#a99bbc', '#92c3d4', '#ffdf5f','#aa4644'];
	}
	//基础参数
	var windowsHeight, _fontSize = 14,

	_fontSize1 = 26,
	breakData = 8;
	legendTop = '30%',
	radiusData = [90, 65],
	lengthECharts = 30;
	var context = $(".context").attr("content");
	var FontColor="#999",backgroundColor="#fff";
/* 	var theme=$.cookie('theme'); */
	if(theme=="black"){
		FontColor="#fff";
		backgroundColor="#0e2a4c";
	}
	$(function(){
	    windowsHeight= $(window).width();

	    if (windowsHeight >= 1600) {
	    	
	        _fontSize = 14;
	        legendTop = '15%';
	        radiusData = [90, 65];
	        lengthECharts = 20;
	        _fontSize1 = 26;
	        breakData = 8;
	        legendRight="8%"
	    }else {
	    	
	        _fontSize = 12;
	        legendTop = '15%';
	        radiusData = [60, 45];
	        lengthECharts = 5;
	        _fontSize1 = 12;
	        breakData = 6;
	        legendRight="5%"
	    }

	    
	    $.getJSON(context + "/report/ccmReportOthers/getHouseAndBuild", function(
				data) {
			// 接收参数
			//$.GetWorkSheets("ccmPopTenantHouseType", $.ToConvertA(JSON.parse(data[0])));
			//$.GetChangeSheets("ccmPopTenantHazard", JSON.parse(data[1]));
			$.GetListSheets("tbody", data);
		});
	  	//全部房屋类型统计
		$.getJSON(context + "/report/ccmPeopleAmount/getListTypeAll", function(
				data) {
			$.GetWorkSheets("ccmPopTenantHouseType", $.ToConvertA(data));
		});
	  	//房屋安全隐患统计
		$.getJSON(context + "/report/ccmReportOthers/getHouseHazard", function(
				data) {
			$.GetChangeSheets("ccmPopTenantHazard", data);
		});
		
		
	})	
	
	
	
	// 饼图pingJson况
	$.ToConvertA = function(object) {
        var ajaxData = new Array();
        for (var one in object) {
            ajaxData.push({
                "name": object[one]["type"],
                "value": Number(object[one]["value"])
            });
        }
        return ajaxData;
    } 
	// list
	$.GetListSheets = function(model, tbodyList) {
        // 拼接 table内容
        var tableContent = "";
        for (var i in tbodyList) {
            // 获取每一个值
            tableContent += "<tr>";
            	tableContent += "<td>"+tbodyList[i]["value1"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value2"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value3"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value4"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value5"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value6"]+"</td>";
            	tableContent += "<td>"+tbodyList[i]["value7"]+"</td>";
           tableContent += "</tr>";
        }
        // 添加内容 到页面
        $("#"+model).html(tableContent);
    } 
	
	//饼图统计情况
	$.GetWorkSheets = function(model,data) {
		
        var nameArr = [],
        DataArr = [];
        
        var option = {
        	 backgroundColor:backgroundColor,
            tooltip: {
                trigger: 'item',
                formatter: "{b} :<br/> {c}套 ({d}%)",
		        confine:true
            },
            legend: {
                
                type: 'scroll',
                orient: 'vertical',
                left:'70%',
                top:'middle',
                textStyle: {
                    color: FontColor,
                    fontSize: _fontSize,
                },
                data: data
            }, 
            series: [{
                name: '特殊人群类型',
                type: 'pie',
                radius : ['40%', '70%'],
                center: ['40%', '50%'],
                color: color,
                label: {
                    normal: {
						  formatter: "{b}：{c}",
                        show: true
                    }
                },
                labelLine: {
                    normal: {
                        show: true
                    }
                },
                data: data
            }]
        };
        var Barchart = echarts.init(document.getElementById(model));
        Barchart.setOption(option);

    }
	//柱状图统计情况
	$.GetChangeSheets =function(model,data){
		   var type=[];
		   var value=[];
		   for(var i=0;i<data.length;i++){ 
			   // if(data[i]['typeO']=='00'){
				   // type.push("无");
				   // value.push(data[i]['value']);
			   // }
			   if(data[i]['typeO']=='01'){
				   type.push("火灾隐患");
				   value.push(data[i]['value']);
			   }
			   if(data[i]['typeO']=='02'){
				   type.push("治安隐患");
				   value.push(data[i]['value']);
			   }
			   if(data[i]['typeO']=='03'){
				   type.push("矛盾纠纷隐患");
				   value.push(data[i]['value']);
			   }
			   if(data[i]['typeO']=='04'){
				   type.push("危化物品隐患");
				   value.push(data[i]['value']);
			   }
			   if(data[i]['typeO']=='05'){
				   type.push("疾病传染隐患");
				   value.push(data[i]['value']);
			   }
			   if(data[i]['typeO']=='99'){
				   type.push("其他");
				   value.push(data[i]['value']);
			   }
			}

		 var   option = {
				 backgroundColor:backgroundColor,
				   tooltip : {
	        	        trigger: 'item',
	        	        formatter: "{b} :<br/> {c}套",
				        confine:true
	        	    },
	        	     grid: {
		                    left: '3%',
		                    right: '3%',
		                    bottom: '3%',
		                    top: '5%',
		                    containLabel: true
		                },
		                
	   	    yAxis: {
	   	        type: 'value',
		   	     axisLabel : {
					 formatter: '{value}套',
				},
				axisLabel : {
					textStyle : {
						color : FontColor,
					}
				},
	   	        axisLine: {
	                    lineStyle: {
	                        color: '#808080'
	                    }
	                },
	                splitLine: {
	                    show: false
	                }
	   	    },
	   	    
	   	    xAxis: {
	   	        type: 'category',
	   	        data: type,
	   	 	axisLabel : {
				textStyle : {
					color : FontColor,
				}
			},
	   	        axisLine: {
	                    lineStyle: {
	                        color: '#808080'
	                    }
	                },
	                splitLine: {
	                    show: false
	                }
	   	    },
	   	    series: [{
	   	        data: value,
	   	        type: 'bar',
	   	        barWidth: '40%',
	   	        //配置样式
	   	        itemStyle: {   
	   	            //通常情况下：
	   	            normal:{  
	   	                color: function (params){
	   	                    var colorList = color;
	   	                    return colorList[params.dataIndex];
	   	                }
	   	            },
	   	            //鼠标悬停时：
	   	            emphasis: {
	   	                    shadowBlur: 10,
	   	                    shadowOffsetX: 0,
	   	                    shadowColor: 'rgba(0, 0, 0, 0.5)'
	   	            }
	   	        },
	   	    }]
		   }

		    var Barchart = echarts.init(document.getElementById(model));
	        Barchart.setOption(option);
	   }
	 
		
</script>
</body>
</html>