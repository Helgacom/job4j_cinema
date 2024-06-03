package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Genre;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oGenreRepositoryTest {

    private static Sql2o sql2o;

    static Sql2oGenreRepository sql2oGenreRepository;

    private static Genre genre;

    @BeforeAll
    public static void initRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepository.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oGenreRepository = new Sql2oGenreRepository(sql2o);

        genre = new Genre("test2");
        sql2oGenreRepository.save(genre);
    }

    @AfterAll
    public static void deleteGenre() {
        sql2oGenreRepository.deleteById(genre.getId());
    }

    @Test
    public void whenSaveThenGetSame() {
        var sameGenre = sql2oGenreRepository.findById(genre.getId()).get();
        assertThat(genre).isEqualTo(sameGenre);
    }

    @Test
    public void whenInvalidDeleteThenGetFalse() {
        var deleted = sql2oGenreRepository.deleteById(5);
        assertThat(deleted).isFalse();
    }

    @Test
    public void whenFindAllThenGetList() {
        var list = sql2oGenreRepository.findAll();
        assertThat(list.size()).isEqualTo(2);
    }

    @Test
    public void whenIFindByIdThenGetGenre() {
        var expectedGenre = sql2oGenreRepository.findById(genre.getId());
        assertThat(expectedGenre).isEqualTo(Optional.of(genre));
    }
}