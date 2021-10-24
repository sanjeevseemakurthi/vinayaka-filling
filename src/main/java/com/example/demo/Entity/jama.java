package com.example.demo.Entity;

import java.time.LocalDate;

public class jama {
	private Long amount;
	private LocalDate date;
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "jama [amount=" + amount + ", date=" + date + "]";
	}
}
