package com.purepoint.receipe.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.purepoint.receipe.R;
import com.purepoint.receipe.entities.Receipe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class DataAdapter extends RecyclerView.Adapter<DataAdapter.Holder> {

    private final LayoutInflater inflator;
    private List<Receipe> receipes;

    public DataAdapter(LayoutInflater inflator) {
        this.inflator = inflator;
        receipes = new ArrayList<>();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(inflator.inflate(R.layout.rv_receipe_item, parent, false));
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Receipe toBind = receipes.get(position);
        holder.title.setText(toBind.getTitle());
        holder.ingredients.setText(toBind.getIngredients());
        if (toBind.getThumbnail() != null && !toBind.getThumbnail().isEmpty()) {
            Picasso.with(holder.title.getContext()).load(toBind.getThumbnail()).into(holder.thumb);
        }
    }

    @Override
    public int getItemCount() {
        return receipes.size();
    }

    public void addReceipes(List<Receipe> receipes) {
        this.receipes.clear();
        this.receipes.addAll(receipes);
        notifyDataSetChanged();
    }

    public void clearReceipes() {
        receipes.clear();
        notifyDataSetChanged();
    }

    public class Holder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView thumb;
        TextView ingredients;

        public Holder(View v) {
            super(v);
            title = v.findViewById(R.id.receipe_title);
            thumb = v.findViewById(R.id.receipe_thumb);
            ingredients = v.findViewById(R.id.receipe_ingredients);
        }
    }
}