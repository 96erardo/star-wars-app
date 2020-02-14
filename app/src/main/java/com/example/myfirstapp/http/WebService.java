package com.example.myfirstapp.http;

import com.google.gson.JsonObject;

import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

@Singleton
public interface WebService {
    @GET("films")
    Call<JsonObject> getFilms();

    @GET("films/{id}")
    Call<JsonObject> getFilm (@Path("id") int id);
}
