package br.com.nutriconecta.nutriconecta;

import br.com.nutriconecta.nutriconecta.service.NutriService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class NutriconectaApplication {
    public static void main(String[] args) {
        SpringApplication.run(NutriconectaApplication.class, args);
    }

    @Bean
    CommandLineRunner seed(NutriService service) {
        return args -> service.criarDadosIniciais();
    }
}
