<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.ICourseDetailDao"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.dao.IWeixinUserDao"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.model.CourseDetailModel"%>
<%@ page import="com.ddcb.model.WeixinUserModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDetailDao courseDetailDao = (ICourseDetailDao)wac.getBean("courseDetailDao");
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
List<CourseDetailModel> list = null;
long id = Long.valueOf((String)request.getParameter("course_id"));
list = courseDetailDao.getCourseDetailByCourseId(id);
CourseModel cm = courseDao.getCourseByCourseId(id);
/* Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/playDDCBOpenClass?course_id=" + id); */
String userId = (String)session.getAttribute("openid");
String courseDate = cm.getCourse_date().toString();
String courseDateReadable = cm.getCourse_date_readable();
String courseLength = cm.getCourse_length();
%>
<!DOCTYPE html>
<html lang="zh-CN">

	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1,user-scalable=no">
		<meta name="keywords" content="" />
		<meta name="description" content="" />
		<title></title>
		<link rel="stylesheet" href="/css/weixincss/bootstrap.min.css">
		<link rel="stylesheet" href="/css/weixincss/style.css">
		<link rel="stylesheet" href="/css/weixincss/newplay.css">
		<link rel="stylesheet" href="/css/weixincss/android.css">
		<link rel="stylesheet" href="/css/weixincss/mui.min.css">
	</head>

	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<div style="position: relative;">
			<div id="video_div" class="video" style="display:none;background:#1cbcd6;">
				<video id="video" controls preload="none" width="640" height="264" poster="/files/imgs/<%=list.get(0).getVideo_image() %>" data-setup="{}">
					<source id="video_src" src="<%=list.get(0).getVideosrc() %>" type='video/mp4'>
				</video>
			</div>
			<div id="playClassTimeTips" style="width:100%;height:150px;line-height:150px;text-align:center;background:#1cbcd6;">
				
			</div>
		</div>

		<div id="tabtip" class="container">
			<ul id="myTab" class="nav nav-tabs row mantoutab" style="padding-left:0px;padding-right:0px;">
				<li class="col-xs-6 text-center active"><a vinfo="summary" class="center-block" data-toggle="tab">课程简介</a></li>
			</ul>
		</div>

		<div class="content">
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
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	document.addEventListener("WeixinJSBridgeReady", function () {
		var courseDate = new Date("<%=courseDate%>").getTime() / 1000;
		var currentDate = new Date().getTime() / 1000;
		var courseLength = parseInt("<%=courseLength%>") * 60;
		if(courseDate>currentDate) {
			//document.getElementById("video").style.display = "none";
			//document.getElementById("playClassTimeTips").style.display = "";
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;'>课程直播时间：<%=courseDateReadable%></p>";
		} else {
			if(courseDate + courseLength < currentDate) {
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;'>课程直播已经结束，感谢您的关注！</p>";
			} else {
				alert(111);
				document.getElementById("playClassTimeTips").style.display = "none";
				document.getElementById("video_div").style.display = "";
				document.getElementById("video").play();
				var hasSetTime = false;
				document.getElementById("video").addEventListener("timeupdate", function(){
					if(!hasSetTime && document.getElementById("video").duration > 1) {
						alert(document.getElementById("video").duration);
						document.getElementById("video").pause();
						document.getElementById("video").currentTime = 30;
						document.getElementById("video").play();
						hasSetTime = true;
					}
				});
				/* var timer = setInterval(function(){
					alert(document.getElementById("video").readyState);
					if(document.getElementById("video").readyState>0) {
						clearInterval(timer);
						
					}
				},500); */
			}
		}
	});
	</script>
</html>