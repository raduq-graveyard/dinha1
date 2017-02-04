package com.raduq.dinha1;

import java.util.Objects;

/**
 * Created by Dell on 04/02/2017.
 */
public class CellValue {
    private String value;

    public CellValue(Object value) {
        this.value = String.valueOf(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellValue cellValue = (CellValue) o;
        return Objects.equals(value, cellValue.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
