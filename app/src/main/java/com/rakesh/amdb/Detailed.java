package com.rakesh.amdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Detailed extends AppCompatActivity {
    DataBaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        db = new DataBaseHandler(this);
        Bundle extras = getIntent().getExtras();
        String id = extras.getString("imdbid");
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
        director.setText("Directed by " + m.getDirector());

        TextView rating = (TextView) findViewById(R.id.rating);
        rating.setText("IMDB Rating: " + m.getImdbRating());

        FloatingActionButton fabx = (FloatingActionButton) findViewById(R.id.fabx);
        fabx.setOnClickListener(view -> {
            db.delMovie(m);
            Intent k = new Intent(this, GridActivity.class);
            startActivity(k);
        });
    }

    @Override
    public void onStop(){
        db.close();
        finish();
        super.onStop();
    }
}
