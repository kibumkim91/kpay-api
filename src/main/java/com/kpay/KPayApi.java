package com.kpay;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = { 
		"com.kpay.common.mapper"
		, "com.kpay.moneyspraying.mapper" }
)
public class KPayApi {

	public static void main(String[] args) {
		SpringApplication.run(KPayApi.class, args);
	}

}