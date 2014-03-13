package com.koleksinaia.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.koleksinaia.rest.controller", "com.koleksinaia.core.service", "com.koleksinaia.dao", "com.koleksinaia.core.entity"})
public class MVCConfig {}
