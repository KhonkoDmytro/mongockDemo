package com.example.mongockdemo.migrations._1_create_product;

import com.example.mongockdemo.migrations.common.BaseMigration;
import io.mongock.api.annotations.ChangeUnit;
import io.mongock.api.annotations.Execution;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;

@ChangeUnit(id="createSomeBananas", order = "3", author = "Dmytro Khonko")
@RequiredArgsConstructor
public class V003CreateSomeBananas extends BaseMigration  {
  private final MongoTemplate mongoTemplate;

  @Execution
  public void changeSet() {
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
  }
}
