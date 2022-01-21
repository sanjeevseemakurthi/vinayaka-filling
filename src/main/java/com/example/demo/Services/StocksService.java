package com.example.demo.Services;

import com.example.demo.Entity.settings;
import com.example.demo.Entity.stocks;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
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


    public Long addedinpreviousdate(stocks data ) {

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
        settings settingsupdate = settingsRepository.findById(data.getSettingsid());
        long settingsstockdata = data.getQty();
        long settingsstockamount = data.getAmount();
        System.out.println(settingsupdate.toString());
        if(settingsupdate != null){
            if(flag){
                settingsstockdata = settingsupdate.getStockleft()+data.getQty();
                settingsstockamount = settingsupdate.getStockamount() + data.getAmount();

            } else {
                settingsstockdata = settingsupdate.getStockleft()-data.getQty();
                settingsstockamount = settingsupdate.getStockamount() - data.getAmount();

            }
        }
        settingsRepository.updatestocksleftamountbyid(settingsstockdata,settingsstockamount,settingsupdate.getId());

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
        return Long.valueOf(1);
    }
    public String getstocksdatabyinterval(LocalDate date, int interval,int intevalnumber,Long userid) {
        List <LocalDate> startdates=new ArrayList<>();
        List <LocalDate> enddates=new ArrayList<>();
        for( int i =0;i< intevalnumber; i++) {
            enddates.add(date.minusDays((i+1)*interval));
            startdates.add(date.minusDays(i*interval));
        }
        settings settingsdata[] = settingsRepository.findByUserid(userid);
        JSONArray  subresult = new JSONArray ();
        for (settings node : settingsdata ) {
            JSONArray  result = new JSONArray ();
            JSONObject propertydata = new JSONObject();
            result.put(propertydata.append("property", node.getProperty()));
            JSONObject subpropertydata = new JSONObject();
            result.put(subpropertydata.append("subproperty", node.getSubproperty()));
            for (int i= 0;i<intevalnumber;i++) {
                JSONObject propertiesdata = new JSONObject();
                List<Long[]> datas = stocksRepository.getstocksbydaterange(startdates.get(i), enddates.get(i), userid, node.getId(), true);
                String datecoversion = (startdates.get(i)).toString() +","+ (enddates.get(i)).toString();
                propertiesdata.append(datecoversion,datas.get(0));
                result.put(propertiesdata);
            }
            subresult.put(result);
        }
        return subresult.toString();
    }
    public String getstocksdatabyintervalqty(LocalDate date, int interval,int intevalnumber,Long userid) {
        List <LocalDate> startdates=new ArrayList<>();
        List <LocalDate> enddates=new ArrayList<>();
        for( int i =0;i< intevalnumber; i++) {
            enddates.add(date.minusDays((i+1)*interval));
            startdates.add(date.minusDays(i*interval));
        }
        settings settingsdata[] = settingsRepository.findByUserid(userid);
        JSONArray  subresult = new JSONArray ();
        for (settings node : settingsdata ) {
            JSONArray  result = new JSONArray ();
            JSONObject propertydata = new JSONObject();
            result.put(propertydata.append("property", node.getProperty()));
            JSONObject subpropertydata = new JSONObject();
            result.put(subpropertydata.append("subproperty", node.getSubproperty()));
            for (int i= 0;i<intevalnumber;i++) {
                JSONObject propertiesdata = new JSONObject();
                List<Long[]> datas = stocksRepository.getstocksbydaterangeqty(startdates.get(i), enddates.get(i), userid, node.getId(), true);
                String datecoversion = (startdates.get(i)).toString() +","+ (enddates.get(i)).toString();
                propertiesdata.append(datecoversion,datas.get(0));
                result.put(propertiesdata);
            }
            subresult.put(result);
        }
        return subresult.toString();
    }
    public  stocks[] getlatesttransactions(LocalDate date, int interval,Long userid) {
        LocalDate enddate = date.minusDays(interval);
//        stocks stocksdata[]  = stocksRepository.gettransactionsbydaterange(date, enddate,userid);
        stocks stocksdata[]  = stocksRepository.gettransactionslatest(userid);
        return stocksdata;
    }

    public String deletetransactionbyid(Long id,Long userid) {
        stocks stockdata = stocksRepository.gettransactionbyid(id);
        stocks stocksdata[]  = stocksRepository.getalltransactionafterthedate(stockdata.getInitialdate(),stockdata.getSettingsid(),stockdata.getUserid());
        for (stocks updatedata : stocksdata) {
            if(updatedata.getId() > stockdata.getId() || updatedata.getInitialdate().isAfter( stockdata.getInitialdate())) {
                if(stockdata.getStockflag()) {
                    updatedata.setLeftamount(updatedata.getLeftamount() - stockdata.getAmount());
                    updatedata.setLeftqty(updatedata.getLeftqty() - stockdata.getQty());
                    if(updatedata.getInitialdate().isEqual( stockdata.getInitialdate())) {
                        updatedata.setDaystocks(updatedata.getDaystocks() - stockdata.getQty());
                        updatedata.setDaystockamount(updatedata.getDaystockamount() - stockdata.getAmount());
                    }
                } else  {
                    updatedata.setLeftamount(updatedata.getLeftamount() + stockdata.getAmount());
                    updatedata.setLeftqty(updatedata.getLeftqty() + stockdata.getQty());
                    if (updatedata.getInitialdate().isEqual(stockdata.getInitialdate())) {
                        updatedata.setDaysales(updatedata.getDaysales() - stockdata.getQty());
                        updatedata.setDaysalesamount(updatedata.getDaysalesamount() - stockdata.getAmount());
                    }
                }
                stocksRepository.updateallqtydetails(updatedata.getId(),updatedata.getLeftqty(),updatedata.getLeftamount(),updatedata.getDaystocks(),updatedata.getDaystockamount(),updatedata.getDaysales(),updatedata.getDaysalesamount());
            }

        }
        // updating settings data
        settings dataofsettings = settingsRepository.findById(stockdata.getSettingsid());
        if(stockdata.getStockflag()) {
            settingsRepository.updatestocksleftamountbyid(dataofsettings.getStockleft()-stockdata.getQty(),
                    dataofsettings.getStockamount() - stockdata.getAmount(),dataofsettings.getId());
        } else {
            settingsRepository.updatestocksleftamountbyid(dataofsettings.getStockleft() + stockdata.getQty(),
                    dataofsettings.getStockamount() + stockdata.getAmount(),dataofsettings.getId());
        }
        // deleting transaction
        stocksRepository.deletestockrow(stockdata.getId());
        // updating flag of latest transaction
        if(stockdata.getDaylatest() == true) {
            Long previouslatestid =  stocksRepository.getpreviouslatesttransaction(stockdata.getInitialdate(),stockdata.getSettingsid(),stockdata.getUserid());
            if(previouslatestid != null) {
                stocksRepository.updateFlagById(previouslatestid,true);
            }
        }


        JSONObject result = new JSONObject();
        result.put("status","sucess");
        return result.toString();
    }
    public String edittransactionbyid(Long id, stocks stockdatafromresponse,Long userid) {
        stocks stockdata = stocksRepository.gettransactionbyid(id);
        if (stockdatafromresponse.getAmount() - stockdata.getAmount() != 0 || stockdatafromresponse.getQty() - stockdata.getQty() != 0) {
            stocks stocksdata[] = stocksRepository.getalltransactionafterthedate(stockdata.getInitialdate(), stockdata.getSettingsid(), stockdata.getUserid());
            for (stocks updatedata : stocksdata) {
                if (updatedata.getId() >= stockdata.getId() || updatedata.getInitialdate().isAfter(stockdata.getInitialdate())) {
                    if (stockdata.getStockflag()) {
                        updatedata.setLeftamount(updatedata.getLeftamount() - stockdata.getAmount() + stockdatafromresponse.getAmount());
                        updatedata.setLeftqty(updatedata.getLeftqty() - stockdata.getQty() + stockdatafromresponse.getQty());
                        if (updatedata.getInitialdate().isEqual(stockdata.getInitialdate())) {
                            updatedata.setDaystocks(updatedata.getDaystocks() - stockdata.getQty() + stockdatafromresponse.getQty());
                            updatedata.setDaystockamount(updatedata.getDaystockamount() - stockdata.getAmount() + stockdatafromresponse.getAmount());
                        }
                    } else {
                        updatedata.setLeftamount(updatedata.getLeftamount() + stockdata.getAmount() - stockdatafromresponse.getAmount());
                        updatedata.setLeftqty(updatedata.getLeftqty() + stockdata.getQty() - stockdatafromresponse.getQty());
                        if (updatedata.getInitialdate().isEqual(stockdata.getInitialdate())) {
                            updatedata.setDaysales(updatedata.getDaysales() - stockdata.getQty() + stockdatafromresponse.getQty());
                            updatedata.setDaysalesamount(updatedata.getDaysalesamount() - stockdata.getAmount() + stockdatafromresponse.getAmount());
                        }
                    }
                    if (updatedata.getId() == stockdata.getId()) {
                        stockdatafromresponse.setDaysalesamount(updatedata.getDaysalesamount());
                        stockdatafromresponse.setDaystockamount(updatedata.getDaystockamount());
                        stockdatafromresponse.setDaysales(updatedata.getDaysales());
                        stockdatafromresponse.setDaystocks(updatedata.getDaystocks());
                        stockdatafromresponse.setLeftamount(updatedata.getLeftamount());
                        stockdatafromresponse.setLeftqty(updatedata.getLeftqty());
                    } else {
                        stocksRepository.updateallqtydetails(updatedata.getId(), updatedata.getLeftqty(), updatedata.getLeftamount(), updatedata.getDaystocks(), updatedata.getDaystockamount(), updatedata.getDaysales(), updatedata.getDaysalesamount());
                    }
                }

            }
            // updating settings data
            settings dataofsettings = settingsRepository.findById(stockdata.getSettingsid());
            if (stockdata.getStockflag()) {
                settingsRepository.updatestocksleftamountbyid(dataofsettings.getStockleft() - stockdata.getQty() +stockdatafromresponse.getQty(),
                        dataofsettings.getStockamount() - stockdata.getAmount()+stockdatafromresponse.getAmount(), dataofsettings.getId());
            } else {
                settingsRepository.updatestocksleftamountbyid(dataofsettings.getStockleft() + stockdata.getQty()-stockdatafromresponse.getQty(),
                        dataofsettings.getStockamount() + stockdata.getAmount()-stockdatafromresponse.getAmount(), dataofsettings.getId());
            }
        }
        stocksRepository.save(stockdatafromresponse);
        JSONObject result = new JSONObject();
        result.put("status","sucess");
        return result.toString();
    }
}
