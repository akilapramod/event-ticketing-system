/*
    Real-Time
    Event Ticketing System, focusing on the use of Object-
    Oriented Programming (OOP) principles and the Producer-Consumer
    pattern to simulate a dynamic ticketing environment. The system will
    handle concurrent ticket releases and purchases while maintaining data
    integrity.
 */


package dev.akila.ticketing_system;

import dev.akila.ticketing_system.cli.TicketingSystemCLI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

import java.util.logging.Logger;

@SpringBootApplication
public class TicketingSystemApplication {


    @Autowired
    private TicketingSystemCLI ticketingSystemCLI;

    @Bean
    @ConditionalOnMissingBean(name = "disableCLIForTests")
    public CommandLineRunner runCLI() {
         /*
        This method provides a command-line runner bean to initiate the CLI configuration menu.
        It serves as the entry point for the ticketing system, allowing users to interact with the system.
         */
        return args -> {
            ticketingSystemCLI.displayConfigurationMenu();
        };
    }
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(TicketingSystemApplication.class.getName());

        SpringApplication.run(TicketingSystemApplication.class, args);
        logger.info("Ticketing System Application Started");
    }


}
