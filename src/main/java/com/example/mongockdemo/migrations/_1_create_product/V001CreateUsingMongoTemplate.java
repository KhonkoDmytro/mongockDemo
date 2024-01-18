package com.example.mongockdemo.migrations._1_create_product;

import com.example.mongockdemo.domain.entity.Product;
import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.BeforeExecution;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id="createProductUsingMongoTemplate", order = "1", author = "Dmytro Khonko", runAlways = true)
@RequiredArgsConstructor
public class V001CreateUsingMongoTemplate extends BaseMigration {
  private final MongoTemplate mongoTemplate;

  @BeforeExecution
  public void dropDB() {
//    mongoTemplate.dropCollection(Product.class);
    mongoTemplate.createCollection(Product.class);
  }

  @Execution
  public void changeSet() {
    mongoTemplate.executeCommand("""
            { insert: "product",documents: [
             {
               "price" : 30,
               "name" : "apple",
               "description" : "green and sour",
               "_class": "com.example.mongockdemo.domain.entity.Product"
             }, {
               "price" : 40,
               "name" : "apple",
               "description" : "black is new white",
               "_class": "com.example.mongockdemo.domain.entity.Product"
             },
            ]}""");
  }
}
