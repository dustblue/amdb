package com.rakesh.amdb.GridActivityComponents;

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

import com.rakesh.amdb.Activities.Detailed;
import com.rakesh.amdb.Activities.GridActivity;
import com.rakesh.amdb.DataBaseManagement.DataBaseHandler;
import com.rakesh.amdb.POJOs.Movie;
import com.rakesh.amdb.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesTab extends Fragment {
    ArrayList<Movie> movie_data = new ArrayList<>();
    List<Movie> movies;
    GridView moviesView;
    TextView text;
    MovieGridAdapter adapter;
    DataBaseHandler db;
    Intent j;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab_movies,container,false);
        db = new DataBaseHandler(getContext());
        moviesView = (GridView)v.findViewById(R.id.moviesView);
        text = (TextView)v.findViewById(R.id.moviesDefaultText);

        GridActivity gridActivity = (GridActivity) getActivity();
        movies = db.getAllMovies(gridActivity.ifSort());
        for (Movie mv : movies) {
            if (mv.getType().equals("movie")) {
                movie_data.add(mv);
            }
        }

        if(movie_data.size() == 0) {
            text.setText(R.string.add_movies);
        } else {
            adapter = new MovieGridAdapter(getContext(), R.layout.grid_element, movie_data);
            moviesView.setAdapter(adapter);
        }

        j = new Intent(getContext(), Detailed.class);

        moviesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                j.putExtra("imdbID", movie_data.get(i).getImdbID());
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