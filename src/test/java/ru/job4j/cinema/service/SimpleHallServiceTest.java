package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleHallServiceTest {

    private HallRepository hallRepository;
    private HallService hallService;

    @BeforeEach
    public void initServices() {
        hallRepository = mock(HallRepository.class);
        hallService = new SimpleHallService(hallRepository);
    }

    @Test
    public void whenFindHallThenGetHall() {
        var hall = new Hall("test", 15, 12, "test");
        when(hallRepository.findById(hall.getId())).thenReturn(Optional.of(hall));
        var foundHall = hallService.findById(hall.getId()).get();
        assertThat(foundHall.getName()).isEqualTo(hall.getName());
    }
}