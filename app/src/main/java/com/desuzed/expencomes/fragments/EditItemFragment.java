package com.desuzed.expencomes.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.model.CategoryViewModel;
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.ItemViewModel;
import com.desuzed.expencomes.util.CustomTextWatcher;
import com.desuzed.expencomes.util.DataEditor;
import com.desuzed.expencomes.util.OnCategoryChosenListener;
import com.desuzed.expencomes.util.FragmentCreator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class EditItemFragment extends Fragment implements OnCategoryChosenListener {
    private String type = "default";
    private Category mCategory = null;
    private Button btnChooseCategory;
    private TextInputLayout txtInpLayoutComment, txtInpLayoutValue;
  //  private OnDataEditListener editListener;
    private Item oldItem;
    private FragmentCreator fragmentCreator;
    private DataEditor dataEditor;
    public static final String OLD_ITEM = "oldItem";


    public static EditItemFragment newInstance(String type, Item item) {
        EditItemFragment f = new EditItemFragment();
        Bundle b = new Bundle();
        b.putString(MainActivity.BUDGET_TYPE, type);
        if (item != null) {
            b.putSerializable(OLD_ITEM, item);
        }
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
    }

    private void init(View view) {
        if (getArguments() != null) {
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
            oldItem = (Item) getArguments().getSerializable(OLD_ITEM);
            Toast.makeText(getActivity(), type, Toast.LENGTH_SHORT).show();
        }
        txtInpLayoutComment = view.findViewById(R.id.comment_txtInputLayout);
        txtInpLayoutValue = view.findViewById(R.id.value_txtInputLayout);
        setupFloatingLabelError();
        btnChooseCategory = view.findViewById(R.id.btnChooseCategory);
        btnChooseCategory.setOnClickListener(view1 -> {
            ChooseCategoryDialog chooseCategoryDialog = ChooseCategoryDialog.getInstance(type);
            //TODO Будет отваливаться при перевороте
            chooseCategoryDialog.setCategoryChosenListener(this);
            chooseCategoryDialog.show(getFragmentManager(), chooseCategoryDialog.getTag());
    });
        FloatingActionButton fabAddNewItem = view.findViewById(R.id.add_new_item);
        fabAddNewItem.setOnClickListener(onFABAddNewItemClickListener);
        CategoryViewModel categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);
        ItemViewModel itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ItemViewModel.class);
        //dataEditor = new DataEditor(categoryViewModel, itemViewModel );
        dataEditor = new DataEditor(this, getActivity().getApplication());
        initFieldsWithOldItem();
        fragmentCreator = new FragmentCreator(getActivity(), getFragmentManager());
       // onClickHandler = OnClickHandler.getInstance(null, null);
    }

    private final View.OnClickListener onFABAddNewItemClickListener = view -> {
        String value = txtInpLayoutValue.getEditText().getText().toString();
        String comment = txtInpLayoutComment.getEditText().getText().toString();
        if (value.isEmpty() || mCategory == null) {
            Toast.makeText(getActivity(), "Поля не должны быть пустыми", Toast.LENGTH_SHORT).show();
        } else {
            handleItem(value, comment);
//                    if (getFragmentManager() != null) {
//                        getFragmentManager().popBackStack();
//                    }
            //TODO вероятно неправильный подход
            Objects.requireNonNull(getActivity()).onBackPressed();
        }
    };

    private void handleItem (String value, String comment){
        //TODO long не подходит для очень больших значений
        long price = Long.parseLong(value);
        Item newItem = new Item(mCategory.getName(), price, comment, type);
        if (oldItem==null){
            dataEditor.insertNewItem(newItem, mCategory);
        }else {
            dataEditor.editItem(oldItem, newItem, mCategory);
        }
    }

    @SuppressLint("SetTextI18n")
    private void initFieldsWithOldItem() {
        if (oldItem != null) {
            txtInpLayoutValue.getEditText().setText(Long.toString(oldItem.getValue()));
            txtInpLayoutComment.getEditText().setText(oldItem.getComment());
            mCategory = dataEditor.findCategoryByName(oldItem.getCategory());
            btnChooseCategory.setText(mCategory.getName());
        }
    }

    private void setChosenCategory(Category c, boolean isNewCategory) {
        if (isNewCategory) {
            mCategory = dataEditor.insertNewCategory(c);
        } else {
            mCategory = c;
        }
        btnChooseCategory.setText(mCategory.getName());
    }

    //TODO Возможно смысла в нем нет и можно обойтись тостами
    private void setupFloatingLabelError() {
        txtInpLayoutValue.getEditText().addTextChangedListener(new CustomTextWatcher(txtInpLayoutValue));

    }


    @Override
    public void categoryChosen(Category category, boolean isNewCategory) {
        setChosenCategory(category, isNewCategory);
    }

}
