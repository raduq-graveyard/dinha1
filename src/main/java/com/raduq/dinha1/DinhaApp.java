package com.raduq.dinha1;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Dell on 04/02/2017.
 */
public class DinhaApp {

    public static void main(String... args) throws IOException, InvalidFormatException {
        int keyA = Integer.valueOf(args[2]);
        int keyB = Integer.valueOf(args[3]);
        int cellA = Integer.valueOf(args[4]);
        int cellB = Integer.valueOf(args[5]);

        Sheet file1 = new Sheet(args[0], keyA, cellA);
        Sheet file2 = new Sheet(args[1], keyB, cellB);

        List<MergedRow> mergedRows = file1.merge(file2);

        mergedRows.forEach(mergedRow ->
                System.out.println(mergedRow.getKey() + "-> A: " + mergedRow.getRowA() + " B: " + mergedRow.getRowB()));

        XSSFWorkbook wb = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet s = wb.createSheet("xxx");
        for (int i = 0; i < mergedRows.size(); i++) {
            MergedRow mg = mergedRows.get(i);
            Row rooow = s.createRow(i);
            rooow.createCell(0).setCellValue(mg.getKey());
            rooow.createCell(1).setCellValue(mg.getRowA());
            rooow.createCell(2).setCellValue(mg.getRowB());
        }
        FileOutputStream out = new FileOutputStream(args[6]);
        wb.write(out);
        out.close();
    }
}
