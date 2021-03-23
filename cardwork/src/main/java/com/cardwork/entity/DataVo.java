package com.cardwork.entity;

public class DataVo {
    private String id;
    private int length;
    private String value;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String raw() {
        StringBuffer padded = new StringBuffer(this.value);
        while (padded.toString().getBytes().length < this.length) {
            padded.append(' ');
        }
        return padded.toString();
    }
    
    public static DataVo create(String id, int length, String value) {
    	DataVo datavo = new DataVo();
    	datavo.setId(id);
    	datavo.setLength(length);
    	datavo.setValue(value);
        return datavo;
    }
}
