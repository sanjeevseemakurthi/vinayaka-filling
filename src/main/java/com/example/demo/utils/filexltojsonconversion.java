package com.example.demo.utils;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class filexltojsonconversion {
    public String convertxlstoCSV(InputStream inputStream) throws IOException {

        Workbook wb = WorkbookFactory.create(inputStream);

        return  csvConverter(wb.getSheetAt(0));
    }

    private String csvConverter(Sheet sheet) {
        Row row = null;
        String str = new String();
        for (int i = 0; i < sheet.getLastRowNum()+1; i++) {
            row = sheet.getRow(i);
            String rowString = new String();
            for (int j = 0; j < row.getLastCellNum(); j++) {
                if(row.getCell(j)==null) {
                    rowString = rowString + ' ' + ',';
                }
                else {
                    rowString = rowString + row.getCell(j)+',';
                }
            }
            str = str + rowString.substring(0,rowString.length()-1)+ "\r\n";
        }
        return str;
    }

    public  ByteArrayInputStream load(String data) {
        final CSVFormat format = CSVFormat.DEFAULT.withQuoteMode(QuoteMode.MINIMAL);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter csvPrinter = new CSVPrinter(new PrintWriter(out), format);) {
            csvPrinter.printRecord(data);
            csvPrinter.flush();
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException("fail to import data to CSV file: " + e.getMessage());
        }
    }
}
