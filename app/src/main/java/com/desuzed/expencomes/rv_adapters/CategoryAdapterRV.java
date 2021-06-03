package com.desuzed.expencomes.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.util.OnCategoryClickListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterRV extends RecyclerView.Adapter<CategoryAdapterRV.CustomViewHolder>{
    private List<Category> list = new ArrayList<>();
    private Context context;
    private OnCategoryClickListener categoryClickListener;
    public void setCategoryClickListener(OnCategoryClickListener categoryClickListener) {
        this.categoryClickListener = categoryClickListener;
    }

    public CategoryAdapterRV(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_layout, parent, false);
        return new CustomViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Category c = list.get(position);
        holder.bind(c, categoryClickListener);
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
                                categoryClickListener.onEditMenuOptionsClick(c);
                              //  onClickHandler.onEditCategoryClick(c);
                              //  categoryMenuOptionsClickListener.onEditClick(c);
                                break;
                            case R.id.menu_delete:
                                categoryClickListener.onDeleteMenuOptionsClick(c);
                               // onClickHandler.onDeleteCategoryClick(c);
                               // categoryMenuOptionsClickListener.onDeleteClick(c);
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName, tvValue;
        private CardView cvCategory;
        private Context context;
        public TextView buttonViewOption;
        private View categoryColor;
        public CustomViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            categoryColor = itemView.findViewById(R.id.category_color);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvValue = itemView.findViewById(R.id.tvCategoryValue);
            cvCategory = itemView.findViewById(R.id.cvCategory);
            buttonViewOption = itemView.findViewById(R.id.tvCategoryOptions);
        }

        public void bind(Category c, OnCategoryClickListener listener){
            tvCategoryName.setText(c.getName());
            tvValue.setText(String.valueOf(c.getTotalValue()));
            categoryColor.setBackgroundResource(R.drawable.circle);
            GradientDrawable gd = (GradientDrawable) categoryColor.getBackground();
            gd.setColor(Color.parseColor("#FF6200EE"));
           // categoryColor.setBackgroundColor();
//            if (c.getType().equals(MainActivity.TYPE_INCOME)){
//                itemCardView.setCardBackgroundColor(Color.CYAN);      //TODO Не работает
//            }else if (c.getType().equals(MainActivity.TYPE_EXPENSE)){
//                itemCardView.setCardBackgroundColor(Color.YELLOW);
//            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onCategoryClick(c);
                }
            });
        }

    }

    public void setList(List<Category> list) {
        if (list != null){
            this.list = list;
            notifyDataSetChanged();
        }
    }

}
