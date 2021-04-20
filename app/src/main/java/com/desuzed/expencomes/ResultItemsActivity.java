package com.desuzed.expencomes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.desuzed.expencomes.db.DBManager;
import com.desuzed.expencomes.model.BudgetItem;
import com.desuzed.expencomes.rv_adapters.BudgetItemAdapterRV;

import java.util.ArrayList;

public class ResultItemsActivity extends AppCompatActivity {
    private ArrayList<BudgetItem> itemsList = new ArrayList<>();
    private DBManager dbManager;
    private BudgetItemAdapterRV adapter;
    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_items);
        Toolbar toolbar = findViewById(R.id.toolbar_result_items);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        categoryName = getIntent().getExtras().getString(ResultCategoryActivity.CATEGORY_NAME);
        ab.setTitle(categoryName);
        dbManager = new DBManager(this);
        ab.setDisplayHomeAsUpEnabled(true);
        RecyclerView rv = findViewById(R.id.rvResultItems);
        adapter = new BudgetItemAdapterRV(this, itemsList );
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
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
        itemsList = dbManager.getItemsByCategory(categoryName);
        adapter.setList(itemsList);
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbManager.closeDB();
    }
}