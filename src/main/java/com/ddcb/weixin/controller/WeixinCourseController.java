package com.ddcb.weixin.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ddcb.dao.ICourseDao;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.dao.IUserCourseDao;
import com.ddcb.dao.IUserForwardDao;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.UserCourseModel;
import com.ddcb.model.UserForwardModel;

@Controller
public class WeixinCourseController {

	private static final Logger logger = LoggerFactory
			.getLogger(WeixinCourseController.class);
	
	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private IUserCourseDao userCourseDao;
	
	@Autowired
	private IUserForwardDao userForwardDao;
	
	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@RequestMapping("/course/getAllCourse")
	@ResponseBody
	public List<CourseModel> getAllCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllCourse();
		return courseList;
	}
	
	@RequestMapping("/course/getAllOpenCourse")
	@ResponseBody
	public List<CourseModel> getAllOpenCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllOpenCourse();
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
	
	@RequestMapping("/course/getUserCourse")
	@ResponseBody
	public List<UserCourseModel> getUserCourse(HttpServletRequest request) {
		List<UserCourseModel> courseList = userCourseDao.getAllUserCourseByHasUpload();
		return courseList;
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
			CourseModel cm = courseDao.getCourseByCourseId(id);
			courseDetailModel.setCourse_date(cm.getCourse_date_readable());
			courseDetailModel.setCourse_length(cm.getCourse_length());
		} catch(Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseDetailModel;
	}
	
	@RequestMapping("/weixin/uploadShareImage")
	@ResponseBody
	public Map<String, String> uploadShareImage(@RequestParam MultipartFile files, HttpSession httpSession,
			HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String userId = (String)httpSession.getAttribute("user_id");
		String courseId = (String)httpSession.getAttribute("courseid");
		if(userId == null || userId.isEmpty()) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "您还没有登陆，请先登陆！");
			return retMap;
		}
		if(courseId == null || courseId.isEmpty()) {
			retMap.put("error_code", "2");
			retMap.put("error_msg", "您选择的课程信息已经丢失，请重现在近期课程中选择！");
			return retMap;
		}
		long courseId_ = 0;
		try {
			courseId_ = Long.valueOf(courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		if(courseId_ == 0) {
			retMap.put("error_code", "3");
			retMap.put("error_msg", "您选择的课程信息错误，请重现在近期课程中选择！");
			return retMap;
		}
		if(files == null || files.isEmpty()) {
			retMap.put("error_code", "4");
			retMap.put("error_msg", "您上传的文件为空文件，请检查！");
			return retMap;
		}
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		realPath = realPath.substring(0, realPath.indexOf("/", 1));
		String imgPath = realPath + "/files/forwardimage";
		//String imgPath = "/files/forwardimage";
		String imgFileName = getUniqueIdentifier() + ".jpg";
		try {
			FileUtils.copyInputStreamToFile(files.getInputStream(),
					new File(imgPath, imgFileName));
			UserForwardModel ufm = userForwardDao.getUserForwardByUserIdAndCourseId(userId, courseId_);
			if(ufm != null) {
				ufm.setScreenshot(imgFileName);
				if(userForwardDao.updateScreenShot(ufm)) {
					retMap.put("error_code", "0");
					retMap.put("error_msg", "");
					writeSelectCourseInfoToDB(userId, courseId_);
				} else {
					retMap.put("error_code", "6");
					retMap.put("error_msg", "上传文件失败，没有成功写入数据库，请重试！");
				}
			} else {
				ufm = new UserForwardModel();
				ufm.setCourse_id(courseId_);
				ufm.setScreenshot(imgFileName);
				ufm.setUser_id(userId);
				ufm.setCreate_time(new Timestamp(System.currentTimeMillis()));
				if(userForwardDao.addUserForward(ufm)) {
					retMap.put("error_code", "0");
					retMap.put("error_msg", "");
					writeSelectCourseInfoToDB(userId, courseId_);
				} else {
					retMap.put("error_code", "6");
					retMap.put("error_msg", "上传文件失败，没有成功写入数据库，请重试！");
				}
			}
			return retMap;
		} catch (IOException e) {
			logger.error(e.toString());
			retMap.put("error_code", "7");
			retMap.put("error_msg", "上传文件失败，请您稍后重试！");
			return retMap;
		}
	}
	
	@RequestMapping("/course/auditUserCourse")
	@ResponseBody
	public Map<String, String> auditUserCourse(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String auditDataList = request.getParameter("list");
		String auditStatus = request.getParameter("auditStatus");
		String[] auditData = auditDataList.split(";");
		for(String data : auditData) {
			int pos = data.indexOf("_");
			String userId = data.substring(0, pos);
			String courseId = data.substring(pos + 1);
			long courseId_ = 0;
			try {
				courseId_ = Long.valueOf(courseId);
			} catch(Exception ex) {
				logger.error(ex.toString());
				continue;
			}
			if(("0").equals(auditStatus)) {
				userCourseDao.updateForwardStatus(userId, courseId_, 1);
			} else if(("1").equals(auditStatus)) {
				userCourseDao.updateForwardStatus(userId, courseId_, 2);
			}
		}
		retMap.put("error_code", "0");
		retMap.put("error_msg", "");
		return retMap;
	}
	
	private String getUniqueIdentifier() {
		 String uuid = UUID.randomUUID().toString();
		 uuid =  uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	     return uuid;  
	}
	
	private void writeSelectCourseInfoToDB(String userId, long courseId) {
		 UserCourseModel ucm = new UserCourseModel();
		 ucm.setCourse_id(courseId);
		 ucm.setUser_id(userId);
		 ucm.setPay_status(0);
		 ucm.setForward_status(0);
		 ucm.setCreate_time(new Timestamp(System.currentTimeMillis()));
		 userCourseDao.addUserCourseModel(ucm);
	}
}