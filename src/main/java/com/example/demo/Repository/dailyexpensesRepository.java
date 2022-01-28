package com.example.demo.Repository;

import com.example.demo.Entity.dailyexpenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface dailyexpensesRepository extends JpaRepository <dailyexpenses,Long> {
}
