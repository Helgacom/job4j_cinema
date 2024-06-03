package ru.job4j.cinema.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.repository.TicketRepository;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SimpleTicketServiceTest {

    private TicketRepository ticketRepository;
    private TicketService ticketService;

    @BeforeEach
    public void initServices() {
        ticketRepository = mock(TicketRepository.class);
        ticketService = new SimpleTicketService(ticketRepository);
    }

    @Test
    public void whenSaveTicketThenGetTicket() {
        var ticket = new Ticket(1, 1, 3, 3, 1);
        when(ticketRepository.save(ticket)).thenReturn(Optional.of(ticket));
        var savedTicket = ticketService.save(ticket);
        assertThat(savedTicket.get()).isEqualTo(ticket);
    }
}