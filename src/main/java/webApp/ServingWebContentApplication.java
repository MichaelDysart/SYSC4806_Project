package webApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan(basePackages = {"survey"})
public class ServingWebContentApplication {
    public static void main(String[] args) {
        SpringApplication.run(webApp.ServingWebContentApplication.class, args);
    }
}