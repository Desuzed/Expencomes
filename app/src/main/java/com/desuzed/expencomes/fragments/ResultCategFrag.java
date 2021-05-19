package com.desuzed.expencomes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.CategoryViewModel;
import com.desuzed.expencomes.rv_adapters.CategoryAdapterRV;
import com.desuzed.expencomes.util.OnCategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class ResultCategFrag extends Fragment {
    private CategoryAdapterRV categoryAdapter;
    private String type;
    private OnCategoryClickListener listener;

    public static ResultCategFrag getInstance(String type){
        ResultCategFrag f = new ResultCategFrag();
        Bundle b = new Bundle();
        b.putString(MainActivity.BUDGET_TYPE, type);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_result_categ, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments()!=null){
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rvCategoryResult);
        categoryAdapter = new CategoryAdapterRV(getActivity());
        categoryAdapter.setListener(listener);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);
        CategoryViewModel cViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
        //    iViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ItemViewModel.class);
        cViewModel.getCategoriesByType(type).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setList(categories);
            }
        });

    }

    public void setListener(OnCategoryClickListener listener) {
        this.listener = listener;
    }

}
