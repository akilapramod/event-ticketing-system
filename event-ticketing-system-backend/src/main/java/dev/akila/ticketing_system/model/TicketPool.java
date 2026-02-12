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
    private int availableTicketCount;

    public TicketPool(int availableTicketCount, int maximumTicketCapacity) {
        this.maximumTicketCapacity = maximumTicketCapacity;
        this.availableTicketCount = availableTicketCount;
        this.ticketsList = Collections.synchronizedList(new LinkedList<>());
        for (int i = 0; i < availableTicketCount; i++) {
            Ticket ticket = new Ticket("Event " + (i + 1), new BigDecimal(10.0));
            ticketsList.add(ticket);
        }
    }

    //Getters for the TicketPool


    public List<Ticket> getTicketsList() {
        return ticketsList;
    }


    //get available tickets
    public int getAvailableTicketCount() {
        return ticketsList.size();
    }

    public synchronized void addTickets(Ticket ticket) {
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

