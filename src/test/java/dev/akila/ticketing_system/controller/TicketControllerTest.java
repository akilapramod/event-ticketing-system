package dev.akila.ticketing_system.controller;

import dev.akila.ticketing_system.service.TicketPoolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class TicketControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TicketPoolService ticketPoolService;
    
    @InjectMocks
    @Autowired
    private TicketController ticketController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(ticketPoolService.getAvailableTickets()).thenReturn(50);
    }

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
