<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
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
List<CourseModel> list = courseDao.getOpenCourseByCondition(1,8, "最新", "全部领域", "全部行业", "全部职能", "全部等级");
List<BannerModel> bannerList = bannerDao.getAllBanner();
String code = (String)session.getAttribute("url_code");
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/weixin/weixinLogin?view=ddcb_open_class&code="+code+"&state=123");
%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
		<title>点豆大讲堂</title>
		<link href="/css/weixincss/mui.min.css" rel="stylesheet" />
		<link href="/css/weixincss/mui.picker.min.css" rel="stylesheet" />
		<link href="/css/weixincss/mui.poppicker.css" rel="stylesheet" />
		<link href="/css/weixincss/loading.css" rel="stylesheet" />
		<style>
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active {
				color: #66d6a6 !important;
		    	border-bottom: 2px solid #66d6a6;
		    	background: 0 0;
			}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item {
				color: black !important;
			}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item a{
				color: black;
			}
			.mui-segmented-control.mui-segmented-control-inverted .mui-control-item.mui-active a{
				color: #66d6a6 !important;
			}
			body{
			    margin: 0;
			    padding: 0;
			    color: #333;
			    font-size:16px;
			    background: #ededed;
			    font-family: Microsoft YaHei;
			}
			
			/* screening */
			div.screening{
			    width: 100%;
			    overflow: hidden;
			    background: #fff;
			    position: fixed;
			    z-index: 4;
			}
			div.screening>ul{
			    margin: 0;
			    padding: 0;
			    list-style-type: none;
			    border-bottom: solid 1px #d3d3d3;
			    overflow: hidden;
			}
			div.screening>ul>li{
			    float: left;
			    width: 24%;
			    text-align: center;
			    line-height: 3rem;
			    border-left: solid 1px #d3d3d3;
			    background: url("../images/on_1.png") no-repeat 85% center;
			}
			/* grade */
			.Sort-eject{
			    position:fixed;
			    top: -65%;
			    width: 100%;
			    height: 63%;
			    z-index: 1;
			    -webkit-transition-duration: 0.4s;
			}
			
			.Sort-eject>ul{
			    margin: 0;
			    padding: 0;
			    overflow: auto;
			    height: 100%;
			    width: 100%;
			    -webkit-transition-duration: 0.4s;
			}
			.Sort-eject>ul>li{
				height: 3rem;
			    line-height: 3rem;
			    font-size: 16px;
			    padding-left: 1rem;
			    border-bottom: solid 1px #eee;
			}
			
			.grade-w-roll{
			top: 3rem;
			}
			.grade-w-roll::after{
			    position: fixed;
			    content: "";
			    width: 100%;
			    height: 100%;
			    display: block;
			    background:rgba(0,0,0,0.2);
			    top: 0;
			}
			/*Sort-eject*/
			.Sort-Sort{
			    background: #fff;
			    position: absolute;
			    z-index: 3;
			    left: 0;
			    list-style-type: none;
			}
			.Sort-Sort>li{
			    border-bottom: solid 1px #eee;
			    padding: 0;
			}
			.Sort-height{
			    height: 63%;
			}
			
			/* demo-content */
			.demo-content{padding-top: 3rem;}
		</style>
	</head>
	<body>
		<header class="mui-bar mui-bar-nav" style="background-color: #66d6a6;">
			<h1 class="mui-title" style="color:white;">点豆公开课</h1>
			<a id="searchButton" href="#searchInput" style="color:white;font-size: 25px;font-weight:600;" class="mui-icon mui-icon-search mui-pull-right"></a>
		</header>
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top:5px;">
			<div class="mui-scroll">
				<div id="slider" class="mui-slider" style="width:100%;max-height: 150px;">
					<div class="mui-slider-group mui-slider-loop">
						<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(3).getCourse_id() %>" class="mui-slider-item mui-slider-item-duplicate">
							<a href="#">
								<img src="/files/bannerimgs/banner4.jpg">
							</a>
						</div>
						<!-- 第一张 -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(0).getCourse_id() %>" class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner1.jpg">
							</a>
						</div>
						<!-- 第二张 -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(1).getCourse_id() %>" class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner2.jpg">
							</a>
						</div>
						<!-- 第三张 -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(2).getCourse_id() %>" class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner3.jpg">
							</a>
						</div>
						<!-- 第四张 -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(3).getCourse_id() %>" class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner4.jpg">
							</a>
						</div>
						<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
						<div course_path="/playDDCBOpenClass?course_id=<%=bannerList.get(0).getCourse_id() %>" class="mui-slider-item mui-slider-item-duplicate">
							<a href="#">
								<img src="/files/bannerimgs/banner1.jpg">
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
				
				<div class="mui-card" style="margin:10px 0px;border:none;border-radius:0px;">
					<div class="mui-scroll-wrapper mui-slider-indicator mui-segmented-control mui-segmented-control-inverted" style="background-color: white;height:40px;margin-top:5px;margin-left: 5px;">
						<div class="mui-scroll" style="background-color: white;height:40px;">
							<div class="mui-control-item mui-active" style="padding:8px 0px;">
								<a href="#latestOrHotest"><div id="latestOrHotestTips" style="width:80px;max-width:80px;" class='mui-ellipsis'>最新</div></a>
							</div>
							<div class="mui-control-item" style="padding:8px 0px;">
								<a href="#selectField"><div id="selectFieldTips" style="width:80px;max-width:80px;" class='mui-ellipsis'>全部领域</div></a>
							</div>
							<div class="mui-control-item" style="padding:8px 0px;">
								<a href="#selectIndustry"><div id="selectIndustryTips" style="width:80px;max-width:80px;" class='mui-ellipsis'>全部行业</div></a>
							</div>
							<div class="mui-control-item" style="padding:8px 0px;">
								<a href="#selectCompetency"><div id="selectCompetencyTips" style="width:80px;max-width:80px;" class='mui-ellipsis'>全部职能</div></a>
							</div>
							<div class="mui-control-item" style="padding:8px 0px;margin-right:10px;">
								<a href="#selectGrade"><div id="selectGradeTips" style="width:80px;max-width:80px;" class='mui-ellipsis'>全部等级</div></a>
							</div>
						</div>
					</div>
				</div>
				
				<div class="screening">
				<ul>
				<li class="meishi">美食</li>
				<li class="Regional">附近</li>
				<li class="Sort">排序</li>
				<li class="Brand">筛选</li>
				</ul>
				</div>
				<div class="Sort-eject Sort-height">
				<ul class="Sort-Sort" id="Sort-Sort">
				<li onclick="Sorts(this)">智能排序</li>
				<li onclick="Sorts(this)">离我最近</li>
				<li onclick="Sorts(this)">好评优先</li>
				<li onclick="Sorts(this)">人气优先</li>
				<li onclick="Sorts(this)">价格最低</li>
				<li onclick="Sorts(this)">价格最高</li>
				</ul>
				</div>
				
				<%if(list == null || list.isEmpty()) {%>
				<div style="margin-top:50px;text-align:center;">暂时没有数据，请稍后重试！</div>
				<%} else {%>
				<div style="margin-top:10px;">
					<ul id="data_list" class="mui-table-view">
						<%for(CourseModel cm : list) { %>
						<li class="mui-table-view-cell mui-media" course_path='/playDDCBOpenClass?course_id=<%=cm.getId() %>'>
							<img class="mui-media-object mui-pull-left" style="height:70px;width:100px;max-width:100px;" src="/files/imgs/<%=cm.getImage()%>">
							<div class="mui-media-body">
								<h4 style="font-size:15px;"><%=cm.getName() %></h4>
								<h6 style="margin-top:5px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=cm.getTeacher() %></h6>
								<p style="margin-top:5px;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-compose"></span><%=cm.getCourse_length()%>分钟&nbsp;&nbsp;<%=cm.getPeople_count() %>人学习</p>
							</div>
						</li>
						<%} %>
					</ul>
				</div>
				<%} %>
			</div>
		</div>
		<div id="loadingToast" class="weui_loading_toast" style="display:none;">
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
	            <p style="color:white;" class="weui_toast_content">数据加载中</p>
	        </div>
        </div>
        <div id="searchInput" class="mui-popover mui-popover-action mui-popover-bottom">
			<div class="mui-poppicker-header">
				<button onclick="searchCancel()" class="mui-btn mui-poppicker-btn-cancel">取消</button>
				<button onclick="searchContent()" class="mui-btn mui-btn-blue mui-poppicker-btn-ok">确定</button>
				<div class="mui-poppicker-clear"></div>
			</div>
			<div class="mui-poppicker-body" style="height:100px;background-color:white;">
				<div class="mui-input-row mui-search" style="margin:30px 10px;">
					<input id="search_key" type="search" class="mui-input-clear" style="background-color:white;" placeholder="点击输入关键词">
				</div>
			</div>
		</div>
		<div id="latestOrHotest" class="mui-popover" style="height:300px;z-index:9999;">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">最新</li>
						<li class="mui-table-view-cell">最热</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="selectField" class="mui-popover" style="height:300px;z-index:9999;">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">全部领域</li>
						<li class="mui-table-view-cell">互联网</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="selectIndustry" class="mui-popover" style="height:300px;z-index:9999;">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">全部行业</li>
						<li class="mui-table-view-cell">社交</li>
						<li class="mui-table-view-cell">游戏</li>
						<li class="mui-table-view-cell">电商</li>
						<li class="mui-table-view-cell">教育</li>
						<li class="mui-table-view-cell">金融</li>
						<li class="mui-table-view-cell">医疗</li>
						<li class="mui-table-view-cell">旅游</li>
						<li class="mui-table-view-cell">餐饮</li>
						<li class="mui-table-view-cell">交通</li>
						<li class="mui-table-view-cell">智能硬件</li>
						<li class="mui-table-view-cell">可穿戴</li>
						<li class="mui-table-view-cell">招聘</li>
						<li class="mui-table-view-cell">工具</li>
						<li class="mui-table-view-cell">O2O</li>
						<li class="mui-table-view-cell">汽车</li>
						<li class="mui-table-view-cell">房地产</li>
						<li class="mui-table-view-cell">企业服务</li>
						<li class="mui-table-view-cell">IT服务</li>
						<li class="mui-table-view-cell">大数据</li>
						<li class="mui-table-view-cell">传媒</li>
						<li class="mui-table-view-cell">娱乐</li>
						<li class="mui-table-view-cell">安全</li>
						<li class="mui-table-view-cell">能源</li>
						<li class="mui-table-view-cell">其它</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="selectCompetency" class="mui-popover" style="height:300px;z-index:9999;">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">全部职能</li>
						<li class="mui-table-view-cell">技术</li>
						<li class="mui-table-view-cell">产品</li>
						<li class="mui-table-view-cell">运营</li>
						<li class="mui-table-view-cell">市场</li>
						<li class="mui-table-view-cell">招聘</li>
						<li class="mui-table-view-cell">管理</li>
						<li class="mui-table-view-cell">投融资</li>
						<li class="mui-table-view-cell">战略</li>
					</ul>
				</div>
			</div>
		</div>
		<div id="selectGrade" class="mui-popover" style="height:300px;z-index:9999;">
			<div class="mui-scroll-wrapper">
				<div class="mui-scroll">
					<ul class="mui-table-view">
						<li class="mui-table-view-cell">全部等级</li>
						<li class="mui-table-view-cell">初级</li>
						<li class="mui-table-view-cell">中级</li>
						<li class="mui-table-view-cell">高级</li>
					</ul>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript" src="/js/weixinjs/mui.min.js" ></script>
	<script src="/js/weixinjs/mui.picker.min.js"></script>
	<script src="/js/weixinjs/mui.poppicker.min.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" charset="utf-8">
			function searchKeyCancel() {
				mui('#searchInput').popover('toggle');
			}
			function searchKeyContent() {
				mui('#searchInput').popover('toggle');
				alert(document.getElementById("search_key").value);
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
			var slider = mui("#slider");
			slider.slider({
				interval: 2000
			});
			mui('#data_list li').each(function(){
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
			var latestOrHotest = "最新";
			var selectField = "全部领域";
			var selectIndustry = "全部行业";
			var selectCompetency = "全部职能";
			var selectGrade = "全部等级";
			var page = 1;
			var count = 8;
			var ajaxData = {page:1, countPerPage:8, latestOrHotest:latestOrHotest, selectField:selectField, selectIndustry:selectIndustry, selectCompetency:selectCompetency, selectGrade:selectGrade};
			function pullupRefresh() {
				ajaxData.page = ajaxData.page + 1;
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
	    						liNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='height:50px;width:80px;max-width:100px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body'><h4 style='font-size:15px;'>"+data[i].name+"</h4><h6 style='margin-top:10px;color:#2ab888;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-contact'></span>"+data[i].teacher+"</h6></div>";
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
				mui.ajax({
            		url: '/course/getOpenCourseByCondition',
            		type: "POST",
            		data: {page:"1", countPerPage:"8", latestOrHotest:latestOrHotest, selectField:selectField, selectIndustry:selectIndustry, selectCompetency:selectCompetency, selectGrade:selectGrade},
            		success: function(data) {
            			if (!checkJsonIsEmpty(data)) {
            				var i = 0;
            				var rootNode = document.getElementById("data_list");
    						rootNode.innerHTML = "";
            				for (i in data) {
	    						var liNode = document.createElement('li');
	    						liNode.setAttribute('class', 'mui-table-view-cell mui-media');
	    						liNode.setAttribute('course_path', '/playDDCBOpenClass?course_id='+data[i].id);
	    						liNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='height:50px;width:80px;max-width:100px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body'><h4 style='font-size:15px;'>"+data[i].name+"</h4><h6 style='margin-top:10px;color:#2ab888;' class='mui-ellipsis'><span style='font-size:16px;' class='mui-icon mui-icon-contact'></span>"+data[i].teacher+"</h6></div>";
	    						rootNode.appendChild(liNode);
	    						liNode.addEventListener('tap',function(){
	    					        window.location.href=this.getAttribute('course_path'); 
	    					    });
            				}
            				ajaxData.page = 1;
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
			
			mui('#latestOrHotest li').each(function(){
				this.addEventListener('tap',function(){
					document.getElementById("latestOrHotestTips").innerHTML = this.innerHTML;
					mui('#latestOrHotest').popover('toggle');
					if(latestOrHotest != this.innerHTML) {
						latestOrHotest = this.innerHTML;
						searchCourseByCondition();
					}
			    });
			});
			mui('#selectField li').each(function(){
				this.addEventListener('tap',function(){
					document.getElementById("selectFieldTips").innerHTML = this.innerHTML;
					mui('#selectField').popover('toggle');
					if(selectField != this.innerHTML) {
						selectField = this.innerHTML;
						searchCourseByCondition();
					}
			    });
			});
			mui('#selectIndustry li').each(function(){
				this.addEventListener('tap',function(){
					document.getElementById("selectIndustryTips").innerHTML = this.innerHTML;
					mui('#selectIndustry').popover('toggle');
					if(selectIndustry != this.innerHTML) {
						selectIndustry = this.innerHTML;
						searchCourseByCondition();
					}
			    });
			});
			mui('#selectCompetency li').each(function(){
				this.addEventListener('tap',function(){
					document.getElementById("selectCompetencyTips").innerHTML = this.innerHTML;
					mui('#selectCompetency').popover('toggle');
					if(selectCompetency != this.innerHTML) {
						selectCompetency = this.innerHTML;
						searchCourseByCondition();
					}
			    });
			});
			mui('#selectGrade li').each(function(){
				this.addEventListener('tap',function(){
					document.getElementById("selectGradeTips").innerHTML = this.innerHTML; 
					mui('#selectGrade').popover('toggle');
					if(selectGrade != this.innerHTML) {
						selectGrade = this.innerHTML;
						searchCourseByCondition();
					}
			    });
			});
			
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
			mui('.Sort')[0].addEventListener('tap',function(){
				var currentClass = mui('.Sort-eject')[0].getAttribute("class");
				if(currentClass.indexOf("grade-w-roll") != -1) {
					mui('.Sort-eject')[0].setAttribute("class", currentClass.replace("grade-w-roll", ""));
				} else {
					mui('.Sort-eject')[0].setAttribute("class", currentClass + " grade-w-roll");
				}
			});
		</script>
</html>
