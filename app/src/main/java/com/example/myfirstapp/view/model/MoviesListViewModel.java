package com.example.myfirstapp.view.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.repository.FilmRepository;

import java.util.ArrayList;

public class MoviesListViewModel extends AndroidViewModel {
    public FilmRepository filmRepo = new FilmRepository(getApplication());

    public LiveData<ArrayList<Film>> films = filmRepo.getFilms();

    public LiveData<Boolean> fetched;

    public int selected = 0;

    public MoviesListViewModel(@NonNull Application application) {
        super(application);
    }
}
