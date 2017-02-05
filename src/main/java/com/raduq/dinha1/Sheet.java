package com.raduq.dinha1;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Created by Raduq on 04/02/2017.
 */
public class Sheet {

    private File file;
    private int keyCel;

    public Sheet(String filepath,int keyCel) {
        this.file = read(filepath);
        this.keyCel = keyCel;
    }

    private File read(String filepath) {
        return new File(filepath);
    }

    public File getFile() {
        return file;
    }

    public int getKeyCel() {
        return keyCel;
    }

    public List<XSSFRow> getRows() throws IOException {
        List<XSSFRow> rows = new ArrayList<>();
        XSSFWorkbook wb = new XSSFWorkbook(new FileInputStream(file));
        XSSFSheet sheet = wb.getSheetAt(0);
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            rows.add(sheet.getRow(i));
        }
        return rows;
    }

    public List<MergedRow> merge(Sheet another) throws IOException {
        List<MergedRow> mergedRows = getRows().stream().filter(Objects::nonNull).map(rowA -> {
            MergedRow mergedRow = new MergedRow();
            Optional<XSSFCell> optKeyCell = Optional.ofNullable(rowA.getCell(keyCel));
            optKeyCell.ifPresent(xssfCell -> mergedRow.setKey(getCellKey(optKeyCell.get()).getKey()));

            mergeOthers(rowA, mergedRow, getKeyCel());
            return mergedRow;
        }).collect(Collectors.toList());

        another.getRows().stream().filter(Objects::nonNull).forEach(rowB -> {
            Optional<XSSFCell> bKeyCell = Optional.ofNullable(rowB.getCell(another.getKeyCel()));
            bKeyCell.ifPresent(xssfCell -> {
                CellKey bCellKey = getCellKey(bKeyCell.get());
                for(MergedRow mergedRow : mergedRows){
                    if(bCellKey.getKey().equalsIgnoreCase(mergedRow.getKey())){
                        mergeOthers(rowB,mergedRow,bCellKey.getNum());
                    }
                }
            });
        });
        return mergedRows;
    }

    private void mergeOthers(XSSFRow row, MergedRow mergedSheet, int celKey) {
        for(int i=0;i< row.getLastCellNum(); i++){
            if(celKey == i){
                continue;
            }
            addMergedRow(row,mergedSheet,i);
        }
    }

    private void addMergedRow(XSSFRow row, MergedRow mergedSheet, int celNumber){
        Optional<XSSFCell> optValueCell = Optional.ofNullable(row.getCell(celNumber));
        optValueCell.ifPresent(xssfCell -> mergedSheet.addRow(getCellValue(optValueCell.get()).getValue()));
    }

    private CellKey getCellKey(XSSFCell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return new CellKey(cell.getNumericCellValue(),cell.getColumnIndex());
        }
        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return new CellKey(cell.getStringCellValue(),cell.getColumnIndex());
        }
        throw new RuntimeException("Tipo incompativel");
    }

    private CellValue getCellValue(XSSFCell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return new CellValue(cell.getNumericCellValue());
        }
        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return new CellValue(cell.getStringCellValue());
        }
        throw new RuntimeException("Tipo incompativel");
    }
}
