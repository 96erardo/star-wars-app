package com.example.myfirstapp.dagger.modules;

import com.example.myfirstapp.dagger.componets.MoviesComponent;

import dagger.Module;

@Module(subcomponents = {
    MoviesComponent.class,
})
public class SubcomponentsModule {
}
