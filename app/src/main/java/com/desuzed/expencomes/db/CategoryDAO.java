package com.desuzed.expencomes.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.desuzed.expencomes.model.Category;

import java.util.List;

@Dao
public interface CategoryDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    public long insertCategory(Category category);

    @Query("SELECT * FROM "+Constants.CATEGORY_TABLE_NAME)
    LiveData<List<Category>> getAllCategories();

    @Query("SELECT * FROM "+Constants.CATEGORY_TABLE_NAME + " WHERE " + Constants.COLUMN_TYPE + " = :type")
    LiveData<List <Category>> getCategoryByType(String type);

    @Query("SELECT * FROM "+Constants.CATEGORY_TABLE_NAME + " WHERE " + Constants.COLUMN_CATEGORY + " = :categoryName")
    Category getCategoryByName(String categoryName);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateCategory (Category category);

    @Delete
    void deleteCategory (Category category);
}
