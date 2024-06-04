package ru.job4j.cinema.service;

import org.junit.jupiter.api.*;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.*;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleFilmServiceTest {

    private FilmRepository filmRepository;
    private GenreService genreService;
    private FilmService filmService;

    @BeforeEach
    public void initServices() {
        filmRepository = mock(FilmRepository.class);
        genreService = mock(GenreService.class);
        filmService = new SimpleFilmService(filmRepository, genreService);
    }

    @Test
    public void whenFindByIdFilmThenGetEmptyFilmDto() {
        var film1 = new Film("test1", "desc1",
                2022, 3, 18, 120, 1);
        when(filmRepository.findById(film1.getId())).thenReturn(Optional.empty());
        var optionalFilmDto = filmService.findById(film1.getId());
        assertThat(optionalFilmDto).isEmpty();
    }
}