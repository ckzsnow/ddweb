<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.dao.IUserCourseDao"%>
<%@ page import="com.ddcb.dao.IUserCollectionDao"%>
<%@ page import="com.ddcb.dao.IUserStudyRecordDao"%>
<%@ page import="com.ddcb.dao.IWeixinUserDao"%>
<%@ page import="com.ddcb.model.CourseModel"%>
<%@ page import="com.ddcb.model.LiveCourseModel"%>
<%@ page import="com.ddcb.model.CourseDetailModel"%>
<%@ page import="com.ddcb.model.WeixinUserModel"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.sql.Timestamp"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
IUserCourseDao userCourseDao = (IUserCourseDao)wac.getBean("userCourseDao");
IWeixinUserDao weixinUserDao = (IWeixinUserDao)wac.getBean("weixinUserDao");
IUserCollectionDao userCollectionDao = (IUserCollectionDao)wac.getBean("userCollectionDao");
IUserStudyRecordDao userStudyRecordDao = (IUserStudyRecordDao)wac.getBean("userStudyRecordDao");
Map<String, String> result = new HashMap<>();
String code = (String)session.getAttribute("url_code");
result = WeixinTools.getSign("http://www.diandou.me/weixin/weixinLogin?view=ddcb_user_center&code="+code+"&state=123");
String userId = (String)session.getAttribute("openid");
String nickname = (String)session.getAttribute("nickname");
String headimgurl = (String)session.getAttribute("headimgurl");
WeixinUserModel wum = weixinUserDao.getWeixinUserByUserId(userId);
Date currentDate = new Date();
int userStatus = 0;//非会员
String vipExpirationTime = wum == null || wum.getExpiration_time() == null ? "" : wum.getExpiration_time().toString().substring(0, 10);
if(wum !=null && wum.getPay_status() != 0) {
	long expirationTime = wum.getExpiration_time().getTime();
	long currentTime = currentDate.getTime();
	if(currentTime >= expirationTime) {
		userStatus = 1;//会员已经超期
	} else {
		userStatus = 2;//会员且没有超期
	}
}

List<CourseModel> buyCourseList = userCourseDao.getUserBuyClass(userId);
List<LiveCourseModel> collectionOpenCourseList = userCollectionDao.getUserCollectionOpenCourse(userId);
List<LiveCourseModel> collectionLiveCourseList = userCollectionDao.getUserCollectionLiveCourse(userId);
List<LiveCourseModel> userStudyRecordCourseList = userStudyRecordDao.getUserStudyRecord(userId);
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
        <link rel="stylesheet" href="/css/weixincss/mygrowup.css">
        <link rel="stylesheet" href="/css/weixincss/course.css" />
        <link rel="stylesheet" href="/css/weixincss/mui.min.css" />
        <style>
        	.mui-views,
			.mui-view,
			.mui-pages,
			.mui-page,
			.mui-page-content {
				position: absolute;
				left: 0;
				right: 0;
				top: 0;
				bottom: 0;
				width: 100%;
				height: 100%;
				background-color: #efeff4;
			}
			.mui-pages {
				top: 46px;
				height: auto;
			}
			.mui-scroll-wrapper,
			.mui-scroll {
				background-color: #efeff4;
			}
			.mui-page.mui-transitioning {
				-webkit-transition: -webkit-transform 300ms ease;
				transition: transform 300ms ease;
			}
			.mui-page-left {
				-webkit-transform: translate3d(0, 0, 0);
				transform: translate3d(0, 0, 0);
			}
			.mui-ios .mui-page-left {
				-webkit-transform: translate3d(-20%, 0, 0);
				transform: translate3d(-20%, 0, 0);
			}
			.mui-navbar {
				position: fixed;
				right: 0;
				left: 0;
				z-index: 10;
				height: 44px;
				background-color: #f7f7f8;
			}
			.mui-navbar .mui-bar {
				position: absolute;
				background: transparent;
				text-align: center;
			}
			.mui-android .mui-navbar-inner.mui-navbar-left {
				opacity: 0;
			}
			.mui-ios .mui-navbar-left .mui-left,
			.mui-ios .mui-navbar-left .mui-center,
			.mui-ios .mui-navbar-left .mui-right {
				opacity: 0;
			}
			.mui-navbar .mui-btn-nav {
				-webkit-transition: none;
				transition: none;
				-webkit-transition-duration: .0s;
				transition-duration: .0s;
			}
			.mui-navbar .mui-bar .mui-title {
				display: inline-block;
				width: auto;
			}
			.mui-page-shadow {
				position: absolute;
				right: 100%;
				top: 0;
				width: 16px;
				height: 100%;
				z-index: -1;
				content: '';
			}
			.mui-page-shadow {
				background: -webkit-linear-gradient(left, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
				background: linear-gradient(to right, rgba(0, 0, 0, 0) 0, rgba(0, 0, 0, 0) 10%, rgba(0, 0, 0, .01) 50%, rgba(0, 0, 0, .2) 100%);
			}
			.mui-navbar-inner.mui-transitioning,
			.mui-navbar-inner .mui-transitioning {
				-webkit-transition: opacity 300ms ease, -webkit-transform 300ms ease;
				transition: opacity 300ms ease, transform 300ms ease;
			}
			.mui-page {
				display: none;
			}
			.mui-pages .mui-page {
				display: block;
			}
			.mui-page .mui-table-view:first-child {
				margin-top: 0px;
			}
			.mui-page .mui-table-view:last-child {
				margin-bottom: 0px;
			}
			.mui-table-view {
				margin-top: 20px;
			}
			
			.mui-table-view span.mui-pull-right {
				color: #999;
			}
			.mui-table-view-divider {
				background-color: #efeff4;
				font-size: 14px;
			}
			.mui-table-view-divider:before,
			.mui-table-view-divider:after {
				height: 0;
			}
			.head {
				height: 40px;
			}
			#head {
				line-height: 40px;
			}
			.head-img {
				width: 40px;
				height: 40px;
			}
			#head-img1 {
				position: absolute;
				bottom: 10px;
				right: 40px;
				width: 40px;
				height: 40px;
			}
			.update {
				font-style: normal;
				color: #999999;
				margin-right: -25px;
				font-size: 15px
			}
			.mui-fullscreen {
				position: fixed;
				z-index: 20;
				background-color: #000;
			}
			.mui-ios .mui-navbar .mui-bar .mui-title {
				position: static;
			}
			.dialog {
				position: fixed;
				z-index: 998;
				top: 0;
				right: 0;
				bottom: 0;
				left: 0;
				background-color: rgba(0, 0, 0, .4);
			}
        </style>
    </head>
    <body style="padding-bottom:10px;">
    	<div id="user_center" class="mui-views">
			<div class="mui-view">
				<div class="mui-navbar">
				</div>
				<div class="mui-pages">
				</div>
			</div>
		</div>
    	<div id="mainpage" class="mui-page">
    		<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<h1 class="mui-center mui-title" style="color:white;">个人中心</h1>
			</div>
	        <div class="mygrowup">            
	            <div class="avatar">
	                <div class="avatarimg center-block">
						<img id="person_img" src="<%=headimgurl %>" alt="个人头像"/>
	                </div>
	            </div>
	            <p class="nickname text-center" id="nickname">
	            	<span style='color:white;font-size:15px;font-weight:300;'><%=nickname %></span>
	            	<%if(userStatus == 0) { %>
	            		<span style='font-size:10px;background-color:#888888;color:white;padding:1px 6px;margin-left:5px;line-height:20px;height:20px;border-radius:5px;'>非VIP会员</span>
	            	<%} else if(userStatus == 1){ %>
	            		<span style='font-size:10px;background-color: #888888;color:white;padding:1px 6px;margin-left:5px;line-height:20px;height:20px;border-radius:5px;'>VIP会员</span>
	            	<%} else {%>
	            		<span style='font-size:10px;background-color: #f0ad4e;color:white;padding:1px 6px;margin-left:5px;line-height:20px;height:20px;border-radius:5px;'>VIP会员</span>
	            	<%} %>
	            <p>
	            <h2 class="experience text-center"></h2>
	            <ul>
	                <li style="width:100%;margin:5px 0px;">
	                	<%if(userStatus == 0) { %>
		            		<p style="text-align:center;color:white;padding-bottom:0px;" id="vip_exp">您目前还不是VIP会员,请在下方VIP中心购买</p>
		            	<%} else if(userStatus == 1){ %>
		            		<p style="text-align:center;color:white;padding-bottom:0px;" id="vip_exp">您的VIP会员已经超期，请续费！</p>
		            	<%} else {%>
		            		<p style="text-align:center;color:white;padding-bottom:0px;" id="vip_exp">VIP会员到期时间:<%=vipExpirationTime %></p>
		            	<%} %>
	                </li>
	                
	            </ul>
	        </div>
	        <ul class="mui-table-view mui-table-view-chevron" style="margin-top:10px;">
				<li class="mui-table-view-cell">
					<a href="#study_records" class="mui-navigate-right" style="font-size:15px;"><span class="mui-icon mui-icon-compose"></span>学习记录</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#course_collection" class="mui-navigate-right" style="font-size:15px;"><span class="mui-icon mui-icon-star"></span>课程收藏</a>
				</li>
				<li class="mui-table-view-cell">
					<a href="#buy_live_class" class="mui-navigate-right" style="font-size:15px;"><span class="mui-icon mui-icon-mic"></span>已购买直播讲座</a>
				</li>
			</ul>
			<ul class="mui-table-view mui-table-view-chevron" style="margin-top:10px;">
				<li class="mui-table-view-cell">
					<a href="#vip_center" class="mui-navigate-right" style="font-size:15px;"><span class="mui-icon mui-icon-person"></span>VIP中心</a>
				</li>
			</ul>
		</div>
		<div id="study_records" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">学习记录</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
						<%if(userStudyRecordCourseList == null || userStudyRecordCourseList.size() == 0) { %>
							<div style="margin-top:50%;text-align:center;">您还没有任何学习记录！</div>
						<%} else { %>
							<ul id="user_study_record_data_list" class="mui-table-view">
								<%for(LiveCourseModel lcm : userStudyRecordCourseList) { %>
									<%if(lcm.getCourseType() == 0) { %>
										<li class="mui-table-view-cell mui-media" course_id="<%=lcm.getId() %>" course_path='/playDDCBOpenClass?course_id=<%=lcm.getId() %>'>
											<div class="mui-slider-right mui-disabled">
												<a class="mui-btn mui-btn-red">删除</a>
											</div>
											<div class="mui-slider-handle">
												<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
												<div class="mui-media-body">
													<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
													<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
												</div>
											</div>
										</li>
									<%} else {%>
										<%
											String courseHasEnd = "0";
											long currentTime = System.currentTimeMillis();
											if(lcm.getCourse_date().getTime() + Integer.valueOf(lcm.getCourse_length())*60000 <= currentTime) {
												courseHasEnd = "1";
											}
											if(("1").equals(courseHasEnd)) {
										%>
											<li class="mui-table-view-cell mui-media" course_id="<%=lcm.getId() %>" course_path=''>
												<div class="mui-slider-right mui-disabled">
													<a class="mui-btn mui-btn-red">删除</a>
												</div>
												<div class="mui-slider-handle">
													<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
													<div class="mui-media-body">
														<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
														<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
													</div>
												</div>
											</li>
										<%} else { %>
											<li class="mui-table-view-cell mui-media" course_id="<%=lcm.getId() %>" course_path='/playDDCBLiveClass?course_id=<%=lcm.getId() %>'>
												<div class="mui-slider-right mui-disabled">
													<a class="mui-btn mui-btn-red">删除</a>
												</div>
												<div class="mui-slider-handle">
													<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
													<div class="mui-media-body">
														<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
														<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
													</div>
												</div>
											</li>
										<%} %>
									<%} %>
									
								<%} %>
							</ul>
						<%} %>
					</div>
				</div>
			</div>
		</div>
		<div id="course_collection" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">课程收藏</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
					<div id="tabtip" class="container" style="background-color:white;">
						<ul id="myTab" class="nav nav-tabs row mantoutab" style="padding-left:0px;padding-right:0px;">
							<li class="col-xs-6 text-center active"><a vinfo="open_class" class="center-block" data-toggle="tab">公开课</a></li>
							<li class="col-xs-6 text-center"><a vinfo="live_class" class="center-block" data-toggle="tab">直播课</a></li>
						</ul>
					</div>
					<div class="content">
						<div id="myTabContent" class="tab-content">
							<div class="tab-pane fade in active" id="open_class">
								<div class="container" style="padding-left:0px;padding-right:0px;">
									<%if(collectionOpenCourseList == null || collectionOpenCourseList.size() == 0) { %>
										<div style="margin-top:50%;text-align:center;">您还没有收藏任何公开课！</div>
									<%} else { %>
										<ul id="collection_open_class_data_list" class="mui-table-view">
											<%for(LiveCourseModel lcm : collectionOpenCourseList) { %>
												<li class="mui-table-view-cell mui-media" course_id="<%=lcm.getId() %>" course_path='/playDDCBOpenClass?course_id=<%=lcm.getId() %>'>
													<div class="mui-slider-right mui-disabled">
														<a class="mui-btn mui-btn-red">删除</a>
													</div>
													<div class="mui-slider-handle">
														<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
														<div class="mui-media-body">
															<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
															<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
														</div>
													</div>
												</li>
											<%} %>
										</ul>
									<%} %>
								</div>
							</div>
							<div class="tab-pane fade in" id="live_class">
								<div class="container" style="padding-left:0px;padding-right:0px;">
									<%if(collectionLiveCourseList == null || collectionLiveCourseList.size() == 0) { %>
										<div style="margin-top:50%;text-align:center;">您还没有收藏任何直播课！</div>
									<%} else { %>
										<ul id="collection_live_class_data_list" class="mui-table-view">
											<%for(LiveCourseModel lcm : collectionLiveCourseList) { %>
													<%
														String courseHasEnd = "0";
														long currentTime = System.currentTimeMillis();
														if(lcm.getCourse_date().getTime() + Integer.valueOf(lcm.getCourse_length())*60000 <= currentTime) {
															courseHasEnd = "0";
														}
													%>
													<%if(lcm.getPay_status() == null || lcm.getPay_status() == 0) {%>
														<li class="mui-table-view-cell mui-media" course_has_end="<%=courseHasEnd %>" course_id="<%=lcm.getId() %>" course_price="<%=lcm.getPrice() %>" course_path='/playDDCBLiveClass?course_id=<%=lcm.getId() %>'>
															<div class="mui-slider-right mui-disabled">
																<a class="mui-btn mui-btn-red">删除</a>
															</div>
															<div class="mui-slider-handle">
																<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
																<div class="mui-media-body">
																	<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
																	<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
																</div>
																<div style="margin-top:5px;">
																	<div style="float:left;height:25px;line-height:25px;"><p style="font-size:12px;">课程售价：<%=lcm.getPrice() %>元</p></div>
																	<%if(("0").equals(courseHasEnd)) { %>
																		<div style="float:right;height:25px;line-height:25px;"><p id="course_id_<%=lcm.getId() %>" style="font-size:12px;">点击进入</p></div>
																	<%} else { %>
																		<div style="float:right;height:25px;line-height:25px;"><p id="course_id_<%=lcm.getId() %>" style="font-size:12px;">课程已结束</p></div>
																	<%} %>
																</div>
															</div>
														</li>
													<%} else {%>
														<li class="mui-table-view-cell mui-media" course_id="<%=lcm.getId() %>" course_has_end="<%=courseHasEnd %>" course_path='/playDDCBLiveClass?course_id=<%=lcm.getId() %>'>
															<div class="mui-slider-right mui-disabled">
																<a class="mui-btn mui-btn-red">删除</a>
															</div>
															<div class="mui-slider-handle">
																<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=lcm.getImage()%>">
																<div class="mui-media-body">
																	<h4 style="font-size:15px;"><%=lcm.getName() %></h4>
																	<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=lcm.getTeacher() %></h6>
																</div>
																<div style="margin-top:5px;">
																	<div style="float:left;height:25px;line-height:25px;"><p style="font-size:12px;">课程售价：<%=lcm.getPrice() %>元</p></div>
																	<%if(("0").equals(courseHasEnd)) { %>
																		<div style="float:right;height:25px;line-height:25px;"><p style="font-size:12px;">点击进入</p></div>
																	<%} else { %>
																		<div style="float:right;height:25px;line-height:25px;"><p id="course_id_<%=lcm.getId() %>" style="font-size:12px;">课程已结束</p></div>
																	<%} %>
																</div>
															</div>
														</li>
													<%} %>
											<%} %>
										</ul>
									<%} %>
								</div>
							</div>
						</div>
					</div>
						
					</div>
				</div>
			</div>
		</div>
		<div id="buy_live_class" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">已购买直播讲座</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">					
						<%if(buyCourseList == null || buyCourseList.size() == 0) { %>
							<div style="margin-top:50%;text-align:center;">您还没有购买过直播讲座！</div>
						<%} else { %>
							<ul id="buy_live_class_data_list" class="mui-table-view">
								<%for(CourseModel cm : buyCourseList) { %>
								<li class="mui-table-view-cell mui-media" course_path='/playDDCBLiveClass?course_id=<%=cm.getId() %>'>
									<img class="mui-media-object mui-pull-left" style="height:50px;width:80px;max-width:100px;" src="/files/imgs/<%=cm.getImage()%>">
									<div class="mui-media-body">
										<h4 style="font-size:15px;"><%=cm.getName() %></h4>
										<h6 style="margin-top:10px;color:#2ab888;" class='mui-ellipsis'><span style="font-size:16px;" class="mui-icon mui-icon-contact"></span><%=cm.getTeacher() %></h6>
									</div>
								</li>
								<%} %>
							</ul>
						<%} %>
					</div>
				</div>
			</div>
		</div>
		<div id="vip_center" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" id="back_btn"  class="mui-left mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">VIP中心</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
						<div style="margin-top:10px;height:65px;">
							<div user_type="1" price="45.00" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_blue.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">月会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;45.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;border: 1px;">点击购买</p></div>
							</div>
							<div user_type="2" price="120.00" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_pur.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">季会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;120.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;">点击购买</p></div>
							</div>
							<div user_type="3" price="365.00" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_yellow.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">年会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;365.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;">点击购买</p></div>
							</div>
						</div>
						<div style="margin-top:10px;">
							<div>
								<p style="text-align:center;color:black;">VIP会员专属特权</p>
							</div>
							<div class="mui-card" style="margin:0px 5px;">
								<ul class="mui-table-view">
									<li class="mui-table-view-cell"><p style="margin-top:0px;">免费直播讲座到点直接观看，无需报名</p></li>
									 <li class="mui-table-view-cell"><p style="margin-top:0px;">在线观看大讲堂全部讲座，付费直播除外</p></li>
							         <li class="mui-table-view-cell"><p>优先入导师答疑群，与导师互动</p></li>
							         <li class="mui-table-view-cell"><p>线下活动优先参加</p></li>
							         <li class="mui-table-view-cell"><p>持续更新讲座内容</p></li>
							         <li class="mui-table-view-cell" style="margin-bottom:0px;"><p style="margin-bottom:0px;">酌情接受订制讲座</p></li>
								</ul>
							</div>
						</div>
					</div>
				</div>
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
	            <p style="color:white;" class="weui_toast_content">正在提交请求</p>
	        </div>
        </div>
    </body>
    <script src="/js/weixinjs/jquery.js"></script>
    <script src="/js/weixinjs/mui.min.js"></script>
    <script src="/js/weixinjs/mui.view.js"></script>
    <script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
    <script>
    var userStatus = <%=userStatus%>;
    var userType = "";
    var userTypePrice = "0.01";
    var hasClickVip = false;
    mui.init();
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
    mui.createConfirmDialog = function(info, btnInfo, cancelCallBack, acceptCallBack) {
		var template = "<div style='width:80%;margin:50% 10%;border:1px solid #ddd;background-color: white;border-radius: 5px;'><div style='margin-top:10px;margin-left:20px;font-size:15px;'>提示信息</div><hr style='margin-top:10px;margin-bottom:10px;'/><div style='margin-left:20px;margin-right:20px;height:40px;font-size:15px;'>{{info}}</div><div style='text-align:right;margin-bottom:10px;margin-right:20px;'><a id='createConfirmDialog_cancel' href='javascript:void(0);' style='margin-right:20px;text-decoration:none;font-size:15px;'>取消</a><a id='createConfirmDialog_accept' href='javascript:void(0);' style='text-decoration:none;font-size:15px;'>{{btnInfo}}</a></div></div>";
		var element = document.createElement('div');
		element.classList.add('dialog');
		element.innerHTML = template.replace('{{info}}', info);
		element.innerHTML = element.innerHTML.replace('{{btnInfo}}', btnInfo);
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
    var viewApi = mui('#user_center').view({
    	defaultPage: '#mainpage'
    });
    mui('.mui-scroll-wrapper').scroll();
    var view = viewApi.view;
    var oldBack = mui.back;
    mui.back = function() {
    	if (viewApi.canBack()) { //如果view可以后退，则执行view的后退
    		viewApi.back();
    	} else { //执行webview后退
    		oldBack();
    	}
    };
    function checkJsonIsEmpty(json) {
		var isEmpty = true;
		if (json == null) return true;
		for (var jsonKey in json) {
			isEmpty = false;
			break;
		}
		return isEmpty;
	}
    wx.ready(function() {
    	mui('#collection_live_class_data_list li').each(function(){
    		this.addEventListener('tap',function(){
    			var ele = this;
    			var courseHasEnd = this.getAttribute('course_has_end');
    			var coursePath = this.getAttribute('course_path');
    			if(courseHasEnd == "1") {
    				alert("当前直播讲座已经结束， 感谢您的关注！");
    			} else if(coursePath != "") {
    				window.location.href=this.getAttribute('course_path');
    			} else {
    				 var confirmDialog = mui.createConfirmDialog('您还没有购买当前直播讲座，无法观看！',"点击购买",
    					function() {
    						confirmDialog.close();
    					},
    					function() {
    						confirmDialog.close();
    						document.getElementById("loadingToast").style.display = "";
    						var courseId = ele.getAttribute("course_id");
    						var coursePrice = ele.getAttribute("course_price");
    		    			$.ajax({
    		            		url: '/userLiveClassWeixinPay',
    		            		type: "POST",
    		            		data: {fee:coursePrice,course_id:courseId},
    		            		success: function(data) {
    		            			document.getElementById("loadingToast").style.display = "none";
    		            			var jsonData = JSON.parse("{"+data+"}");
    		            			if(jsonData.ddcb_error_msg != null) {
    		            				alert(jsonData.ddcb_error_msg);
    		            			} else {
    		            				wx.chooseWXPay({
    		            		            timestamp: jsonData.timeStamp,
    		            		            nonceStr: jsonData.nonceStr,
    		            		            package: jsonData.package,
    		            		            signType: jsonData.signType,
    		            		            paySign: jsonData.paySign,
    		            		            success: function (res) {
    		            		            	if(res.errMsg != null && res.errMsg == "chooseWXPay:ok") {
    		            		            		alert("支付成功!");
    		            		            		ele.setAttribute("course_path", "/playDDCBLiveClass?course_id="+courseId);
    		            		            		$('#course_id_'+courseId).html("点击进入");
    		            		            	} else {
    		            		            		alert("支付失败！");
    		            		            	}																            
    		            		            },
    		            		            fail:function(res) {
    		            		            	alert(JSON.stringify(res));
    		            		            }
    		            		        });
    		            			}
    		            		},
    		            		error: function(status, error) {
    		            			document.getElementById("loadingToast").style.display = "none";
    		            			alert("支付失败！");
    		            		}
    		            	});
    					}
    				);
    				confirmDialog.show();
    			}
    	    });  
    	});
    	$('.buy_vip').each(function(){
    		$(this).click(function(event) {
    			if(hasClickVip) return;
    			hasClickVip = true;
    			userType = $(this).attr('user_type')
    			if(userStatus == 2) {
    				var confirmDialog = mui.createConfirmDialog("您当前VIP会员还没有到期，您确定要续费嘛？", "确定",
    						function() {
    							confirmDialog.close();
    							hasClickVip = false;
    						},
    						function() {
    							confirmDialog.close();
    							document.getElementById("loadingToast").style.display = "";
    			    			$.ajax({
    			            		url: '/userVIPWeixinPay',
    			            		type: "POST",
    			            		data: {user_type:userType, fee:userTypePrice},
    			            		success: function(data) {
    			            			document.getElementById("loadingToast").style.display = "none";
    			            			var jsonData = JSON.parse("{"+data+"}");
    			            			if(jsonData.ddcb_error_msg != null) {
    			            				alert(jsonData.ddcb_error_msg);
    			            				hasClickVip = false;
    			            			} else {
    			            				wx.chooseWXPay({
    			            		            timestamp: jsonData.timeStamp,
    			            		            nonceStr: jsonData.nonceStr,
    			            		            package: jsonData.package,
    			            		            signType: jsonData.signType,
    			            		            paySign: jsonData.paySign,
    			            		            success: function (res) {
    			            		            	if(res.errMsg != null && res.errMsg == "chooseWXPay:ok") {
    			            		            		alert("支付成功!");
    			            		            		window.location.href="/weixin/getDDCBUserCenter";
    			            		            	} else {
    			            		            		alert("支付失败！");
    			            		            	}
    			            		            	hasClickVip = false;
    			            		            },
    			            		            fail:function(res) {
    			            		            	alert(JSON.stringify(res));
    			            		            	hasClickVip = false;
    			            		            }
    			            		        });
    			            			}
    			            		},
    			            		error: function(status, error) {
    			            			document.getElementById("loadingToast").style.display = "none";
    			            			alert("支付失败！");
    			            			hasClickVip = false;
    			            		}
    			            	});
    						}
    					);
    					confirmDialog.show();
    			} else {
    				document.getElementById("loadingToast").style.display = "";
        			$.ajax({
                		url: '/userVIPWeixinPay',
                		type: "POST",
                		data: {user_type:$(this).attr('user_type'), fee:"0.01"},
                		success: function(data) {
                			document.getElementById("loadingToast").style.display = "none";
                			var jsonData = JSON.parse("{"+data+"}");
                			if(jsonData.ddcb_error_msg != null) {
                				alert(jsonData.ddcb_error_msg);
                				hasClickVip = false;
                			} else {
                				wx.chooseWXPay({
                		            timestamp: jsonData.timeStamp,
                		            nonceStr: jsonData.nonceStr,
                		            package: jsonData.package,
                		            signType: jsonData.signType,
                		            paySign: jsonData.paySign,
                		            success: function (res) {
                		            	if(res.errMsg != null && res.errMsg == "chooseWXPay:ok") {
                		            		alert("支付成功!");
                		            		window.location.href="/weixin/getDDCBUserCenter";
                		            	} else {
                		            		alert("支付失败！");
                		            	}
                		            	hasClickVip = false;
                		            },
                		            fail:function(res) {
                		            	alert(JSON.stringify(res));
                		            	hasClickVip = false;
                		            }
                		        });
                			}
                		},
                		error: function(status, error) {
                			document.getElementById("loadingToast").style.display = "none";
                			alert("支付失败！");
                			hasClickVip = false;
                		}
                	});
    			}
    		});
    	});
    });
    mui('#back_btn')[0].addEventListener('tap',function(){
		window.location.href="/weixin/getDDCBUserCenter"; 
	});
    mui('#buy_live_class_data_list li').each(function(){
		this.addEventListener('tap',function(){
	        window.location.href=this.getAttribute('course_path'); 
	    });  
	});
    mui('#collection_open_class_data_list li').each(function(){
		this.addEventListener('tap',function(){
			window.location.href=this.getAttribute('course_path');
	    });
    });
    mui('#collection_open_class_data_list').on('slideleft', '.mui-table-view-cell', function(event) {
		var elem = this;
		var confirmDialog = mui.createConfirmDialog("您确定要删除吗？", "确定",
			function() {
				confirmDialog.close();
				setTimeout(function() {
					mui.swipeoutClose(elem);
				}, 0);
			},
			function() {
				confirmDialog.close();
				elem.parentNode.removeChild(elem);
				var courseId = elem.getAttribute("course_id");
				$.ajax({
					url: "/course/delUserCollection",
					type: "POST",
					data: {courseId:courseId},
					success: function(data) {
					},
					error: function(status, error) {
					}
				});
			}
		);
		confirmDialog.show();
	});
    mui('#collection_live_class_data_list').on('slideleft', '.mui-table-view-cell', function(event) {
		var elem = this;
		var confirmDialog = mui.createConfirmDialog("您确定要删除吗？", "确定",
			function() {
				confirmDialog.close();
				setTimeout(function() {
					mui.swipeoutClose(elem);
				}, 0);
			},
			function() {
				confirmDialog.close();
				elem.parentNode.removeChild(elem);
				var courseId = elem.getAttribute("course_id");
				$.ajax({
					url: "/course/delUserCollection",
					type: "POST",
					data: {courseId:courseId},
					success: function(data) {
					},
					error: function(status, error) {
					}
				});
			}
		);
		confirmDialog.show();
	});
    mui('#user_study_record_data_list li').each(function(){
		this.addEventListener('tap',function(){
			if(this.getAttribute('course_path') == "") {
				alert("该直播讲座已经结束，无法进入，感谢您的关注！");
			} else {
				window.location.href=this.getAttribute('course_path');
			}
	    });
    });
    mui('#user_study_record_data_list').on('slideleft', '.mui-table-view-cell', function(event) {
		var elem = this;
		var confirmDialog = mui.createConfirmDialog("您确定要删除吗？", "确定",
			function() {
				confirmDialog.close();
				setTimeout(function() {
					mui.swipeoutClose(elem);
				}, 0);
			},
			function() {
				confirmDialog.close();
				elem.parentNode.removeChild(elem);
				var courseId = elem.getAttribute("course_id");
				$.ajax({
					url: "/course/delStudyRecord",
					type: "POST",
					data: {courseId:courseId},
					success: function(data) {
					},
					error: function(status, error) {
					}
				});
			}
		);
		confirmDialog.show();
	});
    </script>
</html>