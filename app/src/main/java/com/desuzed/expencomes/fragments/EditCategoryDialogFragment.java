package com.desuzed.expencomes.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.util.DataEditor;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputLayout;

public class EditCategoryDialogFragment extends BottomSheetDialogFragment {
    public static final String OLD_CATEGORY = "OLD_CATEGORY";
    private Category oldCategory = null;
    public EditCategoryDialogFragment() {
    }

    public static EditCategoryDialogFragment newInstance(Category category) {
        EditCategoryDialogFragment f = new EditCategoryDialogFragment();
        Bundle b = new Bundle();
        if (category != null) {
            b.putSerializable(OLD_CATEGORY, category);
        }
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.edit_category_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            oldCategory = (Category) getArguments().getSerializable(OLD_CATEGORY);
        }
        TextInputLayout editCategoryTxtInput = view.findViewById(R.id.edit_category_txtInpLayout);
        EditText et = editCategoryTxtInput.getEditText();
        et.setText(oldCategory.getName());
        ImageButton btnEdit = view.findViewById(R.id.btnEditCategory);
        DataEditor dataEditor = new DataEditor(this, getActivity().getApplication());
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newName = et.getText().toString();
                if (newName.isEmpty()) {
                    Toast.makeText(getContext(), "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
                } else {
                   // Category newCategory = oldCategory;
                    String oldCategoryName = oldCategory.getName();
                    oldCategory.setName(newName);
                    dataEditor.editCategory(oldCategory, oldCategoryName);
                    Toast.makeText(getActivity(), oldCategory.getName() + " ; " + oldCategoryName, Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });
    }
}
