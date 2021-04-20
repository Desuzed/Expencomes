
package com.desuzed.expencomes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.desuzed.expencomes.db.DBManager;
import com.desuzed.expencomes.model.BudgetItem;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.rv_adapters.CategoryAdapterRV;
import com.desuzed.expencomes.util.RetrieveDataListener;

import java.util.ArrayList;

public class ResultCategoryActivity extends AppCompatActivity {
    public static final String CATEGORY_NAME = "CATEGORY_NAME";
    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private CategoryAdapterRV categoryAdapter;
    private ArrayList<Category> categoryList = new ArrayList<>();
    private ArrayList<BudgetItem> itemsList = new ArrayList<>();
    private DBManager dbManager;
    private String type;


//TODO СДелать списки по датам через табы (ПОД ВОПРОСОМ)

    //TODO сделать через фрагменты результирующие экраны
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_category);
        toolbar = findViewById(R.id.toolbar_result_category);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        type = getIntent().getStringExtra(MainActivity.BUDGET_TYPE);
        if (type.equals(MainActivity.TYPE_EXPENSE)){
            ab.setTitle(R.string.all_expense);
        }else if (type.equals(MainActivity.TYPE_INCOME)){
            ab.setTitle(R.string.all_income);
        }
        dbManager = new DBManager(this);
        recyclerView = findViewById(R.id.rvCategoryResult);
        categoryAdapter = new CategoryAdapterRV(this, categoryList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(categoryAdapter);
        categoryAdapter.setListener(new RetrieveDataListener() {
            @Override
            public void retrieveData(Category category) {
                Intent i = new Intent(ResultCategoryActivity.this, ResultItemsActivity.class);
                i.putExtra(CATEGORY_NAME, category.getName());
                //TODO Передавать название категории для тулбара
                startActivity(i);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDB();
        categoryList = dbManager.getCategoryList(type);
        categoryAdapter.setList(categoryList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbManager.closeDB();
    }
}