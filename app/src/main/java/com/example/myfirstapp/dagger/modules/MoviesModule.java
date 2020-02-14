package com.example.myfirstapp.dagger.modules;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.myfirstapp.repository.FilmRepository;
import com.example.myfirstapp.view.model.MoviesListViewModel;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public abstract class MoviesModule {

    @Provides static MoviesListViewModel provideMovieListViewModel (FilmRepository filmRepository, FragmentActivity fragmentActivity) {
        MoviesListViewModel model = ViewModelProviders.of(fragmentActivity).get(MoviesListViewModel.class);
         model.setFilmRepo(filmRepository);

        return model;
    }
}
