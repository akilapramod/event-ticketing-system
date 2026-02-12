package dev.akila.ticketing_system.threads;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.Ticket;
import dev.akila.ticketing_system.model.TicketPool;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Customer implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    //    String customerID;
    //    String customerName;
    private Configuration configuration;
    private TicketPool ticketPool;
    private volatile boolean running = true;


    public Customer(Configuration configuration, TicketPool ticketPool) {
        //this.customerID = customerID;
        //this.customerName = customerName;
        this.configuration = configuration;
        this.ticketPool = ticketPool;
    }

    public void run() {
        while (running) {
            try {
                Ticket ticket = ticketPool.removeTickets();
                logger.info("Customer retrieved ticket: ID {}. Total tickets: {}", ticket.getTicketId(),
                        ticketPool.getAvailableTicketCount());
                Thread.sleep(configuration.getCustomerRetrievalRate());
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
