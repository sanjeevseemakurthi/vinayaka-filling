package com.example.demo.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

import com.example.demo.Entity.settings;

@Controller
public interface settingsRepository extends JpaRepository<settings,String>{

	<List> settings[] findByUserid(Long id); 
}
