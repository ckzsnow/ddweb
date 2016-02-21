<%@ page contentType="text/html; charset=UTF-8"%>
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
	                    <a href="/perinfo/160454" uid="160454">
	                        <img id="person_img" src="" alt="个人头像"/>
	                    </a>
	                </div>
	            </div>
	            <p class="nickname text-center" id="nickname"><p>
	            <h2 class="experience text-center"></h2>
	            <ul>
	                <li style="width:100%;margin:5px 0px;">
	                    <p style="text-align:center;color:white;padding-bottom:0px;">VIP会员到期时间</p>
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
					<a href="#buy_live_class" class="mui-navigate-right" style="font-size:15px;"><span class="mui-icon mui-icon-mic"></span>已购买直播课程</a>
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
						
					</div>
				</div>
			</div>
		</div>
		<div id="buy_live_class" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">已购买直播课程</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
						
					</div>
				</div>
			</div>
		</div>
		<div id="vip_center" class="mui-page">
			<div class="mui-navbar-inner mui-bar mui-bar-nav" style="background-color: #66d6a6;">
				<button type="button" class="mui-left mui-action-back mui-btn  mui-btn-link mui-btn-nav mui-pull-left">
					<span class="mui-icon mui-icon-left-nav" style="color:white;"></span>
				</button>
				<h1 class="mui-center mui-title" style="color:white;">VIP中心</h1>
			</div>
			<div class="mui-page-content">
				<div class="mui-scroll-wrapper">
					<div class="mui-scroll">
						<div style="margin-top:10px;height:65px;">
							<div style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_blue.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">月会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;45.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;border: 1px;">点击购买</p></div>
							</div>
							<div style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_pur.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">季会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;120.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;">点击购买</p></div>
							</div>
							<div style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_yellow.png');float:left;">
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
		
        <div class="courselist" style="display:none;">
			<ul id="courseList">
			</ul>
		</div>
    </body>
    <script src="/js/weixinjs/jquery.js"></script>
    <script src="/js/weixinjs/mui.min.js"></script>
    <script src="/js/weixinjs/mui.view.js"></script>
    <script>
    mui.init();
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
    var openid = "";
    $.ajax({
		url: '/getWeixinLoginUserInfo',
		type: "POST",
		data: {},
		success: function(data) {
			document.getElementById('person_img').setAttribute('src', data.headimgurl);
			document.getElementById('nickname').innerHTML = "<span style='color:white;font-size:15px;font-weight:300;'>"+data.nickName+"</span><span style='font-size:10px;background-color: #f0ad4e;color:white;padding:1px 6px;margin-left:5px;line-height:20px;height:20px;border-radius:5px;'>VIP会员</span><span style='font-size:10px;background-color:#888888;color:white;padding:1px 6px;margin-left:5px;line-height:20px;height:20px;border-radius:5px;'>非VIP会员</span>";
			openId = data.openid;
		},
		error: function(status, error) {
		}
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
    function createDataList(data) {
		var detailNode = document.getElementById('courseList');
		for (var i in data) {
			var liNode = document.createElement('li');
			liNode.setAttribute('class', 'course-list-item clearfix');
			liNode.setAttribute('course_id', data[i].id);
			liNode.innerHTML = "<div class='item-avatar'><img src='/files/imgs/"+data[i].image+"'/></div><div class='item-content'><h3 class='item-title'>"+data[i].name+"</h3><div class='item-time'><span class='menter'><span class='glyphicon glyphicon-time'></span>"+data[i].course_date_readable+"</span></div><div class='item-teacher'><span class='menter'><span class='glyphicon glyphicon-user'></span>"+data[i].teacher+"</span></div></div>";
			detailNode.appendChild(liNode);
		}
		$('.course-list-item.clearfix').each(function(){
			$(this).click(function(event) {
				var elem = this;
				var course_id = elem.getAttribute('course_id');
				window.location = "/course/playPayedLiveCourse?course_id=" + course_id;
			});
		});
	}
    /* $.ajax({
		url: '/course/getUserPayedCourse',
		type: "POST",
		data: {openid:openid},
		success: function(data) {
			if (!checkJsonIsEmpty(data)) {
				document.getElementById('load_tip').style.display = 'none';
				createDataList(data);
			} else {
				document.getElementById('load_tip').innerHTML = '您还有购买过直播课，暂时没有数据！';
			}
		},
		error: function(status, error) {
		}
	}); */
    </script>
</html>