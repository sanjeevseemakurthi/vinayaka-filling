package com.example.demo.Repository;

import com.example.demo.Entity.dailyexpenses;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface dailyexpensesRepository extends JpaRepository <dailyexpenses,Long> {
    List<dailyexpenses> findByTidAndType(Long tid,String type);
    Integer deleteMyEntityById(Long id);
}
