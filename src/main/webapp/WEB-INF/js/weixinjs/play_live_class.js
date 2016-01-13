function checkJsonIsEmpty(json) {
	var isEmpty = true;
	if (json == null) return true;
	for (var jsonKey in json) {
		isEmpty = false;
		break;
	}
	return isEmpty;
}

mui.createTipDialog = function(info, callBack) {
	var template = "<div style='width:80%;margin:50% 10%;border:1px solid #ddd;background-color: white;border-radius: 5px;'><div style='margin-top:20px;margin-left:20px;'>提示信息</div><hr/><div style='margin-top:20px;margin-left:20px;margin-bottom:20px;margin-right:20px;height:60px;'>{{info}}</div></div>";
	var element = document.createElement('div');
	element.classList.add('dialog');
	element.innerHTML = template.replace('{{info}}', info);
	element.addEventListener('touchmove', mui.preventDefault);
	element.addEventListener('tap', function() {
		mask.close();
	});
	var mask = [element];
	mask._show = false;
	mask.show = function() {
		mask._show = true;
		element.setAttribute('style', 'opacity:1');
		document.body.appendChild(element);
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
		if (callBack) {
			callBack();
		}
		mask._remove();
	};
	return mask;
};
mui.init({
	swipeBack: true //启用右滑关闭功能
});
mui('.mui-scroll-wrapper').scroll();
mui("#scrollToTop").on("tap", ".scroll_to_top", function() {
	mui('#mui_scroll_wrapper').scroll().scrollTo(0, 0);
});
mui("#scrollToBottom").on("tap", ".scroll_to_bottom", function() {
	mui('#mui_scroll_wrapper').scroll().scrollToBottom();
});
var globalData = null;
function countdownTime() {
	/*var node = document.getElementById('countdown_time_div');
	var endtime = new Date(node.getAttribute('endtime')).getTime();//取结束日期(毫秒值)
    var nowtime = new Date().getTime();        //今天的日期(毫秒值)
    var youtime = endtime-nowtime;//还有多久(毫秒值)
    var seconds = youtime/1000;
    var minutes = Math.floor(seconds/60);
    var hours = Math.floor(minutes/60);
    var days = Math.floor(hours/24);
    var CDay= days ;
    var CHour= hours % 24;
    var CMinute= minutes % 60;
    var CSecond= Math.floor(seconds%60);
    if(endtime <= nowtime) {
    	playLive(0);
    } else {
    	node.innerHTML = "<i>剩余：</i><span>"+CHour+"</span>时<span>"+CMinute+"</span>分<span>"+CSecond+"</span>秒";
    	setTimeout("countdownTime()",1000);
    }*/
}
function waitLive(endTime) {
	var note = $('#note'),
	ts = new Date(endTime);	
	$('#countdown').countdown({
		timestamp	: ts,
		callback	: function(days, hours, minutes, seconds){	
			var message = "";
			message += days + " 天" + ", ";
			message += hours + " 小时" + ", ";
			message += minutes + " 分钟" + ", ";
			message += seconds + " 秒" + " <br />";
			message += "欢迎您收看点豆成兵直播课！";
			note.html(message);
			if(days == 0 && hours == 0 && minutes == 0 && seconds == 0) {
				playLive(0);
			}
		}
	});	
}
function playLive(startPlayTime) {
	document.getElementById('tempCountDiv').style.display = "none";
	var playerNode = document.getElementById('player_video');
	var videoNode = document.createElement("video");
	videoNode.setAttribute("id", "video");
	videoNode.setAttribute("src", globalData.videosrc);
	videoNode.setAttribute("poster", "/files/imgs/" + globalData.video_image);
	videoNode.setAttribute("width", "100%");
	videoNode.setAttribute("height", "100%");
	playerNode.appendChild(videoNode);
	alert(111);
	var i = setInterval(function() {
		if(videoNode.readyState > 0) {
			alert("videoNode.readyState > 0");
			clearInterval(i);
			var seconds = globalData.course_length;
			alert("startPlayTime : " + startPlayTime);
			alert("videoNode.duration : " + globalData.course_length);
			if(startPlayTime >= seconds) {
				videoNode.style.display = "none";
				document.getElementById('endNoteDiv').style.display = "";
				document.getElementById('tempCountDiv').style.display = "none";
				document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
			} else {
				alert("before play");
				videoNode.currentTime = parseInt(startPlayTime);
				videoNode.play();
				document.addEventListener("WeixinJSBridgeReady", function () {
					alert("WeixinJSBridgeReady");
					document.getElementById('video').play();
					var hasSetted = false;
					document.getElementById('video').ontimeupdate = function () {
						if(document.getElementById('video').duration() != 0 && !hasSetted) {
			        		hasSetted = true;
			        		document.getElementById('video').currentTime(100);
			        	}
					};
			    }, false);
				alert("after play");
				document.getElementById('video').addEventListener('ended', function(){
					videoNode.style.display = "none";
					document.getElementById('endNoteDiv').style.display = "";
					document.getElementById('tempCountDiv').style.display = "none";
					document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
				}, false);
			}
		}
	}, 500);
}
function fillDataIntoHtml(data) {
	globalData = data;
	document.getElementById('teacher_image').setAttribute('src', "/files/imgs/" + data.teacher_image);
	document.getElementById('teacher_name').innerHTML = data.teacher_name;
	document.getElementById('teacher_info').innerHTML = data.teacher_info;
	document.getElementById('teacher_position').innerHTML = data.teacher_position;
	document.getElementById('crowd').innerHTML = data.crowd;
	document.getElementById('details').innerHTML = data.details;
	if(data.course_date) {
		var date= new Date(Date.parse(data.course_date));
		var currentDate = new Date();
		if(date.getTime() > currentDate.getTime()) {
			waitLive(date);
		} else if(date.getTime() + parseInt(data.course_length) * 1000 > currentDate.getTime()){
			playLive((currentDate.getTime() - date.getTime()) / 1000);
		} else {
			document.getElementById('endNoteDiv').style.display = "";
			document.getElementById('tempCountDiv').style.display = "none";
			document.getElementById('end_note').innerHTML = "当前课程已结束，非常感谢您的关注！";
		}
	}
}

function getJsConfigInfoSuccess(data,status) {
	wx.config({
		debug:true,
		appId: 'wx55d4da6e29cc6c83',
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
/*mui.ajax({ 
    type : "POST", 
    url  : "/getJsConfigInfo?url=" + encodeURIComponent(location.href.split('#')[0]),
    async: false,
    success : getJsConfigInfoSuccess 
});
wx.ready(function(){
	mui.ajax({
		url: '/course/getCourseDetailByCourseId',
		type: "POST",
		data: {},
		success: function(data) {
			if (!checkJsonIsEmpty(data)) {
				fillDataIntoHtml(data);
				document.getElementById('data_loading').style.display = 'none';
				document.getElementById('mui_scroll_wrapper').style.display = '';
			} else {
				document.getElementById('data_loading').style.display = 'none';
				document.getElementById('tips_info_detail').innerHTML = '暂时没有获取到您选择的课程数据,请稍后重试!';
				document.getElementById('tips_info').style.display = '';
			}
		},
		error: function(status, error) {
			document.getElementById('data_loading').style.display = 'none';
			document.getElementById('tips_info_detail').innerHTML = '服务器暂时无法响应请求,请稍后重试!';
			document.getElementById('tips_info').style.display = '';
		}
	});
});*/