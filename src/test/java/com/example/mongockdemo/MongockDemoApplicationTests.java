package com.example.mongockdemo;

import com.example.mongockdemo.domain.repository.ProductRepository;
import com.example.mongockdemo.migrations._1_create_product.V003CreateSomeBananas;
import com.example.mongockdemo.migrations._2_update_products.V005UpdateUsingMongoTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(initializers = MongoTestContainer.class)
@SpringBootTest(classes = MongockDemoApplication.class)
@ActiveProfiles("test")
class MongockDemoApplicationTests {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @BeforeEach
  void afterEach() {
    mongoTemplate.getDb().drop();
  }

  @Test
  void testChangesWithMigrations() {
    new V003CreateSomeBananas(mongoTemplate).changeSet();
    new V005UpdateUsingMongoTemplate(mongoTemplate).changeSet();
    Assertions.assertEquals(2 , productRepository.findAll().size());
    Assertions.assertEquals("new apple description" , productRepository.findAllByName("apple").get(0).getDescription());
  }

  @Test
  void testChangesWithMongoTemplate() {
    mongoTemplate.executeCommand("""
            { insert: "product",documents: [
             {
               "price" : 20,
               "name" : "banana",
               "description" : "big regular",
               "_class": "com.example.mongockdemo.domain.entity.Product"
             }, {
               "price" : 30,
               "name" : "apple",
               "description" : "small and sweet",
               "_class": "com.example.mongockdemo.domain.entity.Product"
             },
            ]}
           """);

    new V005UpdateUsingMongoTemplate(mongoTemplate).changeSet();
    Assertions.assertEquals(2 , productRepository.findAll().size());
    Assertions.assertEquals("new apple description" , productRepository.findAllByName("apple").get(0).getDescription());
  }
}
