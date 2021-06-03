package com.desuzed.expencomes.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.desuzed.expencomes.db.RepositoryApp;

import java.util.List;
import java.util.concurrent.ExecutionException;

//Модель не должна хранить информацию, а должна реагировать на события

public class CategoryViewModel  extends AndroidViewModel {
    private RepositoryApp repo;
    public CategoryViewModel(@NonNull Application application) {
        super(application);
        repo = new RepositoryApp(application);

    }

    public LiveData<List<Category>> getAllCategories() {
        return repo.getAllCategories();
    }

    public LiveData<List<Category>> getCategoriesByType(String type) {
        return repo.getCategoriesByType(type);
    }

    public long insertCategory (Category c) throws ExecutionException, InterruptedException {
        return repo.insertCategory(c);
    }

    public Category getCategoryByName(String categoryName){
        return repo.getCategoryByName(categoryName);
    }

    public void updateCategory (Category c){
        repo.updateCategory(c);
    }

    public void deleteCategory (Category c){
        repo.deleteCategory(c);
    }
}
