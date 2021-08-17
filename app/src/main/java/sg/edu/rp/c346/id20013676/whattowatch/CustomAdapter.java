package sg.edu.rp.c346.id20013676.whattowatch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {
    Context parent_context;
    int layout_id;
    ArrayList<Movie> MovieList;

    public CustomAdapter(Context context, int resource, ArrayList<Movie> objects) {
        super(context, resource, objects);

        this.parent_context = context;
        this.layout_id = resource;
        this.MovieList = objects;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(layout_id, parent, false);

        TextView tvName = rowView.findViewById(R.id.textViewName);
        TextView tvGenre = rowView.findViewById(R.id.textViewGenre);
//        TextView tvStars = rowView.findViewById(R.id.textViewStars);
        TextView tvDesc = rowView.findViewById(R.id.textViewDescription);
        TextView tvDate = rowView.findViewById(R.id.textViewDate);
//        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);
        RatingBar ratingBar = (RatingBar) rowView.findViewById(R.id.ratingBar);

        Movie Movie = MovieList.get(position);

        tvName.setText(Movie.getName());
        tvGenre.setText(Movie.getGenre() + "");
//        tvStars.setText(Movie.toStars());
        tvDate.setText(Movie.getDate());
        tvDesc.setText(Movie.getDescription());
        ratingBar.setRating(Movie.getStars());

//        if (Movie.getGenre() > 4) {
//            ivNew.setVisibility(View.VISIBLE);
//        } else {
//            ivNew.setVisibility(View.INVISIBLE);
//        }



        return rowView;
    }



}
