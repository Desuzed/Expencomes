package com.desuzed.expencomes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.desuzed.expencomes.db.Constants;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = Constants.CATEGORY_TABLE_NAME)
public class Category  {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants._ID)
    private int id;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_CATEGORY)
    private String name;
    //  private ArrayList <BudgetItem> itemsList;
    @NonNull
    @ColumnInfo(name = Constants.CATEGORY_TOTAL_VALUE)
    private long totalValue;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_TYPE)
    private String type;

    public Category(String name, String type, long totalValue) {
        this.name = name;
        this.type = type;
        this.totalValue = totalValue;
    }

    public String getType() {
        return type;
    }

//    public ArrayList<BudgetItem> getItemsList() {
//        return itemsList;
//    }

//    public void setItemsList(ArrayList<BudgetItem> itemsList) {
//        this.itemsList = itemsList;
//    }

    public String getName() {
        return name;
    }

    public long getTotalValue() {
        return totalValue;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
