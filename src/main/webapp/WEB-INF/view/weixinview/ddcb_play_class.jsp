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
IWeixinUserDao weixinUserDao = (IWeixinUserDao)wac.getBean("weixinUserDao");
List<CourseDetailModel> list = null;
long id = Long.valueOf((String)request.getParameter("course_id"));
session.setAttribute("course_id", String.valueOf(id));
CourseModel cm = courseDao.getCourseByCourseId(id);
list = courseDetailDao.getCourseDetailByCourseId(id);
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/playDDCBOpenClass?course_id=" + id);
String userId = (String)session.getAttribute("openid");
WeixinUserModel wum = weixinUserDao.getWeixinUserByUserId(userId);
long currentTime = System.currentTimeMillis();
int userStatus = 0;
if(wum != null && wum.getPay_status() == 1 && wum.getExpiration_time().getTime()>=currentTime) userStatus = 1;
if(wum != null && wum.getPay_status() == 1 && wum.getExpiration_time().getTime()<currentTime) userStatus = 2;
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
			.dialog {
				position: fixed;
				z-index: 99899999999999999;
				top: 0;
				right: 0;
				bottom: 0;
				left: 0;
				background-color: rgba(0, 0, 0, .4);
			}
			.weui_dialog {
  position: fixed;
  z-index: 13;
  width: 85%;
  top: 50%;
  left: 50%;
  -webkit-transform: translate(-50%, -50%);
          transform: translate(-50%, -50%);
  background-color: #FAFAFC;
  text-align: center;
  border-radius: 3px;
}
.weui_dialog_confirm .weui_dialog .weui_dialog_hd {
  padding: 1.2em 20px .5em;
}
.weui_dialog_confirm .weui_dialog .weui_dialog_bd {
  text-align: left;
}
.weui_dialog_hd {
  padding: 1.2em 0 .5em;
}
.weui_dialog_title {
  font-weight: 400;
  font-size: 17px;
}
.weui_dialog_bd {
  padding: 0 20px;
  font-size: 15px;
  color: #888;
}
.weui_dialog_ft {
  position: relative;
  line-height: 42px;
  margin-top: 20px;
  font-size: 17px;
  display: -webkit-box;
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
}
.weui_dialog_ft a {
  display: block;
  -webkit-box-flex: 1;
  -webkit-flex: 1;
      -ms-flex: 1;
          flex: 1;
  color: #3CC51F;
  text-decoration: none;
  -webkit-tap-highlight-color: rgba(0, 0, 0, 0);
}
.weui_dialog_ft a:active {
  background-color: #EEEEEE;
}
.weui_dialog_ft:after {
  content: " ";
  position: absolute;
  left: 0;
  top: 0;
  width: 100%;
  height: 1px;
  border-top: 1px solid #D5D5D6;
  color: #D5D5D6;
  -webkit-transform-origin: 0 0;
          transform-origin: 0 0;
  -webkit-transform: scaleY(0.5);
          transform: scaleY(0.5);
}
.weui_dialog_confirm .weui_dialog_ft a {
  position: relative;
}
.weui_dialog_confirm .weui_dialog_ft a:after {
  content: " ";
  position: absolute;
  left: 0;
  top: 0;
  width: 1px;
  height: 100%;
  border-left: 1px solid #D5D5D6;
  color: #D5D5D6;
  -webkit-transform-origin: 0 0;
          transform-origin: 0 0;
  -webkit-transform: scaleX(0.5);
          transform: scaleX(0.5);
}
.weui_dialog_confirm .weui_dialog_ft a:first-child:after {
  display: none;
}
.weui_btn_dialog.default {
  color: #353535;
}
.weui_btn_dialog.primary {
  color: #0BB20C;
}
@media screen and (min-width: 1024px) {
  .weui_dialog {
    width: 35%;
  }
}
.weui_mask {
  position: fixed;
  z-index: 1;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(0, 0, 0, 0.6);
}
		</style>
	</head>

	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<div style="position: relative;">
			<div class="video" style="background:#1cbcd6;">
				<video id="video" controls preload="none" height="100%" poster="/files/imgs/<%=list.get(0).getVideo_image() %>" data-setup="{}">
					<source id="video_src" src="<%=list.get(0).getVideosrc() %>" type='video/mp4'>
				</video>
			</div>
		</div>

		<div id="tabtip" class="container">
			<ul id="myTab" class="nav nav-tabs row mantoutab" style="padding-left:0px;padding-right:0px;">
				<li class="col-xs-6 text-center active"><a vinfo="menu" class="center-block" data-toggle="tab">目录</a></li>
				<li class="col-xs-6 text-center"><a vinfo="summary" class="center-block" data-toggle="tab">简介</a></li>
			</ul>
		</div>

		<div class="content">
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="menu">
					<div class="container" style="padding-left:0px;padding-right:0px;">
						<%if(userStatus == 0) {%>
							<p style="margin-top:10px;margin-left:18px;font-size:10px;">非VIP会员只能观看课时1的视频, <a href="/weixin/getDDCBBuyVip" style="font-size:12px;text-decoration: none;color:#22cc99;">请购买VIP</a>！</p>
						<%} else if(userStatus == 2){ %>
							<p style="margin-top:10px;margin-left:18px;font-size:10px;">您的VIP会员已经到期,<a href="/weixin/getDDCBBuyVip" style="font-size:12px;text-decoration: none;color:#22cc99;">请购买VIP</a>！</p>
						<%} %>
						<ul id="course_list" class="mui-table-view" style="margin-top:10px;">
						<%
							int index = 1;
							for(CourseDetailModel cdm : list) {
								if(cdm.getCourse_time_length() != null) {
									if(cdm.getCourse_time_length().indexOf("00:") == 0) {
										cdm.setCourse_time_length(cdm.getCourse_time_length().substring(3));
									}
								}
								if(index != 1) {
									if(userStatus != 1) {
						%>						
							 			<li class="mui-table-view-cell" id="" style="font-size:15px;" data_src=""><span style='float:left;'>课时<%=index %>:<%=cdm.getSubTitle()%></span><span style='float:right;'><%=cdm.getCourse_time_length()%></span></li>
						<%
									} else {			
						%>
										<li class="mui-table-view-cell" style="font-size:15px;" id="course_list_<%=index %>" data_src="<%=cdm.getVideosrc()%>"><span style='float:left;'>课时<%=index %>:<%=cdm.getSubTitle()%></span><span style='float:right;'><%=cdm.getCourse_time_length()%></span></li>										
						<%
									}
								} else {
						%>
									<li class="mui-table-view-cell" style="font-size:15px;color:#22cc99;" id="course_list_<%=index %>" data_src="<%=cdm.getVideosrc()%>" style="color:#22cc99;"><span style='float:left;'>课时<%=index %>:<%=cdm.getSubTitle()%></span><span style='float:right;'><%=cdm.getCourse_time_length()%></span></li>
					    <%
								}
								index++;
							}
					    %>
						</ul>
					</div>
				</div>
				<div class="tab-pane fade in" id="summary">
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
		<div class="weui_dialog_confirm" id="comfirm_dialog" style="display: none;">
	        <div class="weui_mask"></div>
	        <div class="weui_dialog">
	            <div class="weui_dialog_hd"><strong class="weui_dialog_title">点豆大讲堂</strong></div>
	            <div class="weui_dialog_bd" id="comfirm_dialog_tips"></div>
	            <div class="weui_dialog_ft">
	                <a onclick="dialogClickCancel()" class="weui_btn_dialog default">取消</a>
	                <a onclick="dialogClickAccept()" class="weui_btn_dialog primary">确定</a>
	            </div>
	        </div>
	    </div>
	</body>
	<script src="/js/weixinjs/mui.min.js"></script>
	<script src="/js/weixinjs/jquery.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	document.getElementById('video').setAttribute("width", document.body.clientWidth);
	/* document.addEventListener("WeixinJSBridgeReady", function () {
		document.getElementById('video').play();
	}); */
	function dialogClickCancel(){
		document.getElementById("comfirm_dialog").style.display = "none";
		document.getElementById("comfirm_dialog_tips").innerHTML = "";
	}
	function dialogClickAccept(){
		document.getElementById("comfirm_dialog").style.display = "none";
		document.getElementById("comfirm_dialog_tips").innerHTML = "";
		window.location.href="/weixin/getDDCBBuyVip";
	}
	mui.init();
	mui.createConfirmDialog = function(info, cancelCallBack, acceptCallBack) {
		var template = "<div style='width:80%;margin:50% 10%;border:1px solid #ddd;background-color: white;border-radius: 5px;'><div style='margin-top:20px;margin-left:20px;'>提示信息</div><hr/><div style='margin-top:20px;margin-left:20px;margin-bottom:20px;margin-right:20px;height:60px;'>{{info}}</div><div style='text-align:right;margin-bottom:20px;margin-right:20px;'><a id='createConfirmDialog_cancel' href='javascript:void(0);' style='margin-right:20px;text-decoration:none;'>取消</a><a id='createConfirmDialog_accept' href='javascript:void(0);' style='text-decoration:none;'>点击购买</a></div></div>";
		var element = document.createElement('div');
		element.classList.add('dialog');
		element.innerHTML = template.replace('{{info}}', info);
		element.addEventListener('touchmove', mui.preventDefault);
		var mask = [element];
		mask._show = false;
		mask.show = function() {
			mask._show = true;
			element.setAttribute('style', 'opacity:1');
			document.body.appendChild(element);
			document.getElementById('createConfirmDialog_cancel').addEventListener('tap', function() {
				if (cancelCallBack) cancelCallBack();
				mask.close();
			});
			document.getElementById('createConfirmDialog_accept').addEventListener('tap', function() {
				if (acceptCallBack) acceptCallBack();
				mask.close();
			});
			return mask;
		};
		mask._remove = function() {
			if (mask._show) {
				mask._show = false;
				element.setAttribute('style', 'opacity:0');
				mui.later(function() {
					var body = document.body;
					element.parentNode === body && body.removeChild(element);
				}, 350);
			}
			return mask;
		};
		mask.close = function() {
			mask._remove();
		};
		return mask;
	};
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
	$("#course_list li").click(function() {
	   if($(this).attr('data_src') == "") {
		   /* var confirmDialog = mui.createConfirmDialog('您不是VIP会员，只能观看课时1的视频！观看更多视频，请购买VIP会员！',
				function() {
					//confirmDialog.close();
				},
				function() {
					//confirmDialog.close();
					window.location.href="/weixin/getDDCBBuyVip";
				}
			);
			confirmDialog.show();
		   return; */
		   /* document.getElementById("comfirm_dialog_tips").innerHTML = "您不是VIP会员，只能观看课时1的视频！观看更多视频，请购买VIP会员！";
		   document.getElementById("comfirm_dialog").style.display = ""; */
		   var btnArray = ['购买', '取消'];
			mui.confirm('您不是VIP会员，只能观看课时1的视频！观看更多视频，请购买VIP会员！', '点都大讲堂', btnArray, function(e) {
				if (e.index == 0) {
					window.location.href="/weixin/getDDCBBuyVip";
				} else {
					return;
				}
			})
	   } else {
		   $("#video_src").attr('src', $(this).attr('data_src'));
		   $("#course_list li").each(function(){
			   $(this).attr('style', 'font-size:15px;');
		   });
		   $(this).attr('style', 'font-size:15px;color:#22cc99;');
		   document.getElementById('video').load();
		   document.getElementById('video').play();  
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
	var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
	var lineLink = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbd6aef840715f99d&redirect_uri=http%3A%2F%2Fwww.diandou.me%2Fweixin%2FweixinLogin%3Fview%3Dddcb_open_class&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
	var descContent = "点豆成兵---为进取心而生，专注职场“传、帮、带”";
	var shareTitle = "点豆成兵";
	var shareCircleTitle = "<%=cm.getName()%>-<%=cm.getTeacher()%>-[点豆大讲堂]";
	<%if(list != null && !list.isEmpty()) {%>
		imgUrl = "http://www.diandou.me/files/imgs/<%=list.get(0).getTeacher_image()%>";
		<%-- descContent = "<%=list.get(0).getDetails().replaceAll("\r\n", "")%>";
		shareTitle = "<%=list.get(0).getSubTitle().replaceAll("\r\n", "")%>"; --%>
		descContent = "主讲人：<%=cm.getTeacher()%>";
		shareTitle = "<%=cm.getName()%>-[点豆大讲堂]";
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
	wx.ready(function(){
		setTimeout(function(){
			wx.onMenuShareTimeline({
			    title: shareCircleTitle, // 分享标题
			    link: lineLink, // 分享链接
			    imgUrl: imgUrl, // 分享图标
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
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
			    success: function () { 
			        // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			        // 用户取消分享后执行的回调函数
			    }
			});
			wx.onMenuShareQQ({
			    title: shareTitle, // 分享标题
			    desc: descContent, // 分享描述
			    link: lineLink, // 分享链接
			    imgUrl: imgUrl, // 分享图标
			    success: function () { 
			       // 用户确认分享后执行的回调函数
			    },
			    cancel: function () { 
			       // 用户取消分享后执行的回调函数
			    }
			});
		}, 500);
	});
	</script>
</html>