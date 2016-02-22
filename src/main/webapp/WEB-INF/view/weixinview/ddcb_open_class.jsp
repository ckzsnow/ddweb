<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
List<CourseModel> list = courseDao.getAllOpenCourse(1,8);
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
	</head>
	<body>
		<header class="mui-bar mui-bar-nav" style="background-color: #66d6a6;">
			<h1 class="mui-title" style="color:white;">点豆大讲堂</h1>
			<a id="searchButton" style="color:white;font-size: 25px;font-weight:600;" class="mui-icon mui-icon-search mui-pull-right"></a>
		</header>
		<div id="pullrefresh" class="mui-content mui-scroll-wrapper" style="margin-top:5px;">
			<div class="mui-scroll">
				<div id="slider" class="mui-slider" style="width:100%;max-height: 150px;">
					<div class="mui-slider-group mui-slider-loop">
						<!-- 额外增加的一个节点(循环轮播：第一个节点是最后一张轮播) -->
						<div class="mui-slider-item mui-slider-item-duplicate">
							<a href="#">
								<img src="/files/bannerimgs/banner4.jpg">
							</a>
						</div>
						<!-- 第一张 -->
						<div class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner1.jpg">
							</a>
						</div>
						<!-- 第二张 -->
						<div class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner2.jpg">
							</a>
						</div>
						<!-- 第三张 -->
						<div class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner3.jpg">
							</a>
						</div>
						<!-- 第四张 -->
						<div class="mui-slider-item">
							<a href="#">
								<img src="/files/bannerimgs/banner4.jpg">
							</a>
						</div>
						<!-- 额外增加的一个节点(循环轮播：最后一个节点是第一张轮播) -->
						<div class="mui-slider-item mui-slider-item-duplicate">
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
				<%if(list == null || list.isEmpty()) {%>
				<div style="margin-top:50px;text-align:center;">暂时没有数据，请稍后重试！</div>
				<%} else {%>
				<div style="margin-top:10px;">
					<ul id="data_list" class="mui-table-view">
						<%for(CourseModel cm : list) { %>
						<li class="mui-table-view-cell mui-media" course_path='/playDDCBOpenClass?course_id=<%=cm.getId() %>'>
							<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=cm.getImage()%>">
							<div class="mui-media-body">
								<h4 style="font-size:15px;"><%=cm.getName() %></h4>
								<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=cm.getTeacher() %></h6>
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
	</body>
	<script type="text/javascript" src="/js/weixinjs/mui.min.js" ></script>
	<script src="/js/weixinjs/mui.picker.min.js"></script>
	<script src="/js/weixinjs/mui.poppicker.min.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script type="text/javascript" charset="utf-8">
			mui('.mui-scroll-wrapper').scroll();
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
			var page = 1;
			var count = 8;
			var ajaxData = {page:page, count:count};
			var ajaxURL = "/course/getAllOpenCourseByPage";
			function pullupRefresh() {
				ajaxData.page = ajaxData.page + 1;
				mui.ajax({
            		url: ajaxURL,
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
			var searchOne = "";
			var searchTwo = "";
			var searchThree = "";
			var competencyData = [{value:'',text:"技术"},{value:'',text:"产品"},{value:'',text:"运营"},{	value:'',text:"市场"},{value:'',text:"招聘"},{value:'',text:"管理"},{value:'',text:"投融资"},{	value:'',text:"战略"}];
			var courseTypeData = [{
				value:'',
				text:'全部领域',
				children:[{value:'',text:'全部行业',children:[{value:'',text:'全部职能'}]}]
			},{
				value:'',
				text:'互联网',
				children:[{value:'',text:'社交',children:competencyData},{value:'',text:'游戏',children:competencyData},{value:'',text:'电商',children:competencyData},{value:'',text:'教育',children:competencyData},{value:'',text:'金融',children:competencyData},{value:'',text:'医疗',children:competencyData},{value:'',text:'旅游',children:competencyData},{value:'',text:'餐饮',children:competencyData},{value:'',text:'交通',children:competencyData},{value:'',text:'智能硬件',children:competencyData},{value:'',text:'可穿戴',children:competencyData},{value:'',text:'招聘',children:competencyData},{value:'',text:'工具',children:competencyData},{value:'',text:'O2O',children:competencyData},{value:'',text:'汽车',children:competencyData},{value:'',text:'房地产',children:competencyData},{value:'',text:'企业服务',children:competencyData},{value:'',text:'IT服务',children:competencyData},{value:'',text:'大数据',children:competencyData},{value:'',text:'传媒',children:competencyData},{value:'',text:'娱乐',children:competencyData},{value:'',text:'安全',children:competencyData},{value:'',text:'能源',children:competencyData},{value:'',text:'其它',children:competencyData}]
			}];
			function searchCourse(coursePicker) {
				coursePicker.hide();
				document.getElementById("loadingToast").style.display = "";
				if(searchOne == "全部领域") {
					ajaxData = {page:1,count:8};
					ajaxURL = "/course/getAllOpenCourseByPage";
					mui('#pullrefresh').pullRefresh().refresh(true);
					mui.ajax({
	            		url: ajaxURL,
	            		type: "POST",
	            		data: {page:1,count:8},
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
				} else {
					ajaxData ={field:searchOne,industry:searchTwo,competency:searchThree,page:1,count:8};
					ajaxURL = "/course/getAllOpenCourseByCondition";
					mui.ajax({
	            		url: '/course/getAllOpenCourseByCondition',
	            		type: "POST",
	            		data: {field:searchOne,industry:searchTwo,competency:searchThree,page:1,count:8},
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
	    					} else {
	    						ajaxURL = "/course/getAllOpenCourseByPage";
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
			}
			(function($, doc) {
				$.ready(function() {
					var coursePicker = new $.PopPicker({
						layer: 3
					});
					coursePicker.setData(courseTypeData);
					var searchButton = doc.getElementById('searchButton');
					searchButton.addEventListener('tap', function(event) {
						coursePicker.show(function(items) {
							searchOne = (items[0] || {}).text;
							searchTwo = (items[1] || {}).text;
							searchThree = (items[2] || {}).text;
							searchCourse(coursePicker);
						});
					}, false);
				});
			})(mui, document);
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
		</script>
</html>
