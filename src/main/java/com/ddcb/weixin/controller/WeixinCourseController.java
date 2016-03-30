package com.ddcb.weixin.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
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

import com.ddcb.dao.IClickLikeDao;
import com.ddcb.dao.ICourseDao;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.dao.IQuestionDao;
import com.ddcb.dao.IUserCollectionDao;
import com.ddcb.dao.IUserCourseDao;
import com.ddcb.dao.IUserForwardDao;
import com.ddcb.dao.IUserLiveCoursePayDao;
import com.ddcb.dao.IUserStudyRecordDao;
import com.ddcb.model.ClickLikeModel;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveClassApplyModel;
import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.QuestionModel;
import com.ddcb.model.UserCollectionModel;
import com.ddcb.model.UserCourseModel;
import com.ddcb.model.UserForwardModel;
import com.ddcb.model.UserStudyRecordModel;

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
	private IUserCollectionDao userCollectionDao;
	
	@Autowired
	private IUserStudyRecordDao userStudyRecordDao;
	
	@Autowired
	private IQuestionDao questionDao;
	
	@Autowired
	private IUserLiveCoursePayDao userLiveCoursePayDao;
	
	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@Autowired
	private IClickLikeDao clickLikeDao;
	
	@RequestMapping("/course/getAllCourse")
	@ResponseBody
	public List<CourseModel> getAllCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllCourse();
		return courseList;
	}
	
	@RequestMapping("/course/getAllLiveClassApply")
	@ResponseBody
	public List<LiveClassApplyModel> getAllLiveClassApply(HttpServletRequest request) {
		List<LiveClassApplyModel> courseList = courseDao.getAllLiveClassApply();
		return courseList;
	}
	
	@RequestMapping("/course/getAllOpenCourse")
	@ResponseBody
	public List<CourseModel> getAllOpenCourse(HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllOpenCourse();
		return courseList;
	}
	
	@RequestMapping("/course/getAllOpenCourseByPage")
	@ResponseBody
	public List<CourseModel> getAllOpenCourseByPage(HttpServletRequest request) {
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<CourseModel> courseList = courseDao.getAllOpenCourse(page_, count_);
		return courseList;
	}
	
	@RequestMapping("/course/getAllOpenCourseByCondition")
	@ResponseBody
	public List<CourseModel> getAllOpenCourseByCondition(HttpServletRequest request) {
		String field = request.getParameter("field");
		String industry = request.getParameter("industry");
		String competency = request.getParameter("competency");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<CourseModel> courseList = courseDao.getAllOpenCourseByCondition(page_, count_, field, industry, competency);
		return courseList;
	}
	
	@RequestMapping("/course/getAllFinishedLiveCourseByPage")
	@ResponseBody
	public List<LiveCourseModel> getAllFinishedLiveCourseByPage(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<LiveCourseModel> courseList = courseDao.getAllFinishedLiveCourse(page_, count_, userId);
		return courseList;
	}
	
	@RequestMapping("/course/getAllGoingLiveCourseByPage")
	@ResponseBody
	public List<LiveCourseModel> getAllGoingLiveCourseByPage(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<LiveCourseModel> courseList = courseDao.getAllLiveCourse(page_, count_, userId);
		return courseList;
	}
	
	@RequestMapping("/course/getAllLiveCourseByCondition")
	@ResponseBody
	public List<LiveCourseModel> getAllLiveCourseByCondition(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String field = request.getParameter("field");
		String industry = request.getParameter("industry");
		String competency = request.getParameter("competency");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<LiveCourseModel> courseList = courseDao.getAllLiveCourseByCondition(page_, count_, field, industry, competency, userId);
		return courseList;
	}
	
	@RequestMapping("/course/getAllRecentCourse")
	@ResponseBody
	public Map<String, Object> getAllRecentCourse(HttpSession httpSession, HttpServletRequest request) {
		/*String userId = (String)httpSession.getAttribute("openid");
		List<CourseModel> courseList = courseDao.getAllRecentCourse();
		Map<String, Object> retMap = new HashMap<>();
		for(CourseModel cm : courseList) {
			UserCourseModel ucm = userCourseDao.getUserCourseByUserIdAndCourseId(userId, cm.getId(), 1);
			if(ucm == null) {
				cm.setPay_status(0);
			} else {
				cm.setPay_status(1);
			}
		}
		retMap.put("hasLogin", "1");
		retMap.put("data", courseList);
		return retMap;*/
		return null;
	}
	
	@RequestMapping("/course/getUserCourse")
	@ResponseBody
	public List<UserCourseModel> getUserCourse(HttpServletRequest request) {
		List<UserCourseModel> courseList = userCourseDao.getAllUserCourseByHasUpload();
		return courseList;
	}
	
	@RequestMapping("/course/getUserPayedCourse")
	@ResponseBody
	public List<CourseModel> getUserPayedCourse(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		logger.debug("getUserPayedCourse userId : {}", userId);
		List<CourseModel> courseList = courseDao.getAllUserPayedCourseRecentCourse();
		logger.debug("getUserPayedCourse courseList : {}", courseList.size());
		List<CourseModel> retList = new ArrayList<>();
		for(CourseModel cm : courseList) {
			UserCourseModel ucm = userCourseDao.getUserCourseByUserIdAndCourseId(userId, cm.getId(), 1);
			logger.debug("getUserPayedCourse ucm : {}", ucm != null);
			if(ucm != null) {
				retList.add(cm);
			}
		}
		return retList;
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
	
	@RequestMapping("/course/playPayedLiveCourse")
	public String playPayedLiveCourse(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		httpSession.setAttribute("courseid", courseId);
		return "redirect:/view/weixinview/play_live_class.html";
	}
	
	@RequestMapping("/course/clickLiveCourse")
	public String clickLiveCourse(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		String userId = (String)httpSession.getAttribute("userId");
		if(userId == null || userId.isEmpty()) {
			return "redirect:/view/weixinview/weixin_login.html";
		} else {
			return "redirect:/view/weixinview/play_live_class.html";
		}
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
	
	@RequestMapping("/course/userCollectionCourse")
	@ResponseBody
	public Map<String, String> userCollectionCourse(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String courseId = request.getParameter("course_id");
		String userId = (String) httpSession.getAttribute("openid");
		long courseId_ = 0;
		if(userId == null) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "无法获取您的身份信息，请退出重新进入该页面！");
			return retMap;
		}
		try {
			courseId_ = Long.valueOf(courseId);
			if(!userCollectionDao.isFinishCollection(userId, courseId_)) {
				UserCollectionModel ucm = new UserCollectionModel();
				ucm.setCourse_id(courseId_);
				ucm.setUser_id(userId);
				ucm.setCreate_time(new Timestamp(System.currentTimeMillis()));
				userCollectionDao.addUserCollection(ucm);
			}
			retMap.put("error_code", "0");
			retMap.put("error_msg", "");
		} catch(Exception ex) {
			logger.error(ex.toString());
			retMap.put("error_code", "2");
			retMap.put("error_msg", "获取课程编号信息错误，请退出重新进入该页面！");
		}
		return retMap;
	}
	
	@RequestMapping("/course/getCourseDetailByCourseId")
	@ResponseBody
	public CourseDetailModel getCourseDetailByCourseId(HttpSession httpSession) {
		String courseId = (String)httpSession.getAttribute("courseid");
		CourseDetailModel courseDetailModel = null;
		/*try {
			long id = Long.valueOf(courseId);
			courseDetailModel = courseDetailDao.getCourseDetailByCourseId(id);
			CourseModel cm = courseDao.getCourseByCourseId(id);
			courseDetailModel.setCourse_date(cm.getCourse_date_readable());
			courseDetailModel.setCourse_length(cm.getCourse_length());
			courseDetailModel.setName(cm.getName());
		} catch(Exception e) {
			logger.debug("exception : {}", e.toString());
		}*/
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
	
	@RequestMapping("/course/addStudyRecord")
	@ResponseBody
	public List<CourseModel> addStudyRecord(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("courseId");
		try {
			long courseId_ = Long.valueOf(courseId);
			if(userStudyRecordDao.isFinishAddUserStudyRecord(userId, courseId_)) {
				userStudyRecordDao.updateUserStudyRecord(userId, courseId_);
			} else {
				UserStudyRecordModel usrm = new UserStudyRecordModel();
				usrm.setCourse_id(courseId_);
				usrm.setUser_id(userId);
				usrm.setCreate_time(new Timestamp(System.currentTimeMillis()));
				usrm.setUpdate_time(new Timestamp(System.currentTimeMillis()));
				userStudyRecordDao.addUserStudyRecord(usrm);
				courseDao.updateCourseStudyPeopleCount(courseId_);
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
	
	@RequestMapping("/course/delStudyRecord")
	@ResponseBody
	public List<CourseModel> delStudyRecord(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("courseId");
		try {
			long courseId_ = Long.valueOf(courseId);
			userStudyRecordDao.delUserStudyRecord(userId, courseId_);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
	
	@RequestMapping("/course/delUserCollection")
	@ResponseBody
	public List<CourseModel> delUserCollection(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("courseId");
		try {
			long courseId_ = Long.valueOf(courseId);
			userCollectionDao.delUserCollection(userId, courseId_);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
	
	@RequestMapping("/course/getLiveCourseByCourseId")
	@ResponseBody
	public List<CourseDetailModel> getLiveCourseByCourseId(HttpSession httpSession, HttpServletRequest request) {
		String courseId = request.getParameter("course_id");
		List<CourseDetailModel> list = null;
		try {
			long courseId_ = Long.valueOf(courseId);
			list = courseDetailDao.getCourseDetailByCourseId(courseId_);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return list;
	}
	
	@RequestMapping("/course/getOpenCourseByCondition")
	@ResponseBody
	public List<CourseModel> getOpenCourseByCondition(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String field = request.getParameter("selectField");
		String industry = request.getParameter("selectIndustry");
		String competency = request.getParameter("selectCompetency");
		String key = request.getParameter("selectKey");
		String type = request.getParameter("latestOrHotest");
		String grade = request.getParameter("selectGrade");
		String page = request.getParameter("page");
		String count = request.getParameter("countPerPage");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<CourseModel> courseList = courseDao.getOpenCourseByCondition(userId, page_, count_, type, field, industry, competency, grade, key);
		return courseList;
	}
	
	@RequestMapping("/course/uploadUserShare")
	@ResponseBody
	public List<CourseModel> uploadUserShare(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("courseId");
		try {
			long courseId_ = Long.valueOf(courseId);
			UserForwardModel ufm = userForwardDao.getUserForwardByUserIdAndCourseId(userId, courseId_);
			if(ufm == null) {
				ufm = new UserForwardModel();
				ufm.setCourse_id(courseId_);
				ufm.setCreate_time(new Timestamp(System.currentTimeMillis()));
				ufm.setScreenshot("1");
				ufm.setUser_id(userId);
				userForwardDao.addUserForward(ufm);
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
	
	@RequestMapping("/weixin/getAllLiveClass")
	@ResponseBody
	public List<CourseModel> getAllLiveClass(HttpSession httpSession, HttpServletRequest request) {
		List<CourseModel> courseList = courseDao.getAllLiveClass();
		return courseList;
	}
	
	@RequestMapping("/getAllCourseQuestions")
	@ResponseBody
	public List<QuestionModel> getAllCourseQuestions(HttpSession httpSession, HttpServletRequest request) {
		String userId = (String)httpSession.getAttribute("openid");
		String courseId = request.getParameter("course_id");
		String page = request.getParameter("page");
		String count = request.getParameter("count");
		int page_ = Integer.valueOf(page);
		int count_ = Integer.valueOf(count);
		List<QuestionModel> courseList = null;
		try {
			long courseId_ = Long.valueOf(courseId);
			courseList = questionDao.getAllQuestionByCourseId(userId, courseId_, page_, count_);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return courseList;
	}
	
	@RequestMapping("/userPublishQuestion")
	@ResponseBody
	public Map<String, String> userPublishQuestion(HttpSession httpSession, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String courseId = request.getParameter("course_id");
		String userId = (String)httpSession.getAttribute("openid");
		String question = request.getParameter("question");
		long pId = -1;
		boolean addSuccess = false;
		try {
			long courseId_ = Long.valueOf(courseId);
			QuestionModel qm = new QuestionModel();
			qm.setClick_like(0);
			qm.setCourse_id(courseId_);
			qm.setCreate_time(new Timestamp(System.currentTimeMillis()));
			qm.setQuestion(question);
			qm.setOpen_id(userId);
			pId = questionDao.addQuestion(qm);
			addSuccess = pId != -1;
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		if(addSuccess) {
			retMap.put("error_code", "0");
			retMap.put("error_msg", String.valueOf(pId));
		} else {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "保存至数据库失败！");
		}
		return retMap;
	}
	
	@RequestMapping("/userClickLikeQuestion")
	@ResponseBody
	public Map<String, String> userClickLikeQuestion(HttpSession httpSession, HttpServletRequest request) {
		String questionId = request.getParameter("id");
		String userId = (String)httpSession.getAttribute("openid");
		String like = request.getParameter("like");
		try {
			long questionId_ = Long.valueOf(questionId);
			int like_ = Integer.valueOf(like);
			if(like_ == 1) {
				questionDao.updateClickLike(questionId_, 1);
			} else if(like_ == 0) {
				questionDao.updateClickLike(questionId_, 0);
			}
			ClickLikeModel clm = clickLikeDao.getClickLike(questionId_, userId);
			if(clm == null) {
				clm = new ClickLikeModel();
				clm.setCreate_time(new Timestamp(System.currentTimeMillis()));
				clm.setClick_like(like_);
				clm.setOpen_id(userId);
				clm.setQuestion_id(questionId_);
				clickLikeDao.addClickLike(clm);
			} else {
				clickLikeDao.updateClickLike(questionId_, userId, like_);
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return null;
	}
	
	@RequestMapping("/course/getAllOpenClassStudyCount")
	@ResponseBody
	public List<LiveClassApplyModel> getAllOpenClassStudyCount(HttpServletRequest request) {
		List<LiveClassApplyModel> courseList = courseDao.getAllOpenClassStudyCount();
		return courseList;
	}
	
	@RequestMapping("/course/updateStudyCount")
	@ResponseBody
	public Map<String, String> updateStudyCount(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String courseId = request.getParameter("courseId");
		String count = request.getParameter("count");
		try {
			long courseId_ = Long.valueOf(courseId);
			int count_ = Integer.valueOf(count);
			courseDao.updateCourseStudyPeopleCountForCount(courseId_, count_);
			retMap.put("error_code", "0");
			retMap.put("error_msg", "");
		} catch(Exception ex) {
			retMap.put("error_code", "1");
			retMap.put("error_msg", "参数错误，请检查！");
			logger.error(ex.toString());
		}
		return retMap;
	}
	
}
