function checkJsonIsEmpty(json) {
	var isEmpty = true;
	if (json == null) return true;
	for (var jsonKey in json) {
		isEmpty = false;
		break;
	}
	return isEmpty;
}
mui.createProcessingMask = function(callback) {
	var element = document.createElement('div');
	element.classList.add('upload-file');
	element.addEventListener('touchmove', mui.preventDefault);
	element.addEventListener('tap', function() {
		// mask.close();
	});
	var processingNode = document.createElement('div');
	processingNode.setAttribute('class', 'mui-loading');
	processingNode.innerHTML = "<div class='mui-spinner' style='width:60px;height:60px;'></div><div style='text-align: center;color:white;'>正在处理请求</div>";
	element.appendChild(processingNode);
	var mask = [ element ];
	mask._show = false;
	mask.show = function() {
		mask._show = true;
		element.setAttribute('style', 'opacity:1');
		// processingNode.style.marginLeft = window.screen.availWidth/2 + "px";
		processingNode.style.marginTop = window.screen.availHeight / 2 - 30
				+ "px";
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
		if (callback) {
			if (callback() !== false) {
				mask._remove();
			}
		} else {
			mask._remove();
		}
	};
	return mask;
};
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
function loginClick() {
	window.location = "login.html";
}
function logoutClick() {
	window.location = "/weixin/weixinLogout";
}
function createDataList(data) {
	var detailNode = document.getElementById('courseListDetail');
	if(data.hasLogin == "0") {
		document.getElementById('login_button').style.display = "";
		document.getElementById('logout_button').style.display = "none";
	} else {
		document.getElementById('login_button').style.display = "none";
		document.getElementById('logout_button').style.display = "";
	}
	data = data.data;
	for (var i in data) {
		var liNode = document.createElement('li');
		liNode.setAttribute('class', 'mui-table-view-cell mui-media');
		liNode.style.paddingBottom = '0px';
		liNode.setAttribute('course_id', data[i].id);
		var liNodeFirstNode = document.createElement('div');
		liNodeFirstNode.innerHTML = "<img class='mui-media-object mui-pull-left' style='line-height: 120px;height:84px;width:115px;min-width: 115px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body' style='height:84px;'><p class='mui-ellipsis' tyle='font-size:15px;color:black;'>"+data[i].name+"</p><p class='mui-ellipsis-2'>"+data[i].course_abstract+"</p><p class='mui-ellipsis' style='color:#1d8c3e;'><span class='mui-icon mui-icon-person-filled'></span>"+data[i].teacher+"</p></div>";
		//liNode.innerHTML = "<div><img class='mui-media-object mui-pull-left' style='line-height: 120px;height:84px;width:115px;min-width: 115px;' src='/files/imgs/"+data[i].image+"'><div class='mui-media-body' style='height:84px;'><p class='mui-ellipsis' tyle='font-size:15px;color:black;'>"+data[i].name+"</p><p class='mui-ellipsis-2'>"+data[i].course_abstract+"</p><p class='mui-ellipsis' style='color:#1d8c3e;'><span class='mui-icon mui-icon-person-filled'></span>"+data[i].teacher+"</p></div></div><div><div class='mui-pull-right'><button>报名</button></div></div>";
		var liNodeSecondNode = document.createElement('div');
		liNodeSecondNode.style.marginTop = "5px";
		var buttonDivNode = document.createElement('div');
		buttonDivNode.classList.add('mui-pull-right');
		var buttonNode = document.createElement('button');
		buttonNode.classList.add('mui-btn-block');
		buttonNode.classList.add('mui-btn-success');
		buttonNode.style.backgroundColor = '#1d8c3e';
		buttonNode.style.lineHeight = '0.1';
		buttonNode.style.fontSize = '10px';
		buttonNode.style.width = '100px';
		buttonNode.style.width = '100px';
		buttonDivNode.appendChild(buttonNode);
		buttonNode.setAttribute("select_status", data[i].select_status);
		buttonNode.setAttribute("pay_status", data[i].pay_status);
		buttonNode.setAttribute("course_id", data[i].id);
		buttonNode.setAttribute("forward_status", data[i].forward_status);
		buttonNode.setAttribute("button_type", "0");
		if(data[i].select_status == "1") {
			if(data[i].pay_status == "1") {
				buttonNode.innerHTML = "点击上课";
				buttonNode.setAttribute("button_type", "1");
			} else {
				 if(data[i].forward_status == "0") {
					 buttonNode.innerHTML = "分享审核中";
					 buttonNode.setAttribute("disabled", "");
				 } else if(data[i].forward_status == "2"){
					 buttonNode.innerHTML = "审核未通过";
					 buttonNode.setAttribute("disabled", "");
				 } else {
					 buttonNode.innerHTML = "点击上课";
					 buttonNode.setAttribute("button_type", "1");
				 }
			}
		} else {
			buttonNode.innerHTML = "我要报名";
		}
		liNodeSecondNode.appendChild(buttonDivNode);
		liNode.appendChild(liNodeFirstNode);
		liNode.appendChild(liNodeSecondNode);
		detailNode.appendChild(liNode);
	}
	mui('#courseListDetail').on('tap', 'button', function(event) {
		var elem = this;
		var btn_type = elem.getAttribute('button_type');
		var course_id = elem.getAttribute('course_id');
		if(btn_type == "1") {
			window.location = "/course/playLiveCourse?course_id=" + course_id;
		} else {
			var mask = mui.createProcessingMask(null);
			mask.show();
			mui.ajax({
				url: '/course/selectCourse',
				type: "POST",
				data: {course_id:course_id},
				success: function(data) {
					mask.close();
					if (data.error_code == "0") {
						window.location = "select_course.html";
					} else {
						mui.createTipDialog(data.error_msg, null).show();
					}
				},
				error: function(status, error) {
					mask.close();
					mui.createTipDialog("服务器暂时无法响应您的请求，请稍后重试！", null).show();
				}
			});
		}
	});
}

mui.ajax({
	url: '/course/getAllRecentCourse',
	type: "POST",
	data: {},
	success: function(data) {
		if (!checkJsonIsEmpty(data.data)) {
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