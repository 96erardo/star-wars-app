package com.example.myfirstapp.view.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.myfirstapp.dagger.scopes.ActivityScope;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.repository.FilmRepository;

import java.util.ArrayList;

import javax.inject.Inject;

@ActivityScope
public class MoviesListViewModel extends ViewModel {
    @Inject
    public FilmRepository filmRepo;

    public LiveData<ArrayList<Film>> films;

    public LiveData<Boolean> fetchingFilmsError;

    public LiveData<Film> film;

    public void setFilmRepo (FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
        this.fetchingFilmsError = filmRepo.fetchingFilmsError;
        this.films = filmRepo.data;
    }

    public void fetchFilms () {
        films = filmRepo.getFilms();
    }

    public void fetchFilm (int filmId) { film = filmRepo.getFilm(filmId); }
}
