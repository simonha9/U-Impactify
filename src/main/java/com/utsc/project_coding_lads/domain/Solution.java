package com.utsc.project_coding_lads.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = Solution.TABLE_NAME)
public class Solution extends BaseDataEntity {

	static final String TABLE_NAME = "SOLUTION";
	
	private QuizQuestion question;
	private String answer;
	
	@JsonIgnore
	@OneToOne
	@JoinColumn(name = "quiz_question_id")
	public QuizQuestion getQuestion() {
		return question;
	}
	public void setQuestion(QuizQuestion question) {
		this.question = question;
	}
	@Column(name = "answer")
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	
	
	
	
	
	
}
