package springBootCurso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "springBootCurso")
@EntityScan
public class SpringBootCursoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCursoApplication.class, args);
	}

}
