<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.ICourseDetailDao"%>
<%@ page import="com.ddcb.dao.ICourseDao"%>
<%@ page import="com.ddcb.dao.IUserCourseDao"%>
<%@ page import="com.ddcb.dao.IWeixinUserDao"%>
<%@ page import="com.ddcb.dao.IUserForwardDao"%>
<%@ page import="com.ddcb.dao.ILiveClassShareDao"%>
<%@ page import="com.ddcb.dao.IUserLiveCoursePayDao"%>
<%@ page import="com.ddcb.model.LiveCourseShareModel"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.model.UserCourseModel"%>
<%@ page import="com.ddcb.model.CourseDetailModel"%>
<%@ page import="com.ddcb.model.UserForwardModel"%>
<%@ page import="com.ddcb.model.UserLiveCoursePayModel"%>
<%@ page import="com.ddcb.model.WeixinUserModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
ICourseDetailDao courseDetailDao = (ICourseDetailDao)wac.getBean("courseDetailDao");
IUserLiveCoursePayDao userLiveCoursePayDao = (IUserLiveCoursePayDao)wac.getBean("userLiveCoursePayDao");
ICourseDao courseDao = (ICourseDao)wac.getBean("courseDao");
IUserCourseDao userCourseDao = (IUserCourseDao)wac.getBean("userCourseDao");
IUserForwardDao userForwardDao = (IUserForwardDao)wac.getBean("userForwardDao");
ILiveClassShareDao liveClassShareDao = (ILiveClassShareDao) wac.getBean("liveClassShareDao");
List<CourseDetailModel> list = null;
long id = Long.valueOf((String)request.getParameter("course_id"));
list = courseDetailDao.getCourseDetailByCourseId(id);
CourseModel cm = courseDao.getCourseByCourseId(id);
System.out.println(cm.toString());
CourseModel parentCm = courseDao.getCourseByCourseId(cm.getParentId());
String parentCourseExist = parentCm != null && parentCm.getId() != 0? "exist" : "notExist";
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/playDDCBLiveClass?course_id=" + id);
String userId = (String)session.getAttribute("openid");
String nickname = (String)session.getAttribute("nickname");
String headimgurl = (String)session.getAttribute("headimgurl");
String courseDate = cm.getCourse_date().toString();
String courseDateReadable = cm.getCourse_date_readable();
String courseLength = cm.getCourse_length();
UserCourseModel ucm = userCourseDao.getUserCourseByUserIdAndCourseId(userId, id);
UserForwardModel ufm =  userForwardDao.getUserForwardByUserIdAndCourseId(userId, id);
String userStatus = "";
if(cm.getPrice() == null || cm.getPrice().isEmpty() || ("0").equals(cm.getPrice())) {
	if(ufm == null || ufm.getScreenshot() == null || !("1").equals(ufm.getScreenshot()))
		userStatus = "1";//不收费课程，用户未分享
	else
		userStatus = "2";//不收费课程，用户已分享
} else {
	if(ucm != null && ucm.getPay_status() != null && ucm.getPay_status() == 1)
		userStatus = "3";//收费课程，用户已购买
	else
		userStatus = "4";//收费课程，用户未购买
}
long currentTime = System.currentTimeMillis();
IWeixinUserDao weixinUserDao = (IWeixinUserDao)wac.getBean("weixinUserDao");
WeixinUserModel wum = weixinUserDao.getWeixinUserByUserId(userId);
if(wum != null && wum.getPay_status() == 1 && wum.getExpiration_time().getTime()>=currentTime) {
	userStatus = "2";
}
UserLiveCoursePayModel ulcpm = userLiveCoursePayDao.getUserCourseByUserIdAndCourseId(userId, id);
if(ulcpm != null && ulcpm.getPay_status() == 1) {
	userStatus = "2";
}
LiveCourseShareModel lcsm = liveClassShareDao.getLiveClassShareByCourseId(id);
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
		 /* video::-webkit-media-controls-enclosure {
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

video::-webkit-media-controls-volume-slider {} */
		</style>
	</head>

	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<div style="position: relative;">
			<%if(("2").equals(userStatus) || ("3").equals(userStatus)){%>
			<div id="video_div" style="display:none;background:#1cbcd6;">
				<video id="video" controls preload="none" height="100%" poster="/files/imgs/<%=list.get(0).getVideo_image() %>" data-setup="{}">
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
				<li class="col-xs-6 text-center active" style="backgroud-color:white;"><a vinfo="summary" class="center-block" data-toggle="tab">简介</a></li>
				<li class="col-xs-6 text-center" style="backgroud-color:white;"><a vinfo="question" class="center-block" data-toggle="tab">提问</a></li>
			</ul>
		</div>

		<div class="content">
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="summary">
					<div class="container" style="backgroud-color:white;">
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
				<div class="tab-pane fade in" id="question">
					<div class="container" id="questionList" style="margin-bottom:63px;">	
					</div>
					<div class="container publishbox" id="question_publish">
                        <div class="row publish">
                            <div class="col-xs-10 cmtcnt">
                                <textarea id="replycotent" placeholder="有问题？快提出来吧~" rows="1" cols="40" style="overflow:scroll;overflow-y:hidden;;overflow-x:hidden"></textarea>
                            </div>
                            <div class="col-xs-2 cmtbtn"><span replaycommenttext="" replyname="" replycommentid="0" replyuserid="0" id="publishbtn" class="text-center publishbtn">提问</span></div>
                        </div>
                    </div>
				</div>
			</div>
		</div>
		<div id="countdown" style="display:none;width:100%;"></div>
	</body>
	<script src="/js/weixinjs/jquery.js"></script>
	<script src="/js/weixinjs/jquery.countdown.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	var userStatus = "<%=userStatus%>";
	$("#myTab li a").click(function() {
	    $(this).parent().addClass("active");
	    $(this).parent().siblings().removeClass("active");
	    $(this).parent().css("background-color", "#fff");
	    $("#" + $(this).attr("vinfo")).attr("style", "display:block;opacity:1")
	    $("#" + $(this).attr("vinfo")).siblings().hide();
	    if ($(this).attr("vinfo") == "comment") {
	        $(".navbar-fixed-bottom").hide();
	    } else {
	        $(".navbar-fixed-bottom").show();
	    }
	});
	var imgUrl = "http://www.diandou.me/files/imgs/<%=cm.getImage()%>";
	var lineLink = window.location.href;
	var descContent = "<%=cm.getName()%>";
	var shareTitle = "<%=cm.getName()%>";
	<%if (lcsm != null) {%>
		imgUrl = "http://www.diandou.me/files/imgs/<%=lcsm.getImage()%>";
		descContent = "<%=lcsm.getTitle()%>";
		shareTitle = "<%=lcsm.getTitle()%>";
		lineLink = "<%=lcsm.getLink()%>";
	<%}%>
	var globalPlayStatus = "stop";
	var playVideoEvent = 1;
	var pauseVideoEvent = 1;
	var seekingVideoEvent = 1;
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
	<%if(("2").equals(userStatus) || ("3").equals(userStatus)){%>
	document.getElementById('video').setAttribute("width", document.body.clientWidth);
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
		document.getElementById("video").addEventListener('pause', function(){
			globalPlayStatus = "pause";
		});
		document.getElementById("video").addEventListener('play', function(){
			if(playVideoEvent > 2) {
				currentDate = new Date().getTime() / 1000;
				document.getElementById("video").pause();
				document.getElementById("video").currentTime = currentDate - courseDate;
				if(currentDate - courseDate >= document.getElementById("video").duration) {
					//document.getElementById("video").pause();
					<%if(("exist").equals(parentCourseExist)) {%>
					document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
					var countDown = 5;
					var timer = setInterval(function(){
						if(countDown == 0) {
							clearInterval(timer);
							window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
						} else {
							document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
						}
						countDown--;
					}, 1000);
					<%} else {%>
					document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
					<%}%>		
					document.getElementById("playClassTimeTips").style.display = "";
					document.getElementById("video_div").style.display = "none";
				} else {
					document.getElementById("playClassTimeTips").style.display = "none";
					document.getElementById("video_div").style.display = "";
					document.getElementById("video").play();
					globalPlayStatus = "play";
					playVideoEvent = 2;
				}
			} else {
				playVideoEvent++;
			}
		});
		document.getElementById("video").addEventListener('seeking', function(){
			if(seekingVideoEvent == 1) {
				seekingVideoEvent++;
				return;
			}
			seekingVideoEvent++;
			if(seekingVideoEvent == 4) {
				currentDate = new Date().getTime() / 1000;
				document.getElementById("video").pause();
				document.getElementById("video").currentTime = currentDate - courseDate;
				if(currentDate - courseDate >= document.getElementById("video").duration) {
					//document.getElementById("video").pause();
					<%if(("exist").equals(parentCourseExist)) {%>
					document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
					var countDown = 5;
					var timer = setInterval(function(){
						if(countDown == 0) {
							clearInterval(timer);
							window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
						} else {
							document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
						}
						countDown--;
					}, 1000);
					<%} else {%>
					document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
					<%}%>		
					document.getElementById("playClassTimeTips").style.display = "";
					document.getElementById("video_div").style.display = "none";
				} else {
					document.getElementById("playClassTimeTips").style.display = "none";
					document.getElementById("video_div").style.display = "";
					document.getElementById("video").play();
					globalPlayStatus = "play";
					seekingVideoEvent = 2;
					playVideoEvent = 2;
				}
			}
		});
		if(courseDate>currentDate) {
			//document.getElementById("video").style.display = "none";
			//document.getElementById("playClassTimeTips").style.display = "";
			//document.getElementById("video").pause();
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>播放讲座时间：<%=courseDateReadable%></p><p id='time_counter' style='color:white;'></p>";
			var ts = new Date(year, month-1, day, hour, minute, seconds);
			$('#countdown').countdown({
				timestamp	: ts,
				callback	: function(days, hours, minutes, seconds){	
					var message = "倒计时：";
					message += days + " 天" + ", ";
					message += hours + " 小时" + ", ";
					message += minutes + " 分钟" + ", ";
					message += seconds + " 秒" + " <br />";
					//message += "欢迎您收看点豆成兵直播课！";
					$('#time_counter').html(message);
					if(days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
						document.getElementById("playClassTimeTips").style.display = "none";
						document.getElementById("video_div").style.display = "";
						document.getElementById("video").currentTime = 0;
						document.getElementById("video").play();
						globalPlayStatus = "play";
						document.getElementById("video").addEventListener('ended', function() {
							currentDate = new Date().getTime() / 1000;
							document.getElementById("video").currentTime = currentDate - courseDate;
							if (currentDate - courseDate >= document.getElementById("video").duration) {
								globalPlayStatus = "end";
								//document.getElementById("video").pause();
								<%if(("exist").equals(parentCourseExist)) {%>
								document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
								var countDown = 5;
								var timer = setInterval(function() {
									if (countDown == 0) {
										clearInterval(timer);
										window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
									} else {
										document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座," + countDown + "秒后自动跳转。";
									}
									countDown--;
								}, 1000);
								<%} else {%>
								document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
								<%}%>
								document.getElementById("playClassTimeTips").style.display = "";
								document.getElementById("video_div").style.display = "none";
							} else {
								document.getElementById("playClassTimeTips").style.display = "none";
								document.getElementById("video_div").style.display = "";
								document.getElementById("video").play();
								globalPlayStatus = "play";
								seekingVideoEvent = 2;
								playVideoEvent = 2;
							}
						}, false);
					}
				}
			});
		} else {
			if(courseDate + courseLength < currentDate) {
				//$("#question_publish").hide();
				document.getElementById("video").pause();
				<%if(("exist").equals(parentCourseExist)) {%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
				var countDown = 5;
				var timer = setInterval(function(){
					if(countDown == 0) {
						clearInterval(timer);
						window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
					} else {
						document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
					}
					countDown--;
				}, 1000);
				<%} else {%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
				<%}%>		
			} else {
				var hasSetTime = false;
				document.getElementById("video").addEventListener("timeupdate", function(){
					if(!hasSetTime && document.getElementById("video").duration > 1) {
						hasSetTime = true;
						document.getElementById("video").pause();
						document.getElementById("video").currentTime = currentDate - courseDate;
						if(currentDate - courseDate >= document.getElementById("video").duration) {
							//document.getElementById("video").pause();
							<%if(("exist").equals(parentCourseExist)) {%>
							document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
							var countDown = 5;
							var timer = setInterval(function(){
								if(countDown == 0) {
									clearInterval(timer);
									window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
								} else {
									document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
								}
								countDown--;
							}, 1000);
							<%} else {%>
							document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
							<%}%>		
							document.getElementById("playClassTimeTips").style.display = "";
							document.getElementById("video_div").style.display = "none";
						} else {
							document.getElementById("playClassTimeTips").style.display = "none";
							document.getElementById("video_div").style.display = "";
							document.getElementById("video").play();
							globalPlayStatus = "play";
						}
					}
				});
				document.getElementById("video").addEventListener('ended', function(){
					currentDate = new Date().getTime() / 1000;
					document.getElementById("video").currentTime = currentDate - courseDate;
					if(currentDate - courseDate >= document.getElementById("video").duration) {
						globalPlayStatus = "end";
						//document.getElementById("video").pause();
						<%if(("exist").equals(parentCourseExist)) {%>
						document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
						var countDown = 5;
						var timer = setInterval(function(){
							if(countDown == 0) {
								clearInterval(timer);
								window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
							} else {
								document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
							}
							countDown--;
						}, 1000);
						<%} else {%>
						document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
						<%}%>		
						document.getElementById("playClassTimeTips").style.display = "";
						document.getElementById("video_div").style.display = "none";
					} else {
						document.getElementById("playClassTimeTips").style.display = "none";
						document.getElementById("video_div").style.display = "";
						document.getElementById("video").play();
						globalPlayStatus = "play";
						seekingVideoEvent = 2;
						playVideoEvent = 2;
					}
				}, false);
				document.getElementById("video").play();
				globalPlayStatus = "play";
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
	//$("#question_publish").hide();
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
			<%if(("4").equals(userStatus)){%>
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>播放讲座时间：<%=courseDateReadable%></p><p style='color:white;'>您还没有购买当前课程，无法观看！</p>";
			<%} else {%>
			document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>播放讲座时间：<%=courseDateReadable%></p><p style='color:white;'>您还没有在朋友圈分享当前课程，无法观看！</p>";
			<%}%>
		} else {
			if(courseDate + courseLength < currentDate) {
				<%if(("exist").equals(parentCourseExist)) {%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p id='redirect_open_class' style='color:white;'>大讲堂已收录该讲座</p>";
				var countDown = 5;
				var timer = setInterval(function(){
					if(countDown == 0) {
						clearInterval(timer);
						window.location.href = "/playDDCBOpenClass?course_id=<%=parentCm.getId() %>";
					} else {
						document.getElementById("redirect_open_class").innerHTML = "大讲堂已收录该讲座,"+countDown+"秒后自动跳转。";
					}
					countDown--;
				}, 1000);
				<%} else {%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>录播讲座已经结束，感谢您的关注！</p><p style='color:white;'>大讲堂正在收录该讲座，<br/>稍后请至大讲堂查看。</p>";
				<%}%>		
			} else {
				<%if(("4").equals(userStatus)){%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>播放讲座中......</p><p style='color:white;'>您还没有购买当前课程，无法观看！</p>";
				<%} else {%>
				document.getElementById("playClassTimeTips").innerHTML = "<p style='color:white;padding-top:50px;'>播放讲座中......</p><p style='color:white;'>您还没有在朋友圈分享当前课程，无法观看！</p>";
				<%}%>		
			}
		}
	<%}%>
	wx.config({
		appId: 'wxbd6aef840715f99d',
		timestamp: <%=result.get("timestamp")%>,
		nonceStr: '<%=result.get("nonceStr")%>',
		signature: '<%=result.get("signature")%>',
		jsApiList: [
			'onMenuShareQQ',
			'onMenuShareTimeline',
			'onMenuShareAppMessage',
			'chooseWXPay'
		]
	});
	<%-- var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
	var lineLink = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbd6aef840715f99d&redirect_uri=http%3A%2F%2Fwww.diandou.me%2Fweixin%2FweixinLogin%3Fview%3Dddcb_live_class&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	var descContent = "点豆大讲堂---为进取心而生，专注职场“传、帮、带”";
	var shareTitle = "点豆大讲堂";
	var shareCircleTitle = "直播-<%=cm.getName()%>-<%=cm.getTeacher()%>-[点豆大讲堂]";
	<%if (list != null && !list.isEmpty()) {%>
		imgUrl = "http://www.diandou.me/files/imgs/<%=cm.getImage()%>";
		descContent = "主讲人：<%=cm.getTeacher()%>";
		shareTitle = "直播-<%=cm.getName()%>-[点豆大讲堂]";
	<%}%> --%>
wx.ready(function() {
setTimeout(function() {
	wx.onMenuShareTimeline({
		title : shareTitle, // 分享标题
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
			$.ajax({
        		url: "/course/uploadUserShare",
        		type: "POST",
        		data: {courseId:"<%=cm.getId()%>"},
        		success: function(data) {
        			alert("报名成功！");
        			window.location.href="/playDDCBLiveClass?course_id=<%=id%>";
        		},
        		error: function(status, error) {
        			alert("报名失败！");
        		}
        	});			
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
			alert("您没有分享，报名失败！");
		}
	});
	wx.onMenuShareAppMessage({
		title : shareTitle, // 分享标题
		desc : descContent, // 分享描述
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		type : '', // 分享类型,music、video或link，不填默认为link
		dataUrl : '', // 如果type是music或video，则要提供数据链接，默认为空
		success : function() {
			// 用户确认分享后执行的回调函数
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});
	wx.onMenuShareQQ({
		title : shareTitle, // 分享标题
		desc : descContent, // 分享描述
		link : lineLink, // 分享链接
		imgUrl : imgUrl, // 分享图标
		success : function() {
			// 用户确认分享后执行的回调函数
		},
		cancel : function() {
			// 用户取消分享后执行的回调函数
		}
	});
}, 500);
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
var countPerPage = 5;
$.ajax({
	url: "/getAllCourseQuestions",
	type: "POST",
	data: {course_id:<%=id%>, page:page, count:countPerPage},
	success: function(data) {
		if (!checkJsonIsEmpty(data)) {
			var questionList = $('#questionList');
			var questionListHTML = "";
			var count = 0;
			var hasBind = false;
			for (i in data) {
				questionListHTML += "<div class='row commentlist'><div class='col-xs-3 commenter basecommenter'><img src='"+data[i].headimgurl+"'></div><div class='cmtdetials'><div class='row'><div class='col-xs-12 text-left name'>"+data[i].user_nickname+"</div></div><div class='row'><div class='col-xs-12'><p>"+data[i].question+"</p></div></div><div class='row'><div class='col-xs-7 text-left time'>"+data[i].create_time_readable.substring(0,16)+"</div><div current_click_like='"+data[i].current_click_like+"' question_id='"+data[i].id+"' class='col-xs-5 text-right commentbuttom clicklikecall'><span class='agree'><span class='agreeimg'><img style='width: 16px;height: 16px;border-radius: 0;margin-left: 0;vertical-align: top;' src='/img/weixinimg/priced.png'></span><span class='count'>"+data[i].click_like+"</span></span></div></div></div></div>";
				count++;
			}
			if(count>=countPerPage) {
				hasBind = true;
				questionListHTML += "<h3 id='show' style='height:40px;margin-top:18px;'><span class='click'>点击展开更多问题</span><span class='glyphicon glyphicon-menu-down'></span></h3>";
			} else {
				questionListHTML += "<h3 id='show' style='height:40px;margin-top:18px;'><p class='click'>该课程所有问题均已显示</p></h3>";
			} 
			questionList.html(questionListHTML);
			$('.clicklikecall').each(function(){
				$(this).click(function(){
					var questionId = $(this).attr("question_id");
					var current_click_like = $(this).attr("current_click_like");
					if(current_click_like == "0") {
						alert("点赞成功！");
						$(this).attr("current_click_like", "1");
						var ct = parseInt($(this).find('.count')[0].innerHTML);
						$($(this).find('.count')[0]).html(ct + 1);
						current_click_like = "1";
					} else if(current_click_like == "1"){
						alert("取消点赞成功！！");
						$(this).attr("current_click_like", "0");
						var ct = parseInt($(this).find('.count')[0].innerHTML);
						$($(this).find('.count')[0]).html(ct - 1);
						current_click_like = "0";
					}
					$.ajax({
						url: "/userClickLikeQuestion",
						type: "POST",
						data: {id:questionId, like:current_click_like},
						success: function(data) {
						},
						error: function(status, error) {
						}
					});
				})
			});
			if(hasBind) {
				$("#show").click(function(){
					page++;
					$.ajax({
						url: "/getAllCourseQuestions",
						type: "POST",
						data: {course_id:<%=id%>, page:page, count:countPerPage},
						success: function(data) {
							if (!checkJsonIsEmpty(data)) {
								var questionList = $('#questionList > div:last');
								var questionListHTML = "";
								var pullcount = 0;
								for (i in data) {
									questionListHTML += "<div class='row commentlist'><div class='col-xs-3 commenter basecommenter'><img src='"+data[i].headimgurl+"'></div><div class='cmtdetials'><div class='row'><div class='col-xs-12 text-left name'>"+data[i].user_nickname+"</div></div><div class='row'><div class='col-xs-12'><p>"+data[i].question+"</p></div></div><div class='row'><div class='col-xs-7 text-left time'>"+data[i].create_time_readable.substring(0,16)+"</div><div current_click_like='"+data[i].current_click_like+"' question_id='"+data[i].id+"' class='col-xs-5 text-right commentbuttom clicklikecall_"+page+"'><span class='agree'><span class='agreeimg'><img style='width: 16px;height: 16px;border-radius: 0;margin-left: 0;vertical-align: top;' src='/img/weixinimg/priced.png'></span><span class='count'>"+data[i].click_like+"</span></span></div></div></div></div>";
									pullcount++;
								}
								questionList.append(questionListHTML);
								$('.clicklikecall_'+page).each(function(){
									$(this).click(function(){
										var questionId = $(this).attr("question_id");
										var current_click_like = $(this).attr("current_click_like");
										if(current_click_like == "0") {
											alert("点赞成功！");
											$(this).attr("current_click_like", "1");
											var ct = parseInt($(this).find('.count')[0].innerHTML);
											$($(this).find('.count')[0]).html(ct + 1);
											current_click_like = "1";
										} else if(current_click_like == "1"){
											alert("取消点赞成功！！");
											$(this).attr("current_click_like", "0");
											var ct = parseInt($(this).find('.count')[0].innerHTML);
											$($(this).find('.count')[0]).html(ct - 1);
											current_click_like = "0";
										}
										$.ajax({
											url: "/userClickLikeQuestion",
											type: "POST",
											data: {id:questionId, like:current_click_like},
											success: function(data) {
											},
											error: function(status, error) {
											}
										});
									})
								});
								if(pullcount >= countPerPage) {
									
								} else {
									$("#show").unbind("click"); 
									$("#show").html("<p class='click'>该课程所有问题均已显示</p>");
								}
							} else {
								$("#show").unbind("click"); 
								$("#show").html("<p class='click'>该课程所有问题均已显示</p>");
							}
						},
						error: function(status, error) {
						}
					});
				});
			}
		} else {
			var questionList = $('#questionList');
			var questionListHTML = "<h3 id='show' style='height:40px;margin-top:18px;'><p class='click'>该课程当前还没有用户提问</p></h3>";
			questionList.append(questionListHTML);
		}
	},
	error: function(status, error) {
	}
});
Date.prototype.Format = function(fmt)   
{ //author: meizz   
  var o = {   
    "M+" : this.getMonth()+1,                 //月份   
    "d+" : this.getDate(),                    //日   
    "h+" : this.getHours(),                   //小时   
    "m+" : this.getMinutes(),                 //分   
    "s+" : this.getSeconds(),                 //秒   
    "q+" : Math.floor((this.getMonth()+3)/3), //季度   
    "S"  : this.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
}  
var publish_index = 1;
$("#publishbtn").click(function(){
	var question = $('#replycotent').val();
	if(question == null || question=="") {
		alert("提问内容不能为空！");
		return;
	}
	if(userStatus == "1" || userStatus == "4") {
		alert("您还没有报名，请您先报名！");
		return;
	}
	$.ajax({
		url: "/userPublishQuestion",
		type: "POST",
		data: {course_id:<%=id%>, question:question},
		success: function(data) {
			if(data != null && data.error_code == '0') {
				alert("提问成功！");
				publish_index++;
				var htmlStr = "<div class='row commentlist'><div class='col-xs-3 commenter basecommenter'><img src='<%=headimgurl%>'></div><div class='cmtdetials'><div class='row'><div class='col-xs-12 text-left name'><%=nickname%></div></div><div class='row'><div class='col-xs-12'><p>"+question+"</p></div></div><div class='row'><div class='col-xs-7 text-left time'>"+new Date().pattern("yyyy-MM-dd HH:mm")+"</div><div current_click_like='0' question_id='"+data.error_msg+"' class='col-xs-5 text-right commentbuttom clicklikecall_publish_"+publish_index+"'><span class='agree'><span class='agreeimg'><img style='width: 16px;height: 16px;border-radius: 0;margin-left: 0;vertical-align: top;' src='/img/weixinimg/priced.png'></span><span class='count'>0</span></span></div></div></div></div>";
				$('#questionList').prepend(htmlStr);
				$("#show").html("<p class='click'>该课程所有问题均已显示</p>");
				$('.clicklikecall_publish_' + publish_index).each(function(){
					$(this).click(function(){
						var questionId = $(this).attr("question_id");
						var current_click_like = $(this).attr("current_click_like");
						if(current_click_like == "0") {
							alert("点赞成功！");
							$(this).attr("current_click_like", "1");
							var ct = parseInt($(this).find('.count')[0].innerHTML);
							$($(this).find('.count')[0]).html(ct + 1);
							current_click_like = "1";
						} else if(current_click_like == "1"){
							alert("取消点赞成功！！");
							$(this).attr("current_click_like", "0");
							var ct = parseInt($(this).find('.count')[0].innerHTML);
							$($(this).find('.count')[0]).html(ct - 1);
							current_click_like = "0";
						}
						$.ajax({
							url: "/userClickLikeQuestion",
							type: "POST",
							data: {id:questionId, like:current_click_like},
							success: function(data) {
							},
							error: function(status, error) {
							}
						});
					})
				});
			} else {
				alert(data.error_msg);
			}
		},
		error: function(status, error) {
			alert("网络原因，提问失败，请稍后重试！");
		}
	});
});

setInterval(function(){
	$.ajax({
		url: "/statistics/addPerMinuteStatistics",
		type: "POST",
		data: {course_id:<%=id%>, open_id:'<%=userId%>', play_status:globalPlayStatus},
		success: function(data) {
		},
		error: function(status, error) {
		}
	});
}, 1000*60);
$.ajax({
	url: "/statistics/addPerMinuteStatistics",
	type: "POST",
	data: {course_id:<%=id%>, open_id:'<%=userId%>', play_status:globalPlayStatus},
	success: function(data) {
	},
	error: function(status, error) {
	}
});
	</script>
</html>