package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.Genre;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Disabled
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
    public void whenRequestFilmListPageThenGetCollectionFilmDto() {

        var genre = new Genre("test2");
        genre.setId(2);
        when(genreService.findById(genre.getId()).get()).thenReturn(genre);
        var film1 = new Film("film1", "description1", 2024, 2, 18, 120, 1);
        film1.setId(1);
        var film2 = new Film("film2", "description2", 2009, 2, 18, 140, 1);
        film2.setId(2);
        var filmDto1 = new FilmDto(1, "film1", "description1", 2024, "test2",
                18, 120, 1);
        var filmDto2 = new FilmDto(2, "film2", "description2", 2009, "test2",
                18, 140, 1);
        Collection<Film> expectedFilms = List.of(film1, film2);
        var expectedDtoFilms = List.of(filmDto1, filmDto2);
        when(filmRepository.findAll()).thenReturn(expectedFilms);
        when(filmService.findAll()).thenReturn(expectedDtoFilms);

        Collection<FilmDto> listDto = filmService.findAll();
        assertThat(listDto.size()).isEqualTo(2);
        assertThat(expectedDtoFilms).usingRecursiveComparison().isEqualTo(listDto);
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