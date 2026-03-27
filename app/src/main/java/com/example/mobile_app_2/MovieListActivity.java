package com.example.mobile_app_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app_2.adapter.MovieAdapter;
import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Movie;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        db = AppDatabase.getDatabase(this);
        rvMovies = findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));

        List<Movie> movies = db.appDao().getAllMovies();
        MovieAdapter adapter = new MovieAdapter(movies, movie -> {
            Intent intent = new Intent(MovieListActivity.this, ShowtimeListActivity.class);
            intent.putExtra("movieId", movie.id);
            intent.putExtra("movieTitle", movie.title);
            startActivity(intent);
        });
        rvMovies.setAdapter(adapter);
    }
}
