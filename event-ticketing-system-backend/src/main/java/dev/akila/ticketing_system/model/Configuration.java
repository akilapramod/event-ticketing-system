package dev.akila.ticketing_system.model;/*
 *  This class is responsible for setting the configuration of the ticket magaging system.
 */


public class Configuration {
    private int totalTickets;
    private int maxTicketCapacity;
    private int ticketReleaseRate;
    private int customerRetrievalRate;


    public Configuration(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate){
        this.totalTickets = totalTickets;
        this.maxTicketCapacity = maxTicketCapacity;
        this.ticketReleaseRate = ticketReleaseRate;
        this.customerRetrievalRate = customerRetrievalRate;
    }

    // Setters for the configuration variables
    public void setTotalTickets(int totalTickets){
        this.totalTickets = totalTickets;
    }

    public void setMaxTicketCapacity(int maxTicketCapacity){
        this.maxTicketCapacity = maxTicketCapacity;
    }

    public void setTicketReleaseRate(int ticketReleaseRate){
        this.ticketReleaseRate = ticketReleaseRate;
    }

    public void setCustomerRetrievalRate(int customerRetrievalRate){
        this.customerRetrievalRate = customerRetrievalRate;
    }


   // Getters for the configuration variables

    public int getTotalTickets() {
        return totalTickets;
    }

    public int getMaxTicketCapacity() {
        return maxTicketCapacity;
    }

    public int getTicketReleaseRate(){
        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate(){
        return customerRetrievalRate;
    }


    @Override
    public String toString() {
        return "Configuration{" +
                "totalTickets=" + totalTickets +
                ", maxTicketCapacity=" + maxTicketCapacity +
                ", ticketReleaseRate=" + ticketReleaseRate +
                ", customerRetrievalRate=" + customerRetrievalRate +
                '}';
    }
}
