package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.example.demo.Entity.stocks;
import org.springframework.stereotype.Repository;

@Repository
public interface stocksRepository extends JpaRepository<stocks,String> {

}
