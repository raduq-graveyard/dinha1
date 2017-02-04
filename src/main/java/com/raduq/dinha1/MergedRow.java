package com.raduq.dinha1;

import java.util.Objects;

/**
 * Created by Dell on 04/02/2017.
 */
public class MergedRow {

    private String key;
    private String rowA;
    private String rowB;

    public MergedRow(){}

    public MergedRow(String key, Object rowA, Object rowB) {
        this.key = key;
        this.rowA = String.valueOf(rowA);
        this.rowB = String.valueOf(rowB);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getRowA() {
        return rowA;
    }

    public void setRowA(String rowA) {
        this.rowA = rowA;
    }

    public String getRowB() {
        return rowB;
    }

    public void setRowB(String rowB) {
        this.rowB = rowB;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MergedRow mergedRow = (MergedRow) o;
        return Objects.equals(key, mergedRow.key) &&
                Objects.equals(rowA, mergedRow.rowA) &&
                Objects.equals(rowB, mergedRow.rowB);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, rowA, rowB);
    }

    @Override
    public String toString(){
        return key + "-> " + rowA + " : " + rowB;
    }
}