package com.utsc.project_coding_lads.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = QuizQuestionOption.TABLE_NAME)
public class QuizQuestionOption extends BaseDataEntity {

	final static String TABLE_NAME = "QUIZ_QUESTION_OPTION";
	
	private QuizQuestion question;
	private String questionOption;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "question_id")
	public QuizQuestion getQuestion() {
		return question;
	}
	public void setQuestion(QuizQuestion question) {
		this.question = question;
	}
	@Column(name = "option")
	public String getQuestionOption() {
		return questionOption;
	}
	public void setQuestionOption(String questionOption) {
		this.questionOption = questionOption;
	}
	
	
	
}
