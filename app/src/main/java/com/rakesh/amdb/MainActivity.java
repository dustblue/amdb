package com.rakesh.amdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EditText editText = (EditText)findViewById(R.id.editText);

        FloatingActionButton flab = (FloatingActionButton) findViewById(R.id.flab);
        flab.setOnClickListener(view -> {
            Intent j = new Intent(this, GridActivity.class);
            startActivity(j);
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent j = new Intent(this, GridActivity.class);
            db = new DataBaseHandler(this);


            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://omdbapi.com/")
                    .build();
            Log.d("check", "Retrofit Initialized");

            MovieService movieService = retrofit.create(MovieService.class);
            Log.d("check", "Interface Initialized");

            Observable<Movie> movieObservable = movieService.getMovieData(editText.getText().toString().toLowerCase());
            Log.d("check", "Observable Initialized");

            movieObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movie -> {
                        Log.d("check", "Receiving Query : " + movie.getTitle());
                        if (movie.getResponse().equals("True")) {
                            Log.d("check", "Query found");

                            db.addMovie(movie);
                            startActivity(j);
                            finish();
                        }
                        else Toast.makeText(MainActivity.this, "Not Found, Try Again", Toast.LENGTH_SHORT).show();
                    });
        });

    }

    @Override
    public void onStop(){
        super.onStop();
    }

    public interface MovieService {
        @GET("?")
        Observable<Movie> getMovieData(@Query("t") String title);
    }
}
