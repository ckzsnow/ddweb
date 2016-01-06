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

function fillDataIntoHtml(data) {
	imgUrl = "http://www.diandou.me/files/imgs/" + data.teacher_image;
	descContent = data.details;
	shareTitle = data.name;
	document.getElementById('teacher_image').setAttribute('src', "/files/imgs/" + data.teacher_image);
	document.getElementById('teacher_name').innerHTML = data.teacher_name;
	document.getElementById('teacher_info').innerHTML = data.teacher_info;
	document.getElementById('teacher_position').innerHTML = data.teacher_position;
	document.getElementById('crowd').innerHTML = data.crowd;
	document.getElementById('details').innerHTML = data.details;
	var playerNode = document.getElementById('player_video');
	var videoNode = document.createElement("video");
	videoNode.setAttribute("id", "video");
	videoNode.setAttribute("src", data.videosrc);
	videoNode.setAttribute("poster", "/files/imgs/" + data.video_image);
	videoNode.setAttribute("controls", "");
	videoNode.setAttribute("width", "100%");
	videoNode.setAttribute("height", "100%");
	playerNode.appendChild(videoNode);
}

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