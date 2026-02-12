package dev.akila.ticketing_system.service;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.model.TicketPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TicketPoolService {
    private static final Logger logger = LoggerFactory.getLogger(TicketPoolService.class);

    private TicketPool ticketPool;

    @Autowired
    private ConfigurationService configurationService;

    /**
     * Initializes the TicketPool on application startup.
     * If a configuration file exists, it will automatically create the pool.
     * This prevents NullPointerException after application restarts.
     */
    @PostConstruct
    public void init() {
        Configuration config = configurationService.getConfiguration();
        if (config != null && config.getMaxTicketCapacity() > 0) {
            ticketPool = new TicketPool(
                    config.getTotalTickets(),
                    config.getMaxTicketCapacity());
            logger.info("TicketPool initialized on startup with {} tickets (max: {})",
                    config.getTotalTickets(), config.getMaxTicketCapacity());
        } else {
            logger.info("No valid configuration found. TicketPool will be initialized when configuration is set.");
        }
    }

    public void setTicketPool(Configuration configuration) {
        ticketPool = new TicketPool(configuration.getTotalTickets(), configuration.getMaxTicketCapacity());
    }

    public void setTicketPool(int totalTickets, int maxTicketCapacity) {
        ticketPool = new TicketPool(totalTickets, maxTicketCapacity);
    }

    public TicketPool getTicketPool() {
        return ticketPool;
    }

    public int getAvailableTickets() {
        if (ticketPool == null) {
            logger.warn("TicketPool is not initialized. Returning 0.");
            return 0;
        }
        return ticketPool.getAvailableTicketCount();
    }

}
