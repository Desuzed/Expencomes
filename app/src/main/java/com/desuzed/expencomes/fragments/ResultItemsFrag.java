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
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.model.ItemViewModel;
import com.desuzed.expencomes.rv_adapters.ItemAdapterRV;
import com.desuzed.expencomes.util.DataEditor;
import com.desuzed.expencomes.util.FragmentCreator;
import com.desuzed.expencomes.util.OnItemClickListener;

import java.util.List;

public class ResultItemsFrag extends Fragment implements OnItemClickListener {
    public static final String CATEGORY_NAME = "categoryName";
    private ItemAdapterRV adapter;
    private String categoryName;
    private String type;
    private ItemViewModel itemViewModel;
    private FragmentCreator fragmentCreator;
    private DataEditor dataEditor;


    public static ResultItemsFrag newInstance(Category category) {
        ResultItemsFrag f = new ResultItemsFrag();
        Bundle b = new Bundle();
        b.putString(MainActivity.BUDGET_TYPE, category.getType());
        b.putString(CATEGORY_NAME, category.getName());
        f.setArguments(b);
        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.frag_res_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
//        itemViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ItemViewModel.class);
//        itemViewModel.getItemsByCategory(categoryName).observe(this, new Observer<List<Item>>() {
//            @Override
//            public void onChanged(List<Item> items) {
//                adapter.setList(items);
//            }
//        });
//        CategoryViewModel categoryViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(CategoryViewModel.class);

        // dataEditor = new DataEditor(categoryViewModel, itemViewModel );
        dataEditor = new DataEditor(this, getActivity().getApplication());
        dataEditor.getItemViewModel().getItemsByCategory(categoryName).observe(this, new Observer<List<Item>>() {
            @Override
            public void onChanged(List<Item> items) {
                adapter.setList(items);
            }
        });
    }

    private void init(View view) {
        if (getArguments() != null) {
            type = getArguments().getString(MainActivity.BUDGET_TYPE);
            categoryName = getArguments().getString(CATEGORY_NAME);
            Toast.makeText(getActivity(), type + " , " + categoryName, Toast.LENGTH_SHORT).show();
        }
        RecyclerView rv = view.findViewById(R.id.rvResultItems);
        adapter = new ItemAdapterRV(getActivity());
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentCreator = new FragmentCreator(getActivity(), getFragmentManager());
        //  onClickHandler = OnClickHandler.getInstance(null, null);

        adapter.setListener(this);
        rv.setAdapter(adapter);
    }

//    @Override
//    public void onEditItemClick(Item i) {
//
//
//        launchEditFragmentListener.launchEditFragment(i);
//    }
//
//    @Override
//    public void onDeleteItemClick(Item i) {
//        showDialog(i);
//    }

    private void showDialog(Item item) {
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setMessage("Вы уверены?")
                .setTitle("Удаление элемента")
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dataEditor.deleteItem(item);
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
    public void onItemClick(Item item) {
        Toast.makeText(getActivity(), "Clicked" + item.getCategory(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteMenuOptionsClick(Item item) {
        showDialog(item);
        Toast.makeText(getActivity(), "Clicked" + item.getCategory(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onEditMenuOptionsClick(Item item) {
        EditItemFragment editFragment = EditItemFragment.newInstance(item.getType(), item);
        fragmentCreator.startFragment(editFragment);
        Toast.makeText(getActivity(), "Clicked" + item.getCategory(), Toast.LENGTH_SHORT).show();
    }
}
