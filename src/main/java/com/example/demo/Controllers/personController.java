package com.example.demo.Controllers;

import com.example.demo.Entity.finance;
import com.example.demo.Entity.people;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class personController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;

    @Autowired
    public com.example.demo.Repository.peopleRepository peopleRepository;

    @PostMapping("addnewperson")
    @ResponseBody
    public people addnewperson(@RequestHeader(value = "Authorization") String authorization, @RequestBody people data) {
        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);

        data.setUid(userdata.getId());
        people aftersave = peopleRepository.save(data);
        return  aftersave;
    }

}
