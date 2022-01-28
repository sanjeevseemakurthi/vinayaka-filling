package com.example.demo.Entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class daysheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToMany(targetEntity = dailyexpenses.class,cascade = CascadeType.ALL)
    @JoinColumn(name = "joinid",referencedColumnName = "id")
    private List<dailyexpenses> dailyexpenses;
    private LocalDate date;
    private long uid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<com.example.demo.Entity.dailyexpenses> getDailyexpenses() {
        return dailyexpenses;
    }

    public void setDailyexpenses(List<com.example.demo.Entity.dailyexpenses> dailyexpenses) {
        this.dailyexpenses = dailyexpenses;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "daysheet{" +
                "id=" + id +
                ", dailyexpenses=" + dailyexpenses +
                ", date=" + date +
                ", uid=" + uid +
                '}';
    }
}
