package com.desuzed.expencomes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.desuzed.expencomes.fragments.EditItemFragment;
import com.desuzed.expencomes.fragments.MainActivityFragment;
import com.desuzed.expencomes.fragments.ResultCategoryFrag;
import com.desuzed.expencomes.util.DataEditor;
import com.desuzed.expencomes.util.FragmentCreator;

public class MainActivity extends AppCompatActivity  {
    public static final String BUDGET_TYPE = "type";
    public static final String TYPE_INCOME = "income";
    public static final String TYPE_EXPENSE = "expense";
    public static final String TYPE_BALANCE = "balance";
    private FragmentManager fManager;
    private ActionBar actionBar;
    private DataEditor dataEditor;
    private FragmentCreator fragmentCreator;
    //TODO Создать сервис, который будет подсчитывать в фоне сумму всех позиций и сравнивать с общим значением категорий
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);
//        CategoryViewModel categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(CategoryViewModel.class);
//        ItemViewModel itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ItemViewModel.class);
        dataEditor = new DataEditor(this, getApplication());
       // dataEditor = new DataEditor(categoryViewModel, itemViewModel);
        fManager = getSupportFragmentManager();
        fragmentCreator = new FragmentCreator(this, getSupportFragmentManager());
        //onClickHandler = OnClickHandler.getInstance(this, fManager);
        MainActivityFragment maFrag = MainActivityFragment.newInstance();
        fragmentCreator.startFragment(maFrag);
    }



    @SuppressLint("NonConstantResourceId")
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.addExpense:
                EditItemFragment editExpense = EditItemFragment.newInstance(TYPE_EXPENSE, null);
                fragmentCreator.startFragment(editExpense);
                break;
            case R.id.addIncome:
                EditItemFragment editIncome = EditItemFragment.newInstance(TYPE_INCOME, null);
                fragmentCreator.startFragment(editIncome);
                break;
            case R.id.btnAllExpenses:
                ResultCategoryFrag f = ResultCategoryFrag.newInstance(TYPE_EXPENSE);
                fragmentCreator.startFragment(f);
                break;
            case R.id.btnAllIncomes:
                ResultCategoryFrag fragment = ResultCategoryFrag.newInstance(TYPE_INCOME);
                fragmentCreator.startFragment(fragment);
                break;
        }
    }

    boolean doubleBackToExitPressedOnce = false;

    private void doubleClickExit() {
        if (doubleBackToExitPressedOnce) {
            moveTaskToBack(true);
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Нажмите еще раз для выхода", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }

    @Override
    public void onBackPressed() {
        if (fManager.getBackStackEntryCount() > 0) {
            fManager.popBackStack();
        } else {
            doubleClickExit();
        }
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

    private boolean isPortrait() {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

}