package com.cm.personalProject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class PersonalProjConfig {
   
   @Bean
   public PasswordEncoder getPasswordEncord() {
      return new BCryptPasswordEncoder();
   }   

} //class