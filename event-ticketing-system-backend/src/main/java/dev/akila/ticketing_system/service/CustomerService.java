package dev.akila.ticketing_system.service;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.TicketPool;
import dev.akila.ticketing_system.threads.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    static Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketPoolService ticketPoolService;

    int numberCustomers = 4;

    Customer[] customers = new Customer[numberCustomers];

    public void startThreads() {
        Configuration configuration = configurationService.getConfiguration();
        TicketPool ticketPool = ticketPoolService.getTicketPool();

        Thread[] customerThreads = new Thread[numberCustomers];

        for (int i = 0; i < numberCustomers; i++) {
            customers[i] = new Customer(configuration, ticketPool);
            customerThreads[i] = new Thread(customers[i]);
            customerThreads[i].start();
        }

        logger.info("Customer threads started: {}", numberCustomers);
    }

    public void stopThreads(){
        if (customers != null) {
            for (Customer customer : customers) {
                if (customer != null) {
                    customer.stop();
                }

            }

        }
        logger.info("Customer threads stopped.");
    }
}
