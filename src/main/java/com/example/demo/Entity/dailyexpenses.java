package com.example.demo.Entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;

@Controller
@Entity
public class dailyexpenses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long tid;
    private String discription;
    private String type;
    private long deposit;
    private long withdraw;
    private long uid;

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

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
        return "dailyexpenses{" +
                "id=" + id +
                ", tid=" + tid +
                ", discription='" + discription + '\'' +
                ", type='" + type + '\'' +
                ", deposit=" + deposit +
                ", withdraw=" + withdraw +
                ", uid=" + uid +
                '}';
    }
}
