package com.raduq.dinha1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Dell on 04/02/2017.
 */
public class MergedRow {

    private String key;
    private List<String> rows;

    public MergedRow(){
        this.rows = new ArrayList<>();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void addRow(String row){
        this.rows.add(row);
    }

    public List<String> getRows() {
        return rows;
    }

    public void setRows(List<String> rows) {
        this.rows = rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergedRow that = (MergedRow) o;
        return Objects.equals(key, that.key) &&
                Objects.equals(rows, that.rows);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, rows);
    }
}