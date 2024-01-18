package com.example.mongockdemo;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.MongoDBContainer;

public class MongoTestContainer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
  private static final String IMAGE_VERSION = "mongo:4.4.19";
  private static MongoDBContainer container;

  public static MongoDBContainer getInstance() {
    if (container == null) {
      container = new MongoDBContainer(IMAGE_VERSION);
      container.start();
    }
    return container;
  }

  @Override
  public void initialize(ConfigurableApplicationContext applicationContext) {
    getInstance();
    System.setProperty("spring.data.mongodb.host", container.getHost());
    System.setProperty("spring.data.mongodb.port", String.valueOf(container.getFirstMappedPort()));

  }
}