package com.example.demo.Controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.stocks;
import com.example.demo.Repository.settingsRepository;
import com.example.demo.Repository.stocksRepository;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.jwtauth.userdetailsRepository;

@RestController
public class StocksController {

	@Autowired
	public  settingsRepository settingsRepository;
	
	@Autowired
	public  stocksRepository stocksRepository;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	public userdetailsRepository userdetailsRepository;
	
	
	@PostMapping("api/addstocks")
	@ResponseBody
	public String addSettings(@RequestHeader(value = "Authorization") String authorization, @RequestBody stocks data) {

		 String Token = authorization.replace("Bearer ","");
		 String username = jwtUtility.getUsernameFromToken(Token);
		 userdata userdata = userdetailsRepository.findByUsername(username);
//		 JSONObject demo = new JSONObject(data);
		 System.out.println(data);
	return "test";
	}
}
