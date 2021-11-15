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
	private Long qty;
	private Long amount;
	private Boolean stockflag;
	private String phno;
	private LocalDate finaldate;
	private Long amountcleared;
	private Long userid;
	private Deposits[] deposits;
	private Boolean daylatest;
	private long leftqty;
	private long leftamount;

	public long getLeftqty() {
		return leftqty;
	}

	public void setLeftqty(long leftqty) {
		this.leftqty = leftqty;
	}

	public long getLeftamount() {
		return leftamount;
	}

	public void setLeftamount(long leftamount) {
		this.leftamount = leftamount;
	}

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
		return qty;
	}

	public void setQty(Long qty) {
		this.qty = qty;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Boolean getStockflag() {
		return stockflag;
	}

	public void setStockflag(Boolean stockflag) {
		this.stockflag = stockflag;
	}

	public String getPhno() {
		return phno;
	}

	public void setPhno(String phno) {
		this.phno = phno;
	}

	public LocalDate getFinaldate() {
		return finaldate;
	}

	public void setFinaldate(LocalDate finaldate) {
		this.finaldate = finaldate;
	}

	public Long getAmountcleared() {
		return amountcleared;
	}

	public void setAmountcleared(Long amountcleared) {
		this.amountcleared = amountcleared;
	}

	public Long getUserid() {
		return userid;
	}

	public void setUserid(Long userid) {
		this.userid = userid;
	}

	public Deposits[] getDeposits() {
		return deposits;
	}

	public void setDeposits(Deposits[] deposits) {
		this.deposits = deposits;
	}

	public Boolean getDaylatest() {
		return daylatest;
	}

	public void setDaylatest(Boolean daylatest) {
		this.daylatest = daylatest;
	}

	@Override
	public String toString() {
		return "stocks{" +
				"id=" + id +
				", initialdate=" + initialdate +
				", settingsid=" + settingsid +
				", name='" + name + '\'' +
				", qty=" + qty +
				", amount=" + amount +
				", stockflag=" + stockflag +
				", phno='" + phno + '\'' +
				", finaldate=" + finaldate +
				", amountcleared=" + amountcleared +
				", userid=" + userid +
				", deposits=" + Arrays.toString(deposits) +
				", daylatest=" + daylatest +
				", leftqty=" + leftqty +
				", leftamount=" + leftamount +
				'}';
	}
}
