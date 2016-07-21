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
import com.rakesh.amdb.Utils.Movie;
import com.rakesh.amdb.R;

import java.util.ArrayList;
import java.util.List;

public class SeriesTab extends Fragment {
    ArrayList<Movie> serie_data = new ArrayList<>();
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

        GridActivity gridActivity = (GridActivity) getActivity();
        movies = db.getAllMovies(gridActivity.ifSort());
        for (Movie mv : movies) {
            if (mv.getType().equals("series")) {
                serie_data.add(mv);
            }
        }

        if(serie_data.size() == 0) {
            stext.setText(R.string.add_series);
        } else {
            adapter = new MovieGridAdapter(getContext(), R.layout.grid_element, serie_data);
            seriesView.setAdapter(adapter);
        }

        j = new Intent(getContext(), Detailed.class);

        seriesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                j.putExtra("imdbID", serie_data.get(i).getImdbID());
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