package com.desuzed.expencomes.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Item;
import com.desuzed.expencomes.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapterRV extends RecyclerView.Adapter<ItemAdapterRV.CustomViewHolder> {
    //TODO implement ListAdapter
    private List<Item> list = new ArrayList<>();
    private final Context context;
    private OnItemClickListener listener;
    public ItemAdapterRV(Context context) {
        this.context = context;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapterRV.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, parent, false);
        return new ItemAdapterRV.CustomViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Item i = list.get(position);
        holder.bind(i, listener);
        holder.buttonViewOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context, holder.buttonViewOption);
                popup.inflate(R.menu.item_options_menu);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @SuppressLint("NonConstantResourceId")
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.menu_edit:
                                listener.onEditMenuOptionsClick(i);
                         //       onClickHandler.onEditItemClick(i);
                            //    listener.onEditItemClick(i);
                                break;
                            case R.id.menu_delete:
                                listener.onDeleteMenuOptionsClick(i);
                          //      onClickHandler.onDeleteItemClick(i);
                          //      listener.onDeleteItemClick(i);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });
    }

    public void setList(List<Item> list) {
        if (list != null){
            this.list = list;
            notifyDataSetChanged();
        }
    }

//    public void setBudgetItem (Item item){
//        if (item!=null){
//            list.add(item);
//            notifyItemInserted(list.size()-1);
//        }
//    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCategoryName;
        private final TextView tvValue;
        public TextView buttonViewOption;
        private final Context context;

        public CustomViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvCategoryName = itemView.findViewById(R.id.tvItemName);
            tvValue = itemView.findViewById(R.id.tvItemValue);
            buttonViewOption = itemView.findViewById(R.id.tvItemOptions);
        }

        public void bind(Item item, OnItemClickListener listener) {
            tvCategoryName.setText(item.getCategory());
            tvValue.setText(String.valueOf(item.getValue()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
