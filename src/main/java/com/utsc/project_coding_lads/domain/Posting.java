package com.utsc.project_coding_lads.domain;

import java.time.LocalDateTime;
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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = Posting.TABLE_NAME)
public class Posting extends BaseDataEntity {

	public static final String TABLE_NAME = "POSTING";
	
//	private List<Application> application;
	private String name;
	private String postingDesc;
	private User postingCreator;
	private String postingType;
	private LocalDateTime postingDate;
	private SocialInitiative socialInit;
	private List<Application> applications;
	
//	public List<Application> getApplication() {
//		return application;
//	}
//	public void setApplication(List<Application> application) {
//		this.application = application;
//	}
	@Column(name = "name", length = 32)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name = "posting_desc", length = 32)
	public String getPostingDesc() {
		return postingDesc;
	}
	public void setPostingDesc(String postingDesc) {
		this.postingDesc = postingDesc;
	}
//	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	public User getPostingCreator() {
		return postingCreator;
	}
	public void setPostingCreator(User postingCreator) {
		this.postingCreator = postingCreator;
	}
	@Column(name = "posting_date", length = 12)
	public LocalDateTime getPostingDate() {
		return postingDate;
	}
	public void setPostingDate(LocalDateTime postingDate) {
		this.postingDate = postingDate;
	}
	@Column(name = "posting_type", length = 32)
	public String getPostingType() {
		return postingType;
	}
	public void setPostingType(String postingType) {
		this.postingType = postingType;
	}
	@ManyToOne
	@JoinColumn(name="social_init_id")
	public SocialInitiative getSocialInit() {
		return socialInit;
	}
	public void setSocialInit(SocialInitiative socialInit) {
		this.socialInit = socialInit;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "posting", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Application> getApplications() {
		return applications;
	}
	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}
	
	
	
	
	
	
}
