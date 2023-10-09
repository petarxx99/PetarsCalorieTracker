package com.PetarsCalorieTracker.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeFormatConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry){
        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setUseIsoFormat(true);
        registrar.registerFormatters(registry);
    }

    @Bean
    DateTimeFormatter dateTimeFormatter(){
        return DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss");
    }

}
