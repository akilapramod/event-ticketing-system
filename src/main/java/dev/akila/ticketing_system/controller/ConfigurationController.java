package dev.akila.ticketing_system.controller;

import dev.akila.ticketing_system.model.Configuration;
import dev.akila.ticketing_system.service.ConfigurationService;
import dev.akila.ticketing_system.service.TicketPoolService;
import dev.akila.ticketing_system.service.CustomerService;
import dev.akila.ticketing_system.service.VendorService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final TicketPoolService ticketPoolService;
    private final CustomerService customerService;
    private final VendorService vendorService;

    public ConfigurationController(ConfigurationService configurationService, TicketPoolService ticketPoolService, CustomerService customerService, VendorService vendorService) {
        this.configurationService = configurationService;
        this.ticketPoolService = ticketPoolService;
        this.customerService = customerService;
        this.vendorService = vendorService;
    }

    @PostMapping("/set")
    public void setConfigurationService(@RequestBody Configuration configuration) {
        configurationService.setConfiguration(configuration);
        ticketPoolService.setTicketPool(configuration);
    }

    @PostMapping("/get")
    public Configuration getConfiguration() {
        return configurationService.getConfiguration();
    }

    @PostMapping("/start")
    public void startSystem() {
        vendorService.startThreads();
        customerService.startThreads();
    }

    @PostMapping("stop")
    public void stopSystem() {
        vendorService.stopThreads();
        customerService.stopThreads();

    }

    @PostMapping("load")
    public void loadSystem() {
        Configuration config = configurationService.loadConfigurationFromFile();
        /*after api get called for loading the configuration, the configuration should be displayed in the frontend
        and the user should be able to confirm the configuration or edit and set the configuration.
        */
        //setConfigurationService(config);

    }

    @PostMapping("/test")
    public void test() {
        System.out.println("HTTP Test");
    }
}
