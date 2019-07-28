package com.tan.ec;

import com.tan.ec.bean.config.FilterIgnorePropertiesConfig;
import com.tan.ec.bean.filter.LoginFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@ServletComponentScan
@EnableScheduling
@EnableCaching
public class EcApplication {

	@Autowired
	private FilterIgnorePropertiesConfig filterIgnorePropertiesConfig;

	public static void main(String[] args) {
		SpringApplication.run(EcApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean filterRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean(new LoginFilter());
		filterIgnorePropertiesConfig.getUrls().forEach(url -> registration.addInitParameter(url, url));
		registration.setName("loginFilter");
		registration.setOrder(-100);
		return registration;
	}
}
