package com.example.demo.jwtauth;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;

@Controller
public interface userdetailsRepository extends JpaRepository<userdata,String>{
	<List>userdata findByUsername(String s); 

}
