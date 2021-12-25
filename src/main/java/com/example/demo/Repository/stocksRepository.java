package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.stocks;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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

    @Query("select sum(s.daystockamount),sum(s.daysalesamount) from stocks as s where  s.initialdate <=:startdate and initialdate > :enddate and s.daylatest =:flag and s.userid = :userid and s.settingsid =:propertyid")
    List<Long[]> getstocksbydaterange(LocalDate startdate, LocalDate enddate, Long userid, Long propertyid, Boolean flag);

    @Query("select s from stocks as s where  s.initialdate <=:startdate and initialdate > :enddate and s.userid = :userid")
    <List> stocks[] gettransactionsbydaterange(LocalDate startdate, LocalDate enddate, Long userid);

    @Query("select s from stocks as s where s.userid = :userid")
    <List> stocks[] gettransactionslatest(Long userid);

    @Query("select s from stocks as s where  s.id =:id")
    stocks gettransactionbyid(Long id);

    @Query("select s from stocks as s where  s.initialdate >=:Todaydate  and s.userid = :userid and s.settingsid =:propertyid")
    <List> stocks[] getalltransactionafterthedate(LocalDate Todaydate,  Long propertyid, Long userid);

    @Transactional
    @Modifying
    @Query("update stocks s set s.leftqty =:leftqty , s.leftamount =:leftamount, daystocks=:daystock, daystockamount=:daystockamount , daysales=:daysales, daysalesamount=:daysalesamount where s.id =:id")
    void updateallqtydetails(Long id,Long leftqty ,Long leftamount,Long daystock,Long daystockamount,Long daysales,Long daysalesamount);

    @Query("select max(s.id) from stocks as s where  s.initialdate =:Todaydate  and s.userid = :userid and s.settingsid =:propertyid")
    Long getpreviouslatesttransaction(LocalDate Todaydate,  Long propertyid, Long userid);

    @Transactional
    @Modifying
    @Query("delete from  stocks as s where s.id=:id")
    void deletestockrow(Long id);
}
