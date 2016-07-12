package com.rakesh.amdb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieGridAdapter extends ArrayAdapter<Movie> {

    Context context;
    int layoutResId;
    ArrayList<Movie> data=new ArrayList<>();


    public MovieGridAdapter (Context context, int layoutResId, ArrayList<Movie> data) {
        super(context, layoutResId, data);
        this.layoutResId = layoutResId;
        this.data = data;
        this.context = context;
    }

    public class Holder
    {
        TextView name;
        TextView genre;
        ImageView poster;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        Holder holder=new Holder();
        LayoutInflater gridInflator = LayoutInflater.from(getContext());

        View rowView = gridInflator.inflate(layoutResId, parent, false);
        holder.name=(TextView) rowView.findViewById(R.id.name);
        holder.genre=(TextView) rowView.findViewById(R.id.genre);
        holder.poster=(ImageView) rowView.findViewById(R.id.poster);

        Movie movie = data.get(position);
        holder.name.setText(movie.getTitle());
        holder.genre.setText(movie.getType());
        Picasso.with(context)
                .load(movie.getPoster())
                .placeholder(R.drawable.ic_default)
                .error(R.drawable.ic_default)
                .resize(300, 450)
                .into(holder.poster);

        return rowView;
    }


}
