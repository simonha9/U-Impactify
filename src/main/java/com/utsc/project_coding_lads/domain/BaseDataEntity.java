package com.utsc.project_coding_lads.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

@MappedSuperclass
public class BaseDataEntity {

	
	private Integer id;

	@Id
	@SequenceGenerator(name = "APP_SEQ", sequenceName = "APP_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "APP_SEQ")
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
	
}
