package dev.akila.ticketing_system.service;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.TicketPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TicketPoolServiceTest {
    private TicketPoolService ticketPoolService;
    private Configuration config;

    @BeforeEach
    void setUp() {
        ticketPoolService = new TicketPoolService();
        config = new Configuration(100, 50, 10, 5);
        ticketPoolService.setTicketPool(config);
    }

    @Test
    void testGetAvailableTickets() {
        // The service returns totalTickets as available tickets initially
        assertEquals(config.getTotalTickets(), ticketPoolService.getAvailableTickets());
    }

    @Test
    void testSetTicketPoolWithConfiguration() {
        Configuration newConfig = new Configuration(200, 100, 20, 10);
        ticketPoolService.setTicketPool(newConfig);
        assertEquals(newConfig.getTotalTickets(), ticketPoolService.getAvailableTickets());
    }

    @Test
    void testSetTicketPoolWithParams() {
        ticketPoolService.setTicketPool(200, 100);
        // First param is totalTickets, which becomes availableTickets
        assertEquals(200, ticketPoolService.getAvailableTickets());
    }
}
