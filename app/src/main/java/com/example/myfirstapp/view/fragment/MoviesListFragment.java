package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myfirstapp.R;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.adapters.FilmGridAdapter;
import com.example.myfirstapp.view.interfaces.ItemClickListener;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import java.io.FileDescriptor;
import java.io.PrintWriter;

import javax.inject.Inject;

public class MoviesListFragment extends Fragment implements ItemClickListener {
    @Inject
    public MoviesListViewModel model;

    ConstraintLayout constraintLayout;
    RecyclerView recyclerView;
    FilmGridAdapter adapter;
    RecyclerView.LayoutManager layoutManager;

    boolean dualPane;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity) getActivity()).moviesComponent.inject(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        constraintLayout =  (ConstraintLayout) inflater.inflate(R.layout.films_fragment, container, false);
        recyclerView = constraintLayout.findViewById(R.id.recycler_view);

        layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new FilmGridAdapter(new Film[]{}, this);
        recyclerView.setAdapter(adapter);

        return constraintLayout;
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model.fetchFilms();

        model.films.observe(this, films -> {
            constraintLayout.removeView(constraintLayout.getViewById(R.id.progressBar3));

            adapter.setDataSet(films.toArray(new Film[films.size()]));
            adapter.notifyDataSetChanged();
        });

        model.fetchingFilmsError.observe(this, error -> {
            if (error == true) {
                constraintLayout.getViewById(R.id.progressBar3).setVisibility(View.GONE);
                constraintLayout.getViewById(R.id.error_screen).setVisibility(View.VISIBLE);
            }
        });

        constraintLayout.getViewById(R.id.error_screen).getRootView().findViewById(R.id.retry_button).setOnClickListener(v -> {
            constraintLayout.getViewById(R.id.error_screen).setVisibility(View.GONE);
            constraintLayout.getViewById(R.id.progressBar3).setVisibility(View.VISIBLE);

            model.fetchFilms();
        });
    }

    @Override
    public void onItemClicked (int itemId) {
        MoviesListFragmentDirections.DetailAction action = MoviesListFragmentDirections.detailAction().setFilmId(itemId);
        NavHostFragment.findNavController(this).navigate(action);
    }

    @Override
    public void dump(@NonNull String prefix, @Nullable FileDescriptor fd, @NonNull PrintWriter writer, @Nullable String[] args) {
        super.dump(prefix, fd, writer, args);
    }
}
