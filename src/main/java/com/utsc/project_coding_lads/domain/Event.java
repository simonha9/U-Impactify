package com.utsc.project_coding_lads.domain;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = Event.TABLE_NAME)
public class Event extends BaseDataEntity {

	public static final String TABLE_NAME = "EVENT";
	
	private String eventName;
	private String eventDesc;
	private User eventCreator;
	private String imgUrl;
	private LocalDateTime eventStartDate;
	private LocalDateTime eventEndDate;
	
	@Column(name = "event_name", length = 32)
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	@Column(name = "event_desc", length = 256)
	public String getEventDesc() {
		return eventDesc;
	}
	public void setEventDesc(String eventDesc) {
		this.eventDesc = eventDesc;
	}
	@Column(name = "event_start_date_time", length = 12)
	public LocalDateTime getEventStartDate() {
		return eventStartDate;
	}
	public void setEventStartDate(LocalDateTime eventStartDate) {
		this.eventStartDate = eventStartDate;
	}
	@Column(name = "event_end_date_time", length = 12)
	public LocalDateTime getEventEndDate() {
		return eventEndDate;
	}
	public void setEventEndDate(LocalDateTime eventEndDate) {
		this.eventEndDate = eventEndDate;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	public User getEventCreator() {
		return eventCreator;
	}
	public void setEventCreator(User eventCreator) {
		this.eventCreator = eventCreator;
	}
	@Column(name = "img_url", length = 256)
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	
	
	
	
	
}
