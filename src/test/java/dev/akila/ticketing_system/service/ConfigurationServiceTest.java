package dev.akila.ticketing_system.service;

import dev.akila.ticketing_system.model.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationServiceTest {
    private ConfigurationService configurationService;
    private Configuration testConfig;
    
    @BeforeEach
    void setUp() {
        configurationService = new ConfigurationService();
        testConfig = new Configuration(100, 50, 10, 5);
    }

    @Test
    void testSetAndGetConfiguration() {
        configurationService.setConfiguration(testConfig);
        Configuration retrieved = configurationService.getConfiguration();
        assertEquals(testConfig.getMaxTicketCapacity(), retrieved.getMaxTicketCapacity());
        assertEquals(testConfig.getTotalTickets(), retrieved.getTotalTickets());
    }

    @Test
    void testLoadAndSaveConfiguration(@TempDir Path tempDir) throws Exception {
        // Set a test file path in temp directory
        File testFile = tempDir.resolve("test-config.json").toFile();
        
        // Create new service with test file path
        ConfigurationService testService = new ConfigurationService();
        testService.setConfiguration(testConfig);
        testService.saveSystemConfig(testConfig);
        
        Configuration loadedConfig = testService.loadConfigurationFromFile();
        assertNotNull(loadedConfig);
        assertEquals(testConfig.getMaxTicketCapacity(), loadedConfig.getMaxTicketCapacity());
    }

    @Test
    void testDefaultConfiguration() {
        Configuration defaultConfig = configurationService.getConfiguration();
        assertNotNull(defaultConfig);
        assertEquals(1000, defaultConfig.getMaxTicketCapacity());
    }
}
