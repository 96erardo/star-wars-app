package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Space;
import android.widget.TextView;

import com.example.myfirstapp.R;
import com.example.myfirstapp.view.MainActivity;
import com.example.myfirstapp.view.components.SpaceView;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import javax.inject.Inject;

public class DetailsFragment extends Fragment {
    @Inject
    MoviesListViewModel model;

    MediaPlayer mediaPlayer = null;

    SpaceView spaceView = null;

    int playerPosition = 0;

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

        int filmId = DetailsFragmentArgs.fromBundle(getArguments()).getFilmId();

        mediaPlayer = MediaPlayer.create(getContext(), R.raw.sw_intro);
        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.release();
            mediaPlayer = null;

            NavHostFragment.findNavController(this).popBackStack();
        });
        model.fetchFilm(filmId);

        model.film.observe(this, film -> {
            if (film != null) {
                MotionLayout layout = (MotionLayout) getView();
                TextView opening = layout.findViewById(R.id.opening);
                spaceView = layout.findViewById(R.id.space);

                TextView text = layout.findViewById(R.id.openingCrawl);
                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
                text.setPadding(padding, padding, padding, padding);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                text.setText(film.openingCrawl);

                layout.removeView(opening);
                mediaPlayer.start();
                spaceView.setStarsColor(Color.WHITE);

                layout.transitionToEnd();

                layout.setTransitionListener(new MotionLayout.TransitionListener() {
                    @Override public void onTransitionStarted(MotionLayout motionLayout, int i, int i1) { }

                    @Override public void onTransitionChange(MotionLayout motionLayout, int i, int i1, float v) { }

                    @Override public void onTransitionTrigger(MotionLayout motionLayout, int i, boolean b, float v) { }

                    @Override public boolean allowsTransition(MotionScene.Transition transition) { return false; }

                    @Override
                    public void onTransitionCompleted(MotionLayout motionLayout, int i) {
                        spaceView.startAnimation();
                    }
                });
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (spaceView != null) {
            spaceView.resume();
        }

        if (playerPosition != 0) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.sw_intro);
            mediaPlayer.seekTo(playerPosition);
            mediaPlayer.setOnCompletionListener(mp -> {
                mediaPlayer.release();
                mediaPlayer = null;

                NavHostFragment.findNavController(this).popBackStack();
            });

            mediaPlayer.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        if (spaceView != null) {
            spaceView.pause();
        }

        if (mediaPlayer != null) {
            playerPosition = mediaPlayer.getCurrentPosition();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
