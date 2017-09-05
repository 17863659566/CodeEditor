package com.zh.young.codeeditor.entity;

/**
 * 备忘录保存的实体
 */

public class MementoEntity {
    private int  start;
    private int after;
    private String data;

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getAfter() {
        return after;
    }

    public void setAfter(int after) {
        this.after = after;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
