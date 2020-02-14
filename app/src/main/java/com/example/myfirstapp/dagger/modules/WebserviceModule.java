package com.example.myfirstapp.dagger.modules;

import com.example.myfirstapp.http.WebService;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public abstract class WebserviceModule {
    @Provides
    @Singleton
    static WebService provideWebservice () {
        return new Retrofit.Builder()
                .baseUrl("https://swapi.co/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WebService.class);
    }
}
