package ru.job4j.cinema.repository;

import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.sql2o.Sql2o;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oHallRepositoryTest {

    private static Sql2o sql2o;

    static Sql2oHallRepository sql2oHallRepository;

    private static Hall hall;

    @BeforeAll
    public static void intiRepositories() throws IOException {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepository.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
        hall = new Hall("test", 20, 20, "test");
    }

    @Test
    public void whenFindById() {
        assertThat(sql2oHallRepository.findById(1).get().getName()).isEqualTo(hall.getName());
    }

    @Test
    public void whenDidNotFindById() {
        Optional<Hall> actualHall = sql2oHallRepository.findById(2);
        assertThat(actualHall).isEmpty();
    }

    @Test
    public void whenFindByName() {
        assertThat(sql2oHallRepository.findByName("test").get().getName()).isEqualTo(hall.getName());
    }

    @Test
    public void whenFindAll() {
        assertThat(sql2oHallRepository.findAll()).isNotEmpty();
        assertThat(sql2oHallRepository.findAll().size()).isEqualTo(1);
    }
}