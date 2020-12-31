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
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.utsc.project_coding_lads.custom_deserialize.RoleDeserializer;
import com.utsc.project_coding_lads.custom_deserialize.SocialInitDeserializer;

@Entity
@Table(name = User.TABLE_NAME)
//@Inheritance(strategy = InheritanceType.JOINED)
public class User extends BaseDataEntity {
	
	public static final String TABLE_NAME = "UI_USER";
	
	private boolean isAuthenticated;
	
	@JsonProperty("firstName")
	private String firstName;
	@JsonProperty("lastName")
	private String lastName;
	@JsonProperty("username")
	private String username;
	@JsonProperty("password")
	private String hashedPassword;
	@JsonProperty("age")
	private Integer age;
	@JsonDeserialize(using = SocialInitDeserializer.class)
	@JsonProperty("socialInit")
	private SocialInitiative socialInit;
	@JsonDeserialize(using = RoleDeserializer.class)
	@JsonProperty("role")
	private Role role;
	private List<Application> application;
	private List<Event> events = new ArrayList<>();
	private List<Posting> postings = new ArrayList<>();
	private List<Invoice> invoices = new ArrayList<>();
	
	
	@ManyToOne(optional = true, fetch = FetchType.EAGER)
	@JoinColumn(name="socialinit_id")
	public SocialInitiative getSocialInit() {
		return socialInit;
	}
	public void setSocialInit(SocialInitiative socialInit) {
		this.socialInit = socialInit;
	}
	@ManyToOne(optional = true)
	@JoinColumn(name="role_id")
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	@Column(name="first_name", nullable = false, length = 32)
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	@Column(name="last_name", nullable = false, length = 32)
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	@Column(name="user_pw", nullable = false, length = 64)
	public String getHashedPassword() {
		return hashedPassword;
	}
	public void setHashedPassword(String hashedPassword) {
		this.hashedPassword = hashedPassword;
	}
	@Column(name="username", nullable = false, length = 32, unique = true)
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	@Column(name = "age")
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "eventCreator")
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	@JsonIgnore 
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "postingCreator")
	public List<Posting> getPostings() {
		return postings;
	}
	public void setPostings(List<Posting> postings) {
		this.postings = postings;
	}
	@JsonIgnore 
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "applicant")
	public List<Application> getApplication() {
		return application;
	}
	public void setApplication(List<Application> application) {
		this.application = application;
	}
	@JsonIgnore
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	public List<Invoice> getInvoices() {
		return invoices;
	}
	public void setInvoices(List<Invoice> invoices) {
		this.invoices = invoices;
	}
	
	
	
	
	
	
	
}
