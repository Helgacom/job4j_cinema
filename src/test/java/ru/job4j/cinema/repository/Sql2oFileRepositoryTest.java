package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterAll;
import org.sql2o.Sql2o;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.File;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFileRepositoryTest {

    private static Sql2o sql2o;

    static Sql2oFileRepository sql2oFileRepository;

    private static File file;

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

        sql2oFileRepository = new Sql2oFileRepository(sql2o);

        file = new File("test2", "test2");
        file.setId(2);
        sql2oFileRepository.save(file);
    }

    @AfterAll
    public static void deleteFile() {
        sql2oFileRepository.deleteById(file.getId());
    }

    @Test
    public void whenSaveThenGetSame() {
        var sameFile = sql2oFileRepository.findById(file.getId());
        assertThat(sameFile).isEqualTo(Optional.of(file));
    }

    @Test
    public void whenInvalidDeleteThenGetFalse() {
        var deleted = sql2oFileRepository.deleteById(15);
        assertThat(deleted).isFalse();
    }

    @Test
    public void whenFindByIdThenGetFile() {
        var expectedFile = sql2oFileRepository.findById(file.getId()).get();
        assertThat(file).isEqualTo(expectedFile);
    }
}
