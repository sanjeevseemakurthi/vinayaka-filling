package com.example.demo.Controllers;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.settings;
import com.example.demo.Repository.settingsRepository;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.jwtauth.userdetailsRepository;

import java.time.LocalDate;

@RestController
public class settingsController {
	
	@Autowired
	public  settingsRepository settingsRepository;
	
	@Autowired
	private JWTUtility jwtUtility;
	
	@Autowired
	public userdetailsRepository userdetailsRepository;
	
	
	@PostMapping("api/addSettings")
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

	@PostMapping("addSettingsmultiple")
	public String addSettingsmultiple(@RequestHeader(value = "Authorization") String authorization, @RequestBody settings data[]) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		for (settings settingsdata:data) {
			settingsdata.setCreateddate(LocalDate.now());
			settingsdata.setUserid(userdata.getId());
			settingsRepository.save(settingsdata);
		}
		JSONObject result = new JSONObject();
		result.put("status","sucess");
		return result.toString();
	}
	@PostMapping("editSettingsmultiple")
	public String editSettingsmultiple(@RequestHeader(value = "Authorization") String authorization, @RequestBody settings data[]) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		for (settings settingsdata:data) {
			settingsdata.setUserid(userdata.getId());
			settingsRepository.updatesettingsdata(settingsdata.getProperty(),settingsdata.getSubproperty(),settingsdata.getId());
		}
		JSONObject result = new JSONObject();
		result.put("status","sucess");
		return result.toString();
	}
	@PostMapping("deleteSettingsmultiple")
	public String deleteSettingsmultiple(@RequestHeader(value = "Authorization") String authorization, @RequestBody settings data[]) {

		String Token = authorization.replace("Bearer ","");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		for (settings settingsdata:data) {
			settingsRepository.delete(settingsdata);
		}
		JSONObject result = new JSONObject();
		result.put("status","sucess");
		return result.toString();
	}
	@GetMapping("getSettings")
	public String getSettings(@RequestHeader(value = "Authorization") String authorization) {
		 String Token = authorization.replace("Bearer ","");
		 String username = jwtUtility.getUsernameFromToken(Token);
		 userdata userdata = userdetailsRepository.findByUsername(username);
		 settings data[] = settingsRepository.findByUserid(userdata.getId());
		 JSONObject  result = new JSONObject ();
		for (settings node : data ) {
			if (result.has(node.getProperty())) {
				JSONObject test = new JSONObject();
				test.put("name",node.getSubproperty());
				test.put("id",node.getId());
				result.append(node.getProperty(), test);
			} else {
				result.put(node.getProperty(), new JSONArray());
				JSONObject test = new JSONObject();
				test.put("name",node.getSubproperty());
				test.put("id",node.getId());
				result.append(node.getProperty(), test);
			}
		}
	return result.toString();
	}
	@GetMapping("getSettingsall")
	public settings[] getSettingsintableform(@RequestHeader(value = "Authorization") String authorization) {
		String Token = authorization.replace("Bearer ", "");
		String username = jwtUtility.getUsernameFromToken(Token);
		userdata userdata = userdetailsRepository.findByUsername(username);
		settings data[] = settingsRepository.findByUserid(userdata.getId());
		return data;
	}
}