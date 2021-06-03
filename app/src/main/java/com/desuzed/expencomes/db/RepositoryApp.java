package com.desuzed.expencomes.db;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.Category;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class RepositoryApp {
    private CategoryDAO categoryDAO;
    private ItemDAO itemDAO;
    private LiveData<List<Category>> allCategories;
    private LiveData<List<Item>> allItems;

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

    public LiveData<List<Category>> getCategoriesByType(String type) {
        return categoryDAO.getCategoryByType(type);
    }

    public LiveData<List<Item>> getAllItems() {
        return allItems;
    }

    public LiveData<List<Item>> getItemsByCategory(String categoryName) {
        return itemDAO.getItemsByCategory(categoryName);
    }

//TODO Рефакторинг.
    public long insertCategory(Category c) throws ExecutionException, InterruptedException {
        Future<Long> id = DbApp.databaseWriteExecutor.submit(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return categoryDAO.insertCategory(c);
            }
        });
//        DbApp.databaseWriteExecutor.execute(new Runnable() {
//            @Override
//            public void run() {
//                id =
//            }
//        });
        return id.get();
    }

    public void updateCategory(Category c) {
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.updateCategory(c);
            }
        });
    }

    public Category getCategoryByName(String categoryName) {
        return categoryDAO.getCategoryByName(categoryName);
    }

    public void insertItem(Item item) {
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.insertItem(item);
            }
        });
    }

    public void deleteItem(Item item) {
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.deleteItem(item);
            }
        });
    }

    public void deleteCategory (Category c){
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                categoryDAO.deleteCategory(c);
            }
        });
    }

    public void updateItemsWithNewCategory (String newCategoryName, String oldCategoryName){
        DbApp.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                itemDAO.updateItemsWithNewCategory(newCategoryName, oldCategoryName);
            }
        });
    }
}
