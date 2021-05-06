package com.desuzed.expencomes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.desuzed.expencomes.model.Item;

import java.util.List;
@Dao
public interface ItemDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public void insertItem(Item item);

    @Query("DELETE FROM " + Constants.ITEMS_TABLE_NAME)
    void deleteAllItems();

    @Query("SELECT * FROM "+Constants.ITEMS_TABLE_NAME)
    LiveData<List<Item>> getAllItems();

    @Query("SELECT * FROM "+Constants.ITEMS_TABLE_NAME + " WHERE "+ Constants.COLUMN_CATEGORY + " = :categoryName")
    LiveData<List<Item>> getItemsByCategory(String categoryName);
}