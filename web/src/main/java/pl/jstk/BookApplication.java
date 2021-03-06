package pl.jstk;

import nz.net.ultraq.thymeleaf.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import pl.jstk.generator.PasswordGenerator;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"pl"})
@EntityScan(basePackages = {"pl"})
@EnableGlobalMethodSecurity(securedEnabled = true)
@ComponentScan(basePackages = {"pl"})
public class BookApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        PasswordGenerator generator = new PasswordGenerator();
        generator.generate();
        SpringApplication.run(BookApplication.class, args);

    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }
}
