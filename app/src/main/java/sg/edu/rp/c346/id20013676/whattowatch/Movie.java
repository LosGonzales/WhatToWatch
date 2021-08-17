package sg.edu.rp.c346.id20013676.whattowatch;


import java.io.Serializable;

public class Movie implements Serializable {

    private int _id, stars;
    private int count = 0;
    private String name, description, genre, date;

    public Movie(String name, String genre, String description, String date, int stars) {
        this._id = count++;
        this.stars = stars;
        this.name = name;
        this.description = description;
        this.genre = genre;
        this.date = date;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}

