package com.boot.stickershop;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@SpringBootApplication
public class StickershopApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(StickershopApplication.class, args);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if(!registry.hasMappingForPattern("/static/**")){
			registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
		}
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		ObjectMapper mapper = Jackson2ObjectMapperBuilder.json()
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).modules(new JavaTimeModule()).build();

		mapper.registerModule(new Hibernate5Module());
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
		converter.setPrettyPrint(true);

		converters.add(converter);
	}
}
