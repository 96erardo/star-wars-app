package com.example.myfirstapp.repository;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.myfirstapp.db.AppDatabase;
import com.example.myfirstapp.db.dao.FilmDao;
import com.example.myfirstapp.db.models.Cover;
import com.example.myfirstapp.db.models.Film;
import com.example.myfirstapp.http.WebService;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Response;

@Singleton
public class FilmRepository {
    private WebService service;

    public AppDatabase appDatabase;

    @Inject
    public FilmRepository (AppDatabase appDatabase, WebService service) {
        this.appDatabase = appDatabase;
        this.service = service;
    }

    public LiveData<ArrayList<Film>> getFilms () {
        final MutableLiveData<ArrayList<Film>> data = new MutableLiveData<ArrayList<Film>>();

        new AsyncTask<Void, Void, ArrayList<Film>> () {
            @Override
            protected ArrayList<Film> doInBackground(Void... voids) {
                ArrayList<Film> filmsAL = new ArrayList<Film>();

                Cover covers[] = appDatabase.coverDao().getCovers();

                try {
                    Response<JsonObject> response = service.getFilms().execute();

                    if (response.isSuccessful()) {
                        filmsAL = getFilmsFromJsonArray((JsonArray) response.body().get("results"));

                        for (int i = 0; i < filmsAL.size(); i++) {
                            Film film = filmsAL.get(i);
                            Cover cover = appDatabase.coverDao().getCover(film.episode);

                            if (cover != null) {
                                film.cover = cover.url;
                            }

                            filmsAL.set(i, film);
                        }

                        Collections.sort(filmsAL);

                        return filmsAL;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    Film films[] = appDatabase.filmDao().getFilms();

                    for (Film film : films) {
                        filmsAL.add(film);
                    }
                }

                return filmsAL;
            }

            @Override
            protected void onPostExecute(ArrayList<Film> films) {
                super.onPostExecute(films);

                data.setValue(films);
            }
        }.execute();

        return data;
    }

    public LiveData<Film> getFilm (int filmId) {
        final MutableLiveData<Film> film = new MutableLiveData<Film>();

        new AsyncTask<Integer, Void, Film> () {
            @Override
            protected Film doInBackground(Integer... integers) {
                FilmDao filmDao = appDatabase.filmDao();
                Film film = filmDao.getFilm(integers[0]);

                if (film != null) {
                    return film;
                }

                try {

                    Response<JsonObject> response = service.getFilm(integers[0]).execute();
                    film = getFilmFromJsonObject(response.body());
                    filmDao.insertFilm(film);

                    return film;

                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            protected void onPostExecute(Film filmRes) {
                super.onPostExecute(filmRes);

                film.setValue(filmRes);
            }
        }.execute(filmId);

        return film;
    }

    private ArrayList<Film> getFilmsFromJsonArray (JsonArray array) {
        ArrayList<Film> films = new ArrayList<Film>();

        for (int i = 0; i < array.size(); i++)
            films.add(getFilmFromJsonObject(array.get(i).getAsJsonObject()));

        return films;
    }

    private Film getFilmFromJsonObject (JsonObject object) {
        String[] urlPieces = object.get("url").getAsString().split("/");
        int filmId = Integer.parseInt(urlPieces[urlPieces.length - 1]);

        return new Film(
            filmId,
            object.get("episode_id").getAsInt(),
            object.get("title").getAsString(),
            object.get("opening_crawl").getAsString()
        );
    }
}
