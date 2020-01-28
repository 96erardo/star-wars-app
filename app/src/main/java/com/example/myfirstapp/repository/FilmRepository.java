package com.example.myfirstapp.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirstapp.db.AppDatabase;
import com.example.myfirstapp.db.dao.FilmDao;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.http.WebService;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Singleton
public class FilmRepository {
    private WebService service = new Retrofit.Builder()
            .baseUrl("https://swapi.co/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WebService.class);

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public AppDatabase appDatabase;

    @Inject
    public FilmRepository (AppDatabase appDatabase) {
        appDatabase = appDatabase;
    }

    public LiveData<ArrayList<Film>> getFilms () {
        final MutableLiveData<ArrayList<Film>> data = new MutableLiveData<ArrayList<Film>>();

        service.getFilms().enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.isSuccessful()) {
                    ArrayList<Film> films = getFilmsFromJsonArray((JsonArray) response.body().get("results"));

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

    public LiveData<Film> getFilm (int filmId) {
        MutableLiveData<Film> film = new MutableLiveData<Film>();

        executor.execute(new Runnable() {
            @Override
            public void run() {
                FilmDao filmDao = appDatabase.filmDao();
                int filmExists = filmDao.filmExists(filmId, TimeUnit.DAYS.toMillis(1));

                if (filmExists == 1) {
                    film.setValue(filmDao.getFilm(filmId));
                } else {

                }

            }
        });

        return film;
    }

    private ArrayList<Film> getFilmsFromJsonArray (JsonArray array) {
        ArrayList<Film> films = new ArrayList<Film>();

        for (JsonElement result : array)
            films.add(getFilmFromJsonObject(result.getAsJsonObject()));

        return films;
    }

    private Film getFilmFromJsonObject (JsonObject object) {
        return new Film(
            object.get("episode_id").getAsInt(),
            object.get("episode_id").getAsInt(),
            object.get("title").getAsString(),
            object.get("opening_crawl").getAsString()
        );
    }
}
