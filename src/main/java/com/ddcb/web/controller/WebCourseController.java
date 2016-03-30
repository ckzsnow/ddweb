package com.ddcb.web.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.ProcessBuilder.Redirect;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ddcb.dao.IBannerDao;
import com.ddcb.dao.ICourseDao;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.dao.ILiveClassShareDao;
import com.ddcb.dao.ILiveClassStatisticsDao;
import com.ddcb.dao.IQuestionDao;
import com.ddcb.model.BannerModel;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveClassApplyModel;
import com.ddcb.model.LiveClassStatisticsModel;
import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.LiveCourseShareModel;
import com.ddcb.model.QuestionModel;

@Controller
public class WebCourseController {

	private static final Logger logger = LoggerFactory.getLogger(WebCourseController.class);

	@Autowired
	private ICourseDao courseDao;

	@Autowired
	private IBannerDao bannerDao;

	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@Autowired
	private ILiveClassShareDao liveClassShareDao;
	
	@Autowired
	private ILiveClassStatisticsDao liveClassStatisticsDao;
	
	@Autowired
	private IQuestionDao questionDao;

	@RequestMapping("/course/addCourse")
	@ResponseBody
	public Map<String, String> addCourse(@RequestParam MultipartFile[] files, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		logger.debug("realPath : {}", realPath);
		// realPath = realPath.substring(0, realPath.indexOf("/", 1));
		logger.debug("realPath : {}", realPath);
		/*
		 * String videoPath = realPath + "/files/videos"; String imgPath =
		 * realPath + "/files/imgs";
		 */
		String videoPath = "/home/files/videos";
		String imgPath = "/softs/files/imgs";
		String teacherImage = "";
		String videoImage = "";
		String videoSrc = "";
		int index = 1;
		CourseModel cm = new CourseModel();
		List<CourseDetailModel> cdmList = new ArrayList<>();
		Map<String, String[]> params = request.getParameterMap();
		cm.setCourse_abstract("");
		cm.setCourse_date(Timestamp.valueOf(params.get("course_date")[0]));
		cm.setCourse_time("");
		cm.setCreate_time(new Timestamp(System.currentTimeMillis()));
		cm.setCourseType(Integer.valueOf(params.get("course_type")[0]));
		cm.setName(params.get("name")[0]);
		cm.setCourse_length(params.get("course_length")[0]);
		cm.setTeacher(params.get("teacher")[0]);
		cm.setCourseField(params.get("course_field")[0]);
		cm.setCourseIndustry(params.get("course_industry")[0]);
		cm.setCourseCompetency(params.get("course_competency")[0]);
		cm.setPrice(params.get("course_price")[0]);
		cm.setCourseGrade(params.get("course_grade")[0]);
		String courseStudyCount = params.get("course_study_count")[0];
		String liveClassId = params.get("live_class_id")[0];
		Integer courseStudyCount_ = 0;
		Long liveClassClassId_ = 0L;
		try {
			courseStudyCount_ = Integer.valueOf(courseStudyCount);
			liveClassClassId_ = Long.valueOf(liveClassId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		if(courseStudyCount_ == 0) {
			int max = 1500;
		    int min = 300;
		    Random random = new Random();
		    int x = random.nextInt(max)%(max-min+1) + min;
		    cm.setStudy_people_count(x);
		} else {
			cm.setStudy_people_count(courseStudyCount_);
		}
		cm.setParentId(liveClassClassId_);
		if (files.length != 0) {
			for (MultipartFile file : files) {
				String imgFileUnique = getUniqueIdentifier();
				String imgFileName = imgFileUnique + "_tmp.jpg";
				switch (index) {
				case 1:
					saveFile(file, imgPath, imgFileName, imgFileUnique, 150);
					cm.setImage(imgFileUnique + ".jpg");
					break;
				case 2:
					saveFile(file, imgPath, imgFileName, imgFileUnique, 150);
					teacherImage = imgFileUnique + ".jpg";
					break;
				case 3:
					saveFile(file, imgPath, imgFileName, imgFileUnique, 420);
					videoImage = imgFileUnique + ".jpg";
					break;
				case 4:
					String videoFileName = getUniqueIdentifier() + ".mp4";
					try {
						FileUtils.copyInputStreamToFile(file.getInputStream(), new File(videoPath, videoFileName));
					} catch (IOException e) {
						logger.debug(e.toString());
					}
					videoSrc = videoFileName;
					break;
				}
				index++;
			}
		}
		String course_menu = params.get("course_menu")[0];
		String[] menuList = course_menu.split(";");
		List<String> courseMenuTimeList = new ArrayList<>();
		long courseId = courseDao.addCourse(cm);
		if(cm.getCourseType() == 0) {
			for (String menuStr : menuList) {
				String[] menu = menuStr.split("###DDCB###");
				courseMenuTimeList.add(menu[1]);
				CourseDetailModel cdm = new CourseDetailModel();
				cdm.setCreateTime(new Timestamp(System.currentTimeMillis()));
				cdm.setCrowd(params.get("crowd")[0]);
				cdm.setDetails(params.get("details")[0]);
				cdm.setTeacher_info(params.get("teacher_info")[0]);
				cdm.setTeacher_name(params.get("teacher_name")[0]);
				cdm.setTeacher_position(params.get("teacher_position")[0]);
				cdm.setSubTitle(menu[0]);
				cdm.setTeacher_image(teacherImage);
				cdm.setVideo_image(videoImage);
				cdm.setId(courseId);
				cdmList.add(cdm);
			}
			VideoSplitThread vst = new VideoSplitThread();
			vst.setCdmList(cdmList);
			vst.setCourseDetailDao(courseDetailDao);
			vst.setTimeList(courseMenuTimeList);
			vst.setVideoFileName(videoSrc);
			Thread thread = new Thread(vst);
			thread.start();
		} else {
			CourseDetailModel cdm = new CourseDetailModel();
			cdm.setCreateTime(new Timestamp(System.currentTimeMillis()));
			cdm.setCrowd(params.get("crowd")[0]);
			cdm.setDetails(params.get("details")[0]);
			cdm.setTeacher_info(params.get("teacher_info")[0]);
			cdm.setTeacher_name(params.get("teacher_name")[0]);
			cdm.setTeacher_position(params.get("teacher_position")[0]);
			cdm.setSubTitle("");
			cdm.setTeacher_image(teacherImage);
			cdm.setVideo_image(videoImage);
			cdm.setId(courseId);
			cdm.setVideosrc("http://7xknrw.media1.z0.glb.clouddn.com/" + videoSrc);
			courseDetailDao.addCourseDetail(cdm);
			logger.debug(cdm.toString());
			VideoSplitThread vst = new VideoSplitThread();
			vst.setCourseType("1");
			vst.setVideoFileName(videoSrc);
			Thread thread = new Thread(vst);
			thread.start();
		}
		retMap.put("error_code", "0");
		retMap.put("error_message", "");
		return retMap;
	}

	@RequestMapping("/course/addCourseBanner")
	@ResponseBody
	public Map<String, String> addCourseBanner(@RequestParam MultipartFile[] files, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		logger.debug("realPath : {}", realPath);
		realPath = realPath.substring(0, realPath.indexOf("/", 1));
		logger.debug("realPath : {}", realPath);
		String imgPath = realPath + "/files/bannerimgs";
		int index = 1;
		if (files.length != 0) {
			for (MultipartFile file : files) {
				String imgFileNameTmp = "banner" + String.valueOf(index) + "_tmp.jpg";
				String imgFileName = "banner" + String.valueOf(index) + ".jpg";
				String courseId = request.getParameter("courseId" + index);
				try {
					long courseId_ = Long.valueOf(courseId);
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(imgPath, imgFileName));					
					bannerDao.updateBanner("banner" + String.valueOf(index), imgFileName, courseId_);
				} catch (IOException e) {
					logger.debug(e.toString());
				}
				index++;
			}
		}
		retMap.put("error_code", "0");
		retMap.put("error_message", "");
		return retMap;
	}
	
	/*@RequestMapping(value="/course/addLiveCourseShare", headers = "content-type=multipart/*", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> addLiveCourseShare(@RequestParam MultipartFile[] files, HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		logger.debug("realPath : {}", realPath);
		realPath = realPath.substring(0, realPath.indexOf("/", 1));
		logger.debug("realPath : {}", realPath);
		String imgPath = realPath + "/files/bannerimgs";
		String imgPath = "/files/bannerimgs";
		int index = 1;
		int fileIndex = 0;
		if(files.length != 0) {
			for (int i=0; i<=6; i++) {
				index++;
				String imgFileName = "share" + String.valueOf(index) + ".jpg";
				String courseId = request.getParameter("courseId" + index);
				if(courseId == null || courseId.isEmpty()) continue;
				String title = request.getParameter("title" + index);
				String link = request.getParameter("link" + index);
				try {
					long courseId_ = Long.valueOf(courseId);
					FileUtils.copyInputStreamToFile(files[fileIndex++].getInputStream(), new File(imgPath, imgFileName));
					LiveCourseShareModel lcsm = new LiveCourseShareModel();
					lcsm.setId(courseId_);
					lcsm.setImage(imgFileName);
					lcsm.setLink(link);
					lcsm.setTitle(title);
					//lcsm.setWeekDay(index);
					liveClassShareDao.updateLiveClassShare(lcsm);
				} catch (IOException e) {
					logger.debug(e.toString());
				}
			}
		}
		retMap.put("error_code", "0");
		retMap.put("error_message", "");
		return retMap;
	}*/
	
	@RequestMapping("/course/updateLiveCourseShare")
	@ResponseBody
	public Map<String, String> updateLiveCourseShare(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String courseId = request.getParameter("courseId");
		String shareImage = request.getParameter("shareImage");
		String shareTitle = request.getParameter("shareTitle");
		String shareLink = request.getParameter("shareLink");
		String weekDay = request.getParameter("weekDay");
		try {
			long courseId_ = Long.valueOf(courseId);
			LiveCourseShareModel lcsm = liveClassShareDao.getLiveClassShareByCourseId(courseId_);
			if(lcsm == null) {
				lcsm = new LiveCourseShareModel();
				lcsm.setCourseId(courseId_);
				lcsm.setCourseName(shareTitle);
				lcsm.setId(courseId_);
				lcsm.setImage(shareImage);
				lcsm.setLink(shareLink);
				lcsm.setTitle(shareTitle);
				lcsm.setWeekDay(weekDay);
				liveClassShareDao.addLiveClassShare(lcsm);
			} else {
				liveClassShareDao.updateLiveClassShare(shareLink, weekDay);
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		retMap.put("error_code", "0");
		retMap.put("error_message", "");
		return retMap;
	}
	
	@RequestMapping("/course/getAllUnfinishedLiveClass")
	@ResponseBody
	public List<LiveCourseShareModel> getAllUnfinishedLiveClass() {
		return courseDao.getLiveClassShare();
	}

	@RequestMapping("/course/getCourseBanner")
	@ResponseBody
	public Map<String, String> getCourseBanner() {
		Map<String, String> retMap = new HashMap<>();
		List<BannerModel> bannerList = bannerDao.getAllBanner();
		if (bannerList == null || bannerList.size() == 0)
			return retMap;
		for (BannerModel bm : bannerList) {
			retMap.put(bm.getId(), bm.getFile_name());
		}
		return retMap;
	}
	
	@RequestMapping("/course/getBannerCourse")
	@ResponseBody
	public List<CourseModel> getBannerCourse() {
		List<CourseModel> list = bannerDao.getAllBannerCourse();
		return list;
	}
	
	@RequestMapping("/course/getLiveCourseShare")
	@ResponseBody
	public List<LiveCourseShareModel> getLiveCourseShare() {
		List<LiveCourseShareModel> list = liveClassShareDao.getAllLiveClassShare();
		return list;
	}

	private void saveFile(MultipartFile file, String path, String fileName, String uniqueName, int width) {
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path, fileName));
			File originalImage = new File(path, fileName);
			byte[] bytes = ImageSnapshot.resize(ImageIO.read(originalImage), width, 0.1f, false);
			FileOutputStream out = new FileOutputStream(new File(path, uniqueName + ".jpg"));
			out.write(bytes);
			out.close();
			File tempFile = new File(path, fileName);
			if (tempFile.exists() && tempFile.isFile()) {
				tempFile.delete();
			}
		} catch (IOException e) {
			logger.debug(e.toString());
		}
	}

	private String getUniqueIdentifier() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
		return uuid;
	}

	public static void main(String[] args) throws InterruptedException, IOException, ParseException {
		String path = "/home/files/videos/test.mp4";
		String startTime = "00:00:00";
		List<String> timeList = new ArrayList<>();
		timeList.add("00:01:00");
		timeList.add("00:02:10");
		timeList.add("00:03:03");
		int count = 1;
		for (String time : timeList) {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = df.parse("2016-03-01 " + startTime);
			Date now = df.parse("2016-03-01 " + time);
			long l = now.getTime() - date.getTime();
			long day = l / (24 * 60 * 60 * 1000);
			long hour = (l / (60 * 60 * 1000) - day * 24);
			long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
			long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
			String durationTime = hour + ":" + min + ":" + s;
			List<String> command = new ArrayList<>();
			command.add("D:\\home\\ffmpeg\\ffmpeg");
			command.add("-ss");
			command.add(startTime);
			command.add("-i");
			command.add("D:\\home\\files\\videos\\test.mp4");
			command.add("-t");
			command.add(durationTime);
			command.add("D:\\home\\files\\videos\\output_file" + count + ".mp4");
			ProcessBuilder pb = new ProcessBuilder(command);
			pb.redirectOutput(Redirect.INHERIT);
			pb.redirectError(Redirect.INHERIT);
			Process p = pb.start();
			p.waitFor();
			count++;
			startTime = time;
		}
	}
	
	@RequestMapping("/course/updateCourseStudyPeopleCount")
	@ResponseBody
	public List<CourseModel> updateCourseStudyPeopleCount() {
		//courseDao.updateCourseStudyPeopleCount();
		return null;
	}
	
	@RequestMapping("/statistics/getPerMinuteStatistics")
	@ResponseBody
	public List<LiveClassStatisticsModel> getPerMinuteStatistics(HttpServletRequest request) {
		String timeDuration = request.getParameter("duration");
		String beginTime = request.getParameter("begin_time");
		String endTime = request.getParameter("end_time");
		List<LiveClassStatisticsModel> list = liveClassStatisticsDao.getStatisticsByTimeRange(beginTime, endTime);
		return null;
	}
	
	@RequestMapping("/statistics/addPerMinuteStatistics")
	@ResponseBody
	public Map<String, String> addPerMinuteStatistics(HttpServletRequest request) {
		String openId = request.getParameter("open_id");
		String courseId = request.getParameter("course_id");
		String playStatus = request.getParameter("play_status");
		logger.debug("addPerMinuteStatistics, openid:{}, courseId:{}, playStatus:{}", openId, courseId, playStatus);
		long courseId_ = 0;
		try {
			courseId_ = Long.valueOf(courseId);
			LiveClassStatisticsModel lcsm = new LiveClassStatisticsModel();
			lcsm.setCourse_id(courseId_);
			lcsm.setCreate_time(new Timestamp(System.currentTimeMillis()));
			lcsm.setOpen_id(openId);
			lcsm.setPlay_status(playStatus);
			liveClassStatisticsDao.addLiveClassStatistics(lcsm);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return new HashMap<>();
	}
	
	@RequestMapping("/webSearchUserQuestion")
	@ResponseBody
	public List<QuestionModel> webSearchUserQuestion(HttpServletRequest request) {
		List<QuestionModel> retList = null;
		String courseId = request.getParameter("course_id");
		long courseId_ = 0;
		try {
			courseId_ = Long.valueOf(courseId);
			retList = questionDao.getAllQuestionByCourseId(courseId_, 1, 100000);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return retList;
	}
	
	@RequestMapping("/deleteUserQuestion")
	@ResponseBody
	public Map<String, String> deleteUserQuestion(HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String questionId = request.getParameter("id");
		long questionId_ = 0;
		try {
			questionId_ = Long.valueOf(questionId);
			if(questionDao.deleteUserQuestion(questionId_)) {
				retMap.put("error_code", "0");
				retMap.put("error_msg", "");
			}
		} catch(Exception ex) {
			logger.error(ex.toString());
			retMap.put("error_code", "1");
			retMap.put("error_msg", "操作数据库失败！");
		}
		return retMap;
	}
	
	@RequestMapping("/getAllSelectLiveCourse")
	@ResponseBody
	public List<LiveClassApplyModel> getAllSelectLiveCourse(HttpServletRequest request) {		
		return courseDao.getAllSelectLiveCourse();
	}
}
