package com.example.demo.Controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.Entity.settings;
import com.example.demo.Repository.settingsRepository;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.jwtauth.userdetailsRepository;

@Controller
public class settingsController {
	
	@Autowired
	public  settingsRepository settingsRepository;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	public userdetailsRepository userdetailsRepository;
	
	
	@PostMapping("api/addSettings")
	@ResponseBody
	public String addSettings(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

		 String Token = authorization.replace("Bearer ","");
		 String username = jwtUtility.getUsernameFromToken(Token);
		 userdata userdata = userdetailsRepository.findByUsername(username);
		 JSONObject demo = new JSONObject(data);
		 settings settingsdata = new  settings();
		 settingsdata.setProperty(demo.getString("property"));
		 settingsdata.setSubproperty(demo.getString("subproperty"));
		 settingsdata.setStockleft(0);
		 settingsdata.setUserid(userdata.getId());
		 settingsRepository.save(settingsdata);
	return "Sucess";
	}
	@PostMapping("api/getSettings")
	@ResponseBody
	public settings[] getSettings(@RequestHeader(value = "Authorization") String authorization) {

		 String Token = authorization.replace("Bearer ","");
		 String username = jwtUtility.getUsernameFromToken(Token);
		 userdata userdata = userdetailsRepository.findByUsername(username);
		settings data[] = settingsRepository.findByUserid(userdata.getId());
	return data;
	}
}