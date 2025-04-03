package dev.akila.ticketing_system.model;//TicketPool class using a thread-safe data structure


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class TicketPool {
    private static final Logger logger = LoggerFactory.getLogger(TicketPool.class);
    private List<Ticket> ticketsList;
    private int maximumTicketCapacity;
    public TicketPool(int availableTicketCount, int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.ticketsList = Collections.synchronizedList(new LinkedList<>());
        for (int i = 0; i < availableTicketCount; i++) {
            Ticket ticket = new Ticket("Event " + (i + 1), new BigDecimal(10.0));
            ticketsList.add(ticket);
        }
    }



    //get available tickets
    public int getAvailableTicketCount() {
        return ticketsList.size();
    }

    public synchronized void addTickets(Ticket ticket) {

        /*
        This method adds a ticket to the ticket pool, ensuring thread safety through synchronization.
        It handles the addition of tickets in a concurrent environment, preventing data inconsistencies by
        allowing only one thread to modify the pool at a time. This method is crucial for maintaining the
        integrity of the ticket pool in a multithreaded scenario.
        */

        while (ticketsList.size() >= maximumTicketCapacity) {
            try {
                wait();
                logger.info("Ticket Pool is full.");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted Exception");
                return;
            }
        }
        ticketsList.add(ticket);
        notifyAll();
    }

    public synchronized Ticket removeTickets() {

        /*
        This method removes a ticket from the ticket pool, ensuring thread safety through synchronization.
        It handles the removal of tickets in a concurrent environment, preventing data inconsistencies by
        allowing only one thread to modify the pool at a time. This method is essential for maintaining the
        integrity of the ticket pool in a multithreaded scenario.
        */

        while (ticketsList.isEmpty()) {
            try {
                logger.info("Ticket Pool is empty.");
                wait(); //wait if the ticket pool is empty
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Interrupted Exception");
                return null;
            }
        }
        Ticket ticket = ticketsList.remove(0);
        notifyAll();
        return ticket;
    }
}
