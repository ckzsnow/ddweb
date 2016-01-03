package com.ddcb.weixin.controller;

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

import com.ddcb.dao.ICourseDao;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.dao.IUserCourseDao;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.UserCourseModel;

@Controller
public class WeixinCourseController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinCourseController.class);
	
	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private IUserCourseDao userCourseDao;
	
	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@RequestMapping("/course/getAllCourse")
	@ResponseBody
	public List<CourseModel> getAllCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllCourse();
		return courseList;
	}
	
	@RequestMapping("/course/getAllRecentCourse")
	@ResponseBody
	public Map<String, Object> getAllRecentCourse(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("user_id");
		List<CourseModel> courseList = courseDao.getAllRecentCourse();
		Map<String, Object> retMap = new HashMap<>();
		if(userId == null || userId.isEmpty()) {
			retMap.put("hasLogin", "0");
			retMap.put("data", courseList);
			return retMap;
		}
		for(CourseModel cm : courseList) {
			UserCourseModel ucm = userCourseDao.getUserCourseByUserIdAndCourseId(userId, cm.getId());
			if(ucm == null) {
				cm.setSelect_status(0);
			} else {
				cm.setSelect_status(1);
				cm.setPay_status(ucm.getPay_status());
				cm.setForward_status(ucm.getForward_status());
			}
		}
		retMap.put("hasLogin", "1");
		retMap.put("data", courseList);
		return retMap;
	}
	
	@RequestMapping("/course/playCourse")
	public String getPlayCourseHtml(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		httpSession.setAttribute("courseid", courseId);
		return "redirect:/view/weixinview/play_class.html";
	}
	
	@RequestMapping("/course/playLiveCourse")
	public String getPlayLiveCourse(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		httpSession.setAttribute("courseid", courseId);
		return "redirect:/view/weixinview/play_live_class.html";
	}
	
	@RequestMapping("/course/selectCourse")
	@ResponseBody
	public Map<String, String> selectCourse(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String courseId = request.getParameter("course_id");
		String userId = (String) httpSession.getAttribute("user_id");
		if(userId == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "您还没有登陆，请先登陆！");
			return retMap;
		}
		httpSession.setAttribute("courseid", courseId);
		retMap.put("error_code", "0");
		retMap.put("error_msg", "");
		return retMap;
	}
	
	@RequestMapping("/course/getCourseDetailByCourseId")
	@ResponseBody
	public CourseDetailModel getCourseDetailByCourseId(HttpSession httpSession) {
		String courseId = (String)httpSession.getAttribute("courseid");
		CourseDetailModel courseDetailModel = null;
		try {
			long id = Long.valueOf(courseId);
			courseDetailModel = courseDetailDao.getCourseDetailByCourseId(id);
		} catch(Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseDetailModel;
	}
}
