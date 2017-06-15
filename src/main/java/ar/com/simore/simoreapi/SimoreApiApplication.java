package ar.com.simore.simoreapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource({"file:/opt/simore/application.properties", "file:/opt/simore/data.properties"})
public class SimoreApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimoreApiApplication.class, args);
	}
}
