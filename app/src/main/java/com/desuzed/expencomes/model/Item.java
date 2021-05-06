package com.desuzed.expencomes.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.desuzed.expencomes.db.Constants;

import java.io.Serializable;

@Entity(tableName = Constants.ITEMS_TABLE_NAME)
public class Item {
    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_TYPE)
    private String type;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_CATEGORY)
    private String category;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_VALUE)
    private long value;
    @NonNull
    @ColumnInfo(name = Constants.COLUMN_COMMENT)
    private String comment;

    public Item(String category, long value, String comment, String type) {
        this.category = category;
        this.value = value;
        this.comment = comment;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public long getValue() {
        return value;
    }

    public String getComment() {
        return comment;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
