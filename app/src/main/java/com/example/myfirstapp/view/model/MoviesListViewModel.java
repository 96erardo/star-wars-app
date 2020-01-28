package com.example.myfirstapp.view.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.repository.FilmRepository;

import java.util.ArrayList;

import javax.inject.Inject;

public class MoviesListViewModel extends ViewModel {
    @Inject
    public FilmRepository filmRepo;

    public LiveData<ArrayList<Film>> films;

    public int selected = 0;

    public void setFilmRepo (FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
    }

    public void fetchFilms () {
        films = filmRepo.getFilms();
    }
}
