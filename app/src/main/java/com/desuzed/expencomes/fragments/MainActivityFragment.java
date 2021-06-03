package com.desuzed.expencomes.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivityFragment extends Fragment {
    private TextView tvExpense, tvIncome, tvBalance;
    private List<Category> categoryList = new ArrayList<>();

    public static MainActivityFragment newInstance() {
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
//        Bundle b = new Bundle();
//        b.putString(MainActivity.BUDGET_TYPE, type);
//        cf.setArguments(b);
        return mainActivityFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_main_activity, container, false);
    }
//TODO Работает всё неккоректно, придумать другой подход
    long allIncomes = 0;
    long allExpenses = 0;
    long balance = 0;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvExpense = view.findViewById(R.id.expenseMain);
        tvIncome = view.findViewById(R.id.incomeMain);
        tvBalance = view.findViewById(R.id.balanceMain);
        CategoryViewModel cViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
        //    iViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication())).get(ItemViewModel.class);
        cViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Log.i("MainActivityFragment", "onChanged: ");
                categoryList = categories;
                //TODO onChanged Срабатывает 3 раза и из за этого суммируется три раза
//                for (Category c : categories) {
//                    if (c.getType().equals(MainActivity.TYPE_INCOME)){
//                        allIncomes = allIncomes + c.getTotalValue();
//                    }else if (c.getType().equals(MainActivity.TYPE_EXPENSE)){
//                        allExpenses = allExpenses + c.getTotalValue();
//                    }
//                }
//                balance = allIncomes - allExpenses;
//                tvIncome.setText(String.valueOf(allIncomes));
//                tvExpense.setText(String.valueOf(allExpenses));
//                tvBalance.setText(String.valueOf(balance));
            }
        });

    }

}
