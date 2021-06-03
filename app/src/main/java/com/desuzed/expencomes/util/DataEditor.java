package com.desuzed.expencomes.util;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.desuzed.expencomes.db.DbApp;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.CategoryViewModel;
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.ItemViewModel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class DataEditor {
    private final CategoryViewModel categoryViewModel;
    private final ItemViewModel itemViewModel;

    public CategoryViewModel getCategoryViewModel() {
        return categoryViewModel;
    }

    public ItemViewModel getItemViewModel() {
        return itemViewModel;
    }

    public DataEditor(ViewModelStoreOwner owner, Application app) {
        categoryViewModel = new ViewModelProvider(owner, ViewModelProvider.AndroidViewModelFactory.getInstance(app)).get(CategoryViewModel.class);
        itemViewModel = new ViewModelProvider(owner, ViewModelProvider.AndroidViewModelFactory.getInstance(app)).get(ItemViewModel.class);
    }

    public void insertNewItem(Item item, Category category) {
        itemViewModel.insertItem(item);
        category.setTotalValue(category.getTotalValue() + item.getValue());
        categoryViewModel.updateCategory(category);
    }

    public void deleteItem(Item item) {
        itemViewModel.deleteItem(item);
        Category c = findCategoryByName(item.getCategory());
        c.setTotalValue(c.getTotalValue() - item.getValue());
        categoryViewModel.updateCategory(c);
    }


    public Category insertNewCategory(Category c) {
        long id;
        try {
            id = categoryViewModel.insertCategory(c);
            c.setId(id);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return c;
    }


    public void editItem(Item oldItem, Item newItem, Category category) {
        //TODO При изменении категории не обновляется старая и новая
        itemViewModel.deleteItem(oldItem);
        category.setTotalValue(category.getTotalValue() - oldItem.getValue() + newItem.getValue());
        itemViewModel.insertItem(newItem);
        categoryViewModel.updateCategory(category);
    }

    public Category findCategoryByName(String categoryName) {
        Future<Category> f = DbApp.databaseWriteExecutor.submit(new Callable<Category>() {
            @Override
            public Category call() throws Exception {
                return categoryViewModel.getCategoryByName(categoryName);
            }
        });
        Category c = null;
        try {
            c = f.get();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return c;
    }

    public void editCategory (Category newCategory, String oldCategoryName){
        itemViewModel.updateItemsWithNewCategory(newCategory.getName(), oldCategoryName);
        categoryViewModel.updateCategory(newCategory);
    }

    public void deleteCategory(Category category) {
        categoryViewModel.deleteCategory(category);
    }

}
