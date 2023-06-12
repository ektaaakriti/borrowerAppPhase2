package com.securedloan.arthavedika.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;


@Entity
@Table
public class GroupData {
	private @GeneratedValue(strategy = GenerationType.AUTO, generator = "port_gen") @Id @NotNull long id;
	private @NotNull String group_name;
	public String getGroup_name() {
		return group_name;
	}
	public void setGroup_name(String group_name) {
		this.group_name = group_name;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public GroupData(@NotNull long id, @NotNull String group_name) {
		super();
		this.id = id;
		this.group_name = group_name;
	}
	public GroupData() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
