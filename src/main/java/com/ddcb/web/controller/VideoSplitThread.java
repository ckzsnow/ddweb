package com.ddcb.web.controller;

import java.io.File;
import java.io.IOException;
import java.lang.ProcessBuilder.Redirect;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ddcb.dao.ICourseDetailDao;
import com.ddcb.model.CourseDetailModel;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;

public class VideoSplitThread implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(VideoSplitThread.class);

	private List<String> timeList;

	private List<CourseDetailModel> cdmList;

	private ICourseDetailDao courseDetailDao;

	private String videoFileName;
	
	private String courseType;

	public String getCourseType() {
		return courseType;
	}

	public void setCourseType(String courseType) {
		this.courseType = courseType;
	}

	public String getVideoFileName() {
		return videoFileName;
	}

	public void setVideoFileName(String videoFileName) {
		this.videoFileName = videoFileName;
	}

	public List<String> getTimeList() {
		return timeList;
	}

	public void setTimeList(List<String> timeList) {
		this.timeList = timeList;
	}

	public List<CourseDetailModel> getCdmList() {
		return cdmList;
	}

	public void setCdmList(List<CourseDetailModel> cmdList) {
		this.cdmList = cmdList;
	}

	public ICourseDetailDao getCourseDetailDao() {
		return courseDetailDao;
	}

	public void setCourseDetailDao(ICourseDetailDao courseDetailDao) {
		this.courseDetailDao = courseDetailDao;
	}

	@Override
	public void run() {
		if(courseType != null && ("1").equals(courseType)) {
			uploadFile(videoFileName);
		} else {
			String startTime = "00:00:00";
			int index = 0;
			for (String time : timeList) {
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date, now;
				try {
					date = df.parse("2016-03-01 " + startTime);
					now = df.parse("2016-03-01 " + time);
					long l = now.getTime() - date.getTime();
					long day = l / (24 * 60 * 60 * 1000);
					long hour = (l / (60 * 60 * 1000) - day * 24);
					long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
					long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
					String durationTime = (hour<=9?"0"+hour:hour) + ":" + (min<=9?"0"+min:min) + ":" + (s<=9?"0"+s:s);
					String videoName = "";
					List<String> command = new ArrayList<>();
					command.add("/home/ffmpeg/./ffmpeg");
					command.add("-ss");
					command.add(startTime);
					command.add("-i");
					command.add("/home/files/videos/" + videoFileName);
					command.add("-t");
					command.add(durationTime);
					videoName = getUniqueIdentifier() + ".mp4";
					command.add("/home/files/videos/" + videoName);
					ProcessBuilder pb = new ProcessBuilder(command);
					pb.redirectOutput(Redirect.INHERIT);
					pb.redirectError(Redirect.INHERIT);
					Process p = pb.start();
					p.waitFor();
					startTime = time;
					cdmList.get(index).setVideosrc("http://7xknrw.media1.z0.glb.clouddn.com/" + videoName);
					cdmList.get(index).setCourse_time_length(durationTime);
					courseDetailDao.addCourseDetail(cdmList.get(index));
					uploadFile(videoName);
					index++;
					File file = new File("/home/files/videos/" + videoName);
					if (file.exists() && file.isFile()) {
						file.delete();
					}
				} catch (ParseException e) {
					logger.error(e.toString());
				} catch (IOException e) {
					logger.error(e.toString());
				} catch (InterruptedException e) {
					logger.error(e.toString());
				}
			}
			File file = new File("/home/files/videos/" + videoFileName);
			if (file.exists() && file.isFile()) {
				file.delete();
			}
		}
	}

	private String getUniqueIdentifier() {
		String uuid = UUID.randomUUID().toString();
		uuid = uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23)
				+ uuid.substring(24);
		return uuid;
	}

	private void uploadFile(String fileName) {
		Auth auth = Auth.create("0R6T7IB2SlM1aIXXWT8dxYDIOY1aVhUd_tE4ItvQ", "8FIkA5vxhuRfXR6KgDqC2YEstMXtLPmy-2XT9TOT");
		String token = auth.uploadToken("diandou");
		File file = new File("/home/files/videos/" + fileName);
		UploadManager uploadManager = new UploadManager();
		try {
			uploadManager.put(file, fileName, token);
		} catch (QiniuException e) {
			logger.error(e.toString());
		}
	}
}
