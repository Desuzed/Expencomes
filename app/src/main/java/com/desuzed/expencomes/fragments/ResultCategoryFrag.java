package com.desuzed.expencomes.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.rv_adapters.CategoryAdapterRV;
import com.desuzed.expencomes.util.DataEditor;
import com.desuzed.expencomes.util.OnCategoryClickListener;
import com.desuzed.expencomes.util.FragmentCreator;

import java.util.List;

public class ResultCategoryFrag extends Fragment implements OnCategoryClickListener {
    private CategoryAdapterRV categoryAdapter;
    private String type;
    private FragmentCreator fragmentCreator;
    private DataEditor dataEditor;
  //  private OnDataEditListener editListener;


//    public void setEditListener(OnDataEditListener editListener) {
//        this.editListener = editListener;
//    }

    public static ResultCategoryFrag newInstance(String type) {
        ResultCategoryFrag f = new ResultCategoryFrag();
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
        if (getArguments() != null) {
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
        }
        RecyclerView recyclerView = view.findViewById(R.id.rvCategoryResult);
        categoryAdapter = new CategoryAdapterRV(getActivity());
        categoryAdapter.setCategoryClickListener(this);
        fragmentCreator = new FragmentCreator(getActivity(), getFragmentManager());
       // onClickHandler = OnClickHandler.getInstance(getActivity(), getFragmentManager());

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);
//        CategoryViewModel cViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
//        cViewModel.getCategoriesByType(type).observe(this, new Observer<List<Category>>() {
//            @Override
//            public void onChanged(List<Category> categories) {
//                categoryAdapter.setList(categories);
//            }
//        });

      //  ItemViewModel itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ItemViewModel.class);
        //dataEditor = new DataEditor(cViewModel, itemViewModel);
        dataEditor = new DataEditor(this, getActivity().getApplication());
        dataEditor.getCategoryViewModel().getCategoriesByType(type).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoryAdapter.setList(categories);
            }
        });
    }

//
//    @Override
//    public void onEditClick(Category c) {
////        EditCategoryDialogFragment editCategoryDialogFragment = new EditCategoryDialogFragment();
////        editCategoryDialogFragment.show(getFragmentManager(), editCategoryDialogFragment.getTag());
//        Toast.makeText(getActivity(), "Clicked " + c.getName(), Toast.LENGTH_SHORT).show();
//    }

//    @Override
//    public void onDeleteClick(Category c) {
//        showDialog(c);
//    }

    //TODO Рефактор. Встречается много где один и тот же код
    private void showDialog(Category category) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Вы уверены?")
                .setTitle("Удаление элемента")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataEditor.deleteCategory(category);
                    }
                })
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .create();
        dialog.show();
    }


    @Override
    public void onCategoryClick(Category category) {
        ResultItemsFrag f = ResultItemsFrag.newInstance(category);
        fragmentCreator.startFragment(f);
    }

    @Override
    public void onDeleteMenuOptionsClick(Category category) {
        showDialog(category);
        Toast.makeText(getActivity(), "Clicked" + category.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditMenuOptionsClick(Category category) {
        EditCategoryDialogFragment editCategoryDialogFragment = EditCategoryDialogFragment.newInstance(category);
        editCategoryDialogFragment.show(getFragmentManager(),  editCategoryDialogFragment.getTag());
        Toast.makeText(getActivity(), "Clicked" + category.getName(), Toast.LENGTH_SHORT).show();
    }
}
