package com.example.demo.Repository;

import com.example.demo.Entity.lent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface lentRepository extends JpaRepository<lent,Long> {
    lent[] findByPid(long pid);
}
