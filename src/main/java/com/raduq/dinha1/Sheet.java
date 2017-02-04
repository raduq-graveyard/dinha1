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
    private int numCel;

    public Sheet(String filepath,int keyCel, int numCel) {
        this.file = read(filepath);
        this.keyCel = keyCel;
        this.numCel = numCel;
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

    public int getNumCel() {
        return numCel;
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
        List<MergedRow> mergedRows = getRows().stream().filter(Objects::nonNull).map(row -> {
            MergedRow mRow = new MergedRow();
            Optional<XSSFCell> optKeyCell = Optional.ofNullable(row.getCell(keyCel));
            optKeyCell.ifPresent(xssfCell -> mRow.setKey(getCellKey(optKeyCell.get()).getKey()));

            Optional<XSSFCell> optValueCell = Optional.ofNullable(row.getCell(numCel));
            optValueCell.ifPresent(xssfCell -> mRow.setRowA(getCellValue(optValueCell.get()).getValue()));
            return mRow;
        }).collect(Collectors.toList());

        another.getRows().stream().filter(Objects::nonNull).forEach(row -> {
            Optional<XSSFCell> optValueBCell = Optional.ofNullable(row.getCell(another.getKeyCel()));
            optValueBCell.ifPresent(xssfCell -> {
                String aKey = getCellKey(optValueBCell.get()).getKey();
                for(MergedRow mr : mergedRows){
                    if(aKey.equalsIgnoreCase(mr.getKey())){
                        mr.setRowB(getCellValue(row.getCell(another.getNumCel())).getValue());
                    }
                }
            });
        });
        return mergedRows;
    }

    private CellKey getCellKey(XSSFCell cell) {
        if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            return new CellKey(cell.getNumericCellValue());
        }
        if(cell.getCellType() == Cell.CELL_TYPE_STRING) {
            return new CellKey(cell.getStringCellValue());
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
