package com.ddcb.weixin.controller;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
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
import org.jdom.input.SAXBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.xml.sax.InputSource;

import com.ddcb.dao.IUserCourseDao;
import com.ddcb.dao.IUserDao;
import com.ddcb.dao.IUserLiveCoursePayDao;
import com.ddcb.dao.IUserOpenIdDao;
import com.ddcb.dao.IWeixinUserDao;
import com.ddcb.dao.IWeixinUserLogDao;
import com.ddcb.model.UserCourseModel;
import com.ddcb.model.UserLiveCoursePayModel;
import com.ddcb.model.UserModel;
import com.ddcb.model.UserOpenIdModel;
import com.ddcb.model.WeixinUserModel;
import com.ddcb.utils.UserPwdMD5Encrypt;
import com.ddcb.utils.WeixinConstEnum;
import com.ddcb.utils.WeixinPayUtils;
import com.ddcb.utils.WeixinTools;
import com.ddcb.utils.WxPayDto;
import com.ddcb.utils.WxPayResult;

@Controller
public class WeixinUserController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinUserController.class);
	
	@Autowired
	private IUserDao userDao;
	
	@Autowired
	private IUserOpenIdDao userOpenIdDao;
	
	@Autowired
	private IUserCourseDao userCourseDao;
	
	@Autowired
	private IUserLiveCoursePayDao userLiveCoursePayDao;
	
	@Autowired
	private IWeixinUserDao weixinUserDao;
	
	@Autowired
	private IWeixinUserLogDao weixinUserLogDao;
	
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
	
	/*@RequestMapping("/weixin/weixinLogin")
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
	}*/
	
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
	
	@RequestMapping("/weixin/weixinLogin")
	public String weixinAuthorizedLogin(HttpSession httpSession, HttpServletRequest request) {
		String sessionOpenId = (String)httpSession.getAttribute("openid");
		String code = request.getParameter("code");
		String view = request.getParameter("view");
		httpSession.setAttribute("url_code", code);
		logger.debug("weixinAuthorizedLogin, sessionOpenId:{}", sessionOpenId);
		if(sessionOpenId == null || sessionOpenId.isEmpty()) {
			String accessToken = "";
			String openId = "";
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
				logger.debug("Auth openid : {}", openId);
				logger.debug("Auth openid 2: {}", retMap.get("openid"));
				try {
					UserOpenIdModel uoim = userOpenIdDao.getUserByUserOpenId((String)retMap.get("openid"));
					if(uoim == null) {
						logger.debug("UserOpenIdModel not exist, so add.");
						uoim = new UserOpenIdModel();
						uoim.setHeadimgurl((String)retMap.get("headimgurl"));
						uoim.setOpen_id((String)retMap.get("openid"));
						uoim.setUser_city((String)retMap.get("city"));
						uoim.setUser_nickname((String)retMap.get("nickname"));
						uoim.setUser_province((String)retMap.get("province"));
						uoim.setUser_sex(String.valueOf(retMap.get("sex")));
						userOpenIdDao.addUser(uoim);
					} else {
						logger.debug("UserOpenIdModel exist, so update.");
						userOpenIdDao.updateUser((String)retMap.get("nickname"), String.valueOf(retMap.get("sex")), (String)retMap.get("province"), (String)retMap.get("city"), (String)retMap.get("headimgurl"));
					}
				} catch(Exception ex) {
					logger.error(ex.toString());
				}
			}
		}
		if(sessionOpenId == null || sessionOpenId.isEmpty()) {
			
		}
		return "view/weixinview/" + view;
	}
	
	@RequestMapping("/getWeixinLoginUserInfo")
	@ResponseBody
	public Map<String, String> getWeixinLoginUserInfo(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		retMap.put("nickName", (String)httpSession.getAttribute("nickname"));
		retMap.put("headimgurl", (String)httpSession.getAttribute("headimgurl"));
		return retMap;
	}
	
	@RequestMapping("/userLiveClassWeixinPay")
	@ResponseBody
	public String userLiveClassWeixinPay(HttpSession httpSession, HttpServletRequest request) {
		String openId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("course_id");
		logger.debug("userLiveClassWeixinPay course_id : {}", courseId);
		if(openId == null || openId.isEmpty()) {
			return "\"ddcb_error_msg\":\"无法获取到您的openId，请关闭所有页面，从公众号菜单重新进入！\"";
		}
		WeixinPayUtils.setNotifyurl("http://www.diandou.me/weixinLiveClassPayResult");
		String fee = request.getParameter("fee");
		logger.debug("userLiveClassWeixinPay openid : {}", openId);
		logger.debug("userLiveClassWeixinPay fee : {}", fee);
		WxPayDto tpWxPay = new WxPayDto();
		tpWxPay.setOpenId(openId);
		tpWxPay.setBody("点都大讲堂直播课");
		tpWxPay.setOrderId(WeixinPayUtils.getNonceStr());
		tpWxPay.setSpbillCreateIp(request.getRemoteAddr());
		tpWxPay.setTotalFee(fee);
		tpWxPay.setAttach(courseId);
		String finalPK = WeixinPayUtils.getPackage(tpWxPay);
		if(finalPK == null || finalPK.isEmpty()) {
			return "\"ddcb_error_msg\":\"微信服务器无法获取到支付ID，请稍后重试！\"";
		}
		UserCourseModel model = userCourseDao.getUserCourseByUserIdAndCourseId(openId, Long.valueOf(courseId));
		if(model == null) {
			model = new UserCourseModel();
			model.setCourse_id(Long.valueOf(courseId));
			model.setCreate_time(new Timestamp(System.currentTimeMillis()));
			model.setTradeNo(tpWxPay.getOrderId());
			model.setUser_id(openId);
			if(userCourseDao.addUserCourseModel(model)) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		} else {
			if(userCourseDao.updateTradeNo(openId, Long.valueOf(courseId), tpWxPay.getOrderId())) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		}
	}
	
	@RequestMapping("/weixinLiveClassPayResult")
	@ResponseBody
	public String weixinLiveClassPayResult(HttpSession httpSession, HttpServletRequest request) {
		String inputLine;
		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		logger.debug("receive xml:" + notityXml);
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		String courseId = m.get("attach").toString();
		logger.debug("weixinLiveClassPayResult courseOd:" + courseId);
		logger.debug("weixinLiveClassPayResult openid:" + wpr.getOpenid());
		if("SUCCESS".equals(wpr.getResultCode())){
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			userCourseDao.updatePayStatusByTradeNo(wpr.getOpenid(), wpr.getOutTradeNo(), 1);
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		return resXml;
	}
	
	@RequestMapping("/userLiveClassDonateWeixinPay")
	@ResponseBody
	public String userLiveClassDonateWeixinPay(HttpSession httpSession, HttpServletRequest request) {
		String openId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("course_id");
		logger.debug("userLiveClassWeixinPay course_id : {}", courseId);
		if(openId == null || openId.isEmpty()) {
			return "\"ddcb_error_msg\":\"无法获取到您的openId，请关闭所有页面，从公众号菜单重新进入！\"";
		}
		WeixinPayUtils.setNotifyurl("http://www.diandou.me/weixinLiveClassDonatePayResult");
		String fee = "5.00";
		logger.debug("userLiveClassDonateWeixinPay openid : {}", openId);
		logger.debug("userLiveClassDonateWeixinPay fee : {}", fee);
		WxPayDto tpWxPay = new WxPayDto();
		tpWxPay.setOpenId(openId);
		tpWxPay.setBody("点都大讲堂直播讲座");
		tpWxPay.setOrderId(WeixinPayUtils.getNonceStr());
		tpWxPay.setSpbillCreateIp(request.getRemoteAddr());
		tpWxPay.setTotalFee(fee);
		tpWxPay.setAttach(courseId);
		String finalPK = WeixinPayUtils.getPackage(tpWxPay);
		if(finalPK == null || finalPK.isEmpty()) {
			return "\"ddcb_error_msg\":\"微信服务器无法获取到支付ID，请稍后重试！\"";
		}
		UserLiveCoursePayModel model = userLiveCoursePayDao.getUserCourseByUserIdAndCourseId(openId, Long.valueOf(courseId));
		if(model == null) {
			model = new UserLiveCoursePayModel();
			model.setCourse_id(Long.valueOf(courseId));
			model.setCreate_time(new Timestamp(System.currentTimeMillis()));
			model.setTradeNo(tpWxPay.getOrderId());
			model.setUser_id(openId);
			if(userLiveCoursePayDao.addUserLiveCoursePayModel(model)) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		} else {
			if(userLiveCoursePayDao.updateTradeNo(openId, Long.valueOf(courseId), tpWxPay.getOrderId())) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		}
	}
	
	@RequestMapping("/weixinLiveClassDonatePayResult")
	@ResponseBody
	public String weixinLiveClassDonatePayResult(HttpSession httpSession, HttpServletRequest request) {
		String inputLine;
		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		logger.debug("receive xml:" + notityXml);
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		String courseId = m.get("attach").toString();
		logger.debug("weixinLiveClassDonatePayResult courseOd:" + courseId);
		logger.debug("weixinLiveClassDonatePayResult openid:" + wpr.getOpenid());
		if("SUCCESS".equals(wpr.getResultCode())){
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			userLiveCoursePayDao.updatePayStatusByTradeNo(wpr.getOpenid(), wpr.getOutTradeNo(), 1);
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		return resXml;
	}
	
	@RequestMapping("/userVIPWeixinPay")
	@ResponseBody
	public String userVIPWeixinPay(HttpSession httpSession, HttpServletRequest request) {
		String openId = (String)httpSession.getAttribute("openid");
		String userType = request.getParameter("user_type");
		logger.debug("userVIPWeixinPay userType : {}", userType);
		if(openId == null || openId.isEmpty()) {
			return "\"ddcb_error_msg\":\"无法获取到您的openId，请关闭所有页面，从公众号菜单重新进入！\"";
		}
		WeixinPayUtils.setNotifyurl("http://www.diandou.me/weixinVIPPayResult");
		String fee = "";
		if(("1").equals(userType)) {
			fee = "45.00";
		} else if(("2").equals(userType)) {
			fee = "120.00";
		} else {
			fee = "365.00";
		}
		if(("os3bVs6Qiq2Bo1dbu36Tu9WkDEa8").equals(openId)) fee = "0.01";
		logger.debug("userChooseWeixinPay openid : {}", openId);
		logger.debug("userChooseWeixinPay fee : {}", fee);
		WxPayDto tpWxPay = new WxPayDto();
		tpWxPay.setOpenId(openId);
		tpWxPay.setBody("点都大讲堂VIP会员");
		tpWxPay.setOrderId(WeixinPayUtils.getNonceStr());
		tpWxPay.setSpbillCreateIp(request.getRemoteAddr());
		tpWxPay.setTotalFee(fee);
		tpWxPay.setAttach(userType);
		String finalPK = WeixinPayUtils.getPackage(tpWxPay);
		if(finalPK == null || finalPK.isEmpty()) {
			return "\"ddcb_error_msg\":\"微信服务器无法获取到支付ID，请稍后重试！\"";
		}
		WeixinUserModel model = weixinUserDao.getWeixinUserByUserId(openId);
		if(model == null) {
			model = new WeixinUserModel();
			model.setUser_id(openId);
			model.setCreate_time(new Timestamp(System.currentTimeMillis()));
			model.setTrade_no(tpWxPay.getOrderId());
			model.setUser_type(Integer.valueOf(userType));
			weixinUserLogDao.addWeixinUser(model);
			if(weixinUserDao.addWeixinUser(model)) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		} else {
			model.setUser_type(Integer.valueOf(userType));
			model.setTrade_no(tpWxPay.getOrderId());
			model.setCreate_time(new Timestamp(System.currentTimeMillis()));
			weixinUserLogDao.addWeixinUser(model);
			if(weixinUserDao.updateWeixinUserBeforePay(openId, tpWxPay.getOrderId(), Integer.valueOf(userType))) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		}
	}
	
	@RequestMapping("/weixinVIPPayResult")
	@ResponseBody
	public String weixinVIPPayResult(HttpSession httpSession, HttpServletRequest request) {
		String inputLine;
		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		logger.debug("receive xml:" + notityXml);
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		String user_vip_type = m.get("attach").toString();
		logger.debug("user_vip_type:" + user_vip_type);
		logger.debug("user_vip_type openid:" + wpr.getOpenid());
		if("SUCCESS".equals(wpr.getResultCode())){
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			Date date=new Date();
			WeixinUserModel wum = weixinUserDao.getWeixinUserByUserId(wpr.getOpenid());
			if(wum.getPay_status() == 1 && wum.getExpiration_time() != null &&
					wum.getExpiration_time().getTime() >= date.getTime()) {
				date = new Date(wum.getExpiration_time().getTime());
			}
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date);
			if(("1").equals(user_vip_type)) {
				calendar.add(Calendar.MONTH, 1);
				calendar.getTime().getTime();
				Timestamp tm = new Timestamp(calendar.getTime().getTime());
				weixinUserDao.updateWeixinUserAfterPay(wpr.getOpenid(), wpr.getOutTradeNo(), Integer.valueOf(user_vip_type), tm);
				wum.setExpiration_time(tm);
				wum.setPay_status(1);
				wum.setTrade_no(wpr.getOutTradeNo());
				wum.setCreate_time(new Timestamp(System.currentTimeMillis()));
				weixinUserLogDao.addWeixinUser(wum);
			} else if(("2").equals(user_vip_type)) {
				calendar.add(Calendar.MONTH, 3);
				calendar.getTime().getTime();
				Timestamp tm = new Timestamp(calendar.getTime().getTime());
				weixinUserDao.updateWeixinUserAfterPay(wpr.getOpenid(), wpr.getOutTradeNo(), Integer.valueOf(user_vip_type), tm);
				wum.setExpiration_time(tm);
				wum.setPay_status(1);
				wum.setTrade_no(wpr.getOutTradeNo());
				wum.setCreate_time(new Timestamp(System.currentTimeMillis()));
				weixinUserLogDao.addWeixinUser(wum);
			} else if(("3").equals(user_vip_type)) {
				calendar.add(Calendar.MONTH, 12);
				calendar.getTime().getTime();
				Timestamp tm = new Timestamp(calendar.getTime().getTime());
				weixinUserDao.updateWeixinUserAfterPay(wpr.getOpenid(), wpr.getOutTradeNo(), Integer.valueOf(user_vip_type), tm);
				wum.setExpiration_time(tm);
				wum.setPay_status(1);
				wum.setTrade_no(wpr.getOutTradeNo());
				wum.setCreate_time(new Timestamp(System.currentTimeMillis()));
				weixinUserLogDao.addWeixinUser(wum);
			}
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		return resXml;
	}
	
	@RequestMapping("/userChooseWeixinPay")
	@ResponseBody
	public String userChooseWeixinPay(HttpSession httpSession, HttpServletRequest request) {
		String openId = (String)httpSession.getAttribute("openid");
		String courseName = request.getParameter("coursename");
		String courseId = request.getParameter("courseid");
		Long courseId_ = null;
		WeixinPayUtils.setNotifyurl("http://www.diandou.me/weixinPayResult");
		if(openId == null || openId.isEmpty()) {
			return "\"ddcb_error_msg\":\"无法获取到您的openId，请退出后重新进入当前页面！\"";
		}
		try {
			courseId_ = Long.valueOf(courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
			return "\"ddcb_error_msg\":\"课程ID号丢失，请退出后重新进入当前页面！\"";
		}
		
		String fee = request.getParameter("fee");
		logger.debug("userChooseWeixinPay openid : {}", openId);
		logger.debug("userChooseWeixinPay courseName : {}", courseName);
		logger.debug("userChooseWeixinPay fee : {}", fee);
		WxPayDto tpWxPay = new WxPayDto();
		tpWxPay.setOpenId(openId);
		tpWxPay.setBody(courseName);
		tpWxPay.setOrderId(WeixinPayUtils.getNonceStr());
		tpWxPay.setSpbillCreateIp(request.getRemoteAddr());
		tpWxPay.setTotalFee(fee);
		String finalPK = WeixinPayUtils.getPackage(tpWxPay);
		if(finalPK == null || finalPK.isEmpty()) {
			return "\"ddcb_error_msg\":\"微信服务器无法获取到支付ID，请稍后重试！\"";
		}
		UserCourseModel ucm = new UserCourseModel();
		ucm.setCourse_id(courseId_);
		ucm.setCreate_time(new Timestamp(System.currentTimeMillis()));
		ucm.setForward_status(0);
		ucm.setPay_status(0);
		ucm.setTradeNo(tpWxPay.getOrderId());
		ucm.setUser_id(openId);
		logger.debug("weixinpay UserCourseModel : {}", ucm.toString());
		UserCourseModel model = userCourseDao.newGetUserCourseByUserIdAndCourseId(openId, courseId_);
		if(model == null) {
			if(userCourseDao.addUserCourseModel(ucm)) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		} else {
			if(userCourseDao.updateTradeNo(openId, courseId_, tpWxPay.getOrderId())) {
				return finalPK;
			} else {
				return "\"ddcb_error_msg\":\"写数据库错误，请稍后重试！\"";
			}
		}
	}
	
	@RequestMapping("/weixinPayResult")
	@ResponseBody
	public String weixinPayResult(HttpSession httpSession, HttpServletRequest request) {
		String inputLine;
		String notityXml = "";
		String resXml = "";
		try {
			while ((inputLine = request.getReader().readLine()) != null) {
				notityXml += inputLine;
			}
			request.getReader().close();
		} catch (Exception e) {
			logger.debug(e.toString());
		}

		logger.debug("receive xml：" + notityXml);
		
		Map m = parseXmlToList2(notityXml);
		WxPayResult wpr = new WxPayResult();
		wpr.setAppid(m.get("appid").toString());
		wpr.setBankType(m.get("bank_type").toString());
		wpr.setCashFee(m.get("cash_fee").toString());
		wpr.setFeeType(m.get("fee_type").toString());
		wpr.setIsSubscribe(m.get("is_subscribe").toString());
		wpr.setMchId(m.get("mch_id").toString());
		wpr.setNonceStr(m.get("nonce_str").toString());
		wpr.setOpenid(m.get("openid").toString());
		wpr.setOutTradeNo(m.get("out_trade_no").toString());
		wpr.setResultCode(m.get("result_code").toString());
		wpr.setReturnCode(m.get("return_code").toString());
		wpr.setSign(m.get("sign").toString());
		wpr.setTimeEnd(m.get("time_end").toString());
		wpr.setTotalFee(m.get("total_fee").toString());
		wpr.setTradeType(m.get("trade_type").toString());
		wpr.setTransactionId(m.get("transaction_id").toString());
		
		if("SUCCESS".equals(wpr.getResultCode())){
			resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
			+ "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
			userCourseDao.updatePayStatusByTradeNo(wpr.getOpenid(), wpr.getOutTradeNo(), 1);
		}else{
			resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
			+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
		}
		return resXml;
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
	
	private Map parseXmlToList2(String xml) {
		Map retMap = new HashMap();
		try {
			StringReader read = new StringReader(xml);
			// 创建新的输入源SAX 解析器将使用 InputSource 对象来确定如何读取 XML 输入
			InputSource source = new InputSource(read);
			// 创建一个新的SAXBuilder
			org.jdom.input.SAXBuilder sb = new SAXBuilder();
			// 通过输入源构造一个Document
			org.jdom.Document doc = (org.jdom.Document) sb.build(source);
			org.jdom.Element root = doc.getRootElement();// 指向根节点
			List<org.jdom.Element> es = root.getChildren();
			if (es != null && es.size() != 0) {
				for (org.jdom.Element element : es) {
					retMap.put(element.getName(), element.getValue());
				}
			}
		} catch (Exception e) {
			logger.debug(e.toString());
		}
		return retMap;
	}
	
	public static void main(String[] args) {
		Date date=new Date();
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date);
		calendar.add(Calendar.MONTH,5);
		calendar.getTime().getTime();
		Timestamp tm = new Timestamp(calendar.getTime().getTime());
		System.out.println(tm.toString());
	}
}
