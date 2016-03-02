<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@ page import="org.springframework.web.context.WebApplicationContext"%>
<%@ page import="com.ddcb.utils.WeixinTools"%>
<%@ page import="java.util.*"%>
<%
WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(this.getServletContext());
Map<String, String> result = new HashMap<>();
result = WeixinTools.getSign("http://www.diandou.me/weixin/getDDCBBuyVip");
String course_id = (String)session.getAttribute("course_id");
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
        <link href="/css/weixincss/loading.css" rel="stylesheet" />
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
							<div user_type="1" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_blue.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">月会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;45.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;border: 1px;">点击购买</p></div>
							</div>
							<div user_type="2" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_pur.png');float:left;">
								<div><p style="color:white;margin-left:2px;margin-bottom:0px;font-weight: bold;">季会员</p></div>
								<div><p style="text-align:center;color:white;margin-bottom:0px;">&yen;120.00</p></div>
								<div><p style="margin-right:5px;color:white;float:right;font-size:10px;">点击购买</p></div>
							</div>
							<div user_type="3" class="buy_vip" style="border-radius:3px;margin:5px 5px;width:30%;height:60px;background-image: url('/img/weixinimg/vip_yellow.png');float:left;">
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
    mui.init();
    var viewApi = mui('#user_center').view({
    	defaultPage: '#vip_center'
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
    wx.ready(function() {
    	$('.buy_vip').each(function(){
    		$(this).click(function(event) {
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
            		            		window.location.href="/playDDCBOpenClass?course_id=<%=course_id %>";
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
    		});
    	});
    });
    </script>
</html>