package com.utsc.project_coding_lads.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.utsc.project_coding_lads.domain.QuizQuestion;

@Repository
public interface QuizQuestionRepository extends JpaRepository<QuizQuestion, Integer> {

	
	@Query(value = "SELECT q FROM QuizQuestion q WHERE q.quiz.id = :quizId")
	public List<QuizQuestion> findQuizQuestionsByQuizId(@Param("quizId") Integer quizId);
}
