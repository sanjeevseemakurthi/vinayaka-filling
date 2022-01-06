package com.example.demo.Controllers;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.stocks;
import com.example.demo.Entity.settings;
import com.example.demo.Repository.settingsRepository;
import com.example.demo.Repository.stocksRepository;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.jwtauth.userdetailsRepository;
import com.example.demo.Services.StocksService;

import java.time.LocalDate;
import java.util.Date;

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

	@Autowired
	public  StocksService stockservice;
	
	
	@PostMapping("addstocks")
	@ResponseBody
	public String addStocks(@RequestHeader(value = "Authorization") String authorization, @RequestBody stocks data) {

		 String Token = authorization.replace("Bearer ","");
		 String username = jwtUtility.getUsernameFromToken(Token);
		 userdata userdata = userdetailsRepository.findByUsername(username);
		 data.setUserid(userdata.getId());
		 stockservice.addedinpreviousdate(data);
		 JSONObject result = new JSONObject();
		 result.put("status","sucess");
		 return result.toString();
	}

	@PostMapping("getstockbyamount")
	@ResponseBody
	public String getStocksbyday(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		JSONObject demo = new JSONObject(data);
		LocalDate date = LocalDate.now();
		int inteval = 7;
		// check for start-date
		if(demo.has("startdate")){
			date = LocalDate.parse(demo.getString("startdate"));
		}
		// for interval
		if(demo.has("interval")) {
			inteval = demo.getInt("interval");
		}
		return stockservice.getstocksdatabyinterval(date,inteval,userdata.getId());
	}

	@PostMapping("getstockbyqty")
	@ResponseBody
	public String getStocksbydayaty(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		JSONObject demo = new JSONObject(data);
		LocalDate date = LocalDate.now();
		int inteval = 7;
		// check for start-date
		if(demo.has("startdate")){
			date = LocalDate.parse(demo.getString("startdate"));
		}
		// for interval
		if(demo.has("interval")) {
			inteval = demo.getInt("interval");
		}
		return stockservice.getstocksdatabyintervalqty(date,inteval,userdata.getId());
	}
	@PostMapping("gettransactions")
	@ResponseBody
	public stocks[] getlatesttransactions(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		JSONObject demo = new JSONObject(data);
		LocalDate date = LocalDate.now();;
		int inteval = 30;
		// check for start-date
		if(demo.has("startdate")){
			date = LocalDate.parse(demo.getString("startdate"));
		}
		// for interval
		if(demo.has("interval")) {
			inteval = demo.getInt("interval");
		}
		return stockservice.getlatesttransactions(date,inteval,userdata.getId());
	}
	@PostMapping("deletetransactionbyid")
	@ResponseBody
	public String deletetransactionbyid(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		JSONObject demo = new JSONObject(data);
		Long transactionid = demo.getLong("id");
		return stockservice.deletetransactionbyid(transactionid,userdata.getId());
	}

	@PostMapping("editstocks")
	@ResponseBody
	public String updateStocks(@RequestHeader(value = "Authorization") String authorization, @RequestBody stocks data) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		data.setUserid(userdata.getId());

		stockservice.edittransactionbyid(data.getId(),data,data.getUserid());
		JSONObject result = new JSONObject();
		result.put("status","sucess");
		return result.toString();
	}

}
