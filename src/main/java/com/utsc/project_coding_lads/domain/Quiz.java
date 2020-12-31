package com.utsc.project_coding_lads.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = Quiz.TABLE_NAME)
public class Quiz extends BaseDataEntity {

	static final String TABLE_NAME = "QUIZ";
	
	private Course course;
	private LocalDateTime quizStartDate;
	private LocalDateTime quizEndDate;
	private List<QuizQuestion> quizQuestions = new ArrayList<>();
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@Column(name = "quiz_start_date")
	public LocalDateTime getQuizStartDate() {
		return quizStartDate;
	}
	public void setQuizStartDate(LocalDateTime quizStartDate) {
		this.quizStartDate = quizStartDate;
	}
	@Column(name = "quiz_end_date")
	public LocalDateTime getQuizEndDate() {
		return quizEndDate;
	}
	public void setQuizEndDate(LocalDateTime quizEndDate) {
		this.quizEndDate = quizEndDate;
	}
//	@JsonIgnore
	@OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<QuizQuestion> getQuizQuestions() {
		return quizQuestions;
	}
	public void setQuizQuestions(List<QuizQuestion> quizQuestions) {
		this.quizQuestions = quizQuestions;
	}
	
	
	
	
}
