package dev.akila.ticketing_system.service;

import com.google.gson.Gson;
import dev.akila.ticketing_system.model.Configuration;

import lombok.Getter;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

@Service
public class ConfigurationService {
    static Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    @Getter
    private Configuration configuration;

    private final String filePath = "src/main/resources/config.json";


    public ConfigurationService(Configuration configuration) {
        this.configuration = configuration;
        logger.info(configuration.toString());
    }

    public ConfigurationService() {
        this.configuration = new Configuration(500, 1000,
                1000, 1000);
        logger.info(configuration.toString());
    }

    //setting the configuration
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        logger.info("Configuration: "+configuration.toString());
        saveSystemConfig(configuration);
    }

    public void setConfiguration(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        configuration = new Configuration(totalTickets, maxTicketCapacity, ticketReleaseRate, customerRetrievalRate);
        logger.info("Configuration: "+configuration.toString());
        saveSystemConfig(configuration);

    }

    public void setConfiguration(){
        configuration = loadConfigurationFromFile();
    }


    public void saveSystemConfig(Configuration config) {
        /*
        This method saves the current configuration of the ticketing system to a persistent storage, such as a file.
        It writes the configuration parameters to a file, ensuring that the system's state is preserved for future
        use. This method is essential for maintaining data persistence.
        */

        Gson gson = new Gson();
        String json = gson.toJson(config);

        try (FileWriter writer = new FileWriter(filePath)) {
            writer.write(json);
            logger.info("Configuration saved to {}", filePath);
        } catch (IOException e) {
            logger.error("Error saving configuration", e);
        }
    }

    public Configuration loadConfigurationFromFile() {

        /*
        This method loads the configuration of the ticketing system from a persistent storage, such as a file,
        and returns it as a Configuration object. It retrieves the saved configuration parameters, restoring the
        system's state to a previous setup. This method is crucial for resuming operations with saved settings,
        ensuring continuity and persistence in system configuration.
        */

        Gson gson = new Gson();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.json")) {
            if (inputStream == null) {
                logger.error("Configuration file not found.");
                return null;
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            configuration = gson.fromJson(reader, Configuration.class);
            logger.info("Configuration loaded from file: {}", configuration.toString());
        } catch (IOException e) {
            logger.error("Error loading configuration from file", e);
        }
        return configuration;
    }


}
