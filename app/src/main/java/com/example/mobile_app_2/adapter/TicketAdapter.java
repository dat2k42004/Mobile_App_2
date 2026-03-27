package com.example.mobile_app_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mobile_app_2.R;
import com.example.mobile_app_2.data.AppDatabase;
import com.example.mobile_app_2.data.Movie;
import com.example.mobile_app_2.data.Showtime;
import com.example.mobile_app_2.data.Theater;
import com.example.mobile_app_2.data.Ticket;

import java.util.List;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private List<Ticket> tickets;
    private AppDatabase db;
    private OnTicketDeleteListener deleteListener;

    public interface OnTicketDeleteListener {
        void onDelete(Ticket ticket);
    }

    public TicketAdapter(List<Ticket> tickets, AppDatabase db, OnTicketDeleteListener deleteListener) {
        this.tickets = tickets;
        this.db = db;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = tickets.get(position);
        Showtime showtime = db.appDao().getShowtimeById(ticket.showtimeId);
        
        if (showtime != null) {
            Movie movie = db.appDao().getMovieById(showtime.movieId);
            Theater theater = db.appDao().getTheaterById(showtime.theaterId);
            
            holder.tvMovie.setText(movie != null ? movie.title : "Unknown Movie");
            holder.tvTheater.setText(theater != null ? theater.name : "Unknown Theater");
            holder.tvTime.setText(showtime.date + " | " + showtime.startTime);
        }
        
        holder.tvSeat.setText("Ghế: " + ticket.seatNumber);
        holder.btnDelete.setOnClickListener(v -> deleteListener.onDelete(ticket));
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovie, tvTheater, tvTime, tvSeat;
        ImageButton btnDelete;

        public TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovie = itemView.findViewById(R.id.tvTicketMovie);
            tvTheater = itemView.findViewById(R.id.tvTicketTheater);
            tvTime = itemView.findViewById(R.id.tvTicketTime);
            tvSeat = itemView.findViewById(R.id.tvTicketSeat);
            btnDelete = itemView.findViewById(R.id.btnDeleteTicket);
        }
    }
}
