package dev.akila.ticketing_system.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * CORS configuration to allow frontend to communicate with backend
 * when deployed on different domains (e.g., Azure Static Web Apps + Azure App
 * Service).
 * 
 * In development: Allows localhost:5173 (Vite default port)
 * In production: Can be configured via environment variable for security
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                // Allow both local development and Azure deployment
                .allowedOrigins(
                        "http://localhost:5173", // Vite dev server
                        "http://localhost:3000", // Alternative React port
                        "https://*.azurewebsites.net", // Azure Static Web Apps
                        "https://*.azurestaticapps.net" // Azure Static Web Apps alternative
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight response for 1 hour
    }
}
