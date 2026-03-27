package com.example.mobile_app_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app_2.adapter.TheaterAdapter;
import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Theater;

import java.util.List;

public class TheaterListActivity extends AppCompatActivity {

    private RecyclerView rvTheaters;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_list);

        db = AppDatabase.getDatabase(this);
        rvTheaters = findViewById(R.id.rvTheaters);
        rvTheaters.setLayoutManager(new LinearLayoutManager(this));

        List<Theater> theaters = db.appDao().getAllTheaters();
        TheaterAdapter adapter = new TheaterAdapter(theaters, theater -> {
            Intent intent = new Intent(TheaterListActivity.this, ShowtimeListActivity.class);
            intent.putExtra("theaterId", theater.id);
            intent.putExtra("theaterName", theater.name);
            startActivity(intent);
        });
        rvTheaters.setAdapter(adapter);
    }
}