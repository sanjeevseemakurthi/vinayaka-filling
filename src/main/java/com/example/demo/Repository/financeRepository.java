package com.example.demo.Repository;

import com.example.demo.Entity.finance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface financeRepository  extends JpaRepository<finance,Long> {
    finance[] findByPid(long pid);
}
