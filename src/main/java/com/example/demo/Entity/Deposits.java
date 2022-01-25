package com.example.demo.Entity;
import org.springframework.stereotype.Controller;

import java.io.Serializable;
import java.time.LocalDate;

@Controller
public class Deposits implements Serializable {
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
        return "Deposits{" +
                "amount=" + amount +
                ", date=" + date +
                '}';
    }
}
