package com.example.demo.Repository;

import com.example.demo.Entity.settings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Controller;

import com.example.demo.Entity.stocks;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface stocksRepository extends JpaRepository<stocks,String> {

    @Query("select s from stocks s where s.initialdate > :dateTime and s.userid = :userid and s.settingsid =:propertyid")
    <List> stocks[] findgraterthangivendate(LocalDate dateTime, Long userid, Long propertyid);

    @Query("select s from stocks s where s.initialdate = :dateTime and s.daylatest =:flag and s.userid = :userid and s.settingsid =:propertyid")
    stocks getlatestday(LocalDate dateTime, Long userid, Long propertyid,Boolean flag);

    @Query("select s from stocks s where s.initialdate = " +
            "(select max(m.initialdate) from stocks as m where m.initialdate<=:dateTime)" +
            " and s.daylatest =:flag and s.userid = :userid and s.settingsid =:propertyid")
    stocks getlatestdayorprevious(LocalDate dateTime, Long userid, Long propertyid,Boolean flag);

    @Transactional
    @Modifying
    @Query("update stocks s set s.daylatest =:flag where s.id =:id")
    void updateFlagById(Long id,Boolean flag);

    @Transactional
    @Modifying
    @Query("update stocks s set s.leftqty =:leftqty , s.leftamount =:leftamount  where s.id =:id")
    void updateQtyAmount(Long id,Long leftqty ,Long leftamount);

}
