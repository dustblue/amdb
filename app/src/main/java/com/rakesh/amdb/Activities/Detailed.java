package com.rakesh.amdb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.rakesh.amdb.DataBaseManagement.DataBaseHandler;
import com.rakesh.amdb.Utils.Movie;
import com.rakesh.amdb.R;
import com.rakesh.amdb.Interfaces.TrailerService;
import com.rakesh.amdb.Config.YouTubeConfig;
import com.rakesh.amdb.Utils.YouTubeSearch;
import com.squareup.picasso.Picasso;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class Detailed extends AppCompatActivity {
    DataBaseHandler db;
    Observable<YouTubeSearch> trailerObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        db = new DataBaseHandler(this);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("imdbID");
        Movie m = db.getMovie(id);

        ImageView poster = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(m.getPoster())
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .resize(300, 450)
                .into(poster);

        TextView title = (TextView) findViewById(R.id.title);
        title.setText(m.getTitle());

        TextView year = (TextView) findViewById(R.id.year);
        year.setText(m.getYear());

        TextView plot = (TextView) findViewById(R.id.plot);
        plot.setText(m.getPlot());

        TextView genre = (TextView) findViewById(R.id.genre);
        genre.setText(m.getGenre());

        TextView director = (TextView) findViewById(R.id.director);
        director.setText("Directed by, " + m.getDirector());

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText("IMDB Rating: " + m.getImdbRating());

        FloatingActionButton fab_del = (FloatingActionButton) findViewById(R.id.fab_del);
        fab_del.setOnClickListener(view -> {
            db.delMovie(m);
            Intent k = new Intent(this, GridActivity.class);
            startActivity(k);
        });

        FloatingActionButton fab_trailer = (FloatingActionButton) findViewById(R.id.fab_trailer);
        fab_trailer.setOnClickListener(view -> {

            Intent l = new Intent(Detailed.this, TrailerView.class);

            Retrofit retrofit = new Retrofit.Builder()
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://www.googleapis.com/youtube/v3/")
                    .build();

            TrailerService trailerService = retrofit.create(TrailerService.class);

            trailerObservable = trailerService.getTrailerData(YouTubeConfig.YOUTUBE_API_KEY, m.getTitle()+ " movie trailer");

            trailerObservable.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(youTubeSearch -> {
                        if (youTubeSearch.getItems().get(0).getId().getVideoId()!= null) {
                            l.putExtra("src" , youTubeSearch.getItems().get(0).getId().getVideoId());
                            startActivity(l);
                        }
                        else Toast.makeText(Detailed.this, "Trailer Not found", Toast.LENGTH_SHORT);
                    });
        });
    }

    @Override
    public void onStop(){
        db.close();
        super.onStop();
    }
}
