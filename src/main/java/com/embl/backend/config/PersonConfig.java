package com.embl.backend.config;

import com.embl.backend.security.JwtAuthenticationFilter;
import com.embl.backend.security.JwtTokenUtil;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersonConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JwtAuthenticationFilter authFilter(final JwtTokenUtil jwtTokenUtil) {
        return new JwtAuthenticationFilter(jwtTokenUtil);
    }

}
