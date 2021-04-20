package com.desuzed.expencomes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.desuzed.expencomes.db.DBManager;
import com.desuzed.expencomes.model.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final String BUDGET_TYPE = "type";
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_BALANCE = "balance";
    private long totalExpense, totalIncome, totalBalance;
    private Toolbar toolbar;
    private DBManager dbManager;
    private TextView tvExpense, tvIncome, tvBalance;

    //   private Button btnAllExpenses, btnAllIncomes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_main);
        tvExpense = findViewById(R.id.expenseMain);
        tvIncome = findViewById(R.id.incomeMain);
        tvBalance = findViewById(R.id.balanceMain);
        setSupportActionBar(toolbar);
//        btnAllExpenses = findViewById(R.id.btnAllExpenses);
//        btnAllIncomes = findViewById(R.id.btnAllIncomes);
        dbManager = new DBManager(this);
    }

    private long setTotalBudgetValue(String type) {
        return dbManager.getTotalBudgetValue(type);
    }

    public void onClickAddExpense(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(MainActivity.BUDGET_TYPE, TYPE_EXPENSE);
        startActivity(intent);
    }

    public void onClickAddIncome(View v) {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra(MainActivity.BUDGET_TYPE, TYPE_INCOME);
        startActivity(intent);
    }

    public void onClickShowExp(View view) {
        Intent i = new Intent(MainActivity.this, ResultCategoryActivity.class);
        i.putExtra(MainActivity.BUDGET_TYPE, TYPE_EXPENSE);
        startActivity(i);
    }

    public void onClickShowInc(View view) {
        Intent i = new Intent(MainActivity.this, ResultCategoryActivity.class);
        i.putExtra(MainActivity.BUDGET_TYPE, TYPE_INCOME);
        startActivity(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDB();
        totalExpense = setTotalBudgetValue(TYPE_EXPENSE);
        totalIncome = setTotalBudgetValue(TYPE_INCOME);
        totalBalance = totalIncome - totalExpense;
        tvExpense.setText(String.valueOf(totalExpense));
        tvIncome.setText(String.valueOf(totalIncome));
        tvBalance.setText(String.valueOf(totalBalance));
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbManager.closeDB();
    }
}