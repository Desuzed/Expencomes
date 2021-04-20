package com.desuzed.expencomes.model;

import java.util.ArrayList;

public class Category {
    private String name;
    private ArrayList <BudgetItem> itemsList;
    private long totalValue;
    private String type;


    public Category(String name, String type, long totalValue) {
        this.name = name;
        this.type = type;
        this.totalValue = totalValue;
    }

    public String getType() {
        return type;
    }

    public ArrayList<BudgetItem> getItemsList() {
        return itemsList;
    }

    public void setItemsList(ArrayList<BudgetItem> itemsList) {
        this.itemsList = itemsList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTotalValue() {
        return totalValue;
    }
}
