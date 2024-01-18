package com.example.mongockdemo;

import com.example.mongockdemo.domain.entity.Product;
import com.example.mongockdemo.domain.repository.ProductRepository;
import com.mongodb.client.MongoDatabase;
import io.mongock.api.exception.MongockException;
import io.mongock.test.springboot.junit5.MongockSpringbootJUnit5IntegrationTestBase;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@ContextConfiguration(initializers = MongoTestContainer.class)
@SpringBootTest(classes = MongockDemoApplication.class)
@ActiveProfiles("test")
class TestWithJunitExtention extends MongockSpringbootJUnit5IntegrationTestBase {
  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @AfterEach
  public void afterEach() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void collectionNotCreatedYet () {
    mongoTemplate.getDb().drop();
    executeMongock();
    Assertions.assertEquals(5 , productRepository.findAll().size());
  }

  @Test
  void shouldFailWhenCollectionCreated() {
    mongoTemplate.createCollection(Product.class);
    MongockException ex = Assertions.assertThrows(MongockException.class, this::executeMongock);
    Assertions.assertTrue(ex.getMessage().contains("Collection already exists"));
  }
}
