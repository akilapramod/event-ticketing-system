package dev.akila.ticketing_system.controller;

import dev.akila.ticketing_system.service.TicketPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketPoolService ticketPoolService;

    @GetMapping("/available")
    public int getAvailableTickets() {
        return ticketPoolService.getAvailableTickets();
    }
}