package com.example.demo.Entity;

import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Controller
@Entity
public class finance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long pid;
    private long amount;
    private String item;
    private long Qty;
    private LocalDate date;
    @OneToMany(targetEntity = Deposits.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "joinid",referencedColumnName = "id")
    List<Deposits> deposits;
    private long sno;
    private long uid;
    private boolean isactive;
    private boolean fromperson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public long getQty() {
        return Qty;
    }

    public void setQty(long qty) {
        Qty = qty;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Deposits> getDeposits() {
        return deposits;
    }

    public void setDeposits(List<Deposits> deposits) {
        this.deposits = deposits;
    }

    public long getSno() {
        return sno;
    }

    public void setSno(long sno) {
        this.sno = sno;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public boolean isFromperson() {
        return fromperson;
    }

    public void setFromperson(boolean fromperson) {
        this.fromperson = fromperson;
    }

    @Override
    public String toString() {
        return "finance{" +
                "id=" + id +
                ", pid=" + pid +
                ", amount=" + amount +
                ", item='" + item + '\'' +
                ", Qty=" + Qty +
                ", date=" + date +
                ", deposits=" + deposits +
                ", sno=" + sno +
                ", uid=" + uid +
                ", isactive=" + isactive +
                ", fromperson=" + fromperson +
                '}';
    }
}
