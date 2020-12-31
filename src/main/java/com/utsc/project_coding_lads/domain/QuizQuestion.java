package com.utsc.project_coding_lads.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = QuizQuestion.TABLE_NAME)
public class QuizQuestion extends BaseDataEntity {

	static final String TABLE_NAME = "QUIZ_QUESTION";
	
	private String questionType;
	private String question;
	private List<QuizQuestionOption> questionOptions = new ArrayList<>();
	private Quiz quiz;
	private Solution solution;
	private List<StudentAnswer> studentAnswers = new ArrayList<>();
	
	@Column(name = "question_type")
	public String getQuestionType() {
		return questionType;
	}
	public void setQuestionType(String questionType) {
		this.questionType = questionType;
	}
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "quiz_id")
	public Quiz getQuiz() {
		return quiz;
	}
	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
	@OneToOne(mappedBy = "question", cascade = CascadeType.ALL)
	public Solution getSolution() {
		return solution;
	}
	public void setSolution(Solution solution) {
		this.solution = solution;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<StudentAnswer> getStudentAnswers() {
		return studentAnswers;
	}
	public void setStudentAnswers(List<StudentAnswer> studentAnswers) {
		this.studentAnswers = studentAnswers;
	}
	@Column(name = "question")
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	@OneToMany(mappedBy = "question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	public List<QuizQuestionOption> getQuestionOptions() {
		return questionOptions;
	}
	public void setQuestionOptions(List<QuizQuestionOption> questionOptions) {
		this.questionOptions = questionOptions;
	}
	
	
	
	
	
	
	
}
