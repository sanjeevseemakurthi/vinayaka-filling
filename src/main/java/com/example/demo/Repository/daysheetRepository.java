package com.example.demo.Repository;

import com.example.demo.Entity.daysheet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface daysheetRepository extends JpaRepository <daysheet,Long> {
    @Query("select d from daysheet  d where d.date =:date and d.uid =:uid ")
    daysheet[] getdatabydate(LocalDate date, long uid);
}
