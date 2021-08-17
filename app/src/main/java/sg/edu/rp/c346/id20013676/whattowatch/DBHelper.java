package sg.edu.rp.c346.id20013676.whattowatch;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import sg.edu.rp.c346.id20013676.whattowatch.Movie;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "simpleMOVIEs.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_MOVIE = "movie";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_GENRE = "genre";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_STARS = "stars";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createMovieTableSql = "CREATE TABLE " + TABLE_MOVIE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_GENRE + " TEXT, "
                + COLUMN_DESCRIPTION + " TEXT, "
                + COLUMN_DATE + " DATE, "
                + COLUMN_STARS + " INTEGER ) ";
        db.execSQL(createMovieTableSql);
        Log.i("info", "created tables");

        //Dummy records, to be inserted when the database is created
        for (int i = 0; i< 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Name " + i);
            values.put(COLUMN_GENRE, "Genre " + i);
            values.put(COLUMN_DESCRIPTION, "Desc " + i);
            values.put(COLUMN_DATE, i);
            values.put(COLUMN_STARS, i);

            db.insert(TABLE_MOVIE, null, values);
        }
        Log.i("info", "dummy records inserted");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("ALTER TABLE " + TABLE_MOVIE + " ADD COLUMN  genre TEXT ");

    }


    public long insertMovie(String name, String genre, String description, String date, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_GENRE, genre);
        values.put(COLUMN_DESCRIPTION, description);
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_STARS, stars);
        long result = db.insert(TABLE_MOVIE, null, values);
        db.close();
        Log.d("SQL Insert","ID:"+ result); //id returned, shouldn’t be -1
        return result;
    }

    public ArrayList<Movie> getAllMovies()  {
        ArrayList<Movie> movies = new ArrayList<Movie>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_GENRE + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_DATE + ","
                + COLUMN_STARS +
                " FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String genre = cursor.getString(2);
                String description = cursor.getString(3);
                String date = cursor.getString(4);
                int stars = cursor.getInt(5);
                Movie movie = new Movie(name, genre, description, date, stars);
                movie.set_id(id);
                movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return movies;
    }

    public int updateMovie(Movie data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_GENRE, data.getGenre());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_DATE, data.getDate());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_MOVIE, values, condition, args);
        db.close();
        return result;
    }


    public int deleteMovie(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_MOVIE, condition, args);
        db.close();
        return result;
    }
    public ArrayList<Movie> getAllMovies(String keyword) {
        ArrayList<Movie> Movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_STARS};
        String condition = COLUMN_STARS + " = ? OR " + COLUMN_STARS + " = ?";
        String[] args = {"5", "4"};
        Cursor cursor = db.query(TABLE_MOVIE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String genre = cursor.getString(2);
                String description = cursor.getString(3);
                String date = cursor.getString(4);
                int stars = cursor.getInt(5);
                Movie movie = new Movie(name, genre, description, date, stars);
                movie.set_id(id);
                Movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Movies;
    }

    public ArrayList<String> getAllGenres() {
        ArrayList<String> Genres = new ArrayList<String>();

        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + ","
                + COLUMN_GENRE + ","
                + COLUMN_DESCRIPTION + ","
                + COLUMN_DATE + ","
                + COLUMN_STARS +
                " FROM " + TABLE_MOVIE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String genre = cursor.getString(2);
                Genres.add(genre + "");
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Genres;
    }

//    public ArrayList<Movie> filterGenre(String keyword) {
//        ArrayList<Movie> Movies = new ArrayList<Movie>();
//
//        SQLiteDatabase db = this.getReadableDatabase();
//        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_STARS};
//        String condition = COLUMN_GENRE + " = ?";
//        String[] args = {keyword};
//        Cursor cursor = db.query(TABLE_MOVIE, columns, condition, args,
//                null, null, null, null);
//
//        if (cursor.moveToFirst()) {
//            do {
//                int id = cursor.getInt(0);
//                String name = cursor.getString(1);
//                String genre = cursor.getString(2);
//                String description = cursor.getString(3);
//                String date = cursor.getString(4);
//                int stars = cursor.getInt(5);
//                Movie movie = new Movie(name, genre, description, date, stars);
//                movie.set_id(id);
//                Movies.add(movie);
//            } while (cursor.moveToNext());
//        }
//        cursor.close();
//        db.close();
//        return Movies;
//    }

    public ArrayList<Movie> filterGenre(String keyword) {
        ArrayList<Movie> Movies = new ArrayList<Movie>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_GENRE, COLUMN_DESCRIPTION, COLUMN_DATE, COLUMN_STARS};
        String condition = COLUMN_GENRE + " = ?";
        String[] args = {keyword};
        Cursor cursor = db.query(TABLE_MOVIE, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String genre = cursor.getString(2);
                String description = cursor.getString(3);
                String date = cursor.getString(4);
                int stars = cursor.getInt(5);
                Movie movie = new Movie(name, genre, description, date, stars);
                movie.set_id(id);
                Movies.add(movie);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Movies;
    }

}

