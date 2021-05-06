package com.desuzed.expencomes;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.desuzed.expencomes.db.DBManager;
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

public class EditActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private FrameLayout fragContainer;
    private Category mCategory = null;
    private Button btnCategory;
    private TextInputLayout txtInpLayoutValue;
    private TextInputLayout txtInpLayoutComment;
    private DBManager dbManager;
    private FragmentManager fragmentManager;
    private String type;
    public FrameLayout getFragContainer() {
        return fragContainer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        init();
    }

    private void init() {
        toolbar = findViewById(R.id.toolbar_edit);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        assert ab != null;
        ab.setDisplayHomeAsUpEnabled(true);
        type = getIntent().getStringExtra(MainActivity.BUDGET_TYPE);
        if (type.equals(MainActivity.TYPE_EXPENSE)){
            ab.setTitle(R.string.add_expense);
        }else if (type.equals(MainActivity.TYPE_INCOME)){
            ab.setTitle(R.string.add_income);
        }
        this.setupFloatingLabelError();
        fab = findViewById(R.id.fab_edit);
        btnCategory = findViewById(R.id.btnCategory);
        fragContainer = findViewById(R.id.fragContainer);
        CategoryFragment fragment = CategoryFragment.getInstance(type);
        fragment.setRetrieveDataListener(category -> {
            mCategory = category;
            btnCategory.setText(mCategory.getName());
            fragContainer.setVisibility(View.GONE);
        });
        fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.fragContainer, fragment).commit();
        dbManager = new DBManager(this);
    }



    private void setupFloatingLabelError() {
        txtInpLayoutComment = (TextInputLayout) findViewById(R.id.txtInpLayout_comment);
        txtInpLayoutValue = (TextInputLayout) findViewById(R.id.txtInpLayout_value);

        //TODO Разобраться с этим всем
//        txtInpLayoutName.getEditText().addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                if (charSequence.length() == 0) {
//                    txtInpLayoutName.setError("Поле не должно быть пустым");
//                    txtInpLayoutName.setErrorEnabled(true);
//                } else {
//                    txtInpLayoutName.setErrorEnabled(false);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//            }
//        });
        txtInpLayoutValue.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtInpLayoutValue.setError("Поле не должно быть пустым");
                    txtInpLayoutValue.setErrorEnabled(true);
                } else {
                    txtInpLayoutValue.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
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

    public void onClickFabSave(View view) {
        String value = txtInpLayoutValue.getEditText().getText().toString();
        String comment = txtInpLayoutComment.getEditText().getText().toString();
        if (value.isEmpty() || mCategory==null ){
            Toast.makeText(this, "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
        }else {
            //TODO long не подходит для очень больших значений
            Item i = new Item(mCategory.getName(), Long.parseLong(value), comment, type);
            dbManager.addItem(i);
            Toast.makeText(this, "Nice", Toast.LENGTH_SHORT).show();
            finish();
        }

    }
//TODO Сделать через фрагменты

    public void onCategoryClick(View v){
        if (fragContainer.getVisibility()== View.VISIBLE){
            fragContainer.setVisibility(View.GONE);
        }else{
            fragContainer.setVisibility(View.VISIBLE);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager.openDB();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dbManager.closeDB();
    }
}