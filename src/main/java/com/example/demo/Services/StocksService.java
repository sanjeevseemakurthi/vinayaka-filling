package com.example.demo.Services;

import com.example.demo.Entity.settings;
import com.example.demo.Entity.stocks;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import  com.example.demo.Repository.settingsRepository;
import com.example.demo.Repository.stocksRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class StocksService {
    @Autowired
    public  settingsRepository settingsRepository;

    @Autowired
    public stocksRepository stocksRepository;

    public void addedinpreviousdate(stocks data ) {

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

        // this is for updating stock data in settings
        settings settings = settingsRepository.findById(data.getSettingsid());
        long settingsstockdata;
        long settingsstockamount;
        if(flag){
            settingsstockdata = settings.getStockleft()+data.getQty();
            settingsstockamount = settings.getStockamount() + data.getAmount();

        } else {
            settingsstockdata = settings.getStockleft()-data.getQty();
            settingsstockamount = settings.getStockamount() - data.getAmount();

        }
        settingsRepository.updatestocksleftamountbyid(settingsstockdata,settingsstockamount,settings.getId());

        // this is for updating current record status
        long caluclatestockdata;
        long caluclatestockamount;
        long daystock= 0;
        long daysales = 0;
        long daystockamount = 0;
        long daysaleamount =0;
        if(flag){
            caluclatestockdata =  data.getQty();
            caluclatestockamount = data.getAmount();
            daystock =  data.getQty();
            daystockamount = data.getAmount();
        } else {
            caluclatestockdata = -data.getQty();
            caluclatestockamount = -data.getAmount();
            daysales =  data.getQty();
            daysaleamount = data.getAmount();
        }
        // this for updating latest stock left  to that date or before
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
        // update for latest flag
        stocks latestflagchange = stocksRepository.getlatestday(data.getInitialdate(),data.getUserid(),data.getSettingsid(),true);
        if(latestflagchange != null) {
            if (stockschanageflag != null) {
                if (flag) {
                    daystock= stockschanageflag.getDaystocks()+data.getQty();
                    daystockamount = stockschanageflag.getDaystockamount()+data.getAmount();
                    daysales = stockschanageflag.getDaysales();
                    daysaleamount = stockschanageflag.getDaysalesamount();
                } else {
                    daystock= stockschanageflag.getDaystocks();
                    daystockamount = stockschanageflag.getDaystockamount();
                    daysales = stockschanageflag.getDaysales()+data.getQty();
                    daysaleamount = stockschanageflag.getDaysalesamount()+data.getAmount();

                }
            }
            stocksRepository.updateFlagById(latestflagchange.getId(),false);
        }

        data.setDaysales(daysales);
        data.setDaystocks(daystock);
        data.setDaystockamount(daystockamount);
        data.setDaysalesamount(daysaleamount);
        data.setDaylatest(true);
        data.setLeftqty(caluclatestockdata);
        data.setLeftamount(caluclatestockamount);
        stocksRepository.save(data);
    }
    public String getstocksdatabyinterval(LocalDate date, int interval,Long userid) {
        List <LocalDate> startdates=new ArrayList<>();
        List <LocalDate> enddates=new ArrayList<>();
        for( int i =0;i< 10; i++) {
            enddates.add(date.minusDays((i+1)*interval));
            startdates.add(date.minusDays(i*interval));
        }
        settings settingsdata[] = settingsRepository.findByUserid(userid);
        JSONObject finalresult = new JSONObject();
        List <stocks> subproperties = new ArrayList<stocks>();
        for (settings node : settingsdata ) {
            JSONObject propertiesdata = new JSONObject();
            String propertyname = null;
            String subproperty = null;
            for (int i= 0;i<10;i++) {
                List<Long[]> datas = stocksRepository.getstocksbydaterange(startdates.get(i), enddates.get(i), userid, node.getId(), true);
                String datecoversion = (startdates.get(i)).toString() +"-"+ (enddates.get(i)).toString();
                propertiesdata.append(datecoversion,datas.get(0));
                propertyname = node.getProperty();
                subproperty = node.getSubproperty();
            }
            JSONObject  subresult = new JSONObject ();
            subresult.put(subproperty,propertiesdata);
            if (finalresult.has(propertyname)) {
                subresult = finalresult.getJSONObject(propertyname);
                subresult.append(subproperty,propertiesdata);
                finalresult.put(propertyname,subresult);
            } else {
                subresult.put(subproperty,propertiesdata);
                finalresult.put(propertyname,subresult);
            }
        }
        return finalresult.toString();
    }

    public  stocks[] getlatesttransactions(LocalDate date, int interval,Long userid) {
        LocalDate enddate = date.minusDays(interval);
        stocks stocksdata[]  = stocksRepository.gettransactionsbydaterange(date, enddate,userid);
        return stocksdata;
    }
}
