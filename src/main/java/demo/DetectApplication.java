package demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import demo.vsm.TableNumber;

@EnableScheduling
@SpringBootApplication
public class DetectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectApplication.class, args);

	}

}
