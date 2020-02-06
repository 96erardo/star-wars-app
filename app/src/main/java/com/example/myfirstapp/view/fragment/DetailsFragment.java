package com.example.myfirstapp.view.fragment;

import android.content.Context;
import android.media.MediaPlayer;
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

import javax.inject.Inject;

public class DetailsFragment extends Fragment {
    @Inject
    MoviesListViewModel model;

    MediaPlayer mediaPlayer = null;

    PlaybackCompleted playbackCompleted = new PlaybackCompleted();

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
        mediaPlayer.setOnSeekCompleteListener(playbackCompleted);
        model.fetchFilm(filmId);

        model.film.observe(this, film -> {
            if (film != null) {
                MotionLayout layout = (MotionLayout) getView();
                ProgressBar progressBar = layout.findViewById(R.id.progressBar);

                layout.removeView(progressBar);

                mediaPlayer.start();
                TextView text = layout.findViewById(R.id.openingCrawl);

                int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getActivity().getResources().getDisplayMetrics());
                text.setPadding(padding, padding, padding, padding);
                text.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);

                text.setText(film.openingCrawl);

                layout.transitionToEnd();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        if (playerPosition != 0) {
            mediaPlayer = MediaPlayer.create(getContext(), R.raw.sw_intro);
            mediaPlayer.seekTo(playerPosition);
            mediaPlayer.setOnSeekCompleteListener(playbackCompleted);
            mediaPlayer.start();
        }
    }

    @Override
    public void onStop() {
        super.onStop();

        playerPosition = mediaPlayer.getCurrentPosition();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    private class PlaybackCompleted implements MediaPlayer.OnSeekCompleteListener {

        @Override
        public void onSeekComplete(MediaPlayer mp) {
            System.out.println("onSeekComplete");
            playerPosition = 0;
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
