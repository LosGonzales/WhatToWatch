package sg.edu.rp.c346.id20013676.whattowatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.Spinner;

import java.io.Serializable;
import java.util.ArrayList;

public class ShowMoviesActivity extends AppCompatActivity {

    ArrayList<Movie> al = new ArrayList<Movie>();
    ListView lv;
    ArrayAdapter<String> aa;
    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_movies);

        lv = findViewById(R.id.lv);
        Button btnFilter = findViewById(R.id.btnUpdate);
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        DBHelper dbh = new DBHelper(ShowMoviesActivity.this);

//        al.add(new Movie("name", "genre", "desc", "2020-02-12", 2));
        al.addAll(dbh.getAllMovies());
        adapter = new CustomAdapter(this,
                R.layout.row, al);
        lv.setAdapter(adapter);

        final boolean[] isFiltered = {false};

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int
                    position, long identity) {
                Movie data = al.get(position);
                Intent i = new Intent(ShowMoviesActivity.this,
                        EditActivity.class);
                i.putExtra("data", data);
                startActivity(i);
            }
        });

        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(ShowMoviesActivity.this);
                if (!isFiltered[0]) {
                    al.clear();
                    al.addAll(dbh.getAllMovies("5"));
                    adapter.notifyDataSetChanged();
                    isFiltered[0] = true;
                } else {
                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    adapter.notifyDataSetChanged();
                    isFiltered[0] = false;
                }
                spinner.setSelection(spinner.getCount()-1);
            }
        });

        ArrayList<String> genres = new ArrayList<String>();
        genres.addAll(dbh.getAllGenres());
        genres.add("All Genres");

        aa = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, genres);
        spinner.setAdapter(aa);
        spinner.setSelection(spinner.getCount()-1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                DBHelper dbh = new DBHelper(ShowMoviesActivity.this);
                if (spinner.getSelectedItem().toString().equalsIgnoreCase("All Genres")) {
                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    adapter.notifyDataSetChanged();
                } else {
                    al.clear();
                    al.addAll(dbh.filterGenre(spinner.getSelectedItem().toString()));
                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        DBHelper dbh = new DBHelper(ShowMoviesActivity.this);
        al.clear();
        al.addAll(dbh.getAllMovies());
        adapter.notifyDataSetChanged();
    }
}