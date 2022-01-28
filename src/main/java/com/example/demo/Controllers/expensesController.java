package com.example.demo.Controllers;

import com.example.demo.Entity.daysheet;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
public class expensesController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;
    @Autowired
    public com.example.demo.Repository.daysheetRepository daysheetRepository;

    @PostMapping("addexpense")
    public String addexpense(@RequestHeader(value = "Authorization") String authorization, @RequestBody daysheet data) {

        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        data.setUid(userdata.getId());
        daysheetRepository.save(data);

        JSONObject result = new JSONObject();
        result.put("reesult","sucess");
        return result.toString();
    }
    @PostMapping("getexpense")
    public String getexpense(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {

        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject demo = new JSONObject(data);
        LocalDate date = LocalDate.now();
        if(demo.has("date")) {
            date =   LocalDate.parse(demo.getString("date"));
        }
        JSONObject result = new JSONObject();
        daysheet[] datafromquery = daysheetRepository.getdatabydate(date,userdata.getId());
        result.put("expenses",datafromquery);
        return result.toString();
    }

}
