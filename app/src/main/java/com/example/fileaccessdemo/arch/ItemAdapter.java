package com.example.fileaccessdemo.arch;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fileaccessdemo.R;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {
    private List<Item> itemsList;
    public ItemAdapter(List<Item> items) {
        this.itemsList = items;
    }
    public void setItemsList(List<Item> itemsList) {
        this.itemsList = itemsList;
        notifyDataSetChanged();
    }
    public List<Item> getItems() {
        return itemsList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.nameTextView.setText(itemsList.get(position).getName());
        holder.placeTextView.setText(itemsList.get(position).getPlace());
    }
    @Override
    public int getItemCount() {
        return itemsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView,placeTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            placeTextView = itemView.findViewById(R.id.placeTextView);
        }
    }
}
