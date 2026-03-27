package com.example.mobile_app_2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app_2.adapter.TicketAdapter;
import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Ticket;
import com.example.mobile_app_2.utils.PreferenceManager;

import java.util.List;

public class TicketListActivity extends AppCompatActivity {

    private RecyclerView rvTickets;
    private AppDatabase db;
    private PreferenceManager prefManager;
    private TicketAdapter adapter;
    private List<Ticket> tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_list);

        db = AppDatabase.getDatabase(this);
        prefManager = new PreferenceManager(this);

        rvTickets = findViewById(R.id.rvTickets);
        rvTickets.setLayoutManager(new LinearLayoutManager(this));

        loadTickets();
    }

    private void loadTickets() {
        int userId = prefManager.getUserId();
        tickets = db.appDao().getTicketsByUser(userId);

        adapter = new TicketAdapter(tickets, db, ticket -> {
            db.appDao().deleteTicket(ticket);
            Toast.makeText(this, "Đã hủy vé ghế: " + ticket.seatNumber, Toast.LENGTH_SHORT).show();
            loadTickets(); // Refresh list
        });
        rvTickets.setAdapter(adapter);
    }
}