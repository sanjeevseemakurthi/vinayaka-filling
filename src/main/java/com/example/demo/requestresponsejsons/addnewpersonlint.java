package com.example.demo.requestresponsejsons;

import com.example.demo.Entity.lent;
import com.example.demo.Entity.people;

public class addnewpersonlint {
    people peopledata;
    lent lentdata;

    public people getPeopledata() {
        return peopledata;
    }

    public void setPeopledata(people peopledata) {
        this.peopledata = peopledata;
    }

    public lent getLentdata() {
        return lentdata;
    }

    public void setLentdata(lent lentdata) {
        this.lentdata = lentdata;
    }

    @Override
    public String toString() {
        return "addnewpersonlint{" +
                "peopledata=" + peopledata +
                ", lentdata=" + lentdata +
                '}';
    }
}
