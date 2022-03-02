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
            expensedata.setDeposit(Long.valueOf(0));
            expensedata.setDiscription("stock added for property" + settingsdata.getProperty() + "subproperty" + settingsdata.getSubproperty());
        } else  {
            expensedata.setWithdraw(Long.valueOf(0));
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
        if(singlenode.getDailyexpenses() != null) {
        for (dailyexpenses eachnode: singlenode.getDailyexpenses()) {
            System.out.println(eachnode.getTid());
            if(eachnode.getTid() == data.getId() && eachnode.getType().equals("Accounts")) {
                eachnode.setDeposit(data.getDeposit());
                eachnode.setWithdraw(data.getWithdraw());
                eachnode.setDiscription(data.getDiscription());
                existdata = true;
            }
        }}
        if(!existdata){
            dailyexpenses changedata = new dailyexpenses();
            changedata.setDiscription(data.getDiscription());
            changedata.setUid(data.getUid());
            changedata.setDeposit(data.getDeposit());
            changedata.setWithdraw(data.getWithdraw());
            changedata.setType("Accounts");
            changedata.setTid(data.getId());
            if(singlenode.getDailyexpenses() == null) {
                List <dailyexpenses>  multiple =  new ArrayList<dailyexpenses>();
                multiple.add(changedata);
                singlenode.setDailyexpenses(multiple);
            } else  {
                singlenode.getDailyexpenses().add(changedata);
            }
        }
        daysheetRepository.save(singlenode);
        caluclatetota(data.getDate());
    }
    public void caluclatetota(LocalDate date) {
        List<daysheet> totaldata = daysheetRepository.findByDate(date);
        Long Total = Long.valueOf(0);
        if(totaldata.get(0).getDailyexpenses() != null) {
        for (dailyexpenses eachnode : totaldata.get(0).getDailyexpenses()) {
            Total = Total + eachnode.getWithdraw() - eachnode.getDeposit();
        }}
        totaldata.get(0).setClosingbalance(Total);
        daysheetRepository.save(totaldata.get(0));
    }
    public void addfinanceexpense(finance data) {
        List<daysheet> testdata = daysheetRepository.findByDateAndUid(data.getDate(),data.getUid());
        daysheet singlenode = null;
        if(testdata.size() == 0){
            singlenode = this.addnewsheet(data.getDate(),data.getUid());
        } else {
            singlenode = testdata.get(0);
        }
        Boolean existdata = false;
        System.out.println(data.getId());
        if(singlenode.getDailyexpenses() != null) {
        for (dailyexpenses eachnode: singlenode.getDailyexpenses()) {
            System.out.println(eachnode.getTid());
            if(eachnode.getTid() == data.getId() && eachnode.getType().equals("Finance")) {
                eachnode.setWithdraw(data.getAmount());
                existdata = true;
            }
        }}
        if(!existdata){
            dailyexpenses changedata = new dailyexpenses();
            changedata.setDiscription("Finance given");
            changedata.setUid(data.getUid());
            changedata.setDeposit(Long.valueOf(0));
            changedata.setWithdraw(data.getAmount());
            changedata.setType("Finance");
            changedata.setTid(data.getId());
            if(singlenode.getDailyexpenses() == null) {
                List <dailyexpenses>  multiple =  new ArrayList<dailyexpenses>();
                multiple.add(changedata);
                singlenode.setDailyexpenses(multiple);
            } else  {
                singlenode.getDailyexpenses().add(changedata);
            }
        }
        daysheetRepository.save(singlenode);
        caluclatetota(data.getDate());
        for (Deposits eachnode:data.getDeposits()) {
            addoredittransaction(eachnode);
        }
    }
    public void addoredittransaction(Deposits data) {
        List<daysheet> testdata = daysheetRepository.findByDateAndUid(data.getDate(),data.getUid());
        daysheet singlenode = null;
        if(testdata.size() == 0){
            singlenode = this.addnewsheet(data.getDate(),data.getUid());
        } else {
            singlenode = testdata.get(0);
        }
        // check for exist data
        Boolean existdata = false;
        if(singlenode.getDailyexpenses() != null) {
        for (dailyexpenses eachnode: singlenode.getDailyexpenses()) {
            System.out.println(eachnode.getTid());
            if(eachnode.getTid() == data.getId() && eachnode.getType().equals("Finance Transaction")) {
                eachnode.setDeposit(data.getDeposit());
                eachnode.setWithdraw(data.getWithdraw());
                eachnode.setDiscription(data.getDiscription());
                existdata = true;
            }
        }}
        if(!existdata){
            dailyexpenses changedata = new dailyexpenses();
            changedata.setDiscription(data.getDiscription());
            changedata.setUid(data.getUid());
            changedata.setDeposit(data.getDeposit());
            changedata.setWithdraw(data.getWithdraw());
            changedata.setType("Finance Transaction");
            changedata.setTid(data.getId());
            if(singlenode.getDailyexpenses() == null) {
                List <dailyexpenses>  multiple =  new ArrayList<dailyexpenses>();
                multiple.add(changedata);
                singlenode.setDailyexpenses(multiple);
            } else  {
                singlenode.getDailyexpenses().add(changedata);
            }
        }
        daysheetRepository.save(singlenode);
        caluclatetota(data.getDate());
    }
}
