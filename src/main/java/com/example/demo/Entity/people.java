package com.example.demo.Entity;

import org.springframework.stereotype.Controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Controller
@Entity
public class people {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String fatherorhusbandname;
    private String phoneno;
    private String village;
    private String address1;
    private String address2;
    private String Country;
    private long pincode;
    private long accno;
    private long uid;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFatherorhusbandname() {
        return fatherorhusbandname;
    }

    public void setFatherorhusbandname(String fatherorhusbandname) {
        this.fatherorhusbandname = fatherorhusbandname;
    }

    public String getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(String phoneno) {
        this.phoneno = phoneno;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public long getPincode() {
        return pincode;
    }

    public void setPincode(long pincode) {
        this.pincode = pincode;
    }

    public long getAccno() {
        return accno;
    }

    public void setAccno(long accno) {
        this.accno = accno;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "people{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fatherorhusbandname='" + fatherorhusbandname + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", village='" + village + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", Country='" + Country + '\'' +
                ", pincode=" + pincode +
                ", accno=" + accno +
                ", uid=" + uid +
                '}';
    }
}
