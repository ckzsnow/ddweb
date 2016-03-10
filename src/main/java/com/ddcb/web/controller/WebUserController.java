package com.ddcb.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddcb.dao.IUserDao;
import com.ddcb.dao.IUserOpenIdDao;
import com.ddcb.dao.IWeixinUserDao;
import com.ddcb.model.UserModel;
import com.ddcb.model.UserOpenIdDetailModel;
import com.ddcb.model.UserOpenIdModel;
import com.ddcb.utils.UserPwdMD5Encrypt;
import com.ddcb.utils.WeixinConstEnum;
import com.ddcb.utils.WeixinTools;

@Controller
public class WebUserController {

	private static final Logger logger = LoggerFactory.getLogger(WebUserController.class);

	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserOpenIdDao userOpenIdDao;

	@RequestMapping("/web/webUserLogin")
	@ResponseBody
	public Map<String, String> webUserLogin(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		if (user_id == null || user_pwd == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "账号或密码为空，请检查！");
			return retMap;
		}
		UserModel um = userDao.getUserByUserId(user_id);
		if (um != null) {
			if (UserPwdMD5Encrypt.getPasswordByMD5Encrypt(user_pwd).equals(um.getUser_pwd())) {
				retMap.put("error_code", "0");
				retMap.put("error_msg", "");
				httpSession.setAttribute("user_id", user_id);
				return retMap;
			} else {
				retMap.put("error_code", "2");
				retMap.put("error_msg", "您输入的密码不正确，请检查！");
				return retMap;
			}
		} else {
			retMap.put("error_code", "3");
			retMap.put("error_msg", "您输入的账号不正确，请检查！");
			return retMap;
		}
	}

	@RequestMapping("/web/webUserLoginSuccess")
	public String webUserLoginSuccess(HttpSession httpSession) {
		String userId = (String) httpSession.getAttribute("user_id");
		if (userId == null || userId.isEmpty()) {
			return "redirect:/view/webview/login.html";
		} else {
			return "redirect:/view/webview/admin_index.html";
		}
	}

	@RequestMapping("/web/webUserLogout")
	public String webUserLogout(HttpSession httpSession) {
		httpSession.removeAttribute("user_id");
		return "redirect:/view/webview/login.html";
	}

	@RequestMapping("/getUserLoginHtml")
	public String getUserLoginHtml() {
		return "redirect:/view/webview/login.html";
	}
	
	@RequestMapping("/weixin/getAllWeixinUserInfo")
	@ResponseBody
	public List<UserOpenIdDetailModel> getAllWeixinUserInfo(HttpSession httpSession, HttpServletRequest request) {
		return userOpenIdDao.getAllUser();
	}
}
