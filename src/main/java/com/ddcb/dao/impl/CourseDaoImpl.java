package com.ddcb.dao.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import com.ddcb.dao.ICourseDao;
import com.ddcb.mapper.CourseMapper;
import com.ddcb.mapper.LiveClassApplyMapper;
import com.ddcb.mapper.LiveClassShareMapper;
import com.ddcb.mapper.LiveCourseMapper;
import com.ddcb.mapper.SelectCourseMapper;
import com.ddcb.model.CourseModel;
import com.ddcb.model.LiveClassApplyModel;
import com.ddcb.model.LiveCourseModel;
import com.ddcb.model.LiveCourseShareModel;
import com.ddcb.model.SelectCourseModel;

@Repository("courseDao")
public class CourseDaoImpl implements ICourseDao {

	private static final Logger logger = LoggerFactory
			.getLogger(CourseDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public CourseModel getCourseByCourseId(long id) {
		String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, COUNT(c.id) as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c where c.id = ?";
		CourseModel courseModel = null;
		try {
			courseModel = jdbcTemplate.queryForObject(sql,
					new Object[] { id }, new CourseMapper());
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return courseModel;
	}

	@Override
	public List<CourseModel> getAllOpenCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, COUNT(a.course_id) as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c where c.course_type=0 order by c.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<CourseModel> getAllOpenCourse(int page, int count) {
		List<CourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, COUNT(a.course_id) as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c where c.course_type=0 order by c.create_time desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{beginIndex, count}, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<CourseModel> getAllOpenCourseByCondition(int page, int count, String field, String industry, String competency) {
		List<CourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select c.course_grade, c.parent_id, c.study_people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c where c.course_type=0 and c.course_field=? and c.course_industry=? and c.course_competency=? order by c.course_date desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{field, industry, competency, beginIndex, count}, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public long addCourse(CourseModel courseModel) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		try {
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(
						Connection connection) throws SQLException {
					String sql = "insert into course(name, course_abstract, teacher, image, course_time, course_date, course_length, create_time, course_type, course_field, course_industry, course_competency, price, course_grade) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					PreparedStatement ps = connection.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, courseModel.getName());
					ps.setString(2, courseModel.getCourse_abstract());
					ps.setString(3, courseModel.getTeacher());
					ps.setString(4, courseModel.getImage());
					ps.setString(5, courseModel.getCourse_time());
					ps.setTimestamp(6, courseModel.getCourse_date());
					ps.setString(7, courseModel.getCourse_length());
					ps.setTimestamp(8, courseModel.getCreate_time());
					ps.setInt(9, courseModel.getCourseType());
					ps.setString(10, courseModel.getCourseField());
					ps.setString(11, courseModel.getCourseIndustry());
					ps.setString(12, courseModel.getCourseCompetency());
					ps.setString(13, courseModel.getPrice());
					ps.setString(14, courseModel.getCourseGrade());
					return ps;
				}
			}, keyHolder);
			logger.debug("addCourse result : {}", keyHolder.getKey().longValue());
			return keyHolder.getKey().longValue();
		} catch (DataAccessException e) {
			logger.error("addCourse error." + e.toString());
		}
		return -1;
	}

	@Override
	public List<CourseModel> getAllCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, c.price as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c order by c.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<CourseModel> getAllLiveClass() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.parent_id, c.id as has_collection, c.study_people_count, c.price as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c where c.course_type=1 order by c.course_date desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<CourseModel> getAllUserPayedCourseRecentCourse() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c order by c.course_date desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<LiveCourseModel> getAllLiveCourse(int page, int count, String userId) {
		List<LiveCourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select uf.screenshot, !ISNULL(b.user_id) as has_collection, a.pay_status, ulcp.pay_status as donate_pay_status, c.id, c.price, c.course_field, c.course_industry, c.course_competency, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c left JOIN user_collection as b on c.id=b.course_id and b.user_id=? LEFT JOIN user_course as a on a.user_id=? and a.course_id=c.id LEFT JOIN user_forward as uf on uf.user_id=? and uf.course_id=c.id LEFT JOIN user_live_course_pay as ulcp on ulcp.user_id=? and ulcp.course_id=c.id where NOW() < (select date_add(c.course_date, interval c.course_length minute)) and c.course_type=1 order by c.course_date asc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{userId, userId, userId, userId, beginIndex, count}, new RowMapperResultSetExtractor<LiveCourseModel>(
							new LiveCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<LiveCourseModel> getAllLiveCourseByCondition(int page, int count, String field, String industry,
			String competency, String userId) {
		List<LiveCourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select !ISNULL(b.user_id) as has_collection, a.pay_status, c.id, c.price, c.course_field, c.course_industry, c.course_competency, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c left JOIN user_collection as b on c.id=b.course_id and b.user_id=? LEFT JOIN user_course as a on a.user_id=? and a.course_id=c.id where NOW() < (select date_add(c.course_date, interval c.course_length minute)) and c.course_type=1 and c.course_field=? and c.course_industry=? and c.course_competency=? order by c.course_date desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{userId, userId, field, industry, competency, beginIndex, count}, new RowMapperResultSetExtractor<LiveCourseModel>(
							new LiveCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<SelectCourseModel> getLatestCourse() {
		List<SelectCourseModel> list = null;
		try {
			String sql = "select c.id, c.name, c.image, c.course_length, COUNT(a.course_id) as people_count from course as c LEFT JOIN user_study_record as a on a.course_id=c.id GROUP BY c.id ORDER BY c.create_time desc limit 0, 5";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<SelectCourseModel>(
							new SelectCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<SelectCourseModel> getHotestCourse() {
		List<SelectCourseModel> list = null;
		try {
			String sql = "select c.id, c.name, c.image, c.course_length, COUNT(a.course_id) as people_count from course as c LEFT JOIN user_study_record as a on a.course_id=c.id GROUP BY c.id ORDER BY COUNT(a.course_id) desc limit 0, 5";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<SelectCourseModel>(
							new SelectCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<CourseModel> getOpenCourseByCondition(String openId, int page, int count, String type, String field, String industry,
			String competeny, String grade, String key) {
		List<CourseModel> list = null;
		String sql = "select c.course_grade, c.parent_id, a.id as has_collection, c.study_people_count, 0 as people_count,  c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c LEFT JOIN user_collection as a on a.course_id=c.id and a.user_id =? ";
		StringBuilder whereSql = new StringBuilder();
		List<Object> args = new ArrayList<>();
		args.add(openId);
		whereSql.append(" where c.course_type=0 ");
		if(!("全部").equals(field)) {
			whereSql.append(" and c.course_field=? ");
			args.add(field);
		}
		if(!("全部").equals(industry)) {
			whereSql.append(" and c.course_industry=? ");
			args.add(industry);
		}
		if(!("全部").equals(competeny)) {
			whereSql.append(" and c.course_competency=? ");
			args.add(competeny);
		}
		if(!("全部").equals(grade)) {
			whereSql.append(" and c.course_grade=? ");
			args.add(grade);
		}
		if(key != null && !key.isEmpty()) {
			whereSql.append(" and (c.name like '%"+key+"%' or c.teacher like '%"+key+"%') ");
		}
		if(("最新").equals(type)) {
			sql = sql + whereSql.toString() + " GROUP BY c.id order by c.create_time desc limit ?,?";
		} else {
			//sql = sql + whereSql.toString() + " GROUP BY c.id order by COUNT(a.course_id) desc limit ?, ?";
			sql = sql + whereSql.toString() + " GROUP BY c.id order by c.study_people_count desc limit ?, ?";
		}
		int beginIndex = page == 1? 0:(page - 1) * count;
		args.add(beginIndex);
		args.add(count);
		try {
			list = jdbcTemplate.query(sql, (Object[])args.toArray(new Object[args.size()]), new RowMapperResultSetExtractor<CourseModel>(
					new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
			logger.debug("sql : {}", sql);
		}
		return list;
	}

	@Override
	public List<LiveCourseModel> getAllFinishedLiveCourse(int page, int count, String userId) {
		List<LiveCourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select a.pay_status, ulcp.pay_status as donate_pay_status, uf.screenshot, !ISNULL(b.user_id) as has_collection, c.id, c.price, c.course_field, c.course_industry, c.course_competency, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c left JOIN user_collection as b on c.id=b.course_id and b.user_id=? LEFT JOIN user_course as a on a.user_id=? and a.course_id=c.id LEFT JOIN user_forward as uf on uf.user_id=? and uf.course_id=c.id LEFT JOIN user_live_course_pay as ulcp on ulcp.user_id=? and ulcp.course_id=c.id where NOW() >= (select date_add(c.course_date, interval c.course_length minute)) and c.course_type=1 order by c.course_date desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{userId, userId, userId, userId, beginIndex, count}, new RowMapperResultSetExtractor<LiveCourseModel>(
							new LiveCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
	
	@Override
	public List<LiveCourseModel> getAllFinishedLiveCourseByCondition(int page, int count, String field, String industry,
			String competency, String userId) {
		List<LiveCourseModel> list = null;
		int beginIndex = page == 1? 0:(page - 1) * count;
		try {
			String sql = "select uf.screenshot, !ISNULL(b.user_id) as has_collection, a.pay_status, c.id, c.price, c.course_field, c.course_industry, c.course_competency, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c left JOIN user_collection as b on c.id=b.course_id and b.user_id=? LEFT JOIN user_course as a on a.user_id=? and a.course_id=c.id LEFT JOIN user_forward as uf on uf.user_id=? and uf.course_id=c.id where NOW() >= (select date_add(c.course_date, interval c.course_length minute)) and c.course_type=1 and c.course_field=? and c.course_industry=? and c.course_competency=? order by c.course_date desc limit ?,?";
			list = jdbcTemplate.query(sql, new Object[]{userId, userId, field, industry, competency, beginIndex, count}, new RowMapperResultSetExtractor<LiveCourseModel>(
							new LiveCourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public void updateCourseStudyPeopleCount() {
		List<CourseModel> list = null;
		try {
			String sql = "select c.course_grade, c.study_people_count, c.price as people_count, c.price, c.course_field, c.course_industry, c.course_competency, c.id, c.name, c.course_abstract, c.teacher, c.image, DATE_FORMAT(c.course_date,'%Y-%m-%d %T') as course_date_readable, c.course_date, c.course_time, c.course_length, c.create_time, c.course_type from course as c order by c.create_time desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<CourseModel>(
							new CourseMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		for(CourseModel cm : list) {
			String sql = "update course set study_people_count=? where id=?";
			int max = 1500;
		    int min = 300;
		    Random random = new Random();
		    int x = random.nextInt(max)%(max-min+1) + min;
			try {
				jdbcTemplate.update(sql, x, cm.getId());
			} catch(Exception ex) {
				logger.error(ex.toString());
			}
		}
	}

	@Override
	public void updateCourseStudyPeopleCount(Long courseId) {
		String sql = "update course set study_people_count=study_people_count+1 where id=?";
		try {
			jdbcTemplate.update(sql, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
	}

	@Override
	public void updateCourseStudyPeopleCountForCount(Long courseId, Integer study_people_count) {
		String sql = "update course set study_people_count=? where id=?";
		try {
			jdbcTemplate.update(sql, study_people_count, courseId);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
	}
	
	@Override
	public List<LiveCourseShareModel> getLiveClassShare() {
		List<LiveCourseShareModel> list = null;
		try {
			String sql = "select c.id, c.image, a.link, c.name as title, DATE_FORMAT(c.course_date,'%Y-%m-%d') as week, c.id as course_id, c.name as course_name from course as c left join live_class_share as a on a.id=c.id where NOW() < (select date_add(c.course_date, interval c.course_length minute)) and c.course_type=1 order by c.course_date asc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<LiveCourseShareModel>(
							new LiveClassShareMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<LiveClassApplyModel> getAllLiveClassApply() {
		List<LiveClassApplyModel> list = null;
		try {
			String sql = "select id, name, teacher, count(id) as total from ((select a.id, a.name, a.teacher from course as a, user_forward as b where a.id=b.course_id) union all (select t1.id, t1.name, t1.teacher from course as t1, user_live_course_pay as t2 where t1.id=t2.course_id)) as temp group by id";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<LiveClassApplyModel>(
							new LiveClassApplyMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<LiveClassApplyModel> getAllOpenClassStudyCount() {
		List<LiveClassApplyModel> list = null;
		try {
			String sql = "select id, name, teacher, study_people_count as total from course where course_type=0";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<LiveClassApplyModel>(
							new LiveClassApplyMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public List<LiveClassApplyModel> getAllSelectLiveCourse() {
		List<LiveClassApplyModel> list = null;
		try {
			String sql = "select id, name, teacher, study_people_count as total from course where course_type=1 order by course_date desc";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<LiveClassApplyModel>(
							new LiveClassApplyMapper()));
		} catch (Exception e) {
			logger.debug("exception : {}", e.toString());
		}
		return list;
	}
}