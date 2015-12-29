package com.ddcb.weixin.controller;

import java.util.List;

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
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;

@Controller
public class CourseController {

	private static final Logger logger = LoggerFactory
			.getLogger(CourseController.class);
	
	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@RequestMapping("/course/getAllCourse")
	@ResponseBody
	public List<CourseModel> getAllCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllCourse();
		return courseList;
	}
	
	@RequestMapping("/course/playCourse")
	public String getPlayCourseHtml(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		httpSession.setAttribute("courseid", courseId);
		return "redirect:/view/weixinview/play_class.html";
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
