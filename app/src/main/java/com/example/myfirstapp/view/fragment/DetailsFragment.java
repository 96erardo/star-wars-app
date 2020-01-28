package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import javax.inject.Inject;

public class DetailsFragment extends Fragment {
    @Inject
    MoviesListViewModel model;

    public static DetailsFragment newInstance (int index) {
        DetailsFragment f = new DetailsFragment();

        // Supply index input as an argument.
        Bundle args = new Bundle();
        args.putInt("index", index);
        f.setArguments(args);

        return f;
    }

    public int getShownIndex() {
        return getArguments().getInt("index", 0);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        ((MainActivity) getActivity()).moviesComponent.inject(this);
    }

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }

        return inflater.inflate(R.layout.deatils_activity, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        model.fetchFilm(model.selected);

        model.film.observe(this, film -> {
            if (film != null) {
                MotionLayout layout = (MotionLayout) getView();
                ProgressBar progressBar = layout.findViewById(R.id.progressBar);

                layout.removeView(progressBar);

                TextView text = layout.findViewById(R.id.openingCrawl);

                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
                text.setPadding(padding, padding, padding, padding);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                text.setText(film.openingCrawl);

                layout.transitionToEnd();
            }
        });

    }
}
