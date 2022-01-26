package com.example.demo.Entity;

import org.springframework.stereotype.Controller;

import java.io.Serializable;

@Controller
public class expenses implements Serializable {
    private String discription;
    private String type;
    private long typeid;
    private long deposit;
    private long withdraw;

    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getTypeid() {
        return typeid;
    }

    public void setTypeid(long typeid) {
        this.typeid = typeid;
    }

    public long getDeposit() {
        return deposit;
    }

    public void setDeposit(long deposit) {
        this.deposit = deposit;
    }

    public long getWithdraw() {
        return withdraw;
    }

    public void setWithdraw(long withdraw) {
        this.withdraw = withdraw;
    }

    @Override
    public String toString() {
        return "expenses{" +
                "discription='" + discription + '\'' +
                ", type='" + type + '\'' +
                ", typeid=" + typeid +
                ", deposit=" + deposit +
                ", withdraw=" + withdraw +
                '}';
    }
}
