package nintendods.ei.fti.uantwerpen.bankclientapplication.ClientSide;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import java.util.Scanner;

@SpringBootApplication
public class BankClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankClientApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    @Bean
    public Scanner scanner() {
        return new Scanner(System.in);
    }

}
