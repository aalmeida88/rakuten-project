package com.rakuten.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RakutenUIWebServer {

	protected RakutenUIWebServer() {
	}
	
	public static void main(String[] args) {
		SpringApplication.run(RakutenUIWebServer.class, args);
	}
}
