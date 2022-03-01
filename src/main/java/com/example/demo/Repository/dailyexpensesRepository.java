package com.example.demo.Repository;

import com.example.demo.Entity.dailyexpenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface dailyexpensesRepository extends JpaRepository <dailyexpenses,Long> {
    List<dailyexpenses> findByTidAndTypeAndUid(Long tid,String type,Long uid);
    Integer deleteMyEntityById(Long id);
    List<dailyexpenses> findByTidAndUid(Long tid,Long uid);
}
