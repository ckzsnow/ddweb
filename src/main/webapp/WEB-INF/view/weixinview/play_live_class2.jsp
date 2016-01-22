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
	</head>
	<style>
	video::-webkit-media-controls {
	  display:none !important;
	}
	</style>
	<body style="padding-bottom: 10px; background-color: #f1f1f1;">
		<div style="position: relative;">
			<div id="video_div" class="video" style="display:none;background:#1cbcd6;">
				<video id="video" src="" preload="none" width="640" height="264" poster="">
				</video>
			</div>
			<div id="tempCountDiv" style="width:100%;height:150px;background:#1cbcd6;">
				<div id="countdown" style="display:none;width:100%;"></div>
				<div style="text-align:center;line-height:150px;height:150px;"><p id="note"></p></div>
			</div>
			<div id="endNoteDiv" style="display:none;text-align:center;width:100%;height:150px;line-height:150px;background:#1cbcd6;">
				<p id="end_note"></p>
			</div>
		</div>

		<div id="tabtip" class="container">
			<ul id="myTab" class="nav nav-tabs row mantoutab">
				<li class="active col-xs-4 text-center"><a vinfo="summary" class="center-block" data-toggle="tab">简介</a></li>
			</ul>
		</div>

		<div class="content">
			<div id="myTabContent" class="tab-content">
				<div class="tab-pane fade in active" id="summary">
					<div class="container">
						<div class="row csdetials">
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>导师简介</div>
							<div class="col-xs-12  mentername">
								<div class="flowergif" style="display: none;"><img src="http://static.live.mtedu.com/liveImages/images/flower.gif" /></div>
								<div class="row">
									<div class="col-xs-12">
										<div class="avatar">
											<img id="teacher_image" src="" />
										</div>
									</div>
									<div class="col-xs-12">
										<div class="teacher-name" id="teacher_name"></div>
										<div class="teacher-title" id="teacher_position"></div>
										<div class="infolabel">
											<div>Ta的经验</div>
										</div>
										<p id="teacher_info"></p>
									</div>
								</div>
							</div>
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>适合人群</div>
							<div class="col-xs-12  courseintro">
								<p id="crowd"></p>
							</div>
							<div class="col-xs-12  mantoutitle"><span class="color-block"></span>课程简介</div>
							<div class="col-xs-12  courseintro">
								<p id="details"></p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
	<script src="/js/weixinjs/jquery.js"></script>
	<script src="/js/weixinjs/jquery.countdown.js"></script>
	<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
	<script>
	var imgUrl = "http://www.diandou.me/img/weixinimg/share_img.jpg";
	var lineLink = window.location.href;
	var descContent = "点豆成兵---为进取心而生，专注职场“传、帮、带”";
	var shareTitle = "点豆成兵";
	var hasWriteShareInfo = false;
	var globalData;
	var hasSetted = false;
	//document.addEventListener("WeixinJSBridgeReady", function () {
		function waitLive(endTime) {
			var note = $('#note'),
			ts = new Date(endTime);	
			$('#countdown').countdown({
				timestamp	: ts,
				callback	: function(days, hours, minutes, seconds){	
					var message = "剩余时间：";
					message += days + " 天" + ", ";
					message += hours + " 小时" + ", ";
					message += minutes + " 分钟" + ", ";
					message += seconds + " 秒" + " <br />";
					//message += "欢迎您收看点豆成兵直播课！";
					note.html(message);
					if(days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
						playLive(0);
					}
				}
			});	
		}
		function playLive(startPlayTime) {
			var seconds = globalData.course_length * 60;
			if(startPlayTime >= seconds) {
				document.getElementById('endNoteDiv').style.display = "";
				document.getElementById('tempCountDiv').style.display = "none";
				document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
			} else {
				document.getElementById('video_div').style.display = "";
				document.getElementById('endNoteDiv').style.display = "none";
				document.getElementById('tempCountDiv').style.display = "none";
				/* var videoDivNode = document.getElementById('video_div');
				var videoNode = document.createElement('video');
				videoNode.setAttribute('src', globalData.videosrc);
				videoNode.setAttribute("poster", "/files/imgs/" + globalData.video_image);
				videoNode.removeAttribute('controls');
				videoDivNode.appendChild(videoNode);
				videoNode.load();
				videoNode.play(); */
				media = document.getElementById('video');
				var timer = setInterval(function(){
					if(media.readyState>0) {
						clearInterval(timer);
						media.play();
					} else {
						alert(media.readyState);
					}
				},1500);
				media.addEventListener("canplay", function(){
					media.play();
				});
				media.addEventListener("loadeddata", function(){
					media.play();
				});
				media.addEventListener("durationchange", function(){
					media.play();
				});
				media.addEventListener("loadedmetadata", function(){
					media.play();
				});
				media.addEventListener("timeupdate", function(){
					if(!hasSetted && media.duration>1) {
						hasSetted = true;
						media.currentTime = parseInt(startPlayTime);
						media.play();
					} else if(!hasSetted) {
						media.pause();
					}
				});
				media.addEventListener('ended', function(){
					document.getElementById('video_div').style.display = "none";
					document.getElementById('endNoteDiv').style.display = "";
					document.getElementById('tempCountDiv').style.display = "none";
					document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
				}, false);
			}
		}
		function fillDataIntoHtml(data) {
			globalData = data;
			document.getElementById('teacher_image').setAttribute('src', "/files/imgs/" + data.teacher_image);
			document.getElementById('teacher_name').innerHTML = data.teacher_name;
			document.getElementById('teacher_info').innerHTML = data.teacher_info;
			document.getElementById('teacher_position').innerHTML = data.teacher_position;
			document.getElementById('crowd').innerHTML = data.crowd;
			document.getElementById('details').innerHTML = data.details;
			document.getElementById('video').setAttribute('src', data.videosrc);
			document.getElementById('video').setAttribute("poster", "/files/imgs/" + data.video_image);
			document.getElementById('video').load();
			document.getElementById('video').play();
			var date= new Date(Date.parse(data.course_date));
			var currentDate = new Date();
			if(date.getTime() > currentDate.getTime()) {
				waitLive(date);
			} else if(date.getTime() + parseInt(data.course_length) * 1000 * 1000 > currentDate.getTime()){
				playLive((currentDate.getTime() - date.getTime()) / 1000);
			} else {
				document.getElementById('endNoteDiv').style.display = "";
				document.getElementById('tempCountDiv').style.display = "none";
				document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
			}		
		}
		function checkJsonIsEmpty(json) {
			var isEmpty = true;
			if (json == null) return true;
			for (var jsonKey in json) {
				isEmpty = false;
				break;
			}
			return isEmpty;
		}
		$.ajax({
			url: '/course/getCourseDetailByCourseId',
			type: "POST",
			data: {},
			success: function(data) {
				if (!checkJsonIsEmpty(data)) {
					fillDataIntoHtml(data);
				}
			},
			error: function(status, error) {
			}
		});
	//});
	</script>
</html>