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
	private long id;
	private String property;
	private String subproperty;
	private long stockleft;
	private long userid;
	private LocalDate createddate;
	private long stockamount;

	public long getStockamount() {
		return stockamount;
	}

	public void setStockamount(long stockamount) {
		this.stockamount = stockamount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public long getStockleft() {
		return stockleft;
	}

	public void setStockleft(long stockleft) {
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
