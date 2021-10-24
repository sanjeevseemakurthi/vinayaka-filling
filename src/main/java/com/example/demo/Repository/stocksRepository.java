package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.example.demo.Entity.stocks;

@Controller
public interface stocksRepository extends JpaRepository<stocks,String> {

}
