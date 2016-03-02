<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.IBannerDao"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.model.BannerModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
IBannerDao bannerDao = (IBannerDao)wac.getBean("bannerDao");
List<CourseModel> list = courseDao.getOpenCourseByCondition(1,8, "最新", "全部", "全部", "全部", "全部", "");
List<BannerModel> bannerList = bannerDao.getAllBanner();
String code = (String)session.getAttribute("url_code");
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/weixin/weixinLogin?view=ddcb_open_class&code="+code+"&state=123");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
<title>点豆大讲堂</title>
<link href="/css/weixincss/mui.min.css" rel="stylesheet" />
<link href="/css/weixincss/mui.picker.min.css" rel="stylesheet" />
<link href="/css/weixincss/mui.poppicker.css" rel="stylesheet" />
<link href="/css/weixincss/loading.css" rel="stylesheet" />
<style>
.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active
	{
	color: #66d6a6 !important;
	border-bottom: 2px solid #66d6a6;
	background: 0 0;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item
	{
	color: black !important;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item a
	{
	color: black;
}

.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active a
	{
	color: #66d6a6 !important;
}

/* screening */
div.screening {
	width: 100%;
	overflow: hidden;
	background: #fff;
	position: fixed;
	z-index: 4;
}

div.screening>ul {
	margin: 0;
	padding: 0;
	list-style-type: none;
	border-bottom: solid 1px #d3d3d3;
	overflow: hidden;
}

div.screening>ul>li {
	float: left;
	width: 24%;
	text-align: center;
	line-height: 44px;
	border-left: solid 1px #d3d3d3;
	background: url("/img/weixinimg/on_1.png") no-repeat 85% center;
}
/* grade */
.search-eject {
	position: fixed;
	top: 49px;
	width: 100%;
	height: 170px;
	z-index: 11;
	-webkit-transition-duration: 0.4s;
}

.search-eject>ul {
	margin: 0;
	padding: 0;
	overflow: auto;
	height: 170px;
	width: 100%;
	-webkit-transition-duration: 0.4s;
}

.search-eject>ul>li {
	height: 44px;
	line-height: 44px;
	font-size: 16px;
	padding-left: 1rem;
	border-bottom: solid 1px #eee;
}

.grade-w-roll {
	top: 220px;
}

.grade-w-roll::after {
	position: fixed;
	content: "";
	width: 100%;
	height: 100%;
	display: block;
	background: rgba(0, 0, 0, 0.2);
	top: 49px;
}
/*Sort-eject*/
.search-search {
	background: #fff;
	position: absolute;
	z-index: 39;
	left: 0;
	list-style-type: none;
	height: 170px;
	width: 100%;
}

.search-search>li {
	border-bottom: solid 1px #eee;
	padding: 0;
}

.search-height {
	height: 170px;
	width: 100%;
}

/* demo-content */
.demo-content {
	padding-top: 3rem;
}
</style>
</head>
<body>
	<header class="mui-bar mui-bar-nav" style="background-color: #66d6a6;z-index:999999999;">
		<h1 class="mui-title" style="color: white;">点豆公开课</h1>
		<a id="searchButton" href="#searchInput"
			style="color: white; font-size: 25px; font-weight: 600;"
			class="mui-icon mui-icon-search mui-pull-right"></a>
	</header>
	<div class="mui-content">
		<div id="content_top" style="margin-top: 5px;">
			<div id="slider" class="mui-slider"
				style="width: 100%; max-height: 130px; height: 130px; z-index: 100;background-color:white;">
				<div class="mui-slider-group mui-slider-loop">
					<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(3).getCourse_id() %>"
						class="mui-slider-item mui-slider-item-duplicate">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner4.jpg">
						</a>
					</div>
					<!-- 第一张 -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(0).getCourse_id() %>"
						class="mui-slider-item">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner1.jpg">
						</a>
					</div>
					<!-- 第二张 -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(1).getCourse_id() %>"
						class="mui-slider-item">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner2.jpg">
						</a>
					</div>
					<!-- 第三张 -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(2).getCourse_id() %>"
						class="mui-slider-item">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner3.jpg">
						</a>
					</div>
					<!-- 第四张 -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(3).getCourse_id() %>"
						class="mui-slider-item">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner4.jpg">
						</a>
					</div>
					<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
					<div
						course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(0).getCourse_id() %>"
						class="mui-slider-item mui-slider-item-duplicate">
						<a href="#"> <img
							src="http://www.diandou.me/files/bannerimgs/banner1.jpg">
						</a>
					</div>
				</div>
				<div class="mui-slider-indicator">
					<div class="mui-indicator mui-active"></div>
					<div class="mui-indicator"></div>
					<div class="mui-indicator"></div>
					<div class="mui-indicator"></div>
				</div>
			</div>
			<div class="screening" style="z-index:199;">
				<ul>
					<li class="industry" style="font-size:10px;">行业</li>
					<li class="competency" style="font-size:10px;">职能</li>
					<li class="latestOrHotest" style="font-size:10px;">最新</li>
					<li class="courseGrade" style="font-size:10px;">等级</li>
				</ul>
			</div>
			<div id="industry" class="industry search-eject search-height">
				<div class="mui-scroll-wrapper" style="height: 170px;">
					<div class="mui-scroll">
						<ul class="mui-table-view">
							<li style="background:#eee;" class="mui-table-view-cell"  onclick="clickIndustry(this)">全部</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">社交</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">游戏</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">电商</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">教育</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">金融</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">医疗</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">旅游</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">餐饮</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">交通</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">智能硬件</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">可穿戴</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">招聘</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">工具</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">O2O</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">汽车</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">房地产</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">企业服务</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">IT服务</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">大数据</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">传媒</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">娱乐</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">安全</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">能源</li>
							<li class="mui-table-view-cell" onclick="clickIndustry(this)">其它</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="competency" class="competency search-eject search-height">
				<div class="mui-scroll-wrapper" style="height: 170px;">
					<div class="mui-scroll">
						<ul class="mui-table-view">
							<li style="background:#eee;" class="mui-table-view-cell" onclick="clickCompetency(this)">全部</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">技术</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">产品</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">运营</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">市场</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">招聘</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">管理</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">投融资</li>
							<li class="mui-table-view-cell" onclick="clickCompetency(this)">战略</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="latestOrHotest" class="latestOrHotest search-eject search-height">
				<div class="mui-scroll-wrapper" style="height: 170px;">
					<div class="mui-scroll">
						<ul class="mui-table-view">
							<li class="mui-table-view-cell" style="background:#eee;" onclick="clickLatestOrHotest(this)">最新</li>
							<li class="mui-table-view-cell" onclick="clickLatestOrHotest(this)">最热</li>
						</ul>
					</div>
				</div>
			</div>
			<div id="courseGrade" class="courseGrade search-eject search-height">
				<div class="mui-scroll-wrapper" style="height: 170px;">
					<div class="mui-scroll">
						<ul class="mui-table-view">
							<li class="mui-table-view-cell" style="background:#eee;" onclick="clickCourseGrade(this)">全部</li>
							<li class="mui-table-view-cell" onclick="clickCourseGrade(this)">初级</li>
							<li class="mui-table-view-cell" onclick="clickCourseGrade(this)">中级</li>
							<li class="mui-table-view-cell" onclick="clickCourseGrade(this)">高级</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</div>

				<%if(list == null || list.isEmpty()) {%>
				<div style="margin-top:50%;text-align:center;">暂时没有数据，请稍后重试！</div>
				<%} else {%>
				<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top:180px;">
					<div class="mui-scroll">
					<ul id="data_list" class="mui-table-view">
						<%for(CourseModel cm : list) { %>
						<li class="mui-table-view-cell mui-media" course_path='/playDDCBOpenClass?course_id=<%=cm.getId() %>'>
							<img class="mui-media-object mui-pull-left" style="height:70px;width:100px;max-width:100px;" src="/files/imgs/<%=cm.getImage()%>">
							<div class="mui-media-body">
								<h4 style="font-size:15px;"><%=cm.getName() %></h4>
								<h6 style="margin-top:5px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=cm.getTeacher() %></h6>
								<p style="margin-top:5px;font-size:12px;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-compose"></span><%=cm.getCourse_length()%>分钟&nbsp;&nbsp;<%=cm.getStudy_people_count() %>人学习</p>
							</div>
						</li>
						<%} %>
					</ul>
					</div>
				</div>
				<%} %>

	<div id="loadingToast" class="weui_loading_toast"
		style="display: none;">
		<div class="weui_mask_transparent"></div>
		<div class="weui_toast">
			<div class="weui_loading">
				<div class="weui_loading_leaf weui_loading_leaf_0"></div>
				<div class="weui_loading_leaf weui_loading_leaf_1"></div>
				<div class="weui_loading_leaf weui_loading_leaf_2"></div>
				<div class="weui_loading_leaf weui_loading_leaf_3"></div>
				<div class="weui_loading_leaf weui_loading_leaf_4"></div>
				<div class="weui_loading_leaf weui_loading_leaf_5"></div>
				<div class="weui_loading_leaf weui_loading_leaf_6"></div>
				<div class="weui_loading_leaf weui_loading_leaf_7"></div>
				<div class="weui_loading_leaf weui_loading_leaf_8"></div>
				<div class="weui_loading_leaf weui_loading_leaf_9"></div>
				<div class="weui_loading_leaf weui_loading_leaf_10"></div>
				<div class="weui_loading_leaf weui_loading_leaf_11"></div>
			</div>
			<p style="color: white;" class="weui_toast_content">数据加载中</p>
		</div>
	</div>
	<div id="searchInput"
		class="mui-popover mui-popover-action mui-popover-bottom">
		<div class="mui-poppicker-header">
			<button onclick="searchKeyCancel()"
				class="mui-btn mui-poppicker-btn-cancel">取消</button>
			<button onclick="searchKeyContent()"
				class="mui-btn mui-poppicker-btn-ok">确定</button>
			<div class="mui-poppicker-clear"></div>
		</div>
		<div class="mui-poppicker-body"
			style="height: 100px; background-color: white;">
			<div class="mui-input-row mui-search" style="margin: 30px 10px;">
				<input id="search_key" type="search" class="mui-input-clear"
					style="background-color: white;" placeholder="点击输入关键词">
			</div>
		</div>
	</div>
</body>
<script type="text/javascript" src="/js/weixinjs/mui.min.js"></script>
<script src="/js/weixinjs/mui.picker.min.js"></script>
<script src="/js/weixinjs/mui.poppicker.min.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script type="text/javascript" charset="utf-8">
			var latestOrHotest = "最新";
			var selectField = "全部";
			var selectIndustry = "全部";
			var selectCompetency = "全部";
			var selectGrade = "全部";
			var selectKey = "";
			function searchKeyCancel() {
				mui('#searchInput').popover('toggle');
			}
			function searchKeyContent() {
				mui('#searchInput').popover('toggle');
				var key = document.getElementById("search_key").value;
				if(key != null || key != "") {
					selectKey = key;
				}
				searchCourseByCondition();
			}
			mui.init({
				swipeBack:true,
				pullRefresh: {
					container: '#pullrefresh',
					up: {
						contentrefresh: '正在加载...',
						callback: pullupRefresh
					}
				}
			});
			mui('.mui-scroll-wrapper').scroll();
			mui('.mui-slider .mui-slider-group .mui-slider-item img').each(function(){
				var width = document.body.clientWidth + "px;";
				this.setAttribute("style", "width:"+width+";height:130px;");
			});
			var slider = mui("#slider");
			slider.slider({
				interval: 2000
			});
			mui('#data_list li').each(function(){
				this.addEventListener('tap',function(){
			        window.location.href=this.getAttribute('course_path'); 
			    });  
			});
			mui('.mui-slider-item').each(function(){
				this.addEventListener('tap',function(){
			        window.location.href=this.getAttribute('course_path'); 
			    });  
			});
			function checkJsonIsEmpty(json) {
				var isEmpty = true;
				if (json == null) return true;
				for (var jsonKey in json) {
					isEmpty = false;
					break;
				}
				return isEmpty;
			}
			var page = 1;
			var count = 8;
			function pullupRefresh() {
				page++;
				var ajaxData = {page:page, countPerPage:8, latestOrHotest:latestOrHotest, selectField:selectField, selectIndustry:selectIndustry, selectCompetency:selectCompetency, selectGrade:selectGrade, selectKey:selectKey};
				mui.ajax({
            		url: "/course/getOpenCourseByCondition",
            		type: "POST",
            		data: ajaxData,
            		success: function(data) {
            			if (!checkJsonIsEmpty(data)) {
            				var i = 0;
            				for (i in data) {
	    						var rootNode = document.getElementById("data_list");
	    						var liNode = document.createElement('li');
	    						liNode.setAttribute('class', 'mui-table-view-cell mui-media');
	    						liNode.setAttribute('course_path', '/playDDCBOpenClass?course_id='+data[i].id);
	    						liNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='height:70px;width:100px;max-width:100px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body'><h4 style='font-size:15px;'>"+data[i].name+"</h4><h6 style='margin-top:5px;color:#2ab888;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-contact'></span>"+data[i].teacher+"</h6><p style='margin-top:5px;font-size:12px;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-compose'></span>"+data[i].course_length+"分钟&nbsp;&nbsp;"+data[i].study_people_count+"人学习</p></div>";
	    						rootNode.appendChild(liNode);
	    						liNode.addEventListener('tap',function(){
	    					        window.location.href=this.getAttribute('course_path'); 
	    					    });
            				}
            				if(i<7) {
            					mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
            				} else {
            					mui('#pullrefresh').pullRefresh().endPullupToRefresh(false);
            				}
    					} else {
    						mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
    					}
            		},
            		error: function(status, error) {
            			mui('#pullrefresh').pullRefresh().endPullupToRefresh(true);
            			alert("服务器暂时无法获取数据，请稍后重试！");
            		}
            	});
			}
			function searchCourseByCondition() {
				document.getElementById("loadingToast").style.display = "";
				mui('#pullrefresh').pullRefresh().refresh(true);
				mui.ajax({
            		url: '/course/getOpenCourseByCondition',
            		type: "POST",
            		data: {page:"1", countPerPage:"8", latestOrHotest:latestOrHotest, selectField:selectField, selectIndustry:selectIndustry, selectCompetency:selectCompetency, selectGrade:selectGrade, selectKey:selectKey},
            		success: function(data) {
            			if (!checkJsonIsEmpty(data)) {
            				var i = 0;
            				var rootNode = document.getElementById("data_list");
    						rootNode.innerHTML = "";
            				for (i in data) {
	    						var liNode = document.createElement('li');
	    						liNode.setAttribute('class', 'mui-table-view-cell mui-media');
	    						liNode.setAttribute('course_path', '/playDDCBOpenClass?course_id='+data[i].id);
	    						liNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='height:70px;width:100px;max-width:100px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body'><h4 style='font-size:15px;'>"+data[i].name+"</h4><h6 style='margin-top:5px;color:#2ab888;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-contact'></span>"+data[i].teacher+"</h6><p style='margin-top:5px;font-size:12px;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-compose'></span>"+data[i].course_length+"分钟&nbsp;&nbsp;"+data[i].study_people_count+"人学习</p></div>";
	    						rootNode.appendChild(liNode);
	    						liNode.addEventListener('tap',function(){
	    					        window.location.href=this.getAttribute('course_path'); 
	    					    });
            				}
            				page = 1;
            				mui('#pullrefresh').scroll().scrollTo(0,0,100);
    					} else {
    						alert("您搜索的数据为空，请稍后重试！");
    					}
            			document.getElementById("loadingToast").style.display = "none";
            		},
            		error: function(status, error) {
            			document.getElementById("loadingToast").style.display = "none";
            			alert("服务器暂时无法获取导数据，请稍后重试！");
            		}
            	});
			}
			mui('.industry')[0].addEventListener('tap',function(){
				mui('.grade-w-roll').each(function(){
					var currentClass = this.getAttribute("class");
					if(currentClass.indexOf("industry") == -1) {
						this.setAttribute("class", currentClass.replace("grade-w-roll", ""));
					}
				});
				var currentClass = mui('#industry')[0].getAttribute("class");
				if(currentClass.indexOf("grade-w-roll") != -1) {
					mui('#industry')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				} else {
					mui('#industry')[0].setAttribute("class", currentClass + " grade-w-roll");
				}
			});
			mui('.competency')[0].addEventListener('tap',function(){
				mui('.grade-w-roll').each(function(){
					var currentClass = this.getAttribute("class");
					if(currentClass.indexOf("competency") == -1) {
						this.setAttribute("class", currentClass.replace("grade-w-roll", ""));
					}
				});
				var currentClass = mui('#competency')[0].getAttribute("class");
				if(currentClass.indexOf("grade-w-roll") != -1) {
					mui('#competency')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				} else {
					mui('#competency')[0].setAttribute("class", currentClass + " grade-w-roll");
				}
			});
			mui('.latestOrHotest')[0].addEventListener('tap',function(){
				mui('.grade-w-roll').each(function(){
					var currentClass = this.getAttribute("class");
					if(currentClass.indexOf("latestOrHotest") == -1) {
						this.setAttribute("class", currentClass.replace("grade-w-roll", ""));
					}
				});
				var currentClass = mui('#latestOrHotest')[0].getAttribute("class");
				if(currentClass.indexOf("grade-w-roll") != -1) {
					mui('#latestOrHotest')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				} else {
					mui('#latestOrHotest')[0].setAttribute("class", currentClass + " grade-w-roll");
				}
			});
			mui('.courseGrade')[0].addEventListener('tap',function(){
				mui('.grade-w-roll').each(function(){
					var currentClass = this.getAttribute("class");
					if(currentClass.indexOf("courseGrade") == -1) {
						this.setAttribute("class", currentClass.replace("grade-w-roll", ""));
					}
				});
				var currentClass = mui('#courseGrade')[0].getAttribute("class");
				if(currentClass.indexOf("grade-w-roll") != -1) {
					mui('#courseGrade')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				} else {
					mui('#courseGrade')[0].setAttribute("class", currentClass + " grade-w-roll");
				}
			});
			function clickLatestOrHotest(ele) {
				mui('#latestOrHotest li').each(function(){
					if(this != ele && this.getAttribute("style") != null || this.getAttribute("style") != "") {
						this.setAttribute("style", "");
					}
				});
				ele.setAttribute("style", "background:#eee;");
				mui(".latestOrHotest")[0].innerHTML = ele.innerHTML;
				var currentClass = mui('#latestOrHotest')[0].getAttribute("class");
				mui('#latestOrHotest')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				latestOrHotest = ele.innerHTML;
				searchCourseByCondition();				
			}
			function clickCourseGrade(ele) {
				mui('#courseGrade li').each(function(){
					if(this != ele && this.getAttribute("style") != null || this.getAttribute("style") != "") {
						this.setAttribute("style", "");
					}
				});
				ele.setAttribute("style", "background:#eee;");
				mui(".courseGrade")[0].innerHTML = ele.innerHTML;
				var currentClass = mui('#courseGrade')[0].getAttribute("class");
				mui('#courseGrade')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				//selectGrade = ele.innerHTML;
				searchCourseByCondition();
			}
			function clickCompetency(ele) {
				mui('#competency li').each(function(){
					if(this != ele && this.getAttribute("style") != null || this.getAttribute("style") != "") {
						this.setAttribute("style", "");
					}
				});
				ele.setAttribute("style", "background:#eee;");
				mui(".competency")[0].innerHTML = ele.innerHTML;
				var currentClass = mui('#competency')[0].getAttribute("class");
				mui('#competency')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				selectCompetency = ele.innerHTML;
				searchCourseByCondition();
			}
			function clickIndustry(ele) {
				mui('#industry li').each(function(){
					if(this != ele && this.getAttribute("style") != null || this.getAttribute("style") != "") {
						this.setAttribute("style", "");
					}
				});
				ele.setAttribute("style", "background:#eee;");
				mui(".industry")[0].innerHTML = ele.innerHTML;
				var currentClass = mui('#industry')[0].getAttribute("class");
				mui('#industry')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				selectIndustry = ele.innerHTML;
				searchCourseByCondition();
			}
			var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
			var lineLink = window.location.href;
			var descContent = "点豆大讲堂---为进取心而生，专注职场“传、帮、带”";
			var shareTitle = "点豆大讲堂";
			<%if(list != null && !list.isEmpty()) {%>
				imgUrl = "http://www.diandou.me/files/imgs/<%=list.get(0).getImage()%>";
				descContent = "<%=list.get(0).getTeacher()%>";
				shareTitle = "<%=list.get(0).getName()%>";
			<%}%>
			wx.config({
				appId: 'wxbd6aef840715f99d',
				timestamp: <%=result.get("timestamp")%>,
				nonceStr: '<%=result.get("nonceStr")%>',
				signature: '<%=result.get("signature")%>',
				jsApiList: [
					'onMenuShareQQ',
					'onMenuShareTimeline',
					'onMenuShareAppMessage'
				]
			});
			wx.ready(function() {
				setTimeout(function() {
					wx.onMenuShareTimeline({
						title: shareTitle, // 分享标题
						link: lineLink, // 分享链接
						imgUrl: imgUrl, // 分享图标
						success: function() {
							// 用户确认分享后执行的回调函数
						},
						cancel: function() {
							// 用户取消分享后执行的回调函数
						}
					});
					wx.onMenuShareAppMessage({
						title: shareTitle, // 分享标题
						desc: descContent, // 分享描述
						link: lineLink, // 分享链接
						imgUrl: imgUrl, // 分享图标
						type: '', // 分享类型,music、video或link，不填默认为link
						dataUrl: '', // 如果type是music或video，则要提供数据链接，默认为空
						success: function() {
							// 用户确认分享后执行的回调函数
						},
						cancel: function() {
							// 用户取消分享后执行的回调函数
						}
					});
					wx.onMenuShareQQ({
						title: shareTitle, // 分享标题
						desc: descContent, // 分享描述
						link: lineLink, // 分享链接
						imgUrl: imgUrl, // 分享图标
						success: function() {
							// 用户确认分享后执行的回调函数
						},
						cancel: function() {
							// 用户取消分享后执行的回调函数
						}
					});
				}, 500);
			});
			/* setTimeout(function() {
				mui('#pullrefresh').scroll().y = -180;
				mui('#pullrefresh').scroll().lastY = -180;
				mui('#pullrefresh').scroll().scrollTo(0, -180, 1000);
			}, 1000); */
		</script>
</html>