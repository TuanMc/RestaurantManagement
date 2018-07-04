package com.example.mctuan.restaurantmanagement.Object;

import java.util.ArrayList;

public class TablesList {

    private String numberTable;
    private ArrayList<Table> tables;

    public TablesList() {
        this.numberTable = "";
        this.tables = new ArrayList<>();
    }

    public TablesList(String numberTable, ArrayList<Table> tables) {
        this.numberTable = numberTable;
        this.tables = tables;
    }

    public String getNumberTable() {
        return numberTable;
    }

    public void setNumberTable(String numberTable) {
        this.numberTable = numberTable;
    }

    public ArrayList<Table> getTables() {
        return tables;
    }

    public void setTables(ArrayList<Table> tables) {
        this.tables = tables;
    }
}
