package dev.akila.ticketing_system.controller;

import dev.akila.ticketing_system.service.TicketPoolService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TicketPoolService ticketPoolService;

    @Test
    public void testGetAvailableTickets() throws Exception {
        // Mock the service response
        when(ticketPoolService.getAvailableTickets()).thenReturn(50);

        // Test the endpoint
        mockMvc.perform(get("/api/tickets/available"))
               .andExpect(status().isOk())
               .andExpect(content().string("50"));
    }

    @Test
    public void testGetAvailableTicketsWhenZero() throws Exception {
        // Mock the service response for zero tickets
        when(ticketPoolService.getAvailableTickets()).thenReturn(0);

        // Test the endpoint
        mockMvc.perform(get("/api/tickets/available"))
               .andExpect(status().isOk())
               .andExpect(content().string("0"));
    }
}
