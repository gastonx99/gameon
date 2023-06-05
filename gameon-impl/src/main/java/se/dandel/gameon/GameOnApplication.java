package se.dandel.gameon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GameOnApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameOnApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GameOnApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        LOGGER.info("Starting GameOn");
        return null;
    }
}
