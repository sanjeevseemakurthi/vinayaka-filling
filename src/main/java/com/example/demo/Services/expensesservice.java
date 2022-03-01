package com.example.demo.Services;

import com.example.demo.Entity.*;
import com.example.demo.Repository.dailyexpensesRepository;
import com.example.demo.Repository.daysheetRepository;
import com.example.demo.Repository.settingsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class expensesservice {
    @Autowired
    public dailyexpensesRepository dailyexpensesRepository;
    @Autowired
    public daysheetRepository daysheetRepository;

    @Autowired
    public settingsRepository settingsRepository;

    public void addexpensesfromstocks(stocks aftersave ) {

        settings settingsdata = settingsRepository.findById(aftersave.getSettingsid());
        // expenses data one record

        dailyexpenses expensedata = new dailyexpenses();
        expensedata.setUid(aftersave.getUserid());
        expensedata.setType("Stocks");
        expensedata.setTid(aftersave.getId());
        if(aftersave.getStockflag()) {
            expensedata.setWithdraw(aftersave.getAmount());
            expensedata.setDiscription("stock added for property" + settingsdata.getProperty() + "subproperty" + settingsdata.getSubproperty());
        } else  {
            expensedata.setDeposit(aftersave.getAmount());
            expensedata.setDiscription("sales added for property" + settingsdata.getProperty() + "subproperty" + settingsdata.getSubproperty());
        }

        // for existing data
        List<daysheet> daydata =  daysheetRepository.findByDateAndUid(aftersave.getInitialdate(),aftersave.getUserid());
        if(daydata.size() == 0) {
            daysheet daysheetdata = new daysheet();
            daysheetdata.setUid(aftersave.getUserid());
            daysheetdata.setDate(aftersave.getInitialdate());
            List<dailyexpenses> arrdata =  new ArrayList<dailyexpenses>();
            arrdata.add(expensedata);
            daysheetdata.setDailyexpenses(arrdata);
            daydata.add(daysheetdata);
        } else  {
            List<dailyexpenses> datafromquery =daydata.get(0).getDailyexpenses();
            datafromquery.add(expensedata);
            daydata.get(0).setDailyexpenses(datafromquery);
        }
        daysheetRepository.save(daydata.get(0));
    }
    public void editexpensesfromstocks(stocks aftersave ) {
        List<dailyexpenses> data = dailyexpensesRepository.findByTidAndTypeAndUid(aftersave.getId(),"Stocks", aftersave.getUserid());
        if(aftersave.getStockflag()){
            data.get(0).setWithdraw(aftersave.getAmount());
        } else  {
            data.get(0).setDeposit(aftersave.getAmount());
        }
        dailyexpensesRepository.save(data.get(0));
    }
    public void deleteexpensesfromstocks(stocks aftersave ) {
        List<dailyexpenses> data = dailyexpensesRepository.findByTidAndTypeAndUid(aftersave.getId(),"Stocks",aftersave.getUserid());
        dailyexpensesRepository.deleteMyEntityById(data.get(0).getId());
    }
    public  void  editorsavefromlent(lent aftersave) {
        this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),aftersave.getDate(),"Lent",aftersave.isFromperson(),aftersave.getAmount(),aftersave.getItem());
        if(aftersave.getDeposits() != null && aftersave.getDeposits().length !=0){
            for (Deposits data: aftersave.getDeposits()) {
                this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),data.getDate(),"Lent-Deposit",aftersave.isFromperson(),data.getAmount(),aftersave.getItem());
            }
        }
        if(aftersave.getGiveextra() != null  && aftersave.getGiveextra().length !=0 ){
            for (Deposits data: aftersave.getGiveextra()) {
                this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),data.getDate(),"Lent-AddedExtra",aftersave.isFromperson(),data.getAmount(),aftersave.getItem());
            }
        }
    }
    public  void  editorsavefromfinance(finance aftersave) {
        this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),aftersave.getDate(),"Finance",aftersave.isFromperson(),aftersave.getAmount(),aftersave.getItem());
       if( aftersave.getDeposits() != null && aftersave.getDeposits().length !=0) {
           for (Deposits data: aftersave.getDeposits()) {
               this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),data.getDate(),"Finance-Deposit",aftersave.isFromperson(),data.getAmount(),aftersave.getItem());
           }
       }
       if(aftersave.getGiveextra() != null && aftersave.getGiveextra().length != 0 ){
           for (Deposits data: aftersave.getGiveextra()) {
               this.editorsaveexpense(aftersave.getUid(),aftersave.getId(),data.getDate(),"Finance-AddedExtra",aftersave.isFromperson(),data.getAmount(),aftersave.getItem());
           }
       }
    }
    public  void  editorsaveexpense(Long uid,Long tid,LocalDate tdate,String Type,Boolean fromperson,Long amount,String item){
        dailyexpenses expensedata = new dailyexpenses();
        expensedata.setUid(uid);
        expensedata.setTid(tid);
        expensedata.setType(Type);
        if(Type.equals("Finance-Deposit") || Type.equals("Lent-Deposit")) {
            if(fromperson){
                expensedata.setWithdraw(amount);
                expensedata.setDiscription("Your"+Type + " for "+item);
            } else  {
                expensedata.setDeposit(amount);
                expensedata.setDiscription(Type + " for "+item);
            }
        } else  {
            if(!fromperson){
                expensedata.setWithdraw(amount);
                expensedata.setDiscription(Type + " for "+item);
            } else  {
                expensedata.setDeposit(amount);
                expensedata.setDiscription("Your "+Type + " for "+item);
            }
        }

        List<daysheet> testdata = daysheetRepository.findByDateAndUid(tdate,uid);
        daysheet singlenode = null;
        if(testdata.size() == 0){
            singlenode = this.addnewsheet(tdate,uid);
        } else {
            singlenode = testdata.get(0);
        }
        Long totalsum = Long.valueOf(0);
        Boolean nodeexist = false;
        if(singlenode.getDailyexpenses() != null) {
           if(singlenode.getDailyexpenses().size() != 0) {
               for (dailyexpenses everyexpense : singlenode.getDailyexpenses()) {
                   if(everyexpense.getType().equals(Type) && everyexpense.getTid() == tid){
                       nodeexist = true;
                       everyexpense.setDeposit(expensedata.getDeposit());
                       everyexpense.setWithdraw(expensedata.getWithdraw());
                   } else {
                       totalsum = totalsum +  everyexpense.getWithdraw() - everyexpense.getDeposit();
                   }
               }
           }
        }
        if(!nodeexist){
            List<dailyexpenses> appendata = new ArrayList<dailyexpenses>();
            if(singlenode.getDailyexpenses() != null){
                appendata = singlenode.getDailyexpenses();
            }
            appendata.add(expensedata);
            singlenode.setDailyexpenses(appendata);
        }
        totalsum = totalsum +  expensedata.getDeposit() - expensedata.getWithdraw();
        singlenode.setClosingbalance(totalsum);
        daysheetRepository.save(singlenode);
    }
    public  daysheet  addnewsheet(LocalDate date,Long Uid){
        daysheet daysheetdata = new daysheet();
        daysheetdata.setUid(Uid);
        daysheetdata.setDate(date);
        return daysheetRepository.save(daysheetdata);
    }
    public  void editorsaveaccounts(accounts data) {
        List<daysheet> testdata = daysheetRepository.findByDateAndUid(data.getDate(),data.getUid());
        daysheet singlenode = null;
        if(testdata.size() == 0){
            singlenode = this.addnewsheet(data.getDate(),data.getUid());
        } else {
            singlenode = testdata.get(0);
        }
        // check for exist data
        Boolean existdata = false;
        System.out.println(data.getId());
        for (dailyexpenses eachnode: singlenode.getDailyexpenses()) {
            System.out.println(eachnode.getTid());
            if(eachnode.getTid() == data.getId() && eachnode.getType().equals("Accounts")) {
                eachnode.setDeposit(data.getDeposit());
                eachnode.setWithdraw(data.getWithdraw());
                eachnode.setDiscription(data.getDiscription());
                existdata = true;
            }
        }
        if(!existdata){
            dailyexpenses changedata = new dailyexpenses();
            changedata.setDiscription(data.getDiscription());
            changedata.setUid(data.getUid());
            changedata.setDeposit(data.getDeposit());
            changedata.setWithdraw(data.getWithdraw());
            changedata.setType("Accounts");
            changedata.setTid(data.getId());
            singlenode.getDailyexpenses().add(changedata);
        }
        daysheetRepository.save(singlenode);
        caluclatetota(data.getDate());
    }
    public void caluclatetota(LocalDate date) {
        List<daysheet> totaldata = daysheetRepository.findByDate(date);
        Long Total = Long.valueOf(0);
        for (dailyexpenses eachnode : totaldata.get(0).getDailyexpenses()) {
            Total = Total + eachnode.getWithdraw() - eachnode.getDeposit();
        }
        totaldata.get(0).setClosingbalance(Total);
        daysheetRepository.save(totaldata.get(0));
    }
}
