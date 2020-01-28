package com.example.myfirstapp.dagger.componets;

import androidx.fragment.app.FragmentActivity;

import com.example.myfirstapp.dagger.modules.MoviesModule;
import com.example.myfirstapp.dagger.scopes.ActivityScope;
import com.example.myfirstapp.view.fragment.DetailsFragment;
import com.example.myfirstapp.view.fragment.MoviesListFragment;

import dagger.BindsInstance;
import dagger.Subcomponent;

@ActivityScope
@Subcomponent(modules = {
    MoviesModule.class,
})
public interface MoviesComponent {
    @Subcomponent.Factory
    interface Factory {
        MoviesComponent create (@BindsInstance FragmentActivity fragmentActivity);
    }

    void inject (MoviesListFragment moviesListFragment);

    void inject (DetailsFragment detailsFragment);
}