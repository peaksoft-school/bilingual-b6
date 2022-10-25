package kg.peaksoft.bilingualb6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@SpringBootApplication
public class BilingualB6Application {

	public static void main(String[] args) {
		SpringApplication.run(BilingualB6Application.class, args);
		System.out.println("Welcome colleagues, project name is Bilingual!");
	}

	@GetMapping("/")
	public String greetingPage(){
		return "welcome";
	}
}
