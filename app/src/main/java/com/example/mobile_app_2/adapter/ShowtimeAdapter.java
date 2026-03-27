package com.example.mobile_app_2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab_1.R;
import com.example.lab_1.data.AppDatabase;
import com.example.lab_1.data.Movie;
import com.example.lab_1.data.Showtime;
import com.example.lab_1.data.Theater;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private List<Showtime> showtimes;
    private OnBookClickListener listener;
    private AppDatabase db;
    private boolean isMovieView; // To distinguish between Movie-based and Theater-based views

    public interface OnBookClickListener {
        void onBookClick(Showtime showtime);
    }

    public ShowtimeAdapter(List<Showtime> showtimes, AppDatabase db, boolean isMovieView, OnBookClickListener listener) {
        this.showtimes = showtimes;
        this.db = db;
        this.isMovieView = isMovieView;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimes.get(position);

        if (isMovieView) {
            Theater theater = db.appDao().getTheaterById(showtime.theaterId);
            holder.tvTitle.setText(theater != null ? theater.name : "Unknown Theater");
        } else {
            Movie movie = db.appDao().getMovieById(showtime.movieId);
            holder.tvTitle.setText(movie != null ? movie.title : "Unknown Movie");
        }

        holder.tvDetails.setText("Ngày: " + showtime.date + " | Giờ: " + showtime.startTime);
        holder.btnBook.setOnClickListener(v -> listener.onBookClick(showtime));
    }

    @Override
    public int getItemCount() {
        return showtimes.size();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvDetails;
        Button btnBook;

        public ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTheaterNameInShowtime);
            tvDetails = itemView.findViewById(R.id.tvShowtimeDetails);
            btnBook = itemView.findViewById(R.id.btnBook);
        }
    }
}
