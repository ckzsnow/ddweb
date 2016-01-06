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
mui("#slider").slider({
	interval: 1000
});
mui("#scrollToTop").on("tap", ".scroll_to_top", function() {
	mui('#mui_scroll_wrapper').scroll().scrollTo(0, 0);
});
mui("#scrollToBottom").on("tap", ".scroll_to_bottom", function() {
	mui('#mui_scroll_wrapper').scroll().scrollToBottom();
});
var hasWriteShareInfo = false;
function createDataList(data) {
	var detailNode = document.getElementById('courseListDetail');
	for (var i in data) {
		var liNode = document.createElement('li');
		liNode.setAttribute('class', 'mui-table-view-cell mui-media');
		liNode.setAttribute('course_id', data[i].id);
		liNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='line-height: 120px;height:84px;width:115px;min-width: 115px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body'><p class='mui-ellipsis-2' style='font-size:15px;height:60px;color:black;font-weight: bold;'>"+data[i].name+"</p><p class='mui-ellipsis' style='color:#1d8c3e;'><span class='mui-icon mui-icon-person-filled'></span>"+data[i].teacher+"</p></div>";
		detailNode.appendChild(liNode);
		if(!hasWriteShareInfo) {
			imgUrl = "http://www.diandou.me/files/imgs/" + data[i].image;
			descContent = data[i].teacher;
			shareTitle = data[i].name;
		}
	}
	mui('#courseListDetail').on('tap', '.mui-table-view-cell', function(event) {
		var elem = this;
		var course_id = elem.getAttribute('course_id');
		window.location = "/course/playCourse?course_id=" + course_id;
	});
}

mui.ajax({
	url: '/course/getAllOpenCourse',
	type: "POST",
	data: {},
	success: function(data) {
		if (!checkJsonIsEmpty(data)) {
			createDataList(data);
			document.getElementById('data_loading').style.display = 'none';
			document.getElementById('courseList').style.display = '';
		} else {
			document.getElementById('data_loading').style.display = 'none';
			document.getElementById('tips_info_detail').innerHTML = '暂时没有获取到课程数据,请稍后重试!';
			document.getElementById('tips_info').style.display = '';
		}
	},
	error: function(status, error) {
		document.getElementById('data_loading').style.display = 'none';
		document.getElementById('tips_info_detail').innerHTML = '服务器暂时无法响应请求,请稍后重试!';
		document.getElementById('tips_info').style.display = '';
	}
});
document.getElementById('banner_end').src = "/files/bannerimgs/banner1.jpg?" + new Date().getTime();
document.getElementById('banner_begin').src = "/files/bannerimgs/banner4.jpg?" + new Date().getTime();
document.getElementById('banner1').src = "/files/bannerimgs/banner1.jpg?" + new Date().getTime();
document.getElementById('banner2').src = "/files/bannerimgs/banner2.jpg?" + new Date().getTime();
document.getElementById('banner3').src = "/files/bannerimgs/banner3.jpg?" + new Date().getTime();
document.getElementById('banner4').src = "/files/bannerimgs/banner4.jpg?" + new Date().getTime();
/*mui.ajax({
	url: '/course/getCourseBanner',
	type: "POST",
	data: {},
	success: function(data) {
		if (!checkJsonIsEmpty(data)) {
			for (var i in data) {
				document.getElementById().style
			}
		} 
	},
	error: function(status, error) {
		
	}
});*/