<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.ICourseDetailDao"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.model.CourseDetailModel"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDetailDao courseDetailDao = (ICourseDetailDao)wac.getBean("courseDetailDao");
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
long id = Long.valueOf((String)request.getParameter("course_id"));
List<CourseDetailModel> list = courseDetailDao.getCourseDetailByCourseId(id);
CourseModel cm = courseDao.getCourseByCourseId(id);
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/playDDCBOpenClass?course_id=" + id);
%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title>点豆大讲堂</title>
		<link rel="stylesheet" href="/css/weixincss/bootstrap.min.css">
		<link rel="stylesheet" href="/css/weixincss/style.css">
		<link rel="stylesheet" href="/css/weixincss/newplay.css">
		<link rel="stylesheet" href="/css/weixincss/android.css">
		<link rel="stylesheet" href="/css/weixincss/mui.min.css">
	</head>

	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<header class="mui-bar mui-bar-nav" style="background-color: #66d6a6;">
			<button id="returnLastPage" type="button" class="mui-left mui-btn mui-btn-link mui-btn-nav mui-pull-left">
				<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
			</button>
			<h1 class="mui-title" style="color:white;">课程简介</h1>
		</header>
		<div class="content" style="margin-top:44px;">
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="summary">
					<div class="container">
						<div class="row csdetials">
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>导师简介</div>
							<div class="col-xs-12  mentername">
								<div class="row">
									<div class="col-xs-12">
										<div class="avatar">
											<img id="teacher_image" src="/files/imgs/<%=list.get(0).getTeacher_image() %>" />
										</div>
									</div>
									<div class="col-xs-12">
										<div class="teacher-name" id="teacher_name"><%=list.get(0).getTeacher_name() %></div>
										<div class="teacher-title" id="teacher_position"><%=list.get(0).getTeacher_position()%></div>
										<div class="infolabel">
											<div>Ta的经验</div>
										</div>
										<p id="teacher_info"><%=list.get(0).getTeacher_info() %></p>
									</div>
								</div>
							</div>
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>适合人群</div>
							<div class="col-xs-12  courseintro">
								<p id="crowd"><%=list.get(0).getCrowd() %></p>
							</div>
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>课程简介</div>
							<div class="col-xs-12  courseintro">
								<p id="details"><%=list.get(0).getDetails() %></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="/js/weixinjs/jquery.js"></script>
	<script src="/js/weixinjs/mui.min.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	document.getElementById("returnLastPage").addEventListener('tap',function(){
		window.history.back();
    });
	var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
	var lineLink = window.location.href;
	var descContent = "点豆大讲堂---为进取心而生，专注职场“传、帮、带”";
	var shareTitle = "点豆大讲堂";
	<%if(list != null && !list.isEmpty()) {%>
		imgUrl = "http://www.diandou.me/files/imgs/<%=cm.getImage()%>";
		descContent = "<%=cm.getTeacher()%>";
		shareTitle = "<%=cm.getName()%>";
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