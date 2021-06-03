package com.desuzed.expencomes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
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

    @Query("UPDATE " +Constants.ITEMS_TABLE_NAME + " SET "+ Constants.COLUMN_CATEGORY  + " = :newCategoryName " + " WHERE " + Constants.COLUMN_CATEGORY  + " = :oldCategoryName")
    void updateItemsWithNewCategory (String newCategoryName, String oldCategoryName);

    @Delete
    void deleteItem (Item i);
}