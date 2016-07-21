package com.rakesh.amdb.Interfaces;

import com.rakesh.amdb.Utils.Movie;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface MovieService {
    @GET("?")
    Observable<Movie> getMovieData(@Query("t") String search);
}
