package com.desuzed.expencomes;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.db.DBManager;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.rv_adapters.CategoryAdapterRV;
import com.desuzed.expencomes.util.RetrieveDataListener;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class CategoryFragment extends Fragment {
    private RecyclerView recyclerView;
    private CategoryAdapterRV adapter;
    private ArrayList<Category> list;
   // private DBManager dbManager;
    //  private Button btnCategory;
    private RetrieveDataListener retrieveDataListener;
    private String type;

    public void setRetrieveDataListener(RetrieveDataListener retrieveDataListener) {
        this.retrieveDataListener = retrieveDataListener;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_category, container, false);
    }
    public static CategoryFragment getInstance(String type){
        CategoryFragment cf = new CategoryFragment();
        Bundle b = new Bundle();
        b.putString(MainActivity.BUDGET_TYPE, type);
        cf.setArguments(b);
        return cf;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       // dbManager = new DBManager(getContext());
        if (getArguments()!=null){
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
        }
        recyclerView = view.findViewById(R.id.frag_category_recview);
        list = new ArrayList<>();
        adapter = new CategoryAdapterRV(getContext(), list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.setListener(retrieveDataListener);      //TODO добавлять листенер минуя посредника фрагмента
        recyclerView.setAdapter(adapter);
        TextInputLayout txtInpNewCateg = (TextInputLayout) view.findViewById(R.id.txtInpLayout_new_categ);
        txtInpNewCateg.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    txtInpNewCateg.setError("Поле не должно быть пустым");
                    txtInpNewCateg.setErrorEnabled(true);
                } else {
                    txtInpNewCateg.setErrorEnabled(false);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        ImageButton button = view.findViewById(R.id.add_item_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText et = txtInpNewCateg.getEditText();
                String categoryName = et.getText().toString();
                if (categoryName.isEmpty()) {
                    Toast.makeText(getContext(), "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
                } else {
                    Category c = new Category(categoryName, type, 0);
                 //   dbManager.insertCategory(c);
                    adapter.setCategoryItem(c);
                   // ((EditActivity)getActivity()).getFragContainer().setVisibility(View.GONE);
                    retrieveDataListener.retrieveData(c);
                    et.setText(""); //TODO Закрывать фрагмент, чтобы нельзя было ввести снова категорию
                    Toast.makeText(getContext(), "Nice", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    @Override
    public void onPause() {
        super.onPause();
      //  dbManager.closeDB();
    }

    @Override
    public void onResume() {
        super.onResume();
//        dbManager.openDB();
//        list = dbManager.getCategoryList(type);
//        adapter.setList(list);
    }

}
