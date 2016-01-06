package com.ddcb.web.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ddcb.dao.IBannerDao;
import com.ddcb.dao.ICourseDao;
import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.model.BannerModel;
import com.ddcb.model.CourseDetailModel;
import com.ddcb.model.CourseModel;

@Controller
public class WebCourseController {

	private static final Logger logger = LoggerFactory
			.getLogger(WebCourseController.class);
	
	@Autowired
	private ICourseDao courseDao;
	
	@Autowired
	private IBannerDao bannerDao;
	
	@Autowired
	private ICourseDetailDao courseDetailDao;
	
	@RequestMapping("/course/addCourse")
	@ResponseBody
	public Map<String, String> addCourse(@RequestParam MultipartFile[] files,
			HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		logger.debug("realPath : {}", realPath);
		realPath = realPath.substring(0, realPath.indexOf("/", 1));
		logger.debug("realPath : {}", realPath);
		//String videoPath = realPath + "/files/videos";
		String imgPath = realPath + "/files/imgs";
		int index = 1;
		CourseModel cm = new CourseModel();
		CourseDetailModel cdm = new CourseDetailModel();
		Map<String, String[]> params = request.getParameterMap();
		cm.setCourse_abstract(params.get("course_abstract")[0]);
		cm.setCourse_date(Timestamp.valueOf(params.get("course_date")[0]));
		cm.setCourse_time("");
		cm.setCreate_time(new Timestamp(System.currentTimeMillis()));
		cm.setCourseType(Integer.valueOf(params.get("course_type")[0]));
		cm.setName(params.get("name")[0]);
		cm.setCourse_length(params.get("course_length")[0]);
		cm.setTeacher(params.get("teacher")[0]);
		cdm.setCreateTime(new Timestamp(System.currentTimeMillis()));
		cdm.setCrowd(params.get("crowd")[0]);
		cdm.setDetails(params.get("details")[0]);
		cdm.setTeacher_info(params.get("teacher_info")[0]);
		cdm.setTeacher_name(params.get("teacher_position")[0]);
		cdm.setTeacher_position(params.get("course_abstract")[0]);
		cdm.setVideosrc(params.get("video_src")[0]);
		if(files.length != 0) {
			for(MultipartFile file:files) {
				//String videoFileName = getUniqueIdentifier() + ".mp4";
				String imgFileName = getUniqueIdentifier() + ".jpg";
				switch(index) {
				case 1:
					saveFile(file, imgPath, imgFileName);
					cm.setImage(imgFileName);
					break;
				case 2:
					saveFile(file, imgPath, imgFileName);
					cdm.setTeacher_image(imgFileName);
					break;
				case 3:
					saveFile(file, imgPath, imgFileName);
					cdm.setVideo_image(imgFileName);
					break;
				/*case 4:
					saveFile(file, videoPath, videoFileName);
					cdm.setVideosrc(videoFileName);
					break;*/
				}
				index++;
			}
			long courseId = courseDao.addCourse(cm);
			cdm.setId(courseId);
			courseDetailDao.addCourseDetail(cdm);
		}
		retMap.put("error_code", "0");
		retMap.put("error_message", "");
		return retMap;
	}
	
	@RequestMapping("/course/addCourseBanner")
	@ResponseBody
	public Map<String, String> addCourseBanner(@RequestParam MultipartFile[] files,
			HttpServletRequest request) {
		Map<String, String> retMap = new HashMap<>();
		String realPath = request.getSession().getServletContext().getRealPath("/WEB-INF");
		logger.debug("realPath : {}", realPath);
		realPath = realPath.substring(0, realPath.indexOf("/", 1));
		logger.debug("realPath : {}", realPath);
		String imgPath = realPath + "/files/bannerimgs";
		int index = 1;
		if(files.length != 0) {
			for(MultipartFile file:files) {
				String imgFileName = "banner" + String.valueOf(index) + ".jpg";
				try {
					FileUtils.copyInputStreamToFile(file.getInputStream(),
							new File(imgPath, imgFileName));
					bannerDao.updateBanner("banner" + String.valueOf(index), imgFileName);
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
	
	@RequestMapping("/course/getCourseBanner")
	@ResponseBody
	public Map<String, String> getCourseBanner() {
		Map<String, String> retMap = new HashMap<>();
		List<BannerModel> bannerList = bannerDao.getAllBanner();
		if(bannerList == null || bannerList.size() == 0) return retMap;
		for(BannerModel bm : bannerList) {
			retMap.put(bm.getId(), bm.getFile_name());
		}
		return retMap;
	}
	
	private void saveFile(MultipartFile file, String path, String fileName) {
		try {
			FileUtils.copyInputStreamToFile(file.getInputStream(),
					new File(path, fileName));
		} catch (IOException e) {
			logger.debug(e.toString());
		}
	}
	
	private String getUniqueIdentifier() {
		 String uuid = UUID.randomUUID().toString();
		 uuid =  uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	     return uuid;  
	}
}
