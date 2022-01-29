package com.example.demo.Controllers;
import com.example.demo.Entity.people;
import com.example.demo.Entity.settings;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.stocks;
import com.example.demo.Repository.settingsRepository;
import com.example.demo.Repository.stocksRepository;

import com.example.demo.utils.filexltojsonconversion;

import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.jwtauth.userdetailsRepository;
import com.example.demo.Services.StocksService;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

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

	@Autowired
	public filexltojsonconversion filexltojsonconversion;

	@Autowired
	public com.example.demo.Repository.peopleRepository peopleRepository;

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
		int intevalnumber = 10;
		// check for start-date
		if(demo.has("startdate")){
			date = LocalDate.parse(demo.getString("startdate"));
		}
		// for interval
		if(demo.has("interval")) {
			inteval = demo.getInt("interval");
		}
		if(demo.has("intevalnumber")) {
			intevalnumber = demo.getInt("intevalnumber");
		}
		return stockservice.getstocksdatabyinterval(date,inteval,intevalnumber,userdata.getId());
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
		int intevalnumber = 10;
		// check for start-date
		if(demo.has("startdate")){
			date = LocalDate.parse(demo.getString("startdate"));
		}
		// for interval
		if(demo.has("interval")) {
			inteval = demo.getInt("interval");
		}
		if(demo.has("intevalnumber")) {
			intevalnumber = demo.getInt("intevalnumber");
		}
		return stockservice.getstocksdatabyintervalqty(date,inteval,intevalnumber,userdata.getId());
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
	@PostMapping("upload")
	@ResponseBody
	public String buluploadstocks(@RequestHeader(value = "Authorization") String authorization, @RequestParam("file") MultipartFile file) throws IOException {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);

		// getting settings data
		settings settingsdata[]  = settingsRepository.findByUserid(userdata.getId());
		HashMap<String,Long> keyvaluesettings = new HashMap<String,Long>();
		for (settings sdata : settingsdata) {
			keyvaluesettings.put(sdata.getProperty().toLowerCase(Locale.ROOT) + '-' + sdata.getSubproperty().toLowerCase(Locale.ROOT),sdata.getId());
		}

		// to get file extension
		String fileName = file.getOriginalFilename();
		String Extension = fileName.substring(fileName.lastIndexOf(".")+1);

		String completeData = "";
		try {
			if(Extension.startsWith("xl")) {
				// util to convert xl to csv
				completeData = filexltojsonconversion.convertxlstoCSV(file.getInputStream());
			} else if(Extension.startsWith("csv")) {

				byte[] bytes = file.getBytes();
				completeData = new String(bytes);
			}
			String[] rows = completeData.split("\r\n");
			ArrayList<stocks> filedatas= new ArrayList<stocks>();
			int i = 0;
			for (String s : rows) {
				if(i != 0) {
					stocks row = new stocks();
					String[] coldata  = s.split(",");
					row.setSettingsid(keyvaluesettings.get(coldata[0].toLowerCase(Locale.ROOT)+'-'+coldata[1].toLowerCase(Locale.ROOT)));
					row.setQty( Long.parseLong(coldata[3]));
					row.setAmount(Long.parseLong(coldata[4]));
					row.setPhno(coldata[5]);
					row.setUserid(userdata.getId());
					Boolean flag = false;
					if(coldata[6].equalsIgnoreCase("YES"))
					{
						flag = true;
						row.setStockflag(flag);
					} else if(coldata[6].equalsIgnoreCase("NO")) {
						flag = false;
						row.setStockflag(flag);
					}
					row.setInitialdate( LocalDate.parse(coldata[7], DateTimeFormatter.ofPattern("dd-MM-uuuu")));
					filedatas.add(row);
				}
				i++;
			}
			Collections.sort(filedatas,stocks.initldatecomparitator);
			Collections.sort(filedatas,stocks.settingsidcomparator);

			for (int counter = 0; counter < filedatas.size(); counter++) {

				int finalCounter = counter;
				CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> stockservice.addedinpreviousdate(filedatas.get(finalCounter)));
				while (!completableFuture.isDone()) {
				}
				long result = completableFuture.get();
			}
		} catch (Exception e) {
			JSONObject result = new JSONObject();
			result.put("status","Fail");
			return result.toString();
		}

		JSONObject result = new JSONObject();
		result.put("status","sucess");
		return result.toString();
	}

	@PostMapping("getpersonstocks")
	public String getpersonstocks(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {
		JSONObject demo = new JSONObject(data);
		long personid = demo.getLong("pid");
		JSONObject result =new JSONObject();
		people person[] = peopleRepository.serchbyid(personid);
		result.put("person",person);
		result.put("stocks",stocksRepository.findByPid(demo.getLong("pid")));
		return result.toString();
	}
	@GetMapping("/download")
	public String fooAsCSV(HttpServletResponse response) {
		response.setContentType("text/plain; charset=utf-8");
		String data = "Property,SubProperty,Name,Qty,Amount,Phno,Stocks/Sales(YES/NO),Date(dd-mm-yyy)";
		return data;
	}
}
