package com.raduq.dinha1;

import java.util.Objects;

/**
 * Created by Dell on 04/02/2017.
 */
public class CellKey {
    private String key;

    public CellKey(Object key) {
        this.key = String.valueOf(key);
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellKey cellKey = (CellKey) o;
        return Objects.equals(key, cellKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    @Override
    public String toString() {
        return key;
    }
}
