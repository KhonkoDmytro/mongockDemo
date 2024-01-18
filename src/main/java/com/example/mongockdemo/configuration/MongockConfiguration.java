package com.example.mongockdemo.configuration;

import io.mongock.driver.api.driver.ConnectionDriver;
import io.mongock.runner.springboot.MongockSpringboot;
import io.mongock.runner.springboot.base.MongockInitializingBeanRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//public class MongockConfiguration {
//
//  @Bean
//  public MongockInitializingBeanRunner mongockRunner(ConnectionDriver driver, ApplicationContext applicationContext) {
//    return MongockSpringboot.builder()
//            .setDriver(driver)
//            .setSpringContext(applicationContext)
//            .addMigrationScanPackage("com.example.mongockdemo.migrations")
//            .setDefaultAuthor("mongock")
//            .setEnabled(true)
//            .buildInitializingBeanRunner();
//  }
//}
