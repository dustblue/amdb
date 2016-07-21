package com.rakesh.amdb.Interfaces;


import com.rakesh.amdb.POJOs.YouTubeSearch;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface TrailerService {
    @GET("search?part=snippet&maxResults=1&fields=items%2Fid%2FvideoId&")
    Observable<YouTubeSearch>getTrailerData(@Query("key") String key, @Query("q") String name);
}

