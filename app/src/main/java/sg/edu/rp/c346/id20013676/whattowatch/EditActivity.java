package sg.edu.rp.c346.id20013676.whattowatch;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditActivity extends AppCompatActivity {

    DatePickerDialog.OnDateSetListener dateSetListener;
    Movie data;
    TextView etName, etDescription, etGenre, etDate;
    Button btnUpdate, btnDelete, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        etName = findViewById(R.id.tvEditName);
        etDescription = findViewById(R.id.tvEditDescription);
        etGenre = findViewById(R.id.tvEditGenre);
        etDate = findViewById(R.id.tvEditDate);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);

        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Intent i = getIntent();
        data = (Movie) i.getSerializableExtra("data");

        ratingBar.setRating(data.getStars());
        etName.setText(data.getName());
        etDescription.setText(data.getDescription());
        etDate.setText(data.getDate());
        etGenre.setText(data.getGenre());


        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper dbh = new DBHelper(EditActivity.this);
                data.setName(etName.getText().toString());
                data.setDescription(etDescription.getText().toString());
                data.setGenre(etGenre.getText().toString());
                data.setDate(etDate.getText().toString());
                data.setStars((int) ratingBar.getRating());
                dbh.updateMovie(data);
                dbh.close();
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to delete the movie \n" + etName.getText());
                myBuilder.setCancelable(false);
                myBuilder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DBHelper dbh = new DBHelper(EditActivity.this);
                        dbh.deleteMovie(data.get_id());
                        finish();
                    }
                });
                myBuilder.setPositiveButton("Cancel", null);

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(EditActivity.this);

                myBuilder.setTitle("Danger");
                myBuilder.setMessage("Are you sure you want to discard the changes");
                myBuilder.setCancelable(false);
                myBuilder.setPositiveButton("Do Not Discard", null);
                myBuilder.setNegativeButton("Discard", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });

        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(EditActivity.this,
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