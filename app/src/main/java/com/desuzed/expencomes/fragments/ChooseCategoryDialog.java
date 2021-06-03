package com.desuzed.expencomes.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.CategoryViewModel;
import com.desuzed.expencomes.rv_adapters.CategoryAdapterRV;
import com.desuzed.expencomes.util.CustomTextWatcher;
import com.desuzed.expencomes.util.DataEditor;
import com.desuzed.expencomes.util.OnCategoryChosenListener;
import com.desuzed.expencomes.util.OnCategoryClickListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

import java.util.List;

public class ChooseCategoryDialog extends BottomSheetDialogFragment  implements OnCategoryClickListener {
    private String type = "default";
    private RecyclerView recyclerView;
    private CategoryAdapterRV adapter;
    private TextInputLayout txtInpNewCategory;
    private OnCategoryChosenListener categoryChosenListener;
    private DataEditor dataEditor;

    public void setCategoryChosenListener(OnCategoryChosenListener categoryChosenListener) {
        this.categoryChosenListener = categoryChosenListener;
    }

    public ChooseCategoryDialog() {
    }

    public static ChooseCategoryDialog getInstance(String type) {
        ChooseCategoryDialog f = new ChooseCategoryDialog();
        Bundle b = new Bundle();
        b.putString(MainActivity.BUDGET_TYPE, type);
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.bottom_sheet_edit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
//        CategoryViewModel categoryViewModel = new ViewModelProvider(getActivity(), ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
//        categoryViewModel.getCategoriesByType(type).observe(this, new Observer<List<Category>>() {
//            @Override
//            public void onChanged(List<Category> categories) {
//                adapter.setList(categories);
//                //        categoryList = categories;
//            }
//        });

//        Button b = view.findViewById(R.id.btn_newCateg);
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditCategoryDialogFragment editCategoryDialogFragment =  new EditCategoryDialogFragment();
//                editCategoryDialogFragment.show(getFragmentManager(), editCategoryDialogFragment.getTag());
//            }
//        });

    }

    private void init (View view){
        if (getArguments() != null) {
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
            Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
        }
        recyclerView = view.findViewById(R.id.rvEditItemFrag);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CategoryAdapterRV(getContext());
        adapter.setCategoryClickListener(this);
        recyclerView.setAdapter(adapter);
        txtInpNewCategory = view.findViewById(R.id.new_category_txtInpLayout);
        txtInpNewCategory.getEditText().addTextChangedListener(new CustomTextWatcher(txtInpNewCategory));
        ImageButton button = view.findViewById(R.id.add_new_category);
        button.setOnClickListener(onAddNewCategoryClickListener);
        dataEditor = new DataEditor(this, getActivity().getApplication());
        dataEditor.getCategoryViewModel().getCategoriesByType(type).observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                adapter.setList(categories);
                //        categoryList = categories;
            }
        });
    }

    private final View.OnClickListener onAddNewCategoryClickListener = view -> {
        EditText et = txtInpNewCategory.getEditText();
        String categoryName = et.getText().toString();
        if (categoryName.isEmpty()) {
            Toast.makeText(getContext(), "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
        } else {
            Category c = new Category(categoryName, type, 0);
            categoryChosenListener.categoryChosen(c, true);
            Toast.makeText(getActivity(), "Nice", Toast.LENGTH_SHORT).show();
            dismiss();
        }
    };

    @Override
    public void onCategoryClick(Category category) {
        categoryChosenListener.categoryChosen(category, false);
        dismiss();
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
}
