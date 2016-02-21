package com.ddcb.weixin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {
	
	@RequestMapping("/getDDCBOpenClass")
	public String getOpenIdRedirect(HttpSession httpSession,
			HttpServletRequest request) {
		return "view/weixinview/ddcb_open_class";
	}
	
	@RequestMapping("/playDDCBOpenClass")
	public String playDDCBOpenClass(HttpSession httpSession,
			HttpServletRequest request) {
		return "view/weixinview/ddcb_play_class";
	}
	
	@RequestMapping("/weixin/getDDCBBuyVip")
	public String getDDCBBuyVip(HttpSession httpSession,
			HttpServletRequest request) {
		return "view/weixinview/ddcb_buy_vip";
	}
	
	@RequestMapping("/getDDCBLiveClass")
	public String getDDCBLiveClass(HttpSession httpSession,
			HttpServletRequest request) {
		return "view/weixinview/ddcb_live_class";
	}
	
}