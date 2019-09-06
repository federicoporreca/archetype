package it.beta80group.backoffice;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan("it.beta80group")
@EntityScan("it.beta80group")
@EnableJpaRepositories("it.beta80group")
public class Config {

}
