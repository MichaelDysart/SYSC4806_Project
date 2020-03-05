package webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/*
 * The main entry point for the system
 */
@SpringBootApplication
@EntityScan(basePackages = {"survey"})
public class ServingWebContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(webApp.ServingWebContentApplication.class, args);
    }
}