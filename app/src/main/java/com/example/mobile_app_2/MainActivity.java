package com.example.mobile_app_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab_1.data.AppDatabase;
import com.example.lab_1.data.Movie;
import com.example.lab_1.data.Showtime;
import com.example.lab_1.data.Theater;
import com.example.lab_1.data.User;
import com.example.lab_1.utils.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button btnViewMovies, btnViewTheaters, btnViewTickets, btnLogin, btnLogout;
    private PreferenceManager prefManager;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppDatabase.getDatabase(this);
        prefManager = new PreferenceManager(this);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnViewMovies = findViewById(R.id.btnViewMovies);
        btnViewTheaters = findViewById(R.id.btnViewTheaters);
        btnViewTickets = findViewById(R.id.btnViewTickets);
        btnLogin = findViewById(R.id.btnLogin);
        btnLogout = findViewById(R.id.btnLogout);

        initDemoData();

        btnViewMovies.setOnClickListener(v -> startActivity(new Intent(this, MovieListActivity.class)));
        btnViewTheaters.setOnClickListener(v -> startActivity(new Intent(this, TheaterListActivity.class)));
        btnViewTickets.setOnClickListener(v -> startActivity(new Intent(this, TicketListActivity.class)));
        btnLogin.setOnClickListener(v -> startActivity(new Intent(this, LoginActivity.class)));
        btnLogout.setOnClickListener(v -> {
            prefManager.logout();
            updateUI();
            Toast.makeText(this, "Đã đăng xuất", Toast.LENGTH_SHORT).show();
        });

        updateUI();
    }

    private void updateUI() {
        if (prefManager.isLoggedIn()) {
            int userId = prefManager.getUserId();
            User user = db.appDao().getUserById(userId);
            if (user != null) {
                tvWelcome.setText("Xin chào, " + user.fullName);
            }
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            btnViewTickets.setVisibility(View.VISIBLE);
        } else {
            tvWelcome.setText("Welcome to Movie App");
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            btnViewTickets.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    private void initDemoData() {
        if (db.appDao().getAllMovies().isEmpty()) {
            db.appDao().insertMovie(new Movie("Avengers: Endgame", "Siêu anh hùng Marvel", "", "180 phút"));
            db.appDao().insertMovie(new Movie("Spider-Man: No Way Home", "Người nhện hành động", "", "150 phút"));
            db.appDao().insertMovie(new Movie("Dune", "Sử thi khoa học viễn tưởng", "", "155 phút"));

            db.appDao().insertTheater(new Theater("CGV Vincom Center", "72 Lê Thánh Tôn, Quận 1"));
            db.appDao().insertTheater(new Theater("Lotte Cinema Cantavil", "Lầu 7, Cantavil Premier, Quận 2"));

            db.appDao().insertShowtime(new Showtime(1, 1, "10:00 AM", "2023-12-10"));
            db.appDao().insertShowtime(new Showtime(1, 1, "02:00 PM", "2023-12-10"));
            db.appDao().insertShowtime(new Showtime(2, 2, "07:00 PM", "2023-12-10"));

            db.appDao().insertUser(new User("user", "123", "Nguyễn Văn A"));
        }
    }
}