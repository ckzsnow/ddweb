package com.ddcb.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapperResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.ddcb.dao.IBannerDao;
import com.ddcb.mapper.BannerMapper;
import com.ddcb.model.BannerModel;

@Repository("bannerDao")
public class BannerDaoImpl implements IBannerDao {

	private static final Logger logger = LoggerFactory
			.getLogger(BannerDaoImpl.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<BannerModel> getAllBanner() {
		List<BannerModel> list = null;
		try {
			String sql = "select * from banner";
			list = jdbcTemplate.query(sql, new RowMapperResultSetExtractor<BannerModel>(
							new BannerMapper()));
		} catch (Exception e) {
			logger.error("exception : {}", e.toString());
		}
		return list;
	}

	@Override
	public boolean updateBanner(String id, String fileName) {
		String sql = "update banner set file_name=? where id=?";
		int affectedRows = 0;
		try {
			affectedRows = jdbcTemplate.update(sql, fileName, id);
		} catch(Exception ex) {
			logger.error(ex.toString());
		}
		return affectedRows != 0;
	}
	
}