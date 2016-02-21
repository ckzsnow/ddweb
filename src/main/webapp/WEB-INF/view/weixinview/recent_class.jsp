<!DOCTYPE html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1,user-scalable=no">
<meta name="keywords" content="" />
<meta name="description" content="" />
<title>点豆大讲堂</title>
<link rel="stylesheet" href="/css/weixincss/bootstrap.min.css">
</head>

<body style="padding-bottom: 10px;">
	<!--标题-->
	<h1 class="text-center mantoutitle">点豆直播课</h1>
	<div id="load_tip" style="text-align: center; margin-top: 50%;">正在加载直播课程列表...</div>
	<div class="courselist">
		<ul id="courseList">
		</ul>
	</div>
	<!-- <nav class="navbar  navbar-fixed-bottom" role="navigation">
		<div>
				<ul id="mynav" class="nav">
				<li style="float:left;">
					<a href="/view/weixinview/recent_class.html">
						<div class="center-block icon">
							<span style="font-size:20px;color:#81d742;" class='glyphicon glyphicon-stats'></span>
						</div>
						<h3 class="text-center">点豆直播课</h3>
					</a>
				</li>
				<li style="float:right;">
					<a href="/view/weixinview/user_center.html">
						<div class="center-block icon">
							<span style="font-size:20px;color:#81d742;" class='glyphicon glyphicon-user'></span>
						</div>
						<h3 class="text-center">个人中心</h3>
				</a></li>
			</ul>
			</div>
	</nav> -->
</body>
<script src="/js/weixinjs/jquery.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script>
		var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
		var lineLink = window.location.href;
		var descContent = "点豆成兵---为进取心而生，专注职场“传、帮、带”";
		var shareTitle = "点豆成兵";
		var hasWriteShareInfo = false;

		function checkJsonIsEmpty(json) {
			var isEmpty = true;
			if (json == null) return true;
			for (var jsonKey in json) {
				isEmpty = false;
				break;
			}
			return isEmpty;
		}
		function getJsConfigInfoSuccess(data, status) {
			wx.config({
				appId: 'wxbd6aef840715f99d',
				timestamp: data.timestamp,
				nonceStr: data.nonceStr,
				signature: data.signature,
				jsApiList: [
					'onMenuShareQQ',
					'onMenuShareTimeline',
					'onMenuShareAppMessage'
				]
			});
		}
		$.ajax({
			type: "POST",
			url: "/getJsConfigInfo?url=" + encodeURIComponent(location.href.split('#')[0]),
			async: false,
			success: getJsConfigInfoSuccess
		});
		wx.ready(function() {
			var appid = 'wx309df15b6ddc5371';
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
			function createDataList(data) {
				var detailNode = document.getElementById('courseList');
				for (var i in data) {
					var liNode = document.createElement('li');
					liNode.setAttribute('class', 'course-list-item clearfix');
					liNode.setAttribute('course_id', data[i].id);
					liNode.setAttribute('pay_status', data[i].pay_status);
					liNode.setAttribute('course_name', data[i].name);
					if(data[i].pay_status == "1") {
						liNode.innerHTML = "<div class='item-avatar'><img src='/files/imgs/"+data[i].image+"'/></div><div class='item-content'><h3 class='item-title'>"+data[i].name+"</h3><div class='item-time'><span class='menter'><span class='glyphicon glyphicon-time'></span>"+data[i].course_date_readable+"</span></div><div class='item-teacher'><span class='menter'><span class='glyphicon glyphicon-user'></span>"+data[i].teacher+"</span></div></div><div style='float:left;margin-top:10px;'><button style='width:115px;' course_id='"+data[i].id+"' course_name='"+data[i].name+"' type='button' disabled class='btn btn-success btn-xs'>已经购买</button></div></div>";
					} else {
						liNode.innerHTML = "<div class='item-avatar'><img src='/files/imgs/"+data[i].image+"'/></div><div class='item-content'><h3 class='item-title'>"+data[i].name+"</h3><div class='item-time'><span class='menter'><span class='glyphicon glyphicon-time'></span>"+data[i].course_date_readable+"</span></div><div class='item-teacher'><span class='menter'><span class='glyphicon glyphicon-user'></span>"+data[i].teacher+"</span></div></div><div style='float:left;margin-top:10px;'><button style='width:115px;' course_id='"+data[i].id+"' course_name='"+data[i].name+"' type='button' class='btn btn-success btn-xs'>点击购买</button></div>";
					}
					detailNode.appendChild(liNode);
					if(!hasWriteShareInfo) {
						hasWriteShareInfo = true;
						imgUrl = "http://www.diandou.me/files/imgs/" + data[i].image;
						descContent = data[i].teacher;
						shareTitle = data[i].name;
					}
				}
				$('.course-list-item.clearfix').each(function(){
					$(this).click(function(event) {
						var elem = this;
						var pay_status = elem.getAttribute('pay_status');
						var course_id = elem.getAttribute('course_id');
						if(pay_status == "1") {
							window.location = "/course/playPayedLiveCourse?course_id=" + course_id;
						}
					});
				});
				$('.course-list-item.clearfix button').each(function(){
					$(this).click(function(event) {
						var elem = this;
						var course_name = elem.getAttribute('course_name');
						var course_id = elem.getAttribute('course_id');
						$.ajax({
							url: '/userChooseWeixinPay',
							type: "POST",
							data: {coursename:course_name,courseid:course_id,fee:"0.01"},
							success: function(data) {
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
							            		alert("支付成功，若课程未显示已购买，可稍后重新进入公开课页面查看。");
							            		window.location = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxbd6aef840715f99d&redirect_uri=http%3A%2F%2Fwww.diandou.me%2FweixinLogin%3Fview%3Drecent_class.html&response_type=code&scope=snsapi_userinfo&state=123#wechat_redirect";
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
								alert("支付失败！");
							}
						});
					});
				});
			}
			$.ajax({
				url: '/course/getAllRecentCourse',
				type: "POST",
				data: {},
				success: function(data) {
					if (!checkJsonIsEmpty(data.data)) {
						createDataList(data.data);
						document.getElementById('load_tip').style.display = 'none';
					} else {
						document.getElementById('load_tip').innerHTML = '暂时没有公开课，敬请关注！';
					}
				},
				error: function(status, error) {
					document.getElementById('load_tip').innerHTML = '暂时没有公开课，敬请关注！';
				}
			});
		});
	</script>

</html>