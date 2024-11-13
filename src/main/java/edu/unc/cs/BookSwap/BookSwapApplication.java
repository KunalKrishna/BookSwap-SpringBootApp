package edu.unc.cs.BookSwap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
//@RestController
public class BookSwapApplication {

//	@GetMapping("/")
//	public String welcome(){
//		return "hello!";
//	}

	public static void main(String[] args) {
		SpringApplication.run(BookSwapApplication.class, args);
	}

}


