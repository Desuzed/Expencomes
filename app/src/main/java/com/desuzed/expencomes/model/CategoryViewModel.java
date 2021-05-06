package com.desuzed.expencomes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.desuzed.expencomes.db.RepositoryApp;
import com.desuzed.expencomes.model.Category;

import java.util.List;

public class CategoryViewModel  extends AndroidViewModel {
    private RepositoryApp repo;
    private final LiveData<List<Category>> allCategories;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repo = new RepositoryApp(application);
        allCategories = repo.getAllCategories();
    }

    public LiveData<List<Category>> getAllCategories() {
        return allCategories;
    }

    public void insertCategory (Category c){
        repo.insertCategory(c);
    }
}
