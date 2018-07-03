package com.example.mctuan.restaurantmanagement.Object;

public class Food {

    private String name;
    private String ID;
    private String price;
    private String description;
    private String theNumber;

    public Food() {
        this.name = "";
        this.ID = "";
        this.price = "";
        this.description = "";
        this.theNumber = "0";
    }

    public Food(String name, String ID, String price, String description, String theNumber) {
        this.name = name;
        this.ID = ID;
        this.price = price;
        this.description = description;
        this.theNumber = theNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTheNumber() {
        return theNumber;
    }

    public void setTheNumber(String theNumber) {
        this.theNumber = theNumber;
    }
}
