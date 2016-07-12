package com.rakesh.amdb;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHandler extends SQLiteOpenHelper
{
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "MoviesDB";

    private static final String TABLE_MOVIES = "Movies";

    private static final String KEY_IMDBID = "imdbid";
    private static final String KEY_TITLE = "title";
    private static final String KEY_TYPE = "type";
    private static final String KEY_YEAR = "year";
    private static final String KEY_GENRE = "genre";
    private static final String KEY_PLOT = "plot";
    private static final String KEY_DIRECTOR = "director";
    private static final String KEY_IMDBRATING = "imdbrating";
    private static final String KEY_RESPONSE = "response";
    private static final String KEY_POSTER = "poster";

    Movie movie;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_MOVIES_TABLE = "CREATE TABLE " + TABLE_MOVIES + "("
                + KEY_IMDBID + " TEXT PRIMARY KEY," + KEY_TITLE + " TEXT,"
                + KEY_TYPE + " TEXT," + KEY_YEAR + " INTEGER,"
                + KEY_GENRE + " TEXT," + KEY_PLOT + " TEXT"
                + KEY_DIRECTOR + " TEXT" + KEY_IMDBRATING + " TEXT"
                + KEY_RESPONSE + " TEXT" + KEY_POSTER + " TEXT" + ")";
        db.execSQL(CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }

    public void addMovie(Movie movie) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_IMDBID, movie.getImdbID());
        values.put(KEY_TITLE, movie.getTitle());
        values.put(KEY_TYPE, movie.getType());
        values.put(KEY_YEAR, movie.getYear());
        values.put(KEY_GENRE, movie.getGenre());
        values.put(KEY_PLOT, movie.getPlot());
        values.put(KEY_DIRECTOR, movie.getDirector());
        values.put(KEY_IMDBRATING, movie.getImdbRating());
        values.put(KEY_RESPONSE, movie.getResponse());
        values.put(KEY_POSTER, movie.getPoster());

        db.insert(TABLE_MOVIES, null, values);
        db.close();
    }

    public void delMovie(Movie movie) {
         SQLiteDatabase db = this.getWritableDatabase();
         db.delete(TABLE_MOVIES, KEY_IMDBID + " = ?", new String[] { String.valueOf(movie.getImdbID()) });
     }

    Movie getMovie(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_MOVIES, new String[] { KEY_IMDBID,
                        KEY_TITLE, KEY_TYPE, KEY_YEAR, KEY_GENRE, KEY_PLOT,
                        KEY_DIRECTOR, KEY_IMDBRATING, KEY_RESPONSE, KEY_POSTER }, KEY_IMDBID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            movie.setImdbID(cursor.getString(0));
            movie.setTitle(cursor.getString(1));
            movie.setType(cursor.getString(2));
            movie.setYear(cursor.getString(3));
            movie.setGenre(cursor.getString(4));
            movie.setPlot(cursor.getString(5));
            movie.setDirector(cursor.getString(6));
            movie.setImdbRating(cursor.getString(7));
            movie.setResponse(cursor.getString(8));
            movie.setPoster(cursor.getString(9));
        }
        cursor.close();
        return movie;
    }

    public List<Movie> getAllMovies(Boolean sort) {
        List<Movie> movieList = new ArrayList<>();
        String selectQuery;
        if (sort) {
            selectQuery = "SELECT  * FROM Movies ORDER BY title";
        } else {
            selectQuery = "SELECT  * FROM Movies ORDER BY imdbrating";
        }

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Movie movie = new Movie();
                movie.setImdbID(cursor.getString(0));
                movie.setTitle(cursor.getString(1));
                movie.setType(cursor.getString(2));
                movie.setYear(cursor.getString(3));
                movie.setGenre(cursor.getString(4));
                movie.setPlot(cursor.getString(5));
                movie.setDirector(cursor.getString(6));
                movie.setImdbRating(cursor.getString(7));
                movie.setResponse(cursor.getString(8));
                movie.setPoster(cursor.getString(9));

                movieList.add(movie);
            } while (cursor.moveToNext());
        }
        db.close();
        cursor.close();
        return movieList;
    }

}