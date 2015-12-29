function getJsConfigInfoSuccess(data,status) {
	wx.config({
		debug:true,
		appId: 'wx309df15b6ddc5371',
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
mui.ajax({ 
    type : "POST", 
    url  : "/getJsConfigInfo?url=" + encodeURIComponent(location.href.split('#')[0]),
    async: false,
    success : getJsConfigInfoSuccess 
});
wx.ready(function(){
	var imgUrl = "http://diandou.me/img/webimg/share_img.png";
	var lineLink = window.location.href;
	var descContent = "点豆成兵&mdash;为进取心而生，专注职场“传、帮、带”";
	var shareTitle = "点豆成兵";
	var appid = 'wx309df15b6ddc5371';
	setTimeout(function(){
		wx.onMenuShareTimeline({
		    title: shareTitle, // 分享标题
		    link: lineLink, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
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
		    success: function () { 
		        // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		        // 用户取消分享后执行的回调函数
		    }
		});
		wx.onMenuShareQQ({
		    title: shareTitle, // 分享标题
		    desc: descContent, // 分享描述
		    link: lineLink, // 分享链接
		    imgUrl: imgUrl, // 分享图标
		    success: function () { 
		       // 用户确认分享后执行的回调函数
		    },
		    cancel: function () { 
		       // 用户取消分享后执行的回调函数
		    }
		});
	}, 500);
});