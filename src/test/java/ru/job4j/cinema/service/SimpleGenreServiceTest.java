package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.repository.GenreRepository;
import ru.job4j.cinema.model.Genre;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleGenreServiceTest {

    private GenreRepository genreRepository;
    private GenreService genreService;

    @BeforeEach
    public void initServices() {
        genreRepository = mock(GenreRepository.class);
        genreService = new SimpleGenreService(genreRepository);
    }

    @Test
    public void whenFindGenreThenGetGenre() {
        var genre = new Genre("test");
        when(genreRepository.findById(genre.getId())).thenReturn(Optional.of(genre));
        var foundGenre = genreService.findById(genre.getId()).get();
        assertThat(foundGenre).isEqualTo(genre);
    }
}