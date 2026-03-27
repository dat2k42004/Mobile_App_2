package com.example.mobile_app_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app_2.adapter.ShowtimeAdapter;
import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Showtime;
import com.example.mobile_app_2.utils.PreferenceManager;

import java.util.List;

public class ShowtimeListActivity extends AppCompatActivity {

    private RecyclerView rvShowtimes;
    private AppDatabase db;
    private PreferenceManager prefManager;
    private int movieId;
    private int theaterId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime_list);

        db = AppDatabase.getDatabase(this);
        prefManager = new PreferenceManager(this);

        movieId = getIntent().getIntExtra("movieId", -1);
        theaterId = getIntent().getIntExtra("theaterId", -1);
        
        String movieTitle = getIntent().getStringExtra("movieTitle");
        String theaterName = getIntent().getStringExtra("theaterName");

        TextView tvHeader = findViewById(R.id.tvMovieTitleHeader);
        if (movieId != -1) {
            tvHeader.setText("Lịch chiếu phim: " + movieTitle);
        } else if (theaterId != -1) {
            tvHeader.setText("Lịch chiếu tại rạp: " + theaterName);
        }

        rvShowtimes = findViewById(R.id.rvShowtimes);
        rvShowtimes.setLayoutManager(new LinearLayoutManager(this));

        loadShowtimes();
    }

    private void loadShowtimes() {
        List<Showtime> showtimes;
        boolean isMovieView = (movieId != -1);
        
        if (isMovieView) {
            showtimes = db.appDao().getShowtimesByMovie(movieId);
        } else {
            showtimes = db.appDao().getShowtimesByTheater(theaterId);
        }
        
        ShowtimeAdapter adapter = new ShowtimeAdapter(showtimes, db, isMovieView, showtime -> {
            if (prefManager.isLoggedIn()) {
                Intent intent = new Intent(ShowtimeListActivity.this, SeatSelectionActivity.class);
                intent.putExtra("showtimeId", showtime.id);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Vui lòng đăng nhập để đặt vé!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ShowtimeListActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        rvShowtimes.setAdapter(adapter);
    }
}
