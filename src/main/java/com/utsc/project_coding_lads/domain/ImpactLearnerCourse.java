package com.utsc.project_coding_lads.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = ImpactLearnerCourse.TABLE_NAME)
public class ImpactLearnerCourse extends BaseDataEntity {

	public static final String TABLE_NAME = "IMPACT_LEARNER_COURSE";
	
	private ImpactLearner student;
	private Course course;
	
	@ManyToOne
	@JoinColumn(name = "student_id")
	public ImpactLearner getStudent() {
		return student;
	}
	public void setStudent(ImpactLearner student) {
		this.student = student;
	}
	@ManyToOne
	@JoinColumn(name = "course_id")
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	
	
	
	
	
	
}
