package com.codemobiles.stock_backend.controller.api;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import com.codemobiles.stock_backend.util.DateUtils;

//@RestController
public class DemoController {

	DateUtils dateUtis;
	private SayService sayService;

	DemoController(DateUtils dateUtis, @Qualifier("cat_rock") SayService sayService) {
		this.dateUtis = dateUtis;
		this.sayService = sayService;
	}

	@GetMapping("/")
	String getToday() {
		return dateUtis.todayString();
	}

	@GetMapping("/say")
	String say() {
		return sayService.say();
	}

}

interface SayService {
	String say();
}

@Component("cat_rock")
class Cat implements SayService {
	@Override
	public String say() {
		return "meowwww";
	}
}

@Component
class Dog implements SayService {
	@Override
	public String say() {
		return "booooowwwwwww";
	}
}
