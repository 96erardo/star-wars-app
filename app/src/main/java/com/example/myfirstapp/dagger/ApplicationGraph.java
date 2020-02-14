package com.example.myfirstapp.dagger;

import android.content.Context;

import com.example.myfirstapp.SwApplication;
import com.example.myfirstapp.dagger.componets.MoviesComponent;
import com.example.myfirstapp.dagger.modules.AppContextModule;
import com.example.myfirstapp.dagger.modules.RoomModule;
import com.example.myfirstapp.dagger.modules.SubcomponentsModule;
import com.example.myfirstapp.dagger.modules.WebserviceModule;
import com.example.myfirstapp.repository.FilmRepository;
import com.example.myfirstapp.view.fragment.MoviesListFragment;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {
    AppContextModule.class,
    RoomModule.class,
    WebserviceModule.class,
    SubcomponentsModule.class
})
public interface ApplicationGraph {
    FilmRepository filmRepository();

    MoviesComponent.Factory moviesComponent();
}
