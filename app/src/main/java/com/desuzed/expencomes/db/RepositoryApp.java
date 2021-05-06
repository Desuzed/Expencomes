package com.desuzed.expencomes.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.Category;

import java.util.List;

public class RepositoryApp {
    private CategoryDAO categoryDAO;
    private ItemDAO itemDAO;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Item>> allItems;

    //TODO Добавить геттеры и сеттеры для дао и использовать их для нахождения позиций по параметрам
    public RepositoryApp(Application app) {
        DbApp db = DbApp.getDatabase(app);
        itemDAO = db.itemDao();
        categoryDAO = db.categoryDao();
        allCategories = categoryDAO.getAllCategories();
        allItems = itemDAO.getAllItems();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public void insertCategory(Category c) {
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.insertCategory(c);
            }
        });
    }

    public void insertItem(Item item) {
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.insertItem(item);
            }
        });
    }
}
