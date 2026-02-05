package dev.akila.ticketing_system.service;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.TicketPool;
import dev.akila.ticketing_system.threads.Vendor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorService {
    static Logger logger = LoggerFactory.getLogger(VendorService.class);

    @Autowired
    private ConfigurationService configurationService;

    @Autowired
    private TicketPoolService ticketPoolService;

    int numberOfVendors = 1;

    Vendor[] vendors = new Vendor[numberOfVendors];

    public void startThreads() {
        Configuration configuration = configurationService.getConfiguration();
        TicketPool ticketPool = ticketPoolService.getTicketPool();

        Thread[] vendorThreads = new Thread[numberOfVendors];

        for (int i = 0; i < numberOfVendors; i++) {
            vendors[i] = new Vendor(configuration, ticketPool);
            vendorThreads[i] = new Thread(vendors[i]);
            vendorThreads[i].start();
        }

        logger.info("Vendor threads started: {}", numberOfVendors);
    }

    public void stopThreads(){
        if (vendors != null) {
            for (Vendor vendor : vendors) {
                if (vendor != null) {
                    vendor.stop();
                }
            }

        }
        logger.info("Vendor threads stopped.");
    }
}
