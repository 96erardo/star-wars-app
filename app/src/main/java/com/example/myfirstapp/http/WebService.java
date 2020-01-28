package com.example.myfirstapp.http;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

public interface WebService {
    @GET("films")
    Call<JsonObject> getFilms();
}
