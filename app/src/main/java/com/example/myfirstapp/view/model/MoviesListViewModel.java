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

    public LiveData<Film> film;

    public int selected = 0;

    public void setFilmRepo (FilmRepository filmRepo) {
        this.filmRepo = filmRepo;
    }

    public void fetchFilms () {
        films = filmRepo.getFilms();
    }

    public void fetchFilm (int filmId) { film = filmRepo.getFilm(filmId); }

    public Film findFilm (int filmId) {
        ArrayList<Film> films = this.films.getValue();

        for (Film film : films) {
            if (film.id == filmId) {
                return film;
            }
        }

        return null;
    }
}
