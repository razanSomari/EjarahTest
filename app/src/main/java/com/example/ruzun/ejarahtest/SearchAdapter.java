package com.example.ruzun.ejarahtest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    ArrayList<String> features;

    public SearchAdapter(ArrayList<String> features) {
        this.features = features;
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.serach_result, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.searchResult.setText(features.get(position));
    }

    @Override
    public int getItemCount() {
        return features.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView searchResult;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            searchResult=itemView.findViewById(R.id.search_result_text);
        }
    }
}