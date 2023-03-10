package in.net.sampleapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

@ComponentScan(basePackages = "net.uidai.core")
@ComponentScan(basePackages = "in.net.sampleapp")
@SpringBootApplication
@Order(Ordered.LOWEST_PRECEDENCE)
public class SampleappApplication {

	public static void main(String[] args) {
		SpringApplication.run(SampleappApplication.class, args);
	}

}
