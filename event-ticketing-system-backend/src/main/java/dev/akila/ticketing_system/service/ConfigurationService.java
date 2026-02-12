package dev.akila.ticketing_system.service;

import com.google.gson.Gson;
import dev.akila.ticketing_system.model.Configuration;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


@Service
public class ConfigurationService {
    static Logger logger = LoggerFactory.getLogger(ConfigurationService.class);
    private Configuration configuration;

    private final String filePath = "src/main/resources/config.json";


    public ConfigurationService(Configuration configuration) {
        this.configuration = configuration;
        logger.info(configuration.toString());
    }

    public ConfigurationService() {
        configuration = loadConfigurationFromFile();
        logger.info(configuration.toString());
    }

    //setting the configuration
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
        logger.info(configuration.toString());
        saveSystemConfig(configuration);
    }

    public void setConfiguration(int totalTickets, int maxTicketCapacity, int ticketReleaseRate, int customerRetrievalRate) {
        configuration = new Configuration(totalTickets, maxTicketCapacity, ticketReleaseRate, customerRetrievalRate);
        saveSystemConfig(configuration);

    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public String getFilePath() {
        return filePath;
    }

    public void saveSystemConfig(Configuration config) {

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

        Gson gson = new Gson();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.json")) {
            if (inputStream == null) {
                logger.error("Configuration file not found in classpath");
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
