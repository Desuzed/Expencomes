package com.desuzed.expencomes.rv_adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.desuzed.expencomes.R;
import com.desuzed.expencomes.model.BudgetItem;
import com.desuzed.expencomes.model.Category;
import com.desuzed.expencomes.util.RetrieveDataListener;

import java.util.ArrayList;

public class BudgetItemAdapterRV extends RecyclerView.Adapter<BudgetItemAdapterRV.CustomViewHolder> {
    private ArrayList<BudgetItem> list;
    private final Context context;
    private RetrieveDataListener listener;

    public BudgetItemAdapterRV(Context context, ArrayList<BudgetItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public BudgetItemAdapterRV.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new BudgetItemAdapterRV.CustomViewHolder(view, context);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
        BudgetItem item = list.get(position);
        holder.bind(item);
    }

    public void setList(ArrayList<BudgetItem> list) {
        if (list != null){
            this.list = list;
            notifyDataSetChanged();
        }
    }

    public void setBudgetItem (BudgetItem item){
        if (item!=null){
            list.add(item);
            notifyItemInserted(list.size()-1);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvCategoryName;
        private final TextView tvValue;
        private final Context context;

        public CustomViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvValue = itemView.findViewById(R.id.tvValue);
        }

        public void bind(BudgetItem item) {
            tvCategoryName.setText(item.getCategory());
            tvValue.setText(String.valueOf(item.getValue()));
        }
    }
}