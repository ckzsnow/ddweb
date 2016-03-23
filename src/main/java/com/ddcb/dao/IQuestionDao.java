package com.ddcb.dao;

import java.util.List;

import com.ddcb.model.QuestionModel;

public interface IQuestionDao {

	public List<QuestionModel> getAllQuestionByCourseId(Long courseId, int page, int countPerPage);
	
	public boolean addQuestion(QuestionModel qm);
	
	public boolean updateClickLike(Long id);
		
}