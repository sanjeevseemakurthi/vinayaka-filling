package com.example.demo.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
@Entity
public class settings {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String property;
	private String subproperty;
	private int stockleft;
	private long userid;
	private LocalDate createddate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getProperty() {
		return property;
	}
	public void setProperty(String property) {
		this.property = property;
	}
	public String getSubproperty() {
		return subproperty;
	}
	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}
	public int getStockleft() {
		return stockleft;
	}
	public void setStockleft(int stockleft) {
		this.stockleft = stockleft;
	}
	public long getUserid() {
		return userid;
	}
	public void setUserid(long userid) {
		this.userid = userid;
	}

	public LocalDate getCreateddate() {
		return createddate;
	}

	public void setCreateddate(LocalDate createddate) {
		this.createddate = createddate;
	}
}
