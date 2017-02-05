package com.raduq.dinha1;

import java.util.Objects;

/**
 * Created by Dell on 04/02/2017.
 */
public class CellKey {
    private String key;
    private int num;

    public CellKey(Object key,int num) {
        this.key = String.valueOf(key);
        this.num = num;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellKey cellKey = (CellKey) o;
        return num == cellKey.num &&
                Objects.equals(key, cellKey.key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, num);
    }

    @Override
    public String toString() {
        return key + "-" + num;
    }
}
