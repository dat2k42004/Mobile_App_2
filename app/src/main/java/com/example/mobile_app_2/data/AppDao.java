package com.example.mobile_app_2.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDao {
    // Users
    @Insert
    long insertUser(User user);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1")
    User login(String username, String password);

    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(int userId);

    // Movies
    @Insert
    void insertMovie(Movie movie);

    @Query("SELECT * FROM movies")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM movies WHERE id = :movieId")
    Movie getMovieById(int movieId);

    // Theaters
    @Insert
    void insertTheater(Theater theater);

    @Query("SELECT * FROM theaters")
    List<Theater> getAllTheaters();

    @Query("SELECT * FROM theaters WHERE id = :theaterId")
    Theater getTheaterById(int theaterId);

    // Showtimes
    @Insert
    void insertShowtime(Showtime showtime);

    @Query("SELECT * FROM showtimes WHERE movieId = :movieId")
    List<Showtime> getShowtimesByMovie(int movieId);

    @Query("SELECT * FROM showtimes WHERE theaterId = :theaterId")
    List<Showtime> getShowtimesByTheater(int theaterId);

    @Query("SELECT * FROM showtimes WHERE id = :showtimeId")
    Showtime getShowtimeById(int showtimeId);

    // Tickets
    @Insert
    void insertTicket(Ticket ticket);

    @Query("SELECT * FROM tickets WHERE userId = :userId")
    List<Ticket> getTicketsByUser(int userId);

    @Query("SELECT seatNumber FROM tickets WHERE showtimeId = :showtimeId")
    List<String> getBookedSeats(int showtimeId);

    @Query("SELECT * FROM tickets WHERE showtimeId = :showtimeId AND seatNumber = :seatNumber LIMIT 1")
    Ticket getTicketByShowtimeAndSeat(int showtimeId, String seatNumber);

    @Delete
    void deleteTicket(Ticket ticket);

    @Query("DELETE FROM tickets")
    void deleteAllTickets();
}
