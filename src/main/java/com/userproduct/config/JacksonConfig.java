package com.userproduct.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();

        // Create a SimpleModule to configure the date format
        SimpleModule simpleModule = new SimpleModule();
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");

        // Register custom serializers and deserializers for LocalDate with the ObjectMapper
        objectMapper.registerModule(new SimpleModule()
                .addSerializer(LocalDate.class, new LocalDateSerializer(dateFormat))
                .addDeserializer(LocalDate.class, new LocalDateDeserializer(dateFormat)));

        return objectMapper;
    }
}
