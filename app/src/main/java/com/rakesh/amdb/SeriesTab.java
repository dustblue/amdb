package com.rakesh.amdb;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SeriesTab extends Fragment {
    ArrayList<Movie> movie_data = new ArrayList<>();
    List<Movie> movies;
    GridView seriesView;
    TextView stext;
    MovieGridAdapter adapter;
    DataBaseHandler db;
    Intent j;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_series,container,false);
        db = new DataBaseHandler(getContext());
        seriesView = (GridView)v.findViewById(R.id.seriesView);
        stext = (TextView)v.findViewById(R.id.seriesDefaultText);

        movies = db.getAllMovies();
        for (Movie mv : movies) {
            if (mv.getType().equals("series")) {
                movie_data.add(mv);
            }
        }

        if(movie_data.size() == 0) {
            stext.setText(R.string.add_series);
        } else {
            adapter = new MovieGridAdapter(getContext(), R.layout.grid_element, movie_data);
            seriesView.setAdapter(adapter);
        }

        j = new Intent(getContext(), Detailed.class);

        seriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                j.putExtra("imdbid", movie_data.get(i).getImdbID());
                startActivity(j);
            }
        });

        return v;
    }

    @Override
    public void onStop(){
        db.close();
        super.onStop();
    }
}