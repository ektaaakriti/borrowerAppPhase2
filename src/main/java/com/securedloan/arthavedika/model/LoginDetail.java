package com.securedloan.arthavedika.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "login_table")
public class LoginDetail {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int login_id;
	@Column
	private String login_ip;
	@Column
	private Date logged_in_date;
	@Column
	private Date logged_out_date;

	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "user_id")
	private User user;

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}

	public String getLogin_ip() {
		return login_ip;
	}

	public void setLogin_ip(String login_ip) {
		this.login_ip = login_ip;
	}

	public Date getLogged_in_date() {
		return logged_in_date;
	}

	public void setLogged_in_date(Date logged_in_date) {
		this.logged_in_date = logged_in_date;
	}

	public Date getLogged_out_date() {
		return logged_out_date;
	}

	public void setLogged_out_date(Date logged_out_date) {
		this.logged_out_date = logged_out_date;
	}

	public User getUser() {
		return user;
	}

	public LoginDetail() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LoginDetail(String login_ip, Date logged_in_date, Date logged_out_date, User user) {
		super();
		this.login_ip = login_ip;
		this.logged_in_date = logged_in_date;
		this.logged_out_date = logged_out_date;
		this.user = user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
