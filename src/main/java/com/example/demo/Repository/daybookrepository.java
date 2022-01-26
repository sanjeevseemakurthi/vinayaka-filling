package com.example.demo.Repository;

import com.example.demo.Entity.daybook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface daybookrepository  extends JpaRepository<daybook,Long> {
    @Query("select d from daybook  d where d.date =:date and d.uid =:uid ")
    daybook[] getdatabydate(LocalDate date,long uid);
}
