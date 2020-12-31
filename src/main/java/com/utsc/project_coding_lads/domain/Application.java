package com.utsc.project_coding_lads.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Application.TABLE_NAME)
public class Application extends BaseDataEntity {

	public static final String TABLE_NAME = "APPLICATION";
	
	private User applicant;
	private Posting posting;
	private String email;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "applicant_id")
	public User getApplicant() {
		return applicant;
	}
	public void setApplicant(User applicant) {
		this.applicant = applicant;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="posting_id")
	public Posting getPosting() {
		return posting;
	}
	public void setPosting(Posting posting) {
		this.posting = posting;
	}
	@Column(name="email")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
