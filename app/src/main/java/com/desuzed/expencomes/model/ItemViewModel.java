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
    private final LiveData<List<Item>> allItems;
    public ItemViewModel(@NonNull Application application) {
        super(application);
        repo = new RepositoryApp(application);
        allItems = repo.getAllItems();
    }

    public LiveData<List<Item>> getAllCategories() {
        return allItems;
    }

    public void insertItem (Item i){
        repo.insertItem(i);
    }
}
