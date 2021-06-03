package com.desuzed.expencomes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.desuzed.expencomes.db.RepositoryApp;
import com.desuzed.expencomes.model.Item;

import java.util.List;

public class ItemViewModel extends AndroidViewModel {
    private RepositoryApp repo;
    public ItemViewModel(@NonNull Application application) {
        super(application);
        repo = new RepositoryApp(application);
    }

    public LiveData<List<Item>> getAllItems() {
        return repo.getAllItems();
    }

    public LiveData<List<Item>> getItemsByCategory(String categoryName){
        return repo.getItemsByCategory(categoryName);
    }

    public void insertItem (Item i){
        repo.insertItem(i);
    }

    public void deleteItem(Item item){
        repo.deleteItem(item);
    }

    public void updateItemsWithNewCategory (String newCategoryName, String oldCategoryName){
        repo.updateItemsWithNewCategory(newCategoryName, oldCategoryName);
    }
}
