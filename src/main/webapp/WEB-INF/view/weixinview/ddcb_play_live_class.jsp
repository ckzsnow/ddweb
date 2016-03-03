<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.ICourseDetailDao"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.dao.IUserCourseDao"%>
<%@ page import="com.ddcb.dao.IWeixinUserDao"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.model.UserCourseModel"%>
<%@ page import="com.ddcb.model.CourseDetailModel"%>
<%@ page import="com.ddcb.model.WeixinUserModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDetailDao courseDetailDao = (ICourseDetailDao)wac.getBean("courseDetailDao");
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
IUserCourseDao userCourseDao = (IUserCourseDao)wac.getBean("userCourseDao");
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
UserCourseModel ucm = userCourseDao.getUserCourseByUserIdAndCourseId(userId, id);
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
		<style>
		 video::-webkit-media-controls-enclosure {
            display: none !important;
        }
			video::-webkit-media-controls {
			  display:none !important;
			}
			video::-webkit-media-controls-fullscreen-button {

   display: none;

}

video::-webkit-media-controls-play-button {}

video::-webkit-media-controls-play-button {}

video::-webkit-media-controls-timeline {}

video::-webkit-media-controls-current-time-display{}

video::-webkit-media-controls-time-remaining-display {}

video::-webkit-media-controls-time-remaining-display {}

video::-webkit-media-controls-mute-button {}

video::-webkit-media-controls-toggle-closed-captions-button {}

video::-webkit-media-controls-volume-slider {}
		</style>
	</head>

	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<div style="position: relative;">
			<%if(ucm != null && ucm.getPay_status() != null && ucm.getPay_status() == 1){%>
			<div id="video_div" style="display:none;background:#1cbcd6;">
				<video id="video" webkit-playsinline preload="none" height="100%" poster="/files/imgs/<%=list.get(0).getVideo_image() %>" data-setup="{}">
					<source id="video_src" src="<%=list.get(0).getVideosrc() %>" type='video/mp4'>
				</video>
			</div>
			<%}%>
			<div id="playClassTimeTips" style="width:100%;height:150px;text-align:center;background:#1cbcd6;">
				<p style='color:white;padding-top:50px;'>正在加载数据......</p>
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
		<div style="z-index:9999999999999999;position:fixed;top:0;left:0;width:100%;height:264px;">
		</div>
	</body>
	<script src="/js/weixinjs/jquery.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	document.getElementById('video').setAttribute("width", document.body.clientWidth);
	Date.prototype.pattern=function(fmt) {           
	    var o = {           
	    "M+" : this.getMonth()+1, //月份           
	    "d+" : this.getDate(), //日           
	    "h+" : this.getHours()%12 == 0 ? 12 : this.getHours()%12, //小时           
	    "H+" : this.getHours(), //小时           
	    "m+" : this.getMinutes(), //分           
	    "s+" : this.getSeconds(), //秒           
	    "q+" : Math.floor((this.getMonth()+3)/3), //季度           
	    "S" : this.getMilliseconds() //毫秒           
	    };           
	    var week = {           
	    "0" : "/u65e5",           
	    "1" : "/u4e00",           
	    "2" : "/u4e8c",           
	    "3" : "/u4e09",           
	    "4" : "/u56db",           
	    "5" : "/u4e94",           
	    "6" : "/u516d"          
	    };           
	    if(/(y+)/.test(fmt)){           
	        fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));           
	    }           
	    if(/(E+)/.test(fmt)){           
	        fmt=fmt.replace(RegExp.$1, ((RegExp.$1.length>1) ? (RegExp.$1.length>2 ? "/u661f/u671f" : "/u5468") : "")+week[this.getDay()+""]);           
	    }           
	    for(var k in o){           
	        if(new RegExp("("+ k +")").test(fmt)){           
	            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));           
	        }           
	    }           
	    return fmt;           
	} 
	<%if(ucm != null && ucm.getPay_status() != null && ucm.getPay_status() == 1){%>
	document.addEventListener("WeixinJSBridgeReady", function () {
		var year = <%=courseDate.substring(0, 4)%>;
		var month = <%=courseDate.substring(5, 7)%>;
		var day = <%=courseDate.substring(8, 10)%>;
		var hour = <%=courseDate.substring(11, 13)%>;
		var minute = <%=courseDate.substring(14, 16)%>;
		var seconds = <%=courseDate.substring(17, 19)%>;
		var courseDate = new Date(year, month-1, day, hour, minute, seconds).getTime() / 1000;
		var currentDate = new Date().getTime() / 1000;
		var courseLength = parseInt("<%=courseLength%>") * 60;
		if(courseDate>currentDate) {
			//document.getElementById("video").style.display = "none";
			//document.getElementById("playClassTimeTips").style.display = "";
			document.getElementById("video").pause();
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播时间：<%=courseDateReadable%></p><p id='time_counter' style='color:white;'></p>";
			var timer = setInterval(function(){
				var cDate = new Date();
				$('#time_counter').html(cDate.pattern("yyyy-MM-dd hh:mm:ss"));
				if(cDate.getTime() / 1000 >= courseDate) {
					clearInterval(timer);
					document.getElementById("playClassTimeTips").style.display = "none";
					document.getElementById("video_div").style.display = "";
					document.getElementById("video").currentTime = 0;
					document.getElementById("video").play();
					document.getElementById("video").addEventListener('ended', function(){
						document.getElementById("video").pause();
						document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播已经结束，感谢您的关注！</p>";
						document.getElementById("playClassTimeTips").style.display = "";
						document.getElementById("video_div").style.display = "none";
					}, false);
				}
			},1000);
		} else {
			if(courseDate + courseLength < currentDate) {
				document.getElementById("video").pause();
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播已经结束，感谢您的关注！</p>";
			} else {
				var hasSetTime = false;
				document.getElementById("video").addEventListener("timeupdate", function(){
					if(!hasSetTime && document.getElementById("video").duration > 1) {
						hasSetTime = true;
						document.getElementById("video").pause();
						document.getElementById("video").currentTime = currentDate - courseDate;
						if(currentDate - courseDate >= document.getElementById("video").duration) {
							document.getElementById("video").pause();
							document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播已经结束，感谢您的关注！</p>";
							document.getElementById("playClassTimeTips").style.display = "";
							document.getElementById("video_div").style.display = "none";
						} else {
							document.getElementById("playClassTimeTips").style.display = "none";
							document.getElementById("video_div").style.display = "";
							document.getElementById("video").play();
						}
						
					}
				});
				document.getElementById("video").addEventListener('ended', function(){
					document.getElementById("video").pause();
					document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播已经结束，感谢您的关注！</p>";
					document.getElementById("playClassTimeTips").style.display = "";
					document.getElementById("video_div").style.display = "none";
				}, false);
				document.getElementById("video").play();
			}
		}
	});
	$.ajax({
		url: "/course/addStudyRecord",
		type: "POST",
		data: {courseId:<%=id%>},
		success: function(data) {
		},
		error: function(status, error) {
		}
	});
	<%} else {%>
		var year = <%=courseDate.substring(0, 4)%>;
		var month = <%=courseDate.substring(5, 7)%>;
		var day = <%=courseDate.substring(8, 10)%>;
		var hour = <%=courseDate.substring(11, 13)%>;
		var minute = <%=courseDate.substring(14, 16)%>;
		var seconds = <%=courseDate.substring(17, 19)%>;
		var courseDate = new Date(year, month-1, day, hour, minute, seconds).getTime() / 1000;
		var currentDate = new Date().getTime() / 1000;
		var courseLength = parseInt("<%=courseLength%>") * 60;
		if(courseDate>currentDate) {
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播时间：<%=courseDateReadable%></p><p style='color:white;'>您还没有购买当前课程，无法观看！</p>";
		} else {
			if(courseDate + courseLength < currentDate) {
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播已经结束，感谢您的关注！</p>";
			} else {
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>课程直播中......</p><p style='color:white;'>您还没有购买当前课程，无法观看！</p>";
			}
		}
	<%}%>
	</script>
</html>