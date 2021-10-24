package com.example.demo.Entity;

import java.time.LocalDate;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.springframework.stereotype.Controller;

@Controller
@Entity
public class stocks {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private LocalDate initialdate;
	private Long settingsid;
	private String name;
	private Long Qty;
	private Long ammount;
	private String stockflag;
	private String phno;
	private LocalDate finalDate;
	private Long amountCleared;
	private Long userid;
	private jama deposit[];
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDate getInitialdate() {
		return initialdate;
	}
	public void setInitialdate(LocalDate initialdate) {
		this.initialdate = initialdate;
	}
	public Long getSettingsid() {
		return settingsid;
	}
	public void setSettingsid(Long settingsid) {
		this.settingsid = settingsid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getQty() {
		return Qty;
	}
	public void setQty(Long qty) {
		Qty = qty;
	}
	public Long getAmmount() {
		return ammount;
	}
	public void setAmmount(Long ammount) {
		this.ammount = ammount;
	}

	public String getStockflag() {
		return stockflag;
	}
	public void setStockflag(String stockflag) {
		this.stockflag = stockflag;
	}
	public String getPhno() {
		return phno;
	}
	public void setPhno(String phno) {
		this.phno = phno;
	}
	public LocalDate getFinalDate() {
		return finalDate;
	}
	public void setFinalDate(LocalDate finalDate) {
		this.finalDate = finalDate;
	}
	public Long getAmountCleared() {
		return amountCleared;
	}
	public void setAmountCleared(Long amountCleared) {
		this.amountCleared = amountCleared;
	}
	public Long getUserid() {
		return userid;
	}
	public void setUserid(Long userid) {
		this.userid = userid;
	}
	
	public jama[] getDeposit() {
		return deposit;
	}
	public void setDeposit(jama[] deposit) {
		this.deposit = deposit;
	}
	@Override
	public String toString() {
		return "stocks [id=" + id + ", initialdate=" + initialdate + ", settingsid=" + settingsid + ", name=" + name
				+ ", Qty=" + Qty + ", ammount=" + ammount + ", stockflag=" + stockflag + ", phno=" + phno
				+ ", finalDate=" + finalDate + ", amountCleared=" + amountCleared + ", userid=" + userid + ", deposit="
				+ Arrays.toString(deposit) + "]";
	}
	
}
