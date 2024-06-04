package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSession {

    public static final Map<String, String> COLUMN_MAPPING = Map.of(
            "id", "id",
            "film_id", "filmId",
            "halls_id", "hallsId",
            "start_time", "startTime",
            "end_time", "endTime",
            "price", "price"
    );

    private int id;

    private int filmId;

    private int hallsId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private int price;

    public FilmSession(int filmId, int hallsId, LocalDateTime startTime, LocalDateTime endTime, int price) {
        this.filmId = filmId;
        this.hallsId = hallsId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFilmId() {
        return filmId;
    }

    public int getHallsId() {
        return hallsId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
