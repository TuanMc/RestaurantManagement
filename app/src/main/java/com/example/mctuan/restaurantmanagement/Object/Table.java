package com.example.mctuan.restaurantmanagement.Object;

import java.util.ArrayList;

public class Table {

    private String ID;
    private String tableName;
    private ArrayList<Food> foods;
    private String totalPayment;

    public Table() {
        this.ID = "";
        this.tableName = "";
        this.totalPayment = "0";
        this.foods = new ArrayList<>();
    }

    public Table(String ID, String tableName, ArrayList<Food> foods, String totalPayment) {
        this.ID = ID;
        this.tableName = tableName;
        this.foods = foods;
        this.totalPayment = totalPayment;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public void setTotalPayment(String totalPayment) {
        this.totalPayment = totalPayment;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
