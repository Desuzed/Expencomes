package com.desuzed.expencomes.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.MainActivity;
import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.util.RetrieveDataListener;

import java.util.ArrayList;
import java.util.List;

public class CategoryAdapterRV extends RecyclerView.Adapter<CategoryAdapterRV.CustomViewHolder>{
    private List<Category> list;
    private Context context;
    private RetrieveDataListener listener;

    public void setListener(RetrieveDataListener listener) {
        this.listener = listener;
    }

    public CategoryAdapterRV(Context context, ArrayList <Category> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //TODO Сделать другую верстку для BudgetItem
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CustomViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        Category c = list.get(position);
        holder.bind(c, listener);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCategoryName, tvValue;
        private CardView itemCardView;
        private Context context;
        public CustomViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvValue = itemView.findViewById(R.id.tvValue);
            itemCardView = itemView.findViewById(R.id.itemCardView);
        }

        public void bind(Category c, RetrieveDataListener listener){
            tvCategoryName.setText(c.getName());
            tvValue.setText(String.valueOf(c.getTotalValue()));
//            if (c.getType().equals(MainActivity.TYPE_INCOME)){
//                itemCardView.setCardBackgroundColor(Color.CYAN);      //TODO Не работает
//            }else if (c.getType().equals(MainActivity.TYPE_EXPENSE)){
//                itemCardView.setCardBackgroundColor(Color.YELLOW);
//            }
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.retrieveData(c);
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

    public void setCategoryItem (Category item){
        if (item!=null){
            list.add(item);
            notifyItemInserted(list.size()-1);
        }
    }
}
