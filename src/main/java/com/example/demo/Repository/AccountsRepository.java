package com.example.demo.Repository;

import com.example.demo.Entity.accounts;
import com.example.demo.Entity.dailyexpenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface AccountsRepository extends JpaRepository<accounts,Long> {

    @Query("select s from accounts s where s.date = " +
            "(select max(m.date) from accounts m where m.date<=:date and m.uid =:userid  and m.pid =:pid)"
            + " and s.uid =:userid and s.pid =:pid")
    List<accounts> getlatestdayorprevious(LocalDate date, Long userid, Long pid);

    @Query("select s from accounts s where s.date >:date and s.uid =:userid and s.pid =:pid")
    List<accounts>  findgraterthangivendate(LocalDate date, Long userid, Long pid);
    List<accounts> getByPidAndUid(Long pid,Long uid);
    List<accounts> findByUidAndPidAndDateGreaterThanEqualAndIdGreaterThan(Long uid,Long pid,LocalDate date,Long id);
    accounts getById(Long id);
}
