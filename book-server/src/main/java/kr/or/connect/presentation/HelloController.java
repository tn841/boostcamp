package kr.or.connect.presentation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.or.connect.service.BookService;

@RestController
public class HelloController {
	@GetMapping("/hello")
	String hello(){
		return "Hello World";
	}
}
