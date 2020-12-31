package com.utsc.project_coding_lads.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Column;

@Entity
@Table(name = Invoice.TABLE_NAME)
public class Invoice extends BaseDataEntity {

	public static final String TABLE_NAME = "INVOICE";
	
	private User user;
	private Course course;
	private int cost;
	private int initCost;
	@Column(name = "price")
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}

	@Column(name = "init_price")
	public int getInitCost() {
		return initCost;
	}
	public void setInitCost(int initCost) {
		this.initCost = initCost;
	}
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
