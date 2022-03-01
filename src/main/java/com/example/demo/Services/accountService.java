package com.example.demo.Services;

import com.example.demo.Entity.accounts;
import com.example.demo.Repository.AccountsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class accountService {
    @Autowired
    public AccountsRepository accountsRepository;
    @Autowired
    public expensesservice expensesservice;
    public  void  addaccounts(accounts data){
        List<accounts>  daydata = accountsRepository.getlatestdayorprevious(data.getDate(),data.getUid(),data.getPid());
        Integer t =0;
        accounts maxdata = null;
        if(daydata.size() !=0 ) {
            for (accounts eachrecord:daydata) {
                if(maxdata == null || maxdata.getId() < eachrecord.getId()) {
                    maxdata = eachrecord;
                }
            }
        }
        Long calculatetotal = t.longValue();
        if(maxdata != null){
            calculatetotal = maxdata.getTotal();
        }
        calculatetotal = calculatetotal  + data.getWithdraw() - data.getDeposit();
        data.setTotal(calculatetotal);
        accounts aftersave = accountsRepository.save(data);
        List<accounts>  afterdata =  accountsRepository.findgraterthangivendate(data.getDate(),data.getUid(),data.getPid());
        for (accounts eachrecord : afterdata) {
            calculatetotal = eachrecord.getTotal() - data.getDeposit() + data.getWithdraw();
            eachrecord.setTotal(calculatetotal);
        }
        if(afterdata.size() != 0) {
            List<accounts> test  = accountsRepository.saveAll(afterdata);
        }
        expensesservice.editorsaveaccounts(aftersave);
    }
    public void editaccounts(accounts data) {
        accounts existeddata = accountsRepository.getById(data.getId());
        Long differnce =existeddata.getTotal() - existeddata.getWithdraw() + existeddata.getDeposit() + data.getWithdraw() - data.getDeposit();
        Long change = differnce - existeddata.getTotal();
        data.setTotal(differnce);
        List<accounts> updatedata =accountsRepository.findByUidAndPidAndDateGreaterThanEqualAndIdGreaterThan(data.getUid(), data.getPid(),data.getDate(), data.getId());;
        for (accounts eachrow: updatedata) {
            eachrow.setTotal(eachrow.getTotal()+change);
            System.out.println(eachrow);
        }
        accounts aftersave = accountsRepository.save(data);
        System.out.println(aftersave.toString());
        accountsRepository.saveAll(updatedata);
        expensesservice.editorsaveaccounts(accountsRepository.getById(data.getId()));
    }
}
