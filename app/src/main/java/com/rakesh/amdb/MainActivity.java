package com.rakesh.amdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
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

    public interface MovieService {
        @GET("?")
        Observable<Movie> getMovieData(@Query("t") String name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBaseHandler(this);
        EditText editText = (EditText)findViewById(R.id.editText);
        Intent j = new Intent(this, GridActivity.class);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            Retrofit retrofit = new Retrofit().Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("http://omdbapi.com/")
                    .build();

            MovieService movieService = retrofit.create(MovieService.class);
            Observable<Movie> movieObservable = movieService.getMovieData(editText.getText().toString());
            movieObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(movie -> {
                        if (movie.getResponse().equals("true")) {
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
        db.close();
        super.onStop();
    }
}
