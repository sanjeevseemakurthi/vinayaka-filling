package com.example.demo.Controllers;

import com.example.demo.Entity.accounts;
import com.example.demo.Repository.AccountsRepository;
import com.example.demo.Repository.peopleRepository;
import com.example.demo.Services.accountService;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class accountController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;

    @Autowired
    public AccountsRepository accountsRepository;
    @Autowired
    public peopleRepository peopleRepository;
    @Autowired
    public accountService accountService;
    @PostMapping("addaccount")
    public String addaccount(@RequestHeader(value = "Authorization") String authorization, @RequestBody accounts data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        data.setUid(userdata.getId());
        accountService.addaccounts(data);
        JSONObject result = new JSONObject();
        result.put("reesult","sucess");
        return result.toString();
    }
    @PostMapping("getpersonaccount")
    public String getpersonaccount(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        JSONObject demo = new JSONObject(data);
        Long id = demo.getLong("pid");
        JSONObject result = new JSONObject(data);
        result.put("person",peopleRepository.serchbyid(id));
        result.put("Accounts",accountsRepository.getByPidAndUid(id,userdata.getId()));
        return result.toString();
    }
    @PostMapping("editaccount")
    public String editaccount(@RequestHeader(value = "Authorization") String authorization, @RequestBody accounts data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        data.setUid(userdata.getId());
        accountService.editaccounts(data);
        JSONObject result = new JSONObject();
        result.put("reesult","sucess");
        return result.toString();
    }
    }
