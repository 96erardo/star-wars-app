package com.example.myfirstapp.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.squareup.picasso.Picasso;

public class FilmGridAdapter extends RecyclerView.Adapter {
    private String[] dataSet;
    public static class FilmViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;

        public FilmViewHolder (CardView c) {
            super(c);

            this.cardView = c;
            this.imageView = cardView.findViewById(R.id.movie_cover);
        }
    }

    public FilmGridAdapter (String[] titles) {
        dataSet = titles;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.film_item_view, parent,false);

        FilmViewHolder vh = new FilmViewHolder(cardView);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        FilmViewHolder vh = (FilmViewHolder) holder;

        vh.imageView.setContentDescription("SW Episode " + (position + 1) + " cover");
        Picasso.get().load(dataSet[position]).into(vh.imageView);
    }

    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
