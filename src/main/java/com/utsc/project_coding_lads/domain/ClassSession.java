package com.utsc.project_coding_lads.domain;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = ClassSession.TABLE_NAME)
public class ClassSession extends BaseDataEntity {

	public static final String TABLE_NAME = "CLASS_SESSION";
	
	private Course course;
	private LocalDateTime startDate;
	private LocalDateTime endDate;
	
	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	@Column(name = "start_date")
	public LocalDateTime getStartDate() {
		return startDate;
	}
	public void setStartDate(LocalDateTime startDate) {
		this.startDate = startDate;
	}
	@Column(name = "end_date")
	public LocalDateTime getEndDate() {
		return endDate;
	}
	public void setEndDate(LocalDateTime endDate) {
		this.endDate = endDate;
	}
	
	
	
	
}
