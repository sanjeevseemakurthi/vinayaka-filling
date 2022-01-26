package com.example.demo.Entity;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Arrays;


@Controller
@Entity
public class daybook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 3000)
    private expenses daydata[];
    private LocalDate date;
    private long uid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public expenses[] getDaydata() {
        return daydata;
    }

    public void setDaydata(expenses[] daydata) {
        this.daydata = daydata;
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
        return "daybook{" +
                "id=" + id +
                ", daydata=" + Arrays.toString(daydata) +
                ", date=" + date +
                ", uid=" + uid +
                '}';
    }
}
