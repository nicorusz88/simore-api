package ar.com.simore.simoreapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("ar.com.simore.simoreapi.repositories")
public class JPAConfig {

}
