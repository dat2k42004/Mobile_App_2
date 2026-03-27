package com.example.mobile_app_2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Ticket;
import com.example.mobile_app_2.utils.PreferenceManager;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SeatSelectionActivity extends AppCompatActivity {

    private Set<String> selectedSeats = new HashSet<>();
    private List<String> bookedSeats;
    private int showtimeId;
    private AppDatabase db;
    private PreferenceManager prefManager;
    private TextView tvSelectedSeat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        db = AppDatabase.getDatabase(this);
        prefManager = new PreferenceManager(this);

        showtimeId = getIntent().getIntExtra("showtimeId", -1);

        // Fetch seats already booked for this showtime
        bookedSeats = db.appDao().getBookedSeats(showtimeId);

        GridLayout glSeats = findViewById(R.id.glSeats);
        tvSelectedSeat = findViewById(R.id.tvSelectedSeat);
        Button btnConfirm = findViewById(R.id.btnConfirmBooking);

        // Generate demo seats
        for (int i = 1; i <= 16; i++) {
            Button btnSeat = new Button(this);
            String seatName = "G" + i;
            btnSeat.setText(seatName);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = GridLayout.LayoutParams.WRAP_CONTENT;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);
            btnSeat.setLayoutParams(params);

            // Check if seat is already booked
            if (bookedSeats.contains(seatName)) {
                btnSeat.setBackgroundColor(Color.DKGRAY);
                btnSeat.setEnabled(false);
                btnSeat.setText(seatName + " (X)");
            } else {
                btnSeat.setBackgroundColor(Color.LTGRAY);
                btnSeat.setOnClickListener(v -> {
                    if (selectedSeats.contains(seatName)) {
                        selectedSeats.remove(seatName);
                        btnSeat.setBackgroundColor(Color.LTGRAY);
                    } else {
                        selectedSeats.add(seatName);
                        btnSeat.setBackgroundColor(Color.GREEN);
                    }
                    updateSelectedSeatsText();
                });
            }
            glSeats.addView(btnSeat);
        }

        btnConfirm.setOnClickListener(v -> {
            if (selectedSeats.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn ít nhất một ghế!", Toast.LENGTH_SHORT).show();
                return;
            }

            int userId = prefManager.getUserId();

            // Double check if any selected seat was booked during selection (optional but good)
            for (String seat : selectedSeats) {
                if (db.appDao().getTicketByShowtimeAndSeat(showtimeId, seat) != null) {
                    Toast.makeText(this, "Ghế " + seat + " vừa có người đặt. Vui lòng chọn lại!", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Save all selected tickets
            for (String seat : selectedSeats) {
                Ticket ticket = new Ticket(userId, showtimeId, seat);
                db.appDao().insertTicket(ticket);
            }

            Toast.makeText(this, "Đặt vé thành công " + selectedSeats.size() + " ghế!", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, TicketListActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private void updateSelectedSeatsText() {
        if (selectedSeats.isEmpty()) {
            tvSelectedSeat.setText("Chưa chọn ghế");
        } else {
            // Using StringBuilder to avoid String.join issues if any (though minSDK 24 is fine)
            StringBuilder sb = new StringBuilder("Ghế đã chọn: ");
            int i = 0;
            for (String s : selectedSeats) {
                sb.append(s);
                if (++i < selectedSeats.size()) sb.append(", ");
            }
            tvSelectedSeat.setText(sb.toString());
        }
    }
}
