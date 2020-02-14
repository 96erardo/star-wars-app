package com.example.myfirstapp.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.view.interfaces.ItemClickListener;
import com.squareup.picasso.Picasso;

public class FilmGridAdapter extends RecyclerView.Adapter {
    private Film[] dataSet;
    private ItemClickListener clickListener;

    public static class FilmViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView imageView;

        public FilmViewHolder (CardView c) {
            super(c);

            this.cardView = c;
            this.imageView = cardView.findViewById(R.id.movie_cover);
        }

        public void bind (Film film, ItemClickListener itemClickListener) {
            this.imageView.setContentDescription("SW Episode " + film.episode + " cover");
            Picasso.get()
                    .load(film.cover)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.fallback)
                    .into(this.imageView);

            cardView.setOnClickListener(v -> {
                itemClickListener.onItemClicked(film.id);
            });
        }
    }

    public FilmGridAdapter (Film[] films, ItemClickListener itemClickListener) {
        dataSet = films;
        clickListener = itemClickListener;
    }

    public void setDataSet (Film[] films) {
        dataSet = films;
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

        vh.bind(dataSet[position], clickListener);
    }



    @Override
    public int getItemCount() {
        return dataSet.length;
    }
}
