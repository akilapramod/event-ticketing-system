package dev.akila.ticketing_system.threads;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.Ticket;
import dev.akila.ticketing_system.model.TicketPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

public class Vendor implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Vendor.class);

    private Configuration configuration;
    private TicketPool ticketPool;
    private volatile boolean running = true;

    public Vendor(Configuration configuration, TicketPool ticketPool) {
        this.configuration = configuration;
        this.ticketPool = ticketPool;
    }

    @Override
    public void run() {

        while (running) {
            try {

                Ticket newTicket = new Ticket("Event ", new BigDecimal(10.0));
                ticketPool.addTickets(newTicket);
                //TicketingSystemApplication.logMessage("A vendor has added a ticket. Total tickets: " + ticketPool.getAvailableTicketCount());
                logger.info("Vendor added ticket: ID {}. Total tickets: {}", newTicket.getTicketId(), ticketPool.getAvailableTicketCount());
                //System.out.println(ticketPool.getTicketsList());
                Thread.sleep(configuration.getTicketReleaseRate());
            } catch (InterruptedException e) {
                logger.error("Thread interrupted", e);
                Thread.currentThread().interrupt(); // Preserve interrupt status
                running = false; // Gracefully stop the thread
            }
        }
    }

    public void stop() {
        running = false;
    }
}
