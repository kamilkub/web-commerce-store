package org.store.ecommercestore;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;



@EnableAsync
@SpringBootApplication
public class EcommerceStoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceStoreApplication.class, args);
	}

}
