package ar.com.simore.simoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@PropertySource({"file:${user.home}/opt/simore/application.properties", "file:${user.home}/opt/simore/data.properties"})
public class SimoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimoreApiApplication.class, args);
	}
}
