package com.codecool.enterprise.overcomplicated.model;

public class TictactoeField {
    private String fieldValue;
    private int fieldCounter = 0;

    public TictactoeField(String fieldValue, int fieldCounter) {
        this.fieldValue = fieldValue;
        this.fieldCounter = fieldCounter;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public int getFieldCounter() {
        return fieldCounter;
    }

    public void setFieldCounter(int fieldCounter) {
        this.fieldCounter = fieldCounter;
    }
}
