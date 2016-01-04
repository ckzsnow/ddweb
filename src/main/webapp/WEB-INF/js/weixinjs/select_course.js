function checkJsonIsEmpty(json) {
	var isEmpty = true;
	if (json == null)
		return true;
	for ( var jsonKey in json) {
		isEmpty = false;
		break;
	}
	return isEmpty;
}
mui.createUploadMask = function(callback) {
	var element = document.createElement('div');
	element.classList.add('upload-file');	
	element.addEventListener('touchmove', mui.preventDefault);
	element.addEventListener('tap', function() {
		//mask.close();
	});
	var progressNode = document.createElement('div');
	progressNode.id="upload_progress";
	progressNode.setAttribute('class', 'circle');
	progressNode.innerHTML = "<strong></strong>";
	element.appendChild(progressNode);
	var mask = [element];
	mask._show = false;
	mask.show = function() {
		mask._show = true;
		element.setAttribute('style', 'opacity:1');
		progressNode.style.marginLeft = window.screen.availWidth/2-50 + "px";
		progressNode.style.marginTop = window.screen.availHeight/2-50 + "px";
		document.body.appendChild(element);
		$('#upload_progress').circleProgress({
	    	value: 0,
	    	emptyFill: 'rgba(74, 197, 248, 1)',
	    	animation: false,
	    	fill: { gradient: ["red", "blue", "green"], gradientAngle: Math.PI / 4 }	
	    }).on('circle_progress_percent', function(event, progress) {
	        $(this).find('strong').html(parseInt(100 * progress) + '<i>%</i>');
	        $('#upload_progress').circleProgress('value', progress);
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
mui.createProcessingMask = function(callback) {
	var element = document.createElement('div');
	element.classList.add('upload-file');
	element.addEventListener('touchmove', mui.preventDefault);
	element.addEventListener('tap', function() {
		//mask.close();
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
		//processingNode.style.marginLeft = window.screen.availWidth/2 + "px";
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
	var mask = [ element ];
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
	swipeBack : true
//启用右滑关闭功能
});
mui('.mui-scroll-wrapper').scroll();
var uploadFile = "";
function getImageDataURL(img) {
	var canvas = document.createElement('canvas');
	canvas.width = img.width;
	canvas.height = img.height;
	var context = canvas.getContext('2d');
	context.drawImage(img, 0, 0);
	var dataURL = canvas.toDataURL('image/png');
	return dataURL;
}
var imgURL;
document.getElementById("share_image").onchange = function(event) {
	var imageRootNode = document.getElementById('image');
	imageRootNode.innerHTML = '';
	var imgEle = document.createElement('img');
	imgEle.style.marginLeft = '5px';
	imgEle.style.borderRadius = '5px';
	imgEle.style.width = '70px';
	imgEle.style.height = '70px';
	imgEle.setAttribute('data-preview-group', '1');
	imgEle.setAttribute('data-preview-src', '');
	imageRootNode.appendChild(imgEle);
	var files = event.target.files,
		file;
	if (files && files.length > 0) {
		file = files[0];
		try {
			var URL = window.URL || window.webkitURL;
			imgURL = URL.createObjectURL(file);
			imgEle.src = imgURL;
			uploadFile = document.getElementById('share_image').files[0];
			//URL.revokeObjectURL(imgURL);
		} catch (e) {
			try {
				var fileReader = new FileReader();
				fileReader.onload = function(event) {
					imgEle.src = event.target.result;
					uploadFile = document.getElementById('share_image').files[0];
				};
				fileReader.readAsDataURL(file);
			} catch (e) {
				alert("Neither createObjectURL or FileReader are supported");
			}
		}
	}
}
function uploadShareImage() {
	if(uploadFile == null || uploadFile == "") {
		mui.createTipDialog('您还没有选择图片，请先选择图片！',null).show();
		return;
	}
	var mask = mui.createUploadMask(false);
	mask.show();
	$('#upload_progress').find('strong').html(0 + '<i>%</i>');
	var formData = {};
	var xhr = new XMLHttpRequest();
	var fd = new FormData();
	fd.append("files", uploadFile);
	xhr.upload.addEventListener("progress", function(evt) {
		if (evt.lengthComputable) {
			var percentComplete = Math.round(evt.loaded * 100 / evt.total) / 100;
			$('#upload_progress').trigger('circle_progress_percent', [percentComplete]);
		}
	}, false);
	xhr.addEventListener("load", function(evt) {
		var res = evt.target.responseText;
		res = JSON.parse(res);
		if (res.error_code == "0") {
			mui.createTipDialog('微信圈分享图片上传成功!',null).show();
		} else {
			mui.createTipDialog(res.error_msg, null).show();
		}
		mask.close();
	}, false);
	xhr.addEventListener("error", function(evt) {
		mask.close();
		mui.createTipDialog('微信圈分享图片上传失败，请稍后重试！',null).show();
		
	}, false);
	xhr.addEventListener("abort", null, false);
	xhr.open("POST", "/weixin/uploadShareImage");
	xhr.send(fd);
}
