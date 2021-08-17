package sg.edu.rp.c346.id20013676.whattowatch;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button btnAdd, btnShow;
    EditText etName, etDescription, etGenre, etDate;
    RadioGroup rg;
    ArrayList<Movie> al;
    int stars;
    DatePickerDialog.OnDateSetListener dateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnAdd = findViewById(R.id.btnUpdate);
        btnShow = findViewById(R.id.btnDelete);
        etName = findViewById(R.id.tvEditName);
        etDescription = findViewById(R.id.tvEditDescription);
        etGenre = findViewById(R.id.tvEditGenre);
        etDate = findViewById(R.id.tvEditDate);
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        al = new ArrayList<Movie>();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String description = etDescription.getText().toString();
                String genre = etGenre.getText().toString();
                String date = etDate.getText().toString();
                DBHelper dbh = new DBHelper(MainActivity.this);
                long inserted_id = dbh.insertMovie(name, description, genre, date, stars);
                if (inserted_id != -1) {
                    al.clear();
                    al.addAll(dbh.getAllMovies());
                    Toast.makeText(MainActivity.this, "Insert successful",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,
                        ShowMoviesActivity.class);
                DBHelper dbh = new DBHelper(MainActivity.this);
                startActivity(i);

            }
        });

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {
                stars = (int) rating;

            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                        R.style.DialogTheme, dateSetListener, year, month, day);
                dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.color.black));
                dialog.show();
            }
        });

        // Set edit text to selected date
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar cal = Calendar.getInstance();
                cal.set(year, month, dayOfMonth);
                etDate.setText(sdf.format(cal.getTime()));
            }
        };

    }
}