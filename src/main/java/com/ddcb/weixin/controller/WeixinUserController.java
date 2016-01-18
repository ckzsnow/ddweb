package com.ddcb.weixin.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ddcb.dao.IUserDao;
import com.ddcb.model.UserModel;
import com.ddcb.utils.UserPwdMD5Encrypt;
import com.ddcb.utils.WeixinConstEnum;
import com.ddcb.utils.WeixinTools;

@Controller
public class WeixinUserController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinUserController.class);
	
	@Autowired
	private IUserDao userDao;
	
	@RequestMapping("/weixin/weixinRegisterUser")
	@ResponseBody
	public Map<String, String> weixinRegisterUser(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		String check_code = request.getParameter("check_code");
		String sessionCheckCode = (String)httpSession.getAttribute("check_code");
		if(user_id == null || user_pwd == null || check_code == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "账号、密码或验证码有尚未填写的，请检查！");
			return retMap;
		}
		if(!check_code.equals(sessionCheckCode)) {
			retMap.put("error_code", "2");
			retMap.put("error_msg", "您输入的验证码不正确，请检查！");
			return retMap;
		}
		UserModel um = userDao.getUserByUserId(user_id);
		if(um != null && um.getUser_id().equals(user_id)) {
			retMap.put("error_code", "3");
			retMap.put("error_msg", "当前手机号码已经注册过，请直接登陆！");
			return retMap;
		}
		String encryptPwd = UserPwdMD5Encrypt.getPasswordByMD5Encrypt(user_pwd);
		um = new UserModel();
		um.setUser_id(user_id);
		um.setUser_pwd(encryptPwd);
		um.setUser_type(1);
		um.setCreate_time(new Timestamp(System.currentTimeMillis()));
		userDao.addUser(um);
		retMap.put("error_code", "0");
		retMap.put("error_msg", "注册成功，请登录！");
		return retMap;
	}
	
	@RequestMapping("/weixin/weixinLogin")
	@ResponseBody
	public Map<String, String> weixinLogin(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		if(user_id == null || user_pwd == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "账号或密码为空，请检查！");
			return retMap;
		}
		UserModel um = userDao.getUserByUserId(user_id);
		if(um != null) {
			if(UserPwdMD5Encrypt.getPasswordByMD5Encrypt(user_pwd).equals(um.getUser_pwd())) {
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
			retMap.put("error_msg", "您输入的手机号码还没有注册，请先注册！");
			return retMap;
		}
	}
	
	@RequestMapping("/weixin/weixinLogout")
	public String weixinLogout(HttpSession httpSession, HttpServletRequest request) {
		httpSession.removeAttribute("user_id");
		httpSession.removeAttribute("courseid");
		return "redirect:/view/weixinview/recent_class.html";
	}
	
	@RequestMapping("/weixin/weixinForgetPwd")
	@ResponseBody
	public Map<String, String> weixinForgetPwd(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String user_id = request.getParameter("user_id");
		String user_pwd = request.getParameter("user_pwd");
		String check_code = request.getParameter("check_code");
		String sessionCheckCode = (String)httpSession.getAttribute("check_code");
		if(user_id == null || user_pwd == null || check_code == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "账号、密码或验证码有尚未填写的，请检查！");
			return retMap;
		}
		if(!check_code.equals(sessionCheckCode)) {
			retMap.put("error_code", "2");
			retMap.put("error_msg", "您输入的验证码不正确，请检查！");
			return retMap;
		}
		UserModel um = userDao.getUserByUserId(user_id);
		if(um == null) {
			retMap.put("error_code", "3");
			retMap.put("error_msg", "当前手机号码还没有注册过，请先注册！");
			return retMap;
		} else {
			if(userDao.updateUserPwd(user_id, UserPwdMD5Encrypt.getPasswordByMD5Encrypt(user_pwd))) {
				retMap.put("error_code", "0");
				retMap.put("error_msg", "");
				return retMap;
			} else {
				retMap.put("error_code", "4");
				retMap.put("error_msg", "更改密码失败，请联系管理员");
				return retMap;
			}
		}
	}	
	
	@RequestMapping("/weixin/weixinGetCheckCode")
	@ResponseBody
	public Map<String, String> weixinGetCheckCode(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String phone = request.getParameter("user_id");
		sendSMSCode(retMap, phone);
		if(retMap.containsKey("send_status") && ("success").equals(retMap.get("send_status"))) {
			retMap.put("error_code", "0");
			retMap.put("error_msg", "验证码发送成功！");
			httpSession.setAttribute("check_code", retMap.get("check_code"));
		} else {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "验证码服务器暂时不可用，请稍后重试！");
		}
		return retMap;
	}
	
	@RequestMapping("/weixinLogin")
	public String weixinAuthorizedLogin(HttpSession httpSession, HttpServletRequest request) {
		String code = request.getParameter("code");
		String accessToken = "";
		String openId = "";
		if(code == null || code.isEmpty()) {
			return "redirect:/view/weixinview/recent_class.html";
		}
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		url = url.replace("APPID", WeixinConstEnum.COMPANY_APP_ID.toString()).replace("SECRET",
				WeixinConstEnum.COMPANY_APP_SECRET.toString()).replace("CODE",
						code);
		Map<Object, Object> map = WeixinTools.httpGet(url);
		if (map != null && map.containsKey("access_token") && map.containsKey("refresh_token")) {
			accessToken = (String) map.get("access_token");
			openId = (String) map.get("openid");
			url = "https://api.weixin.qq.com/sns/userinfo?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
			url = url.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
			Map<Object, Object> retMap = WeixinTools.httpGet(url);
			httpSession.setAttribute("openid", retMap.get("openid"));
			httpSession.setAttribute("nickname", retMap.get("nickname"));
			httpSession.setAttribute("headimgurl", retMap.get("headimgurl"));
		}
		return "redirect:/view/weixinview/recent_class.html";
	}
	
	@RequestMapping("/getWeixinLoginUserInfo")
	@ResponseBody
	public Map<String, String> getWeixinLoginUserInfo(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		retMap.put("nickName", (String)httpSession.getAttribute("nickname"));
		retMap.put("headimgurl", (String)httpSession.getAttribute("headimgurl"));
		return retMap;
	}
	
	private void sendSMSCode(Map<String, String> retMap, String phone) {
		HttpClient client = new HttpClient(); 
		PostMethod method = new PostMethod("http://106.ihuyi.cn/webservice/sms.php?method=Submit");		
		client.getParams().setContentCharset("UTF-8");
		method.setRequestHeader("ContentType","application/x-www-form-urlencoded;charset=UTF-8");		
		int mobile_code = (int)((Math.random()*9+1)*100000);
		retMap.put("check_code", String.valueOf(mobile_code));
	    String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");
		NameValuePair[] data = {//提交短信
			    new NameValuePair("account", "cf_ckzsnow"), 
			    new NameValuePair("password", "ckzcbm110"), //密码可以使用明文密码或使用32位MD5加密
			    new NameValuePair("mobile", phone), 
			    new NameValuePair("content", content),
		};
		method.setRequestBody(data);
		try {
			client.executeMethod(method);	
			String SubmitResult =method.getResponseBodyAsString();
			Document doc = DocumentHelper.parseText(SubmitResult); 
			Element root = doc.getRootElement();
			String code = root.elementText("code");
			if("2".equals(code)){
				retMap.put("send_status", "success");
			} else {
				retMap.put("send_status", "fail");
			}
		} catch (IOException e) {
			logger.error(e.toString());
		} catch (DocumentException e) {
			logger.error(e.toString());
		}	
	}
}
