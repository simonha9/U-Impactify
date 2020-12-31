package com.utsc.project_coding_lads.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utsc.project_coding_lads.domain.QuizQuestionOption;

@Repository
public interface QuestionOptionRepository extends JpaRepository<QuizQuestionOption, Integer> {

}
