package com.example.demo.Services;

import com.example.demo.Entity.settings;
import com.example.demo.Entity.stocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.example.demo.Repository.settingsRepository;
import com.example.demo.Repository.stocksRepository;
@Service
public class StocksService {
    @Autowired
    public  settingsRepository settingsRepository;

    @Autowired
    public stocksRepository stocksRepository;

    public void addedinpreviousdate(stocks data ) {
        settings settings = settingsRepository.findById(data.getSettingsid());
        // this is for looping grater data and changing quantity and stockamount
        stocks[] stocks = stocksRepository.findgraterthangivendate(data.getInitialdate(),data.getUserid(),data.getSettingsid());
        long updatestockdata;
        long updatestockamount;
        boolean flag = data.getStockflag();
        for (stocks dataofdata:stocks) {
            if(flag) {
                updatestockdata = dataofdata.getLeftqty() +data.getQty();
                updatestockamount = dataofdata.getLeftamount() + data.getAmount();
            } else {
                updatestockdata = dataofdata.getLeftqty()-data.getQty();
                updatestockamount = dataofdata.getLeftamount() - data.getAmount();
            }
            stocksRepository.updateQtyAmount(dataofdata.getId(),updatestockdata,updatestockamount);
        }

        // this is for updating stock data in settings and new property adds newly
        long caluclatestockdata;
        long caluclatestockamount;
        if(flag){
            caluclatestockdata = settings.getStockleft()+data.getQty();
            caluclatestockamount = settings.getStockamount() + data.getAmount();
        } else {
            caluclatestockdata = settings.getStockleft()-data.getQty();
            caluclatestockamount = settings.getStockamount() - data.getAmount();
        }
        settingsRepository.updatestocksleftamountbyid(caluclatestockdata,caluclatestockamount,settings.getId());

        if(flag){
            caluclatestockdata =  data.getQty();
            caluclatestockamount = data.getAmount();
        } else {
            caluclatestockdata = -data.getQty();
            caluclatestockamount = -data.getAmount();
        }
        // this for updating latest flag of the date
        stocks stockschanageflag = stocksRepository.getlatestdayorprevious(data.getInitialdate(),data.getUserid(),data.getSettingsid(),true);
        if (stockschanageflag != null) {
            if(flag){
                caluclatestockdata = stockschanageflag.getLeftqty()+data.getQty();
                caluclatestockamount = stockschanageflag.getLeftamount() + data.getAmount();
            } else {
                caluclatestockdata = stockschanageflag.getLeftqty()-data.getQty();
                caluclatestockamount = stockschanageflag.getLeftamount() - data.getAmount();
            }
        }
        stocks latestflagchange = stocksRepository.getlatestday(data.getInitialdate(),data.getUserid(),data.getSettingsid(),true);
        if(latestflagchange != null) {
            stocksRepository.updateFlagById(latestflagchange.getId(),false);
        }
        data.setDaylatest(true);
        data.setLeftqty(caluclatestockdata);
        data.setLeftamount(caluclatestockamount);
        stocksRepository.save(data);
    }
}
