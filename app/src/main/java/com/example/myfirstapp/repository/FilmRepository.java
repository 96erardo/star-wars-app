package com.example.myfirstapp.repository;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirstapp.db.AppDatabase;
import com.example.myfirstapp.db.dao.FilmDao;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.http.FilmsService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executor;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FilmRepository {
    private FilmsService service = new Retrofit.Builder()
            .baseUrl("https://swapi.co/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FilmsService.class);

    private Context context;

    public FilmRepository (Context context) {
        this.context = context;
    }

    public LiveData<ArrayList<Film>> getFilms () {
        final MutableLiveData<ArrayList<Film>> data = new MutableLiveData<ArrayList<Film>>();

        // In case i need local database
        AppDatabase db = AppDatabase.getInstance(context);
        FilmDao filmDao = db.filmDao();

        service.getFilms().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    JsonObject body = response.body();
                    JsonArray results = (JsonArray) body.get("results");
                    ArrayList<Film> films = new ArrayList<Film>();

                    for (JsonElement result : results) {
                        JsonObject film = result.getAsJsonObject();

                        films.add(new Film(
                            film.get("episode_id").getAsInt(),
                            film.get("episode_id").getAsInt(),
                            film.get("title").getAsString(),
                            film.get("opening_crawl").getAsString()
                        ));
                    }


                    data.setValue(films);
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                System.out.println("result " +  t.getMessage());
            }
        });

        return data;
    }
}
