package com.utsc.project_coding_lads.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = Role.TABLE_NAME)
public class Role extends BaseDataEntity {

	public static final String TABLE_NAME = "ROLE";
	private String name;
	
	public Role() {}
	
	public Role(String name) {
		super();
		this.name = name;
	}

	@Column(name="name", nullable = false, length = 32)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

	
}