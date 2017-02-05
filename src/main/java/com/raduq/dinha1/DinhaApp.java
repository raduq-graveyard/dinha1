package com.raduq.dinha1;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

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

        Sheet file1 = new Sheet(args[0], keyA);
        Sheet file2 = new Sheet(args[1], keyB);

        List<MergedRow> mergedRows = file1.merge(file2);

        mergedRows.forEach(mergedRow ->
                System.out.println(mergedRow.getKey() + " encontrado."));

        XSSFWorkbook wb = new XSSFWorkbook();
        org.apache.poi.ss.usermodel.Sheet s = wb.createSheet("final-merged");
        for (int i = 0; i < mergedRows.size(); i++) {
            MergedRow mg = mergedRows.get(i);
            Row rooow = s.createRow(i);
            rooow.createCell(0).setCellValue(mg.getKey());
            for(int j = 0; j < mg.getRows().size(); j++){
                rooow.createCell(j+1).setCellValue(mg.getRows().get(j));
            }
        }
        FileOutputStream out = new FileOutputStream(args[4]);
        wb.write(out);
        out.close();
    }
}
