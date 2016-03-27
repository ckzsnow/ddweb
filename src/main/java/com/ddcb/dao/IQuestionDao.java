package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.QuestionModel;

public interface IQuestionDao {

	public List<QuestionModel> getAllQuestionByCourseId(String openId, Long courseId, int page, int countPerPage);
	
	public List<QuestionModel> getAllQuestionByCourseId(Long courseId, int page, int countPerPage);
	
	public long addQuestion(QuestionModel qm);
	
	public boolean updateClickLike(Long id, int type);
	
	public boolean deleteUserQuestion(Long id);
		
}