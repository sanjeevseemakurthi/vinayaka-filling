package com.example.demo.Controllers;

import com.example.demo.Entity.finance;
import com.example.demo.Entity.lent;
import com.example.demo.Entity.people;
import com.example.demo.Services.expensesservice;
import com.example.demo.jwtauth.JWTUtility;
import com.example.demo.jwtauth.userdata;
import com.example.demo.requestresponsejsons.addnewpersonfin;
import com.example.demo.requestresponsejsons.addnewpersonlint;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LentController {
    @Autowired
    private JWTUtility jwtUtility;

    @Autowired
    public com.example.demo.jwtauth.userdetailsRepository userdetailsRepository;
    @Autowired
    public com.example.demo.Repository.lentRepository lentRepository;

    @Autowired
    public com.example.demo.Repository.peopleRepository peopleRepository;

    @Autowired
    public  expensesservice expensesservice;

    @PostMapping("addlenttoexistingpeople")
    public String addlenttoexistingpeople(@RequestHeader(value = "Authorization") String authorization, @RequestBody lent data) {

        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        data.setUid(userdata.getId());
        lent aftersave = lentRepository.save(data);
        expensesservice.editorsavefromlent(aftersave);
        JSONObject result = new JSONObject();
        result.put("reesult","sucess");
        return result.toString();
    }
    @PostMapping("addnewpersonlent")
    public String addnewpersonlent(@RequestHeader(value = "Authorization") String authorization, @RequestBody addnewpersonlint data) {

        String Token = authorization.replace("Bearer ","");
        String username = jwtUtility.getUsernameFromToken(Token);
        userdata userdata = userdetailsRepository.findByUsername(username);
        people peopledata =data.getPeopledata();
        lent lentdata = data.getLentdata();
        peopledata.setUid(userdata.getId());
        lentdata.setUid(userdata.getId());
        people aftersavepeople = peopleRepository.save(peopledata);

        while (aftersavepeople == null) {

        }
        lentdata.setPid(aftersavepeople.getId());
        lent aftersave = lentRepository.save(lentdata);
        expensesservice.editorsavefromlent(aftersave);
        return "Sucess";
    }

    @PostMapping("getpersonlent")
    public String getpersonlent(@RequestHeader(value = "Authorization") String authorization, @RequestBody String data) {
        JSONObject demo = new JSONObject(data);
        long personid = demo.getLong("pid");
        JSONObject result =new JSONObject();
        people person[] = peopleRepository.serchbyid(personid);
        result.put("person",person);
        result.put("lent",lentRepository.findByPid(demo.getLong("pid")));
        return result.toString();
    }

}
